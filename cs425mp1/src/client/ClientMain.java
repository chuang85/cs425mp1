package client;

public class ClientMain {
	public static void main(String[] args)
	{
		Client my_client = new Client("localhost", 6666);
		my_client.connect();
		
	}
}
