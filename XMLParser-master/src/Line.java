import java.awt.Color;

public class Line {
	int xstart = 0;
	int ystart = 0;
	int xend = 100;
	int yend = 100;
	Color linecolor = Color.BLACK;
	String starttime = "0";
	String endtime = "100";
	
	public Line(String[] string) {
		this.xstart = Integer.parseInt(string[0]);
		this.ystart = Integer.parseInt(string[1]);
		this.xend = Integer.parseInt(string[2]);
		this.yend = Integer.parseInt(string[3]);
		this.linecolor = Color.getColor(string[4]);
		this.starttime = string[5];
		this.endtime = string[6];
	}

	public int getXstart() {
		return xstart;
	}

	public void setXstart(int xstart) {
		this.xstart = xstart;
	}

	public int getYstart() {
		return ystart;
	}

	public void setYstart(int ystart) {
		this.ystart = ystart;
	}

	public int getXend() {
		return xend;
	}

	public void setXend(int xend) {
		this.xend = xend;
	}

	public int getYend() {
		return yend;
	}

	public void setYend(int yend) {
		this.yend = yend;
	}

	public Color getLinecolor() {
		return linecolor;
	}

	public void setLinecolor(Color linecolor) {
		this.linecolor = linecolor;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
