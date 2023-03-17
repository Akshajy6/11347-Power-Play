package org.firstinspires.ftc.teamcode.PowerPlay2022to2023.MecanumSyntaxError;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Auto.BulkCacheCommand;
import org.firstinspires.ftc.teamcode.PowerPlay2022to2023.Mecanum20D54D.Mecanum;

@TeleOp
public class CommandTeleOp extends CommandOpMode {

    //Initializing drivetrain and four bar
    SyntaxErrorMechanisms mechanisms;
    Mecanum drivetrain;

    //Four bar positions
    final int HIGH = 729;
    final int MID = 519;
    final int LOW = 298;
    final int CONE = 30;

    private boolean pidActive = false;

    @Override
    public void initialize() {
        //Initializing hardware
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

        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> {pidActive = true;}))
                .whenPressed(new FourBarPID(mechanisms, HIGH).withTimeout(1500))
                .whenReleased(new InstantCommand(() -> {pidActive = false;}));

        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new InstantCommand(() -> {pidActive = true;}))
                .whenPressed(new FourBarPID(mechanisms, MID).withTimeout(1500))
                .whenReleased(new InstantCommand(() -> {pidActive = false;}));

        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new InstantCommand(() -> {pidActive = true;}))
                .whenPressed(new FourBarPID(mechanisms, LOW).withTimeout(1500))
                .whenReleased(new InstantCommand(() -> {pidActive = false;}));

        mechanism.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(() -> {pidActive = true;}))
                .whenPressed(new FourBarPID(mechanisms, CONE).withTimeout(1500))
                .whenReleased(new InstantCommand(() -> {pidActive = false;}));

        mechanism.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> {mechanisms.reset();}));

        telemetry.addLine("Initialization Done");
        telemetry.update();
    }

    @Override
    public void run(){
       super.run();
        drivetrain.drive(gamepad1.left_stick_y, -gamepad1.left_stick_x * 1.1, -gamepad1.right_stick_x);

        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            drivetrain.reset();
            gamepad1.rumble(250); //Angle recalibrated
        }
        if (!pidActive)
        {
            mechanisms.runManual(-gamepad2.right_stick_y, gamepad2.left_trigger - gamepad2.right_trigger);
            telemetry.addLine("Lift Position: " + mechanisms.getPosition());
            telemetry.update();
        }
    }
}
