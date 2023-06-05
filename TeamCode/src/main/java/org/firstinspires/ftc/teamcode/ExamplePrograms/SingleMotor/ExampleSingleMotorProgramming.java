package org.firstinspires.ftc.teamcode.ExamplePrograms.SingleMotor;

//Imports DcMotor

import com.qualcomm.robotcore.hardware.DcMotor;

/** Makes a callable class that can then be used in the TeleOp program(s) */
public class ExampleSingleMotorProgramming {

    /** Defines ExampleMotor as a DcMotor and makes it a public variable
     that can be edited later on in the TeleOp program(s) */
    public DcMotor ExampleMotor;


    //Creates a public /*class or variable?*/ containing the motor and defines em1 as the same as the ExampleMotor as not to reassign ExampleMotor
    public ExampleSingleMotorProgramming(DcMotor em1) {
        //Sets ExampleMotor equal to em1
        ExampleMotor = em1;
/**     Makes ExampleMotors to reset its encoder and sets the motor to run without its encoder */
        ExampleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExampleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Creates the callable /*class or variable?*/ defining power to be changed based on the amount the trigger is pressed in
    public void runMotor(double power, double power2) {
/**     Sets the power ExampleMotor is moving at based on the trigger during TeleOp */
        ExampleMotor.setPower(power * 0.25);
    }
}
