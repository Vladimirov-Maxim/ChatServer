package ChatServer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    public static int countId = 1;

    private String name;
    final private String idClient;

    final private ChatServer chatServer;
    final private Socket socket;

    private PrintStream out;
    private Scanner in;

    public Client(Socket socket, ChatServer chatServer) {

        this.socket = socket;
        this.chatServer = chatServer;
        this.idClient = String.format("%05d", countId++);

        new Thread(this).start();

    }

    public void receiveMessage(String message) {
        out.println(message);
    }

    @Override
    public String toString() {
        return name + " (id: " + idClient + ")";
    }

    @Override
    public void run() {

        try {

            in = new Scanner(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");

            out.println("Please, enter your name:");
            name = in.nextLine();
            out.println("You connect!");

            String input = in.nextLine();
            while (!input.equals("bye")) {

                chatServer.sendAll(input, this);
                input = in.nextLine();

            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
