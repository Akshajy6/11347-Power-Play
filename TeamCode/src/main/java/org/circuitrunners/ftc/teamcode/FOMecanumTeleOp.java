package org.circuitrunners.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class FOMecanumTeleOp extends LinearOpMode {
    //Initializing drivetrain and four bar
    Mecanum drivetrain;
    FourBar fb;

    @Override
    public void runOpMode() throws InterruptedException {
        //Initializing hardware
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
        //DcMotor l = hardwareMap.dcMotor.get("l");
        //DcMotor r = hardwareMap.dcMotor.get("r");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        drivetrain = new Mecanum(fl, fr, bl, br, imu);
        //fb = new FourBar(l, r);

        int mode = 1;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            try {
                p1.copy(c1);
                c1.copy(gamepad1);
            } catch (RobotCoreException e) {
                e.printStackTrace();
            }

            //Mecanum drivetrain code
            if (drivetrain.drive(c1.left_stick_y, c1.left_stick_x * 1.1, -c1.right_stick_x, p1.left_bumper, c1.left_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated
            }
        }
    }
}

/*            //TO BE REMOVED AFTER FINDING DR4B POSITIONS
            l.setPower(-gamepad1.right_stick_y);
            r.setPower(gamepad1.right_stick_y);
            telemetry.addLine("Left Pos: " + l.getCurrentPosition());
            telemetry.addLine("Right Pos: " + r.getCurrentPosition());

            //Mode selection
            if (mode == 1 && !p1.right_bumper && c1.right_bumper) {
                mode = 2; //Co-op manual
                gamepad1.rumble(250);
                sleep(250);
                gamepad1.rumble(250);
            }
            else if (mode == 2 && !p1.right_bumper && c1.right_bumper) {
                mode = 3; //Auto
                gamepad1.rumble(250);
                sleep(250);
                gamepad1.rumble(250);
                sleep(250);
                gamepad1.rumble(250);
            }
            else if (mode == 3 && !p1.right_bumper && c1.right_bumper) {
                mode = 1; //Solo manual
                gamepad1.rumble(250);
            }

            //Run modes
            if (mode == 1) { //Solo manual
                double state = l.getCurrentPosition();
                fb.runManual(state, gamepad1.right_stick_y);
            }
            else if (mode == 2) { //Co-op manual
                double state = l.getCurrentPosition();
                fb.runManual(state, gamepad2.right_stick_y);
            }
//            else if (mode == 3) { //Auto TODO
//
//            }
        }
    }
}
*/
