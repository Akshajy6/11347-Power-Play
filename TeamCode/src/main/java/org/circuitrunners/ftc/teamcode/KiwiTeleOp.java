package org.circuitrunners.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class KiwiTeleOp extends LinearOpMode {
    KiwiDrive drivebase;

    public void runOpMode() throws InterruptedException {
        DcMotor left = hardwareMap.dcMotor.get("left");
        DcMotor right = hardwareMap.dcMotor.get("right");
        DcMotor slide = hardwareMap.dcMotor.get("slide");

        drivebase = new KiwiDrive(left, right, slide);
        waitForStart();

        while (opModeIsActive())
        {
            drivebase.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
