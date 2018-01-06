import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Created by Varun on 12/23/2017.
 */

public class test {
    public static void main(String[] args) {

        // Load the openCV core library
//
        System.loadLibrary("opencv_java3");


        // Instantiate the imgcodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        //Reading the Image from the file and storing it in to a Matrix object
        String file ="C:/Desktop/CVTestingImage";
        Mat matrix = Imgcodecs.imread(file);

        System.out.println("Image Loaded ..........");
        String file2 = "C:/Desktop/newCVTestingImage";

        //Writing the image from file one to file two
        Imgcodecs.imwrite(file2, matrix);
        System.out.println("Image Saved ............");
    }
}
