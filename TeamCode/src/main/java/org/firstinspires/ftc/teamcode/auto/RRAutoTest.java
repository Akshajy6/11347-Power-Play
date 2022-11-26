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
                .forward(5)
                .strafeLeft(6)
                .turn(Math.toRadians(90))
                .forward(38)
                .strafeLeft(16)
                .addDisplacementMarker(50, () -> {
                    l.setPower(0.8);
                    r.setPower(0.8);
                })
                .forward(4)
                .addDisplacementMarker(69, () -> {
                    i.setPower(0.8);
                })
                .back(4)
                .build();

//        TrajectorySequence toHighPole = drive.trajectorySequenceBuilder(startpose)
//                .forward(4)
//                .lineToLinearHeading(new Pose2d(-36, 12, Math.toRadians(325)))
//                .addDisplacementMarker(35, () -> {
//                    l.setPower(0.8);
//                    r.setPower(0.8);
//                })
//                .forward(4)
//                .addDisplacementMarker(58, () -> {
//                    i.setPower(0.8);
//                })
//                .back(4)
//                .build();

        waitForStart();

        if(!isStopRequested()) {
            drive.followTrajectorySequence(scoreHighPole);
        }
    }
}
