package org.firstinspires.ftc.teamcode.SyntaxError;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class VFourBar {
    final private DcMotor l;
    final private DcMotor r;
    final private DcMotor i;
    final private CRServo sL;
    final private CRServo sR;

    public VFourBar(DcMotor lm, DcMotor rm, DcMotor im, CRServo LeftServo, CRServo RightServo) {
        l = lm;
        r = rm;
        i = im;
        sL = LeftServo;
        sR = RightServo;
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void VrunManual(double fbp, double rp, double ip) {
        l.setPower(-fbp);
        r.setPower(fbp);
        i.setPower(rp);
        sL.setPower(ip);
        sR.setPower(ip);
    }
}
