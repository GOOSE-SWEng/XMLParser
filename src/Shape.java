import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Shape {
	Polygon polygon = new Polygon();
	Color fill = null;
	Color line = null;
	int sideNo;
	Group group = new Group();
	Canvas canvas; 
	GraphicsContext gc; 
	List<Double> pointsx = new ArrayList<Double>();
	List<Double> pointsy = new ArrayList<Double>();
	boolean oval = false;
	int oheight = 0;
	int owidth = 0;
	int ocx = 0;
	int ocy = 0;
	int width;
	int height;
	
	
	public Shape(Color lineColour, Color fillColour,int w, int h) {
		fill = fillColour;
		line = lineColour;
		width = w;
		height = h;
		canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(fill);
		gc.setStroke(line);
	}
	
	
	public void addPoint(CartesianCoordinate point) {
		pointsx.add(point.getX());
		pointsy.add(point.getY());
	}
	public void addPoint(double x, double y) {
		pointsx.add(x);
		pointsy.add(y);
	}

// here if we need it 
//	public void drawFacet (Facet facet) {
//		pointsx.add(facet.getPoint0().getX());
//		pointsx.add(facet.getPoint1().getX());
//		pointsx.add(facet.getPoint2().getX());
//		
//		pointsy.add(facet.getPoint0().getX());
//		pointsy.add(facet.getPoint1().getX());
//		pointsy.add(facet.getPoint2().getX());
//	}
	
	public void drawOval (int width, int height, int cx, int cy) {
		oval = true;
		oheight = height;
		owidth = width;
		ocx = cx;
		ocy = cy;
	}
	
	public Group get() {
		if (oval == true) {
			gc.fillOval(ocx,ocy,owidth,oheight);
			gc.strokeOval(ocx,ocy,owidth,oheight);
			
			group.getChildren().add(canvas);
		}
		else {
			double[] pointx = new double[pointsx.size()];// used to create an array of points from an arraylist
			double[] pointy = new double[pointsy.size()];
			
			//adding arraylist points to an array
			for (int i = 0; i<pointsx.size(); i++) {
				pointx[i] = pointsx.get(i);
			}
			for (int i = 0; i<pointsy.size(); i++) {
				pointy[i] = pointsy.get(i);
			}
			
			gc.strokePolygon(pointx,pointy,pointsx.size());//creates a polygon outline
			gc.fillPolygon(pointx,pointy,pointsx.size());//creates the polygon solid
			group.getChildren().add(canvas);
		}
		return group;
	}
	
}
