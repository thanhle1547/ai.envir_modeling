package cartesian.coordinate.extent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

/**
 * {@code CCExtentLine} represents a line in a Cartesian coordinate system.
 *
 * @author thanhle1547
 * @see CCSystem
 */
public class CCExtentLine {
    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;
    protected Paint paint;
    protected Stroke stroke;

    public CCExtentLine(double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, Color.BLACK, new BasicStroke(1f));
    }

    public CCExtentLine(Point2D firstPoint, Point2D secondPoint) {
        this(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());
    }

    public CCExtentLine(double x1, double y1, double x2, double y2, Paint paint, Stroke stroke) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.paint = paint;
        this.stroke = stroke;
    }

    public CCExtentLine(Point2D firstPoint, Point2D secondPoint, Paint paint, Stroke stroke) {
        this(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY(), paint, stroke);
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Point2D getFirstPoint() {
        return new Point2D.Double(x1, y1);
    }
    
    public Point2D getSecondPoint() {
        return new Point2D.Double(x2, y2);
    }
}