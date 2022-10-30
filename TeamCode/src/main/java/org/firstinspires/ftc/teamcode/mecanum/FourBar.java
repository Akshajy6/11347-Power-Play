package org.firstinspires.ftc.teamcode.mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class FourBar {
    final private DcMotor l;
    final private DcMotor r;
    final private DcMotor i;

    public FourBar(DcMotor lm, DcMotor rm, DcMotor im) {
        l = lm;
        r = rm;
        i = im;
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void runManual(double fbp, double rp) {
        l.setPower(-fbp);
        r.setPower(fbp);
        i.setPower(rp);
    }

    public void runPID(double kp, double ki, double kd, double targetleft, double targetright) {
        PID pid = new PID(kp, ki, kd);
        double commandleft = pid.update(l.getCurrentPosition(), targetleft);
        double commandright = pid.update(r.getCurrentPosition(), targetright);

        l.setPower(-commandleft);
        r.setPower(commandright);
    }
}