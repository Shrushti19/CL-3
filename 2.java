//RMIClient//
import java.sql.*;
import java.rmi.*;
import java.io.*;
import java.util.*;
import java.util.Vector.*;
import java.lang.*;
import java.rmi.registry.*;

public class Client
{
	static String name1,name2,name3;
	public static void main(String args[])
	{
		Client c=new Client();
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		 int ch;

		 try {
			 Registry r1 = LocateRegistry.getRegistry ( "localhost", 1030);
			 DBInterface DI=(DBInterface)r1.lookup("DBServ");
			 do{
				System.out.println("1.Send input strings\n2.Display concatenated string \nEnter your choice");
		 		ch= Integer.parseInt(b.readLine());

		 		switch(ch){
		 		case 1:
		 			System.out.println(" \n Enter first string:");
		 			name1=b.readLine();
		 			System.out.println(" \n Enter second string:");
		 			name2=b.readLine();
		 			name3=DI.input(name1,name2);
		 			break;
		 		case 2:
		 			System.out.println("\n Concatenated String is : ");
		 			System.out.println(" " +name3+"");
	 	 			break;
				}
 			}while(ch>0);

 		}
 		catch (Exception e)
 		{
 			System.out.println("ERROR: " +e.getMessage());
 		}
	}
}



//Server

import java.sql.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;

interface DBInterface extends Remote
{
    public String input(String name1,String name2) throws RemoteException;
}

public class Server extends UnicastRemoteObject implements DBInterface
{
    String name3;
    public Server() throws RemoteException
    { 
        try
        {
            System.out.println("Initializing Server\nServer Ready");
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " +e.getMessage());
        }
    }

    public static void main(String[] args)
    { 
        try
        { 
            Server rs=new Server();
            java.rmi.registry.LocateRegistry.createRegistry(1030).rebind("DBServ", rs);
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " +e.getMessage());
        }
    }

    public String input(String name1,String name2)
    {
        try
        {
            name3=name1.concat(name2);
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " +e.getMessage());  
        }
        return name3;
    }
}
