package com.example.mobileultimatum;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Phrases {

    // Constants
    public static final String ACCEPT = "Akceptuję";
    public static final String HELLO = "Cześć, czy chcesz zagrać w grę?";
    public static final String THANKS = "Dziękuję za grę!";
    public static final String SUPER = "Akceptuję";
    public static final String CANNOT_UNDERSTAND = "Akceptuję";
    public static final String OHNO = "Akceptuję";
    public static final String NOT_ACCEPT = "Nie akceptuję";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ACCEPT, HELLO, THANKS, SUPER, CANNOT_UNDERSTAND, OHNO, NOT_ACCEPT})
    public @interface PhrasesDef { }
}
