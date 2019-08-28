package org.usfirst.frc.team5436.sensors;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;
import org.opencv.core.Mat;
import org.usfirst.frc.team5436.functions.Gamepad;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

public class camera {    	

public static void cameraPoo() {	
	
Thread t = new Thread(() -> {
    		
    		boolean allowCam1 = false;
    		
    		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
            camera1.setResolution(320, 240);
            camera1.setFPS(30);
            UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
            camera2.setResolution(320, 240);
            camera2.setFPS(30);
            
            CvSink cvSink1 = CameraServer.getInstance().getVideo(camera1);
            CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
            CvSource outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240);
            
            
            while(!Thread.interrupted()) {
            	
            	if(Gamepad.primary.getRB()) {
            		allowCam1 = !allowCam1;
            	}
            	
                if(allowCam1){
                  cvSink2.setEnabled(false);
                  cvSink1.setEnabled(true);
                  cvSink1.grabFrame(image);
                } else{
                  cvSink1.setEnabled(false);
                  cvSink2.setEnabled(true);
                  cvSink2.grabFrame(image);     
                }
                
                outputStream.putFrame(image);
            }            
} );

}

}