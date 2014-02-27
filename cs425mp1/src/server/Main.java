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


public class Main {
	
	public static void main(String args[])
	{
		Server server = new Server();
		
		//input the port number
		System.out.println("Enter the port number : ");
		Scanner scanner = new Scanner(System.in);
		server.portNum = scanner.nextInt();
		
		//input for the number of processes need
		System.out.println("Enter the process number : ");
		server.procCount = scanner.nextInt();
		
		
	}
}
