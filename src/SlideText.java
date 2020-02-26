import java.awt.Color;

import javafx.scene.text.Text;

public class SlideText {
	String text;
	String font;
	int size = 24;
	Color color = null;
	int xPos = 0;
	int yPos = 0;
	String startTime;
	String endTime;
	String b;
	String i;
	
	public SlideText(String[] string) {
		this.text = string[0];
		this.font = string[1];
		this.size = Integer.parseInt(string[2]);
		this.color = Color.getColor(string[3]);
		this.xPos = Integer.parseInt(string[4]);
		this.yPos = Integer.parseInt(string[5]);
		this.startTime = string[6];
		this.endTime = string[7];
		this.b = string[8];
		this.i = string[9];
	}

}
