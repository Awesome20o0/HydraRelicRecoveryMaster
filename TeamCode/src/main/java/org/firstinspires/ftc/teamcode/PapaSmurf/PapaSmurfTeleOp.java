package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Libraries.GlyphScorer;

/**
 * Created by Varun on 9/2/2017.
 */
@TeleOp(name = "PSTeleOp", group = "opMode")
public class PapaSmurfTeleOp extends PapaSmurfOpMode {
    private GlyphScorer glyph;
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
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            double FL = r * Math.cos(robotAngle) + rightX;
            double FR = r * Math.sin(robotAngle) - rightX;
            double BL = r * Math.sin(robotAngle) + rightX;
            double BR = r * Math.cos(robotAngle) - rightX;


            if(((Math.abs(FL) > 1) || (Math.abs(BL) > 1 )) || ((Math.abs(FR) > 1) || (Math.abs(BR) > 1 ))) {
                FL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                BL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                FR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                BR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
            }

            motorFL.setPower(FL);
            motorFR.setPower(-FR);
            motorBL.setPower(BL);
            motorBR.setPower(-BR);
        }

        if (gamepad1.right_bumper)
        {
            glyph.intakeIn();
        }

        if (gamepad1.left_bumper)
        {
            glyph.intakeOut();
        }

        if (gamepad1.y){
            glyph.funnelIn();
        }

        if (gamepad1.a){
            glyph.funnelOut();
        }

        if (gamepad2.b) {
            reverse();
            while (gamepad1.b);
        }

        if (gamepad2.left_bumper)
        {
            glyph.elevateUp();
        }

        if (gamepad2.left_trigger>0)
        {
            glyph.elevateDown();
        }

        if (gamepad2.right_bumper)
        {
            glyph.liftUp();
        }

        if (gamepad2.right_trigger>0)
        {
            glyph.liftDown();
        }

        if (gamepad2.x)
        {
            glyph.outputOut();
        }

        if (gamepad2.b)
        {
            glyph.outputIn();
        }
        //if none of our motors are running, get the voltage
//        if(motorBL.getPower() == 0 && motorBR.getPower() == 0 && motorFL.getPower() == 0 &&
//                motorFR.getPower() == 0) {
//            voltage = getVoltage();
//        }

        telemetry.update();

    }


}