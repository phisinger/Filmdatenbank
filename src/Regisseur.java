public class Regisseur {

    private int id;
    private String name;
    private int[] filme;

    public Regisseur(int id, String name)
    {
        this.id = id;
        this.name = name;
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

    public int[] getFilme() {
        return filme;
    }

    public void setFilme(int[] filme) {
        this.filme = filme;
    }
}
