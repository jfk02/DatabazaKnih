package sk.javakurz.databazaknih.models;

import java.io.Serializable;

public class Kniha extends ZakladnyZaznam implements Serializable {

    private Autor autor;
    private String nazov;
    private int rokVydania;
    private String zaner;

    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getMenoAutora() {
        return autor.getMeno();
    }

    public void setMenoAutora(String autor) {
        this.autor.setMeno(autor);
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public int getRokVydania() {
        return rokVydania;
    }

    public void setRokVydania(int rokVydania) {
        this.rokVydania = rokVydania;
    }

    public String getZaner() {
        return zaner;
    }

    public void setZaner(String zaner) {
        this.zaner = zaner;
    }

    public String getVydavatelstvo() {
        return vydavatelstvo;
    }

    public void setVydavatelstvo(String vydavatelstvo) {
        this.vydavatelstvo = vydavatelstvo;
    }

    private String vydavatelstvo;

    public Kniha(String nazov, int rokVydania, String zaner, String vydavatelstvo, Autor autor, int id) {
        this.nazov = nazov;
        this.rokVydania = rokVydania;
        this.zaner = zaner;
        this.vydavatelstvo = vydavatelstvo;
        this.autor = autor;
        this.id = id;
    }

    public Kniha(String nazov, int rokVydania, Autor autor, int id) {
        this.nazov = nazov;
        this.rokVydania = rokVydania;
        this.autor = autor;
        this.id = id;
    }

    public Kniha(String nazov) {
        this.nazov = nazov;
    }
}
