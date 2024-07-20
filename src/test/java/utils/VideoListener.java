//package utils;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
//import org.openqa.selenium.support.events.EventFiringWebDriver;
//
//import java.io.IOException;
//
//public class VideoListener extends AbstractWebDriverEventListener {
//    private Process screenRecordingProcess;
//    public void startRecording (EventFiringWebDriver driver){
//        try {
//            String command = "ffmpeg -f x11grab -r 25 -s 1024x768 -i :0.0 -qscale 0 C:\\Users\\jonmi\\Desktop\\output.mp4";
//            screenRecordingProcess = Runtime.getRuntime().exec(command);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void stopRecording () {
//        if (screenRecordingProcess != null) {
//            screenRecordingProcess.destroy();
//        }
//    }
//
//}
