package org.firstinspires.ftc.teamcode.Auto;

import android.animation.IntArrayEvaluator;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Mecanum20D54D.Mechanisms;
import org.firstinspires.ftc.teamcode.Auto.apriltag.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.MecanumSyntaxError.FourBarPID;
import org.firstinspires.ftc.teamcode.MecanumSyntaxError.SyntaxErrorMechanisms;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

<<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/AutoLeft.java
@Autonomous(name="LEFT AUTO USE THIS ONE")
public class AutoLeft extends CommandOpMode {
========
@Autonomous(name="Right Auto (1 + Park)")
public class CommandAutoB extends CommandOpMode {
>>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto):TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/CommandAutoB.java
    private DcMotor l;
    private DcMotor r;
    private CRServo il;
    private CRServo ir;
    SampleMecanumDrive drive;

    //Camera stuff, dw about it
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;
    double tagSize = 0.035;
    AprilTagDetection tagOfInterest = null;

    int tagId = 0;

    final double HIGH = 729;
    final double MID = 519;
    final double LOW = 298;
    final double CONE = 30;

    @Override
    public void initialize() {
        //More camera stuff
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagSize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        //Initializing stuff
        drive = new SampleMecanumDrive(hardwareMap);
        l = hardwareMap.dcMotor.get("l");
        r = hardwareMap.dcMotor.get("r");
        il = hardwareMap.crservo.get("il");
        ir = hardwareMap.crservo.get("ir");
        SyntaxErrorMechanisms fb = new SyntaxErrorMechanisms(l, r, il, ir);

        //Even more camera stuff
        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == 0 || tag.id == 1 || tag.id == 2) {
                        tagOfInterest = tag;
                        tagFound = true;
                    }
                    tagId = tag.id;
                }

                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        Trajectories.generateTrajectories(drive); //Loads trajectories from trajectories file

        TrajectorySequence park;
        switch(tagId){
            case 0:
                park = Trajectories.parkLeft;
                break;
            case  1:
                park = Trajectories.parkMid;
                break;
            default:
                park = Trajectories.parkRight;
        }
        schedule(new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> {fb.reset();}),
                        new TrajectorySequenceCommand(drive, Trajectories.spline),
                        new SequentialCommandGroup(
<<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/AutoLeft.java
                                new WaitCommand(1000),
                                new FourBarPID(fb, HIGH + 50).withTimeout(1000),
========
                                new WaitCommand(6000),
                                new FourBarPID(fb, LOW),
                                new WaitCommand(1000),
                                new FourBarPID(fb, HIGH).withTimeout(1000),
                                new WaitCommand(1000),
                                new WaitCommand(600),
>>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto):TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/CommandAutoB.java
                                new InstantCommand(() -> {
                                    fb.runIntake(1);
                                }),
                                new WaitCommand(200),
                                new InstantCommand(() -> {
                                    fb.runIntake(0);
                                }),
<<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/AutoLeft.java
                                new FourBarPID(fb, LOW - 50).withTimeout(1000),
                                new WaitCommand(600),
                                new FourBarPID(fb, CONE).withTimeout(1000),
                                new WaitCommand(100),
                                new InstantCommand(() -> {
                                    fb.runIntake(1);
                                }),
                                new WaitCommand(300),
========
                                new WaitCommand(2650),
                                new FourBarPID(fb, LOW + 100),
                                new WaitCommand(1000),
                                new FourBarPID(fb, 0),
>>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto):TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/CommandAutoB.java
                                new InstantCommand(() -> {
                                    l.setPower(0);
                                    r.setPower(0);
                                })
                        )
<<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/AutoLeft.java
                )
//                new ParallelCommandGroup(
//                        new TrajectorySequenceCommand(drive, Trajectories.toConeStackRight),//goes to cone stack and intakes
//                        new SequentialCommandGroup (
//                                new FourBarPID(fb, MID),
//                                new WaitCommand(1750),//adjust
//                                new FourBarPID(fb, 200),
//                                new WaitCommand(300),
//                                new InstantCommand(() -> {
//                                    fb.runIntake(-1);
//                                }),
//                                new WaitCommand(750),//adjust
//                                new InstantCommand(() -> {
//                                    fb.runIntake(0);
//                                })
//                        )

//                )
//                new TrajectorySequenceCommand(drive, park)
========
                ),//This will cause the robot to park
                new TrajectorySequenceCommand(drive, park)
>>>>>>>> parent of 0ea1c61 (Fixed IMU reset, working on auto):TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/CommandAutoB.java
        ));
    }

    void tagToTelemetry(AprilTagDetection detection) {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("\nParking spot %d", detection.id + 1));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
    }
}