package com.pFrame.widget.view;

import com.pFrame.creature.Thing;
import com.pFrame.widget.Position;

public interface View {

    public void setViewPosition(Position p);

    public Position getViewPosition();

    public Thing getFocus();

    public void setFocus(Thing thing);

}
