package process;

import java.util.LinkedList;
import java.util.Queue;

import message.Message;

public class Channel {

	int id;
	Queue<Message> messageQueue;
	boolean isOn;
	
	public Channel(int from, int to) {
		id = 10*from + to;
		messageQueue = new LinkedList<Message>();
		isOn = false;
	}
	
	public void pushMessage(Message m) {
		messageQueue.add(m);
	}
	
	public void turnOnChannel() {
		isOn = true;
	}
	
	public void turnOffChannel() {
		isOn = false;
	}
	
	public void recordChannelStateAsEmpty() {
		
	}
	
	public void recordChannelState() {
		printCurrState(); // TODO Modify this, should write state info into file, and widget & money should be recorded as well.
	}
	
	public void printCurrState() {
		System.out.println(String.format("id=%d, isOn=", id, isOn));
	}

}
