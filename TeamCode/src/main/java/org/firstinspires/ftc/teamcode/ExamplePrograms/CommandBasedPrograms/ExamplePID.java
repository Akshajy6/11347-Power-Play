package org.firstinspires.ftc.teamcode.ExamplePrograms.CommandBasedPrograms;

import com.arcrobotics.ftclib.command.CommandBase;

public class ExamplePID extends CommandBase {

    private ExampleMechanism em;
    private double target;

    public ExamplePID(ExampleMechanism example, double target) {
        this.em = example;
        this.target = target;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        em.runPID(target);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(em.getPosition() - target) < 25;
    }

    @Override
    public void end(boolean interrupted) {
        em.runManual(0);
    }
}
