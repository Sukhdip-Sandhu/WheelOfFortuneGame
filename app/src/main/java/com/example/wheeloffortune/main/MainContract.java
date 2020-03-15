package com.example.wheeloffortune.main;

public interface MainContract {

    interface View {
        void startGameButtonToggles(boolean startGame);

        void spinWheelButtonToggles(boolean onSpinWheel);

        void showConsonantDialog();

        void displayToast(String msg);

        void toggleVowelButton(boolean playerCanBuyVowel);
    }

    interface Presenter {
        void connectToScreen(MainActivity context);

        void onNewPuzzle(String puzzleName, String puzzleCategory, String playerOneName, String playerTwoName, String playerThreeName);

        void onSpinWheel();

        void onGuessConsonant(String consonant);

        void onBuyVowel(String vowel);

        void onSolvePuzzle(boolean b, String puzzleName);

        void onSwitchPlayer();

        void onDisplayConsonantDialog();

        void canBuyVowel(boolean playerCanBuyVowel);
    }

}
