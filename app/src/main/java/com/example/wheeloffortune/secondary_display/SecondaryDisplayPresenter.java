package com.example.wheeloffortune.secondary_display;

import com.example.wheeloffortune.R;
import com.example.wheeloffortune.model.Player;
import com.example.wheeloffortune.util.PuzzleUtil;

import java.util.ArrayList;
import java.util.Random;

public class SecondaryDisplayPresenter implements SecondaryDisplayContract.Presenter {

    private static final String TAG = "WHEEL_OF_FORTUNE_TAG";

    private SecondaryDisplayContract.View view;
    private PuzzleUtil puzzleUtil;
    private Player playerOne = new Player();
    private Player playerTwo = new Player();
    private Player playerThree = new Player();

    private char[] puzzleAnswer;
    private char[] userAnswer;
    private int playerTurn = 0;
    private long degrees = 0;
    private ArrayList<String> lettersGuessed;

    private int currentWheelValue = 0;

    public SecondaryDisplayPresenter(SecondaryDisplayContract.View view) {
        this.view = view;
        puzzleUtil = new PuzzleUtil();
        view.updatePuzzleGridView(puzzleUtil.getBlankStartingPuzzle());
    }

    @Override
    public void onSetPuzzle(String puzzleName) {
        lettersGuessed = new ArrayList<>();
        view.setLettersGuessed(lettersGuessed);
        puzzleAnswer = puzzleUtil.formatPuzzle(puzzleName);
        userAnswer = puzzleUtil.formatUserAnswer(puzzleAnswer);
        view.toggleBackgroundMusic(true);
        view.playSound(R.raw.puzzle_start);
        view.updatePuzzleGridView(userAnswer);
        view.highlightPlayer(playerTurn);
    }

    @Override
    public void onSetPuzzleCategory(String puzzleCategory) {
        view.setPuzzleCategory(puzzleCategory);
    }

    @Override
    public void onSetPlayerOne(String playerOneName) {
        playerOne.setScore(0);
        playerOne.setPlayerName(playerOneName);
        view.setPlayerOneScore(playerOne.getScore());
        view.setPlayerOneName(playerOne.getPlayerName());
    }

    @Override
    public void onSetPlayerTwo(String playerTwoName) {
        playerTwo.setScore(0);
        playerTwo.setPlayerName(playerTwoName);
        view.setPlayerTwoScore(playerTwo.getScore());
        view.setPlayerTwoName(playerTwo.getPlayerName());
    }

    @Override
    public void onSetPlayerThree(String playerThreeName) {
        playerThree.setScore(0);
        playerThree.setPlayerName(playerThreeName);
        view.setPlayerThreeScore(playerThree.getScore());
        view.setPlayerThreeName(playerThree.getPlayerName());
    }

    @Override
    public void onSpinWheel() {
        view.spinWheel();
    }

    @Override
    public void onGuessConsonant(String consonant) {
        boolean letterAppears = false;
        int howManyTimes = 0;

        char guess = consonant.charAt(0);

        for (int i = 0; i < puzzleAnswer.length; i++) {
            if (puzzleAnswer[i] == guess) {
                letterAppears = true;
                howManyTimes++;
                userAnswer[i] = guess;
            }
        }

        if (letterAppears) {
            addUserScore(playerTurn, howManyTimes * getCurrentWheelValue());
            view.playSound(R.raw.correct_letter);
            view.updatePuzzleGridView(userAnswer);
        } else {
            lettersGuessed.add(consonant);
            view.setLettersGuessed(lettersGuessed);
            view.playSound(R.raw.incorrect_letter);
            nextPlayerTurn();
        }

    }

    @Override
    public void onBuyVowel(String vowel) {
        boolean letterAppears = false;
        if (getPlayer(playerTurn).canBuyVowel()) {
            addUserScore(playerTurn, -250);

            char guess = vowel.charAt(0);

            for (int i = 0; i < puzzleAnswer.length; i++) {
                if (puzzleAnswer[i] == guess) {
                    letterAppears = true;
                    userAnswer[i] = guess;
                }
            }

            if (letterAppears) {
                view.playSound(R.raw.correct_letter);
                view.updatePuzzleGridView(userAnswer);
            } else {
                lettersGuessed.add(vowel);
                view.setLettersGuessed(lettersGuessed);
                view.playSound(R.raw.incorrect_letter);
                nextPlayerTurn();
            }

        } else {
            // TODO
        }
    }

    @Override
    public void onSolvePuzzle(boolean isCorrect, String puzzleName) {
        if (isCorrect) {
            view.toggleBackgroundMusic(false);
            view.playSound(R.raw.puzzle_solved);
            view.updatePuzzleGridView(puzzleAnswer);
        } else {
            view.playSound(R.raw.incorrect_letter);
            nextPlayerTurn();
        }
    }

    @Override
    public void onSwitchPlayer() {
        nextPlayerTurn();
    }

    void onWheelFinishedSpining(String finalWheelValue) {
        switch (finalWheelValue) {
            case "Bankrupt":
                bankruptPlayer(playerTurn);
                break;
            case "Lose Turn":
                nextPlayerTurn();
                break;
            case "Free Play":
                setCurrentWheelValue(2000);
                break;
            case "Million":
                setCurrentWheelValue(1000000);
                break;
            case "Wild":
                setCurrentWheelValue((new Random().nextInt(20) + 5) * 100);
                break;
            default:
                setCurrentWheelValue(Integer.parseInt((finalWheelValue)));
                break;
        }
    }

    // HELPER
    private Player getPlayer(int playerTurn) {
        switch (playerTurn) {
            case 0:
                return playerOne;
            case 1:
                return playerTwo;
            default:
                return playerThree;
        }
    }

    private void bankruptPlayer(int playerTurn) {
        getPlayer(playerTurn).setScore(0);
        nextPlayerTurn();
        updateAllPlayerScores();
    }

    private void nextPlayerTurn() {
        playerTurn++;
        if (playerTurn == 3) {
            playerTurn = 0;
        }
        view.highlightPlayer(playerTurn);
    }

    private void addUserScore(int playerTurn, int score) {
        int tmpScore = getPlayer(playerTurn).getScore();
        tmpScore += score;
        getPlayer(playerTurn).setScore(tmpScore);
        updateAllPlayerScores();
    }

    private void updateAllPlayerScores() {
        view.setPlayerOneScore(playerOne.getScore());
        view.setPlayerTwoScore(playerTwo.getScore());
        view.setPlayerThreeScore(playerThree.getScore());

    }

    public int getCurrentWheelValue() {
        return currentWheelValue;
    }

    public void setCurrentWheelValue(int currentWheelValue) {
        this.currentWheelValue = currentWheelValue;
    }

}
