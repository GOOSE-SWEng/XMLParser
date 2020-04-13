import java.util.ArrayList;

import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class Graphics3DLayer {
	int height;
	int width;
	StackPane sp = new StackPane();
	Canvas canvas = new Canvas(width,height);
	ArrayList<Graphics3D> graphics3D = new ArrayList<Graphics3D>();
	SubScene window = new SubScene(sp,width,height);
	
	public Graphics3DLayer(int width,int height){
		this.height = height;
		this.width = width;
		sp.getChildren().add(canvas);
	}
	
	public void add() {
		//constructor for the audio object
	}
	
	public void remove(Graphics3D object) {
		sp.getChildren().remove(object);
	}
	
	public SubScene get() {
		return (window);
	}
}