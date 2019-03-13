import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Torso {
    private Head head;
    private Limb upperArmL, upperArmR, upperLegL, upperLegR;
    private Limb lowerArmL, lowerArmR, lowerLegL, lowerLegR;
    private Limb handL, handR, footL, footR;
    private Rectangle2D m;
    private boolean focused = false;
    private double offsetX, offsetY;
    private double beginX, beginY;



    public boolean isFocused() {
        return focused;
    }

    Torso(Rectangle2D rec) {m = rec;}

    Torso(int x, int y, int w, int h){
        m = new Rectangle2D.Double(x, y, w, h);
        head = new Head(w/2, -50, this);
        upperArmL = new Limb(-30,10, 30, 100, 30, 0, true, 360);
        upperArmR = new Limb(w, 10,30, 100, 0, 0, true, 360);
        upperLegL = new Limb(0, h,30, 100, 15, 0, true, 90);
        upperLegR = new Limb(w-30, h,30, 100, 15, 0, true,90);
        lowerArmL = new Limb(0,100,30, 100, 15, 0, true,135);
        lowerArmR = new Limb(0,100,30, 100, 15, 0, true,135);
        lowerLegL = new Limb(0,100, 30, 100, 15, 0, true,90);
        lowerLegR = new Limb(0,100,30, 100, 15, 0, true,90);
        handL = new Limb(0,100,30, 40, 15, 0, false,35);
        handR = new Limb(0,100,30, 40, 15, 0, false,35);
        footL = new Limb(-10,100, 40, 30, 25, 0, false,35);
        footR = new Limb(0, 100, 40, 30, 15, 0, false,35);
        upperArmL.attach(lowerArmL);
        upperArmR.attach(lowerArmR);
        upperLegL.attach(lowerLegL);
        upperLegR.attach(lowerLegR);
        lowerArmL.attach(handL);
        lowerArmR.attach(handR);
        lowerLegL.attach(footL);
        lowerLegR.attach(footR);
    }

    // suppose to be in main


    public void paint(Graphics g){
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.draw(new RoundRectangle2D.Double((int)m.getX(), (int)m.getY(),
                (int)m.getWidth(), (int)m.getHeight(), 30, 30));
        var af = graphics2D.getTransform();
        af.translate(m.getX(), m.getY());
        graphics2D.setTransform(af);
        head.paint(g);
        upperArmL.paint(g);
        upperArmR.paint(g);
        upperLegL.paint(g);
        upperLegR.paint(g);
    }

    //@Override
    public boolean collide(Point p)
    {

        p.x-=m.getX();
        p.y-=m.getY();
        if(head.collide(new Point(p)))
            return true;
        else if(upperArmL.collide(new Point(p)))
            return true;
        else if(upperArmR.collide(new Point(p)))
            return true;
        else if(upperLegL.collide(new Point(p)))
            return true;
        else if(upperLegR.collide(new Point(p)))
            return true;
        p.x += m.getX();
        p.y += m.getY();
        if(m.contains(p.getX(), p.getY())) {
            focused = true;
            beginX = p.getX();
            beginY = p.getY();
            System.out.println("collide torso");
            return true;
        }
        else
            return false;
    }

   // @Override
    public void move(double X, double Y){
        head.move((X-m.getX()), (Y-m.getY()));
        upperArmL.move((X-m.getX()), (Y-m.getY()));
        upperArmR.move((X-m.getX()), (Y-m.getY()));
        upperLegL.move((X-m.getX()), (Y-m.getY()));
        upperLegR.move((X-m.getX()), (Y-m.getY()));
        if(focused) {
            offsetX = X - beginX;
            offsetY = Y - beginY;
            m.setRect(m.getX() + offsetX, m.getY() + offsetY, m.getWidth(), m.getHeight());
            beginX = X;
            beginY = Y;
        }
    }

    public void reset(){
        focused = false;
        head.reset();
        upperArmL.reset();
        upperArmR.reset();
        upperLegL.reset();
        upperLegR.reset();
    }
}
