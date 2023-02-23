package org.firstinspires.ftc.teamcode.PID_Testing.positions;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class mechanismPID extends SubsystemBase {

    public enum fourbarPosition
    {
        HIGH(500),
        MID(450),
        LOW(250),
        CONE(30);

        public int position;

        fourbarPosition(int position)
        {
            this.position = position;
        }
    }

    private DcMotorEx leftm;
    private DcMotorEx rightm;

    private VoltageSensor voltageSensor;
    private double voltageComp = 1.0;

    public mechanismPID(HardwareMap hardwareMap)
    {
        leftm = hardwareMap.get(DcMotorEx.class, "motor");
        rightm = hardwareMap.get(DcMotorEx.class, "motor");

        leftm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        voltageSensor = hardwareMap.voltageSensor.iterator().next();
        voltageComp = 12.0 / voltageSensor.getVoltage();
    }

    @Override
    public void periodic(){}

    public void setFourbarPower(double power)
    {
        leftm.setPower(power)
    }
}
