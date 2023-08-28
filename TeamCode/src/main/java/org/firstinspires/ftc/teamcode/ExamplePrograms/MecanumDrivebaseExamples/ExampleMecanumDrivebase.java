package org.firstinspires.ftc.teamcode.ExamplePrograms.MecanumDrivebaseExamples;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/** Makes a callable class that can the be used in teleOp programs */
public class ExampleMecanumDrivebase {

/**    Defines the motors and imu which will be used within the class */
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private BNO055IMU imu;

/**    Create the parameters for the imu to allow for the heading to be calculated later on */


/**    Creates the offset for the robots heading and creates the current angle the robot's headings at */
    private double offset = 0;
    private double currentAngle = 0;

/**    Creates a public //class or variable?// containing the motors being used and their new definitions */
    public ExampleMecanumDrivebase(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4, BNO055IMU imu1) {
        frontLeft = m1;
        frontRight = m2;
        backLeft = m3;
        backRight = m4;
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = imu1;
        //Initializes the imu allowing for the calculation of the heading
    }

    /**    Resets the angle of the imu creating a new robot heading to be used in the math */
    public void reset() {
        offset = imu.getAngularOrientation().firstAngle;
    }

/**    The math allowing for the heading to be calculated and then the motors powered to allow for field-centric movement
    More info can be found at https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html */
    public void driveMath(double y, double x, double rx) {
        currentAngle  = -imu.getAngularOrientation().firstAngle;

        currentAngle = AngleUnit.normalizeRadians(currentAngle - offset);

        double rotX = x * Math.cos(currentAngle) - y * Math.sin(currentAngle);
        double rotY = x * Math.sin(currentAngle) - y * Math.cos(currentAngle);

        double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / d;
        double frontRightPower = (rotY - rotX + rx) / d;
        double backLeftPower = (rotY - rotX - rx) / d;
        double backRightPower = (rotY + rotX - rx) / d;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }
}
