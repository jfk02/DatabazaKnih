package sk.javakurz.models;

import java.io.Serializable;

public class Autor extends ZakladnyZaznam implements Serializable {
    private final Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    private final String meno;

    public String getMeno() {
        return meno;
    }

    public Autor(String meno, int id) {
        this.id = id;
        this.meno = meno;
    }
}
