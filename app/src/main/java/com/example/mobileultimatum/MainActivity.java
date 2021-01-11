package com.example.mobileultimatum;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int i = 0; // todo: remove or at least rename
    private PepperSpeech pepperSpeech;
    private PepperMotion pepperMotion;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private EditText editText;
    private Button micButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup SpeechRecognizer
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        editText = findViewById(R.id.speechTextView);
        editText.setText("Hold mic to speak...");
        micButton = findViewById(R.id.micBtn);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editText.setText("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                // TODO: create proper error handling
                // refer to documentation for proper error message
                editText.setText("Error..." + i);
            }

            @Override
            public void onResults(Bundle bundle) {
                // TODO: nicer button fo listening, color/icon change
//                micButton.setImageResource(R.drawable.ic_launcher_background);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                System.out.println(data);
                editText.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // TODO: nicer button fo listening, color/icon change
//                    micButton.setImageResource(R.drawable.ic_launcher_background);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });


        // Setup ArFragment
        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        assert arFragment != null;
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            createModel(hitResult.createAnchor(), arFragment);
        }));

        pepperSpeech = new PepperSpeech(getApplicationContext());
        pepperMotion = new PepperMotion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
    }

    private void createModel(Anchor anchor, ArFragment arFragment) {

        ModelRenderable
                .builder()
                .setSource(this, R.raw.raw)
                .build()
                .thenAccept(modelRenderable -> {

                    AnchorNode anchorNode = new AnchorNode(anchor);

                    SkeletonNode skeletonNode = new SkeletonNode();
                    skeletonNode.setParent(anchorNode);
                    skeletonNode.setRenderable(modelRenderable);

                    arFragment.getArSceneView().getScene().addChild(anchorNode);

                    // button has been removed
                    // code kept for reference, delete once animations are properly handled
//                    Button button = findViewById(R.id.animate_btn);
//                    button.setOnClickListener(view -> animateModel(modelRenderable));
                });
    }

    private void animateModel(ModelRenderable modelRenderable) {
        switch(i){
            case 0:
                pepperSpeech.sayWords(Phrases.SUPER);
                break;
            case 1:
                pepperSpeech.sayWords(Phrases.OHNO);

                break;
            case 2:
                pepperMotion.waveHand(modelRenderable);
                break;
            case 3:
                pepperMotion.shakeHead(modelRenderable);
                pepperSpeech.sayWords(Phrases.NOT_ACCEPT);
                break;
            case 4:
                pepperMotion.nodHead(modelRenderable);
                pepperSpeech.sayWords(Phrases.ACCEPT);
                break;
            case 5:
                pepperMotion.shakeHead(modelRenderable);
                pepperSpeech.sayWords(Phrases.CANNOT_UNDERSTAND);
                break;
            case 6:
                pepperSpeech.sayWords(Phrases.HELLO);

                break;
            case 7:
                pepperMotion.nodHead(modelRenderable);
                break;
            case 8:
                pepperSpeech.sayWords(Phrases.THANKS);
                break;
        }
        i++;
        if(i==9){
            i = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
