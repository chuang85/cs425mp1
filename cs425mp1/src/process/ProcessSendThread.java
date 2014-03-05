package process;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import message.Marker;
import message.RegularMessage;

public class ProcessSendThread implements Runnable{
	ObjectOutputStream os;
	int id;
	int proc_num;
	public ProcessSendThread(ObjectOutputStream os, int id, int proc_num)
	{
		this.os = os;
		this.id = id;
		this.proc_num = proc_num;
	}
	
	public void sendMessage(int widget, int money, int from, int to) {
		RegularMessage test_m = new RegularMessage(widget, money, from, to);
		test_m.testStr = "Greetings from process " + id;
		try {
			os.writeObject((RegularMessage) test_m);
			// System.out.println(i);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void sendMarker(int sequenceNum, int from, int to) {
		Marker m = new Marker(sequenceNum, from, to);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
			Random rand = new Random(50);
		int rand_num;

		while (true) {
			// get a random number between 0-2
			rand_num = rand.nextInt(proc_num);
			if ((rand_num + 1) != id) {
				sendMessage(10 / (id + 1), 5 / (id + 1), id, rand_num + 1);
			}

		}
	}

}
