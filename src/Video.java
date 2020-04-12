import java.awt.Color;

import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public class Video {
	int xstart;
	int ystart;
	int startTime;
	int canvasWidth;
	int canvasHeight;
	String urlName;
	Boolean loop;
	SubScene subScene;
	
	public Video(String urlName, int startTime, Boolean loop, int xStart, int yStart, int canvasWidth, int canvasHeight) {
		// construct video object using these
		//create the SubScene object
	}
	public SubScene get() {
		return subScene;
	}
	public void remove() {
		//remove video from the subscene
	}
	public void start() {
		//starts playing the video
	}
}
