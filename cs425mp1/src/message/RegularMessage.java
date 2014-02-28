package message;

public class RegularMessage extends Message {
	
	int widget;

	int money;	
	
	int logicalTimestamp;
	int[] vectorTimestamp;
	
	
	
	public RegularMessage(int widget, int money) {
		this.widget = widget;
		this.money = money;
	}
	
	public int getWidget() {
		return widget;
	}

	public void setWidget(int widget) {
		this.widget = widget;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
