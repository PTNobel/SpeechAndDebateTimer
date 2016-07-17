/*
    Copyright 2016 Parth Nobel

    This file is part of Speech and Debate Timer.

    Speech and Debate Timer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Speech and Debate Timer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Speech and Debate Timer.  If not, see <http://www.gnu.org/licenses/>.

 */


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