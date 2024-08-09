package cabal;

public class Main {

    public static void main(String[] args) {
        AuthServer authServer = new AuthServer();
        authServer.start(38101);
    }

}
