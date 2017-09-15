package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Varun on 9/11/2017.
 */

public class Drivetrain_Mecanum{
    public DcMotor motorBL;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorFR;

    public Sensor sensor;

    LinearOpMode opMode;

    public void resetEncoders() throws InterruptedException {

        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();

    }
    public void movepid(double power, int distance, double floor, double kP, double kI, double kD, int accuracy, double turn, double direction) throws InterruptedException {
        double error;
        double inte = 0;
        double der;

        resetEncoders();

        int startEncoder = Math.abs(motorFL.getCurrentPosition());

        int endEncoder;
    }
    public void move(double pow, double rotation, double direction) {

        final double FL = pow * Math.cos(direction) + rotation;
        final double FR = pow * Math.sin(direction) - rotation;
        final double BL = pow * Math.sin(direction) + rotation;
        final double BR = pow * Math.cos(direction) - rotation;

        motorFL.setPower(FL);
        motorBL.setPower(BL);
        motorBR.setPower(BR);
        motorFR.setPower(FR);
    }

    public void startMotors(double ri, double le) throws InterruptedException {
            motorBL.setPower(le);
            motorFL.setPower(-le);
            motorBR.setPower(-ri);
            motorFR.setPower(ri);
        }
    public void stopMotors() throws InterruptedException {
        motorBR.setPower(0);
        motorBL.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(0);
    }
    public void pid(double power, int angleTo, double floor, double kP, double kI, double kD, int accuracy) throws InterruptedException {

        double error;
        double inte = 0;
        double der;

        double currentAngle = sensor.getGyroYaw();
        double previousError = angleTo - currentAngle;

        opMode.telemetry.addData("Current Angle", currentAngle + "");
        opMode.telemetry.addData("Angle To", angleTo + "");
        opMode.telemetry.update();

        opMode.resetStartTime();

        currentAngle = 0;

        while(Math.abs(currentAngle) < Math.abs(angleTo) - accuracy) {
            currentAngle = sensor.getGyroYaw();
            error = Math.abs(angleTo) - Math.abs(currentAngle);

            power = ( power * (error) * kP) + floor;
            inte += ((opMode.getRuntime()) * error * kI);
            der = (error - previousError) / opMode.getRuntime() * kD;

            power = power + inte + der;

            if(angleTo > 0)
                power *= -1;

            Range.clip(power, -1, 1);
            startMotors(-power, power);

            opMode.telemetry.addData("error", error);
            opMode.telemetry.addData("PID", power);
//            opMode.telemetry.addData("integral", inte);
            opMode.telemetry.addData("integral without error", inte);
            opMode.telemetry.addData("angle", currentAngle);

            opMode.telemetry.update();
            previousError = error;
            opMode.idle();
        }

        opMode.telemetry.update();
        stopMotors();
    }

}

