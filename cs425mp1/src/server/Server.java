package server;

import message.Marker;
import message.Message;
import message.RegularMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.BufferedReader;


public class Server implements Runnable{
	
	ServerSocket myServer = null;
	Socket[] clientSocket;
	ObjectInputStream[] is;
	ObjectOutputStream[] os;
//	Message input;
	Queue<Message> message_queue;

	int procCount;
	int portNum;
	
	public Server()
	{
//		input = new byte[3];
		message_queue = new LinkedList<Message>();
		
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
		try {
			  Thread.sleep(1000);
		} catch (InterruptedException ie) {
			    //Handle exception
		}
		System.out.println("Connection completed \n");
		
		//listen on clients
		Message agent;
		/*
		while (true) {
			for (int j = 0; j < procCount; j++) {
				try {
					agent = (Message)is[j].readObject();
					if(agent.isRegular())
						message_queue.add(agent);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			while (!message.isEmpty()) {
				temp = message.poll();
				System.out.println("temp");
				for(int j =0 ; j < 3; j ++)
	    	 		System.out.print(temp[j]+"\n");

				try {
					os[(int)temp[0]].write(temp,0,3);
					System.out.println("writing");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		*/
	}

}
