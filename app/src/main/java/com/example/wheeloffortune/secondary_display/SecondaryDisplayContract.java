package com.example.wheeloffortune.secondary_display;

import java.util.ArrayList;

public interface SecondaryDisplayContract {

    interface View {
        void updatePuzzleGridView(char[] puzzle);

        void setPuzzleCategory(String puzzleCategory);

        void highlightPlayer(int playerTurn);

        void setLettersGuessed(ArrayList<String> lettersGuessedArrayList);

        void setPlayerOneName(String name);

        void setPlayerOneScore(int score);

        void setPlayerTwoName(String name);

        void setPlayerTwoScore(int score);

        void setPlayerThreeName(String name);

        void setPlayerThreeScore(int score);

        void spinWheel();

        void toggleBackgroundMusic(boolean turnMusicOn);

        void playSound(int audioFile);
    }

    interface Presenter {
        void onSetPuzzle(String puzzleName);

        void onSetPuzzleCategory(String puzzleCategory);

        void onSetPlayerOne(String playerOneName);

        void onSetPlayerTwo(String playerTwoName);

        void onSetPlayerThree(String playerThreeName);

        void onSpinWheel();

        void onGuessConsonant(String consonant);

        void onBuyVowel(String toUpperCase);

        void onSolvePuzzle(boolean isCorrect, String puzzleName);

        void onSwitchPlayer();
    }

}
