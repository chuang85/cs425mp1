package message;

public class RegularMessage extends Message {

	/**
	 * Auto-generated serial number.
	 */
	private static final long serialVersionUID = 1536048653421673109L;

	int widget;
	int money;
	
	int logicalTimestamp;
	int[] vectorTimestamp;

	public RegularMessage(int widget, int money, int from, int to) {
		super(from, to);
		this.widget = widget;
		this.money = money;
	}

	public int getWidget() {
		return widget;
	}

	public int getMoney() {
		return money;
	}
	
	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}
}
