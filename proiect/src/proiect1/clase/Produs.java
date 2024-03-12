package proiect1.clase;

import proiect1.excepții.StringPreaScurtException;
import proiect1.excepții.CantitateNegativaException;
import proiect1.excepții.PretPreaMareException;
import proiect1.excepții.PretZeroException;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Produs implements Serializable {
    private String denumire;
    private float pret;
    private int cantitateStoc;
    private UnitateMasura unitateMasura;
    private String descriere;
    private String producator;
    private Map<Date,Float> istoricPreturi = new HashMap<>();
    private int[] ratinguri;
    private int codProdus;
    private static int counter=0;

    public static void setCounter(int counter) {
        Produs.counter = counter;
    }

    public Produs(String denumire, float pret, int cantitateStoc,  String descriere, String producator, Date dataAdaugareProdus,UnitateMasura unitateMasura) throws StringPreaScurtException, PretZeroException, CantitateNegativaException {
        if(denumire.length()>=1)
        this.denumire = denumire;
        else throw new StringPreaScurtException("Denumirea este prea scurta!");
        if(pret>0)
        this.pret = pret;
        else throw new PretZeroException("Pretul trebuie sa fie pozitiv!");
        if(cantitateStoc>0)
        this.cantitateStoc = cantitateStoc;
        else throw new CantitateNegativaException("Cantitatea trebuie sa fie pozitiva!");
        this.unitateMasura = unitateMasura;
        this.descriere = descriere;
        if(producator.length()>=1)
        this.producator = producator;
        else throw new StringPreaScurtException("Numele producatorului este prea scurt!");
        istoricPreturi.put(dataAdaugareProdus,pret);
        this.codProdus=counter;
        counter++;

    }

    public int getCodProdus() {
        return codProdus;
    }

    public Produs() {
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) throws StringPreaScurtException {
        if(denumire.length()>=1)
            this.denumire = denumire;
        else throw new StringPreaScurtException("Denumirea este prea scurta!");
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret,Date dataModificarePret) throws PretPreaMareException, PretZeroException {
        if(pret>0)
        {
            if (istoricPreturi.size()>0)
            {Date date = Date.from(ZonedDateTime.now().minusMonths(4).toInstant());
        if (pret<=1.2*calculeazaMediePreturi(date))
        {this.pret = pret;
        istoricPreturi.put(dataModificarePret,pret);
        }
        else throw new PretPreaMareException("Pretul nu trebuie sa fie mai mare decat 1.2*media preturilor din ultimele 4 luni");}
            else {
                this.pret=pret;
            }
        }

        else throw new PretZeroException("Pretul trebuie sa fie pozitiv");
    }

    public int getCantitateStoc() {
        return cantitateStoc;
    }

    public void setCantitateStoc(int cantitateStoc) throws CantitateNegativaException {
        if(cantitateStoc>=0)
            this.cantitateStoc = cantitateStoc;
        else throw new CantitateNegativaException("Cantitatea negativa!");
    }

    public UnitateMasura getUnitateMasura() {
        return unitateMasura;
    }

    public void setUnitateMasura(UnitateMasura unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) throws StringPreaScurtException {
        if(producator.length()>=1)
            this.producator = producator;
        else throw new StringPreaScurtException("Numele producatorului este prea scurt!");
    }

    public Map<Date,Float> getIstoricPreturi() {
        return istoricPreturi;
    }


    public int[] getRating() {
        return ratinguri;
    }

    public void adaugaRating(int rating) {
        if(ratinguri!=null)
        {int[] newRatinguri = new int[ratinguri.length+1];
        for(int i=0;i<ratinguri.length;i++)
            newRatinguri[i]=ratinguri[i];
        newRatinguri[ratinguri.length]=rating;
        ratinguri=newRatinguri;}
        else {
            ratinguri= new int[1];
            ratinguri[0]=rating;
        }
    }
    public float calculeazaRating(){
        if(ratinguri!=null)
        {float s=0;
        for(int i=0;i<ratinguri.length;i++)
            s+=ratinguri[i];
        s=s/ratinguri.length;
        return s;}
        else {
            return 5;
        }
    }
    public float calculeazaMediePreturi(Date dataFixata){
        if(istoricPreturi!=null)
        {float s=0;
        for (Map.Entry<Date,Float> entry : istoricPreturi.entrySet())
            if(entry.getKey().after(dataFixata))
                 s+=entry.getValue();
        s=s/istoricPreturi.size();
        return s;}
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        float rating=5;
        if(ratinguri!=null){
            rating=calculeazaRating();
        }
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", cantitateStoc=" + cantitateStoc +
                ", unitateMasura='" + unitateMasura + '\'' +
                ", descriere='" + descriere + '\'' +
                ", producator='" + producator + '\'' +
                ", istoricPreturi=" + istoricPreturi +
                ", ratinguri=" + Arrays.toString(ratinguri) +
                ", codProdus='" + codProdus + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
