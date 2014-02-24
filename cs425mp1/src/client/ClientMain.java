package client;

public class ClientMain {
	
	public static final int PORT = 6666;
	
	public static void main(String[] args)
	{
		Client my_client = new Client("localhost", PORT);
		my_client.connect();
		
	}
}
