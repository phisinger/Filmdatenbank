import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FilmDatenbank {

    void einlesen() {

//        Name der der Filmdaten-Datei
        String fileName = "daten.db";
        String line = null;

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


                String[] strings = line.split("\"");
                for(int i = 1; i <= strings.length; i++) {
                    strings[i] = strings[i].trim();
                }
                int id = Integer.parseInt(strings[1]);

                switch(entity) {
//                  Actors
                    case 1:
                        schauspieler.put(id, new Schauspieler(id, strings[3]));
                        break;
//                  Films
                    case 2:
                        SimpleDateFormat dateclass = new SimpleDateFormat ("yyyy-MM-dd");
                        Date date = dateclass.parse(strings[9]);
                        int stimmen = Integer.parseInt(strings[9]);
                        float bewertung = Float.parseFloat(strings[11]);
                        filme.put(id, new Film(id, strings[3], strings[5], strings[7], date, stimmen, bewertung));
                        break;
//                  Directors
                    case 3:
                        regisseure.put(id, new Regisseur(id, strings[3]));
                        break;
                    case 4:
                        int film_id = Integer.parseInt(strings[3]);
                        schauspieler.get(id).addFilme(film_id);
                        filme.get(film_id).addSchauspieler(id);
                        break;
                    case 5:
                        int film_id1 = Integer.parseInt(strings[3]);
                        regisseure.get(id).addFilme(film_id1);
                        filme.get(film_id1).addRegisseure(id);
                        break;
                }

            }
            System.out.println(entity);

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
    }
}
