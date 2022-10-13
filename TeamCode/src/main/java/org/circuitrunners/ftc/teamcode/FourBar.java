package org.circuitrunners.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class FourBar {
    private DcMotor l; private DcMotor r; private DcMotor i;
//    final private double UP = ; //100% extension
//    final private double HIGH = ; //high pole
//    final private double MED = ; //medium pole
//    final private double LOW = ; //low pole
//    final private double GROUND = ; //ground junction
//    final private double CONE = ; //cone height (for intaking)
//    final private double DOWN = ; //0% extension
//    private double target = DOWN;

    public FourBar(DcMotor lm, DcMotor rm) {
        l = lm;
        r = rm;

        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runManual(double state, double input) { //For manual solo or co-op
//        PID controller = new PID(0.05, 0, 0); //TO BE TUNED
//        double factor = 2; //Change to control dr4b speed
//        target += input * factor;
//        double pwr = controller.update(state, target);
//
//        l.setPower(-pwr);
//        r.setPower(pwr);
    }

    public void runAuto() {
        //TODO
    }
}

class PID { //PID feedback-loop controllers
    double Kp; double Ki; double Kd;
    double integral;
    double lastError;
    ElapsedTime timer = new ElapsedTime();


    public PID(double Kp, double Ki, double Kd) {}
    public double update(double state, double target) {
        // calculating error
        double error = target - state;

        // rate of change of the error
        double derivative = (error - lastError) / timer.seconds();

        // sum of all error over time
        integral = integral + (error * timer.seconds());

        // PID equation
        double output = (Kp * error) + (Ki * integral) + (Kd * derivative);

        // updating lastError and resetting timer
        lastError = error;
        timer.reset();
        return output;
    }
}