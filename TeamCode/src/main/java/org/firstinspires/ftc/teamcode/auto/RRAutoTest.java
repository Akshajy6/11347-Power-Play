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

        Pose2d startpose = new Pose2d(-31, 64, Math.toRadians(180));

        drive.setPoseEstimate(startpose);

        TrajectorySequence scoreHighPole = drive.trajectorySequenceBuilder(startpose)
                .forward(4)
                .strafeLeft(6)
                .turn(Math.toRadians(90))
                .forward(47)
                .strafeLeft(10.5)
                .addDisplacementMarker(45, () -> {
                    l.setPower(0.7);
                    r.setPower(0.7);
                })
                .addDisplacementMarker(64, () -> {
                    i.setPower(0.8);
                })
                .addDisplacementMarker(() -> {
                    l.setPower(0);
                    r.setPower(0);
                    i.setPower(0);
                })
                .back(1.5)
                .turn(Math.toRadians(-90))
                .forward(32)
                .addDisplacementMarker(71, () -> {
                    l.setPower(0.6);
                    r.setPower(0.6);
                })
                .addDisplacementMarker(86, ()-> {
                    l.setPower(0);
                    r.setPower(0);
                })
                .addDisplacementMarker(91, () -> {
                    i.setPower(-0.8);
                })
                .addDisplacementMarker(96,()->{
                    l.setPower(0.7);
                    r.setPower(0.7);
                    i.setPower(0);
                })
                .back(32)
                .turn(Math.toRadians(90))
                .forward(2)
                .addDisplacementMarker(135,()-> {
                    i.setPower(0.8);
                })
                .addDisplacementMarker(145, () -> {
                    i.setPower(0);
                    l.setPower(0);
                    r.setPower(0);
                })
                .back(2)
                .build();

        waitForStart();

        if(!isStopRequested()) {
            drive.followTrajectorySequence(scoreHighPole);
        }
    }
}
