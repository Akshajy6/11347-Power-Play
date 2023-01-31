package org.firstinspires.ftc.teamcode.Auto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.FourBar;
//Fourbar PID made by Ryan, its better
public class FourBarPID extends CommandBase {

    private FourBar fb;
    private double target;

    public FourBarPID(FourBar fb, double target){
        this.fb = fb;
        this.target = target;
    }
    @Override
    public void initialize(){
    }
    @Override
    public void execute(){
        fb.runPID(target);
    }
    @Override
    public boolean isFinished(){
        return Math.abs(fb.currentPosition()-target) < 20;
    }
    @Override
    public void end(boolean interrupted){
        fb.runManual(0.11);
    }
}
