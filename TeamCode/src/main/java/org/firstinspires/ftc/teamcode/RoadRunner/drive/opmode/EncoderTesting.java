package org.firstinspires.ftc.teamcode.RoadRunner.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
@Disabled
public class EncoderTesting extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");

        waitForStart();

        while(opModeIsActive())
        {
            telemetry.addData("frontLeft", fl.getCurrentPosition());
            telemetry.addData("frontRight", fr.getCurrentPosition());
            telemetry.addData("backLeft", bl.getCurrentPosition());
            telemetry.addData("backRight", br.getCurrentPosition());
            telemetry.update();
        }
    }
}
