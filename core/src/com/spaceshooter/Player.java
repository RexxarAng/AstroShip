package com.spaceshooter;

public class Player {

    private final int id;
    private int upKey;
    private int leftKey;
    private int downKey;
    private int rightKey;
    private int secondaryKey;
    private String name;
    private Score score;

    public Player(int upKey, int leftKey, int downKey, int rightKey, int secondaryKey, String name, int id) {
        this.upKey = upKey;
        this.leftKey = leftKey;
        this.downKey = downKey;
        this.rightKey = rightKey;
        this.secondaryKey = secondaryKey;
        this.name = name;
        this.id = id;
        this.score = new Score();
    }

    public int getUpKey() {
        return upKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public int getSecondaryKey() {
        return secondaryKey;
    }

    public void setSecondaryKey(int secondaryKey) {
        this.secondaryKey = secondaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score.getPoints();
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

    public void increaseScore(int score) {
        this.score.increaseScore(score);
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

}
