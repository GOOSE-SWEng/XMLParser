import java.awt.Color;

public class Video {
	int xstart = 0;
	int ystart = 0;
	int xend = 100;
	int yend = 100;
	Color linecolor = Color.BLACK;
	String starttime = "0";
	String endtime = "100";
	
	public Video(String[] string) {
		this.xstart = Integer.parseInt(string[0]);
		this.ystart = Integer.parseInt(string[1]);
		this.xend = Integer.parseInt(string[2]);
		this.yend = Integer.parseInt(string[3]);
		this.linecolor = Color.getColor(string[4]);
		this.starttime = string[5];
		this.endtime = string[6];
	}
}
