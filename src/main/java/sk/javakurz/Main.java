package sk.javakurz.databazaknih;

import sk.javakurz.databazaknih.base.FarebnaKonzola;
import sk.javakurz.databazaknih.dao.*;

/**
 * Hlavná trieda programu obsahujúca main metódu.
 */
public class Kniznica {
    private static DatabazaKnihDao mojaKniznica;
    private static HlavneMenu hlavneMenu;

    public static void main(String[] args) {

        //Databáza mojaKniznica -> Dependency Injection.
        mojaKniznica = new DatabazaKnihDaoImpl();
        hlavneMenu = new HlavneMenu(mojaKniznica);
        hlavneMenu.start();

        System.out.println(FarebnaKonzola.CYAN_BOLD_BRIGHT + "PROGRAM KNIŽNICA BOL UKONČENÝ!");
    }
}