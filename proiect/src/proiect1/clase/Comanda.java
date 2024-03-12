package proiect1.clase;

import proiect1.*;
import proiect1.excepții.CantitateNegativaException;
import proiect1.excepții.CodGresitException;
import proiect1.excepții.StocInsuficientException;

import java.io.Serializable;
import java.util.*;

public class Comanda implements Serializable {
    private Client client;
    private Map<Integer,Integer> listaProduse= new HashMap<>();
    private float valoare;
    private Date dataPlasare;
    private static int counter=0;
    private int codComanda;




    public Comanda(Client client, Date dataPlasare) {
        codComanda=counter;
        counter++;
        this.client = client;
        this.dataPlasare=dataPlasare;
        valoare=0;

    }



    public static void setCounter(int counter) {
        Comanda.counter = counter;
    }

    public Comanda() {
    }

    public int getCodComanda() {
        return codComanda;
    }


    public Date getDataPlasare() {
        return dataPlasare;
    }

    public void setDataPlasare(Date dataPlasare) {
        this.dataPlasare = dataPlasare;
    }

    public void adaugaProdus(Produs p, int cantitate) throws StocInsuficientException, CodGresitException, CantitateNegativaException {
        int canti=p.getCantitateStoc();
        if(p.getCantitateStoc()>=cantitate)
        {if(!listaProduse.containsKey(p.getCodProdus())) {
            listaProduse.put(p.getCodProdus(), cantitate);

        }
            else {
                int cant=listaProduse.get(p.getCodProdus());
                listaProduse.replace(p.getCodProdus(),cant+cantitate);

        }
            Main.emag.getProdusCod(p.getCodProdus()).setCantitateStoc(canti-cantitate);


        }

        else throw new StocInsuficientException("Stoc insuficient!");
    }
    public void modificaCantitate(Produs p, int cantitate) throws StocInsuficientException, CodGresitException, CantitateNegativaException {


            int cant=p.getCantitateStoc();

            int cant1=listaProduse.get(p.getCodProdus());
        if(cant+cant1-cantitate>=0) {


            listaProduse.replace(p.getCodProdus(), cantitate);
            Main.emag.getProdusCod(p.getCodProdus()).setCantitateStoc(cant+cant1-cantitate);
        }
        else throw new StocInsuficientException("Stoc insuficient!");
    }
    public void stergeProdus(Produs p) throws CodGresitException, CantitateNegativaException {
        int cant = listaProduse.get(p.getCodProdus());
        listaProduse.remove(p.getCodProdus());
        int cantRamasa=p.getCantitateStoc();
        Main.emag.getProdusCod(p.getCodProdus()).setCantitateStoc(cantRamasa+cant);
    }
    public void calculeazaValoare() throws CodGresitException {
        this.valoare=0;
        for (Map.Entry<Integer,Integer> entry : listaProduse.entrySet())
            this.valoare+=Main.emag.getProdusCod(entry.getKey()).getPret()*entry.getValue();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Map<Integer, Integer> getListaProduse() {
        return listaProduse;
    }


    public float getValoare() throws CodGresitException {
        calculeazaValoare();
        return valoare;
    }

    @Override
    public String toString() {
        try {
            calculeazaValoare();
        } catch (CodGresitException e) {
            e.printStackTrace();
        }
        try {
            return "Comanda{" +
                    "client=" + client +

                    ", valoare=" + valoare +
                    ", cod=" + codComanda +
                    ", listaProduse=" +"\n"+ afiseazaListaProduse() +
                    '}';
        } catch (CodGresitException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String afiseazaListaProduse() throws CodGresitException {
        String produse="";
        for (Map.Entry<Integer,Integer> entry : listaProduse.entrySet()){
            produse+="Produsul: "+Main.emag.getProdusCod(entry.getKey()).toString()+" in cantitatea "+entry.getValue()+"\n";
        }
        return produse;
    }
    public Produs getProdus(int cod) throws CodGresitException {
        Produs pi = new Produs();
        int gasit11=0;
        for (Map.Entry<Integer,Integer> entry : listaProduse.entrySet()){
            if(entry.getKey()==cod){
                gasit11=1;
                pi=Main.emag.getProdusCod(entry.getKey());
            }
        }
            if(gasit11==1){
                return pi;
            }
            throw new CodGresitException("Codul este gresit!");

    }
    public String printareFactura(){
        String s="Factura"+"\n";
        for (Map.Entry<Integer,Integer> entry : listaProduse.entrySet()){
            int cod=entry.getKey();
            try {
                Produs p= Main.emag.getProdusCod(cod);
                 s+=p.getDenumire()+"......."+entry.getValue()+" "+p.getUnitateMasura()+" X "+p.getPret()+"......."+entry.getValue()*p.getPret()+"\n";
            } catch (CodGresitException e) {
                e.printStackTrace();
            }
        }
        try {

            s+="Valoare totala: "+this.getValoare();
        } catch (CodGresitException e) {
            e.printStackTrace();
        }
        return s;
    }
    public static Comparator<Comanda> comparatorValoare = new Comparator<Comanda>() {
        @Override
        public int compare(Comanda o1, Comanda o2) {
            try {
                if(o1.getValoare()>o2.getValoare())
                    return 1;
                else if(o1.getValoare()<o2.getValoare()){
                    return -1;
                }
                else return 0;
            } catch (CodGresitException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };
    public static Comparator<Comanda> comparatorData = new Comparator<Comanda>() {
        @Override
        public int compare(Comanda o1, Comanda o2) {

                if(o1.getDataPlasare().before(o2.getDataPlasare()))
                    return 1;
                else if(o1.getDataPlasare().after(o2.getDataPlasare())){
                    return -1;
                }
                else return 0;


        }
    };

}
