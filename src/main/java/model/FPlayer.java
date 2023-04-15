package model;

import javafx.scene.text.Text;

public class FPlayer implements RacketController {

    public static int SIZE_COSTP1 = 7;
    public static int SPEED_COSTP1 = 5;
    public static int POWER_COSTP1 = 3;

    public static int SIZE_COSTP2 = 7;
    public static int SPEED_COSTP2 = 5;
    public static int POWER_COSTP2 = 3;

    State state;
    private double size;
    private double position;
    private double speed;
    private int sizeLevel;
    private int speedLevel;
    public int powerAmount;
    private boolean isPower;
    private boolean isPowering;
    private int point;
    private final Text pointText;
    private final Text powerAmountText;

    public FPlayer() {
        state = State.IDLE;
        size = 80;
        speed = 130;
        position = 0;
        point = 0;
        sizeLevel = 1;
        speedLevel = 1;
        powerAmount = 0;
        isPower = false;
        isPowering = false;
        pointText = new Text(String.valueOf(point));
        powerAmountText = new Text(String.valueOf(powerAmount));

        reset();
    }

    @Override
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public double getSize() {
        return size;
    }
    public int getSizeLevel() {
        return sizeLevel;
    }
    public boolean increaseSizeLevelP1() {
        if (point < SIZE_COSTP1) {
            return false;
        }
        decreasePoint(SIZE_COSTP1);
        size = size + 15;
        SIZE_COSTP1 = SIZE_COSTP1 + 2;
        sizeLevel++;
        return true;

    }
    public boolean increaseSizeLevelP2() {
        if (point < SIZE_COSTP2) {
            return false;
        }
        decreasePoint(SIZE_COSTP2);
        size = size + 15;
        SIZE_COSTP2 = SIZE_COSTP2 + 2;
        sizeLevel++;
        return true;

    }


    public double getSpeed() {
        return speed;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public boolean increaseSpeedLevelP1() {
        if (point < SPEED_COSTP1) {
            return false;
        }

        decreasePoint(SPEED_COSTP1);
        speed = speed + 15;
        SPEED_COSTP1 = SPEED_COSTP1 + 2;
        speedLevel++;

        return true;
    }
    public boolean increaseSpeedLevelP2() {
        if (point < SPEED_COSTP2) {
            return false;
        }

        decreasePoint(SPEED_COSTP2);
        speed = speed + 15;
        SPEED_COSTP2 = SPEED_COSTP2 + 2;
        speedLevel++;

        return true;
    }

    public int getPowerAmount() {
        return powerAmount;
    }

    public boolean increasePowerAmountP1() {
        if (point < POWER_COSTP1) {
            return false;
        }

        decreasePoint(POWER_COSTP1);
        powerAmount = powerAmount + 1;
        POWER_COSTP1 = POWER_COSTP1 + 2;
        powerAmountText.setText(String.valueOf(powerAmount));
        return true;
    }
    public boolean increasePowerAmountP2() {
        if (point < POWER_COSTP2) {
            return false;
        }

        decreasePoint(POWER_COSTP2);
        powerAmount = powerAmount + 1;
        POWER_COSTP2 = POWER_COSTP2 + 2;
        powerAmountText.setText(String.valueOf(powerAmount));
        return true;
    }
    public boolean usePower() {
        if (powerAmount > 0 && !isPower) {
            isPower = true;
            powerAmount--;
            powerAmountText.setText(String.valueOf(powerAmount));
            return true;
        }

        return false;
    }

    public boolean isPower() {
        return isPower;
    }

    public void setPower(boolean power) {
        isPower = power;
    }

    public boolean isPowering() {
        return isPowering;
    }

    public void setPowering(boolean powering) {
        isPowering = powering;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void increasePoint() {
        increasePoint(1);
    }

    public void increasePoint(int n) {
        point = point + n;
        pointText.setText(String.valueOf(point));
    }

    public void decreasePoint() {
        decreasePoint(1);
    }

    public void decreasePoint(int n) {
        point = point - n;
        pointText.setText(String.valueOf(point));
    }

    public Text getPointText() {
        return pointText;
    }

    public Text getPowerAmountText() {
        return powerAmountText;
    }

    public void reset() {
        point = 0;
        size = 80;
        speed = 220;
        sizeLevel = 1;
        speedLevel = 1;
        powerAmount = 0;
        isPower = false;
        isPowering = false;
        SIZE_COSTP1 = 7;
        SPEED_COSTP1 = 5;
        POWER_COSTP1 = 3;
        SIZE_COSTP2 = 7;
        SPEED_COSTP2 = 5;
        POWER_COSTP2 = 3;
        pointText.setText(String.valueOf(point));
    }



    //ball collision using if statements

}
