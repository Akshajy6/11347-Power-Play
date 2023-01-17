package org.firstinspires.ftc.teamcode.SyntaxError;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.SyntaxError.SyntaxErrorDB;
import org.firstinspires.ftc.teamcode.SyntaxError.VFourBar;

@TeleOp
public class SyntaxErrorTeleOp extends LinearOpMode {
    //Initializing drivetrain and four bar
    SyntaxErrorDB SyntaxError_drivetrain;
    VFourBar Vfb;

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
        CRServo sL = hardwareMap.crservo.get("sL");
        CRServo sR = hardwareMap.crservo.get("sR");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        SyntaxError_drivetrain = new SyntaxErrorDB(fl, fr, bl, br, imu);
        Vfb = new VFourBar(l, r, i, sL, sR);

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
            if (SyntaxError_drivetrain.drive(c1.left_stick_y, -c1.left_stick_x, -c1.right_stick_x, p1.right_bumper, c1.right_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated, add *1.1 for counter straifing if needed, test first
            }

            Vfb.VrunManual(-c2.right_stick_y, c2.left_stick_y, c2.left_trigger - c2.right_trigger);
        }
    }
}


