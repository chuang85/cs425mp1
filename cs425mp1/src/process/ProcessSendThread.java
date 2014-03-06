package process;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import server.Main;
import message.Marker;
import message.Message;
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
	
	
	
	public void sendMarker(int sequenceNum, int from) {
		for(int i = 1; i < Main.proc_num+1; i ++)
		{
			if(i != from)
			{
				Marker m = new Marker(sequenceNum, from, i);
				try {
					os.writeObject((Message) m);
					os.flush();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
			
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random rand = new Random(50);
		Random ano_rand = new Random(20);
		int rand_num;
		int wiget_send = 0;
		int money_send = 0;
		
		while (true) {
			// get a random number between 0-2
			rand_num = rand.nextInt(proc_num);
			
			//for send the marker
			//process 1 init marker at a random time
			
			if((id == 1) && (Main.snapshot_num > 0) && (ano_rand.nextInt(100)<10) && (Main.snapshot_on == false))
			{
				try {
					Main.p[id].recordProcessState();  // ------------HCK ADDED-----------------
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Main.snapshot_on = true;
				Main.p[id].hasRecordedState = true;
				sendMarker(Main.sequence_num, id);
			}	else if((Main.snapshot_on == true)&& (id!= 1) &&(Main.p[id].hasRecordedState == true))
			{
				sendMarker(Main.sequence_num,id);
			}
			
			//send the regular message
			if ((rand_num + 1) != id) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					// Handle exception
				}
				wiget_send = 10 / (id + 1);
				money_send =  5 / (id + 1);
				Main.p[id].widget -= wiget_send;
				Main.p[id].money -= money_send;
				sendMessage(wiget_send, money_send, id, rand_num + 1);
			}
		}
	}

}
