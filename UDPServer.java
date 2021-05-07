import java.net.*;
import java.io.*;
import java.util.*;
public class UDPServer {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //print message so the user knows the server is up
        System.out.println("the server is up and running");
        // create a socket to communicate over set inital value to null
        DatagramSocket aSocket = null;
        // start try catch block to catch any errors and print out the message
        System.out.println("please enter a port number to open");
        int portNum = in.nextInt();

        try{
            //set buffer
            byte[] buffer = new byte[1000];
            //create a socket open on port1000
            aSocket = new DatagramSocket(portNum);
            //run loop looking for a request
            while(true){
                //create a packet with the buffer in mind
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                // recieve a request
                aSocket.receive(request);
                // create a reply with all capital lettera

                String replyString = new String(request.getData());
                System.out.println("Recieved message: " + replyString);
                replyString = replyString.toUpperCase();
                System.out.println("Sending back: " + replyString);
                // convert to bytes
                byte[] replyBytes = replyString.getBytes();
                // create a reply packet to send back
                DatagramPacket reply = new DatagramPacket(replyBytes, replyBytes.length, request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
        }catch (SocketException e){
            System.out.println("Socket:" + e.getMessage());
        }catch (IOException e){
            System.out.println("IO:" + e.getMessage());
        }finally {
            if(aSocket != null){
                aSocket.close();
            }
        }
    }
}
