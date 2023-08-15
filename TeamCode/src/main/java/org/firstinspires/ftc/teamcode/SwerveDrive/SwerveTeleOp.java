package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class SwerveTeleOp extends LinearOpMode {

    //Declare SwerveDrive object
    SwerveDrive drive;

    //Function that runs when "Init" button is pressed on Driver Station
    @Override
    public void runOpMode() {

        //Init code:
        //Initialize motors from hardware map
        DcMotor lt = hardwareMap.dcMotor.get("lt");
        DcMotor lb = hardwareMap.dcMotor.get("lb");
        DcMotor rt = hardwareMap.dcMotor.get("rt");
        DcMotor rb = hardwareMap.dcMotor.get("rb");

        //Initialize SwerveDrive object
        drive = new SwerveDrive(lt, lb, rt, rb);

        //Waits until start button is pressed
        waitForStart();

        while (opModeIsActive()) {
            //TeleOp code:
            //Calls drive function with joystick inputs
            drive.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
