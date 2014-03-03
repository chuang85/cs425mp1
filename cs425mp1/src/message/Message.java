package message;

import java.io.Serializable;

public abstract class Message implements Serializable{
	public int from;
	public int to;
	
	public boolean isMarker() {
		return this instanceof Marker;
	}
	
	public boolean isRegular() {
		return this instanceof RegularMessage;
	}
}
