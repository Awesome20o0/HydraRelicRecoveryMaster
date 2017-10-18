package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Varun on 9/25/2017.
 */
public class GlyphScorer {
    DcMotor intakeL;
    DcMotor intakeR;
//    DcMotor lift;
    Servo outputL;
    Servo outputR;
    DcMotor lift;
    Servo gate;
    LinearOpMode opMode;
    DcMotor relic;


    private final String LOG_TAG = "DriveTrain";
    public GlyphScorer (LinearOpMode opMode){
        this.opMode = opMode;
        intakeL = this.opMode.hardwareMap.dcMotor.get("intakeL");
        outputL = this.opMode.hardwareMap.servo.get("outputL");
        outputR = this.opMode.hardwareMap.servo.get("outputR");
//        liftL = this.opMode.hardwareMap.servo.get("liftL");
//        liftR = this.opMode.hardwareMap.servo.get("liftR");
        intakeR = this.opMode.hardwareMap.dcMotor.get("intakeR");
        lift = this.opMode.hardwareMap.dcMotor.get("lift");
        relic = this.opMode.hardwareMap.dcMotor.get("relic");
//        lift = this.opMode.hardwareMap.dcMotor.get("lift");
//        gate = this.opMode.hardwareMap.servo.get("gate");
        this.opMode.telemetry.addData(LOG_TAG + "init", "finished drivetrain init");
        this.opMode.telemetry.update();
    }

    public void intakeIn(double power){
        intakeL.setPower(power);
        intakeR.setPower(-power);
    }

    public void intakeOut(double power){
        intakeL.setPower(-power);
        intakeR.setPower(power);
    }

    public void intakeSpin(){
        intakeL.setPower(-1);
        intakeR.setPower(-1);
    }


    public void outputOut(){
        outputL.setPosition(1);
        outputR.setPosition(-1);
    }

    public void outputIn(){
        outputL.setPosition(-1);
        outputR.setPosition(1);
    }

    public void stopOutput(){
        outputL.setPosition(0);
        outputR.setPosition(0);
    }

    public void liftUp() throws InterruptedException {
        lift.setPower(1);
        Thread.sleep(1000);
        lift.setPower(0);
    }

    public void liftDown() throws InterruptedException {
        lift.setPower(-1);
        Thread.sleep(1000);
        lift.setPower(0);
    }

    public void relicOut() throws InterruptedException {
        relic.setPower(1);
        Thread.sleep(2000);
        relic.setPower(0);
    }

    public void relicIn() throws InterruptedException {
        relic.setPower(-1);
        Thread.sleep(2000);
        relic.setPower(0);
    }

//    public void liftUp(){
//        lift.setPower(1);
//    }
//
//    public void liftDown(){
//        lift.setPower(-1);
//    }

    public void funnelIn(){
        gate.setPosition(1);
    }

    public void funnelOut(){
        gate.setPosition(0);
    }
}
