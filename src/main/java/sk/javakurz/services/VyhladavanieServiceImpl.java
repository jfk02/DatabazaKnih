package sk.javakurz.services;

import jline.console.completer.StringsCompleter;
import sk.javakurz.base.FarebnaKonzola;
import sk.javakurz.dao.DatabazaKnihDao;
import sk.javakurz.models.Kniha;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class VyhladavanieServiceImpl implements VyhladavanieService {
    private final DatabazaKnihDao databazaKnihDao;
    private final MenuService menuService;

    public VyhladavanieServiceImpl(DatabazaKnihDao databazaKnihDao, MenuService menuService) {
        this.databazaKnihDao = databazaKnihDao;
        this.menuService = menuService;
    }

    @Override
    public void hladajKnihuPodlaNazvu() {

        //StringBuilder zaciatokNazvu = new StringBuilder();
        String zaciatokNazvu = "";
        String textNaKonzolu;
        String zvysokNazvu;
        Kniha najdenaKniha = null;
        int poslednaDlzkaTextu;

        int klavesa = 0;

        var vystupNaKonzolu = menuService.getVstupZKonzoly();

        try{
            var vsetkyNazvyKnih = databazaKnihDao.getVsetkyKnihy().stream()
                            .map(Kniha::getNazov)
                            .toList();
            var nazvyKnihCompleter = new StringsCompleter(vsetkyNazvyKnih);
            vystupNaKonzolu.addCompleter(nazvyKnihCompleter);
            zaciatokNazvu = vystupNaKonzolu.readLine("Názov knihy: ");
            vystupNaKonzolu.removeCompleter(nazvyKnihCompleter);
        }catch (IOException e){
            System.err.println("CHYBA: Nepodarilo sa otvoriť vstup z klávesnice!");
        }


//        try {
//            textNaKonzolu = "Názov knihy: ";
//            poslednaDlzkaTextu = textNaKonzolu.length();
//            vystupNaKonzolu.print(textNaKonzolu);
//            vystupNaKonzolu.flush();
//
//            while (klavesa != 13) {
//                zvysokNazvu = "";
//
//                klavesa = menuService.citajKlavesu();
//                char znak = (char) klavesa;
//
//                if (Character.isLetterOrDigit(znak)) {
//                    zaciatokNazvu.append(znak);
//                } else if (klavesa == 8 && !zaciatokNazvu.isEmpty()) {
//                    zaciatokNazvu.deleteCharAt(zaciatokNazvu.length() - 1);
//                }
//
//                if (!zaciatokNazvu.isEmpty()) {
//                    var vyhladaneKnihy = databazaKnihDao.hladajKnihuPodlaNazvu(zaciatokNazvu.toString());
//                    najdenaKniha = !vyhladaneKnihy.isEmpty() ? vyhladaneKnihy.get(0) : null;
//                }
//
//                if (najdenaKniha != null) {
//                    zvysokNazvu = najdenaKniha.getNazov().substring(zaciatokNazvu.length());
//                    if (klavesa == 9) {
//                        zaciatokNazvu.append(zvysokNazvu);
//                        zvysokNazvu = "";
//                        textNaKonzolu ="Názov knihy: " + zaciatokNazvu;
//                    } else {
//                        textNaKonzolu = "Názov knihy: " + zaciatokNazvu
//                                + FarebnaKonzola.BLUE
//                                + zvysokNazvu
//                                + FarebnaKonzola.RESET;
//                    }
//                } else {
//                    textNaKonzolu = "Názov knihy: " + zaciatokNazvu;
//                }
//
//                vystupNaKonzolu.print("\r" + " ".repeat(poslednaDlzkaTextu));
//                vystupNaKonzolu.flush();
//                vystupNaKonzolu.getCursorBuffer().clear();
//                vystupNaKonzolu.print("\r" + textNaKonzolu);
//                vystupNaKonzolu.setCursorPosition(textNaKonzolu.length() - zvysokNazvu.length());
//                vystupNaKonzolu.flush();
//                poslednaDlzkaTextu = textNaKonzolu.length();
//            }
//        } catch (IOException e) {
//            System.err.println("CHYBA: Nepodarilo sa otvoriť výstup na konzolu!");
//        }

        System.out.println();
        System.out.println("Hľadaný názov knihy: " + zaciatokNazvu);

        if (!zaciatokNazvu.isEmpty()) {
            vypisNajdeneKnihy(databazaKnihDao.hladajKnihuPodlaNazvu(zaciatokNazvu.toString()));
        }
    }

    @Override
    public void hladajKnihu() {
        String hladanyText = menuService.vstup("\tZadaj názov alebo autora: ");
        var najdeneKnihy = databazaKnihDao.hladajKnihu(hladanyText);
        vypisNajdeneKnihy(najdeneKnihy);
    }

    @Override
    public void hladajAutora() {

    }

    private void vypisNajdeneKnihy(List<Kniha> knihyNaVypis) {
        if ((knihyNaVypis != null) && !knihyNaVypis.isEmpty()) {
            System.out.println("\nV knižnici boli nájdené tieto knihy:");
            menuService.vypisHlavickuTabulky();
            knihyNaVypis.forEach(kniha ->
                    menuService.formatovanyVypis(kniha.getId().toString(),
                            kniha.getNazov(),
                            kniha.getMenoAutora(),
                            Integer.toString(kniha.getRokVydania())));
            menuService.vypisOddelovacTabulky();
        } else {
            System.out.println("Neboli nájdené žiadne knihy!");
        }
    }

}
