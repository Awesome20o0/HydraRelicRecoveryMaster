package org.firstinspires.ftc.teamcode.PapaSmurf;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Varun on 9/10/2017.
 */

@Autonomous(name = "Red Autonomous", group = "LinearOpMode")
public class RedAuto extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Test";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize the camera and tell it which camera to use
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // My vuforia license key necessary to use the opMode
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

        telemetry.addData(">", "Press Play to start");
        telemetry.update();


        // Start the actual process of looping
        waitForStart();

        // Activate the process of searching for the image -- like we saw in the video
        relicTrackables.activate();


        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                // If find VuMark, display which one is found
                telemetry.addData("VuMark", "%s visible", vuMark);

            }
            else {
                // Display this until VuMark is found
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }
    }

    // Don't actually know what this is but just copy pasting for now
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}