import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FilmDatenbank {

    HashMap<Integer, Film> filme = new HashMap<Integer, Film>();
    HashMap<Integer, Schauspieler> schauspieler = new HashMap<Integer, Schauspieler>();
    HashMap<Integer, Regisseur> regisseure = new HashMap<Integer, Regisseur>();

    protected void einlesen() {

//        Name der der Filmdaten-Datei
        String fileName = "daten.db";
        String line;

        try {
            //File lesen
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int entity = 0;


            while((line = bufferedReader.readLine()) != null) {
                if(line.contains("New_Entity")) {
                    entity++;
                    continue;
                }

                String[] strings = new String[0];

                if (entity != 2) {
                    strings = line.split("\"");
                    for (int i = 1; i < strings.length; i++) {
                        strings[i] = strings[i].trim();
//                        System.out.println(i + ". " + strings[i]);
                    }
                }



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
                    case 4:
                        id = Integer.parseInt(strings[1]);
                        int film_id = Integer.parseInt(strings[3]);
                        schauspieler.get(id).addFilme(film_id);
                        filme.get(film_id).addSchauspieler(id);
                        break;
                    case 5:
                        id = Integer.parseInt(strings[1]);
                        int film_id1 = Integer.parseInt(strings[3]);
                        regisseure.get(id).addFilme(film_id1);
                        filme.get(film_id1).addRegisseure(id);
                        break;
                    default:
                        System.err.println("READING ERROR");
                }
            }
//            System.out.println(entity);

            // Always close files.
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
//    Filme werden aus der entsprechend Hashmap gesucht
    protected void filmSuche(String suche) {
        for (Film value : filme.values()) {
            if (value.getTitel().toLowerCase().contains(suche.toLowerCase())) {
                System.out.println(value.getId() + ", " + value.getTitel() + ", " + value.getBeschreibeung() + ", " + value.getGenre() + ", " + value.getStimmen() + ", " + value.getBewertung());
            }
        }
    }

//    Schauspieler werden aus der entsprechenden Hashmap gesucht
    protected void schauspielerSuche(String suche) {
        for (Schauspieler value : schauspieler.values()) {
            if (value.getName().toLowerCase().contains(suche.toLowerCase())) {
                System.out.println(value.getId() + ", " + value.getName());
            }
        }
    }

//    @Override
//    public String toString() {
//        return  "Schauspieler=" + schauspieler +
//                ", Film=" + filme +
//                ", Regisseur=" + regisseure;
//    }

    protected void filmNetzwerk(int suche) {
//        StringBuffer anlegen
        StringBuffer schauspielerBuffer = new StringBuffer("Schauspieler: ");
        StringBuffer filmeBuffer = new StringBuffer("Filme: ");
//        Hilfs ArrayLists anlegen
        ArrayList<Integer> actorList = new ArrayList<Integer>();
        ArrayList<Integer> filmList;
//        Den gesuchten Film in der HashMap suchen
        for (Film value : filme.values()) {
            if (value.getId() == suche) {
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
                                    if (filme.getId() == filmId && (filmeBuffer.indexOf(filme.getTitel()) == -1)) {
                                        filmeBuffer.append(filme.getTitel() + ", ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        Strings aus den Buffern generieren
        String schauspieler = new String(schauspielerBuffer);
        String filme = new String(filmeBuffer);

        System.out.println(schauspieler.substring(0,schauspieler.length()-2));
        System.out.println(filme.substring(0,filme.length()-2));
    }


    protected void schauspielerNetzwerk(int suche) {
//        StringBuffer anlegen
        StringBuffer schauspielerBuffer = new StringBuffer("Schauspieler: ");
        StringBuffer filmeBuffer = new StringBuffer("Filme: ");
//        Hilfs ArrayLists anlegen
        ArrayList<Integer> actorList = new ArrayList<Integer>();
        ArrayList<Integer> filmList;
//        Den gesuchten Schauspieler in der HashMap suchen
        for (Schauspieler value : schauspieler.values()) {
            if (value.getId() == suche) {
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
                                    if (schauspieler.getId() == actorId && (schauspielerBuffer.indexOf(schauspieler.getName()) == -1)) {
                                        schauspielerBuffer.append(schauspieler.getName() + ", ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        Strings aus den Buffern generieren
        String schauspieler = new String(schauspielerBuffer);
        String filme = new String(filmeBuffer);

        System.out.println(filme.substring(0,filme.length()-2));
        System.out.println(schauspieler.substring(0,schauspieler.length()-2));

    }

}
