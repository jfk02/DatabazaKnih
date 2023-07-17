package sk.javakurz.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabazaKnihDaoImplTest {

    @Test
    void pridajKnihu() {
        DatabazaKnihDao databazaKnihDao = new DatabazaKnihDaoImpl();
        databazaKnihDao.pridajKnihu("J.R.R. Tolkien", "Pán prsteňov: Spoločenstvo prsteňa", 1954);
        databazaKnihDao.pridajKnihu("George Orwell", "1984", 1949);
        databazaKnihDao.pridajKnihu("Harper Lee", "Neznesiteľná ľahkosť bytia", 1960);
        databazaKnihDao.pridajKnihu("F. Scott Fitzgerald", "Veľký Gatsby", 1925);
        databazaKnihDao.pridajKnihu("Gabriel García Márquez", "Sto rokov samoty", 1967);
        databazaKnihDao.pridajKnihu("Leo Tolstoy", "Vojna a mier", 1869);
        databazaKnihDao.pridajKnihu("Jane Austen", "Hrdinka románu", 1813);
        databazaKnihDao.pridajKnihu("Markus Zusak", "Zlodejka kníh", 2005);
        databazaKnihDao.pridajKnihu("J.D. Salinger", "Kto chytá v žite", 1951);
        databazaKnihDao.pridajKnihu("J.K. Rowling", "Harry Potter a Kameň mudrcov", 1997);
        databazaKnihDao.pridajKnihu("Ray Bradbury", "451 stupňov Fahrenheita", 1953);
        databazaKnihDao.pridajKnihu("Ernest Hemingway", "Starí a more", 1952);
        databazaKnihDao.pridajKnihu("Hermann Hesse", "Stepný vlk", 1927);
        databazaKnihDao.pridajKnihu("Emily Brontë", "Veterný vrch", 1847);
        databazaKnihDao.pridajKnihu("Aldous Huxley", "Nová odveta", 1932);
        databazaKnihDao.pridajKnihu("Victor Hugo", "Biedny vrah", 1869);
        databazaKnihDao.pridajKnihu("Lewis Carroll", "Alenka v kraji zázrakov", 1865);
        databazaKnihDao.pridajKnihu("Terry Pratchett", "Farma zvierat", 1984);
        databazaKnihDao.pridajKnihu("John Steinbeck", "Hrozny hnevu", 1939);
        databazaKnihDao.pridajKnihu("Charlotte Brontë", "Jana Eyrová", 1847);

        databazaKnihDao.vymazKnihu(1);
        databazaKnihDao.vymazKnihu(15);

        databazaKnihDao.pridajKnihu("4", "4", 4);
        databazaKnihDao.pridajKnihu("5", "4", 5);
        databazaKnihDao.pridajKnihu("6", "4", 6);

        assertEquals(21, databazaKnihDao.pocetKnih());
    }
}