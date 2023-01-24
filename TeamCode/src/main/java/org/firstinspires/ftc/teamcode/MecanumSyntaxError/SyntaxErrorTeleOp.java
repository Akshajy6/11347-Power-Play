package org.firstinspires.ftc.teamcode.MecanumSyntaxError;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.Mecanum;

@TeleOp
@Disabled
public class SyntaxErrorTeleOp extends LinearOpMode {
    //Initializing drivetrain and four bar
    Mecanum drivetrain;
    VirtualFourBar v4b;

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
        DcMotor fb = hardwareMap.dcMotor.get("fb");
        CRServo il = hardwareMap.crservo.get("il");
        CRServo ir = hardwareMap.crservo.get("ir");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        drivetrain = new Mecanum(fl, fr, bl, br, imu);
        v4b = new VirtualFourBar(l, r, fb, il, ir);

        waitForStart();

        if (isStopRequested()) return;

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
            if (drivetrain.drive(c1.left_stick_y, -c1.left_stick_x, -c1.right_stick_x, p1.right_bumper, c1.right_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated, add *1.1 for counter strafing if needed, test first
            }

            v4b.runManual(-c2.right_stick_y, c2.left_trigger - c2.right_trigger, p2.left_bumper, c2.left_bumper, p2.right_bumper, c2.right_bumper);
        }
    }
}


