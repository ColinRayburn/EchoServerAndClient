import java.net.*;
import java.io.*;
import java.util.*;


public class UDPClient {
    public static void main(String[] args) {
        while(true){
            //initiate the datagram socket as null
            DatagramSocket aSocket = null;
            //create a scanner to take user input
            Scanner in = new Scanner(System.in);
            //start try block to handle errors
            try {
                aSocket = new DatagramSocket();
                // get message input from user
                System.out.println("Please enter desired message:");
                String message = in.nextLine();
                //convert into an array of bytes for UDP
                byte[] m = message.getBytes();
                // get ip address for desired location loop until a valid ip is given
                boolean flag = false;
                // create a pattern to check the user input ip address to fallow the general ip format
                String pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
                //create a variable to hold the ip address
                String destination = "";
                while (!flag) {
                    // take user input
                    System.out.println("please enter the ip address of the destination:");
                    destination = in.nextLine();
                    // if the input matches the pattern continue
                    if (destination.matches(pattern)) {
                        flag = true;
                        // if user input doesnt match pattern
                    } else {
                        // print error message and have them try again
                        System.out.println("please enter a correctly formatted ip address");
                    }
                }
                // conver string to inet address
                InetAddress aHost = InetAddress.getByName(destination);
                // get a port number loop until a valid int is given
                //create extra variable to track port number and use as a logical flag
                int serverPort = -1;
                flag = false;
                // loop until user enters a positive integer
                while (serverPort < 0 || !flag) {// we want both parameters to be false to break out of the loop we want the server port to be a positive int and it to be a valid integer
                    // try and catch error for when the user enters an integer
                    try {
                        System.out.println("please enter the desired port number:");
                        serverPort = in.nextInt();
                        flag = true;
                    } catch (InputMismatchException e) {
                        //catch the number that is larger than an integer loop until they enter a correct integer
                        //print error message
                        System.out.println("input too large");
                    }

                }
                // create the packet with the collected data
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                //send the request
                aSocket.send(request);
                //buffer to accept a response
                byte[] buffer = new byte[1000];
                // accept a reply with the buffer
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                //receive a reply
                aSocket.receive(reply);
                // print out reply
                System.out.println("Reply:" + new String(reply.getData()));
            //catch errors for the socket not being open
            } catch (SocketException e) {
                System.out.println("Socket:" + e.getMessage());
                // catch input or out put errors
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
                //catch error if the user inputs the wrong data type or nothing at all
            } catch (NoSuchElementException e) {
                System.out.println("Input Issue:" + e.getMessage());
                // if the socket has populated itself with something catch that 
            } finally {
                if (aSocket != null) {
                    aSocket.close();
                }
            }
        }
    }
}
//private ip address 192.168.254.16
//public ip address 47.157.243.151
