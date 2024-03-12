package proiect1.clase;

import proiect1.excepții.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

public class Magazin implements Serializable, IVanzare {
    private String denumire;
    private List<Produs> produse = new ArrayList<>();
    private Client[] clienti;
    private List<Comanda> comenzi=new ArrayList<>();






    public Magazin(String denumire) {
        this.denumire = denumire;

    }


    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public List<Produs> getProduseDisponibile() {
        List<Produs> produseDisponibile=new ArrayList<>();
        for(Produs p:produse)
            if (p.getCantitateStoc()>0)
                produseDisponibile.add(p);
        return produseDisponibile;
    }
    public List<Produs> getProduse() {
    return produse;
    }


    public Client[] getClienti() {
        return clienti;
    }


    public List<Comanda> getComenzi() {
        return comenzi;
    }

    public void adaugaProdus(Produs p){
        produse.add(p);
    }
    public void stergeProdus(Produs p){
        produse.remove(p);
    }
    public void adaugaClient(Client c) throws ClientDejaExistentException {
        List<Client> clientList=new ArrayList<>();
        if(clienti!=null)
        {
            for(int i=0;i<clienti.length;i++)
                clientList.add(clienti[i]);
        }
        int ok=1;
        for (Client c1:clientList){
            if(c1.getEmail().equals(c.getEmail()))
                ok=0;
        }
        if(ok==1)
        {clientList.add(c);
        clienti= new Client[clientList.size()];
        int i=0;
        for (Client cl:clientList){
            clienti[i]=cl;
            i++;
        }
        }
        else throw new ClientDejaExistentException("Clientul deja exista");
    }
    public void stergeClient(Client c){
//        List<Client> clientList = Arrays.asList(clienti);
//        clientList.remove(c);
//        clienti = (Client[]) clientList.toArray();

        List<Client> clientList=new ArrayList<>();
        if(clienti!=null)
        {
            for(int i=0;i<clienti.length;i++)
                clientList.add(clienti[i]);
        }

        {clientList.remove(c);
            clienti= new Client[clientList.size()];
        int i=0;
        for (Client cl:clientList){
            clienti[i]=cl;
            i++;
        }}
    }
    public void adaugaComanda(Comanda c) throws ComandaGoalaException, CodGresitException {
        float val=c.getValoare();
        if(val>0)
        comenzi.add(c);
        else {
            throw new ComandaGoalaException("Comanda nu poate fi goala!");
        }


    }
    public void anuleazaComanda(Comanda c) throws CantitateNegativaException {
        for (Map.Entry<Integer,Integer> entry : c.getListaProduse().entrySet()){
            for(Produs p:produse){
                if (p.getCodProdus()==entry.getKey()){
                    p.setCantitateStoc(p.getCantitateStoc()+entry.getValue());
                }
            }}
    }
    public void stergeComanda(Comanda c) throws TimpulDeAnulareAComenziiExpiratException, CantitateNegativaException {
        Date date = Date.from(ZonedDateTime.now().minusHours(24).toInstant());
        if(c.getDataPlasare().after(date))
        {comenzi.remove(c);
        for (Map.Entry<Integer,Integer> entry : c.getListaProduse().entrySet()){
            for(Produs p:produse){
                if (p.getCodProdus()==entry.getKey()){
                    p.setCantitateStoc(p.getCantitateStoc()+entry.getValue());
                }
            }
        }
    }
        else throw new TimpulDeAnulareAComenziiExpiratException("Nu poti anula o comanda dupa 24 de ore!");
    }
    @Override
    public String toString() {
        return "Magazin{" +
                "denumire='" + denumire + '\'' +
                ", produse=" + produse +
                ", clienti=" + Arrays.toString(clienti) +
                ", comenzi=" + comenzi +
                '}';
    }
    public List<Comanda> vizualizareComenziPtrUnClient(Client c){
        List<Comanda>comandaList = new ArrayList<>();
        for (Comanda com:comenzi){
            if(com.getClient().getCodClient()==c.getCodClient()){
                comandaList.add(com);
            }
        }
        return comandaList;

    }
    public Produs getProdusCod(int cod) throws CodGresitException {
        int gasit = 0;
        Produs prod = new Produs();
        for (Produs p : produse) {
            if (p.getCodProdus() == cod) {
                gasit = 1;
                prod = p;

            }
        }
        if (gasit == 1) {
            return prod;
        }

          throw  new CodGresitException("Cod gresit!");

    }
    public Client getClientCod(int cod) throws CodGresitException, FaraClientiException {
        if(clienti!=null)
        {int gasit21=0;
        Client client = new Client();
        for (int i=0;i<clienti.length;i++)
            if(clienti[i].getCodClient()==cod){
                gasit21=1;
                client=clienti[i];
            }
        if (gasit21==1){
            return client;
        }
        throw new CodGresitException("Cod gresit!");}
        else {
            throw new FaraClientiException("Nu exista clienti!");
        }
    }
    public Comanda getComandaCod(int cod,Client client) throws CodGresitException {
        int gc=0;
        Comanda deAnulat=new Comanda();
        for(Comanda c:vizualizareComenziPtrUnClient(client)){
            if(c.getCodComanda()==cod){
                gc=1;
                deAnulat=c;
            }
        }
        if(gc==1){
            return deAnulat;
        }
        throw new CodGresitException("Cod gresit!");
    }
    public boolean getProdusComandaCod(Client client,Produs pR) throws ProdusNecomandatExceptio {
        int gr2=0;
        for(Comanda c:vizualizareComenziPtrUnClient(client)){
            for (Map.Entry<Integer,Integer> entry : c.getListaProduse().entrySet()){
                if(pR.getCodProdus()==entry.getKey()){
                    gr2=1;

                }
            }


           }
            if(gr2==1){
                return true;
        }
            throw new ProdusNecomandatExceptio("Nu ati comandat acest produs!");
    }


    public List<Comanda> vizualizareComenziPtrUnClientOrdonateDupaValoare (Client c){
        List<Comanda>comandaList = new ArrayList<>();
        for (Comanda com:comenzi){
            if(com.getClient().getCodClient()==c.getCodClient()){
                comandaList.add(com);
            }
        }
        Collections.sort(comandaList,Comanda.comparatorValoare);
        return comandaList;

    }
    public List<Comanda> vizualizareComenziPtrUnClientOrdonateDupaData (Client c){
        List<Comanda>comandaList = new ArrayList<>();
        for (Comanda com:comenzi){
            if(com.getClient().getCodClient()==c.getCodClient()){
                comandaList.add(com);
            }
        }
        Collections.sort(comandaList,Comanda.comparatorData);
        return comandaList;

    }

}
