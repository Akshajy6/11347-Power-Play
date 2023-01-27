package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Mecanum20D54D.FourBar;
import org.firstinspires.ftc.teamcode.auto.apriltag.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous
public class CommandAuto extends CommandOpMode {
    private DcMotor l;
    private DcMotor r;
    private DcMotor i;
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


    double tagId = 0;


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
        i = hardwareMap.dcMotor.get("i");
        FourBar fb = new FourBar(l, r, i);

        //Even more camera stuff
        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == 0 || tag.id == 1 || tag.id == 2) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
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

        //The below should be enough for a 1 + 2 cycle, only change drive trajectories if needed (in trajectories file)
        //Put fourbar/intake/outtake commands in the gaps where the comments are
        //Use fb.runPID(700) to raise fourbar to max and fb.runPID(0) to lower fourbar.
        //For intake, just set positive or negative power and use a wait command after for intake/outtake of cone.
        //Ask Aarush's help for using ParallelCommandGroup inside the SequentialCommandGroup to move fourbar/intake at the same time while robot is moving.
        schedule(new SequentialCommandGroup(
                //lift fourbar to max and score cone
                new ParallelCommandGroup(
                        new TrajectorySequenceCommand(drive, Trajectories.toHighPole),
                        new SequentialCommandGroup(
                                new WaitCommand(1000),
                                new FourbarPID(fb, 700)
                        )

                ),
                new SequentialCommandGroup(
                        new InstantCommand(() -> fb.runIntake(-1)),
                        new WaitCommand(500),
                        new InstantCommand(() -> fb.runIntake(0))
                ),
                new ParallelCommandGroup(
                        switch(tagId){
                            case 0:
                                new TrajectorySequenceCommand(drive, Trajectories.parkLeft);
                            case  1:
                                new TrajectorySequenceCommand(drive, Trajectories.parkMid);
                            default:
                                new TrajectorySequenceCommand(drive, Trajectories.parkRight);
                        }
                )

        ));
    }

    @Override
    public void run() {
        // Run auto based on april tag, dw about it
//        if (tagOfInterest != null) {
//            telemetry.addLine("Tag snapshot:\n");
//            tagToTelemetry(tagOfInterest);
//            telemetry.update();
//        } else {
//            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
//            telemetry.update();
//        }
//
//        if (tagOfInterest == null) {
//            CommandScheduler.getInstance().run();
//        } else {
//            int id = tagOfInterest.id;
//            //Cone scoring auto code with parking
//            CommandScheduler.getInstance().run();
//            if (id == 0) {
//                //Park left
//                drive.followTrajectorySequence(Trajectories.parkLeft);
//            } else if (id == 1) {
//                //Park mid
//                drive.followTrajectorySequence(Trajectories.parkMid);
//            } else {
//                //Park right
//                drive.followTrajectorySequence(Trajectories.parkRight);
//            }
//        }
    }

    void tagToTelemetry(AprilTagDetection detection) {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("\nParking spot %d", detection.id + 1));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
    }
}


//                new TrajectorySequenceCommand(drive, Trajectories.toConeStack),
//
//                //drop fourbar on stack and intake cone, then lift fourbar again so we dont knock over stack
//
//                new TrajectorySequenceCommand(drive, Trajectories.backToHighPole),
//                //lift fourbar to max and score cone (same as other)
//
//                new TrajectorySequenceCommand(drive, Trajectories.toConeStack),
//                //same as other toConeStack
//
//                new TrajectorySequenceCommand(drive, Trajectories.backToHighPole)
//same as other scoring on high pole
