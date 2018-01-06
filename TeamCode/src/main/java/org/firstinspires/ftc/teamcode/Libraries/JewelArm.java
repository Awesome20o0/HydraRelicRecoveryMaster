package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Varun on 9/25/2017.
 */

public class JewelArm {
    private final LinearOpMode opMode;
    Servo hour;
    Servo minute;
    Servo second;

    public SensorRR jewelColor;

    public JewelArm(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        hour = this.opMode.hardwareMap.servo.get("hour");
//        minute = this.opMode.hardwareMap.servo.get("minute");
        second = this.opMode.hardwareMap.servo.get("second");
        this.opMode.telemetry.addData("init", "finished drivetrain init");
        this.opMode.telemetry.update();
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        armIn();
    }

    public void armOut() throws InterruptedException {
        //closer to -1 = ?
        //closer to 1 = closer to floor

//        minute.setPosition(.15);
//
//        Thread.sleep(100);
        //closer to -1 = point up to sky
        //closer to 1 = ?
        hour.setPosition(.83);
        Thread.sleep(400);

    }

    public void armKick(double position) throws InterruptedException {
        second.setPosition(position);
        Thread.sleep(400);
        second.setPosition(.42);
    }

    public void armIn() throws InterruptedException {
        hour.setPosition(-1);
        Thread.sleep(200);
        second.setPosition(.42);
    }

    public void armUp() throws InterruptedException {
        armIn();
    }
}
