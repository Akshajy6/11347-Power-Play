package org.firstinspires.ftc.teamcode.ExamplePrograms.SingleServo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ExampleSingleServoTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();

        Servo ExampleServo = hardwareMap.servo.get("Example Servo");

        Boolean pressed = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.a) {
                ExampleServo.setPosition(180);
            }

            if (gamepad1.b) {
                ExampleServo.setPosition(-180);
            }

            telemetry.addLine("Example Servo Program Running");
            telemetry.update();
        }
    }
}
