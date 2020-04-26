package com.kodilla.main;

import java.io.Serializable;

public class Score implements Serializable {
    public int playerPts;
    public int compPts;
    public int level;

    public Score(int playerPts, int compPts, int level) {
        this.playerPts = playerPts;
        this.compPts = compPts;
        this.level = level;
    }

    public int getPtsDiff() {
        return playerPts - compPts;
    }

    @Override
    public String toString() {
        return "Score{" +
                "playerPts=" + playerPts +
                ", compPts=" + compPts +
                ", level=" + level +
                '}';
    }

    public void resetScore() {
        playerPts = 0;
        compPts = 0;
        level = 1;
    }
}
