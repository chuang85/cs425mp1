package process;

import java.util.LinkedList;
import java.util.Queue;

import message.Message;

public class Channel {

	int from, to;
	int id;
	Queue<Message> messageQueue;
	boolean recordOn;
	
	public Channel(int from, int to) {
		this.from = from;
		this.to = to;
		id = 10*from + to;
		messageQueue = new LinkedList<Message>();
		recordOn = false;
	}
	
	public void addMessage(Message m) {
		messageQueue.offer(m);
	}
	
	public void removeMessage() {
		messageQueue.poll();
	}
	
	public void turnOnRecord() {
		recordOn = true;
	}
	
	public void turnOffRecord() {
		recordOn = false;
	}
	
	public void recordChannelStateAsEmpty() {
		
	}
	
	public void recordChannelState() {
		printCurrState(); // TODO Modify this, should write state info into file, and widget & money should be recorded as well.
	}
	
	public void printCurrState() {
		System.out.println(String.format("id=%d, isOn=", id, recordOn));
	}
}
