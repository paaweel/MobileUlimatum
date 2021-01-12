package com.example.mobileultimatum;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Phrases {

    // Constants
//    public static final String ACCEPT = "Akceptuję";
//    public static final String HELLO = "Cześć, czy chcesz zagrać w grę?";
//    public static final String THANKS = "Dziękuję za grę!";
//    public static final String SUPER = "Super";
//    public static final String CANNOT_UNDERSTAND = "Nie rozumiem";
//    public static final String OHNO = "O nie!";
//    public static final String NOT_ACCEPT = "Nie akceptuję";

    public static final String ACCEPT = "I accept";
    public static final String HELLO = "Hey, do you wanna play a game?";
    public static final String THANKS = "Thank you for playing!";
    public static final String SUPER = "Great";
    public static final String CANNOT_UNDERSTAND = "I don't understand";
    public static final String OHNO = "Oh no!";
    public static final String NOT_ACCEPT = "I decline!";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ACCEPT, HELLO, THANKS, SUPER, CANNOT_UNDERSTAND, OHNO, NOT_ACCEPT})
    public @interface PhrasesDef { }
}
