package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class Trajectories {

    public static TrajectorySequence toHighPole;
    public static TrajectorySequence toConeStack;
    public static TrajectorySequence backToHighPole;
    public static TrajectorySequence parkLeft;
    public static TrajectorySequence parkMid;
    public static TrajectorySequence parkRight;
    public static Pose2d startPose = new Pose2d(-33, 64, Math.toRadians(270));

    public static void generateTrajectories(SampleMecanumDrive drive) {
        drive.setPoseEstimate(startPose);

        //Change trajectories only if needed

        toHighPole = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(8)
                .forward(72)
                .strafeLeft(20)
                .build();

        toConeStack = drive.trajectorySequenceBuilder(toHighPole.end())
                .turn(Math.toRadians(-90))
                .forward(55)
                .build();

        backToHighPole = drive.trajectorySequenceBuilder(toConeStack.end())
                .back(50)
                .turn(Math.toRadians(90))
                .forward(3)
                .build();

        parkLeft = drive.trajectorySequenceBuilder(backToHighPole.end())
                .strafeLeft(24)
                .build();

        parkMid = drive.trajectorySequenceBuilder(backToHighPole.end())
                .strafeRight(4)
                .build();

        parkRight = drive.trajectorySequenceBuilder(backToHighPole.end())
                .strafeRight(48)
                .build();
    }
}
