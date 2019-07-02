import java.util.ArrayList;
import java.util.Date;

public class Film {

    private int id;
    private String titel;
    private String beschreibeung;
    private String genre;
    private Date erscheinungsdatum;
    private int stimmen;
    private float bewertung;
    private ArrayList<Integer> schauspieler = new ArrayList<Integer>();
    private ArrayList<Integer> regisseure = new ArrayList<Integer>();


//    Konstruktor
    public Film(int id, String titel, String beschreibeung, String genre)
    {
        this.id = id;
        this.titel = titel;
        this.beschreibeung = beschreibeung;
        this.genre = genre;
    }
    @Override
    public String toString(){
        return (id + ", " + titel + ", " + beschreibeung + ", " + genre + ", " + stimmen + ", " + bewertung);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibeung() {
        return beschreibeung;
    }

    public void setBeschreibeung(String beschreibeung) {
        this.beschreibeung = beschreibeung;
    }

    public Date getErscheinungsdatum() {
        return erscheinungsdatum;
    }

    public void setErscheinungsdatum(Date erscheinungsdatum) {
        this.erscheinungsdatum = erscheinungsdatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBewertung() {
        return bewertung;
    }

    public void setBewertung(float bewertung) {
        this.bewertung = bewertung;
    }

    public int getStimmen() {
        return stimmen;
    }

    public void setStimmen(int stimmen) {
        this.stimmen = stimmen;
    }

    public void addSchauspieler(int id) {
        schauspieler.add(id);
    }

    public void getSchauspieler(int key) {
        schauspieler.get(key);
    }

    public ArrayList getSchauspieler() {
        return schauspieler;
    }

    public ArrayList getRegisseure() {
        return regisseure;
    }

    public void addRegisseure(int id) {
        regisseure.add(id);
    }

    public void getRegisseure(int key) {
        regisseure.get(key);
    }
}
