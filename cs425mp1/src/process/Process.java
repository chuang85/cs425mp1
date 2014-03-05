package process;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

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
	int proc_num;
	int port_num; // TODO Should assign a initial value?
	int snapshot_num; // TODO Should assign a initial value?

	public Process(int widget, int money, int totalProcNum) {
		// this.id = id;
		this.widget = widget;
		this.money = money;
		logicalTimestamp = 0;
		vectorTimestamp = new int[totalProcNum];
		hasRecordedState = false;
		proc_num = totalProcNum;
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
				System.out.println(String.format("from=%d, to=%d",
						my_m.getFrom(), my_m.getTo()));
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
		System.out.println("Enter the port number : ");
		Scanner scanner = new Scanner(System.in);
		port_num = scanner.nextInt();
		// use this client to communicate with server
		client = new Client("localhost", port_num);
		try {
			id = client.getID();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("This is ID " + id);

		// get the snapshot number
		if (id == 1) {
			try {
				snapshot_num = client.getID(); // ???
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Number of snapshot: " + snapshot_num);
		}

		Random rand = new Random(50);
		int rand_num;

		while (true) {
			// get a random number between 0-2
			rand_num = rand.nextInt(proc_num);
			if ((rand_num + 1) != id) {
				sendMessage(10 / (id + 1), 5 / (id + 1), id, rand_num + 1);
			}
			try {
				receiveMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// System.out.println("sending loop");
		}
	}

}
