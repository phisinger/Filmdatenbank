public class main {

    public static void main(String[] args) {
        FilmDatenbank projekt = new FilmDatenbank();
        projekt.einlesen();

        String suche;

        if (args[0].contains("filmsuche")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
//            System.out.println(suche);
            projekt.filmSuche(suche);
        } else if (args[0].contains("schauspielersuche")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
            projekt.schauspielerSuche(suche);
//            System.out.println(suche);
        } else if (args[0].contains("filmnetzwerk")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
//            System.out.println(suche);
            projekt.filmNetzwerk(Integer.parseInt(suche));
        } else if (args[0].contains("schauspielernetzwerk")) {
            suche = args[0].substring(args[0].indexOf('=')+1);
//            System.out.println(suche);
            projekt.schauspielerNetzwerk(Integer.parseInt(suche));
        }
    }
}
