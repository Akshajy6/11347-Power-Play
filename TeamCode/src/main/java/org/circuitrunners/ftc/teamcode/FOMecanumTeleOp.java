package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class FOMecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor br = hardwareMap.dcMotor.get("br");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            double angle = -imu.getAngularOrientation().firstAngle;

            double rotX = x * Math.cos(angle) - y * Math.sin(angle);
            double rotY = x * Math.sin(angle) + y * Math.cos(angle);

            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double flPwr = (rotY + rotX + rx) / d;
            double frPwr = (rotY - rotX - rx) / d;
            double blPwr = (rotY - rotX + rx) / d;
            double brPwr = (rotY + rotX - rx) / d;

            fl.setPower(flPwr);
            fr.setPower(frPwr);
            bl.setPower(blPwr);
            br.setPower(brPwr);
        }
    }
}