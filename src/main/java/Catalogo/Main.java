package Catalogo;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Creazione di un catalogo come una lista di elementi
        List<ElementoCatalogo> catalogo = new ArrayList<>();

        // Aggiungere dei libri al catalogo
        catalogo.add(new Libro("12345", "Il Signore degli Anelli", 1954, 1200, "J.R.R. Tolkien", "Fantasy"));
        catalogo.add(new Libro("67890", "Il Codice da Vinci", 2003, 500, "Dan Brown", "Thriller"));

        // Aggiungere delle riviste al catalogo
        catalogo.add(new Rivista("11223", "National Geographic", 2023, 100, Periodicita.MENSILE));
        catalogo.add(new Rivista("44556", "Time", 2023, 80, Periodicita.SETTIMANALE));

        // Stampare l'intero catalogo
        System.out.println("Catalogo bibliotecario:");
        for (ElementoCatalogo elemento : catalogo) {
            System.out.println(elemento);
        }

        // Ricerca di un elemento per codice ISBN
        String codiceDaCercare = "12345";
        System.out.println("\nRicerca per codice ISBN (" + codiceDaCercare + "):");
        for (ElementoCatalogo elemento : catalogo) {
            if (elemento.getCodiceISBN().equals(codiceDaCercare)) {
                System.out.println(elemento);
            }
        }
    }
}