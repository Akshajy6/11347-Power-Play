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

    public void drive(double leftPower, double rightPower, double slidePower)
    {
        //Moving forwards and backwards
        if (Math.abs(slidePower - 0) < 0.1)
        {
            motors[0].setPower(leftPower);
            motors[1].setPower(-rightPower);
            motors[2].setPower(0);
        }
        else //Moving side to side
        {
            //TODO:
        }
    }
}
