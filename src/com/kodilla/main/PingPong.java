package com.kodilla.main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PingPong extends Application {

        private Image imageback = new Image("file:resources/background.jpg");
        int width = 720;
        int height = 480;
        boolean gameInitialized = false; //first initialisation of game flag
        boolean gameStarted = false; //resuming game after score/lose with initial ball position
        boolean gamePaused = false; //resuming after pause with remembered ball position
        Score score = new Score(0, 0, 1);
        double pausedBallXPosition;
        double pausedBallYPosition;

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
            score.resetScore();

            //menubar
            MenuBar menuBar = addMenu(primaryStage, scene);

            Canvas canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);

            StackPane pane = new StackPane(canvas);
            pane.setBackground(background);
            vBox.getChildren().add(pane);

            //label with score
            Label scoreLabel = new Label();
            scoreLabel.setTextFill(Color.DEEPSKYBLUE);
            scoreLabel.setOpacity(0.500);
            scoreLabel.setFont(Font.font("Arial", 100));
            pane.getChildren().add(scoreLabel);

            //label with start info
            Label infoLabel = new Label();
            infoLabel.setTextFill(Color.DEEPSKYBLUE);
            infoLabel.setOpacity(0.500);
            infoLabel.setFont(Font.font("Arial", 20));
            infoLabel.setPadding(new Insets(0, 0, 200, 0));
            pane.getChildren().add(infoLabel);
            if(!gameInitialized) infoLabel.isVisible();

            //label with pause-info
            Label pauseLabel = new Label("Press space to pause the game");
            pauseLabel.setTextFill(Color.DEEPSKYBLUE);
            pauseLabel.setOpacity(0.500);
            pauseLabel.setFont(Font.font("Arial", 10));
            pauseLabel.setPadding(new Insets(350, 0, 0, 0));
            pane.getChildren().add(pauseLabel);

            //label with level-info
            Label levelLabel = new Label();
            levelLabel.setTextFill(Color.DEEPSKYBLUE);
            levelLabel.setOpacity(0.500);
            levelLabel.setFont(Font.font("Arial", 20));
            levelLabel.setPadding(new Insets(150, 0, 0, 0));
            pane.getChildren().add(levelLabel);

            //initializing move
            Timeline tLine = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                score = run(gc, score);

                //enabling menubar
                if(gameStarted && !gamePaused) menuBar.setDisable(true);
                else menuBar.setDisable(false);

                //displaying the score
                scoreLabel.setText(Integer.toString(score.playerPts) + " : " + Integer.toString(score.compPts));
                levelLabel.setText("Level " + score.level);

                //displaying info after scoring or pausing
                if (gamePaused) {
                    infoLabel.setVisible(true);
                    infoLabel.setText("Click or press pause to continue");
                } else if(!gameInitialized) {
                    infoLabel.setVisible(true);
                    infoLabel.setText("Click to start the game");
                } else if(!gameStarted) {
                    infoLabel.setVisible(true);
                    infoLabel.setText("Click to continue the game");
                } else {
                    infoLabel.setVisible(false);
                }

                if(gameInitialized && gameStarted) {
                    pauseLabel.setVisible(true);
                    //pausing
                    if (!gamePaused) {
                        pausedBallXPosition = ball.ballXPosition;
                        pausedBallYPosition = ball.ballYPosition;
                        canvas.setOnKeyPressed(ke -> {
                            if (ke.getCode() == KeyCode.SPACE) {
                                gamePaused = true;
                            }
                        });
                    }

                    //resuming after pause
                    if (gamePaused) {
                        canvas.setOnKeyPressed(ke -> {
                            if (ke.getCode() == KeyCode.SPACE) {
                                gamePaused = false;
                            }
                        });
                        pauseLabel.setVisible(false);
                    }
                } else pauseLabel.setVisible(false);
            }));
            tLine.setCycleCount(Timeline.INDEFINITE);

            primaryStage.setTitle("Ping Pong");
            primaryStage.setScene(scene);
            primaryStage.show();
            canvas.requestFocus();
            tLine.play();

            //animating padPlayer move
            canvas.setOnMouseMoved(e -> {
                if (gameInitialized && !gamePaused) {
                        if (e.getY() < (canvas.getHeight() - playerPad.padHeight)) playerPad.padYPos = e.getY();
                        else playerPad.padYPos = canvas.getHeight() - playerPad.padHeight;
                    }
                });

            //click on start
            scene.setOnMouseClicked(e ->  {
                gameInitialized = true;
                gameStarted = true;
                gamePaused = false;
                infoLabel.setVisible(false);
            });
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
            if(gameStarted && !gamePaused) gc.fillOval(ball.ballXPosition += ball.ballXSpeed, ball.ballYPosition += ball.ballYSpeed, ball.ballRadius*2, ball.ballRadius*2);
            else if (gameStarted && gamePaused) {
                //position after pause
                ball.ballXPosition = pausedBallXPosition;
                ball.ballYPosition = pausedBallYPosition;
            } else if(!gameStarted && !gamePaused) {
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
            if(gameStarted) {
                if(!ball.sceneLeftEdgeCollision()) {
                    gameStarted = false;
                    score.compPts++;
                    if(score.compPts%2 == 0 && score.compPts!=0) {
                        if(ball.ballYSpeed > 0) ball.ballYSpeed+=0.5;
                        else ball.ballYSpeed-=0.5;
                        if(ball.ballXSpeed > 0) ball.ballXSpeed+=0.5;
                        else ball.ballXSpeed-=0.5;
                        score.level++;
                    }
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

        private MenuBar addMenu(Stage primaryStage, Scene scene) {
            MenuBar menuBar = new MenuBar();
            Menu menuFile = new Menu("Game");
            Menu menuView = new Menu("About");
            menuBar.getMenus().addAll(menuFile, menuView);
            ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

            MenuItem newGame = new MenuItem("New Game");
            newGame.setOnAction(e -> {
                if(gameInitialized) resultToFile(score);
                gameInitialized = false;
                gameStarted = false;
                gamePaused = false;
                score.resetScore();
            });
            menuFile.getItems().addAll(newGame);

            MenuItem bestResults = new MenuItem("Best results");
            bestResults.setOnAction(e -> {
                List<Score> ranking = resultsFromFile();
                ranking.stream()
                        .forEach(System.out::println);
                Stage bestResultsPopUp = new Stage();
                bestResultsPopUp.setTitle("Best results");
                Label bestResultsLabel = new Label();
                bestResultsLabel.setTextFill(Color.DEEPSKYBLUE);
                bestResultsLabel.setFont(Font.font("Arial", 15));
                bestResultsLabel.setText(rankingListToText(ranking));
                Button closeButton = new Button("Close");
                closeButton.setOnAction(b -> bestResultsPopUp.close());
                VBox layout= new VBox();
                layout.setAlignment(Pos.CENTER);
                bestResultsLabel.setAlignment(Pos.TOP_CENTER);
                closeButton.setAlignment(Pos.BOTTOM_CENTER);
                layout.getChildren().addAll(bestResultsLabel, closeButton);
                Scene scene1= new Scene(layout, 300, 250);
                bestResultsPopUp.setScene(scene1);
                bestResultsPopUp.showAndWait();
            });
            menuFile.getItems().addAll(bestResults);

            MenuItem closeGame = new MenuItem("Close game");
            closeGame.setOnAction(e -> {
                if(gameInitialized) resultToFile(score);
                primaryStage.close();
            });
            menuFile.getItems().addAll(closeGame);

            MenuItem aboutGame = new MenuItem("About game");
            aboutGame.setOnAction(e -> {

            });
            menuView.getItems().addAll(aboutGame);

            return menuBar;
        }

        private void resultToFile(Score score) {
            Path path = Paths.get("C:\\Users\\Samsung\\Projects\\pingpong\\ranking.txt");

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND))
            {
                writer.append("player: " + score.playerPts + ", computer: " + score.compPts + ", level: " + score.level + System.lineSeparator());
            } catch (IOException e) {
                System.out.println("Wystąpił błąd zapisu: " + e);
            }
        }

        private Score fileLineConverter(String fileLine) {
            Pattern pattern = Pattern.compile("player: (\\d+), computer: (\\d+), level: (\\d+)");
            Matcher matcher = pattern.matcher(fileLine);
            if (matcher.matches()) {
                return new Score(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
            } else return null;
        }

        private List<Score> resultsFromFile() {
            List<String> fromFile = new ArrayList<>();
            Path path = Paths.get("C:\\Users\\Samsung\\Projects\\pingpong\\ranking.txt");

            try
            {
                fromFile = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException e)
            {
                System.out.println("Wystąpił błąd odczytu: " + e);
            }

            List<Score> results = fromFile.stream()
                    .map(line -> fileLineConverter(line))
                    .collect(Collectors.toList());

            List<Score> sortedResults = results.stream()
                    .sorted(Comparator.comparingInt(Score::getPtsDiff)
                    .reversed())
                    .collect(Collectors.toList());

            return sortedResults;
        }

        private String rankingListToText(List<Score> scoreList) {
            String rankingText = "";
            if(scoreList.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    rankingText += "" + (i + 1) + ". " + scoreList.get(i).playerPts + " : " + scoreList.get(i).compPts + "\n\n";
                }
            } else {
                for (int i = 0; i < scoreList.size(); i++) {
                    rankingText += "" + (i + 1) + ". " + scoreList.get(i).playerPts + " : " + scoreList.get(i).compPts + "\n\n";
                }
            }
            return rankingText;
        }
}
