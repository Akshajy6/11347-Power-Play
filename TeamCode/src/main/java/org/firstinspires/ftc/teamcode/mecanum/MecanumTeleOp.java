package org.firstinspires.ftc.teamcode.mecanum;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    //Initializing drivetrain and four bar
    Mecanum drivetrain;
    FourBar fb;

    //Four bar positions
//    final double LEFTHIGH = ; //high pole left motor
//    final double RIGHTHIGH = ; //high pole right motor
//    final double LEFTMID = ; //medium pole left motor
//    final double RIGHTMID = ; //medium pole right motor
//    final double LEFTLOW = ; //low pole left motor
//    final double RIGHTLOW = ; //low pole right motor
//    final double LEFTGROUND = ; //ground pole left motor
//    final double RIGHTGROUND = ; //ground pole right motor
//    final double LEFTCONE = ; //ground pole left motor
//    final double RIGHTCONE = ; //ground pole right motor
    @Override
    public void runOpMode() throws InterruptedException {
        //Initializing hardware
        Gamepad p1 = new Gamepad();
        Gamepad p2 = new Gamepad();
        Gamepad c1 = new Gamepad();
        Gamepad c2 = new Gamepad();

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");
        DcMotor i = hardwareMap.dcMotor.get("i");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        drivetrain = new Mecanum(fl, fr, bl, br, imu);
        fb = new FourBar(l, r, i);

        waitForStart();

        if (isStopRequested()) return;

        boolean manual = true;

        while (opModeIsActive()) {
            try {
                p1.copy(c1);
                p2.copy(c2);
                c1.copy(gamepad1);
                c2.copy(gamepad2);
            } catch (RobotCoreException e) {
                e.printStackTrace();
            }

            //Mecanum drivetrain code
            if (drivetrain.drive(c1.left_stick_y, -c1.left_stick_x * 1.1, -c1.right_stick_x, p1.right_bumper, c1.right_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated
            }

            fb.runManual(-c2.right_stick_y, c2.left_trigger - c2.right_trigger);
                    //Mode selection
//            if (!p2.left_bumper && c2.left_bumper) {
//                manual = !manual;
//                if (manual) {
//                    gamepad2.rumble(250);
//                } else {
//                    gamepad2.rumble(250);
//                    sleep(500);
//                    gamepad2.rumble(250);
//                }
//            }

//
//            if (manual) {
//                //Manual mode
//                fb.runManual(-c2.right_stick_y, c2.left_trigger - c2.right_trigger);
//            } else { //IF THIS DOESNT WORK USE CONTROL VARIABLES FOR EACH PID MODE (condition || mode engaged)
//                //PID mode
//                if (!p2.right_bumper && c2.right_bumper) { //Intake height
//                    fb.runPID(10, 0, 0, LEFTCONE, RIGHTCONE); //TUNE AS NEEDED
//                } else if (!p2.dpad_up && c2.dpad_up) { //High pole
//                    fb.runPID(10, 0, 0, LEFTHIGH, RIGHTHIGH); //TUNE AS NEEDED
//                } else if (!p2.dpad_right && c2.dpad_right) { //Medium pole
//                    fb.runPID(10, 0, 0, LEFTMID, RIGHTMID); //TUNE AS NEEDED
//                } else if (!p2.dpad_left && c2.dpad_left) { //Low pole
//                    fb.runPID(10, 0, 0, LEFTLOW, RIGHTLOW); //TUNE AS NEEDED
//                } else if (!p2.dpad_down && c2.dpad_down) { //Ground junction
//                    fb.runPID(10, 0, 0, LEFTGROUND, RIGHTGROUND); //TUNE AS NEEDED
//                } else {
//                    fb.runManual(0, 0);
//                }
//            }
        }
    }
}


