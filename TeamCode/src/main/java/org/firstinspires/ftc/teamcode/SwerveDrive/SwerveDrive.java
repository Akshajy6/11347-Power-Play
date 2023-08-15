package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.DcMotor;

public class SwerveDrive {

    //Declare instance variables for each motor
    private DcMotor leftTop;
    private DcMotor leftBottom;
    private DcMotor rightTop;
    private DcMotor rightBottom;

    //Constructor to assign motors to instance variables
    public SwerveDrive(DcMotor lt, DcMotor lb, DcMotor rt, DcMotor rb) {
        leftTop = lt;
        leftBottom = lb;
        rightTop = rt;
        rightBottom = rb;
    }

    //Drive function
    public void drive(double y, double x, double rot) {
        //TODO: Swerve calculations here
        leftTop.setPower(y);
        leftBottom.setPower(-y);
        rightTop.setPower(-y);
        rightBottom.setPower(y);

        /*

        Forward/Backwards:
            Left: Set power p to top and -p to bottom
            Right: Set power p to top and -p to bottom
        Rotation:
            Left: Set power p to top and -kp to bottom (k < 1)
            Right: Set power p to top and -kp to bottom (k < 1)
         */
    }
}
