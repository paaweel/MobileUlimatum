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
    private ModelAnimator modelAnimator;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            createModel(hitResult.createAnchor(), arFragment);
        }));
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

    private void stopAllAnimations(){
        if (modelAnimator != null && modelAnimator.isRunning()) {
            modelAnimator.end();
        }
    }

    private void animateModel(ModelRenderable modelRenderable) {
        stopAllAnimations();
        int animationCount = modelRenderable.getAnimationDataCount();

        if (i == animationCount)
            i = 0;

        AnimationData animationData = modelRenderable.getAnimationData(i);

        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
        i++;
    }

    private void nodHead(ModelRenderable modelRenderable){
        stopAllAnimations();
        AnimationData animationData = modelRenderable.getAnimationData(4);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
    }

    private void shakeHead(ModelRenderable modelRenderable){
        stopAllAnimations();
        AnimationData animationData = modelRenderable.getAnimationData(5);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
    }

    private void waveHand(ModelRenderable modelRenderable){
        stopAllAnimations();
        AnimationData animationData = modelRenderable.getAnimationData(10);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
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