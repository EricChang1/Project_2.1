
package Helper;

import DataStructure.Vector3;

import java.util.ArrayList;

public class Layout {
    public final Orientation orientation;
    public final Point size;
    public final Point origin;

    public static Orientation pointy = new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0, 0.5);

    public Layout(Point size, Point origin) {
        this.orientation = pointy;
        this.size = size;
        this.origin = origin;
    }


    public Point hexToPixel(Vector3 coord) {
        Orientation orientation = this.orientation;
        double x = (orientation.f0 * coord.x + orientation.f1 * coord.y) * this.size.x;
        double y = (orientation.f2 * coord.x + orientation.f3 * coord.y) * this.size.y;
        return new Point(x + this.origin.x, y + this.origin.y);
    }

    public Vector3 pixelToVector3(Point p) {
        Orientation orientation = this.orientation;
        Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        double x = orientation.b0 * pt.x + orientation.b1 * pt.y;
        double y = orientation.b2 * pt.x + orientation.b3 * pt.y;

        return new Vector3(Math.round(x), Math.round(y), Math.round(-x-y)); // Might have to change this
    }

    public Point hexCornerOffset(int corner)
    {
        Orientation M = this.orientation;
        Point size = this.size;
        double angle = 2.0 * Math.PI * (M.startAngle - corner) / 6;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }

    public ArrayList<Point> polygonCorners(Vector3 coord)
    {
        ArrayList<Point> corners = new ArrayList<Point>(){{}};
        Point center = this.hexToPixel(coord);
        for (int i = 0; i < 6; i++)
        {
            Point offset = this.hexCornerOffset(i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }
}