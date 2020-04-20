import java.awt.Color;

import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public class Audio {
	String source;
	Boolean loop;
	Boolean controls;
	SubScene subScene;
	StackPane sp;
	int startTime;
	int slideNumber;
	
	public Audio(String urlName, int startTime, Boolean loop, Boolean controls, int controlX, int controlY, int width, int height, int slideNumber) {
		source = urlName;
		subScene = new SubScene(sp,width,height);
		this.startTime= startTime;
		this.slideNumber= slideNumber;
		// construct your subscene in here
	}
	public void add() {
		// add the object to the pane
	}
	public void remove() {
		//remove the object from the pane
	}
	public void start() {
		// start playing the audi0
	}
	public void stop() {
		// stop playing the audio 
	}
	public int getStartTime() {
		return(startTime);
	}

	public int getSlideNumber() {
		return(slideNumber);
	}
	public SubScene get() {
		return(subScene);
	}
}
