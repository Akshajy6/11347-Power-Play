package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class RRAutoTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");

        Pose2d startpose = new Pose2d(36, -60);

        drive.setPoseEstimate(startpose);

        TrajectorySequence ts = drive.trajectorySequenceBuilder(startpose)
                .back(24)
                .turn(Math.toRadians(90))
                .forward(44)
                .turn(Math.toRadians(38))
//                .addDisplacementMarker(48, () -> l.setPower(-0.5))
//                .addDisplacementMarker(48, () -> r.setPower(0.5))
                .build();

        waitForStart();

        if(!isStopRequested()) {
            drive.followTrajectorySequence(ts);
        }
    }
}
