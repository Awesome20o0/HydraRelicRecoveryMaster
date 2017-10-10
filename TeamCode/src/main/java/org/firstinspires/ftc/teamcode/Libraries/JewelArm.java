package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Varun on 9/25/2017.
 */

public final class JewelArm {
    private final LinearOpMode opMode;
    Servo jewelHour;
    Servo jewelMinute;

    public SensorRR jewelColor;

    public JewelArm(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        jewelHour = this.opMode.hardwareMap.servo.get("jewelHour");
        jewelMinute = this.opMode.hardwareMap.servo.get("jewelMinute");
        this.opMode.telemetry.addData("init", "finished drivetrain init");
        this.opMode.telemetry.update();
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        armIn();
    }

    public void armOut() throws InterruptedException {
        jewelHour.setPosition(.67);
        Thread.sleep(500);
        jewelMinute.setPosition(1);
    }

    public void armIn() throws InterruptedException {
        jewelMinute.setPosition(0);
        Thread.sleep(100);
        jewelHour.setPosition(0);
    }
}
