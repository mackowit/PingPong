package com.kodilla;

public class Pad {
    double padHeight;
    int padWidth;
    int padXPos;
    double padYPos;

    public Pad(double padHeight, int padWidth, int padXPos, int height) {
        this.padHeight = padHeight;
        this.padWidth = padWidth;
        this.padXPos = padXPos;
        this.padYPos = height/2 - padHeight/2;
    }

    public double AIForCompPad(double ballYPosition, int height) {
         if(ballYPosition < height - padHeight) {
             if (padYPos - ballYPosition > 200) padYPos -= 2;
             else if (padYPos - ballYPosition > 0) padYPos -= 4;
             else if (padYPos - ballYPosition < -200) padYPos += 2;
             else if (padYPos - ballYPosition < 0) padYPos += 4;
            }
         return padYPos;
    }
}
