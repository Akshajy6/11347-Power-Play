package org.circuitrunners.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class TankDrive {
    private final double MAX_POWER = 1.0;
    private final double DEADZONE = 0.1;

    private DcMotor[] motors = new DcMotor[4];

    public TankDrive(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        motors[0] = frontLeft;
        motors[1] = frontRight;
        motors[2] = backLeft;
        motors[3] = backRight;
    }

    public void drive(double leftPower, double rightPower) {
        if (Math.abs(leftPower) < DEADZONE) leftPower = 0;
        if (Math.abs(rightPower) < DEADZONE) rightPower = 0;

        leftPower *= MAX_POWER;
        rightPower *= MAX_POWER;


        for(int i = 0; i < motors.length; i++) {
            motors[i].setPower((i % 2 == 0) ? leftPower : rightPower);
        }
    }
}
