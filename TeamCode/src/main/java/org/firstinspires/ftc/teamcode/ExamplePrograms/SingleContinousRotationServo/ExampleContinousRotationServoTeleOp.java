package org.firstinspires.ftc.teamcode.ExamplePrograms.SingleContinousRotationServo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class ExampleContinousRotationServoTeleOp extends LinearOpMode {

    ExampleContinousRotationServoProgramming ecrsp;

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad p1 = new Gamepad();
        Gamepad c1 = new Gamepad();

        CRServo ExampleCRServo = hardwareMap.crservo.get("Example CRServo");

        ecrsp = new ExampleContinousRotationServoProgramming(ExampleCRServo);

        boolean pressed = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            ecrsp.runCRServo(gamepad1.left_trigger - gamepad1.right_trigger);

            telemetry.addLine("Example CRServo Program Running");
            telemetry.update();
        }
    }
}
