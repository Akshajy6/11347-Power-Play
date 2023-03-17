package org.firstinspires.ftc.teamcode.PowerPlay2022to2023.MecanumSyntaxError;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.PowerPlay2022to2023.Mecanum20D54D.Mecanum;

@TeleOp
public class SyntaxErrorTeleOp extends LinearOpMode {

    //Initializing drivetrain and four bar
    SyntaxErrorMechanisms mechanisms;
    Mecanum drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {

        //Initializing hardware
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");
        CRServo il = hardwareMap.crservo.get("il");
        CRServo ir = hardwareMap.crservo.get("ir");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        drivetrain = new Mecanum(fl, fr, bl, br, imu);
        mechanisms = new SyntaxErrorMechanisms(l, r, il, ir);

        Boolean pressed = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //Mecanum drivetrain code
            drivetrain.drive(c1.left_stick_y, -c1.left_stick_x * 1.1, -c1.right_stick_x);
            mechanisms.runManual(-gamepad2.right_stick_y, gamepad2.left_trigger - gamepad2.right_trigger);
            telemetry.addLine("POS: " + mechanisms.getPosition());
            telemetry.update();
        }
    }
}
