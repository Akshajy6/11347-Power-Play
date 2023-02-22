package org.firstinspires.ftc.teamcode.MecanumSyntaxError;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.Mecanum;

@TeleOp
public class CommandTeleOp extends CommandOpMode {

    //Initializing drivetrain and four bar
    SyntaxErrorMechanisms mechanisms;
    Mecanum drivetrain;

    //Four bar positions
    final double HIGH = 500;
    final double MID = 450;
    final double LOW = 250;
    final double CONE = 30;

    @Override
    public void initialize() {
        //Initializing hardware
        GamepadEx driver = new GamepadEx(gamepad1);
        GamepadEx mechanism = new GamepadEx(gamepad2);

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

        schedule(new BulkCacheCommand(hardwareMap));
        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new FourBarPID(mechanisms, HIGH));
        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new FourBarPID(mechanisms, MID));
        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new FourBarPID(mechanisms, LOW));
        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new FourBarPID(mechanisms, CONE));
        Boolean pressed = driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER);

        telemetry.addLine("Initialization Done");
        telemetry.update();

        while (opModeIsActive()) {
            if (drivetrain.drive(-driver.getLeftY(), -driver.getLeftX() * 1.1, -driver.getRightX(), pressed)) {
                gamepad1.rumble(250); //Angle recalibrated
            }
        }
    }
}
