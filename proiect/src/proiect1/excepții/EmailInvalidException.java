package proiect1.excepții;

public class EmailInvalidException extends Exception{
    public EmailInvalidException() {
        super();
    }

    public EmailInvalidException(String message) {
        super(message);
    }
}
