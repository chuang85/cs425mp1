package process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import message.Message;
import message.RegularMessage;

import org.omg.CORBA.Environment;

import server.Main;
import client.Client;

public class Process implements Runnable {

	public int id;
	public int widget;
	public int money;
	public int logicalTimestamp;
	public int[] vectorTimestamp;
	public boolean hasRecordedState;
	public boolean hasSendMarker;
	Client client;

	public Process(int widget, int money) {
		this.widget = widget;
		this.money = money;
		logicalTimestamp = 0;
		vectorTimestamp = new int[Main.proc_num];
		hasRecordedState = false;
		hasSendMarker = false;
	}

	public void recordProcessState() throws IOException {
		synchronized (this) {
			String content = String.format(
					"id %d : snapshot %d : money %d widgets %d", id,
					Main.sequence_num, money, widget); // TODO ADD TIMESTAMP

			String filePath = Main.txtDirectory + "process_" + id + ".txt";
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// true means append to existing file
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.newLine();
			bw.close();
		}
	}

	public void onReceivingMarker(Message m, Channel c) throws IOException {
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

	public void receiveMessage() throws ClassNotFoundException {
		RegularMessage my_m;
		while (true) {
			try {
				my_m = (RegularMessage) client.is.readObject();
				// System.out.println(String.format(
				// "Process %d said: Receive msg from %d, content: %s",
				// id, my_m.getFrom(), my_m.testStr));
				// System.out.println("money " + my_m.money);
				
				//update timestamp
				Main.lambo[my_m.to] = Math.max(my_m.lamboM+1,Main.lambo[my_m.to]+1);
				for(int j = 1; j < Main.proc_num+1; j ++)
				{
					if(j != my_m.to)
					{
						Main.vector[my_m.to][j] = Math.max(my_m.vectorM[j], Main.vector[my_m.to][j]);
					}	else
					{
						Main.vector[my_m.to][j] ++;
					}
				}
				synchronized (this) 
				{
					money += my_m.money;
					widget += my_m.widget;
				}
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

		ProcessSendThread send = new ProcessSendThread(client.os, id,
				Main.proc_num);
		new Thread(send).start();
		try {
			receiveMessage();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
