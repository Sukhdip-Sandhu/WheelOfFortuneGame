package com.example.wheeloffortune.util;


/*
THIS MIGHT BE THE DUMBEST CODE I"VE EVER WRITTEN BUT IT IS CURRENTLY 3:27am SO IN THE OFF CHANCE
THAT YOU ARE READING THIS PLEASE ENJOY ...
 */
public class PuzzleUtil {

    private final int SIZE_OF_GRID = 56;
    public static final char SPACE = ' '; // represents a space bar
    public static final char MISSING_LETTER = '_'; // represents a letter part of puzzle
    public static final char PLACEHOLDER = '-'; // placeholder character surrounds puzzle

    public PuzzleUtil() {
    }

    public char[] getBlankStartingPuzzle() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < SIZE_OF_GRID; i++)
            tmp.append(PLACEHOLDER);
        return tmp.toString().toCharArray();
    }

    public char[] formatPuzzle(String puzzleAnswer) {
        String[] split = puzzleAnswer.split(" ");
        int x = 0;
        int i = 0;
        int lenFirstRow = 0;
        int lenSecondRow = 0;
        String firstHalf = "";
        String secondHalf = "";
        String userAnswerPuzzleString = "";

        for (String s : split) {
            x += s.length() + 1;
            if (x > 14) { // determine which words go on top line
                break;
            }
            i++;
        }

        for (int j = 0; j < i; j++) {
            lenFirstRow += split[j].length() + 1;
            firstHalf += split[j] + " ";
        }
        firstHalf = firstHalf.trim(); // string representing top row
        lenFirstRow = lenFirstRow - 1; // length of top row string

        for (int j = i; j < split.length; j++) {
            lenSecondRow += split[j].length() + 1;
            secondHalf += split[j] + " ";
        }
        secondHalf = secondHalf.trim(); // string representing bottom row
        lenSecondRow = lenSecondRow - 1; // length of bottom row string
        if (lenSecondRow < 0) lenSecondRow = 0;

        for (int j = 0; j < 14 - lenFirstRow; j++) {
            if (j % 2 == 1) {
                firstHalf = PLACEHOLDER + firstHalf; // add placeholder left of string
            } else {
                firstHalf = firstHalf + PLACEHOLDER; // add placeholder right of string
            }
        }

        for (int j = 0; j < 14 - lenSecondRow; j++) {
            if (j % 2 == 1) {
                secondHalf = PLACEHOLDER + secondHalf; // add placeholder left of string
            } else {
                secondHalf = secondHalf + PLACEHOLDER; // add placeholder right of string
            }
        }

        for (int j = 0; j < 14; j++) {
            userAnswerPuzzleString += PLACEHOLDER;
        }

        userAnswerPuzzleString = userAnswerPuzzleString + firstHalf + secondHalf;

        for (int j = 0; j < 14; j++) {
            userAnswerPuzzleString += PLACEHOLDER;
        }

        return userAnswerPuzzleString.toCharArray();
    }


    public char[] formatUserAnswer(char[] puzzleAnswer) {
        char[] result = new char[puzzleAnswer.length];
        for (int i = 0; i < puzzleAnswer.length; i++)
            if (puzzleAnswer[i] == SPACE) {
                result[i] = SPACE; // SPACE IS A SPACE
            } else if (puzzleAnswer[i] == PLACEHOLDER) {
                result[i] = PLACEHOLDER; // PLACEHOLDER IS A PLACEHOLDER
            } else {
                result[i] = MISSING_LETTER; // ALL LETTERS SET AS MISSING_LETTER
            }
        return result;
    }
}
