package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Varun on 9/2/2017.
 */
@TeleOp(name = "PSTeleOp", group = "opMode")
public class PapaSmurfTeleOp extends PapaSmurfOpMode {

    //Used to keep track of mode
    boolean endGame = false;
    boolean tank = false;

    double slowingFactor = 1;


    @Override
    public void loop() {

        //Changes endGame boolean on button press
        if (gamepad2.back)
            endGame = !endGame;

        if (gamepad1.left_stick_button) {
            slowingFactor = .5;
        } else {
            slowingFactor = 1;
        }

        if (gamepad1.b)
            tank = !tank;

        if (!tank) {
            //Code for mecanum drive when not reversed
            if (((Math.abs(Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y))) > .1) ||
                    Math.abs(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4) > .1) {
                double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = -gamepad1.right_stick_x;
                double FL = 1.3 * (r * Math.cos(robotAngle) + rightX);
                double FR = 1.3 * (r * Math.sin(robotAngle) - rightX);
                double BL = 1.3 * (r * Math.sin(robotAngle) + rightX);
                double BR = 1.3 * (r * Math.cos(robotAngle) - rightX);


                if (((Math.abs(FL) > 1) || (Math.abs(BL) > 1)) || ((Math.abs(FR) > 1) || (Math.abs(BR) > 1))) {
                    FL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                    BL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                    FR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                    BR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
                }

                motorFL.setPower(FL * slowingFactor);
                motorFR.setPower(-FR * slowingFactor);
                motorBL.setPower(BL * slowingFactor);
                motorBR.setPower(-BR * slowingFactor);
            } else {
                stopMotors();
            }
        }
//        if (reversed && !tank) {
//            //Code for mecanum drive when reversed
//            if (((Math.abs(Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y))) > .1) ||
//                    Math.abs(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4) > .1) {
//                double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
//                double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
//                double rightX = -gamepad1.right_stick_x;
//                double FL = r * Math.cos(robotAngle) + rightX;
//                double FR = r * Math.sin(robotAngle) - rightX;
//                double BL = r * Math.sin(robotAngle) + rightX;
//                double BR = r * Math.cos(robotAngle) - rightX;
//
//
//                if (((Math.abs(FL) > 1) || (Math.abs(BL) > 1)) || ((Math.abs(FR) > 1) || (Math.abs(BR) > 1))) {
//                    FL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
//                    BL /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
//                    FR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
//                    BR /= Math.max(Math.max(Math.abs(FL), Math.abs(FR)), Math.max(Math.abs(BL), Math.abs(BR)));
//                }
//
//                motorFL.setPower(-FL * slowingFactor);
//                motorFR.setPower(FR * slowingFactor);
//                motorBL.setPower(-BL * slowingFactor);
//                motorBR.setPower(BR * slowingFactor);
//
//            } else {
//                stopMotors();
//            }
//        }


        if (tank) {

            if (Math.abs(gamepad1.right_stick_x) > .1 && (Math.abs(gamepad1.left_stick_x) > .1)) {

                motorFL.setPower(((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
                motorFR.setPower(((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
                motorBL.setPower(-((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
                motorBR.setPower(-((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);

            } else if (Math.abs(gamepad1.right_stick_y) > .05 || (Math.abs(gamepad1.left_stick_y) > .05)) {
                startMotors(gamepad1.right_stick_y, gamepad1.left_stick_y);
            } else {
                stopMotors();
            }
        }
//        if (tank) {
//
//            if (Math.abs(gamepad1.right_stick_x) > .3 && (Math.abs(gamepad1.left_stick_x) > .3)) {
//
//                motorFL.setPower(((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
//                motorFR.setPower(((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
//                motorBL.setPower(-((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
//                motorBR.setPower(-((gamepad1.right_stick_x + gamepad1.left_stick_x) / 2.0) * slowingFactor);
//
//            } else if (Math.abs(gamepad1.right_stick_y) > .05 || (Math.abs(gamepad1.left_stick_y) > .05)) {
//                startMotors(-gamepad1.right_stick_y * slowingFactor, -gamepad1.left_stick_y * slowingFactor);
//            } else {
//                stopMotors();
//            }
//        }

        if (gamepad2.right_bumper && gamepad2.left_bumper) {
            intakeOut();
        } else if (gamepad2.right_bumper) {
            intakeIn();
        } else if (gamepad2.left_bumper) {
            intakeStop();
        }

        if (gamepad2.a) {
            intakeSpin();
        }

        if (gamepad1.left_bumper) {
            pushersIn();
        }

        if (gamepad1.right_bumper) {
            pushersOut();
        }

//        if (gamepad1.a && reversed) {
//
//            reverse();
//        }

        //Only run if not in endGame
        if (!endGame) {

//            if (gamepad1.dpad_down) {
//                try {
//                    balance();
//                } catch (InterruptedException e) {
//                }
//            }

            if (gamepad1.dpad_left){
                pushersOut();
                move(.8, 0, 3*Math.PI/2);
                try {
                    Thread.sleep(1650);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    stopMotors();
                }

            }

            if (gamepad1.dpad_right){
                pushersOut();
                move(.8, 0, 3*Math.PI/2);
                try {
                    Thread.sleep(1850);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    stopMotors();
                }

            }

            if (gamepad2.dpad_down) {
                hourAndGate();
            }

            if (gamepad1.left_trigger > .1 && gamepad1.right_trigger > .1) {
                outputIn(.85);
            } else if (gamepad1.right_trigger > .1) {
                outputOut(.85);
            } else if (gamepad1.left_trigger > .1) {
                stopOutput();
            }

            if (gamepad2.x) {
                omnipUp();

            }

            if (gamepad2.b) {

                omnipDown();
            }

            if (!(gamepad2.x || gamepad2.b))
                omnipStop();


            if (gamepad2.right_trigger > .1) {
                liftUp(gamepad2.right_trigger);
            }

            if (!(gamepad2.right_trigger > .1 || gamepad2.left_trigger > .1) ){
                liftStop();
            }

            if (gamepad2.left_trigger > .1) {

                liftDown(gamepad2.left_trigger/2);
            }

        } else {
            if (gamepad2.right_trigger > 0.1) {
                try {
                    relicOut();
                } catch (InterruptedException e) {
                }
            }

            if (gamepad2.left_trigger > 0.1) {
                try {
                    relicIn();
                } catch (InterruptedException e) {
                }
            }


        }




        //if none of our motors are running, get the voltage
        if (motorBL.getPower() == 0 && motorBR.getPower() == 0 && motorFL.getPower() == 0 &&
                motorFR.getPower() == 0) {
            // voltage = getVoltage();
        }

        telemetry.update();
    }
}
