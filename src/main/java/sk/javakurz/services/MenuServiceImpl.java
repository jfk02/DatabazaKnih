package sk.javakurz.databazaknih.services;

import sk.javakurz.databazaknih.base.FarebnaKonzola;
import sk.javakurz.databazaknih.dao.DatabazaKnihDao;

import java.util.Scanner;

public class MenuServiceImpl implements MenuService {

    private final DatabazaKnihDao databazaKnihDao;

    private final Scanner scanner = new Scanner(System.in);

    private final String oddelovacTabulky = "+" + "-".repeat(7)
            + "+" + "-".repeat(42)
            + "+" + "-".repeat(32)
            + "+" + "-".repeat(6) + "+";
    private final String formatTabulky = "| %5s | %-40s | %-30s | %-4s |\n";

    /**
     * Pretypuje číslo v stringu na int s kontrolou na výnimku.
     *
     * @param cislo Pretypované číslo.
     * @return Číslo ako int ak je úspešný, inak -1.
     */
    private int mojStringNaInt(String cislo) {
        int pretypovaneCislo;
        try {
            pretypovaneCislo = Integer.parseInt(cislo);
        } catch (NumberFormatException e) {
            pretypovaneCislo = -1;
        }
        return pretypovaneCislo;
    }

    /**
     * Formátovaný výpis knižnice.
     *
     * @param index
     * @param nazov
     * @param autor
     * @param rok
     */
    private void formatovanyVypis(String index, String nazov, String autor, String rok) {
        System.out.printf(formatTabulky, index, nazov, autor, rok);
    }

    /**
     * Vypíše formátovanú hlavičku tabuľky.
     */
    private void vypisHlavickuTabulky() {
        System.out.println(oddelovacTabulky);
        formatovanyVypis("Index", "Nazov", "Autor", "Rok");
        System.out.println(oddelovacTabulky);
    }

    /**
     * Vypíše chybové hlásenie zvýraznené farbou.
     *
     * @param chyba Chybové hlásenie.
     */
    private void vypisChyboveHlasenie(String chyba) {
        System.out.println(FarebnaKonzola.RED + chyba + FarebnaKonzola.RESET);
    }

    @Override
    public String vstup(String vyzva) {
        System.out.print(vyzva);
        return scanner.nextLine().trim();
    }

    @Override
    public void novaKniha() {
        String nazov = "";
        String autor = "";

        nazov = vstup("\tNázov: ");
        autor = vstup("\tAutor: ");
        String rokVydania = vstup("\tRok vydania: ");
        int rok = mojStringNaInt(rokVydania);

        if (rok > -1 && !autor.isEmpty() && !nazov.isEmpty()) {
            databazaKnihDao.pridajKnihu(nazov, autor, rok);
        } else {
            vypisChyboveHlasenie("Neboli zadané platné údaje, kniha NEBOLA pridaná do knižnice!");
        }
    }

    @Override
    public void zobrazVsetkyKnihy() {
        if (databazaKnihDao.pocetKnih() > 0) {
            vypisHlavickuTabulky();
            databazaKnihDao.getVsetkyKnihy()
                    .forEach(kniha ->
                            formatovanyVypis(kniha.getId().toString(),
                                    kniha.getNazov(),
                                    kniha.getMenoAutora(),
                                    Integer.toString(kniha.getRokVydania())));
            System.out.println(oddelovacTabulky);
        } else {
            vypisChyboveHlasenie("Knižnica je prázdna!");
        }
    }

    @Override
    public void zobrazKnihu() {
        String vstupIndex = vstup("\tIndex knihy pre zobrazenie: ");
        int index;

        index = mojStringNaInt(vstupIndex);

        var kniha = databazaKnihDao.getKniha(index);
        if (kniha != null) {
            System.out.println();
            vypisHlavickuTabulky();
            formatovanyVypis(Integer.toString(index),
                    kniha.getNazov(),
                    kniha.getMenoAutora(),
                    Integer.toString(kniha.getRokVydania()));
            System.out.println(oddelovacTabulky);
        } else {
            vypisChyboveHlasenie("Kniha s indexom " + index + " neexistuje!");
        }
    }

    @Override
    public void vymazKnihu() {
        String vstupIndex = vstup("\tIndex knihy pre vymazanie: ");
        int index;
        index = mojStringNaInt(vstupIndex);

        var vymazana = databazaKnihDao.vymazKnihu(index);

        if (vymazana) {
            System.out.println("Kniha s indexom " + index + " bola vymazaná.");
        } else {
            vypisChyboveHlasenie("Kniha s indexom " + index + " neexistuje!");
        }
        System.out.println();
    }

    @Override
    public void vymazKniznicu() {
        String skutocneVymazat = vstup(FarebnaKonzola.RED_BRIGHT
                + "\tSkutočne vymazať celú knižnicu?(Nie/ÁNO): "
                + FarebnaKonzola.RESET);
        if (skutocneVymazat.equals("ÁNO")) {
            databazaKnihDao.vymazKniznicu();
            vypisChyboveHlasenie("KNIŽNICA BOLA VYMAZANÁ!");
        }
    }

    @Override
    public void hladajKnihu() {
        String hladanyText = vstup("\tZadaj názov alebo autora: ");

        var najdeneKnihy = databazaKnihDao.hladajKnihu(hladanyText);

        if ((najdeneKnihy != null) && !najdeneKnihy.isEmpty()) {
            System.out.println("\nV knižnici boli nájdené tieto knihy:");
            vypisHlavickuTabulky();
            najdeneKnihy.forEach(kniha ->
                    formatovanyVypis(kniha.getId().toString(),
                            kniha.getNazov(),
                            kniha.getMenoAutora(),
                            Integer.toString(kniha.getRokVydania())));
            System.out.println(oddelovacTabulky);
        } else {
            System.out.println("Neboli nájdené žiadne knihy!");
        }
    }

    @Override
    public void vypisPocetKnih() {
        System.out.println("Počet kníh v knižnici je: " + databazaKnihDao.pocetKnih());
    }


    public MenuServiceImpl(DatabazaKnihDao databazaKnihDao) {
        this.databazaKnihDao = databazaKnihDao;
    }
}
