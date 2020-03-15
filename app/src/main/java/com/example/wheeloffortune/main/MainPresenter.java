package com.example.wheeloffortune.main;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

import com.example.wheeloffortune.secondary_display.SecondaryDisplay;
import com.example.wheeloffortune.secondary_display.SecondaryDisplayPresenter;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private SecondaryDisplay secondaryDisplay;
    private SecondaryDisplayPresenter displayPresenter;

    public DisplayManager displayManager = null;
    public Display[] presentationDisplays = null;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void connectToScreen(MainActivity context) {
        displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager != null) {
            presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if (presentationDisplays.length > 0) {
                secondaryDisplay = new SecondaryDisplay(context, presentationDisplays[0]);
                secondaryDisplay.show();
                displayPresenter = new SecondaryDisplayPresenter(secondaryDisplay);
                secondaryDisplay.passPresenter(displayPresenter);
            }
        }
    }

    @Override
    public void onNewPuzzle(String puzzleName, String puzzleCategory, String playerOneName, String playerTwoName, String playerThreeName) {
        displayPresenter.onSetPuzzle(puzzleName.toUpperCase());
        displayPresenter.onSetPuzzleCategory(puzzleCategory.toUpperCase());
        displayPresenter.onSetPlayerOne(playerOneName.toUpperCase());
        displayPresenter.onSetPlayerTwo(playerTwoName.toUpperCase());
        displayPresenter.onSetPlayerThree(playerThreeName.toUpperCase());
        view.toggleButtons(true);
    }

    @Override
    public void onSpinWheel() {
        displayPresenter.onSpinWheel();
    }

    @Override
    public void onGuessConsonant(String consonant) {
        if (isConsonant(consonant)) {
            displayPresenter.onGuessConsonant(consonant.toUpperCase());
        } else {
            view.displayToast("Please enter a valid consonant");
        }
    }

    @Override
    public void onBuyVowel(String vowel) {
        if (isVowel(vowel)) {
            displayPresenter.onBuyVowel(vowel.toUpperCase());
        } else {
            view.displayToast("Please enter a valid vowel");
        }
    }

    @Override
    public void onSolvePuzzle(boolean isAnswerCorrect, String puzzleName) {
        if (isAnswerCorrect) view.toggleButtons(false);
        displayPresenter.onSolvePuzzle(isAnswerCorrect, puzzleName);
    }

    @Override
    public void onSwitchPlayer() {
        displayPresenter.onSwitchPlayer();
    }

    private boolean isVowel(String vowel) {
        String[] vowels = {"A", "E", "I", "O", "U"};
        for (String v : vowels) if (v.equalsIgnoreCase(vowel)) return true;
        return false;
    }

    private boolean isConsonant(String consonant) {
        String[] consonants = {"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N",
                "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z"};
        for (String c : consonants) if (c.equalsIgnoreCase(consonant)) return true;
        return false;
    }

}
