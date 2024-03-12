package proiect1;

import proiect1.clase.*;
import proiect1.excepții.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    //ca magazin
    // -vezi produsele
    //-modifici pretul produselor
    //-adaugi produse
    //-stergi produse
    //-modifici informatii produs


    //ca client
    //-te adugi in lista de clienti
    //-iti vezi informatiile si le modifici
    //-vezi lista produselor disponibile
    //-vezi istoricul comenzilor
    //-creezi o noua comanda
    //-adaugi produse la noua comanda;
    //-stergi produse din noua comanda;
    //-modifici cantitaile produselor din noua comanda
    //-plasezi noua comanda
    //-stregi noua comanda
    //-te stergi pe tine
    //-sa lase un rating


    //salvare fisier
    //ratinguri

    public static Magazin emag;

    public static void scrieFisier(ObjectOutputStream os) {
        try {
            os = new ObjectOutputStream(new FileOutputStream("out.bin"));
            os.writeObject(emag);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ObjectInputStream is = null;
        ObjectOutputStream os = null;

        emag = new Magazin("Emagazin");

        File f = new File("out.bin");
        if (f.exists())
            try {
                is = new ObjectInputStream((new FileInputStream("out.bin")));
                emag = (Magazin) is.readObject();
                is.close();
                if (emag.getProduse().size() > 0) {
                    int nrProduse = emag.getProduse().size();
                    int[] codProduse = new int[nrProduse];
                    for (int i = 0; i < nrProduse; i++) {
                        codProduse[i] = emag.getProduse().get(i).getCodProdus();
                    }
                    int maxCodProdus = codProduse[0];
                    for (int i = 0; i < nrProduse; i++) {
                        if (maxCodProdus < codProduse[i])
                            maxCodProdus = codProduse[i];
                    }
                    Produs.setCounter(maxCodProdus + 1);
                }
                if (emag.getClienti() != null) {
                    int nrClienti = emag.getClienti().length;
                    int[] codClienti = new int[nrClienti];
                    for (int i = 0; i < nrClienti; i++) {
                        codClienti[i] = emag.getClienti()[i].getCodClient();
                    }
                    int maxCodClient = codClienti[0];
                    for (int i = 0; i < nrClienti; i++) {
                        if (maxCodClient < codClienti[i])
                            maxCodClient = codClienti[i];
                    }
                    Client.setCounter(maxCodClient + 1);
                }
                if (emag.getComenzi().size() > 0) {
                    int nrComenzi = emag.getComenzi().size();
                    int[] codComenzi = new int[nrComenzi];
                    for (int i = 0; i < nrComenzi; i++) {
                        codComenzi[i] = emag.getComenzi().get(i).getCodComanda();
                    }
                    int maxCodComanda = codComenzi[0];
                    for (int i = 0; i < nrComenzi; i++) {
                        if (maxCodComanda < codComenzi[i])
                            maxCodComanda = codComenzi[i];
                    }
                    Comanda.setCounter(maxCodComanda + 1);
                }
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
        Scanner scanner = new Scanner(System.in);
        boolean ok = true;


        try {

            while (ok) {
                System.out.println("Buna ziua! Bine ati venit la " + emag.getDenumire() + "! Va rog sa introduceti 1 daca suneti angajat, 2 daca suneti client si 3 daca doriti sa parasiti aplicatia!");
                int nr = scanner.nextInt();
                if (nr == 1) {
                    boolean ok1 = true;
                    while (ok1) {
                        System.out.println("Ati introdus 1. Sunteti angajat! Daca introduceti: ");
                        System.out.println("1-puteti vizualiza produsele din magazin");
                        System.out.println("2-puteti modifica informatii legate de produsele existente");
                        System.out.println("3-adauga produse noi");
                        System.out.println("4-sterge produse");
                        System.out.println("Orice alta cifra pentru a reveni la meniul principal");
                        int nr1 = scanner.nextInt();
                        if (nr1 == 1) {
                            for (Produs p : emag.getProduse())
                                System.out.println(p);
//                        System.out.println(emag.getProduse());

                        } else if (nr1 == 2) {
                            System.out.println("Acestea sunt produsele magazinului: ");
                            for (Produs p : emag.getProduse())
                                System.out.println(p);
//                        System.out.println(emag.getProduse());
                            System.out.println("Va rog sa introduceti codul produsului pe care doriti sa il modificati!");
                            int cod = scanner.nextInt();

                            Produs prod = null;
                            try {
                                prod = emag.getProdusCod(cod);
                                System.out.println("Aceasta este produsul pe care doriti sa il modificati:");
                                System.out.println(prod);
                                boolean ok12 = true;
                                while (ok12) {
                                    System.out.println("Introduceti");
                                    System.out.println("1-pentru a modifica denumirea produsului");
                                    System.out.println("2-pentru a modifica pretul produsului");
                                    System.out.println("3-pentru a modifica cantitatea produsului");
                                    System.out.println("4-pentru a modifica unitatea de masaura a produsului");
                                    System.out.println("5-pentru a modifica descrierea produsului");
                                    System.out.println("6-pentru a modifica producatorul produsului");
                                    System.out.println("Orice alta cifra pentru a reveni la meniul anterior");
                                    int nr12 = scanner.nextInt();
                                    if (nr12 == 1) {
                                        boolean denumireScurt = true;
                                        while (denumireScurt) {
                                            System.out.println("Va rog introduceti noua denumire a produsului!");
                                            String denumire = scanner.nextLine();
                                            if (denumire.length() < 1)
                                                denumire = scanner.nextLine();
                                            try {
                                                prod.setDenumire(denumire);
                                                denumireScurt = false;
                                            } catch (StringPreaScurtException e) {
                                                System.out.println(e.getMessage());
//                                            e.printStackTrace();
//                                            System.out.println("Introduceti o denumire de minim un caracter!");
                                            }
                                        }
                                        System.out.println("Produsul a fost modificat! Asa arata produsul dupa modificari:");
                                        System.out.println(prod);
                                        scrieFisier(os);

                                    } else if (nr12 == 2) {
                                        boolean pretz = true;
                                        boolean pretMare = true;
                                        boolean pretN = true;
                                        while (pretz || pretMare || pretN)
                                            try {
                                                {
                                                    System.out.println("Va rog introduceti noul pret al produsului!");
                                                    float pret = scanner.nextFloat();
                                                    pretN = false;

                                                    try {
                                                        try {
                                                            prod.setPret(pret, new Date());
                                                            pretMare = false;
                                                        } catch (PretPreaMareException e) {
                                                            System.out.println(e.getMessage());
//                                        e.printStackTrace();
//                                        System.out.println("Pretul depaseste cu mai mult de 20% media preturilor din ultimele 4 luni");
                                                        }
                                                        pretz = false;

                                                    } catch (PretZeroException e) {
                                                        System.out.println(e.getMessage());
//                                            e.printStackTrace();
//                                            System.out.println("Introduceti un pret pozitiv si nenul");
                                                    }
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Introduceti un numar intreg sau cu .");
                                                scanner.nextLine();
                                            }
                                        System.out.println("Produsul a fost modificat! Asa arata produsul dupa modificari:");

                                        System.out.println(prod);
                                        scrieFisier(os);
                                    } else if (nr12 == 3) {
                                        boolean cantn = true;
                                        boolean cantn1 = true;
                                        while (cantn || cantn1)
                                            try {
                                                {
                                                    System.out.println("Va rog introduceti noua cantitate a produsului!");
                                                    int cantitate = scanner.nextInt();
                                                    cantn1 = false;
                                                    try {
                                                        prod.setCantitateStoc(cantitate);
                                                        cantn = false;
                                                    } catch (CantitateNegativaException e) {
                                                        System.out.println(e.getMessage());
//                                            e.printStackTrace();
//                                            System.out.println("Cantitate negativa!");
                                                    }
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Va rog introduceti un numar");
                                                scanner.nextLine();
                                            }
                                        System.out.println("Produsul a fost modificat! Asa arata produsul dupa modificari:");
                                        System.out.println(prod);
                                        scrieFisier(os);
                                    } else if (nr12 == 4) {
                                        System.out.println("Va rog introduceti noua unitate de masura pentru produs!");
                                        System.out.println("Va rog sa alegeti una dintre aceste variante: BUCATA, LITRII, KILOGRAME");
                                        System.out.println("Va rog sa introduceti 1 daca doriti prima varianta, 2 pentru cea de-a doua varianta si orice alta cifra pentru ultima varinata");
                                        boolean umm = true;
                                        while (umm)
                                            try {
                                                int varianta = scanner.nextInt();
                                                UnitateMasura um;
                                                umm = false;
                                                if (varianta == 1) {
                                                    um = UnitateMasura.BUCATA;
                                                } else if (varianta == 2) {
                                                    um = UnitateMasura.LITRII;
                                                } else {
                                                    um = UnitateMasura.KILOGRAME;
                                                }
                                                prod.setUnitateMasura(um);
                                                System.out.println("Produsul a fost modificat! Asa arata produsul dupa modificari:");
                                                System.out.println(prod);
                                                scrieFisier(os);
                                            } catch (InputMismatchException e) {
                                                System.out.println("Va rog introduceti un numar");
                                                scanner.nextLine();
                                            }

                                    } else if (nr12 == 5) {
                                        System.out.println("Va rog introduceti noua descriere a produsului!");
                                        String descriere = scanner.nextLine();
                                        descriere = scanner.nextLine();
                                        prod.setDescriere(descriere);
                                        System.out.println("Produsul a fost modificat! Asa arata produsul dupa modificari:");
                                        System.out.println(prod);
                                        scrieFisier(os);
                                    } else if (nr12 == 6) {
                                        boolean prods = true;
                                        while (prods) {
                                            System.out.println("Va rog introduceti noul producator al produsului!");
                                            String producator = scanner.nextLine();
                                            if (producator.length() < 1)
                                                producator = scanner.nextLine();
                                            try {
                                                prod.setProducator(producator);
                                                prods = false;
                                            } catch (StringPreaScurtException e) {
                                                System.out.println(e.getMessage());
//                                            e.printStackTrace();
//                                            System.out.println("Denumirea producatorului este prea scurta");
                                            }
                                        }

                                        System.out.println("Produsul a fost modificat! Asa arata produsul dupa modificari:");
                                        System.out.println(prod);
                                        scrieFisier(os);
                                    } else {

                                        ok12 = false;

                                    }


                                }
                            } catch (CodGresitException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
//                            System.out.println("Cod incorect!Niciun produs nu a fost modificat!");
                            }


                        } else if (nr1 == 3) {
                            Produs p = new Produs();
                            String denumire = "";
                            boolean denumires = true;
                            while (denumires) {
                                System.out.println("Va rog introduceti denumirea noului produs!");
                                denumire = scanner.nextLine();
                                if (denumire.length() < 1)
                                    denumire = scanner.nextLine();
                                try {
                                    p.setDenumire(denumire);
                                    denumires = false;
                                } catch (StringPreaScurtException e) {
                                    System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Introduceti o denumire cu minim un cracter");
                                }
                            }
                            float pret = 0;
                            boolean pret1 = true;
                            boolean pret2 = true;
                            boolean pret3 = true;
                            while (pret1 || pret2 || pret3) {
                                System.out.println("Va rog introduceti pretul noului produs!");
                                try {
                                    pret = scanner.nextFloat();
                                    pret3 = false;
                                    try {
                                        try {
                                            p.setPret(pret, new Date());
                                            pret2 = false;
                                        } catch (PretPreaMareException e) {
                                            System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Introduceti un pret care sa nu fie mai mare decat 1.2*media preturilor din ultimele 4 luni");
                                        }
                                        pret1 = false;
                                    } catch (PretZeroException e) {
                                        System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Introduceti un pret mai mare decat 0");
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Introduceti un numar intreg sau cu .");
                                    scanner.nextLine();

                                }


                            }
                            int cantitate = 0;
                            boolean can = true;
                            boolean can2 = true;
                            while (can || can2)
                                try {
                                    {
                                        System.out.println("Va rog introduceti cantitatea noului produs!");
                                        cantitate = scanner.nextInt();
                                        can2 = false;
                                        try {
                                            p.setCantitateStoc(cantitate);
                                            can = false;
                                        } catch (CantitateNegativaException e) {
                                            System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Introduceti o cantitate minima 0");
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Va rog introduceti un numar intreg");
                                    scanner.nextLine();
                                }
                            System.out.println("Va rog introduceti unitatea de masura a noului produs!");
                            System.out.println("Va rog sa alegeti una dintre aceste variante: BUCATA, LITRII, KILOGRAME");
                            System.out.println("Va rog sa introduceti 1 daca doriti prima varianta, 2 pentru cea de-a doua varianta si orice alta cifra pentru ultima varinata");
                            UnitateMasura um = UnitateMasura.BUCATA;
                            boolean umm = true;
                            while (umm)
                                try {
                                    int varianta = scanner.nextInt();
                                    umm = false;
                                    if (varianta == 1) {
                                        um = UnitateMasura.BUCATA;
                                    } else if (varianta == 2) {
                                        um = UnitateMasura.LITRII;
                                    } else {
                                        um = UnitateMasura.KILOGRAME;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Va rog introduceti un numar");
                                    scanner.nextLine();
                                }

                            System.out.println("Va rog introduceti descrierea noului produs!");
                            String descriere = scanner.nextLine();
                            descriere = scanner.nextLine();
                            String producator = "";
                            boolean pro = true;
                            while (pro) {
                                System.out.println("Va rog introduceti producatorul noului produs!");
                                producator = scanner.nextLine();
                                try {
                                    p.setProducator(producator);
                                    pro = false;
                                } catch (StringPreaScurtException e) {
                                    System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Introduceti denumirea unui producator de minim un caracter");
                                }
                            }

                            try {
                                p = new Produs(denumire, pret, cantitate, descriere, producator, new Date(), um);
                            } catch (StringPreaScurtException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
                            } catch (PretZeroException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
                            } catch (CantitateNegativaException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
                            }
                            emag.adaugaProdus(p);
                            scrieFisier(os);

                        } else if (nr1 == 4) {
                            System.out.println("Aceasta este lista cu produse:");
//                        System.out.println(emag.getProduse());
                            for (Produs p : emag.getProduse())
                                System.out.println(p);
                            System.out.println("Va rog sa introduceti codul produsului pe care doriti sa il stergeti!");
                            int cod = scanner.nextInt();
                            try {
                                Produs p = emag.getProdusCod(cod);
                                emag.stergeProdus(p);
                                System.out.println("Produsul a fost sters! Aceasta este noua lista a produselor:");
                                for (Produs pp : emag.getProduse())
                                    System.out.println(pp);
                                scrieFisier(os);
//                            System.out.println(emag.getProduse());
                            } catch (CodGresitException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
//                            System.out.println("Cod incorect!Niciun produs nu a fost sters!");
                            }

                        } else {
                            ok1 = false;
                        }


                    }

                } else if (nr == 2) {
                    boolean ok2 = true;
                    while (ok2) {
                        System.out.println("Ati introdus 2. Sunteti client! Introduceti: ");
                        System.out.println("1-daca sunteti deja client al magazinului");
                        System.out.println("2-daca doriti sa va inregistrati");
                        System.out.println("Orice alta cifra daca doriti sa reveniti la meniul anterior");
                        int nr2 = scanner.nextInt();
                        if (nr2 == 1) {
                            System.out.println("Bine ati revenit! Va rog intruceti codul de client!");
                            int cod = scanner.nextInt();

                            Client client = null;
                            try {

                                client = emag.getClientCod(cod);
                                System.out.println(client);


                                boolean ok21 = true;
                                while (ok21) {
                                    System.out.println("Introduceti:");
                                    System.out.println("1-pentru a va actualiza contul");
                                    System.out.println("2-pentru a va sterge contul");
                                    System.out.println("3-pentru a vedea produsele disponibile");
                                    System.out.println("4-pentru a vedea istoricul comenzilor dumneavoastra");
                                    System.out.println("5-pentru a lasa un raiting unui produs");
                                    System.out.println("Orice alta cifra pentru a reveni la meniul anterior");
                                    int nr21 = scanner.nextInt();
                                    if (nr21 == 1) {
                                        boolean ok211 = true;
                                        System.out.println("Datele contului dumneavoatra arata astfel: ");
                                        System.out.println(client);
                                        while (ok211) {
                                            System.out.println("Introduceti:");
                                            System.out.println("1-pentru a va actualiza denumirea");
                                            System.out.println("2-pentru a va actualiza data nasterii");
                                            System.out.println("3-pentru a va actaliza adresa de e-mail");
                                            System.out.println("4-pentru a va actualiza numarul de telefon");
                                            System.out.println("5-pentru a va actualiza adresa");
                                            System.out.println("Orice alta cifra pentru a reveni la meniul anterior");
                                            int nr211 = scanner.nextInt();
                                            if (nr211 == 1) {
                                                String denumire = "";
                                                boolean denumireScurta = true;
                                                while (denumireScurta) {
                                                    System.out.println("Va rog sa va introduceti noua denumire!");
                                                    denumire = scanner.nextLine();
                                                    if (denumire.length() < 1)
                                                        denumire = scanner.nextLine();

                                                    try {
                                                        client.setDenumire(denumire);
                                                        denumireScurta = false;
                                                    } catch (StringPreaScurtException e) {
                                                        System.out.println(e.getMessage());
//                                                    e.printStackTrace();
//                                                    System.out.println("Denumirea este prea scurta");
                                                    }
                                                }
                                                System.out.println(client);
                                                scrieFisier(os);
                                            } else if (nr211 == 2) {
                                                Date date1 = null;
                                                boolean dataMica = true;
                                                while (dataMica) {
                                                    boolean dataGresita = true;
                                                    while (dataGresita) {
                                                        System.out.println("Va rog sa va introduceti noua data a nasterii sub formatul dd/MM/yyyy !");
                                                        String data = scanner.nextLine();
                                                        if (data.length() < 1)
                                                            data = scanner.nextLine();


                                                        try {
                                                            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                                            dataGresita = false;
                                                            try {
                                                                client.setDataNasterii(date1);
                                                                dataMica = false;
                                                            } catch (ClientMinorException e) {
                                                                System.out.println(e.getMessage());
//                                                    e.printStackTrace();
//                                                    System.out.println("Client minor!");
                                                            }
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                            System.out.println("Data nu respecta formatul dorit");
                                                        }
                                                    }

                                                }
                                                System.out.println(client);
                                                scrieFisier(os);

                                            } else if (nr211 == 3) {
                                                String mail = "";
                                                boolean emailGresit = true;
                                                while (emailGresit) {
                                                    System.out.println("Va rog sa va introduceti noua adresa de e-mail!");

                                                    mail = scanner.nextLine();
                                                    if (mail.length() < 1)
                                                        mail = scanner.nextLine();
                                                    try {
                                                        client.setEmail(mail);
                                                        emailGresit = false;
                                                    } catch (EmailInvalidException e) {
                                                        System.out.println(e.getMessage());
//                                                e.printStackTrace();
//                                                System.out.println("Email invalid!");
                                                    }
                                                }
                                                System.out.println(client);
                                                scrieFisier(os);

                                            } else if (nr211 == 4) {
                                                String tel = "";
                                                boolean telGresit = true;
                                                while (telGresit) {
                                                    System.out.println("Va rog sa va introduceti nouul nr. de telefon!");
                                                    tel = scanner.nextLine();
                                                    if (tel.length() < 1)
                                                        tel = scanner.nextLine();
                                                    try {
                                                        client.setNrTelefon(tel);
                                                        telGresit = false;
                                                    } catch (NrTelefonInvalidException e) {
                                                        System.out.println(e.getMessage());
//                                                    e.printStackTrace();
//                                                    System.out.println("Numar de telefon invalid");
                                                    }
                                                }
                                                System.out.println(client);
                                                scrieFisier(os);

                                            } else if (nr211 == 5) {
                                                String adresa = "";
                                                boolean andrGresita = true;
                                                while (andrGresita) {
                                                    System.out.println("Va rog sa va introduceti noua adresa!");
                                                    adresa = scanner.nextLine();
                                                    if (adresa.length() < 1)
                                                        adresa = scanner.nextLine();

                                                    try {
                                                        client.setAdresa(adresa);
                                                        andrGresita = false;
                                                    } catch (StringPreaScurtException e) {
                                                        System.out.println(e.getMessage());
//                                                    e.printStackTrace();
//                                                    System.out.println("Adresa introdusa este prea scurta");
                                                    }
                                                }
                                                System.out.println(client);
                                                scrieFisier(os);

                                            } else {
                                                ok211 = false;

                                            }
                                        }


                                    } else if (nr21 == 2) {
                                        System.out.println("Sunteti sigur ca vreti sa va stergeti contul? Apasati 1 pentru da si orice alta cifra pentru nu!");
                                        int nr212 = scanner.nextInt();
                                        if (nr212 == 1) {
                                            emag.stergeClient(client);
                                            System.out.println("Contul dumneavoatra a fost sters");
                                            ok21 = false;
                                        } else {
                                            System.out.println("Contul dumneavoatra nu a fost sters");
                                            scrieFisier(os);
                                        }

                                    } else if (nr21 == 3) {
                                        System.out.println("Acestea sunt produsele disponibile:");
                                        for (Produs p : emag.getProduseDisponibile())
                                            System.out.println(p);
//                                    System.out.println(emag.getProduseDisponibile());
                                        System.out.println("Doriti sa realizati o comanda?Apasati pe 1 pentru da si pe orice altă cifra pentru nu.");
                                        int nr213 = scanner.nextInt();
                                        if (nr213 == 1) {
                                            Comanda com = new Comanda(client, new Date());
                                            boolean ok213 = true;
                                            while (ok213) {
                                                System.out.println("Comamda dumnevoatra arata astfel:");
                                                System.out.println(com);
                                                System.out.println("Introduceti 1 pentru a adauga produse, 2 pentru a modifica comanda si orice alta cifra pentru a finaliza sau intrerupe comanda.");
                                                int nr2131 = scanner.nextInt();
                                                if (nr2131 == 1) {
                                                    System.out.println("Acestea sunt produsele disponibile:");
                                                    for (Produs p : emag.getProduseDisponibile())
                                                        System.out.println(p);
//                                                System.out.println(emag.getProduseDisponibile());
                                                    System.out.println("Introduceti codul produsului pe care doriti sa il adaugati si cantitatea.");
                                                    System.out.println("Codul:");
                                                    int codProdus = scanner.nextInt();
                                                    System.out.println("Cantitatea:");
                                                    int cant = scanner.nextInt();
                                                    Produs p = new Produs();
                                                    try {
                                                        p = emag.getProdusCod(codProdus);

                                                        try {
                                                            com.adaugaProdus(p, cant);

                                                        } catch (StocInsuficientException e) {
                                                            System.out.println(e.getMessage());
//                                                        e.printStackTrace();
                                                        } catch (CantitateNegativaException e) {
                                                            System.out.println(e.getMessage());
//                                                        e.printStackTrace();
//                                                        System.out.println("Introduceti o cantitate pozitiva");
                                                        }
                                                    } catch (CodGresitException e) {
                                                        System.out.println(e.getMessage());
//                                                    e.printStackTrace();
//                                                    System.out.println("Codul produsui este gresit");
                                                    }

                                                } else if (nr2131 == 2) {
                                                    {

                                                        boolean ok00 = true;
                                                        while (ok00) {
                                                            System.out.println("Introduceti 1 daca doriti sa modificati cantitatea unui produs, 2 daca doriti sa stergeti un produs si orice alta cifra pentru a reveni la meniul anterior");
                                                            int nr000 = scanner.nextInt();
                                                            if (nr000 == 1) {
                                                                System.out.println("Acestea sunt produsele pe care le contine comanda:");
                                                                System.out.println(com);
                                                                System.out.println("Introduceti codul produsului a carui cantitate doriti sa o modificati:");
                                                                int codp = scanner.nextInt();

                                                                Produs pi = new Produs();
                                                                try {
                                                                    pi = com.getProdus(codp);
                                                                    System.out.println("Introduceti noua cantitate:");
                                                                    int cantNou = scanner.nextInt();
                                                                    try {
                                                                        com.modificaCantitate(pi, cantNou);
                                                                        System.out.println("Cantitatea a fost modificata!");
                                                                    } catch (StocInsuficientException e) {
                                                                        System.out.println(e.getMessage());
//                                                                    e.printStackTrace();
                                                                    } catch (CantitateNegativaException e) {
                                                                        System.out.println(e.getMessage());
//                                                                    e.printStackTrace();
//                                                                    System.out.println("Introduceti o cantitate pozitiva");
                                                                    }

                                                                    System.out.println("Comanda arata acum astfel:");
                                                                    System.out.println(com);

                                                                } catch (CodGresitException e) {
                                                                    System.out.println(e.getMessage());
//                                                                e.printStackTrace();
//                                                                System.out.println("Codul este gresit!");
                                                                }


                                                            } else if (nr000 == 2) {
                                                                System.out.println("Acestea sunt produsele pe care le contine comanda:");
                                                                System.out.println(com);
                                                                System.out.println("Introduceti codul produsului pe care doriti sa il stergeti:");
                                                                int codp = scanner.nextInt();

                                                                Produs pi = new Produs();
                                                                try {
                                                                    pi = com.getProdus(codp);
                                                                    com.stergeProdus(pi);
                                                                    System.out.println("Produsul a fost sters din comanda!");
                                                                    System.out.println("Comanda arata acum astfel:");
                                                                    System.out.println(com);
                                                                } catch (CodGresitException e) {
                                                                    System.out.println(e.getMessage());
//                                                                e.printStackTrace();
//                                                                System.out.println("Codul produsului este gresit!");
                                                                } catch (CantitateNegativaException e) {
                                                                    System.out.println(e.getMessage());
//                                                                e.printStackTrace();
                                                                }


                                                            } else {
                                                                System.out.println("Nu s-a realizat nicio modificare asupra comenzii dumneavoatra");
                                                                ok00 = false;
                                                            }
                                                        }


                                                    }

                                                } else {
                                                    System.out.println("Comanda dumneavoatra arata astfel:");
                                                    System.out.println(com);
                                                    System.out.println("Doriti sa finalizati comanda? Introduceti 1 daca da si orice alta cifra daca nu");
                                                    int nr0 = scanner.nextInt();
                                                    if (nr0 == 1) {
                                                        try {
                                                            emag.adaugaComanda(com);
                                                            System.out.println("Comanda dumneavoatra a fost plasata cu succes!");
                                                            scrieFisier(os);
                                                            System.out.println("Aceasta este factura comenzii dumneavoatre: ");
                                                            System.out.println(com.printareFactura());
                                                            System.out.println("Doriti sa o salvati intr-un fisier text? Apsati 1 daca da si orice alta cifra daca nu.");
                                                            int mel = scanner.nextInt();
                                                            if (mel == 1) {
                                                                String fileName = "Factura" + com.getCodComanda() + ".txt";
                                                                File myObj = new File("Factura" + com.getCodComanda() + ".txt");

                                                                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                                                                writer.write(com.printareFactura().toString());

                                                                writer.close();
                                                                System.out.println("Factura dumneavoatra a fost salvata!");
                                                            }
                                                        } catch (ComandaGoalaException e) {
                                                            System.out.println(e.getMessage());
//                                                        e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        ok213 = false;
                                                    } else {
                                                        System.out.println("Comanda a fost anulata");
                                                        emag.anuleazaComanda(com);
                                                        ok213 = false;
                                                    }

                                                }
                                            }
                                        }

                                    } else if (nr21 == 4) {
                                        System.out.println("Acestea sunt comenzile dumneavoatre anterioare:");
                                        for (Comanda coo : emag.vizualizareComenziPtrUnClient(client))
                                            System.out.println(coo);
                                        System.out.println("Introduceti 1 daca doriti sa anulati o comanda," + "\n" + " 2 daca doriti un raport cu comenzile intr-un fisier text," + "\n" + " 3 daca doriti sa vedeti comenzile intr-o anumita ordine si orice alta cifra daca doriti sa reveniti la meniul anterior");
                                        int s = scanner.nextInt();
                                        if (s == 1) {
                                            System.out.println("Introduceti codul comenzii pe care doriti sa o anulati! Atentie! O comanda nu poate fi anulata decat in primele 24 de ore!");
                                            System.out.println("Introduceti codul comenzii pe care doriti sa o anulati!");
                                            int codc = scanner.nextInt();

                                            try {
                                                Comanda deAnulat = new Comanda();
                                                deAnulat = emag.getComandaCod(codc, client);


                                                try {
                                                    emag.stergeComanda(deAnulat);
                                                    scrieFisier(os);
                                                    System.out.println("Comanda dumneavoatra a fost stearsa!");
                                                } catch (TimpulDeAnulareAComenziiExpiratException e) {
                                                    System.out.println(e.getMessage());
//                                                e.printStackTrace();
                                                }

                                            } catch (CodGresitException e) {
                                                System.out.println(e.getMessage());
//                                            e.printStackTrace();
//                                            System.out.println("Codul comenzii este gresit!");
                                            }


                                        } else if (s == 2) {
                                            try {
                                                String fileName = "raport" + client.getCodClient() + ".txt";
                                                File myObj = new File("raport" + client.getCodClient() + ".txt");
//                                            if (myObj.createNewFile()) {
                                                System.out.println("Fisierul a fost creat: " + myObj.getName());
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                                                for (Comanda coo : emag.vizualizareComenziPtrUnClient(client))
                                                    writer.write(coo.toString());
//                                                writer.write(emag.vizualizareComenziPtrUnClient(client).toString());

                                                writer.close();
//                                            } else {
//                                                System.out.println("Fisierul deja exista.");
//                                            }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (s == 3) {
                                            System.out.println("Introduceti 1 daca doriti sa vedeti comenzile ordonate crescator dupa valoare");
                                            System.out.println("Introduceti 2 daca doriti sa vedeti comenzile ordonate crescator dupa data");
                                            System.out.println("Introduceti 3 daca doriti sa vedeti comenzile ordonate descrescator dupa valoare");
                                            System.out.println("Introduceti 4 daca doriti sa vedeti comenzile ordonate descrescator dupa data");
                                            int r = scanner.nextInt();
                                            if (r == 1) {
                                                for (Comanda coo : emag.vizualizareComenziPtrUnClientOrdonateDupaValoare(client))
                                                    System.out.println(coo);

                                            } else if (r == 2) {
                                                for (Comanda coo : emag.vizualizareComenziPtrUnClientOrdonateDupaData(client))
                                                    System.out.println(coo);
                                            } else if (r == 3) {
                                                List<Comanda> comenziReverse = emag.vizualizareComenziPtrUnClientOrdonateDupaValoare(client);
                                                Collections.reverse(comenziReverse);
                                                for (Comanda coo : comenziReverse)
                                                    System.out.println(coo);
                                            } else if (r == 4) {
                                                List<Comanda> comenziReverse = emag.vizualizareComenziPtrUnClientOrdonateDupaData(client);
                                                Collections.reverse(comenziReverse);
                                                for (Comanda coo : comenziReverse)
                                                    System.out.println(coo);
                                            }
                                        } else {
                                            System.out.println("Nicio comanda nu a fost anulata");

                                        }


                                    } else if (nr21 == 5) {
                                        System.out.println("Acestea sunt comenzile dumneavoatre anterioare:");
                                        for (Comanda coo : emag.vizualizareComenziPtrUnClient(client))
                                            System.out.println(coo);
                                        System.out.println("Introduceti codul produsului caruia doriti sa ii lasati un rating:");
                                        int codR = scanner.nextInt();
                                        Produs pR = new Produs();
                                        try {
                                            pR = emag.getProdusCod(codR);

                                            try {
                                                boolean ok111 = emag.getProdusComandaCod(client, pR);
                                                System.out.println("Introduceti o nota de la 1 la 5");
                                                int nota = scanner.nextInt();
                                                pR.adaugaRating(nota);
                                                System.out.println("Multumim pentru recenzie");
                                                scrieFisier(os);
                                            } catch (ProdusNecomandatExceptio e) {
                                                System.out.println(e.getMessage());
//                                            e.printStackTrace();
//                                            System.out.println("Nu ati comandat produsul respectiv");
                                            }

                                        } catch (CodGresitException e) {
                                            System.out.println(e.getMessage());
//                                        e.printStackTrace();
//                                        System.out.println("Codul produsului este gresit!");
                                        }


                                    } else {
                                        ok21 = false;
                                    }
                                }
                            } catch (CodGresitException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
//                            System.out.println("Codul introdus este gresit");
                            } catch (CantitateNegativaException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
                            } catch (FaraClientiException e) {
                                System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Magazinul nu are momentan niciun client!");
                            }


                        } else if (nr2 == 2) {
                            Client client = new Client();
                            System.out.println("Bine ati venit la magazinul nostru! Va rog introduceti datele necesare inregistrarii!");
//                        System.out.println("Va rog sa va introduceti denumirea!");
//                        String denumire = scanner.nextLine();
////                        denumire = scanner.nextLine();
                            String denumire = "";
                            boolean denumireScurta = true;
                            while (denumireScurta) {
                                System.out.println("Va rog sa va introduceti denumirea!");
                                denumire = scanner.nextLine();
                                if (denumire.length() < 1)
                                    denumire = scanner.nextLine();

                                try {
                                    client.setDenumire(denumire);
                                    denumireScurta = false;
                                } catch (StringPreaScurtException e) {
                                    System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Denumirea este prea scurta");
                                }
                            }
                            Date date1 = null;
                            boolean dataMica = true;
                            while (dataMica) {
                                boolean dataCorecta = true;


                                while (dataCorecta) {
                                    System.out.println("Va rog sa va introduceti data nasterii sub formatul dd/MM/yyyy!");
                                    String data = scanner.nextLine();


                                    try {
                                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                        dataCorecta = false;
                                        try {
                                            client.setDataNasterii(date1);
                                            dataMica = false;
                                        } catch (ClientMinorException e) {
                                            System.out.println(e.getMessage());
//                                        e.printStackTrace();
//                                        System.out.println("Clientul este minor!");
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        System.out.println("Data nu respecta formatul dorit");
                                    }
                                }
                            }

                            boolean mailGresit = true;
                            String mail = "";
                            while (mailGresit) {
                                System.out.println("Va rog sa va introduceti adresa de e-mail!");
                                mail = scanner.nextLine();
                                try {
                                    client.setEmail(mail);
                                    mailGresit = false;
                                } catch (EmailInvalidException e) {
                                    System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Adresa de mail invalida!");
                                }
                            }
                            boolean telGresit = true;
                            String nrTel = "";
                            while (telGresit) {
                                System.out.println("Va rog sa va introduceti numarul de telefon!");
                                nrTel = scanner.nextLine();
                                try {
                                    client.setNrTelefon(nrTel);
                                    telGresit = false;
                                } catch (NrTelefonInvalidException e) {
                                    System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Numar de telefon invalid");
                                }
                            }

//                        System.out.println("Va rog sa va introduceti adresa!");
//                        String adresa = scanner.nextLine();
                            String adresa = "";
                            boolean andrGresita = true;
                            while (andrGresita) {
                                System.out.println("Va rog sa va introduceti adresa!");


                                adresa = scanner.nextLine();

                                try {
                                    client.setAdresa(adresa);
                                    andrGresita = false;
                                } catch (StringPreaScurtException e) {
                                    System.out.println(e.getMessage());
//                                e.printStackTrace();
//                                System.out.println("Adresa introdusa este prea scurta");
                                }
                            }


                            try {
                                client = new Client(denumire, date1, mail, nrTel, adresa);
                            } catch (ClientMinorException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
//                            System.out.println("Trebuie sa aveti peste 18 ani!");
                            } catch (EmailInvalidException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
//                            System.out.println("Email invalid!");
                            } catch (NrTelefonInvalidException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
//                            System.out.println("Numar de telefon invalid!");
                            } catch (StringPreaScurtException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
                            }
                            try {
                                emag.adaugaClient(client);
                                scrieFisier(os);

                                System.out.println("V-ati inregistrat cu succes! Acesta este codul dumneavoatra de client " + client.getCodClient() + " Va rog sa pastrati codul pentru a efectua comenzi!");
                                try {
                                    String fileName = "client" + client.getCodClient() + ".txt";
                                    File myObj = new File("client" + client.getCodClient() + ".txt");

                                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                                    writer.write(client.toString());

                                    writer.close();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (ClientDejaExistentException e) {
                                System.out.println(e.getMessage());
//                            e.printStackTrace();
                            }

                        } else {
                            ok2 = false;


                        }
                    }

                } else {
                    ok = false;

                    try {

                        os = new ObjectOutputStream(new FileOutputStream("out.bin"));
                        os.writeObject(emag);
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
//        try {
//            os = new ObjectOutputStream(new FileOutputStream("out.bin"));
//            os.writeObject(emag);
//            os.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


}

