package client;

public class client_main {
	public static void main(String[] args)
	{
		client my_client = new client("localhost", 12345);
		my_client.connect();
		
	}
}
