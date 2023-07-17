package sk.javakurz.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabazaKnih implements Serializable {

    private final ArrayList<Kniha> databazaKnih;

    private final ArrayList<Autor> databazaAutorov;

    public ArrayList<Kniha> getDatabazaKnih() {
        return databazaKnih;
    }

    public ArrayList<Autor> getDatabazaAutorov() {
        return databazaAutorov;
    }

    public List<Integer> getIdAutorov() {
        return databazaAutorov
                .stream()
                .map(ZakladnyZaznam::getId)
                .sorted()
                .toList();
    }

    public List<Integer> getIdKnih() {
        return databazaKnih
                .stream()
                .map(ZakladnyZaznam::getId)
                .sorted()
                .toList();
    }

    public DatabazaKnih() {
        this.databazaKnih = new ArrayList<>();
        this.databazaAutorov = new ArrayList<>();
    }
}
