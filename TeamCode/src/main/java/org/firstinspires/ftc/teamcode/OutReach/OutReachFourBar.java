package org.firstinspires.ftc.teamcode.OutReach;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;

public class OutReachFourBar extends SubsystemBase {
    final private DcMotor l;
    final private DcMotor r;
    final private DcMotor i;

    public OutReachFourBar(DcMotor lm, DcMotor rm, DcMotor im) {
        l = lm;
        r = rm;
        i = im;
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void runManual(double fbp, double rp) {
        l.setPower(fbp);
        r.setPower(-fbp);
        i.setPower(rp);
    }
}
