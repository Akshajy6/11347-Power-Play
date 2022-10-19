package org.circuitrunners.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
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
        //KiwiLauncher = new KiwiLauncher(launcher);
        waitForStart();

        while (opModeIsActive())
        {
            drivebase.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            try {p.copy(c); c.copy(gamepad1);} catch (RobotCoreException e) {e.printStackTrace();}

            if (!p.a && c.a) {
                //launch
                //set certain velocity to l (sleep for some amount of ms to run motor for that long)
                //set feed servo to certain position
                f.setPosition(f.getPosition()+0.5);
                l.setPower(0.5);
                sleep(3000);
            }
        }
    }
}