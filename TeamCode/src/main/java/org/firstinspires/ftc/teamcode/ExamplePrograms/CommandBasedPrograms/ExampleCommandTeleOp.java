package org.firstinspires.ftc.teamcode.ExamplePrograms.CommandBasedPrograms;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Auto.BulkCacheCommand;

@TeleOp
public class ExampleCommandTeleOp extends CommandOpMode {

    ExampleMechanism em;

    final int Example = 100;

    private boolean pidActive = false;

    @Override
    public void initialize() {
        GamepadEx exampleGamepad1 = new GamepadEx(gamepad1);

        DcMotor ExampleMotor = hardwareMap.dcMotor.get("Example Motor");

        em = new ExampleMechanism(ExampleMotor);

        schedule(new BulkCacheCommand(hardwareMap));

        exampleGamepad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> {pidActive = true;}))
                .whenPressed(new ExamplePID(em, Example).withTimeout(1500))
                .whenReleased(new InstantCommand(() -> {pidActive = false;}));

        telemetry.addLine("Example Command TeleOp ready to Run");
        telemetry.update();
    }

    @Override
    public void run() {
        super.run();
        if (!pidActive)
        {
            em.runManual(gamepad2.left_trigger - gamepad2.right_trigger);
            telemetry.addLine("Motor Position:" + em.getPosition());
            telemetry.update();
        }
    }
}
