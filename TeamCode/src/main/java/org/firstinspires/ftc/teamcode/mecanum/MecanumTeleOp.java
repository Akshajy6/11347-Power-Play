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
//        fb = new FourBar(l, r);

        int mode = 1;

        waitForStart();

        if (isStopRequested()) return;

        boolean upPressed = false;
        boolean downPressed = false;

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
            if (drivetrain.drive(c1.left_stick_y, -c1.left_stick_x * 1.1, -c1.right_stick_x, p1.left_bumper, c1.left_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated
            }

            if (!p2.y && c2.y) {
                upPressed = !upPressed;
            }

            if (upPressed && !downPressed) {
                l.setPower(-0.6);
                r.setPower(0.6);
            }

            if (!p2.a && c2.a) {
                downPressed = !downPressed;
            }

            if (downPressed && !upPressed) {
                l.setPower(0.1); //Tune as needed to counteract gravity
                r.setPower(-0.1);
            }

            i.setPower(c2.left_trigger - c2.right_trigger);//right for intake, left for outtake


//            //Mode selection
//            if (mode == 1 && !p1.right_bumper && c1.right_bumper) {
//                mode = 2; //Co-op manual
//                gamepad1.rumble(250);
//                sleep(250);
//                gamepad1.rumble(250);
//            }
//            else if (mode == 2 && !p1.right_bumper && c1.right_bumper) {
//                mode = 3; //Auto
//                gamepad1.rumble(250);
//                sleep(250);
//                gamepad1.rumble(250);
//                sleep(250);
//                gamepad1.rumble(250);
//            }
//            else if (mode == 3 && !p1.right_bumper && c1.right_bumper) {
//                mode = 1; //Solo manual
//                gamepad1.rumble(250);
//            }
//
//            //Run modes
//            if (mode == 1) { //Solo manual
//                double state = l.getCurrentPosition();
//                fb.runManual(state, gamepad1.right_stick_y);
//            }
//            else if (mode == 2) { //Co-op manual
//                double state = l.getCurrentPosition();
//                fb.runManual(state, gamepad2.right_stick_y);
//            }
//            else if (mode == 3) { //Auto TODO
//
//            }
        }
    }
}

