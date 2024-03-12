package proiect1.excepții;

public class ClientDejaExistentException extends Exception{
    public ClientDejaExistentException() {
        super();
    }

    public ClientDejaExistentException(String message) {
        super(message);
    }
}
