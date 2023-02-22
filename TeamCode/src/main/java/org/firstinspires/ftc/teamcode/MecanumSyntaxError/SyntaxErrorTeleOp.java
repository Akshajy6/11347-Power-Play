package org.firstinspires.ftc.teamcode.MecanumSyntaxError;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_UP;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.Mecanum;

@TeleOp
public class SyntaxErrorTeleOp extends LinearOpMode {


    //Initializing drivetrain and four bar
    SyntaxErrorMechanisms mechanisms;
    Mecanum drivetrain;

    //Four bar positions
    final double HIGH = 500;
    final double MID = 450;
    final double LOW = 250;
    final double CONE = 30;

    @Override
    public void runOpMode() throws InterruptedException {
        

        //Initializing hardware
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();
        GamepadEx manipulator = new GamepadEx(gamepad2);

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

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            try {
                p1.copy(c1);
                c1.copy(gamepad1);
            }

            catch (RobotCoreException e)
            {
                e.printStackTrace();
            }

            //Mecanum drivetrain code
            if (drivetrain.drive(c1.left_stick_y, -c1.left_stick_x * 1.1, -c1.right_stick_x, p1.right_bumper, c1.right_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated
            }

            mechanisms.runManual(-gamepad2.right_stick_y, gamepad2.left_trigger - gamepad2.right_trigger);

//            intakePosCommand = new IntakePositionCommand(fbpid, manipulator);
//
//            fbpid.setDefaultCommand(new PerpetualCommand(intakePosCommand));
        }
    }
}
