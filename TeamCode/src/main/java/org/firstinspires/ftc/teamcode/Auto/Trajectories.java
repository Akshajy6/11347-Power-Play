package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

public class Trajectories {

    public static TrajectorySequence toHighPole;
    public static TrajectorySequence toConeStack;
    public static TrajectorySequence backToHighPole;
    public static TrajectorySequence parkLeft;
    public static TrajectorySequence parkMid;
    public static TrajectorySequence parkRight;
    public static TrajectorySequence RBadPark;
    public static TrajectorySequence LBadPark;
    public static Pose2d startPose = new Pose2d(-33, 64, Math.toRadians(270));

    public static void generateTrajectories(SampleMecanumDrive drive) {
        drive.setPoseEstimate(startPose);

        //Change trajectories only if needed
        toHighPole = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(8)
                .forward(106)
                .back(10)
                .strafeLeft(36)
                .back(10)
                .build();

        toConeStack = drive.trajectorySequenceBuilder(toHighPole.end())
                .back(1)
                .turn(Math.toRadians(-90))
                .forward(4)
                .strafeLeft(2.5)
                .forward(47)
                .build();

        backToHighPole = drive.trajectorySequenceBuilder(toConeStack.end())
                .back(47)
                .turn(Math.toRadians(90))
                .forward(3)
                .build();

        parkLeft = drive.trajectorySequenceBuilder(backToHighPole.end())
                .back(1)
                .strafeLeft(24)
                .build();

        parkMid = drive.trajectorySequenceBuilder(backToHighPole.end())
                .strafeRight(12)
                .build();

        parkRight = drive.trajectorySequenceBuilder(backToHighPole.end())
                .strafeRight(48)
                .build();

        RBadPark = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(20)
                .back(5)
                .build();

        LBadPark = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(20)
                .back(5)
                .build();
    }
}
