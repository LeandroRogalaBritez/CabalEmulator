package cabal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthServer {

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(38101)) {
            System.out.println("Servidor iniciado e aguardando conexões na porta " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão estabelecida com " + clientSocket.getInetAddress().getHostAddress());
                String threadName = String.format(
                        "%s:%d",
                        clientSocket.getInetAddress().getHostAddress(),
                        clientSocket.getPort()
                );
                new Thread(new ClientSession(clientSocket), threadName).start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor na porta " + port);
            e.printStackTrace();
        }
    }

}
