package com.kodilla.main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PingPong extends Application {

        private Image imageback = new Image("file:resources/background.jpg");
        int width = 720;
        int height = 480;
        boolean gameStarted = false;
        Score score = new Score();


        //initializing ball and pads objects
        Ball ball = new Ball(2, 1, 5, 360, 240);
        Pad playerPad = new Pad(100, 10, 25, height);
        Pad compPad = new Pad(100, 10, width - 35, height);

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            VBox vBox = new VBox();
            Scene scene = new Scene(vBox);
            score.playerPts = 0;
            score.compPts = 0;

            //menubar
            MenuBar menuBar = new MenuBar();
            Menu menuFile = new Menu("File");
            Menu menuEdit = new Menu("Edit");
            Menu menuView = new Menu("View");
            menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
            ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);

            StackPane pane = new StackPane(canvas);
            pane.setBackground(background);
            vBox.getChildren().add(pane);

            //label with score
            Label label = new Label();
            label.setTextFill(Color.DEEPSKYBLUE);
            label.setOpacity(0.500);
            label.setFont(Font.font("Arial", 100));
            pane.getChildren().add(label);

            //initializing move
            Timeline tLine = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                score = run(gc, score);
                label.setText(Integer.toString(score.playerPts) + " : " + Integer.toString(score.compPts));
            }));
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

        private Score run(GraphicsContext gc, Score score) {
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
            /*if(gameStarted) gameStarted = ball.sceneLeftEdgeCollision();
            else if(gameStarted) gameStarted = ball.sceneRightEdgeCollision(width);*/
            if(gameStarted) {
                if(!ball.sceneLeftEdgeCollision()) {
                    gameStarted = false;
                    score.compPts++;
                }
                else if (!ball.sceneRightEdgeCollision(width)) {
                    gameStarted = false;
                    score.playerPts++;
                }
            }

            //simple AI for padComputer
            if(gameStarted) {
                compPad.padYPos = compPad.AIForCompPad(ball.ballYPosition, height);
            }
        return score;
        }
}
