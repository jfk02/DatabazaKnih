package sk.javakurz;

import sk.javakurz.base.FarebnaKonzola;
import sk.javakurz.dao.*;

/**
 * Hlavná trieda programu obsahujúca main metódu.
 */
public class Main {


    public static void main(String[] args) {

        //Databáza mojaKniznica -> Dependency Injection.
        var mojaKniznica = new DatabazaKnihDaoImpl();
        var hlavneMenu = new HlavneMenu(mojaKniznica);
        hlavneMenu.start();

        System.out.println(FarebnaKonzola.CYAN_BOLD_BRIGHT + "PROGRAM KNIŽNICA BOL UKONČENÝ!");
        System.out.println(FarebnaKonzola.RESET);
    }
}