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
}