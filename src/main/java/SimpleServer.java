import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mnoe on 30.11.2016.
 */
public class SimpleServer {
    ServerSocket serverSocket;
    List<SimpleServerHandler> handlers = new ArrayList<>();
    boolean endServer = false;

    public SimpleServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    synchronized void handlerShutdownCallback(SimpleServerHandler handler) {
        handlers.remove(handler);
    }

    synchronized void globalShutdown(){
        endServer = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        try {
            while (!endServer) {
                Socket connectionSocket = serverSocket.accept();
                SimpleServerHandler handler = new SimpleServerHandler(connectionSocket, this);
                synchronized (this) {
                    handlers.add(handler);
                }
                System.out.println("New connection, starting new handler thread");
            }
        } catch(SocketException e) {
            if(endServer){
                System.out.println("Ordered shutdown, server socket closed");
            } else {
                System.out.println("Socket unexpectedly closed, raising exception");
                throw e;
            }
        } finally {
            for (SimpleServerHandler handler: handlers ) {
                handler.initiateShutdown();
                try {
                    handler.thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            serverSocket.close();
        }
    }
}
