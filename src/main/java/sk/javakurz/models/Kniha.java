package sk.javakurz.models;

import java.io.Serializable;

public class Kniha extends ZakladnyZaznam implements Serializable {

    private final Autor autor;
    private final String nazov;
    private final int rokVydania;

    private final Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getMenoAutora() {
        return autor.getMeno();
    }

    public String getNazov() {
        return nazov;
    }

    public int getRokVydania() {
        return rokVydania;
    }

    public Kniha(String nazov, int rokVydania, Autor autor, int id) {
        this.nazov = nazov;
        this.rokVydania = rokVydania;
        this.autor = autor;
        this.id = id;
    }

}
