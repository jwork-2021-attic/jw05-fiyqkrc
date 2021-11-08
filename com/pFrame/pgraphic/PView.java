package com.pFrame.pgraphic;

import com.pFrame.Position;

public interface PView {

    public void setViewPosition(Position p);

    public Position getViewPosition();

    public PGraphicItem getFocus();

    public void setFocus(PGraphicItem thing);

}
