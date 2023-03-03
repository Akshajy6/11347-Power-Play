package org.firstinspires.ftc.teamcode.Outreach.Mecanum;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.Mechanisms;

@TeleOp
public class RobotCentricTeleOp extends LinearOpMode {
    //Initializing drivetrain and four bar
    RobotCentric drivetrain;
    Mechanisms fb;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
//        DcMotor l = hardwareMap.dcMotor.get("l");
//        DcMotor r = hardwareMap.dcMotor.get("r");
//        CRServo iL = hardwareMap.crservo.get("iL");
//        CRServo iR = hardwareMap.crservo.get("iR");

        drivetrain = new RobotCentric(fl, fr, bl, br);
//        fb = new Mechanisms(l, r, iL, iR);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drivetrain.drive(gamepad1.left_stick_y, -gamepad1.left_stick_x * 1.1, -gamepad1.right_stick_x);
        }
    }
}