package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-24, 64, Math.toRadians(180)))
                                .forward(4)
                        .strafeLeft(6)
                        .turn(Math.toRadians(90))
                        .forward(38)
                        .strafeLeft(17)
                        .addDisplacementMarker(45, () -> {
//                            l.setPower(0.7);
//                            r.setPower(0.7);
                        })
                        .addDisplacementMarker(64, () -> {
//                            i.setPower(0.8);
                        })
                        .addDisplacementMarker(() -> {
//                            l.setPower(0);
//                            r.setPower(0);
//                            i.setPower(0);
                        })
                        .back(2.5)
                        .turn(Math.toRadians(-90))
                        .forward(26.45)
                        .addDisplacementMarker(71, () -> {
//                            l.setPower(0.6);
//                            r.setPower(0.6);
                        })
                        .addDisplacementMarker(86, ()-> {
//                            l.setPower(0);
//                            r.setPower(0);
                        })
                        .addDisplacementMarker(91, () -> {
//                            i.setPower(-0.8);
                        })
                        .addDisplacementMarker(96,()->{
//                            l.setPower(0.7);
//                            r.setPower(0.7);
//                            i.setPower(0);
                        })
                        .back(29.5)
                        .turn(Math.toRadians(90))
                        .forward(3)
                        .addDisplacementMarker(135,()-> {
//                            i.setPower(0.8);
                        })
                        .addDisplacementMarker(145, () -> {
//                            i.setPower(0);
//                            l.setPower(0);
//                            r.setPower(0);
                        })
                        .back(4)

                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}