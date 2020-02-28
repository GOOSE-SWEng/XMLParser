import java.awt.Color;

public class Shape {
	int xStart;
	int yStart;
	int xEnd;
	int yEnd;
	Color lineColor;
	String startTime;
	String endTime;
	
	public Shape(String[] string) {
		this.xStart = Integer.parseInt(string[0]);
		this.yStart = Integer.parseInt(string[1]);
		this.xEnd = Integer.parseInt(string[2]);
		this.yEnd = Integer.parseInt(string[3]);
		this.lineColor = Color.getColor(string[4]);
		this.startTime = string[5];
		this.endTime = string[6];
	}
}
