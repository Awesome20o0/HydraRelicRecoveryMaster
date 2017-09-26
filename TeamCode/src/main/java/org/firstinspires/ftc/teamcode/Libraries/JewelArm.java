package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Varun on 9/25/2017.
 */

public final class JewelArm {
    private final LinearOpMode opMode;
    Servo jewelArm;
    Servo jewelHinge;

    public SensorRR jewelColor;

    public JewelArm(LinearOpMode opMode)throws InterruptedException {

        this.opMode = opMode;
        jewelArm = this.opMode.hardwareMap.servo.get("jewelArm");
        jewelHinge = this.opMode.hardwareMap.servo.get("jewelHinge");
        this.opMode.telemetry.addData("init", "finished drivetrain init");
        this.opMode.telemetry.update();
        this.opMode.telemetry.addData("init", "init finished");
        this.opMode.telemetry.update();
        armIn();
    }

    public void armOut() throws InterruptedException {
        jewelArm.setPosition(.67);
        Thread.sleep(500);
        jewelHinge.setPosition(1);
    }

    public void armIn() throws InterruptedException {
        jewelHinge.setPosition(0);
        Thread.sleep(100);
        jewelArm.setPosition(0);
    }
}
