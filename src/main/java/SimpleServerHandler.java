import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mnoe on 30.11.2016.
 */
public class SimpleServerHandler implements Runnable {
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    SimpleServer simpleServer;
    Thread thread;
    boolean exitThread = false;

    public SimpleServerHandler(Socket socket, SimpleServer server) throws IOException {
        this.socket = socket;
        this.simpleServer = server;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            shutdownAndCleanup();
        }

        thread = new Thread(this);
        thread.start();
    }

    void initiateShutdown(){
        exitThread = true;
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutdownAndCleanup(){
        try {
            this.socket.close();
            this.simpleServer.handlerShutdownCallback(this);
        } catch (IOException e){
            System.out.println("Error during socket shutdown");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(!exitThread) {
            out.flush();
            String input;
            try {
                input = in.readLine();
                System.out.println("Read " + input + " from client");

                parseCommand(input);

                out.write(input + "\n");
                out.flush();
            } catch (IOException e) {
                if(exitThread){
                    System.out.print("Closed socket because of ordered shutdown.");
                } else {
                    System.out.print("Unexpected socket error! Raising exception!");
                    e.printStackTrace();
                    exitThread = true;
                }
            }
        }
        shutdownAndCleanup();
    }

    private void parseCommand(String input) {
        switch(input) {
            case "global shutdown":
                out.print("Initiating global shutdown!");
                this.simpleServer.globalShutdown();
                break;
            case "exit": case "local shutdown":
                out.print("Initiating local shutdown!");
                initiateShutdown();
                break;
            default:
                out.print("Unknown command: " + input);
                break;
        }
    }
}
