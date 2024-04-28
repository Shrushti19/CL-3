//Server
import java.util.*;
import java.sql.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
interface DBInterface extends Remote
{
    public String input(String name, String date, String operation) throws RemoteException;
}

public class Server extends UnicastRemoteObject implements DBInterface
{
    Map<String, String[]> dateMap = new HashMap<>();
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

    public String input(String name, String date, String operation)
    {
        try
        {
            if(operation.equals("Book"))
            {
                if(dateMap.containsKey(date))
                {
                    String[] str = dateMap.get(date);
                    int nextEmptyIndex = -1;
                    for (int i = 0; i < str.length; i++) 
                    {
                        if (str[i].equals(null)) 
                        {
                            nextEmptyIndex = i;
                            break;
                        }
                    }
                    if(nextEmptyIndex == -1) 
                    {
                        return "Hotel is full for Date "+date;
                    }
                    else
                    {
                        str[nextEmptyIndex] = name;
                        // dateMap.get(date) = str;
                        return "\nBooking for "+name+" for Date "+date+" has been confirmed.";
                    }
                }
                else
                {
                    System.out.println("In book section else");
                    String[] strings = {name, "", "", "", "", "", "", "", "", ""};
                    dateMap.put(date, strings);
                    return "\nBooking for "+name+" for Date "+date+" has been confirmed.";
                }
            }
            else if(operation.equals("Cancel"))
            {
                if (dateMap.containsKey(date)) 
                {
                    String[] str = dateMap.get(date);
        
                    int index = -1;
                    for (int i = 0; i < str.length; i++) 
                    {
                        if (str[i].equals(name)) 
                        {
                            index = i;
                            break;
                        }
                    }
        
                    if (index == -1) 
                    {
                        return "No bookings available with this name.";
                        
                    } else 
                    {
                        str[index] = "";
                        // dateMap.get(date) = str;
                        return "\nCancellation for "+name+" for Date "+date+" on "+date+" is successful.";
                    }
                } 
                else 
                {
                    return "No bookings available on this date.";
                }
            }
        }
        catch (Exception e)
        {
            return "ERROR: " +e.getMessage(); 
        }
        return "";
    }
}
//Client
import java.sql.*;
import java.rmi.*;
import java.io.*;
import java.util.*;
import java.util.Vector.*;
import java.lang.*;
import java.rmi.registry.*;

public class Client
{ 
    static String send_details;
    public static void main(String args[])
    {
        Client c = new Client();
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        int ch;
        String name, date, type_of_room, breakfast;
        try 
        { 
            Registry r1 = LocateRegistry.getRegistry ( "localhost", 1030);
            DBInterface DI=(DBInterface)r1.lookup("DBServ");
            do
            {
                System.out.println("1. Book Room for Specific Guest\n2. Cancel the Booking for the Guest \nEnter ur choice");
                ch = Integer.parseInt(b.readLine());
                switch(ch)
                {
                    case 1:
                    {
                        System.out.println(" \nEnter the name of the guest:");
                        name = b.readLine();
                        System.out.println(" \nEnter Check-In Date(YYYY-MM-DD):");
                        date = b.readLine();
                        System.out.println(" \nEnter which type of room you prefer(Standard / Deluxe):");
                        type_of_room = b.readLine();
                        System.out.println(" \nDo you want to include Breakfast(Yes / No):");
                        breakfast = b.readLine();
                        send_details = DI.input(name, date, "Book");
                        System.out.println(" \n"+send_details+"\n");
                        break;
                    }
                    case 2:
                    {
                        System.out.println(" \nEnter the name of the guest:");
                        name = b.readLine();
                        System.out.println(" \nEnter Check-In Date(YYYY-MM-DD):");
                        date = b.readLine();
                        send_details = DI.input(name, date, "Cancel");
                        System.out.println(" \n"+send_details+"\n");
                        break;
                    }
                }
            }while(ch>0);
        }       
        catch (Exception e)
        {
            System.out.println("ERROR: " +e.getMessage());
        }
    }
}
