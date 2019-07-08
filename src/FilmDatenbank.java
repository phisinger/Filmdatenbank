import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FilmDatenbank {

    private HashMap<Integer, Film> filme = new HashMap<>();
    private HashMap<Integer, Schauspieler> schauspieler = new HashMap<Integer, Schauspieler>();
    private HashMap<Integer, Regisseur> regisseure = new HashMap<Integer, Regisseur>();

    public int filmeSize() {
        return filme.size();
    }

    public int schauspielerSize() {
        return schauspieler.size();
    }

    public int regisseureSize() {
        return regisseure.size();
    }

    protected void einlesen() {

//        Name der der Filmdaten-Datei
        String fileName = "daten.db";
        String line;

        try {
            //File lesen
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int entity = 0;

//          Datenobjekt erkennen
            while((line = bufferedReader.readLine()) != null) {
                if(line.contains("New_Entity")) {
                    entity++;
                    continue;
                }

//                Array, um Daten gewünscht aufzuteilen
                String[] strings = new String[0];
                if (entity != 2) {
                    strings = line.split("\"");
                    for (int i = 1; i < strings.length; i++) {
                        strings[i] = strings[i].trim();
//                        System.out.println(i + ". " + strings[i]);
                    }
                }


//                Switch zur richtigen Verarbeitung der Datenobjekte
                switch(entity) {
//                  Actors
                    case 1:
                        int id = Integer.parseInt(strings[1]);
                        schauspieler.put(id, new Schauspieler(id, strings[3]));
                        break;
//                  Films
                    case 2:
                        strings = line.split("\",\"");
                        for(int i = 0; i < strings.length; i++) {
                            strings[i] = strings[i].trim();
//                            System.out.println(i + ". " + strings[i]);
                        }
                        id = Integer.parseInt(strings[0].substring(1));

                        filme.put(id, new Film(id, strings[1], strings[2], strings[3]));
//                        Erscheinungsdatum
                        if (!("".equals(strings[4]))) {
                            SimpleDateFormat dateclass = new SimpleDateFormat ("yyyy-MM-dd");
                            Date date = dateclass.parse(strings[4]);
                            filme.get(id).setErscheinungsdatum(date);
                        }
//                        Stimmenanzahl
                        if (!("".equals(strings[5]))) {
                            int stimmen = Integer.parseInt(strings[5]);
                            filme.get(id).setStimmen(stimmen);
                        }
//                        Bewertung
                        if (!("\"".equals(strings[6]))) {
                            float bewertung = Float.parseFloat(strings[6].substring(0, strings[6].length() - 1));
                            filme.get(id).setBewertung(bewertung);
                        }

                        break;
//                  Directors
                    case 3:
                        id = Integer.parseInt(strings[1]);
                        regisseure.put(id, new Regisseur(id, strings[3]));
                        break;
//                  Verbindung zwischen Film und Schauspieler
                    case 4:
                        id = Integer.parseInt(strings[1]);
                        int film_id = Integer.parseInt(strings[3]);
                        schauspieler.get(id).addFilme(film_id);
                        filme.get(film_id).addSchauspieler(id);
                        break;
//                    Verbindung zwischen Film und Regisseur
                    case 5:
                        id = Integer.parseInt(strings[1]);
                        int film_id1 = Integer.parseInt(strings[3]);
                        regisseure.get(id).addFilme(film_id1);
                        filme.get(film_id1).addRegisseure(id);
                        break;
//                    Error falls etwas schief läuft
                    default:
                        System.err.println("READING ERROR");
                }
            }

//            File schließen
            bufferedReader.close();
        }
//        Alle Exception auffangen
        catch(Exception e) {
            e.printStackTrace();
        }
    }


//    Filme werden aus der entsprechend Hashmap gesucht
    protected ArrayList<String> filmSuche(String suche) {
//        ArrayList zum einfacheren Testen
        ArrayList<String> output = new ArrayList<>();
        for (Film value : filme.values()) {
//            Der Schauspieler wird gesucht...
            if (value.getTitel().toLowerCase().contains(suche.toLowerCase())) {
//              ...und ausgegeben.
                output.add(value.toString());
                System.out.println(value.toString());
            }
        }
        return output;
    }

//    Schauspieler werden aus der entsprechenden Hashmap gesucht
    protected ArrayList<String> schauspielerSuche(String suche) {
//        ArrayList zum einfacheren Testen
        ArrayList<String> output = new ArrayList<>();
//        Der Schauspieler wird gesucht...
        for (Schauspieler value : schauspieler.values()) {
            if (value.getName().toLowerCase().contains(suche.toLowerCase())) {
//                ...und ausgegeben.
                output.add(value.toString());
                System.out.println(value.toString());
            }
        }
        return output;
    }


//  Netzwerk des Films mit Schauspielern und Filmen
    protected String[] filmNetzwerk(int suche) {
//        StringBuffer anlegen
        String suchWort;
        StringBuffer schauspielerBuffer = new StringBuffer("Schauspieler: ");
        StringBuffer filmeBuffer = new StringBuffer("Filme: ");
//        Hilfs ArrayLists anlegen
        ArrayList<Integer> actorList = new ArrayList<Integer>();
        ArrayList<Integer> filmList;
//        Den gesuchten Film in der HashMap suchen
        for (Film value : filme.values()) {
            if (value.getId() == suche) {
                suchWort = value.getTitel();
//                Die Schauspieler für den gesuchten Film zwischenspeichern
                actorList = value.getSchauspieler();
//                Die Schauspieler aus der HashMap suchen
                for(int actorId : actorList) {
                    for (Schauspieler wert : schauspieler.values()) {
                        if (actorId == wert.getId()) {
                            filmList = wert.getFilme();
//                            Namen der Schauspieler speichern
                            schauspielerBuffer.append(wert.getName() + ", ");
//                            Die Filme im Objekt "schauspieler x" nehmen...
                            for (int filmId : filmList) {
//                                ...und Namen suchen
                                for (Film filme : filme.values()) {
                                    if (filme.getId() == filmId && (filmeBuffer.indexOf(filme.getTitel()) == -1) && !(filme.getTitel() == suchWort)) {
                                        filmeBuffer.append(filme.getTitel() + ", ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        Ausgabefunktion aufrufen
        String output[] = ausgabe(filmeBuffer, schauspielerBuffer, false);
        return output;
    }


//  Netzwerk des Films mit Filmen und Schauspielern
    protected String[] schauspielerNetzwerk(int suche) {
        String suchWort;
//        StringBuffer anlegen
        StringBuffer schauspielerBuffer = new StringBuffer("Schauspieler: ");
        StringBuffer filmeBuffer = new StringBuffer("Filme: ");
//        Hilfs ArrayLists anlegen
        ArrayList<Integer> actorList = new ArrayList<Integer>();
        ArrayList<Integer> filmList;
//        Den gesuchten Schauspieler in der HashMap suchen
        for (Schauspieler value : schauspieler.values()) {
            if (value.getId() == suche) {
                suchWort = value.getName();
//                Die Filme für den gesuchten Schauspieler zwischenspeichern
                filmList = value.getFilme();
//                Die Filme aus der HashMap suchen
                for(int filmId : filmList) {
                    for (Film wert : filme.values()) {
                        if (filmId == wert.getId()) {
                            actorList = wert.getSchauspieler();
//                            Namen der Filme speichern
                            filmeBuffer.append(wert.getTitel() + ", ");
//                            Die Schauspieler im Objekt "Film x" nehmen...
                            for (int actorId : actorList) {
//                                ...und Namen suchen
                                for (Schauspieler schauspieler : schauspieler.values()) {
                                    if (schauspieler.getId() == actorId && schauspielerBuffer.indexOf(schauspieler.getName()) == -1 && !(schauspieler.getName() == suchWort)) {
                                        schauspielerBuffer.append(schauspieler.getName() + ", ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        Ausgabefunktion aufrufen
        String output[] = ausgabe(filmeBuffer, schauspielerBuffer, true);
        return output;

    }

//    Funktion zur ausgabe der Netzwerke und zur Generierung des Test-Arrays
    protected String[] ausgabe(StringBuffer filmeBuffer, StringBuffer schauspielerBuffer, boolean SchauspielerNetzwerk) {
//        Strings aus den Buffern generieren
        String schauspieler = new String(schauspielerBuffer);
        String filme = new String(filmeBuffer);

//        Die geforderte Reinfolge einhalten
        if(SchauspielerNetzwerk) {
            System.out.println(filme.substring(0,filme.length()-2));
            System.out.println(schauspieler.substring(0,schauspieler.length()-2));
        }else {
            System.out.println(schauspieler.substring(0,schauspieler.length()-2));
            System.out.println(filme.substring(0,filme.length()-2));
        }

//        Strings in ein Array Speichern, und zurückgeben, um zu testen.
        String output[] = new String[2];
        output[0] = filme;
        output[1] = schauspieler;
        return output;



    }

}
