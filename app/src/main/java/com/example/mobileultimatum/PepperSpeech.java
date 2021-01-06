package com.example.mobileultimatum;

import android.content.Context;
import android.media.MediaPlayer;

public class PepperSpeech {
    private MediaPlayer mediaPlayer;
    private Context context;

    PepperSpeech(Context appContext){
        context = appContext;
        mediaPlayer = MediaPlayer.create(appContext, R.raw.czesc);
    }

    private void playSound(int resource) {
        if (mediaPlayer.isPlaying()){
            this.stopPlaying();
        }
        mediaPlayer = MediaPlayer.create(context, resource);

        mediaPlayer.start();
    }

    private void stopPlaying() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void saySuper(){
        this.playSound(R.raw.superowo);
    }
    public void sayOhNo(){
        this.playSound(R.raw.onie);
    }
    public void sayDontAccept(){ this.playSound(R.raw.nieakceptuje); }
    public void sayAccept(){
        this.playSound(R.raw.akceptuje);
    }
    public void sayWelcome(){
        this.playSound(R.raw.czesc);
    }
    public void sayThanks(){ this.playSound(R.raw.dziekuje); }

}

