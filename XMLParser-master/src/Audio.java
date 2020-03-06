import java.awt.Color;

public class Audio {
	String source;
	String startTime;
	Boolean loop;

	
	public Audio(String[] string) {
		this.source = string[0];
		this.startTime = string[1];
		this.loop = Boolean.parseBoolean(string[2]);

	}
}
