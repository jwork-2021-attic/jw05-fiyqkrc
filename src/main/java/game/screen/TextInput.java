package game.screen;

import com.pFrame.Position;
import com.pFrame.pwidget.PHeadWidget;
import com.pFrame.pwidget.PLabel;
import com.pFrame.pwidget.PWidget;
import org.bytedeco.opencv.presets.opencv_core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TextInput extends PLabel {
    private String inputText = "";
    private Object c;
    private Method m;
    private final String foreText;

    public TextInput(PWidget parent, Position p, String foreText) {
        super(parent, p);
        this.foreText = foreText;
        setText(foreText + ":\n", 1, Color.WHITE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        switch (e.getKeyChar()) {
            case '\n' -> inputFinishedFunc();
            case '\b' -> inputText = inputText.substring(0, inputText.length() - 1);
            default -> inputText += e.getKeyChar();
        }
        setText(foreText + ":\n" + inputText, 1, Color.WHITE);
    }

    private void inputFinishedFunc() {
        try {
            Object[] objects = {inputText};
            m.invoke(c, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setInputFinishFunc(Object c, Method m) {
        this.c = c;
        this.m = m;
    }

}
