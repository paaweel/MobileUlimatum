package com.example.mobileultimatum;


import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private PepperSpeech pepperSpeech;
    private PepperMotion pepperMotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            createModel(hitResult.createAnchor(), arFragment);
        }));

        pepperSpeech = new PepperSpeech(getApplicationContext());
        pepperMotion = new PepperMotion();
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

                    Button button = findViewById(R.id.button);
                    button.setOnClickListener(view -> animateModel(modelRenderable));
                });
    }

    private void animateModel(ModelRenderable modelRenderable) {
        switch(i){
            case 0:
                pepperSpeech.saySuper();
                break;
            case 1:
                pepperSpeech.sayOhNo();
                break;
            case 2:
                pepperMotion.waveHand(modelRenderable);
                break;
            case 3:
                pepperMotion.shakeHead(modelRenderable);
                pepperSpeech.sayDontAccept();
                break;
            case 4:
                pepperMotion.nodHead(modelRenderable);
                pepperSpeech.sayAccept();
                break;
            case 5:
                pepperMotion.shakeHead(modelRenderable);
                //"I don't understand, can you repeat?" recording is missing
                break;
            case 6:
                pepperSpeech.sayWelcome();
                break;
            case 7:
                pepperMotion.nodHead(modelRenderable);
                break;
            case 8:
                pepperSpeech.sayThanks();
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