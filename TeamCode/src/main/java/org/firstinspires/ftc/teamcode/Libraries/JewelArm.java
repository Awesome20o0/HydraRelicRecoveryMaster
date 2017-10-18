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

    public SensorRR jewelColor;

    public JewelArm(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        hour = this.opMode.hardwareMap.servo.get("hour");
        // minute = this.opMode.hardwareMap.servo.get("minute");
        this.opMode.telemetry.addData("init", "finished drivetrain init");
        this.opMode.telemetry.update();
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        armIn();
    }

    public void armOut() throws InterruptedException {
        hour.setPosition(.67);
        Thread.sleep(500);
        minute.setPosition(1);
    }

    public void armIn() throws InterruptedException {
        minute.setPosition(0);
        Thread.sleep(100);
        hour.setPosition(0);
    }
}
