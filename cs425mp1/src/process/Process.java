package process;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import server.Main;
import message.Message;
import message.RegularMessage;
import client.Client;

public class Process implements Runnable {

	int id;
	int widget;
	int money;
	int logicalTimestamp;
	int[] vectorTimestamp;
	boolean hasRecordedState;
	Client client;

	public Process(int widget, int money) {
		this.widget = widget;
		this.money = money;
		logicalTimestamp = 0;
		vectorTimestamp = new int[Main.proc_num];
		hasRecordedState = false;
	}

	public void recordProcessState() {
		printCurrState(); // TODO Modify this, should write state info into
							// file.
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
		System.out.println(String.format(
				"id=%d, widget=%d, money=%d, logical=%d", id, widget, money,
				logicalTimestamp));
	}

	public void sendMessage(int widget, int money, int from, int to) {
		RegularMessage test_m = new RegularMessage(widget, money, from, to);
		test_m.testStr = "Greetings from process " + id;
		try {
			client.os.writeObject((RegularMessage) test_m);
			// System.out.println(i);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void receiveMessage() throws ClassNotFoundException {
		RegularMessage my_m;
		while (true) {
			try {
				my_m = (RegularMessage) client.is.readObject();
				System.out.println(String.format("Process %d said: Receive msg from %d, content: %s", id, my_m.getFrom(), my_m.testStr));
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			// Handle exception
		}
		// use this client to communicate with server
		client = new Client("localhost", Main.port_num);
		try {
			id = client.getID();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("This is ID " + id);

		// get the snapshot number
		if (id == 1) {
			try {
				Main.snapshot_num = client.getID(); // ???
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Number of snapshot: " + Main.snapshot_num);
		}

		proc_send send = new proc_send(client.os,id, Main.proc_num);
		new Thread(send).start();
		try {
			receiveMessage();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
