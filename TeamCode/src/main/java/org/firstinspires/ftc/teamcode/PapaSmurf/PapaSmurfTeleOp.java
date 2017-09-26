package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Varun on 9/2/2017.
 */
@TeleOp(name = "PSTeleOp", group = "opMode")
public class PapaSmurfTeleOp extends PapaSmurfOpMode {

    @Override
    public void loop() {

        //color sensor stuff

        if(gamepad1.a) {
            reverse();
            while(gamepad1.a);
        }

        if(((Math.abs(Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y))) > .1) ||
                Math.abs(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4) > .1) {
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            final double FL = r * Math.cos(robotAngle) + rightX;
            final double FR = r * Math.sin(robotAngle) - rightX;
            final double BL = r * Math.sin(robotAngle) + rightX;
            final double BR = r * Math.cos(robotAngle) - rightX;

            Range.clip(FL, -1, 1);
            Range.clip(FR, -1, 1);
            Range.clip(BL, -1, 1);
            Range.clip(BR, -1, 1);

            motorFL.setPower(FL);
            motorFR.setPower(FR);
            motorBL.setPower(BL);
            motorBR.setPower(BR);

        }

        //if none of our motors are running, get the voltage
//        if(motorBL.getPower() == 0 && motorBR.getPower() == 0 && motorFL.getPower() == 0 &&
//                motorFR.getPower() == 0) {
//            voltage = getVoltage();
//        }

        telemetry.update();

    }
}