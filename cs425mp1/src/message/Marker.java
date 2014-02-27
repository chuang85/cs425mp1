package message;

public class Marker extends Message {
	int sequenceNumber;
	
	public Marker(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
