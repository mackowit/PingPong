package com.kodilla.testing;

import com.kodilla.main.Ball;
import com.kodilla.main.Pad;
import org.junit.Assert;
import org.junit.Test;

public class PingPongTestSuite {
    int width = 720;
    int height = 480;

    @Test
    //ball's x position of ball is lesser than x position of pad and y position of ball is within y range of pad
    public void testPadPlayerCollisionXMatchYMatch() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 34, 140);
        //When
        double ballXSpeedResult = testBall.padPlayerCollision(25, 100, 10, 100);
        //Then
        Assert.assertEquals(2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is lesser than x position of pad and y position of ball is outside of y range of pad
    public void testPadPlayerCollisionXMatchYNotMatch() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 34, 240);
        //When
        double ballXSpeedResult = testBall.padPlayerCollision(25, 100, 10, 100);
        //Then
        Assert.assertEquals(-2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is greater than x position of pad and y position of ball is outside of y range of pad
    public void testPadPlayerCollisionXNotMatchYNotMatch() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 36, 240);
        //When
        double ballXSpeedResult = testBall.padPlayerCollision(25, 100, 10, 100);
        //Then
        Assert.assertEquals(-2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is greater than x position of pad and y position of ball is within y range of pad
    public void testPadPlayerCollisionXNotMatchYMatch() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 36, 140);
        //When
        double ballXSpeedResult = testBall.padPlayerCollision(25, 100, 10, 100);
        //Then
        Assert.assertEquals(-2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is bigger than x position of pad and y position of ball is within of y range of pad
    public void testPadCompCollisionXMatchYMatch() {
        //Given
        int width = 720;
        Ball testBall = new Ball(2, 1, 5, 676, 140);
        //When
        double ballXSpeedResult = testBall.padCompCollision(width - 35, 100, 100);
        //Then
        Assert.assertEquals(-2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is bigger than x position of pad and y position of ball is outside of y range of pad
    public void testPadCompCollisionXMatchYNotMatch() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 676, 240);
        //When
        double ballXSpeedResult = testBall.padCompCollision(width - 35, 100, 100);
        //Then
        Assert.assertEquals(2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is lesser than x position of pad and y position of ball is within of y range of pad
    public void testPadCompCollisionXNotMatchYMatch() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 674, 140);
        //When
        double ballXSpeedResult = testBall.padCompCollision(width - 35, 100, 100);
        //Then
        Assert.assertEquals(2, ballXSpeedResult, 0);
    }

    @Test
    //ball's x position of ball is lesser than x position of pad and y position of ball is outside of y range of pad
    public void testPadCompCollisionXNotMatchYNotMatch() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 674, 240);
        //When
        double ballXSpeedResult = testBall.padCompCollision(width - 35, 100, 100);
        //Then
        Assert.assertEquals(2, ballXSpeedResult, 0);
    }

    @Test
    //ball's y position is outside of top edge of scene
    public void testSceneTopEdgeCollision() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 200, 0);
        //When
        double ballYSpeedResult = testBall.sceneTopBottomEdgesCollision(height);
        //Then
        Assert.assertEquals(-1, ballYSpeedResult, 0);
    }

    @Test
    //ball's y position is outside of bottom edge of scene
    public void testSceneBottomEdgeCollision() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 200, 480);
        //When
        double ballYSpeedResult = testBall.sceneTopBottomEdgesCollision(height);
        //Then
        Assert.assertEquals(-1, ballYSpeedResult, 0);
    }

    @Test
    //ball's y position is inside of top edge of scene
    public void testSceneTopEdgeNotCollision() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 200, 10);
        //When
        double ballYSpeedResult = testBall.sceneTopBottomEdgesCollision(height);
        //Then
        Assert.assertEquals(1, ballYSpeedResult, 0);
    }

    @Test
    //ball's y position is inside of bottom edge of scene
    public void testSceneBottomEdgeNotCollision() {
        //Given
        Ball testBall = new Ball(2, 1, 5, 200, 469);
        //When
        double ballYSpeedResult = testBall.sceneTopBottomEdgesCollision(height);
        //Then
        Assert.assertEquals(1, ballYSpeedResult, 0);
    }

    @Test
    //ball's x position is outside of left edge of scene
    public void testSceneLeftEdgeCollision() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, -1, 100);
        //When
        boolean gameStartedResult = testBall.sceneLeftRightEdgesCollision(width);
        //Then
        Assert.assertFalse(gameStartedResult);
    }

    @Test
    //ball's x position is outside of right edge of scene
    public void testSceneRightEdgeCollision() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 711, 100);
        //When
        boolean gameStartedResult = testBall.sceneLeftRightEdgesCollision(width);
        //Then
        Assert.assertFalse(gameStartedResult);
    }

    @Test
    //ball's x position is inside of left edge of scene
    public void testSceneLeftEdgeNotCollision() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 1, 100);
        //When
        boolean gameStartedResult = testBall.sceneLeftRightEdgesCollision(width);
        //Then
        Assert.assertTrue(gameStartedResult);
    }

    @Test
    //ball's x position is inside of right edge of scene
    public void testSceneRightEdgeNotCollision() {
        //Given
        Ball testBall = new Ball(-2, 1, 5, 709, 100);
        //When
        boolean gameStartedResult = testBall.sceneLeftRightEdgesCollision(width);
        //Then
        Assert.assertTrue(gameStartedResult);
    }

    @Test
    //test of AI for CompPad
    public void testAIForCompPad() {
        //Given
        Pad testPad1 = new Pad(100, 10, width - 35, height);
        Pad testPad2 = new Pad(100, 10, width - 35, height);
        Pad testPad3 = new Pad(100, 10, width - 35, height);
        Pad testPad4 = new Pad(100, 10, width - 35, height);
        Pad testPad5 = new Pad(100, 10, width - 35, height);
        //When
        double padYPosResultGreaterThan200 = testPad1.AIForCompPad(-11, height);
        double padYPosResultGreaterThan0 = testPad2.AIForCompPad(189, height);
        double padYPosResultLesserThanMinus200 = testPad3.AIForCompPad(391, 500);
        double padYPosResultLesserThan0 = testPad4.AIForCompPad(191, height);
        double padYPosResultBallYPosGreaterThan380 = testPad5.AIForCompPad(381, height);
        //Then
        Assert.assertEquals(188, padYPosResultGreaterThan200, 0);
        Assert.assertEquals(186, padYPosResultGreaterThan0, 0);
        Assert.assertEquals(192, padYPosResultLesserThanMinus200, 0);
        Assert.assertEquals(194, padYPosResultLesserThan0, 0);
        Assert.assertEquals(190, padYPosResultBallYPosGreaterThan380, 0);
    }
}
