package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Varun on 9/2/2017.
 */
@TeleOp(name = "PSTeleOp", group = "opMode")
public class PapaSmurfTeleOp extends PapaSmurfOpMode {

    @Override
    public void loop() {
        powerR = gamepad1.right_stick_y;
        powerL = gamepad1.left_stick_y;

        slowingFactor = 1 - ((gamepad1.right_trigger * .5) + (gamepad1.left_trigger * .25));

        if(gamepad1.a) {
            reverse();
            while(gamepad1.a);
        }

        //if none of our motors are running, get the voltage
        if(motorBL.getPower() == 0 && motorBR.getPower() == 0 && motorFL.getPower() == 0 &&
                motorFR.getPower() == 0) {
            voltage = getVoltage();
        }

        telemetry.update();

        if (Math.abs(powerR) > .05 || (Math.abs(powerL) > .05)) {
            startMotors(powerR, powerL);
        } else {
            stopMotors();
        }
    }
}