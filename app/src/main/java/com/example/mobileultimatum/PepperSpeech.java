package com.example.mobileultimatum;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import java.util.Locale;
import android.os.Build;

public class PepperSpeech {

    private TextToSpeech textToSpeechEngine;

    OnInitListener onInit = new OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.setLanguage(new Locale("pl-PL")) ;
            }
        }
    };

    PepperSpeech(Context appContext){
        textToSpeechEngine = new TextToSpeech(appContext, onInit);
    }

    public void sayWords(@Phrases.PhrasesDef String text){
        if (text.length() > 0 ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            } else {
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public void shutdown(){
        textToSpeechEngine.stop();
    }
    public void stop(){
        textToSpeechEngine.shutdown();
    }

}

