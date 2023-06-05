package org.firstinspires.ftc.teamcode.SwerveDrive;

//Imports DcMotor

import com.qualcomm.robotcore.hardware.DcMotor;

/** Makes a callable class that can then be used in the TeleOp program(s) */
public class SwerveDriveProgramming {

    /** Defines ExampleMotor as a DcMotor and makes it a public variable
     that can be edited later on in the TeleOp program(s) */
    public DcMotor LeftFrontSwerveMotor;
    public DcMotor LeftBackSwerveMotor;
    public DcMotor RightFrontSwerveMotor;
    public DcMotor RightBackSwerveMotor;


    //Creates a public /*class or variable?*/ containing the motor and defines em1 as the same as the ExampleMotor as not to reassign ExampleMotor
    public SwerveDriveProgramming(DcMotor lf, DcMotor lb, DcMotor lr, DcMotor br) {
        //Sets ExampleMotor equal to em1
        LeftFrontSwerveMotor = lf;
        LeftBackSwerveMotor = lb;
        RightFrontSwerveMotor = lr;
        RightBackSwerveMotor = br;
/**     Makes ExampleMotors to reset its encoder and sets the motor to run without its encoder */
        LeftFrontSwerveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftFrontSwerveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftBackSwerveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBackSwerveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightFrontSwerveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFrontSwerveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightBackSwerveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBackSwerveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Creates the callable /*class or variable?*/ defining power to be changed based on the amount the trigger is pressed in
    public void runMotor(double power, double power2) {
/**     Sets the power ExampleMotor is moving at based on the trigger during TeleOp */
        LeftFrontSwerveMotor.setPower(power);
        LeftBackSwerveMotor.setPower(power2);
        RightFrontSwerveMotor.setPower(power);
        RightBackSwerveMotor.setPower(power2);
    }
}
