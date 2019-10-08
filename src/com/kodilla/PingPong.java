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


            GridPane grid = new GridPane();
            grid.setBackground(background);

            Scene scene = new Scene(grid, 720, 480, Color.BLACK);

            Rectangle padPlayer = new Rectangle(190,100,25,100);
            padPlayer.setFill(Color.BLUE);

            Rectangle padComputer = new Rectangle(25,25,25,100);
            padComputer.setFill(Color.GREEN);

            Circle ball = new Circle(10);
            ball.setFill(Color.YELLOW);

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100);
            column.setHgrow(Priority.ALWAYS);

            grid.add(padPlayer, 1, 0);
            grid.add(padComputer, 1, 0);
            grid.add(ball, 1, 0);

            primaryStage.setTitle("Ping Pong");
            primaryStage.setScene(scene);
            primaryStage.show();

        }
}
