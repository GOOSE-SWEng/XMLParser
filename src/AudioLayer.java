import java.util.ArrayList;

import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class AudioLayer {
	int height;
	int width;
	StackPane sp = new StackPane();
	Canvas canvas = new Canvas(width,height);
	ArrayList<Audio> audio = new ArrayList<Audio>();
	SubScene window = new SubScene(sp,width,height);
	
	public AudioLayer(int width,int height){
		this.height = height;
		this.width = width;
		sp.getChildren().add(canvas);
	}
	
	public void add() {
		//constructor for the audio object
	}
	
	public void remove(Audio object) {
		sp.getChildren().remove(object);
	}
	
	public SubScene get() {
		return (window);
	}
}
