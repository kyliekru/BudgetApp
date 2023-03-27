package ui.graphics;

import java.awt.*;

//Represents a logo canvas, using a png
public class LogoCanvas extends Canvas {

    //MODIFIES: this
    //EFFECTS: draw image on panel
    public void paint(Graphics g) {

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("ProjectLogo.png");
        g.drawImage(i, 120,100,this);

    }
}
