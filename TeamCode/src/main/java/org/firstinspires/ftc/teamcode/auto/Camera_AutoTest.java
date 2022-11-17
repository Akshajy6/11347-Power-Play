package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auto.apriltag.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous
public class Camera_AutoTest extends LinearOpMode
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
    double tagsize = 0.035;
    int[] ids = {0, 1, 2};

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

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

        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        DcMotor bl = hardwareMap.dcMotor.get("bl");
        DcMotor br = hardwareMap.dcMotor.get("br");
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor l = hardwareMap.dcMotor.get("l");
        DcMotor r = hardwareMap.dcMotor.get("r");
        DcMotor i = hardwareMap.dcMotor.get("i");

        telemetry.setMsTransmissionInterval(50);
        double p = 0.5;



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
        //Left to high pole
        fl.setPower(p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(p);
        sleep(3550);

        //Stop moving wheels
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        sleep(200);

        //Raise fourbar (might have to change power on this one as well as time for it to actually go up and counteract gravity) (try time first)
        l.setPower(0.8);
        r.setPower(0.8);
        sleep(2000);

        //Go forwards a little to position intake/outtake over pole
        fl.setPower(-p);
        fr.setPower(-p);
        bl.setPower(-p);
        br.setPower(-p);
        sleep(350);

        //Stop moving wheels
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        //Run outtake to score cone
        i.setPower(p);
        sleep(500);

        //Move backwards so outtake is not over pole
        fl.setPower(p);
        fr.setPower(p);
        bl.setPower(p);
        br.setPower(p);
        sleep(400);

        //Stop extending fourbar (might have to be negative, idk try 0 first Fourbar needs lots of trial and error)
        l.setPower(0);
        r.setPower(0);

        //Don't change unless auto cannot be completed in time

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


//**IMPORTANT** The sleep values are assuming each square is 600ms long
        // Actually do something useful
        if(tagOfInterest == null)
        {
            //Trying to make useful lol, gonna try and put a tele op here that will go and place cones
            //If it can't park might as well have a semi useful program to go get cones and score some
            //Who knows might actually be able to be implemented directly into main teleOp and not used as a backup
            //Turn to straighten orientation for TeleOp
            fl.setPower(p);
            fr.setPower(-p);
            bl.setPower(p);
            br.setPower(-p);
            sleep(728);

            fr.setPower(-p);
            fl.setPower(p);
            br.setPower(p);
            bl.setPower(-p);
            sleep(300);

            fr.setPower(p);
            fl.setPower(-p);
            br.setPower(p);
            bl.setPower(-p);
            sleep(1456);

            l.setPower(0.6);
            r.setPower(0.6);
            sleep(550);

            //Go forwards a little to position intake/outtake over pole
            fl.setPower(-p);
            fr.setPower(-p);
            bl.setPower(-p);
            br.setPower(-p);
            sleep(350);

            //Stop moving wheels
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);

            l.setPower(0);
            r.setPower(0);

            //Fourbar should have dropped at this point so:
            i.setPower(p);
            sleep(300);

            //Fourbar should now have cone
            l.setPower(0.7);
            r.setPower(0.7);
            sleep(400);

            fl.setPower(p);
            fr.setPower(p);
            bl.setPower(p);
            br.setPower(p);
            sleep(350);

            l.setPower(0);
            r.setPower(0);

            //Fourbar should now be off stack and moved back to original position
            fr.setPower(p);
            fl.setPower(-p);
            br.setPower(p);
            bl.setPower(-p);
            sleep(1456);

            fr.setPower(p);
            fl.setPower(p);
            br.setPower(p);
            bl.setPower(p);
            sleep(300);

            //Raise fourbar (might have to change power on this one as well as time for it to actually go up and counteract gravity) (try time first)
            l.setPower(0.8);
            r.setPower(0.8);
            sleep(2000);

            //Go forwards a little to position intake/outtake over pole
            fl.setPower(-p);
            fr.setPower(-p);
            bl.setPower(-p);
            br.setPower(-p);
            sleep(350);

            //Stop moving wheels
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
            sleep(200);

            //Run outtake to score cone
            i.setPower(p);
            sleep(500);

            //Move backwards so outtake is not over pole
            fl.setPower(p);
            fr.setPower(p);
            bl.setPower(p);
            br.setPower(p);
            sleep(300);

            //Stop extending fourbar (might have to be negative, idk try 0 first Fourbar needs lots of trial and error)
            l.setPower(0);
            r.setPower(0);

        }
        else
        {
            int id = tagOfInterest.id;

            //Parking scoring auto code
            if (id == 0) {
                //Go right to where april tag cone started
                fl.setPower(-p);
                fr.setPower(p);
                bl.setPower(p);
                br.setPower(-p);
                sleep(2500);

                //Go backwards to parking spot
                fl.setPower(p);
                fr.setPower(p);
                bl.setPower(p);
                br.setPower(p);
                sleep(1000);

            } else if (id == 1) {
                //Go right to where april tag cone started
                fl.setPower(-p);
                fr.setPower(p);
                bl.setPower(p);
                br.setPower(-p);
                sleep(3600);

                //I have no idea what this section is for tbh
                fl.setPower(-p);
                fr.setPower(p);
                bl.setPower(p);
                br.setPower(-p);
                sleep(200);

            } else {
                //Go right to where april tag cone started
                fl.setPower(-p);
                fr.setPower(p);
                bl.setPower(p);
                br.setPower(-p);
                sleep(3600);

                //Go forward to parking space
                fl.setPower(-p);
                fr.setPower(-p);
                bl.setPower(-p);
                br.setPower(-p);
                sleep(600);
            }

            //Turn to straighten orientation for TeleOp
            fl.setPower(p);
            fr.setPower(-p);
            bl.setPower(p);
            br.setPower(-p);
            sleep(715);
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

