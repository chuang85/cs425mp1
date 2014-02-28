package message;

public abstract class Message {
	public int from;
	public int to;
	
	public boolean isMarker() {
		return this instanceof Marker;
	}
	
	public boolean isRegular() {
		return this instanceof RegularMessage;
	}
}
