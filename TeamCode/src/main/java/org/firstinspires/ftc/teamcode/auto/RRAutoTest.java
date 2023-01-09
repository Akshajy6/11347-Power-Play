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
        DcMotor i = hardwareMap.dcMotor.get("i");
        i.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Pose2d startPose= new Pose2d(-33, 64, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence scoreHighPole = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(9)
                .forward(75)
                .strafeLeft(21)
                .addDisplacementMarker(85, () -> {
                    l.setPower(0.8);
                    r.setPower(0.8);
                })
                .addDisplacementMarker(104, () -> {
                    l.setPower(0);
                    r.setPower(0);
                })
                .waitSeconds(1)
                .addDisplacementMarker(() -> {
                    i.setPower(0.8);
                    l.setPower(0.8);
                    r.setPower(0.8);
                })
                .waitSeconds(2)
                .back(6)
//                .turn(Math.toRadians(-90))
//                .forward(48)
//                .addDisplacementMarker(135, () -> {
//                    l.setPower(0);
//                    r.setPower(0);
//                    i.setPower(0);
//                })
//                .addDisplacementMarker(159, () -> {
//                    i.setPower(-0.8);
//                })
//                .waitSeconds(0.5)
//                .addDisplacementMarker(() -> {
//                    l.setPower(0.8);
//                    r.setPower(0.8);
//                })
//                .waitSeconds(2)
//                .back(40)
//                .turn(Math.toRadians(90))
//                .strafeLeft(8)
                .build();

        TrajectorySequence parkLeft = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(16)
                .build();

        TrajectorySequence parkMid = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(16)
                .build();

        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(40)
                .build();

        waitForStart();

        if(!isStopRequested()) {
            drive.followTrajectorySequence(parkRight);
        }
    }
}
