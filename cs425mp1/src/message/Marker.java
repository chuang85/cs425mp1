package message;

public class Marker extends Message {
	/**
	 * Auto-generated serial number.
	 */
	private static final long serialVersionUID = 8133701318068098279L;
	int sequenceNumber;
	
	public Marker(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
