package com.kodilla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PingPong extends Application {

        private Image imageback = new Image("file:resources/background.jpg");
        int width = 720;
        int height = 480;
        double ballXPosition = 360;
        double ballYPosition = 240;
        double padHeight = 100;
        int padWidth = 10;
        int padXPos = 25;
        double padPlayerYPos = height/2 - padHeight/2;
        double padCompYPos = height/2 - padHeight/2;
        double ballXSpeed = 2;
        double ballYSpeed = 1;
        int ballRadius = 5;
        boolean gameStarted = false;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);

            StackPane pane = new StackPane(canvas);
            pane.setBackground(background);

            Scene scene = new Scene(pane);
            //initializing move
            Timeline tLine = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
            tLine.setCycleCount(Timeline.INDEFINITE);

            primaryStage.setTitle("Ping Pong");
            primaryStage.setScene(scene);
            primaryStage.show();
            tLine.play();

            //animating padPlayer move
            canvas.setOnMouseMoved(e ->  {
                if(e.getY() < (canvas.getHeight() - padHeight))  padPlayerYPos  = e.getY();
                else padPlayerYPos = canvas.getHeight() - padHeight;
            });

            //click on start
            canvas.setOnMouseClicked(e ->  gameStarted = true);
        }

        private void run(GraphicsContext gc) {
            //clearing the scene
            gc.clearRect(0, 0, width, height);
            //setting all shapes color
            gc.setFill(Color.WHITE);
            //drawing pads
            gc.fillRect(padXPos, padPlayerYPos, padWidth, padHeight);
            gc.fillRect(width - padXPos - padWidth, padCompYPos, padWidth, padHeight);
            //drawing a ball
            if(gameStarted) gc.fillOval(ballXPosition += ballXSpeed,ballYPosition +=ballYSpeed, ballRadius*2, ballRadius*2);
            else {
                //starting position of ball
                ballXPosition = 360;
                ballYPosition = 240;
            }

            //checking for collisions
            //1. collision with pads
            if((ballYPosition >= padPlayerYPos && ballYPosition <= padPlayerYPos + padHeight) || (ballYPosition >= padCompYPos && ballYPosition <= padCompYPos + padHeight))
            {
                if (ballXPosition < (padXPos + padWidth) || ballXPosition + ballRadius * 2 > (width - (padXPos + padWidth))) {
                    ballXSpeed = -ballXSpeed;
                    //ballYSpeed = -ballYSpeed;
                }
            }
            //2. collision with up and down edge of scene
            if(ballXPosition >= 0 && ballXPosition <= width - ballRadius * 2) {
                if(ballYPosition <= 0 || ballYPosition >= height - ballRadius * 2) ballYSpeed = -ballYSpeed;
            } else
            //3. collision with right and left edge of scene
            gameStarted = false;

            //simple AI for padComputer
            //padCompYPos = padPlayerYPos;
            if(gameStarted) {
                if(ballYPosition < height - padHeight) {
                    if (padCompYPos - ballYPosition > 200) padCompYPos -= 2;
                    else if (padCompYPos - ballYPosition > 0) padCompYPos -= 4;
                    else if (padCompYPos - ballYPosition < -200) padCompYPos += 2;
                    else if (padCompYPos - ballYPosition < 0) padCompYPos += 4;
                }
            }



        }
}
