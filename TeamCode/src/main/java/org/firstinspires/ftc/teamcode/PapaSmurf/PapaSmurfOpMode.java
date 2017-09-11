package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


/**
 * Created by Varun on 9/2/2017.
 */

public abstract class PapaSmurfOpMode extends OpMode {
    // Define all motors, servos, sensors, etc.
    DcMotor motorFL;
    DcMotor motorBL;
    DcMotor motorFR;
    DcMotor motorBR;

    PapaSmurfOpMode opMode;

    public BNO055IMU gyro;
    private Orientation angles;
    private Acceleration gravity;
    private BNO055IMU.Parameters parameters;

    // To monitor current voltage
    double voltage = 0.0;

    //For reversing which side is "forward" during the match
    boolean reversed;

    //To be accessed by the actual opMode
    double slowingFactor;
    double powerL;
    double powerR;

    @Override
    public void init() {
        opMode = this;
        composeTelemetry();
        motorBL = hardwareMap.dcMotor.get("BL");
        motorBR = hardwareMap.dcMotor.get("BR");
        motorFR = hardwareMap.dcMotor.get("FR");
        motorFL = hardwareMap.dcMotor.get("FL");
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void reverse() {
        reversed = !reversed;
    }

    public void startMotors(double r, double l) {
        if(reversed) {
            motorFL.setPower(l * slowingFactor);
            motorBL.setPower(l * slowingFactor);
            motorFR.setPower(-r * slowingFactor);
            motorBR.setPower(-r * slowingFactor);
        } else {
            motorFL.setPower(-l * slowingFactor);
            motorBL.setPower(-l * slowingFactor);
            motorFR.setPower(r * slowingFactor);
            motorBR.setPower(r * slowingFactor);
        }
    }

    public void startMotorsHalf(double r, double l) {
        if (reversed) {
            motorFL.setPower(l * .5 * slowingFactor);
            motorBL.setPower(l * .5 * slowingFactor);
            motorFR.setPower(-r * .5 * slowingFactor);
            motorBR.setPower(-r * .5 * slowingFactor);
        } else {
            motorFL.setPower(-l * .5 * slowingFactor);
            motorBL.setPower(-l * .5 * slowingFactor);
            motorFR.setPower(r * .5 * slowingFactor);
            motorBR.setPower(r * .5 * slowingFactor);
        }
    }

    public void stopMotors() {
        motorBL.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorFR.setPower(0);
    }

    // Encoder Stuff
    public int getEncoderAvg() {

        // Throws out any bad encoders in attempt to keep accurate average
        int numZeros = 0;
        if (motorBR.getCurrentPosition() == -1) {
            numZeros++;
        }
        if (motorFR.getCurrentPosition() == -1) {
            numZeros++;
        }
        if (motorBL.getCurrentPosition() == -1) {
            numZeros++;
        }
        if (motorFL.getCurrentPosition() == -1) {
            numZeros++;
        }
        return (Math.abs(motorBR.getCurrentPosition()) + Math.abs(motorBL.getCurrentPosition()) +
                Math.abs(motorFR.getCurrentPosition()) + Math.abs(motorFL.getCurrentPosition())) / (4 - numZeros);
    }

    public int getRightEncoderAvg() {
        return (Math.abs(motorFR.getCurrentPosition()) + Math.abs(motorBR.getCurrentPosition())) / 2;
    }

    public int getLeftEncoderAvg() {
        return (Math.abs(motorFL.getCurrentPosition()) + Math.abs(motorBL.getCurrentPosition())) / 2;
    }


    // Gyro Stuff
    private void updateValues() {
        angles = gyro.getAngularOrientation();
    }

    public double getGyroYaw() {
        updateValues();
        double value = angles.firstAngle * -1;
        if(angles.firstAngle < -180)
            value -= 360;
        return value;
    }

    public double getVoltage() {
        return hardwareMap.voltageSensor.get("Motor Controller 5").getVoltage();
    }

    private void composeTelemetry() {

        telemetry.addLine()
                .addData("voltage", new Func<String>() {
                    @Override public String value() {
                        return "voltage: " + voltage;
                    }
                });
        telemetry.addLine()
                .addData("BL", new Func<String>() {
                    @Override public String value() {
                        return "BL: " + motorBL.getCurrentPosition();
                    }
                });
        telemetry.addLine()
                .addData("BR", new Func<String>() {
                    @Override public String value() {
                        return "BR: " + motorBR.getCurrentPosition();
                    }
                });
        telemetry.addLine()
                .addData("FL", new Func<String>() {
                    @Override public String value() {
                        return "FL: " + motorFL.getCurrentPosition();
                    }
                });
        telemetry.addLine()
                .addData("FR", new Func<String>() {
                    @Override public String value() {
                        return "FR: " + motorFR.getCurrentPosition();
                    }
                });
    }
}