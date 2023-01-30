package org.firstinspires.ftc.teamcode.Outreach.Kiwi;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class KiwiTeleOp extends LinearOpMode {
    KiwiDrive drivebase;
    Gamepad p = new Gamepad();
    Gamepad c = new Gamepad();

    public void runOpMode() throws InterruptedException {
        DcMotor left = hardwareMap.dcMotor.get("left");
        DcMotor right = hardwareMap.dcMotor.get("right");
        DcMotor slide = hardwareMap.dcMotor.get("slide");
        DcMotor l = hardwareMap.dcMotor.get("l");
        Servo f = hardwareMap.servo.get("f");

        drivebase = new KiwiDrive(left, right, slide);
        waitForStart();

        while (opModeIsActive())
        {
//            drivebase.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            drivebase.drive(gamepad1.left_stick_y, gamepad1.right_stick_y, (gamepad1.left_stick_x + gamepad1.right_stick_x) / 2 );

            try {p.copy(c); c.copy(gamepad1);} catch (RobotCoreException e) {e.printStackTrace();}

            l.setPower((gamepad1.right_trigger - gamepad1.left_trigger) / 2);
            if (!p.x && c.x) {
                f.setPosition(f.getPosition() + 0.1);
            }
        }
    }
}