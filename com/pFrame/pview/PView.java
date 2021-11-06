package com.pFrame.pview;

import com.pFrame.Position;

import game.creature.Thing;

public interface PView {

    public void setViewPosition(Position p);

    public Position getViewPosition();

    public Thing getFocus();

    public void setFocus(Thing thing);

}
