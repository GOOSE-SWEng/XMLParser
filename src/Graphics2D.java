import java.util.ArrayList;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Graphics2D {
	int height;
	int width;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	int currentPoly = 0;
	
	public Graphics2D(int width,int height) {
		this.height = height;
		this.width = width;
	}
	
	public void addOval(Color LineColour, Color fillColour, int oWidth, int oHeight, int cX, int cY) {
		Shape temp = new Shape(LineColour,fillColour,width,height);
		temp.drawOval(oWidth,oHeight,cX,cY);
		shapes.add(temp);
	}
	public void addPoly(Color LineColour,Color fillColour) {
		Shape temp = new Shape(LineColour,fillColour,width,height);
		shapes.add(temp);
		currentPoly = shapes.size()-1;
	}
	public void addPoint(double x, double y) {
		shapes.get(currentPoly).addPoint(x,y);
	}
	
	public SubScene Update(double width, double height) {
		GridPane gp = new GridPane();
		Canvas canvas = new Canvas(width, height);
		gp.add(canvas,0,0);
		
		for (int i = 0; i<shapes.size();i++) {
			gp.add(shapes.get(i).get(),0,0);
		}
		
		SubScene Window = new SubScene(gp,width,height);
		return Window;
	}
}
