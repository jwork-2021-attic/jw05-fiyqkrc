package game.screen;

import com.pFrame.PTimer;
import com.pFrame.PTimerTask;
import com.pFrame.Position;
import com.pFrame.pwidget.PButton;
import com.pFrame.pwidget.PWidget;

import java.awt.*;



public class MessageLabel extends PButton {

    public MessageLabel(PWidget parent, Position p) {
        super(parent, p);
        setText("",1, Color.ORANGE);
    }

    public void sendMessage(String string,int time){
        setText(string,1,Color.ORANGE);
        PTimer timer=new PTimer();
        stopMessage stopFunc=new stopMessage(this);
        timer.schedule(stopFunc,false,time);
        Thread thread=new Thread(timer);
        thread.start();
    }
}

class stopMessage implements PTimerTask{
    public MessageLabel messageLabel;

    public stopMessage(MessageLabel messageLabel){
        this.messageLabel=messageLabel;
    }

    @Override
    public void doTask() {
        messageLabel.setText("",1,Color.ORANGE);
    }
}
