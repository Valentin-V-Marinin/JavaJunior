package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Socket socket;
    private final String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public Client(Socket socket, String userName){
        this.socket = socket;
        name = userName;
        try
        {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }


    }

    /**
     * Слушатель для входящих сообщений
     */
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()){
                    try {
                        message = bufferedReader.readLine();
                        if (isAcceptableMessage(message)) {
                            System.out.println(message);
                        }
                    }
                    catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    /**
     * Отправить сообщение
     */
    public void sendMessage(){
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(name + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Проверка - является ли сообщение персональным для данного клиента или общим,
     * в таком случае метод вернет true, если это персональное сообщение для другого клиента,
     * метод вернет false
     * @param msg - проверяемое сообщение
     * @return - результат проверки true/false
     */
    private boolean isAcceptableMessage(String msg){
        boolean result = false;
        if (msg.contains("@")) {

            int begin_index_substr = msg.indexOf("@")+1;
            int end_index_substr = begin_index_substr + msg.substring(msg.indexOf(" ")+1).indexOf(" ")-1;
            String msgName = msg.substring(begin_index_substr, end_index_substr);

            if (msgName.equalsIgnoreCase(name)) result = true;
        } else {
            result = true;
        }

        return result;
    }


}
