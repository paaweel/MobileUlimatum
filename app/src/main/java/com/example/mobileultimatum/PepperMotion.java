package com.example.mobileultimatum;

import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class PepperMotion {
    private ModelAnimator modelAnimator;

    private void stopAllAnimations(){
        if (modelAnimator != null && modelAnimator.isRunning()) {
            modelAnimator.end();
        }
    }

    public void nodHead(ModelRenderable modelRenderable){
        stopAllAnimations();
        AnimationData animationData = modelRenderable.getAnimationData(4);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
    }

    public void shakeHead(ModelRenderable modelRenderable){
        stopAllAnimations();
        AnimationData animationData = modelRenderable.getAnimationData(5);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
    }

    public void waveHand(ModelRenderable modelRenderable){
        stopAllAnimations();
        AnimationData animationData = modelRenderable.getAnimationData(10);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
    }
}
