package org.firstinspires.ftc.teamcode.OpenCVTesting;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;



/**
 * Created by Varun on 12/21/2017.
 */

public class ImgWritingTest {

   static {

            //System.loadLibrary("opencv_java3");
    }

    public static void main(String[] args) {

        // Load the openCV core library

       // Instantiate the imgcodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        //Reading the Image from the file and storing it in to a Matrix object
        String file ="C:/Desktop/CVTestingImage";
        Mat matrix = imageCodecs.imread(file);

        System.out.println("Image Loaded ..........");
        String file2 = "C:/Desktop/newCVTestingImage";

        //Writing the image from file one to file two
        imageCodecs.imwrite(file2, matrix);
        System.out.println("Image Saved ............");
    }
}
