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

    int nullValue;
    LinearOpMode opMode;

    public Drivetrain_Mecanum(LinearOpMode opMode)throws InterruptedException {
        this.opMode = opMode;
        nullValue = 0;
        motorBL = this.opMode.hardwareMap.dcMotor.get("BL");
        motorBR = this.opMode.hardwareMap.dcMotor.get("BR");
        motorFL = this.opMode.hardwareMap.dcMotor.get("FL");
        motorFR = this.opMode.hardwareMap.dcMotor.get("FR");
        this.opMode.telemetry.addData("init", "finished drivetrain init");
        this.opMode.telemetry.update();
        sensor = new Sensor(opMode);
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
    }

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

    public int getEncoderAvg() {
        return ((Math.abs(motorBR.getCurrentPosition())) +
                Math.abs(motorBL.getCurrentPosition()) +
                (Math.abs(motorFR.getCurrentPosition())) +
                Math.abs(motorFL.getCurrentPosition())) / 4;
    }



    public void movepid(double power, int distance, double floor, double kP, double kI, double kD, int accuracy, double rotation, double direction) throws InterruptedException {
        double error;
        double inte = 0;
        double der;
        double previousRunTime;

        resetEncoders();

        double previousError = distance - getEncoderAvg();

        opMode.telemetry.addData("distance left", distance + "");
        opMode.telemetry.addData("current Encoder", getEncoderAvg() + "");
        opMode.telemetry.update();

        opMode.resetStartTime();

        while(getEncoderAvg() < (distance - accuracy)) {
            error = Math.abs(distance) - Math.abs(getEncoderAvg());
            previousRunTime = opMode.getRuntime();
            power = (power * (error) * kP) + floor;
            inte += ((opMode.getRuntime()) * error * kI);
            der = (error - previousError) / (opMode.getRuntime() - previousRunTime) * kD;

            power = power + inte + der;

            Range.clip(power, -1, 1);
            move(power, rotation, direction);

            opMode.telemetry.addData("error", error);
            opMode.telemetry.addData("PID", power);
//            opMode.telemetry.addData("integral", inte);
            opMode.telemetry.addData("integral", inte);
            opMode.telemetry.addData("Encoder", getEncoderAvg());

            opMode.telemetry.update();
            previousError = error;
            opMode.idle();
        }

        opMode.telemetry.update();
        stopMotors();
    }

    public void move(double pow, double rotation, double direction) {

        final double FL = pow * Math.cos(direction - Math.PI/4) + rotation;
        final double FR = pow * Math.sin(direction - Math.PI/4) - rotation;
        final double BL = pow * Math.sin(direction - Math.PI/4) + rotation;
        final double BR = pow * Math.cos(direction - Math.PI/4) - rotation;

        motorFL.setPower(FL);
        motorBL.setPower(BL);
        motorBR.setPower(BR);
        motorFR.setPower(FR);
    }

    public void move(double pow, double rotation, double direction, int encoder) {

        final double FL = pow * Math.cos(direction - Math.PI/4) + rotation;
        final double FR = pow * Math.sin(direction - Math.PI/4) - rotation;
        final double BL = pow * Math.sin(direction - Math.PI/4) + rotation;
        final double BR = pow * Math.cos(direction - Math.PI/4) - rotation;

        while (getEncoderAvg() < encoder) {
            motorFL.setPower(FL);
            motorBL.setPower(BL);
            motorBR.setPower(BR);
            motorFR.setPower(FR);
        }
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

