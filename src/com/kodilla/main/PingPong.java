package com.kodilla.main;

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
        boolean gameStarted = false;


        //initializing ball and pads objects
        Ball ball = new Ball(2, 1, 5, 360, 240);
        Pad playerPad = new Pad(100, 10, 25, height);
        Pad compPad = new Pad(100, 10, width - 35, height);

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
                if(e.getY() < (canvas.getHeight() - playerPad.padHeight))  playerPad.padYPos  = e.getY();
                else playerPad.padYPos = canvas.getHeight() - playerPad.padHeight;
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
            gc.fillRect(playerPad.padXPos, playerPad.padYPos, playerPad.padWidth, playerPad.padHeight);
            gc.fillRect(compPad.padXPos, compPad.padYPos, compPad.padWidth, compPad.padHeight);

            //drawing a ball
            if(gameStarted) gc.fillOval(ball.ballXPosition += ball.ballXSpeed, ball.ballYPosition += ball.ballYSpeed, ball.ballRadius*2, ball.ballRadius*2);
            else {
                //starting position of ball
                ball.ballXPosition = 360;
                ball.ballYPosition = 240;
            }

            //checking for collisions
            //1. collision with player pad
            ball.ballXSpeed = ball.padPlayerCollision(playerPad.padXPos, playerPad.padYPos, playerPad.padWidth, playerPad.padHeight);

            //1A collision with comp pad
            ball.ballXSpeed = ball.padCompCollision(compPad.padXPos, compPad.padYPos, compPad.padHeight);

            //2. collision with up and down edge of scene
            ball.ballYSpeed = ball.sceneTopBottomEdgesCollision(height);

            //3. collision with right and left edge of scene
            if(gameStarted) gameStarted = ball.sceneLeftRightEdgesCollision(width);

            //simple AI for padComputer
            if(gameStarted) {
                compPad.padYPos = compPad.AIForCompPad(ball.ballYPosition, height);
            }
        }
}
