package org.firstinspires.ftc.teamcode.MecanumSyntaxError;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class VirtualFourBar {
    final private DcMotor l;
    final private DcMotor r;
    final private DcMotor v4b;
    final private CRServo il;
    final private CRServo ir;

    public VirtualFourBar(DcMotor lm, DcMotor rm, DcMotor v4bm, CRServo ils, CRServo irs) {
        l = lm;
        r = rm;
        v4b = v4bm;
        il = ils;
        ir = irs;
    }
    public void runManual(double dr4bp, double ip, boolean pl, boolean cl, boolean pr, boolean cr) {
        l.setPower(-dr4bp);
        r.setPower(dr4bp);
        il.setPower(ip);
        ir.setPower(ip);
//        if (!pl && cl) {
//            v4b.setTargetPosition();
//            v4b.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        } else if (!pr && cr) {
//            v4b.setTargetPosition();
//            v4b.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
    }
}
