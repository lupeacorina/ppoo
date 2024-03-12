package proiect1.clase;

import proiect1.excepții.ClientMinorException;
import proiect1.excepții.EmailInvalidException;
import proiect1.excepții.NrTelefonInvalidException;
import proiect1.excepții.StringPreaScurtException;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Serializable {
    private int codClient;
    private String denumire;//validat
    private Date dataNasterii;//validat
    private String email;//validat
    private String nrTelefon;//validat
    private String adresa;//validat
    private static int counter=0;

    public Client(String denumire, Date dataNasterii, String email, String nrTelefon, String adresa) throws ClientMinorException, EmailInvalidException, NrTelefonInvalidException, StringPreaScurtException {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Pattern p = Pattern.compile("^((07))[0-9]{8}$");

        Date date = Date.from(ZonedDateTime.now().minusYears(18).toInstant());
        this.codClient = counter;
        if(denumire.length()>=1)
        this.denumire = denumire;
        else throw new StringPreaScurtException("Denumirea este prea scurta!");
        if(dataNasterii.before(date))
        this.dataNasterii = dataNasterii;
        else throw new ClientMinorException("Trebie sa aveti minim 18 ani!");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
        this.email = email;
        else throw new EmailInvalidException("Email invalid!");
        Matcher m = p.matcher(nrTelefon);
        if(m.matches())
        this.nrTelefon = nrTelefon;
        else throw new NrTelefonInvalidException("Numar de telefon invalid!");
        if(adresa.length()>=1)
        this.adresa = adresa;
        else throw new StringPreaScurtException("Adresa este prea scurta!");
        counter++;
    }

    public static void setCounter(int counter) {
        Client.counter = counter;
    }

    public Client() {
    }

    public int getCodClient() {
        return codClient;
    }


    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) throws StringPreaScurtException {
        if(denumire.length()>=1)
        this.denumire = denumire;
        else throw new StringPreaScurtException("Denumirea este prea scurta!");
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) throws ClientMinorException {
        Date date = Date.from(ZonedDateTime.now().minusYears(18).toInstant());
        if(dataNasterii.before(date))
        this.dataNasterii = dataNasterii;
        else throw new ClientMinorException("Trebie sa aveti minim 18 ani!");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailInvalidException {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
        if(matcher.matches())
        this.email = email;
        else throw new EmailInvalidException("Email invalid!");
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) throws NrTelefonInvalidException {
        Pattern p = Pattern.compile("^((07))[0-9]{8}$");
        Matcher m = p.matcher(nrTelefon);
        if(m.matches())
        this.nrTelefon = nrTelefon;
        else throw new NrTelefonInvalidException("Numar de telefon invalid!");
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) throws StringPreaScurtException {
        if(adresa.length()>=1)
        this.adresa = adresa;
        else throw new StringPreaScurtException("Adresa este prea scurta!");
    }

    @Override
    public String toString() {
        return "Client{" +
                "codClient='" + codClient + '\'' +
                ", denumire='" + denumire + '\'' +
                ", dataNasterii=" + dataNasterii +
                ", email='" + email + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}
