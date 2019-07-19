import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

//Testklasse mit fünf Tests
public class testFilmDatenbank {

    private FilmDatenbank tester = new FilmDatenbank();

//    Filmsuche test
//    es wird getestet, ob der richtige Film ausgegeben wird und ob die Anzahl der Ausgabe stimmt.
    @Test
    public void testFilmsuche() {
        tester.einlesen();
        ArrayList<String> output = tester.filmSuche("Matrix");
        Assert.assertEquals("4899, Matrix Revolutions, The, The human city of Zion defends itself against the massive invasion of the machines as Neo fights to end the war at another front while also opposing the rogue Agent Smith., IMAX, 354082, 6.7", output.get(2));
        Assert.assertEquals(4, output.size());
        System.out.println("\n----------------------------------");
        System.out.println("Filmsuche erfolgreich");
    }


//    Schauspielersuche test
//    es wird getestet, ob der richtige Schauspieler und die richtige Anzahl ausgegeben wird.
    @Test
    public void testSchauspielersuche() {
        tester.einlesen();
        ArrayList<String> output = tester.schauspielerSuche("Smith");
        Assert.assertEquals("11306, Jamie Smith", output.get(3));
        Assert.assertEquals(69, output.size());
        System.out.println("\n----------------------------------");
        System.out.println("Schauspielersuche erfolgreich");
    }

//    Einlesen test
//    Die Anzahl der Elemente in der HashMap wird getestet
    @Test
    public void testeinlesen() {
        tester.einlesen();
        Assert.assertEquals(18159, tester.schauspielerSize());
        Assert.assertEquals(9124, tester.filmeSize());
        Assert.assertEquals(4339, tester.regisseureSize());
        System.out.println("\n----------------------------------");
        System.out.println("Einlesen erfolgreich");
    }

//    Schauspielernetzwerk
    @Test
    public void testSchauspielerNetzwerk() {
        tester.einlesen();
        String[] output;
        output = tester.schauspielerNetzwerk(17639);
//        Test, ob der richtige Filmausgegeben wird
        Assert.assertTrue(output[0].contains("Leaving Las Vegas"));
//        Testen, ob die richtige Anzahl an Filmen ausgeben wird
        int anzahlkomma = 0;
        int ind = 0;
        while(output[0].indexOf(",",ind) > -1) {
            ind = output[0].indexOf(",",ind) + 1;
            anzahlkomma++;
        }
//        Es wird ein Komma mehr gezählt, als eigentlich ausgegeben wurde
        Assert.assertEquals(1+1, anzahlkomma);

//        Testen, ob die richtige Anzahl an Schauspielern ausgegeben wird
        anzahlkomma = 0;
        ind = 0;
        while(output[1].indexOf(",",ind) > -1) {
            ind = output[1].indexOf(",",ind) +1;
            anzahlkomma++;
        }
//        Es wird ein Komma mehr gezählt, als eigentlich ausgegeben wurde
        Assert.assertEquals(5+1, anzahlkomma);
        System.out.println("\n----------------------------------");
        System.out.println("Schauspielernetzwerk erfolgreich");

    }

    //    Schauspielernetzwerk
    @Test
    public void testFilmNetzwerk() {
        tester.einlesen();
        String[] output;
        output = tester.filmNetzwerk(5313);
//        Testen, ob der richtige Film gesucht wird
        Assert.assertTrue(output[0].contains("Who's Afraid of Virginia Woolf?"));
//        Testen, ob die richtige Anzahl an Filmen ausgeben wird
        int anzahlkomma = 0;
        int ind = 0;
        while(output[0].indexOf(",",ind) > -1) {
            ind = output[0].indexOf(",",ind) + 1;
            anzahlkomma++;
        }
//        Es wird ein Komma mehr gezählt, als eigentlich ausgegeben wird
        Assert.assertEquals(38+1, anzahlkomma);

//        Testen, ob die richtige Anzahl an Schauspielern ausgegeben wird
        anzahlkomma = 0;
        ind = 0;
        while(output[1].indexOf(",",ind) > -1) {
            ind = output[1].indexOf(",",ind) +1;
            anzahlkomma++;
        }
//        Es wird ein Komma mehr gezählt, als eigentlich ausgegeben wird
        Assert.assertEquals(3+1, anzahlkomma);
        System.out.println("\n----------------------------------");
        System.out.println("Filmnetzwerk erfolgreich");

    }

}
