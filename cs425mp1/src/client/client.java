package client;
import java.io.*;
import java.net.*;

public class client {
	 Socket client = null;  
     DataOutputStream os = null;
     DataInputStream is = null;
     public client(String hostName, int port_num)
     {
    	 try {
             client = new Socket(hostName, port_num);
             os = new DataOutputStream(client.getOutputStream());
             is = new DataInputStream(client.getInputStream());
         } catch (UnknownHostException e) {
             System.err.println("Don't know about host: hostname");
         } catch (IOException e) {
             System.err.println("Couldn't get I/O for the connection to: hostname");
         }
    	 
     }
     
     public void connect()
     {
    	 System.out.println("client getting connection\n");
    	 byte[] temp = new byte[3];
    	 temp[0] = 0;
    	 for(int i = 1; i < 3; i++)
    		 temp[i] =1;
    	 try
    	 {
    		 os.write(temp);
    	 }
    	 catch (IOException e) {
	           System.out.println(e);
	     } 
    	 
    	 	byte[] temp2 = new byte[3];
    	 while(true)
    	 {
    	 	try
       	 {
       		 is.read(temp2);
       	 }
       	 catch (IOException e) {
   	           System.out.println(e);
   	     } 
    	 
    	 System.out.println(temp2);
    	 }
     }
}
