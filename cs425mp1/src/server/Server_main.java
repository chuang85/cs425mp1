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

public class Server_main {
	
	public static void main(String[] args)
	{
		ServerSocket my_server = null;
		DataInputStream[] is;
		DataOutputStream[] os;
		Socket[] clientSocket;
		int proc_num;
		int port_num;
		byte[] input;
		Queue<byte[]> message = null;
		//input the port number and process number
		System.out.println("Enter the port number : ");
	    Scanner sc = new Scanner(System.in);
		port_num = sc.nextInt();
//		System.out.println(port_num);
		System.out.println("Enter the process number : ");
		proc_num = sc.nextInt();
		
		//init the client socket array and is os array
		clientSocket = new Socket[proc_num];
		is = new DataInputStream[proc_num];
		os = new DataOutputStream[proc_num];
		input = new byte[3];
		message = new LinkedList<byte[]>();
		//create the server
		try {
	           my_server = new ServerSocket(port_num);
	    }
	    catch (IOException e) {
	           System.out.println(e);
	    } 
		
		//wait for all the clients to connect
		System.out.println("Waiting for all the clients to connect... \n");
		int i = 0;
		while(true)
		{
			try 
			{
				clientSocket[i] = my_server.accept();
				is[i] = new DataInputStream(clientSocket[i].getInputStream());
				os[i] = new DataOutputStream(clientSocket[i].getOutputStream());
				i++;
	           
			}   
			catch (IOException e) 
			{
				System.out.println(e);
			}
			if(i == proc_num)
				break;
		}
		System.out.println("Connection completed \n");
		
		//listen to the clients
		byte[] temp;
		temp = new byte[3];
		while(true)
		{
			for(int j = 0; j < proc_num; j++)
			{
				 try{
                     is[j].read(input);
    //                 System.out.println("here\n" + input[0]);
                      message.add(input);
                 }
                 catch(IOException e){ e.printStackTrace(); }
			}
			while(message.peek() != null)
			{
				temp = message.poll();
				System.out.println("temp\n");
				System.out.println(temp[0]);
				
				try{
				os[temp[0]].write(1);
				}
				catch(IOException e) 
				{
					e.printStackTrace();
				}
				
				
			}
		}
		
	}
}
