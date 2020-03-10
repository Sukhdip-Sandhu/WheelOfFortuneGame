package com.example.wheeloffortune.model;

public class Player {
    private String playerName;
    private int score;

    public Player() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean canBuyVowel() {
        return getScore() >= 250;
    }

}
