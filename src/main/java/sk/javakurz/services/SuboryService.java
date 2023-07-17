package sk.javakurz.services;

public interface SuboryService {

    /**
     * Vytvorí PDF s aktuálnym zoznamom kníh v kníznici.
     * @return
     */
    boolean ulozDoPDF();

    /**
     * Uloží na disk aktuálny stav knižnice.
     * @return
     */
    boolean ulozDatabazu();

    /**
     * Načíta z disku stav knižnice.
     * @return
     */
    boolean nacitajDatabazu();
}
