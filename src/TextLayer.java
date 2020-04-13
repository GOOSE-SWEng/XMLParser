import java.util.ArrayList;

import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class TextLayer {
	int height;
	int width;
	StackPane sp = new StackPane();
	Canvas canvas = new Canvas(width,height);
	ArrayList<SlideText> slideText = new ArrayList<SlideText>();
	SubScene window = new SubScene(sp,width,height);
	
	public TextLayer(int width,int height){
		this.height = height;
		this.width = width;
		sp.getChildren().add(canvas);
	}
	
	public void add() {
		//constructor for the audio object
	}
	
	public void remove(TextLayer object) {
		sp.getChildren().remove(object);
	}
	
	public SubScene get() {
		return (window);
	}
}