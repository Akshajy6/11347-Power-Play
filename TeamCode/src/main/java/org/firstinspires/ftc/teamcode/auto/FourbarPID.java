package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Mecanum20D54D.FourBar;
//Fourbar PID made by Ryan, its better
public class FourbarPID extends CommandBase {

    private FourBar fb;
    private double target;

    public FourbarPID(FourBar fb, double target){
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
        return Math.abs(fb.currentPosition()-target) < 4;
    }
    @Override
    public void end(boolean interrupted){
        fb.runManual(0.11);
    }
}
