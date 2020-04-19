package com.kodilla.main;

public class Ball {
    double ballXSpeed;
    double ballYSpeed;
    int ballRadius;
    double ballXPosition;
    double ballYPosition;

    public Ball(double ballXSpeed, double ballYSpeed, int ballRadius, double ballXPosition, double ballYPosition) {
        this.ballXSpeed = ballXSpeed;
        this.ballYSpeed = ballYSpeed;
        this.ballRadius = ballRadius;
        this.ballXPosition = ballXPosition;
        this.ballYPosition = ballYPosition;
    }

    public double padPlayerCollision(int padXPos, double padPlayerYPos, int padWidth, double padHeight) {
        if (ballXPosition < (padXPos + padWidth)) {
            if (ballYPosition >= padPlayerYPos && ballYPosition <= (padPlayerYPos + padHeight)) {
                return -ballXSpeed;
            } else {
                return ballXSpeed;
            }
        } else {
            return ballXSpeed;
        }
    }

    public double padCompCollision(int padXPos, double padCompYPos, double padHeight) {
        if (ballXPosition + ballRadius * 2 > padXPos) {
            if(ballYPosition >= padCompYPos && ballYPosition <= (padCompYPos + padHeight)) {
                return -ballXSpeed;
            } else {
                return ballXSpeed;
            }
        } else {
            return ballXSpeed;
        }
    }

    public double sceneTopBottomEdgesCollision(int height) {
        if (ballYPosition <= 0 || ballYPosition >= (height - ballRadius * 2)) {
                ballYSpeed = -ballYSpeed;
            }
        return ballYSpeed;
    }

    public boolean sceneLeftEdgeCollision() {
        return (ballXPosition >= 0); //&& ballXPosition <= (width - ballRadius * 2));
    }

    public boolean sceneRightEdgeCollision(int width) {
        //return (ballXPosition >= 0 &&
        return (ballXPosition <= (width - ballRadius * 2));
    }
}
