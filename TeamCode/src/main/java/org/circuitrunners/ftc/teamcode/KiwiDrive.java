package org.circuitrunners.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class KiwiDrive {
    private DcMotor[] motors = new DcMotor[3];

    public KiwiDrive(DcMotor left, DcMotor right, DcMotor slide)
    {
        motors[0] = left;
        motors[1] = right;
        motors[2] = slide;
    }
//
//    public void drive(double yPower, double xPower, double rotPower)
//    {
//        if (Math.abs(yPower) > 0)
//        {
//            motors[0].setPower(yPower);
//            motors[1].setPower(-yPower);
//            motors[2].setPower(0);
//        }
//
//        if (Math.abs(xPower) > 0)
//        {
//            motors[2].setPower(-xPower);
//            motors[0].setPower(xPower / 2);
//            motors[1].setPower(xPower / 2);
//        }
//
//        if (Math.abs(rotPower) > 0)
//        {
//            motors[0].setPower(rotPower);
//            motors[1].setPower(rotPower);
//            motors[2].setPower(rotPower);
//        }

    public void drive(double leftPower, double rightPower, double slidePower) {

        if (Math.abs(slidePower - 0) < 0.05)
        {
            if (leftPower > 0 && rightPower < 0 || leftPower < 0 && rightPower > 0) //Rotating
            {
                motors[0].setPower(leftPower);
                motors[1].setPower(-rightPower);
                motors[2].setPower((leftPower - rightPower) / 2);
            }
            else //Moving forwards and backwards
            {
                motors[0].setPower(leftPower);
                motors[1].setPower(-rightPower);
                motors[2].setPower(0);
            }
        }
        //Moving side to side
        else if (Math.abs(leftPower - 0) < 0.05 && Math.abs(rightPower - 0) < 0.05)
        {
            motors[2].setPower(-slidePower);
            motors[0].setPower(slidePower / 2);
            motors[1].setPower(slidePower / 2);
        }
    }
}
