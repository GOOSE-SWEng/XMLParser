import java.util.ArrayList;

import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class VideoLayer {
	int height;
	int width;
	StackPane sp = new StackPane();
	ArrayList<Video> videos = new ArrayList<Video>();
	SubScene window = new SubScene(sp,width,height);
	
	public VideoLayer(int width,int height){
		this.height = height;
		this.width = width;
		sp.setMinSize(width,height);
	}
	
	public void add(String urlName, int startTime, Boolean loop, int xStart, int yStart) {
		Video video = new Video( urlName, startTime, loop, xStart, yStart, width, height);//creates te video object and its subscene
		videos.add(video);// adds the video object to the array list
		sp.getChildren().add(video.get());// adds the SubScene(created with the constructor) to the video layer stack pane
	}
	
	public void remove(Video video) {
		sp.getChildren().remove(video.get());
	}
	
	public SubScene get() {
		return (window);
	}
}