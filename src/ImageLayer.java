import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class ImageLayer {
	int sceneHeight;
	int sceneWidth;
	StackPane sp = new StackPane();
	ArrayList<SlideImage> images = new ArrayList<SlideImage>();
	SubScene window;
	
	public ImageLayer(int width,int height, ArrayList<SlideImage> images){
		sceneHeight = height;
		sceneWidth = width;
    sp.setMinSize(sceneWidth,sceneHeight);
    sp.setAlignment(Pos.TOP_LEFT);
		this.images = images;
	}
	
	public void add(String urlName, int xStart, int yStart, int width, int height, int startTime, int endTime, int slideNumber) {
		//constructor for the image object
		SlideImage image = new SlideImage(urlName, xStart, yStart, width, height, startTime, endTime, slideNumber, sceneWidth, sceneHeight);
    images.add(image);
		sp.getChildren().add(image.get());
	}
	
	public void remove(SlideImage object) {
		sp.getChildren().remove(object.get());
	}
	
	public SubScene get() {
    window = new SubScene(sp,sceneWidth,sceneHeight);
		return (window);
  }
  
  public ArrayList<SlideImage> getList() {
    return images;
  }
}