package org.firstinspires.ftc.teamcode.Outreach.Mecanum;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class

FieldCentricMecanum {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private BNO055IMU imu;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    public FieldCentricMecanum(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4, BNO055IMU imu1) {
        fl = m1;
        fr = m2;
        bl = m3;
        br = m4;
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = imu1;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public boolean drive(double y, double x, double rx, boolean p, boolean c) {
        double angle = -imu.getAngularOrientation().firstAngle;
        boolean calibrated = false;

        //Recalibrate imu angle
        if (!p && c) {
            imu.initialize(parameters);
            calibrated = true;
        }

        //Robot-Centric Mecanum
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double flpwr = (y + x + rx) / denominator;
        double blpwr = (y - x + rx) / denominator;
        double frpwr = (y - x - rx) / denominator;
        double brpwr = (y + x - rx) / denominator;

        fl.setPower(flpwr);
        bl.setPower(blpwr);
        fr.setPower(frpwr);
        br.setPower(brpwr);
        return calibrated;
    }
}