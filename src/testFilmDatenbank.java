import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class testFilmDatenbank {

    FilmDatenbank tester = new FilmDatenbank();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();




//    Filmsuche test
    @Test
    public void testFilmsuche() {
        tester.einlesen();
        ArrayList<String> output = tester.filmSuche("Smith");
        Assert.assertTrue(output.contains("27822, Mike Smith"));
        Assert.assertEquals("11306, Jamie Smith", output.get(3));
        Assert.assertEquals(69, output.size());
        System.out.println("\n----------------------------------");
        System.out.println("Filmsuche erfolgreich");
    }


//    Schauspielersuche test
    @Test
    public void testSchauspielersuche() {
        tester.einlesen();
        ArrayList<String> output = tester.schauspielerSuche("Smith");
        Assert.assertTrue(output.contains("27822, Mike Smith"));
        Assert.assertEquals("11306, Jamie Smith", output.get(3));
        Assert.assertEquals(69, output.size());
        System.out.println("\n----------------------------------");
        System.out.println("Schauspielersuche erfolgreich");
    }

}
