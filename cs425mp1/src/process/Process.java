package process;

import java.util.Scanner;

import client.Client;
import message.Message;

public class Process implements Runnable{
	
	int id;
	int widget;
	int money;
	int logicalTimestamp;
	int[] vectorTimestamp;
	boolean hasRecordedState;
	Client client;
	int port_num;
	
	public Process(int widget, int money, int totalProcNum) {
//		this.id = id;
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
		try {
			  Thread.sleep(1000);
		} catch (InterruptedException ie) {
			    //Handle exception
		}
		System.out.println("Enter the port number : ");
		Scanner scanner = new Scanner(System.in);
		port_num = scanner.nextInt();
		//use this client to communicate with server
		client = new Client("localhost", port_num);
		try {
			id = client.getID();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("This is ID " + id);
//		printCurrState();
	}
	/*
	public static void main(String args[]) {
		Process p1 = new Process(1, 12, 13, 5);
		Process p2 = new Process(2, 22, 23, 5);
		Process p3 = new Process(3, 33, 34, 5);

	}
	*/
}
