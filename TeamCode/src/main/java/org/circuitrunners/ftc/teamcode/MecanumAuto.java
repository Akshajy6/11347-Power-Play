package org.circuitrunners.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class MecanumAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ColorSensor test = hardwareMap.colorSensor.get("sensor");
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor br = hardwareMap.dcMotor.get("br");


        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive())
        {
            int value = test.argb();
            if (value >= 0)
            {
                fl.setPower(0.5);
                bl.setPower(0.5);
                fr.setPower(0.5);
                br.setPower(0.5);
            }
        }
    }
}
