package proiect1.excepții;

public class NrTelefonInvalidException extends Exception{
    public NrTelefonInvalidException() {
        super();
    }

    public NrTelefonInvalidException(String message) {
        super(message);
    }
}
