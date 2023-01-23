package org.firstinspires.ftc.teamcode.auto.apriltag;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class EncoderDebugger extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addLine("FLPos: " + fl.getCurrentPosition());
            telemetry.addLine("FRPos: " + fr.getCurrentPosition());
            telemetry.addLine("BLPos: " + bl.getCurrentPosition());
            telemetry.addLine("BRPos: " + br.getCurrentPosition());
        }
    }
}
