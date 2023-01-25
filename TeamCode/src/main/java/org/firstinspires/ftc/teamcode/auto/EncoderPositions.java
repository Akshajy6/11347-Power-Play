package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class EncoderPositions extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addLine("FLPos: " + fl.getCurrentPosition());
            telemetry.addLine("FRPos: " + fr.getCurrentPosition());
            telemetry.addLine("BLPos: " + bl.getCurrentPosition());
            telemetry.addLine("BRPos: " + br.getCurrentPosition());
            telemetry.addLine("LPos: " + l.getCurrentPosition());
            telemetry.addLine("RPos: " + r.getCurrentPosition());
            telemetry.update();
        }
    }
}
