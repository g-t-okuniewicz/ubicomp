package com.example.gokuniewicz.highernumber;

import java.util.Random;

/**
 * Created by gokuniewicz on 24.09.2017.
 */

public class Game {
    private int leftNumber, rightNumber, score;

    public Game() {
        rollNumbers();
        score = 0;
    }

    public int getLeftNumber() {
        return leftNumber;
    }

    public int getRightNumber() {
        return rightNumber;
    }

    public void checkAnswer(boolean b) {
        if((b && leftNumber > rightNumber) || (!b && rightNumber > leftNumber)) {
            score++;
            rollNumbers();
        } else
            rollNumbers();
    }

    public int getScore() {
        return score;
    }

    private void rollNumbers() {
        Random r = new Random();
        leftNumber = r.nextInt(101 - 1) + 1;
        do{
            rightNumber = r.nextInt(101 - 1) + 1;
        } while (rightNumber == leftNumber);
    }


}
