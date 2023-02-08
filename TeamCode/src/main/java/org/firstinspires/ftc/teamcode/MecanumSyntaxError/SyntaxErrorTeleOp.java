package org.firstinspires.ftc.teamcode.MecanumSyntaxError;
import org.firstinspires.ftc.teamcode.Mecanum20D54D.Mecanum;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class SyntaxErrorTeleOp extends LinearOpMode {
    //Initializing drivetrain and four bar
    Mecanum drivetrain;
    SyntaxErrorMechanisms mechanisms;

    //Four bar positions
    final double HIGH = 650;
    final double MID = 450;
    final double LOW = 250;
    final double CONE = 30;

    @Override
    public void runOpMode() throws InterruptedException {
        //Initializing hardware
        Gamepad p1 = new Gamepad();
        Gamepad p2 = new Gamepad();
        Gamepad c1 = new Gamepad();
        Gamepad c2 = new Gamepad();

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
//        DcMotor l = hardwareMap.dcMotor.get("l");
//        DcMotor r = hardwareMap.dcMotor.get("r");
//        CRServo il = hardwareMap.crservo.get("il");
//        CRServo ir = hardwareMap.crservo.get("ir");

        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");

        drivetrain = new Mecanum(fl, fr, bl, br, imu);
//        mechanisms = new SyntaxErrorMechanisms(l, r);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            try {
                p1.copy(c1);
                p2.copy(c2);
                c1.copy(gamepad1);
                c2.copy(gamepad2);
            } catch (RobotCoreException e) {
                e.printStackTrace();
            }
            //Mecanum drivetrain code
            if (drivetrain.drive(c1.left_stick_y, -c1.left_stick_x, -c1.right_stick_x, p1.right_bumper, c1.right_bumper)) {
                gamepad1.rumble(250); //Angle recalibrated
            }
//            mechanisms.runManual(-c2.right_stick_y, c2.left_trigger - c2.right_trigger);
//
//
//            if (!p1.dpad_down && c1.dpad_down) {
//                mechanisms.runPID(CONE);
//            } else if (!p1.dpad_left && c1.dpad_left) {
//                mechanisms.runPID(LOW);
//            } else if (!p1.dpad_right && c1.dpad_right) {
//                mechanisms.runPID(MID);
//            } else if (!p1.dpad_up && c1.dpad_up) {
//                mechanisms.runPID(HIGH);
//            }
        }
    }
}