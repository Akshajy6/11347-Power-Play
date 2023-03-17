package org.firstinspires.ftc.teamcode.ExamplePrograms.CommandBasedPrograms;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Auto.BulkCacheCommand;
import org.firstinspires.ftc.teamcode.ExamplePrograms.MecanumDrivebaseExamples.ExampleMecanumDrivebase;

/** Initializes TeleOp and makes the class detected as a TeleOp class */
@TeleOp
/** After initializing the TeleOp, the class extends CommandOpMode in order to run as a TeleOp */
public class ExampleMecanumCommandTeleOp extends CommandOpMode {

/**    Renames the public variable ExampleMecanumDrivebase to emd to make it easier to call later on */
    ExampleMecanumDrivebase emd;

    @Override
    public void initialize() {
        GamepadEx driver = new GamepadEx(gamepad1);

/**        Defines they hardware type and calls it from the hardwareMap */
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");

/**        Enables the control hubs imu to allow for mecanum math to calculate heading */
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

/**        Allows emd to call for the motors contained in ExampleMecanumDrivebase allowing later for driver control */
        emd = new ExampleMecanumDrivebase(frontLeft, frontRight, backLeft, backRight, imu);

/**        Only needed when running PID commands in the teleOp, used during the 2022-23
        game PowerPlay to allow the fourbar manipulator to go to preset hights quickly */
        schedule(new BulkCacheCommand(hardwareMap));
    }

    @Override
    public void run() {
        super.run();
/**        Using the mecanum math in ExampleMecanumDrivebase, the robots movement is calculated based on inputs from the joysticks */
        emd.driveMath(gamepad1.left_stick_y, gamepad1.left_stick_x * 1.1, gamepad1.right_stick_x);

/**        Resets the heading angle when both bumpers are pressed so if the robots heading is off it can be fixed */
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            emd.reset();
            //Activates controller rumble to tell the driver the heading is reset
            gamepad1.rumble(250);
        }
    }
}
