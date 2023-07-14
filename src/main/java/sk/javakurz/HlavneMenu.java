package sk.javakurz.databazaknih;

import sk.javakurz.databazaknih.base.FarebnaKonzola;
import sk.javakurz.databazaknih.dao.DatabazaKnihDao;
import sk.javakurz.databazaknih.services.MenuService;
import sk.javakurz.databazaknih.services.MenuServiceImpl;
import sk.javakurz.databazaknih.services.SuboryService;
import sk.javakurz.databazaknih.services.SuboryServiceImpl;

import java.util.HashMap;

public class HlavneMenu {

    private static MenuService menuService;
    private static SuboryService suboryService;

    //Slovník akcií vyvolávaných z menu.
    private static final HashMap<String, Runnable> MENU_AKCIE = new HashMap<>() {{
        put("1", () -> menuService.novaKniha());
        put("2", () -> menuService.zobrazVsetkyKnihy());
        put("3", () -> menuService.zobrazKnihu());
        put("4", () -> menuService.vymazKnihu());
        put("5", () -> menuService.vypisPocetKnih());
        put("6", () -> menuService.hladajKnihu());
        put("7", () -> {
            if (suboryService.nacitajDatabazu()) {
                System.out.println("Knižnica bola úspešne načítaná z disku.");
            }
        });
        put("8", () -> {
            if (suboryService.ulozDatabazu()) {
                System.out.println("Knižnica bola uložená na disk.");
            }
        });
        put("9", () -> {
            if (suboryService.ulozDoPDF()) {
                System.out.println("Knižnica bola uložená ako PDF.");
            }
        });
        put("X", () -> menuService.vymazKniznicu());
        put("Q", () -> {
        });
    }};

    /**
     * Hlavné menu programu Kniznica.
     */
    void start() {

        var volba = "";

        do {
            System.out.println(FarebnaKonzola.CYAN_BOLD_BRIGHT);
            System.out.println("""
                    +--------------------------------------------+
                    |               MENU KNIŽNICE                |
                    +--------------------------------------------+
                    | 1. Zadaj novú knihu                        |
                    | 2. Zobraz všetky knihy                     |
                    | 3. Zobraz konkrétnu knihu (podľa indexu)   |
                    | 4. Vymaž konkrétnu knihu (podľa indexu)    |
                    | 5. Zobraz počet všetkých kníh              |
                    | 6. Hľadaj knihu podľa autora alebo názvu   |
                    | 7. Načítaj knižnicu z disku                |
                    | 8. Ulož knižnicu na disk                   |
                    | 9. Ulož zoznam kníh ako PDF                |
                    | X. Vymaž všetky knihy                      |
                    | Q. Skončí program                          |
                    +--------------------------------------------+
                    """);

            volba = menuService.vstup(FarebnaKonzola.RESET + "Tvoja voľba: ");

            if (MENU_AKCIE.containsKey(volba)) {
                MENU_AKCIE.get(volba).run();
                menuService.vstup("\nPre pokračovanie stlač ENTER.");
            } else {
                System.out.println("Zadaj voľbu z menu.");
            }
        } while (!volba.equals("Q"));
    }

    public HlavneMenu(DatabazaKnihDao databazaKnihDao) {

        //Vytvorenie služieb potrebných pre chod hlavného menu.
        menuService = new MenuServiceImpl(databazaKnihDao);
        suboryService = new SuboryServiceImpl(databazaKnihDao);
    }
}
