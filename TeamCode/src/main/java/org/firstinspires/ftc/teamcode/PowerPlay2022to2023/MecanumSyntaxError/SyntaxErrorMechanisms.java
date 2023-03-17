package org.firstinspires.ftc.teamcode.PowerPlay2022to2023.MecanumSyntaxError;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.PowerPlay2022to2023.Mecanum20D54D.PID;

public class SyntaxErrorMechanisms {
    final private DcMotor l;
    final private DcMotor r;
    final private CRServo il;
    final private CRServo ir;

    PID pid = new PID(0.008, 0, 0);

    public SyntaxErrorMechanisms(DcMotor lm, DcMotor rm, CRServo ils, CRServo ilr) {
        l = lm;
        r = rm;
        il = ils;
        ir = ilr;
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void runManual(double dr4bp, double ip) {
        l.setPower(-dr4bp * 0.75 - 0.11);
        r.setPower(dr4bp * 0.75 + 0.11);
        il.setPower(ip);
        ir.setPower(ip);
    }
    public void runIntake(double ip) {
        il.setPower(ip);
        ir.setPower(ip);
    }
    public void runPID(double target) {
        double command = pid.update(-l.getCurrentPosition(), target) + 0.11;
        l.setPower(-command);
        r.setPower(command);
    }
    public void reset() {
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public double getPosition() {
        return r.getCurrentPosition();
    }
}
