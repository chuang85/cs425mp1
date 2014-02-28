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

import process.Process;

public class Main {
	
	public static void main(String args[])
	{
		Server server = new Server();
		int proc_num = 0;
		int snapshot_num = 0;
		//input the port number
		System.out.println("Enter the port number : ");
		Scanner scanner = new Scanner(System.in);
		server.portNum = scanner.nextInt();
		
		//input for the number of processes need
		System.out.println("Enter the process number : ");
		proc_num = scanner.nextInt();
		server.procCount = proc_num;
		
		
		//input number of snapshots
		System.out.println("Enter the snapshot number : ");
		snapshot_num = scanner.nextInt();
		server.snapshot_num = snapshot_num;
		
		
		//the process array,    starting from index 1       !!!!!!!!!!!!!!!!!!!!!!
		Process[] p = new Process[proc_num+1]; 
		for(int i = 1; i < proc_num+1; i++)
		{
			//give each process 100 money and 100 wigets to start
			p[i] = new Process(100, 100, proc_num);
		}
		new Thread(server).start();
		
		for(int i = 1; i < proc_num+1; i++)
		{
			new Thread(p[i]).start();
			try {
				  Thread.sleep(5000);
			} catch (InterruptedException ie) {
				    //Handle exception
			}
		}
		
		
		
	}
}
