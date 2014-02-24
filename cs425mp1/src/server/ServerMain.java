package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.BufferedReader;

public class ServerMain {

	public static void main(String[] args) {
		ServerSocket myServer = null;
		Socket[] clientSocket;
		DataInputStream[] is;
		DataOutputStream[] os;
		byte[] input = new byte[3];
		Queue<byte[]> message = new LinkedList<byte[]>();

		int procCount;
		int portNum;

		// input the port number and process number
		System.out.println("Enter the port number : ");
		Scanner scanner = new Scanner(System.in);
		portNum = scanner.nextInt();
		// System.out.println(port_num);
		System.out.println("Enter the process number : ");
		procCount = scanner.nextInt();

		// init the client socket array and is os array
		clientSocket = new Socket[procCount];
		is = new DataInputStream[procCount];
		os = new DataOutputStream[procCount];
		// create the server
		try {
			myServer = new ServerSocket(portNum);
		} catch (IOException e) {
			System.out.println(e);
		}

		// wait for all the clients to connect
		System.out.println("Waiting for all the clients to connect... \n");
		int i = 0;
		while (true) {
			try {
				clientSocket[i] = myServer.accept();
				is[i] = new DataInputStream(clientSocket[i].getInputStream());
				os[i] = new DataOutputStream(clientSocket[i].getOutputStream());
				i++;

			} catch (IOException e) {
				System.out.println(e);
			}
			if (i == procCount)
				break;
		}
		System.out.println("Connection completed \n");

		// listen to the clients
		byte[] temp;
		temp = new byte[3];
		while (true) {
			for (int j = 0; j < procCount; j++) {
				try {
					is[j].read(input);
					message.add(input);
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

	}
}
