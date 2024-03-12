package proiect1.clase;

import proiect1.excepții.CodGresitException;
import proiect1.excepții.ComandaGoalaException;

public interface IVanzare {
    void adaugaComanda(Comanda c) throws ComandaGoalaException, CodGresitException;
}
