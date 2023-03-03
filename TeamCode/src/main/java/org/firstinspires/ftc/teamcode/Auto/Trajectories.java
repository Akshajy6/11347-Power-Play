package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

public class Trajectories {

    public static TrajectorySequence toHighPoleLeft;
    public static TrajectorySequence toHighPoleRight;
    public static TrajectorySequence toConeStackLeft;
    public static TrajectorySequence toConeStackRight;
<<<<<<< HEAD
    public static TrajectorySequence toShortPoleRight;
    public static TrajectorySequence backToConeStackRight;
    public static TrajectorySequence spline;
=======
    public static TrajectorySequence toLowPole;
    public static TrajectorySequence fromLowToStack;
>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto)
    public static TrajectorySequence parkLeft;
    public static TrajectorySequence parkMid;
    public static TrajectorySequence parkRight;
    public static Pose2d startPose = new Pose2d(-64, 40, Math.toRadians(0));

    public static void generateTrajectories(SampleMecanumDrive drive) {
        drive.setPoseEstimate(startPose);

<<<<<<< HEAD
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

=======
        //Change trajectories only if needed
>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto)
        toHighPoleLeft = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(12)
                //.turn(Math.toRadians(-5.5))
                .forward(58)
                .back(6)
<<<<<<< HEAD
                .turn(Math.toRadians(-60))
=======
                .turn(Math.toRadians(-58))
>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto)
                .forward(7.75)
//                .waitSeconds(0.1)
//                .forward(3)
                .waitSeconds(1)
                .back(12)
                .waitSeconds(0.5)
                .turn(Math.toRadians(-50))
                .build();

        toHighPoleRight = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(11)
                //.turn(Math.toRadians(-8))
                .forward(58)
                .back(6)
                .turn(Math.toRadians(58))
                .forward(6.5)
                .waitSeconds(1)
                .back(12)
                .waitSeconds(0.5)
                .turn(Math.toRadians(50))
                .build();

        toConeStackLeft = drive.trajectorySequenceBuilder(toHighPoleLeft.end())
                .turn(Math.toRadians(146.5))
                .forward(33.5)
                .back(28)
//                .back(1)
//                .strafeLeft(2.5)
//                .forward(47)
                .build();

        toConeStackRight = drive.trajectorySequenceBuilder(toHighPoleRight.end())
                .turn(Math.toRadians(-146.5))
                .build();

        toLowPole = drive.trajectorySequenceBuilder(toConeStackLeft.end())
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
                .forward(1)
                .turn(1)
                .build();

        parkRight = drive.trajectorySequenceBuilder(toLowPole.end())
                .forward(27)
                .build();
    }
}
