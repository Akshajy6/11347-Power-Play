package org.firstinspires.ftc.teamcode.Outreach.Tank;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Outreach.Tank.TankDrive;

@TeleOp(name="Tank Drive Tele Operation Software Hack Bundle")
public class TankTeleOp extends LinearOpMode {
    DcMotor fl, fr, bl, br;

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotor.class, "frontleft");
        fr = hardwareMap.get(DcMotor.class, "frontright");
        bl = hardwareMap.get(DcMotor.class, "backleft");
        br = hardwareMap.get(DcMotor.class, "backright");

        waitForStart();


        while (opModeIsActive()) {
            double left = gamepad1.left_stick_y;
            double right = -gamepad1.right_stick_y;
            fl.setPower(left
            );
            fr.setPower(right);
            bl.setPower(right);
            br.setPower(left);
        }
    }
}
