package org.firstinspires.ftc.teamcode.Mecanum20D54D;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

public class Mechanisms extends SubsystemBase {
    final private DcMotor l;
    final private DcMotor r;
    final private CRServo iL;
    final private CRServo iR;
    PID pid = new PID(0.008, 0, 0);

    public Mechanisms(DcMotor lm, DcMotor rm, CRServo iLm, CRServo iRm) {
        l = lm;
        r = rm;
        iL = iLm;
        iR = iRm;
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void runIntake(double power) {
        iL.setPower(power);
        iR.setPower(-power);
    }

    public void runManual(double fbp) {
        l.setPower(fbp);
        r.setPower(-fbp);
    }
    public void runManual(double fbp, double rp) {
        l.setPower(-fbp);
        r.setPower(fbp);
        iL.setPower(rp);
        iR.setPower(rp);
    }
    public void runPID(double target) {
        double command = pid.update(l.getCurrentPosition(), target) + 0.11;
        l.setPower(command);
        r.setPower(-command);
    }

    public double currentPosition(){
        return l.getCurrentPosition();
    }
}