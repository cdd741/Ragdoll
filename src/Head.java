import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.Math;

import static java.lang.Math.atan2;

public class Head {
    private Torso torso; // Parent
    private Ellipse2D m;
    private double px, py;
    private double theta = 0;
    private double thetaBegin, thetaEnd;
    private double w = 100;
    private double h = 100;

    private boolean focused = false;
    private AffineTransform affine, resetAffine;



    Head(double x, double y, Torso torso){
        this.torso = torso;
        m = new Ellipse2D.Double(-w/2, -h/2, w, h);
//        anchorX =
        resetAffine = new AffineTransform();
        resetAffine.translate(x, y);
        affine = new AffineTransform(resetAffine);
    }

//    @Override
    AffineTransform gl;
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform save = new AffineTransform(g2.getTransform());

        AffineTransform af = g2.getTransform();
        af.concatenate(affine);
        af.concatenate(AffineTransform.getRotateInstance(theta, 0, h/2));
        g2.setTransform(af);
        gl = new AffineTransform(af);
        g2.draw(m);
        g2.setTransform(save);
    }

    private boolean inRange(Point2D p){
        Point2D dst = new Point2D.Double();
        try {
            AffineTransform af = new AffineTransform(affine);
            af.concatenate(AffineTransform.getRotateInstance(theta, 0, h/2));
            var a = af.createInverse();
            a.transform(p, dst);
        }
        catch(NoninvertibleTransformException e)
        {
            return false;
        }

        if(m.contains(dst.getX(), dst.getY())) {
            return true;
        }
        return false;
    }

    public boolean collide(Point2D p)
    {
        Point2D dst = new Point2D.Double();
        try {
            AffineTransform af = new AffineTransform(affine);
            af.concatenate(AffineTransform.getRotateInstance(theta, 0, h/2));
            var a = af.createInverse();
            a.transform(p, dst);
        }
        catch(NoninvertibleTransformException e)
        {
            return false;
        }

        if(m.contains(dst.getX(), dst.getY())) {
//            System.out.println("collide head");
            focused = true;
            px = dst.getX();
            py = dst.getY();
            thetaBegin = atan2(py - 50, px);

            return true;
        }
        return false;
    }

    public void move(double X, double Y){
        if(focused && inRange(new Point2D.Double(X, Y))){
            Point2D src = new Point2D.Double(X, Y);
            Point2D dst = new Point2D.Double();
            try {
                AffineTransform af = new AffineTransform(affine);
                af.concatenate(AffineTransform.getRotateInstance(theta, 0, h/2));
                var a = af.createInverse();
                a.transform(src, dst);
            }
            catch(NoninvertibleTransformException e)
            {
                return;
            }
            px = dst.getX();
            py = dst.getY();
            thetaEnd = atan2(py - 50, px);
            double temp = theta + thetaEnd - thetaBegin;
            if(-(Math.PI / 180 * 50) < temp && temp < (Math.PI / 180 * 50))
                theta += thetaEnd - thetaBegin;

            src = new Point2D.Double(X, Y);
            try {
                AffineTransform af = new AffineTransform(affine);
                af.concatenate(AffineTransform.getRotateInstance(theta, 0, h/2));
                var a = af.createInverse();
                a.transform(src, dst);
            }
            catch(NoninvertibleTransformException e)
            {
                return;
            }
            px = dst.getX();
            py = dst.getY();
            thetaBegin = atan2(py - 50, px);
        }
    }

    public void reset(){
        focused = false;
    }
}
