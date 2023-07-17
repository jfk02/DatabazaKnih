package sk.javakurz.dao;

import sk.javakurz.models.Autor;
import sk.javakurz.models.DatabazaKnih;
import sk.javakurz.models.Kniha;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabazaKnihDaoImpl implements DatabazaKnihDao {

    private DatabazaKnih databazaKnih;

    private boolean kniznicaZmenena = false;

    private Integer generujDalsiKlucPreKnihu() {
        int novyKluc = 1;
        if (!databazaKnih.getDatabazaKnih().isEmpty()) {
            novyKluc = getIndexyKnih().get(pocetKnih() - 1) + 1;
        }
        return novyKluc;
    }

    private Integer generujDalsiKlucPreAutora() {
        int novyKluc = 1;
        if (!databazaKnih.getDatabazaAutorov().isEmpty()) {
            novyKluc = getIndexyAutorov().get(getIndexyAutorov().size() - 1) + 1;
        }
        return novyKluc;
    }

    @Override
    public boolean isKniznicaZmenena() {
        return kniznicaZmenena;
    }

    @Override
    public void setKniznicaZmenena(boolean kniznicaZmenena) {
        this.kniznicaZmenena = kniznicaZmenena;
    }

    @Override
    public void setDatabazaKnih(DatabazaKnih databazaKnih) {
        this.databazaKnih = databazaKnih;
    }

    @Override
    public DatabazaKnih getDatabazaKnih() {
        return databazaKnih;
    }

    @Override
    public Kniha getKniha(int index) {
        return this.databazaKnih.getDatabazaKnih().stream()
                .filter(kniha -> kniha.getId() == index)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean vymazKnihu(int index) {
        kniznicaZmenena = true;
        var velkostKniznice = pocetKnih();
        var i = 0;

        while (i < velkostKniznice && databazaKnih.getDatabazaKnih().get(i).getId() != index) {
            i++;
        }

        var jeVDatabaze = i != velkostKniznice;

        if (jeVDatabaze) {
            databazaKnih.getDatabazaKnih().remove(i);
        }

        return jeVDatabaze;
    }

    @Override
    public int pocetKnih() {
        return databazaKnih.getDatabazaKnih().size();
    }

    @Override
    public void vymazKniznicu() {
        kniznicaZmenena = true;
        databazaKnih.getDatabazaKnih().clear();
        databazaKnih.getDatabazaAutorov().clear();
    }

    @Override
    public void pridajKnihu(String nazov, String autor, int rokVydania) {

        var existujuciAutor = najdiAutora(autor);

        if (existujuciAutor == null) {
            var idKnihy = generujDalsiKlucPreKnihu();
            var idAutora = generujDalsiKlucPreAutora();
            var novyAutor = new Autor(autor, idAutora);
            var novaKniha = new Kniha(nazov, rokVydania, novyAutor, idKnihy);
            databazaKnih.getDatabazaAutorov().add(novyAutor);
            databazaKnih.getDatabazaKnih().add(novaKniha);
        } else {
            var idKnihy = generujDalsiKlucPreKnihu();
            var novaKniha = new Kniha(nazov, rokVydania, existujuciAutor, idKnihy);
            databazaKnih.getDatabazaKnih().add(novaKniha);
        }

        kniznicaZmenena = true;
    }

    @Override
    public List<Kniha> hladajKnihu(String hladanyText) {
        return databazaKnih.getDatabazaKnih()
                .stream()
                .filter(kniha -> kniha.getNazov().contains(hladanyText)
                        || kniha.getMenoAutora().contains(hladanyText))
                .collect(Collectors.toList());
    }
    @Override
    public List<Kniha> hladajKnihuPodlaNazvu(String zaciatokNazvu){
        return databazaKnih.getDatabazaKnih()
                .stream()
                .filter(kniha -> kniha.getNazov().toLowerCase()
                        .startsWith(zaciatokNazvu.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public ArrayList<Kniha> getVsetkyKnihy() {
        return databazaKnih.getDatabazaKnih();
    }

    @Override
    public ArrayList<Autor> getVsetciAutori() {
        return databazaKnih.getDatabazaAutorov();
    }

    @Override
    public List<Integer> getIndexyKnih() {
        return databazaKnih.getIdKnih();
    }

    @Override
    public List<Integer> getIndexyAutorov() {
        return databazaKnih.getIdAutorov();
    }

    @Override
    public Autor najdiAutora(String meno) {
        return databazaKnih.getDatabazaAutorov().stream()
                .filter(a -> a.getMeno().equals(meno))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Kniha> getVsetkyKnihyAutora(int idAutora) {
        return databazaKnih.getDatabazaKnih().stream()
                .filter(kniha -> kniha.getAutor().getId() == idAutora)
                .collect(Collectors.toList());
    }

    public DatabazaKnihDaoImpl() {

        this.databazaKnih = new DatabazaKnih();

        //TODO:LEN PRE PREZENTÁCIU FUNKCIÍ
        naplnenieKniznice();
    }

    //TODO: Vymazať keď už nebude potrebné.

    /**
     * Naplnenie knižnice pre testovanie.
     */
    private void naplnenieKniznice() {
        pridajKnihu("1984", "George Orwell", 1949);
        pridajKnihu("Brána do času", "Robert A. Heinlein", 1959);
        pridajKnihu("Duna", "Frank Herbert", 1965);
        pridajKnihu("Základňa", "Isaac Asimov", 1951);
        pridajKnihu("Vládca much", "William Golding", 1954);
        pridajKnihu("Hyperion", "Dan Simmons", 1989);
        pridajKnihu("Neuromancer", "William Gibson", 1984);
        pridajKnihu("Do Androids Dream of Electric Sheep?", "Philip K. Dick", 1968);
        pridajKnihu("Starship Troopers", "Robert A. Heinlein", 1959);
        pridajKnihu("Marsian", "Andy Weir", 2011);
        pridajKnihu("Fahrenheit 451", "Ray Bradbury", 1953);
        pridajKnihu("Snow Crash", "Neal Stephenson", 1992);
        pridajKnihu("Strieborný úsvit", "Scott Westerfeld", 2009);
        pridajKnihu("Väzenie nesmrteľných", "Karel Čapek", 1920);
        pridajKnihu("Hra Enderova", "Orson Scott Card", 1985);
        pridajKnihu("Súmrak gigantov", "Arthur C. Clarke", 1953);
        pridajKnihu("Temné lesy", "Liu Cixin", 2008);
        pridajKnihu("Útok z vesmíru", "H. G. Wells", 1898);
        pridajKnihu("Ready Player One", "Ernest Cline", 2011);
        pridajKnihu("Detská planéta", "Orson Scott Card", 1973);
    }
}
