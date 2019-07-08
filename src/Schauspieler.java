import java.util.ArrayList;

//Die Schauspieler-Klasse mit den Attributen ID, Name und einer ArrayList, in der die Filme stehen,
//an denen der Schauspieler mitgarbeitet hat.
public class Schauspieler {

    private int id;
    private String name;
    private ArrayList<Integer> filme = new ArrayList<Integer>();

    public Schauspieler(int id, String name) {
        this.id = id;
        this.name = name;

    }


//    Getter und Setter und eine ToString-Funktion für die Aufgabe
    @Override
    public String toString() {
        return (id + ", " + name);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFilme(int id) {
        filme.add(id);
    }

    public ArrayList getFilme() {
        return filme;
    }

    public void getFilme(int key) {
        filme.get(key);
    }
}
