package sk.javakurz.services;

import jline.console.ConsoleReader;

public interface MenuService {

    ConsoleReader getVstupZKonzoly();

    /**
     * Formátovaný výpis knižnice.
     *
     * @param index
     * @param nazov
     * @param autor
     * @param rok
     */
    void formatovanyVypis(String index, String nazov, String autor, String rok);

    void vypisOddelovacTabulky();

    /**
     * Vypíše formátovanú hlavičku tabuľky.
     */
    void vypisHlavickuTabulky();

    /**
     * Vstup užívateľa z konzoly.
     *
     * @param vyzva Výzva - prompt pre užívateľa.
     * @return Vstup užívateľa ako String.
     */
    String vstup(String vyzva);

    int citajKlavesu();

    /**
     * Vytvorí novú knihu zo vstupu užívateľa.
     *
     * @return Nová Kniha.
     */
    void novaKniha();

    /**
     * Vypíše všetky knihy v databáze na konzolu.
     *
     */
    void zobrazVsetkyKnihy();

    /**
     * Zobrazí jednu knihu na konzole.
     *
     */
    void zobrazKnihu();

    /**
     * Vymaže konkrétnu knihu.
     *
     */
    void vymazKnihu();

    /**
     * Vymaže celú knižnicu po odpovedi na otázku "Skutočne vymazať celú knižnicu?(Nie/ÁNO): "
     *
     */
    void vymazKniznicu();

    /**
     * Vypíše počet kníh v databáze na konzolu.
     *
     */
    void vypisPocetKnih();

    void zatvorVstup();

}
