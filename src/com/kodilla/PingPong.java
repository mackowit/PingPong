package com.kodilla;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PingPong extends Application {

        private Image imageback = new Image("file:resources/background.jpg");

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);


            /*GridPane grid = new GridPane();
            grid.setBackground(background);*/
            Pane pane = new Pane();
            pane.setBackground(background);

            Scene scene = new Scene(pane, 720, 480, Color.BLACK);

            int padHeight = 100;
            int padWidth = 25;
            int padPlayerX = 50;
            int padPlayerY = 190;
            Rectangle padPlayer = new Rectangle(50, scene.getHeight()/2-padHeight/2, padWidth, padHeight);
            padPlayer.setFill(Color.BLUE);

            int padComputerX = 645;
            int padComputerY = 190;
            Rectangle padComputer = new Rectangle(scene.getWidth()-50-padWidth, scene.getHeight()/2-padHeight/2, padWidth, padHeight);
            padComputer.setFill(Color.GREEN);

            Circle ball = new Circle(10);
            ball.setCenterX(360);
            ball.setCenterY(240);
            ball.setFill(Color.YELLOW);

            pane.getChildren().add(padPlayer);
            pane.getChildren().add(padComputer);
            pane.getChildren().add(ball);


            primaryStage.setTitle("Ping Pong");
            primaryStage.setScene(scene);
            primaryStage.show();

        }
}
