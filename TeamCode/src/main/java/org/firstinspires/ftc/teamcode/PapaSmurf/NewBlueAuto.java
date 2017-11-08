package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Libraries.Drivetrain_Mecanum;
import org.firstinspires.ftc.teamcode.Libraries.GlyphScorer;
import org.firstinspires.ftc.teamcode.Libraries.JewelArm;
import org.firstinspires.ftc.teamcode.Libraries.SensorRR;

/**
 * Created by willi on 11/6/2017.
 */
@Autonomous(name = "New Blue Auto", group = "LinearOpMode")
public class NewBlueAuto extends LinearOpMode {
    private GlyphScorer glyphScorer;
    private Drivetrain_Mecanum drivetrainM;
    private String version;
    private SensorRR sensors;
    private JewelArm arm;
    public static final String TAG = "Vuforia VuMark Test";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    int vu;

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrainM = new Drivetrain_Mecanum(this);
        glyphScorer = new GlyphScorer(this);
        sensors = new SensorRR(this);
        arm = new JewelArm(this);

        composeTelemetry();

        // Start the actual process of looping
        waitForStart();

        // Move omnipulator up to prevent it from hitting the balancing stone
        glyphScorer.liftUp();
        glyphScorer.liftStop();

        // Knock off correct jewel ball
        arm.armOut();

        Thread.sleep(500);

        int color = sensors.getColorValue();
        if (color < 0) {
            // Move servo clockwise
        } else {
            // Move servo counter clockwise
        }

        Thread.sleep(500);

        arm.armIn();

        // Move off of the balancing stone
        drivetrainM.movepid(.3, 1400, .1, .0009, .00025, 0, 25, 0, Math.PI * 3/2, 5000);

        Thread.sleep(500);

        // Turn 90 degrees towards cryptobox
        drivetrainM.pid(.5, -90, .1, .0009, .00025, 0, 1, 5000);

        Thread.sleep(500);

        // Drive towards cryptobox
        drivetrainM.movepid(.5, 1000, .1, .0009, .00025, 0, 25, Math.PI * 3/2, 5000);

        Thread.sleep(500);

        // Strafe depending on Vuforia reading
        if (vu == 1) {
            while(sensors.getDistanceL() > 55 ){
                drivetrainM.strafe(.5, .5, Math.PI );
            }
            drivetrainM.stopMotors();
        }
        if (vu == 2) {
            while(sensors.getDistanceL() > 40 ){
                drivetrainM.strafe(.5, .5, Math.PI );
            }
            drivetrainM.stopMotors();
        }
        if (vu == 3) {
            while(sensors.getDistanceL() > 25 ){
                drivetrainM.strafe(.5, .5, Math.PI );
            }
            drivetrainM.stopMotors();
        }

        Thread.sleep(500);

        // Move forward and deposit
        drivetrainM.movepid(.5, 200, .1, .0009, .00025, 0, 10, Math.PI * 3/2, 3000);

        glyphScorer.outputOut();

        Thread.sleep(500);

        // Back up to park
        drivetrainM.movepid(.5, 200, .1, .0009, .00025, 0, 10, Math.PI/2, 3000);

        glyphScorer.stopOutput();
    }



    private void composeTelemetry() {
        telemetry.addLine()
                .addData("Avg", new Func<String>() {
                    @Override public String value() {
                        return "avg: " + drivetrainM.getEncoderAvg();
                    }
                });
        telemetry.addLine()
                .addData("gyroYaw", new Func<String>() {
                    @Override public String value() {
                        return "gyro yaw: " + drivetrainM.sensor.getGyroYaw();
                    }
                });
        telemetry.addLine()
                .addData("Color", new Func<String>() {
                    @Override public String value() {
                        return "Color: " + sensors.getColorValue();
                    }
                });
        telemetry.addLine()
                .addData("gyroPitch", new Func<String>() {
                    @Override public String value() {
                        return "gyro pitch: " + drivetrainM.sensor.getGyroPitch();
                    }
                });
        telemetry.addLine()
                .addData("gyroRoll", new Func<String>() {
                    @Override public String value() {
                        return "gyro roll: " + drivetrainM.sensor.getGyroRoll();
                    }
                });
    }

    public void getVuMark() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AYPVi+D/////AAAAGWcdhlX" +
                "rGkdFvb06tBr5+AFjSDfw/YB3Am9Am/B21oh9Jy6CyrZzHhH1A7s" +
                "sJo723Ha+8w0KNhmv38iW3hieiGS3ww/zbK7RgfMDhlAN5Ky/BZ2" +
                "s2NUfKLIt32e9E6O23jOumaRs1Tw6BrIpfi0HnCjUwmkVi/Jd2FXUT" +
                "vWOCPRiJ+Sm7J10sdb4612yzZnx/GpwnFsT9AtKamYqDzHs4CYDXlBJXe" +
                "tnon03SnnZjUxK/8NYbFRRIgKE+N/u3qCwSzus8GJkfwPbxMok9xIWwz" +
                "rDnko2yiKqYb5wZlmZBYI722gR6IOmK8qlGJ+f+stBPQyseR7Q468By8u6" +
                "WcucjveY3gVWh3uGbmzRE0BUTNkV";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        //telemetry.addData(">", "Press Play to start");
        relicTrackables.activate();

        // copy pasta from the ftc ppl
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        ElapsedTime times = new ElapsedTime();
        telemetry.addData("VuMark ", vuMark);
        times.reset();

        while (vuMark == RelicRecoveryVuMark.UNKNOWN && times.seconds() < 2 && opModeIsActive()) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }
        telemetry.addData("VuMark ", vuMark);

        int vu = 1;

        if (vuMark == RelicRecoveryVuMark.RIGHT) {
            vu = 1;
        } else if (vuMark == RelicRecoveryVuMark.CENTER) {
            vu = 2;
        } else if (vuMark == RelicRecoveryVuMark.LEFT) {
            vu = 3;
        }
    }


    }



