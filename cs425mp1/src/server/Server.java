package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import message.Message;
import message.RegularMessage;


public class Server implements Runnable{
	
	ServerSocket myServer = null;
	Socket[] clientSocket;
	ObjectInputStream[] is;
	ObjectOutputStream[] os;
//	Message input;
	ConcurrentLinkedQueue<Message> message_queue; // ConcurrentLinkedQueue is thread safe

	int procCount;
	int portNum;
	int snapshot_num;
	
	public Server()
	{
//		input = new byte[3];
		message_queue = new ConcurrentLinkedQueue<Message>();
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// init the client socket array and is os array
		clientSocket = new Socket[procCount+1];
		is = new ObjectInputStream[procCount+1];
		os = new ObjectOutputStream[procCount+1];
		// create the server
		try {
			myServer = new ServerSocket(portNum);
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
				os[i] = new ObjectOutputStream(clientSocket[i].getOutputStream());
				//send the process id to the connecting process
				try {
					os[i].writeObject((Integer) i);
//					System.out.println(i);
				} catch (IOException e) {
					System.out.println(e);
				}
				i++;

			} catch (IOException e) {
				System.out.println(e);
			}
			if (i == procCount+1)
				break;
		}
		
		//send the snapshot number to process 1
		try {
			os[1].writeObject((Integer) snapshot_num);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		try {
			  Thread.sleep(1000);
		} catch (InterruptedException ie) {
			    //Handle exception
		}
		System.out.println("Connection completed \n");
		
		//listen on clients
		Message agent;
		
		while (true) {
			//enqueue all the messages and markers, and put marker into channel
			for (int j = 1; j < procCount+1; j++) {
					try {
						agent = (RegularMessage)is[j].readObject();
						message_queue.add((RegularMessage) agent);
						if(agent.isRegular())
						{
							
						}
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
				try {
					os[(int)agent.to].writeObject((RegularMessage)agent);
					System.out.println("writing");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		
	}

}
