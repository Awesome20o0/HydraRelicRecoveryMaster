package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Varun on 9/25/2017.
 */
public class GlyphScorer {
    DcMotor intake;
    Servo outputL;
    Servo outputR;
    Servo elevatorL;
    Servo elevatorR;
    DcMotor liftL;
    DcMotor liftR;
    Servo funnel;
    LinearOpMode opMode;

    private final String LOG_TAG = "DriveTrain";
    public GlyphScorer (LinearOpMode opMode){
        this.opMode = opMode;
        intake = this.opMode.hardwareMap.dcMotor.get("intake");
        outputL = this.opMode.hardwareMap.servo.get("outputL");
        outputR = this.opMode.hardwareMap.servo.get("outputR");
        elevatorL = this.opMode.hardwareMap.servo.get("elevatorL");
        elevatorR = this.opMode.hardwareMap.servo.get("elevatorR");
        liftR = this.opMode.hardwareMap.dcMotor.get("liftR");
        liftL = this.opMode.hardwareMap.dcMotor.get("liftL");
        funnel = this.opMode.hardwareMap.servo.get("funnerl");
        this.opMode.telemetry.addData(LOG_TAG + "init", "finished drivetrain init");
        this.opMode.telemetry.update();
    }

    public void intakeIn(){
        intake.setPower(1);
    }

    public void intakeOut(){
        intake.setPower(-1);
    }

    public void outputOut(){
        outputL.setPosition(1);
        outputR.setPosition(-1);
    }

    public void outputIn(){
        outputL.setPosition(-1);
        outputR.setPosition(1);
    }

    public void elevateUp(){
        elevatorL.setPosition(1);
        elevatorR.setPosition(-1);
    }

    public void elevateDown(){
        elevatorL.setPosition(-1);
        elevatorR.setPosition(1);
    }

    public void liftUp(){
        liftL.setPower(1);
        liftR.setPower(-1);
    }

    public void liftDown(){
        liftL.setPower(-1);
        liftR.setPower(1);
    }

    public void funnelIn(){
        funnel.setPosition(1);
    }

    public void funnelOut(){
        funnel.setPosition(0);
    }
}
