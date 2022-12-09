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

        Pose2d startPose= new Pose2d(-31, 64, Math.toRadians(180));

        drive.setPoseEstimate(startPose);

        TrajectorySequence scoreHighPole = drive.trajectorySequenceBuilder(startPose)
                .forward(4)
                .strafeLeft(6)
                .turn(Math.toRadians(90))
                .forward(34)
                .strafeLeft(14.5)
                .addDisplacementMarker(45, () -> {
                    l.setPower(0.7);
                    r.setPower(0.7);
                })
                .forward(5)
                .addDisplacementMarker(63.5, () -> {
                    i.setPower(0.8);
                })
                .back(3)
                .addDisplacementMarker(65, () -> {
                    i.setPower(0);
                    l.setPower(0);
                    r.setPower(0);
                })
//                .turn(Math.toRadians(-90))
//                .forward(30)
//                .addDisplacementMarker(90, ()-> {
//                    l.setPower(0);
//                    r.setPower(0);
//                })
//                .addDisplacementMarker(96, () -> {
//                    i.setPower(-0.8);
//                    l.setPower(0.8);
//                    r.setPower(0.8);
//                })
//                .back(5)
//                .addDisplacementMarker(() -> {
//                    l.setPower(0);
//                    r.setPower(0);
//                })
//                .back(27)
//                .addDisplacementMarker(115, () -> {
//                    l.setPower(0.8);
//                    r.setPower(0.8);
//                })
//                .turn(Math.toRadians(90))
//                .forward(2)
//                .addDisplacementMarker(127,()-> {
//                    i.setPower(0.8);
//                })
//                .back(5)
//                .addDisplacementMarker(() -> {
//                    i.setPower(0);
//                    l.setPower(0);
//                    r.setPower(0);
//                })
                .build();

        TrajectorySequence parkLeft = drive.trajectorySequenceBuilder(scoreHighPole.end())
                .strafeLeft(12)
                .build();

        TrajectorySequence parkMid = drive.trajectorySequenceBuilder(scoreHighPole.end())
                .strafeRight(12)
                .build();

        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(scoreHighPole.end())
                .strafeLeft(24)
                .build();

        waitForStart();

        if(!isStopRequested()) {
            drive.followTrajectorySequence(scoreHighPole);
        }
    }
}
