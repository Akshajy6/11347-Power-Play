/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auto.apriltag.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous
public class NewAuto extends LinearOpMode
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagSize = 0.035;
    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagSize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");
        DcMotor i = hardwareMap.dcMotor.get("i");
        i.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Pose2d startPose = new Pose2d(-31, 64, Math.toRadians(180));

        drive.setPoseEstimate(startPose);

        telemetry.setMsTransmissionInterval(50);

        TrajectorySequence scoreHighPole = drive.trajectorySequenceBuilder(startPose)
                .forward(5)
                .strafeLeft(6)
                .turn(Math.toRadians(90))
                .forward(41)
                .strafeLeft(19)
                .addDisplacementMarker(61, () -> {
                    l.setPower(0.8);
                    r.setPower(0.8);
                })
                .forward(5.5)
                .addDisplacementMarker(76.5, () -> {
                    i.setPower(0.8);
                })
                .back(4)
                .addDisplacementMarker(78, () -> {
                    i.setPower(0);
                    l.setPower(0);
                    r.setPower(0);
                })
                .turn(Math.toRadians(-90))
                .forward(36)
                .addDisplacementMarker(110, () -> {
                    l.setPower(0.8);
                    r.setPower(0.8);
                })
                .addDisplacementMarker(115, () -> {
                    l.setPower(0);
                    r.setPower(0);
                })
                .addDisplacementMarker(116.5, () -> {
                    i.setPower(-0.8);
                })
                .waitSeconds(1)
                .addDisplacementMarker(() -> {
                    l.setPower(0.8);
                    r.setPower(0.8);
                })
                .back(36)
                .addDisplacementMarker()
                .turn(Math.toRadians(90))
                .forward(4)
                .addDisp
                .build();

        TrajectorySequence parkLeft = drive.trajectorySequenceBuilder(scoreHighPole.end())
                .strafeLeft(15)
                .build();

        TrajectorySequence parkMid = drive.trajectorySequenceBuilder(scoreHighPole.end())
                .strafeRight(12)
                .build();

        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(scoreHighPole.end())
                .strafeRight(33)
                .build();


        //
        // The INIT-loop:
        // This REPLACES waitForStart!
        //
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == 0 || tag.id == 1 || tag.id == 2)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        //
        // The START command just came in: now work off the latest snapshot acquired
        // during the init loop.


        // Update the telemetry
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        // Actually do something useful
        if(tagOfInterest == null)
        {
            //Cone scoring auto code without parking
            drive.followTrajectorySequence(scoreHighPole);
        }
        else
        {
            int id = tagOfInterest.id;
            //Cone scoring auto code with parking
            drive.followTrajectorySequence(scoreHighPole);
            if (id == 0) {
                //Park left
                drive.followTrajectorySequence(parkLeft);
            } else if (id == 1) {
                //Park mid
                drive.followTrajectorySequence(parkMid);
            } else {
                //Park right
                drive.followTrajectorySequence(parkRight);
            }
        }


        //You wouldn't have this in your autonomous, this is just to prevent the sample from ending
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
    }
}

