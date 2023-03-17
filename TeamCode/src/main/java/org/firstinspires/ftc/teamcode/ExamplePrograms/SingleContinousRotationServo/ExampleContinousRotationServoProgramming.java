/**
 * Basically programmed the same as a motor
 */
package org.firstinspires.ftc.teamcode.ExamplePrograms.SingleContinousRotationServo;

import com.qualcomm.robotcore.hardware.CRServo;

public class ExampleContinousRotationServoProgramming {

    public CRServo ExampleCRServo;

/**    Creates a public variable containing the CRServo and defines es1 as the same as the ExampleCRServo as not to reassign ExampleCRServo */
    public ExampleContinousRotationServoProgramming(CRServo es1) {
/**        Sets ExampleCRServo equal to es1 */
        ExampleCRServo = es1;
    }

/**    Creates the callable variable defining power to be changed based on the amount the trigger is pressed in TeleOp */
    public void runCRServo(double power) {
/**        Sets the power ExampleMotor is moving at based on the trigger during TeleOp */
        ExampleCRServo.setPower(power);
    }
}
