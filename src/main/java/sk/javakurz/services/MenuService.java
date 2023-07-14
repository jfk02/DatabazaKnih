package sk.javakurz.databazaknih.services;

public interface MenuService {
    /**
     * Vstup užívateľa z konzoly.
     *
     * @param vyzva Výzva - prompt pre užívateľa.
     * @return Vstup užívateľa ako String.
     */
    String vstup(String vyzva);

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
     * Vyhľadá knihu v databáze na základe zadaného textu.
     *
     */
    void hladajKnihu();

    /**
     * Vypíše počet kníh v databáze na konzolu.
     *
     */
    void vypisPocetKnih();
}
