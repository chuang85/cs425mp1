package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import message.RegularMessage;

public class Client {
	Socket client = null;
	ObjectOutputStream os = null;
	ObjectInputStream is = null;

	public Client(String hostName, int port_num) {
		try {
			client = new Socket(hostName, port_num);
			os = new ObjectOutputStream(client.getOutputStream());
			is = new ObjectInputStream(client.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to: hostname");
		}

	}

	public int getID() throws ClassNotFoundException {
		System.out.println("client getting connection, getting process id \n");
		int id = 0;
		while (id == 0) {
			try {
				id = (Integer) is.readObject();

			} catch (IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("This is the process's ID " + id);
		return id;
	}

	public void sendMessage(int widget, int money, int from, int to) {
		RegularMessage test_m = new RegularMessage(widget, money, from, to);
		try {
			os.writeObject((RegularMessage) test_m);
			// System.out.println(i);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void listen() throws ClassNotFoundException {
		RegularMessage my_m;
		while (true) {
			try {
				my_m = (RegularMessage) is.readObject();
				System.out.println(my_m.getFrom() + my_m.getTo());
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	/*public void connect() {

		byte[] temp = new byte[3];
		temp[0] = 0;
		for (int i = 1; i < 3; i++)
			temp[i] = (byte) i;

		try {
			os.write(temp);
		} catch (IOException e) {
			System.out.println(e);
		}

		byte[] temp2 = new byte[3];
		while (true) {
			try {
				is.read(temp2);
			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println("printing out temp \n");
			for (int i = 0; i < 3; i++)
				System.out.print(temp[i] + "\n");
		}
	}*/

}
