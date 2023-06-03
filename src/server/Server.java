package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.sql.*;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean isDone;

    public Server() {
        this.isDone = false;
    }

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(6666);
            while (!isDone){
            Socket client = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(client);
            executorService = Executors.newCachedThreadPool();
            executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class ClientHandler implements Runnable{

    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private Socket client;

    @Override
    public void run() {
        try {
            java.sql.Connection connection = DriverManager.getConnection("jdbc:sqlite:jdbc.db");
            Statement statement = connection.createStatement();
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String command;
            while(!(command = (String) in.readObject()).equals("exit") && (command = (String) in.readObject()) != null ) {
                if (command.equals("sign-up")) {
                    User x;
                    signUpServer(x=(User)in.readObject());
                }
                else if (command.equals("sign-in")) {

                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public ClientHandler(Socket client) {
        this.client=client;
    }

    public static void signUpServer(User theUser) throws SQLException {
        java.sql.Connection connection = DriverManager.getConnection("jdbc:sqlite:jdbc.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

        while(resultSet.next()){
            if(resultSet.)
        }

        statement.executeUpdate("INSERT INTO user(id,firstName,lastName,email,phoneNumber,password,country,birthDate,registerDate) " +
                "VALUES "+ theUser.getId()+theUser.getFirstName()+theUser.getLastName()+theUser.getEmail()+theUser.getPhoneNumber()+
                theUser.getPassword()+theUser.getCountry()+theUser.getBirthDate()+theUser.getRegisterDate());

    }
}





