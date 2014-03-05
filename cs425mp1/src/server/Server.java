package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import process.Channel;
import message.Marker;
import message.Message;
import message.RegularMessage;

public class Server implements Runnable {

	ServerSocket myServer = null;
	Socket[] clientSocket;
	ObjectInputStream[] is;
	ObjectOutputStream[] os;
	Channel[][] channel;
	int total_marker; 
 	// Message input. ConcurrentLinkedQueue is thread safe
	ConcurrentLinkedQueue<Message> message_queue = new ConcurrentLinkedQueue<Message>();
	
	public void reset_process()
	{
		for(int j = 1; j < Main.proc_num+1; j ++)
			Main.p[j].hasRecordedState = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// init the client socket array and is os array
		clientSocket = new Socket[Main.proc_num + 1];
		is = new ObjectInputStream[Main.proc_num + 1];
		os = new ObjectOutputStream[Main.proc_num + 1];
		// create the server
		try {
			myServer = new ServerSocket(Main.port_num);
		} catch (IOException e) {
			System.out.println(e);
		}

		// wait for all the clients to connect
		System.out.println("Waiting for all the clients to connect... \n");
		int i = 1;
		while (true) {
			try {
				clientSocket[i] = myServer.accept();
				is[i] = new ObjectInputStream(clientSocket[i].getInputStream());
				os[i] = new ObjectOutputStream(
						clientSocket[i].getOutputStream());
				// send the process id to the connecting process
				os[i].writeObject((Integer) i);
				os[i].flush();
				i++;
			} catch (IOException e) {
				System.out.println(e);
			}
			if (i == Main.proc_num + 1)
				break;
		}

		// send the snapshot number to process 1
		try {
			os[1].writeObject((Integer) Main.snapshot_num);
			os[1].flush();
		} catch (IOException e) {
			System.out.println(e);
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			// Handle exception
		}
		System.out.println("Connection completed \n");
		
		//channel  format  channel[from][to]  starting from 1,1 
		channel = new Channel[Main.proc_num+1][Main.proc_num+1];
		for(int j = 1; j < Main.proc_num+1; j ++)
		{
			for(int k = 1; k < Main.proc_num+1; k++)
			{
				if(j != k)
				{
					channel[j][k] = new Channel(j,k);
				}
			}
		}
		
		total_marker = Main.proc_num*(Main.proc_num-1);
		// listen on clients
		Message agent;
		while (true) {
			// enqueue all the messages and markers, and put marker into channel
			for (int j = 1; j < Main.proc_num + 1; j++) {
				try {
//					agent = (RegularMessage) is[j].readObject();
//					message_queue.add((RegularMessage) agent);
					agent = (Message) is[j].readObject();
					message_queue.add(agent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			while (!message_queue.isEmpty()) {
				agent = message_queue.poll();
				
				//message is a marker
				if (agent.isMarker()) {
					System.out.println("receiving marker");
					
					//p has not recorded its state yet
					if(Main.p[agent.to].hasRecordedState == false)
					{
						try {
							Main.p[agent.to].recordProcessState();  // ------------HCK ADDED-----------------
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// TODO record it process state now
						Main.p[agent.to].hasRecordedState = true;
						//turns on recording of messages arrving over other incoming channels
						for(int j = 1; j < Main.proc_num+1; j++){
							if((j != agent.to) && (j != agent.from))
								channel[j][agent.to].turnOnRecord();	
						}
						
					}	else
					{
						channel[agent.from][agent.to].turnOffRecord();
						System.out.println("printing channel message");
						while(channel[agent.from][agent.to].messageQueue.peek() != null)
						{
							System.out.println(((RegularMessage)channel[agent.from][agent.to].messageQueue.poll()).from);
						}
						///
						///
						///           record the channel message            to do 
						///
						///
						///
					}
					/*
					try {
						os[(int) agent.to].writeObject((Marker) agent);
						os[(int) agent.to].flush();
						System.out.println(String.format("Sending msg from %d to %d", agent.from, agent.to));
					} catch (IOException e) {
						e.printStackTrace();
					}
					*/
					total_marker --;
					//done with one snapshot 
					if(total_marker == 0)
					{
						Main.snapshot_num --;
						Main.sequence_num ++;
						Main.snapshot_on = false;
						reset_process();
						total_marker = Main.proc_num*(Main.proc_num-1);
						System.out.println("Printing out process states");
						for( int j = 1; j < Main.proc_num+1; j++)
						{
							System.out.println("money:");
							System.out.println(Main.p[j].money);
							System.out.println("widget");
							System.out.println(Main.p[j].widget);
						}
							
						///
						///
						///
						///
						/// write the process's state to file. done with one snapshot          to do
						///
						///
						///
						///
						if(Main.snapshot_num == 0)
							System.exit(1);
					}
				}
				//message is a regular message
				else
				{
					//if the channel's recording is on, store the message in the channel
					if(channel[agent.from][agent.to].outputCurrState())
						channel[agent.from][agent.to].addMessage(agent);
					try {
						os[(int) agent.to].writeObject((RegularMessage) agent);
						os[(int) agent.to].flush();
						System.out.println(String.format("Sending msg from %d to %d", agent.from, agent.to));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
