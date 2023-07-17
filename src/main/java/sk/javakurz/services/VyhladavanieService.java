package sk.javakurz.services;

public interface VyhladavanieService {
    void hladajKnihuPodlaNazvu();

    /**
     * Vyhľadá knihu v databáze na základe zadaného textu.
     *
     */
    void hladajKnihu();

    void hladajAutora();

}
