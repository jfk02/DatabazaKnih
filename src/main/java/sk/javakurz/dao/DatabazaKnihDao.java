package sk.javakurz.dao;

import sk.javakurz.models.Autor;
import sk.javakurz.models.DatabazaKnih;
import sk.javakurz.models.Kniha;

import java.util.ArrayList;
import java.util.List;

public interface DatabazaKnihDao {

    DatabazaKnih getDatabazaKnih();

    void setDatabazaKnih(DatabazaKnih databazaKnih);

    /**
     * Nájde knihu podľa indexu.
     *
     * @param index Index knihy pre nájdenie.
     * @return Objekt Kniha alebo null ak index neexistuje.
     */
    Kniha getKniha(int index);

    /**
     * Vymaže knihu podľa indexu.
     *
     * @param index Index knihy pre vymazanie.
     * @return TRUE ak kniha bola vymazana a FALSE ako index neexistuje.
     */
    boolean vymazKnihu(int index);

    /**
     * Zistí počet kníh v knižnici.
     *
     * @return Počet kníh.
     */
    int pocetKnih();

    /**
     * Vymaže všetky knihy v knižnici.
     */
    void vymazKniznicu();

    /**
     * Pridá knihu do databáze.
     * @param nazov Názov knihy.
     * @param autor Autor knihy.
     * @param rokVydania Rok vydania knihy.
     */
    void pridajKnihu(String nazov, String autor, int rokVydania);

    /**
     * Vyhľadá knihu v databáze podľa hladaného textu v názve alebo autorovi knihy.
     *
     * @param hladanyText Hľadaný text.
     * @return List nájdených kníh alebo null.
     */
    List<Kniha> hladajKnihu(String hladanyText);

    /**
     * Vráti všetky knihy bez indexu zotriedené podľa poradia pridania, najnovšie ako posledné.
     * @return Collection nájdených kníh KnihaModel bez indexu.
     */
    ArrayList<Kniha> getVsetkyKnihy();

    /**
     * Vráti všetky indexy použité v databázi kníh.
     * @return Zoradený List všetkých indexov kníh.
     */
    List<Integer> getIndexyKnih();

    /**
     * Vráti všetky indexy použité v databázi autorov.
     * @return Zoradený List všetkých indexov autorov.
     */
    List<Integer> getIndexyAutorov();

    /**
     * Zistí stav knižnice, ak bola zmenená je false.
     * @return Stav knižnice ako boolean.
     */
    boolean isKniznicaZmenena();

    /**
     * Nastaví stav zmeny knižnice
     * @param kniznicaZmenena Stav knižnice.
     */
    void setKniznicaZmenena(boolean kniznicaZmenena);

    /**
     * Nájde autora podľa mena.
     * @param meno Meno autora.
     * @return Autor alebo null ak autor neexistuje.
     */
    Autor najdiAutora(String meno);

    List<Kniha> hladajKnihuPodlaNazvu(String zaciatokNazvu);
}
