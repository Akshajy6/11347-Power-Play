package org.firstinspires.ftc.teamcode.ExamplePrograms.CommandBasedPrograms;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.PowerPlay2022to2023.Mecanum20D54D.PID;

public class ExampleMechanism {

    private DcMotor ExampleMotor;

    //Creates a variable for PID
    PID pid = new PID(0.008, 0, 0);

/**    Creates a public //class or variable?// containing the moror and defines it as exm.
    The motors encoders are then reset and then set to run without using its encoders */
    public ExampleMechanism(DcMotor exm) {
        ExampleMotor = exm;
        ExampleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExampleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

/**    Creates a callable //class or variable?// that then sets the motors power based on input from teleOp */
    public void runManual(double power) {
        ExampleMotor.setPower(power * 0.75 - 0.11);
    }

/**    Using PID, sets the motors position to the requested position with a buffer of 0.11 */
    public void runPID(double target) {
        double command = pid.update(ExampleMotor.getCurrentPosition(), target) + 0.11;
        ExampleMotor.setPower(command);
    }

/**    Resets the motors encoders so that the position for PID is accurate */
    public void reset() {
        ExampleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ExampleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

/**    When called, gets the motors current position based on encoder */
    public double getPosition() {return ExampleMotor.getCurrentPosition();}

}
