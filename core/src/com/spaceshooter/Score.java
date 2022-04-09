package com.spaceshooter;

public class Score {
    private int points;

    public Score() {
        this.points = 0;
    }

    public Score(int points) {
        this.points = points;
    }

    public void setScore(int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    public void increaseScore(int points) {
        this.points += points;
    }
}
