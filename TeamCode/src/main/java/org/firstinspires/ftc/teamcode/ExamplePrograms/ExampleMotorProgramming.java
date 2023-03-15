package org.firstinspires.ftc.teamcode.ExamplePrograms;

//Imports DcMotor
import com.qualcomm.robotcore.hardware.DcMotor;

//Makes a callable class that can then be redefined in the TeleOp program(s)
public class ExampleMotorProgramming {

    //Defines ExampleMotor as a DcMotor and makes it a public variable
    //that can be edited later on in the TeleOp program(s)
    public DcMotor ExampleMotor;

    //Creates a public variable containing the motor and defines em1 as the same as the ExampleMotor as not to reassign ExampleMotor
    public ExampleMotorProgramming(DcMotor em1) {
        //Sets ExampleMotor equal to em1
        ExampleMotor = em1;
        //Makes ExampleMotor to reset its encoder and sets the motor to run without its encoder
        ExampleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExampleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Creates the callable variable defining power to be changed based on the amount the trigger is pressed in TeleOp
    public void runMotor(double power) {
        //Sets the power ExampleMotor is moving at based on the trigger during TeleOp
        ExampleMotor.setPower(power);
    }
}
