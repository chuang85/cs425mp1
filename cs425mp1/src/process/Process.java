package process;

import message.Message;

public class Process implements Runnable{
	
	int id;
	int widget;
	int money;
	int logicalTimestamp;
	int[] vectorTimestamp;
	boolean hasRecordedState;
	
	public Process(int id, int widget, int money, int totalProcNum) {
		this.id = id;
		this.widget = widget;
		this.money = money;
		logicalTimestamp = 0;
		vectorTimestamp = new int[totalProcNum];
		hasRecordedState = false;
	}
	
	public void recordProcessState() {
		printCurrState(); //TODO Modify this, should write state info into file.
	}
	
	public void onReceivingMarker(Message m, Channel c) {
		if (m.isMarker()) {
			if (!hasRecordedState) {
				recordProcessState();
				c.recordChannelState();
			}
		} else {
			System.out.println("Not a marker");
		}
	}
	
	public void updateStateOnReceiving(Message m) {
		if (m.isMarker()) {
			
		} else if (m.isRegular()) {
			
		} else {
			System.out.println("Who you are??");
		}
	}
	
	public void printCurrState() {
		System.out.println(String.format("id=%d, widget=%d, money=%d, logical=%d", id, widget, money, logicalTimestamp));
	}
	
	@Override
	public void run() {
		printCurrState();
	}
	
	/*public static void main(String args[]) {
		Process p1 = new Process(1, 12, 13, 5);
		Process p2 = new Process(2, 22, 23, 5);
		Process p3 = new Process(3, 33, 34, 5);
		
		new Thread(p1).start();
		new Thread(p2).start();
		new Thread(p3).start();
	}*/
	
}
