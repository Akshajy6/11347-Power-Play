package org.firstinspires.ftc.teamcode.MecanumSyntaxError;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.PID;

public class SyntaxErrorMechanisms {
    final private DcMotor l;
    final private DcMotor r;
//    final private CRServo il;
//    final private CRServo ir;

    PID pid = new PID(0.008, 0, 0);

    public SyntaxErrorMechanisms(DcMotor lm, DcMotor rm) {
        l = lm;
        r = rm;
//        il = ils;
//        ir = irs;
    }
    public void runManual(double dr4bp, double ip) {
        l.setPower(-dr4bp);
        r.setPower(dr4bp);
//        il.setPower(ip);
//        ir.setPower(ip);
    }
    public void runPID(double target) {
        double command = pid.update(l.getCurrentPosition(), target) + 0.11;

        l.setPower(command);
        r.setPower(-command);
    }
}
