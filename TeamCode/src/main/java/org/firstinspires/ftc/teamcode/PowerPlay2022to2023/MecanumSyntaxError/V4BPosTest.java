package org.firstinspires.ftc.teamcode.PowerPlay2022to2023.MecanumSyntaxError;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
@Disabled
public class V4BPosTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor v4b = hardwareMap.dcMotor.get("v4b");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addLine("Pos: " + v4b.getCurrentPosition());
            telemetry.update();
        }
    }
}
