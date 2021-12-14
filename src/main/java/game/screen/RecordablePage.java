package game.screen;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pwidget.PLayout;
import com.pFrame.pwidget.PWidget;
import game.Config;
import log.Log;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class RecordablePage extends PLayout {

    protected String path;
    protected Pixel[][] lastFrame;
    protected ArrayList<Pixel[][]> queue = new ArrayList<>();
    protected RecordingProcess recordingProcess;


    public RecordablePage(PWidget parent, Position p, int rownum, int columnnum, boolean Inset) {
        super(parent, p, rownum, columnnum, Inset);
        path = Config.DataPath;
        recordingProcess=null;
    }

    public boolean isRecording(){
        return recordingProcess!=null;
    }

    public void startRecord() {
        if(recordingProcess==null) {
            recordingProcess = new RecordingProcess();
            recordingProcess.start();
        }else{
            Log.WarningLog(this,"recording is processing,you should stop recording first");
        }
    }

    public void finishRecord() {
        if(recordingProcess!=null) {
            recordingProcess.interrupt();
            recordingProcess=null;
        }
        else{
            Log.ErrorLog(this,"recording is not processing!!!");
        }
    }

    public void getScreenShot() {
        BufferedImage image = Pixel.toBufferedImage(lastFrame);
        try {
            ImageIO.write(image, "png", new FileOutputStream(new File(path+"/"+new Date().getTime()+".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOutPutPath(String path) {
        this.path = path;
    }

    @Override
    public Pixel[][] displayOutput() {
        Pixel[][] pixel = super.displayOutput();
        lastFrame=pixel;
        return pixel;
    }

    class RecordingProcess extends Thread{
        @Override
        public void run() {
            super.run();
            while(!interrupted()){
                try {
                    queue.add(lastFrame);
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
            try {
                createMP4();
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createMP4() throws FrameRecorder.Exception {
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(path+"/"+new Date().getTime()+".mp4", widgetWidth-widgetWidth%2, widgetHeight-widgetHeight%2);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFrameRate(60);
        recorder.setFormat("mp4");
        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            for(Pixel[][] pixels:queue) {
                BufferedImage image=Pixel.toBufferedImage(pixels);
                BufferedImage copyImage=new BufferedImage(image.getWidth()-image.getWidth()%2,image.getHeight()-image.getHeight()%2,BufferedImage.TYPE_3BYTE_BGR);
                copyImage.getGraphics().drawImage(image,0,0,null);
                recorder.record(converter.getFrame(copyImage));
            }
            queue.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recorder.stop();
            recorder.release();
        }
    }
}

