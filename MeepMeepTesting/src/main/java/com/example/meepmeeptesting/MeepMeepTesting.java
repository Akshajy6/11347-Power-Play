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
                        drive.trajectorySequenceBuilder(new Pose2d(-31, 64, Math.toRadians(180)))
                                .forward(4)
                                .strafeLeft(6)
                                .turn(Math.toRadians(90))
                                .forward(34)
                                .strafeLeft(14.5)
                                .addDisplacementMarker(45, () -> {
//                                    l.setPower(0.7);
//                                    r.setPower(0.7);
                                })
                                .forward(5)
                                .addDisplacementMarker(63.5, () -> {
//                                    i.setPower(0.8);
                                })
                                .back(3)
                                .addDisplacementMarker(65, () -> {
//                                    i.setPower(0);
//                                    l.setPower(0);
//                                    r.setPower(0);
                                })

                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}