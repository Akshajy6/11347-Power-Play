package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class RRAutoTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startpose = new Pose2d(60, 36);

        drive.setPoseEstimate(startpose);

        TrajectorySequence ts = drive.trajectorySequenceBuilder(startpose)
                .lineTo(new Vector2d(12, 36))
                .build();

        waitForStart();

        if(!isStopRequested()) {
            drive.followTrajectorySequence(ts);
        }
    }
}
