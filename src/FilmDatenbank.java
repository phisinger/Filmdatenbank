import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FilmDatenbank {

    private void einlesen() {

//        Name der der Filmdaten-Datei
        String fileName = "daten.db";
        String line;

        try {
            //File lesen
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int entity = 0;
            HashMap<Integer, Film> filme = new HashMap<Integer, Film>();
            HashMap<Integer, Schauspieler> schauspieler = new HashMap<Integer, Schauspieler>();
            HashMap<Integer, Regisseur> regisseure = new HashMap<Integer, Regisseur>();

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
                        System.out.println("READING ERROR");
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

    public static void main(String[] args) {
        FilmDatenbank projekt = new FilmDatenbank();
        projekt.einlesen();

        String suche;

        if (args[0].contains("filmsuche")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
            System.out.println(suche);
        } else if (args[0].contains("schauspielersuche")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
            System.out.println(suche);
        } else if (args[0].contains("filmnetzwerk")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
            System.out.println(suche);
        } else if (args[0].contains("schauspielernetzwerk")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
            System.out.println(suche);
        }
    }
}
