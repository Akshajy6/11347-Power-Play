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

        //Don't change unless auto cannot be completed in time
        double p = 0.5;

        //Tune and add to the following (you can change direction, time, etc...)
        //All time units are in milliseconds
        //By the way our mecanum drivebase is backwards so if the goBilda diagram says all powers must be positive for robot to move forward, for us it is negative. (Reverse all directions)

        //Forward a little to middle of square
        fl.setPower(-p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(-p);
        sleep(140);

        //Left to high pole
        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(p);
        sleep(2740);

        //Turn right slightly to orient intake with pole
        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(p);
        br.setPower(-p);
        sleep(400);

       //Stop moving wheels
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        //Raise fourbar (might have to change power on this one as well as time for it to actually go up and counteract gravity) (try time first)
        l.setPower(0.8);
        r.setPower(0.8);
        sleep(2000);

        //Go forwards a little to position intake/outtake over pole
        fl.setPower(-p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(-p);
        sleep(350);

        //Stop moving wheels
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        //Run outtake to score cone
        i.setPower(p);
        sleep(500);

        //Move backwards so outtake is not over pole
        fl.setPower(p);
        fr.setPower(p);
        bl.setPower(p);
        br.setPower(p);
        sleep(300);

        //Stop extending fourbar (might have to be negative, idk try 0 first Fourbar needs lots of trial and error)
        l.setPower(0);
        r.setPower(0);

        //Turn left slightly to straighten robot
        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(p);
        br.setPower(-p);
        sleep(270);

        //Once you get everything before this tuned and working feel free to work on bringing robot back to original starting place
    }
}

