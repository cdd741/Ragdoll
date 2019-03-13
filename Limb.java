import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static java.lang.Math.atan2;

public class Limb {
    String name;
    private Limb child;
    private Rectangle2D m;
    private boolean focused = false;
    private boolean stretch;
    private int angle;

    private double px, py;
    private double beginLen, endLen;
    private double beginX, beginY;
    private double endX, endY;
    private double anchorX, anchorY;
    private double theta = 0;
    private double thetaBegin, thetaEnd;

    private AffineTransform affine, resetAffine;

    Limb(double x, double y, double w, double h, double anchorX, double anchorY, boolean stretch, int angle){
        m = new Rectangle2D.Double(0, 0, w, h);
        this.angle = angle;
        this.stretch = stretch;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        resetAffine = new AffineTransform();
        resetAffine.translate(x, y);
        affine = new AffineTransform(resetAffine);
    }

    public void attach(Limb child){
        this.child = child;
    }

    AffineTransform gl;
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform save = new AffineTransform(g2.getTransform());

        AffineTransform af = g2.getTransform();
        af.concatenate(affine);
        af.concatenate(AffineTransform.getRotateInstance(theta, anchorX, anchorY));
        g2.setTransform(af);
        gl = new AffineTransform(af);
        g2.draw(m);
        if(child != null)
            child.paint(g);
        g2.setTransform(save);
    }

    private boolean inRange(Point2D p){
        Point2D dst = new Point2D.Double();
        try {
            AffineTransform af = new AffineTransform(affine);
            af.concatenate(AffineTransform.getRotateInstance(theta, anchorX, anchorY));
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
            af.concatenate(AffineTransform.getRotateInstance(theta, anchorX, anchorY));
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
            thetaBegin = atan2(py - anchorY, px - anchorX);
            beginLen = Math.pow((Math.pow(px - anchorX, 2) + Math.pow(py - anchorY, 2)), 0.5);
            beginX = px;
            beginY = py;
            return true;
        }
        if(child != null)
            child.collide(dst);

        return false;
    }

    public void move(double X, double Y){
        Point2D src = new Point2D.Double(X, Y);
        Point2D dst = new Point2D.Double();
        try {
            AffineTransform af = new AffineTransform(affine);
            af.concatenate(AffineTransform.getRotateInstance(theta, anchorX, anchorY));
            var a = af.createInverse();
            a.transform(src, dst);
        } catch (NoninvertibleTransformException e) {
            return;
        }

        if(focused && inRange(new Point2D.Double(X, Y))) {
            px = dst.getX();
            py = dst.getY();
            thetaEnd = atan2(py - anchorY, px - anchorX);
            double temp = theta + thetaEnd - thetaBegin;
            if ((-(Math.PI / 180 * angle) < temp && temp < (Math.PI / 180 * angle)) || angle == 360)
                theta += thetaEnd - thetaBegin;

            src = new Point2D.Double(X, Y);
            try {
                AffineTransform af = new AffineTransform(affine);
                af.concatenate(AffineTransform.getRotateInstance(theta, anchorX, anchorY));
                var a = af.createInverse();
                a.transform(src, dst);
            } catch (NoninvertibleTransformException e) {
                return;
            }
            px = dst.getX();
            py = dst.getY();
            thetaBegin = atan2(py - anchorY, px - anchorX);
            extend(px, py);
        }
        if(child != null) {
            child.move(dst.getX(), dst.getY());
        }
    }

    public void reset(){
        focused = false;
        if(child != null)
            child.reset();
    }

    private void extend(double X, double Y){
        if(!stretch)
            return;
        endLen = Math.pow((Math.pow(px - anchorX, 2) + Math.pow(py - anchorY, 2)), 0.5);
        endY = Math.pow(Math.pow(endLen, 2) -(Math.pow(px - anchorX, 2)), 0.5);
        m.setRect(m.getX(), m.getY(), m.getWidth(), m.getHeight() + endY - beginY);

        if (child != null) {
            child.affine.translate(0, + endY - beginY);
            child.extend(endY - beginY);
        }
        beginY = endY;
    }

    private void extend(double len){
        if(!stretch)
            return;
        m.setRect(m.getX(), m.getY(), m.getWidth(), m.getHeight() + len);
        if (child != null) {
            child.affine.translate(0, len);
        }
    }
}
