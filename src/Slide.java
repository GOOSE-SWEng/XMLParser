
public class Slide {
	String title;
	int slideNo;
	SlideText text;
	Line line;
	Shape shape;
	Audio audio;
	Image image;
	Video video;
	public Slide(String title, int slideNo, SlideText text, Line line, Shape shape, Audio audio, Image image, Video video) {
		this.title = title;
		this.slideNo = slideNo;
		this.text = text;
		this.line = line;
		this.shape = shape;
		this.audio = audio;
		this.image = image;
		this.video = video;
	}
}
