package ChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Runnable{

    ServerSocket serverSocket;
    private List<Client> clientList = new ArrayList<>();

    public ChatServer() {

        // создаем серверный сокет на порту 1234
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendAll(String message, Client sender) {

        for (Client client : clientList) {
            if (client == sender) {
                client.receiveMessage("Your message: " + message);
            } else {
                client.receiveMessage(sender + ": " + message);
            }
        }

    }

    @Override
    public void run() {

        while (true) {

            System.out.println("Waiting...");

            // ждем клиента
            Socket socket;
            try {
                socket = serverSocket.accept();

                System.out.println("ChatServer.Client connected!");

                clientList.add(new Client(socket, this));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws IOException {

        new Thread(new ChatServer()).start();

    }

}
