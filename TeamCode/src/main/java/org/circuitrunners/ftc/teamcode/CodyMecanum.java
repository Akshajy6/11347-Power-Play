package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class CodyMecanum extends LinearOpMode {
    public double cubeInput(double input, double factor) {
        double t = factor * Math.pow(input, 3);
        double r = input * (1 - factor);
        return t + r;
    }

    enum DR4BState {
        FOURBARUP,
        FOURBARDOWN,
        FOURBARABOVE,
        FOURBARCONE,
        FOURBARHIGH, // may be removed
        FOURBARMED,
        FOURBARLOW,
    };

    DR4BState FourBarState = DR4BState.FOURBARDOWN;
    int FOURBARUP = 999;    // 100% extension
    int FOURBARDOWN = 0;    // 0% extension
    int FOURBARABOVE = 111; // above cone height
    int FOURBARCONE = 99;   // cone height
    int FOURBARHIGH = 999;  // high
    int FOURBARMED = 666;   // med
    int FOURBARLOW = 333;   // low
    int position = 333;
    int height = 3;
    int mode = 0;

    // PID Init (to be tuned)
    PIDController PREPARE = new PIDController(0.05, 0.01, 0.01);
    PIDController INTAKE = new PIDController(0.05, 0.01, 0.01);
    PIDController EXTEND = new PIDController(0.05, 0.01, 0.01);
    PIDController OUTTAKE = new PIDController(0.05, 0.01, 0.01);
    PIDController REDUCE = new PIDController(0.05, 0.01, 0.01);
    PIDController POSITION = new PIDController(0.05, 0.01, 0.01);

    ElapsedTime timer = new ElapsedTime();

    public class PIDController {
        // initializing variables
        double Kp; double Ki; double Kd;
        double integralSum;
        double lastError;

        public PIDController(double Kp, double Ki, double Kd) {}
        public double update(int target, int state) {
            // calculate the error
            int error = target - state;

            // rate of change of the error
            double derivative = (error - lastError) / timer.seconds();

            // sum of all error over time
            integralSum = integralSum + (error * timer.seconds());

            // PID equation
            double out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

            // updating lastError, sending value, resetting timer
            lastError = error;
            timer.reset();
            return out;
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // Motor Declarations
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        DcMotor fourBarLeft = hardwareMap.get(DcMotorEx.class, "fourBarLeft");
        DcMotor fourBarRight = hardwareMap.get(DcMotorEx.class, "fourBarRight");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intakeMotor");

        // Motor Modes
        fourBarLeft.setMode(STOP_AND_RESET_ENCODER);
        fourBarRight.setMode(STOP_AND_RESET_ENCODER);
        fourBarLeft.setMode(RUN_WITHOUT_ENCODER);
        fourBarRight.setMode(RUN_WITHOUT_ENCODER);

        // Right Motor Reverse
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve IMU
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Gamepad State Storage
            try {previousGamepad1.copy(currentGamepad1); previousGamepad2.copy(currentGamepad2); currentGamepad1.copy(gamepad1); currentGamepad2.copy(gamepad2);}
            catch (RobotCoreException e) {}

            // Drivebase Code
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // Read inverse IMU heading, as the IMU heading is CW positive
            double botHeading = -imu.getAngularOrientation().firstAngle + (Math.PI/2);
            if (currentGamepad1.square && !previousGamepad1.square) {
                botHeading = 0;
                gamepad1.rumble(250);
            }

            double rotX = x * Math.cos(botHeading) + y * Math.sin(botHeading);
            double rotY = x * -Math.sin(botHeading) + y * Math.cos(botHeading);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            // Drivetrain Curve
            motorFrontLeft.setPower(cubeInput(frontLeftPower, 0.5));
            motorBackLeft.setPower(cubeInput(backLeftPower, 0.5));
            motorFrontRight.setPower(cubeInput(frontRightPower, 0.5));
            motorBackRight.setPower(cubeInput(frontLeftPower, 0.5));
            intakeMotor.setPower(gamepad2.right_trigger);
            intakeMotor.setPower(-gamepad2.left_trigger);

            // DR4B Mode Selection
            if (currentGamepad1.circle && !previousGamepad1.circle && mode == 0) {
                mode = 1;
                gamepad1.rumble(125);
                sleep(125);
                gamepad1.rumble(125);
            } else if (currentGamepad1.circle && !previousGamepad1.circle && mode == 1) {
                mode = 2;
                gamepad1.rumble(125);
                sleep(125);
                gamepad1.rumble(125);
                sleep(125);
                gamepad1.rumble(125);
            } else if (currentGamepad1.circle && !previousGamepad1.circle && mode == 2) {
                mode = 0;
                gamepad1.rumble(125);
            }

            // Solo Manual DR4B Code
            if (mode == 0) {
                position += 3 * gamepad1.right_stick_y; // int is constant, allows adjustment of position change speed
                int state = fourBarLeft.getCurrentPosition();
                double command = POSITION.update(position, state);

                fourBarLeft.setPower(-command);
                fourBarRight.setPower(command);

                // REMOVE THIS WHEN PID IS FIXED
                fourBarLeft.setPower(-gamepad1.right_trigger);
                fourBarRight.setPower(gamepad1.right_trigger);
            }

            // Co-op Manual DR4B
            if (mode == 1) {
                position += 3 * gamepad2.right_stick_y; // int is constant, allows adjustment of position change speed
                int state = (Math.abs(fourBarLeft.getCurrentPosition()) + Math.abs(fourBarRight.getCurrentPosition()) / 2);
                double command = POSITION.update(position, state);

                fourBarLeft.setPower(-command);
                fourBarRight.setPower(command);
            }

            // Automatic DR4B Code
            if (mode == 2) {
                switch (FourBarState) {
                    case FOURBARDOWN:
                        // wait for input
                        if (gamepad2.cross) {
                            // while x is pressed, extend
                            // obtain the encoder position, send to PID
                            int state = (Math.abs(fourBarLeft.getCurrentPosition()) + Math.abs(fourBarRight.getCurrentPosition()) / 2);
                            double command = PREPARE.update(FOURBARABOVE, state);

                            // send PID values
                            fourBarLeft.setPower(-command);
                            fourBarRight.setPower(command);
                            if (Math.abs(fourBarLeft.getCurrentPosition() - FOURBARABOVE) < 10 && Math.abs(fourBarRight.getCurrentPosition() - FOURBARABOVE) < 10) {
                                FourBarState = DR4BState.FOURBARABOVE;
                                break;
                            }
                        } else {
                            fourBarLeft.setPower(0);
                            fourBarRight.setPower(0);
                        }
                    case FOURBARABOVE:
                        if (gamepad2.right_bumper) {
                            // while right_bumper is pressed, lower slightly
                            // obtain the encoder position, send to PID
                            int state = (Math.abs(fourBarLeft.getCurrentPosition()) + Math.abs(fourBarRight.getCurrentPosition()) / 2);
                            double command = INTAKE.update(FOURBARCONE, state);

                            // send PID values
                            fourBarLeft.setPower(-command);
                            fourBarRight.setPower(command);
                            if (Math.abs(fourBarLeft.getCurrentPosition() - FOURBARCONE) < 10 && Math.abs(fourBarRight.getCurrentPosition() - FOURBARCONE) < 10) {
                                FourBarState = DR4BState.FOURBARCONE;
                                break;
                            }
                        }
                    case FOURBARCONE:
                        if (!gamepad2.right_bumper) {
                            // while right_bumper is NOT pressed, extend to max
                            // obtain the encoder position, send to PID
                            int state = (Math.abs(fourBarLeft.getCurrentPosition()) + Math.abs(fourBarRight.getCurrentPosition()) / 2);
                            double command = EXTEND.update(FOURBARUP, state);

                            // send PID values
                            fourBarLeft.setPower(-command);
                            fourBarRight.setPower(command);
                            if (Math.abs(fourBarLeft.getCurrentPosition() - FOURBARUP) < 10 && Math.abs(fourBarRight.getCurrentPosition() - FOURBARUP) < 10) {
                                FourBarState = DR4BState.FOURBARUP;
                                break;
                            }

                            else {
                                // otherwise, decrease height of center of mass
                                // lowers DR4B to not waste power
                                fourBarLeft.setPower(0);
                                fourBarRight.setPower(0);
                            }
                        }
                    case FOURBARUP:
                        // height picking -- default is high
                        if (currentGamepad1.cross && !previousGamepad1.cross && height == 3) {
                            height = 2;
                            gamepad2.rumble(125);
                            gamepad2.rumble(125);
                        } else if (currentGamepad1.cross && !previousGamepad1.cross && height == 2) {
                            height = 1;
                            gamepad2.rumble(125);
                        } else if (currentGamepad1.cross && !previousGamepad1.cross && height == 1) {
                            height = 3;
                            gamepad2.rumble(125);
                            gamepad2.rumble(125);
                            gamepad2.rumble(125);
                        }
                        // lower intake to height
                        if (gamepad2.right_bumper && height == 3) {
                            // while right_bumper is pressed, lower to high
                            // obtain the encoder position, send to PID
                            int state = Math.abs((fourBarLeft.getCurrentPosition() + fourBarRight.getCurrentPosition()) / 2);
                            double command = OUTTAKE.update(FOURBARHIGH, state);

                            // send PID values
                            fourBarLeft.setPower(-command);
                            fourBarRight.setPower(command);
                            if (Math.abs(fourBarLeft.getCurrentPosition() - FOURBARHIGH) < 10 && Math.abs(fourBarRight.getCurrentPosition() - FOURBARHIGH) < 10) {
                                FourBarState = DR4BState.FOURBARHIGH;
                                break;
                            }
                        } else if (gamepad2.right_bumper && height == 2) {
                            // while right_bumper is pressed, lower to med
                            // obtain the encoder position, send to PID
                            int state = Math.abs((fourBarLeft.getCurrentPosition() + fourBarRight.getCurrentPosition()) / 2);
                            double command = OUTTAKE.update(FOURBARMED, state);

                            // send PID values
                            fourBarLeft.setPower(-command);
                            fourBarRight.setPower(command);
                            if (Math.abs(fourBarLeft.getCurrentPosition() - FOURBARMED) < 10 && Math.abs(fourBarRight.getCurrentPosition() - FOURBARMED) < 10) {
                                FourBarState = DR4BState.FOURBARMED;
                                break;
                            }
                        } else if (gamepad2.right_bumper && height == 1) {
                            // while right_bumper is pressed, lower to low
                            // obtain the encoder position, send to PID
                            int state = Math.abs((fourBarLeft.getCurrentPosition() + fourBarRight.getCurrentPosition()) / 2);
                            double command = OUTTAKE.update(FOURBARLOW, state);

                            // send PID values
                            fourBarLeft.setPower(-command);
                            fourBarRight.setPower(command);
                            if (Math.abs(fourBarLeft.getCurrentPosition() - FOURBARLOW) < 10 && Math.abs(fourBarRight.getCurrentPosition() - FOURBARLOW) < 10) {
                                FourBarState = DR4BState.FOURBARLOW;
                                break;
                            }
                        }
                    case FOURBARHIGH:
                        if (gamepad2.left_trigger > 0) {

                        }
                        break;
                    case FOURBARMED:
                        break;
                    case FOURBARLOW:
                        break;
                    default:
                        // should never be reached, as liftState should never be null
                        FourBarState = DR4BState.FOURBARDOWN;
                }
            }
        }
    }
}