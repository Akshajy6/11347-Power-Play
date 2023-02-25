package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

public class Trajectories {

    public static TrajectorySequence toHighPoleLeft;
    public static TrajectorySequence toHighPoleRight;
    public static TrajectorySequence toConeStack;
    public static TrajectorySequence toLowPole;
    public static TrajectorySequence fromLowToStack;
    public static TrajectorySequence parkLeft;
    public static TrajectorySequence parkMid;
    public static TrajectorySequence parkRight;
    public static Pose2d startPose = new Pose2d(-33, 64, Math.toRadians(270));

    public static void generateTrajectories(SampleMecanumDrive drive) {
        drive.setPoseEstimate(startPose);

        //Change trajectories only if needed
        toHighPoleLeft = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(12)
                .turn(Math.toRadians(-5.5))
                .forward(58)
                .back(6)
                .turn(Math.toRadians(-54))
                .forward(7.75)
//                .waitSeconds(0.1)
//                .forward(3)
                .waitSeconds(1)
                .back(12)
                .waitSeconds(0.5)
                .turn(Math.toRadians(-63))
                .build();

        toHighPoleRight = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(12)
                .turn(Math.toRadians(-8))
                .forward(58)
                .back(6)
                .turn(Math.toRadians(66))
                .forward(11.5)
                .waitSeconds(1)
                .back(7)
                .build();

        toConeStack = drive.trajectorySequenceBuilder(toHighPoleLeft.end())
                .turn(Math.toRadians(146.5))
                .forward(33.5)
                .back(28)
//                .back(1)
//                .strafeLeft(2.5)
//                .forward(47)
                .build();

        toLowPole = drive.trajectorySequenceBuilder(toConeStack.end())
                .waitSeconds(.1)
                .turn(Math.toRadians(-90))
                .forward(5)
                .build();

        fromLowToStack = drive.trajectorySequenceBuilder(toLowPole.end())
                .back(10)
                .build();

        parkLeft = drive.trajectorySequenceBuilder(toLowPole.end())
                .back(27)
                .build();

        parkMid = drive.trajectorySequenceBuilder(toLowPole.end())
                .build();

        parkRight = drive.trajectorySequenceBuilder(toLowPole.end())
                .forward(27)
                .build();
    }
}
