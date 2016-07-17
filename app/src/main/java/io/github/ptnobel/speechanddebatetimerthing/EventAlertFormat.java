package io.github.ptnobel.speechanddebatetimerthing;

/**
 * Created by parth on 7/16/16.
 */
public class EventAlertFormat {
    int speechLen = 180;
    int lenCountDown = 10;
    int[] warningPoints = {60, 120, 150};
    String name = "Congress";

    public String getName() {
        return this.name;
    }

    public int getSpeechLen() {
        return this.speechLen;
    }

    public int getLenCountDown() {
        return this.lenCountDown;
    }

    public int[] getWarningPoints() {
        return this.warningPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeechLen(int speechLen) {
        this.speechLen = speechLen;
    }

    public void setLenCountDown(int lenCountDown) {
        this.lenCountDown = lenCountDown;
    }

    public void setWarningPoints(int[] warningPoints) {
        this.warningPoints = warningPoints;
    }
}