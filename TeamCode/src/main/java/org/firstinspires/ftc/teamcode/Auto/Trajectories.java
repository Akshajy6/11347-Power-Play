package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

public class Trajectories {

    public static TrajectorySequence toHighPoleLeft;
    public static TrajectorySequence toHighPoleRight;
    public static TrajectorySequence toConeStackLeft;
    public static TrajectorySequence toConeStackRight;
    public static TrajectorySequence toShortPoleRight;
    public static TrajectorySequence backToConeStackRight;
    public static TrajectorySequence spline;
    public static TrajectorySequence parkLeft;
    public static TrajectorySequence parkMid;
    public static TrajectorySequence parkRight;
    public static Pose2d startPose = new Pose2d(-64, 40, Math.toRadians(0));

    public static void generateTrajectories(SampleMecanumDrive drive) {
        drive.setPoseEstimate(startPose);

        toHighPoleRight = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(11)
                .forward(43)
                .turn(Math.toRadians(47))
                .forward(12)
                .waitSeconds(0.5)
                .back(7)
                .turn(Math.toRadians(-115))
                .build();

        spline = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-12, 37, Math.toRadians(320)))
                .lineToLinearHeading(new Pose2d(-12, 60, Math.toRadians(90)))
                .waitSeconds(0.75)
                .lineToLinearHeading(new Pose2d(-6, 37, Math.toRadians(320)))
                .lineToLinearHeading(new Pose2d(-12, 60, Math.toRadians(90)))
                .waitSeconds(0.75)
                .lineToLinearHeading(new Pose2d(-6, 37, Math.toRadians(320)))
                .lineToLinearHeading(new Pose2d(-12, 60, Math.toRadians(90)))
                .waitSeconds(0.75)
                .lineToLinearHeading(new Pose2d(-6, 37, Math.toRadians(320)))
                .build();

        toHighPoleLeft = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(12)
                //.turn(Math.toRadians(-5.5))
                .forward(54)
                .back(6)
                .turn(Math.toRadians(-60))
                .forward(7.75)
//                .waitSeconds(0.1)
//                .forward(3)
                .waitSeconds(1)
                .back(12)
                .waitSeconds(0.5)
                .turn(Math.toRadians(-50))
                .build();

        toConeStackLeft = drive.trajectorySequenceBuilder(toHighPoleLeft.end())
                .turn(Math.toRadians(146.5))
                .forward(33.5)
                .back(28)
//                .back(1)
//                .strafeLeft(2.5)
//                .forward(47)
                .build();

        parkLeft = drive.trajectorySequenceBuilder(toHighPoleLeft.end())
                .forward(30)
                .build();

        parkMid = drive.trajectorySequenceBuilder(toHighPoleLeft.end())
                .turn(Math.toRadians(1))
                .build();

        parkRight = drive.trajectorySequenceBuilder(toHighPoleLeft.end())
                .back(27)
                .build();
    }
}
