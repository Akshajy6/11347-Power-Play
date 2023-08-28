package org.firstinspires.ftc.teamcode.ExamplePrograms.MecanumDrivebaseExamples;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class ExampleMecanumManualTeleOp extends LinearOpMode {

    ExampleMecanumDrivebase emd;

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        emd = new ExampleMecanumDrivebase(frontLeft, frontRight, backLeft, backRight, imu);

        boolean pressed = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            try {
                p1.copy(c1);
                c1.copy(gamepad1);
            }
            catch (RobotCoreException e) {
                e.printStackTrace();
            }

            emd.driveMath(c1.left_stick_y, -c1.left_stick_x * 1.1, -c1.right_stick_x);
        }
    }
}
