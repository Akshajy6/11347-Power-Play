package org.firstinspires.ftc.teamcode.mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class VFourBar {
    final private DcMotor l;
    final private DcMotor r;
    final private DcMotor i;

    public VFourBar(DcMotor lm, DcMotor rm, DcMotor im) {
        l = lm;
        r = rm;
        i = im;
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void VrunManual(double fbp, double rp) {
        l.setPower(-fbp);
        r.setPower(fbp);
        i.setPower(rp);
    }
}
