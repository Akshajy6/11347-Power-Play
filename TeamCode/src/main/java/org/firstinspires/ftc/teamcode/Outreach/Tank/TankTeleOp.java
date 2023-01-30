package org.firstinspires.ftc.teamcode.Outreach.Tank;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Outreach.Tank.TankDrive;

@TeleOp
@Disabled
public class TankTeleOp extends OpMode {
    TankDrive drivebase;

    @Override
    public void init() {
        drivebase = new TankDrive(hardwareMap.get(DcMotor.class, "frontleft"),
                hardwareMap.get(DcMotor.class, "frontright"),
                hardwareMap.get(DcMotor.class, "backleft"),
                hardwareMap.get(DcMotor.class, "backright"));
    }

    @Override
    public void loop() {
        drivebase.drive(gamepad1.left_stick_y, -gamepad1.right_stick_y);
    }
}
