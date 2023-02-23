package org.firstinspires.ftc.teamcode.Mecanum20D54D;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PID {
    private final double p;
    private final double i;
    private final double d;
    double integral;
    double lastError;
    ElapsedTime timer = new ElapsedTime();

    public PID (double kp, double ki, double kd) {
        p = kp;
        i = ki;
        d = kd;
    }

    public double update(double state, double target) {
        // calculating error
        double error = target - state;
        // rate of change of the error
        double derivative = (error - lastError) / timer.seconds();
        // sum of all error over time
        integral += (error * timer.seconds());
        // PID equation
        double output = (p * error) + (i * integral) + (d * derivative);
        // updating lastError and resetting timer
        lastError = error;
        timer.reset();
        return output;
    }
}
