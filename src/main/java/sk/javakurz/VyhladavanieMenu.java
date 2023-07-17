package sk.javakurz;

import sk.javakurz.base.FarebnaKonzola;
import sk.javakurz.dao.DatabazaKnihDao;
import sk.javakurz.services.VyhladavanieService;
import sk.javakurz.services.VyhladavanieServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class VyhladavanieMenu {

    private final HlavneMenu hlavneMenu;
    private final Map<String, Runnable> vyhladavanieAkcie;

    public VyhladavanieMenu(DatabazaKnihDao databazaKnihDao, HlavneMenu hlavneMenu) {
        VyhladavanieService vyhladavanieService = new VyhladavanieServiceImpl(databazaKnihDao, hlavneMenu.menuService);
        this.hlavneMenu = hlavneMenu;

        vyhladavanieAkcie = Map.of(
                "1", vyhladavanieService::hladajKnihuPodlaNazvu,
                "S", () -> {
                }
        );
    }

    void start() {

        var volba = "";

        do {
            System.out.println(FarebnaKonzola.CYAN_BOLD_BRIGHT);
            System.out.println("""
                    +--------------------------------------------+
                    |                Vyhľadávanie                |
                    +--------------------------------------------+
                    | 1. Hľadaj knihu podľa názvu                |
                    | 2. Hľadaj podľa autora                     |
                    | S. Späť                                    |
                    +--------------------------------------------+
                    """);

            volba = hlavneMenu.menuService.vstup(FarebnaKonzola.RESET + "Tvoja voľba: ");

            if (vyhladavanieAkcie.containsKey(volba)) {
                vyhladavanieAkcie.get(volba).run();
                if (!volba.equals("S")) hlavneMenu.menuService.vstup("\nPre pokračovanie stlač ENTER.");
            } else {
                System.out.println("Zadaj voľbu z menu.");
            }
        } while (!volba.equals("S"));
    }
}
