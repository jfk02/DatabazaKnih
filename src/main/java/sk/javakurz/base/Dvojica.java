package sk.javakurz.databazaknih.base;

public class Dvojica<T1, T2> {
    public T1 getPrvy() {
        return prvy;
    }

    public void setPrvy(T1 prvy) {
        this.prvy = prvy;
    }

    public T2 getDruhy() {
        return druhy;
    }

    public void setDruhy(T2 druhy) {
        this.druhy = druhy;
    }

    private T1 prvy;
    private T2 druhy;

    public Dvojica(T1 prvy, T2 druhy) {
        this.prvy = prvy;
        this.druhy = druhy;
    }

}
