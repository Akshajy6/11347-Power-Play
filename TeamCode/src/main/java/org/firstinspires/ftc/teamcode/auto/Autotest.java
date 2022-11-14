package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.mecanum.Mecanum;

@Autonomous
public class Autotest extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");
        DcMotor i = hardwareMap.dcMotor.get("i");

        waitForStart();

        double p = 0.5;
        fl.setPower(-p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(-p);
        sleep(140);

        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(p);
        sleep(2700);

        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(p);
        br.setPower(-p);
        sleep(300);

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(150);

        l.setPower(0.8);
        r.setPower(0.8);
        sleep(4000);

        fl.setPower(-p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(-p);
        sleep(300);

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        i.setPower(p);
        sleep(200);

        fl.setPower(p);
        fr.setPower(p);
        bl.setPower(p);
        br.setPower(p);
        sleep(300);

        l.setPower(0);
        r.setPower(0);

        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(p);
        br.setPower(-p);
        sleep(300);
    }
}
