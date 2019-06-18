import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FilmDatenbank {

    void einlesen(String path) {

//        Name der der Filmdaten-Datei
        String fileName = "daten.db";
        String line = null;

        try {
            //File lesen
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
//                Die Daten verarbeiten
            }

            // Always close files.
            bufferedReader.close();
        }
//        Exception Behandlung
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
