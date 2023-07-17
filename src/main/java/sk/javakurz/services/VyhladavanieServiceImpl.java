package sk.javakurz.services;

import jline.console.completer.StringsCompleter;
import sk.javakurz.dao.DatabazaKnihDao;
import sk.javakurz.models.Autor;
import sk.javakurz.models.Kniha;

import java.io.IOException;
import java.util.List;

public class VyhladavanieServiceImpl implements VyhladavanieService {
    private final DatabazaKnihDao databazaKnihDao;
    private final MenuService menuService;

    public VyhladavanieServiceImpl(DatabazaKnihDao databazaKnihDao, MenuService menuService) {
        this.databazaKnihDao = databazaKnihDao;
        this.menuService = menuService;
    }

    @Override
    public void hladajKnihuPodlaNazvu() {
        String hladanyNazov = "";
        var vystupNaKonzolu = menuService.getVstupZKonzoly();

        try {
            var vsetkyNazvyKnih = databazaKnihDao
                    .getVsetkyKnihy()
                    .stream()
                    .map(Kniha::getNazov)
                    .toList();
            var nazvyKnihCompleter = new StringsCompleter(vsetkyNazvyKnih);
            vystupNaKonzolu.addCompleter(nazvyKnihCompleter);
            hladanyNazov = vystupNaKonzolu.readLine("Názov knihy: ");
            vystupNaKonzolu.removeCompleter(nazvyKnihCompleter);
        } catch (IOException e) {
            System.err.println("CHYBA: Nepodarilo sa otvoriť vstup z klávesnice!");
        }

        System.out.println();

        if (!hladanyNazov.isEmpty()) {
            vypisNajdeneKnihy(databazaKnihDao.hladajKnihuPodlaNazvu(hladanyNazov.trim()));
        }
    }

    @Override
    public void hladajKnihu() {
        String hladanyText = menuService.vstup("\tZadaj časť názvu alebo mena autora: ");
        var najdeneKnihy = databazaKnihDao.hladajKnihu(hladanyText);
        vypisNajdeneKnihy(najdeneKnihy);
    }

    @Override
    public void hladajKnihuPodlaAutora() {
        String hladanyAutor = "";
        var vystupNaKonzolu = menuService.getVstupZKonzoly();

        try {
            var vsetciAutori = databazaKnihDao
                    .getVsetciAutori()
                    .stream()
                    .map(Autor::getMeno)
                    .toList();
            var menaAutorovCompleter = new StringsCompleter(vsetciAutori);
            vystupNaKonzolu.addCompleter(menaAutorovCompleter);
            hladanyAutor = vystupNaKonzolu.readLine("Meno autora: ");
            vystupNaKonzolu.removeCompleter(menaAutorovCompleter);
        } catch (IOException e) {
            System.err.println("CHYBA: Nepodarilo sa otvoriť vstup z klávesnice!");
        }

        System.out.println();

        var autor = databazaKnihDao.najdiAutora(hladanyAutor.trim());
        if (autor != null) {
            vypisNajdeneKnihy(databazaKnihDao.getVsetkyKnihyAutora(autor.getId()));
        } else {
            System.out.println("Autor " + hladanyAutor + " nebol nájdený!");
        }
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
