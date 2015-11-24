package guimodule;

import processing.core.PApplet;

/**
 * Created by Mark on 9/27/15.
 */
public class MyDisplay extends PApplet {

    public void setup()
    {
        size(400, 600);
        background(255, 204, 2);
    }

    public void draw()
    {
        fill(255, 255, 0);
        ellipse(200, 200, 390, 390);
        fill(0, 0, 0);

    }

}
