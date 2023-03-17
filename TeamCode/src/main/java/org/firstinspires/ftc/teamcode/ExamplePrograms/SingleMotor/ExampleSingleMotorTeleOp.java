package org.firstinspires.ftc.teamcode.ExamplePrograms.SingleMotor;

//Imports what is needed to run the program from their respective areas

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/** Initializes TeleOp and makes the class detected as a TeleOp class */
@TeleOp

/** After initializing the TeleOp, the class has to extend LinearOpMode in order to run as a TeleOp */
public class ExampleSingleMotorTeleOp extends LinearOpMode {

/** Using the public variable ExampleMotorProgramming, ExampleMotroProgramming is renamed to emp in order to allow it to be called easier */
    ExampleSingleMotorProgramming emp;

    @Override
    public void runOpMode() throws InterruptedException {

/**     This creates a previous (p1) and current (c1) gamepad in order to detect
        if the controller input is being pressed vs no longer being pressed */
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();

/**     Defines ExampleMotor as a DcMotor and calls it from the hardwareMap
        When labled/defined in the hardwareMap, its called ExampleMotor which depending
        on how its named in the ""s it changes how it needs to be labled in the hardwareMap */
        DcMotor ExampleMotor = hardwareMap.dcMotor.get("ExampleMotor");

        //using the new name (emp) of ExampleMotorProgramming, it calls ExampleMotor from EMP */
        emp = new ExampleSingleMotorProgramming(ExampleMotor);

        Boolean pressed = false;

/**     Waits to start running the program until the start button is pressed */
        waitForStart();

/**     When the stop button is pressed, it exits/ends the running TeleOp */
        if (isStopRequested()) return;

/**     After the start button has been pressed, until the TeleOp is stopped, will run */
        while (opModeIsActive()) {
/**         When the left trigger is pressed on gamepad1, it gives power to the motor defined in EMP */
            emp.runMotor(gamepad1.left_trigger);

/**         Simply adds a line to be printed on the phone */
            telemetry.addLine("Example Motor Program Running");
/**         Updates the text on the screen so it will print */
            telemetry.update();
        }
    }
}
