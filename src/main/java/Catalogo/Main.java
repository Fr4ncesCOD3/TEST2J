package Catalogo;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale che gestisce l'interfaccia utente del catalogo bibliotecario.
 * Permette di gestire libri e riviste attraverso un menu interattivo.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Archivio archivio = new Archivio();

    /**
     * Metodo principale che avvia l'applicazione.
     * Inizializza l'archivio con alcuni elementi di esempio e gestisce il menu principale.
     */
    public static void main(String[] args) {
        inizializzaArchivio();
        
        while (true) {
            try {
                mostraMenu();
                int scelta = Integer.parseInt(scanner.nextLine());
                
                switch (scelta) {
                    case 1:
                        aggiungiElemento();
                        break;
                    case 2:
                        cercaPerISBN();
                        break;
                    case 3:
                        cercaPerAnno();
                        break;
                    case 4:
                        cercaPerAutore();
                        break;
                    case 5:
                        aggiornaElemento();
                        break;
                    case 6:
                        System.out.println("Arrivederci!");
                        return;
                    default:
                        System.out.println("Scelta non valida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserire un numero valido!");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    /**
     * Visualizza il menu principale con tutte le opzioni disponibili.
     */
    private static void mostraMenu() {
        System.out.println("\n=== GESTIONE CATALOGO ===");
        System.out.println("1. Aggiungi elemento");
        System.out.println("2. Cerca per ISBN");
        System.out.println("3. Cerca per anno");
        System.out.println("4. Cerca per autore");
        System.out.println("5. Aggiorna elemento");
        System.out.println("6. Esci");
        System.out.print("Scelta: ");
    }

    /**
     * Inizializza l'archivio con alcuni elementi di esempio.
     * Aggiunge due libri e due riviste come dati iniziali.
     */
    private static void inizializzaArchivio() {
        try {
            archivio.aggiungiElemento(new Libro("12345", "Il Signore degli Anelli", 1954, 1200, "J.R.R. Tolkien", "Fantasy"));
            archivio.aggiungiElemento(new Libro("67890", "Il Codice da Vinci", 2003, 500, "Dan Brown", "Thriller"));
            archivio.aggiungiElemento(new Rivista("11223", "National Geographic", 2023, 100, Periodicita.MENSILE));
            archivio.aggiungiElemento(new Rivista("44556", "Time", 2023, 80, Periodicita.SETTIMANALE));
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione: " + e.getMessage());
        }
    }

    /**
     * Gestisce l'aggiunta di un nuovo elemento al catalogo.
     * Permette di aggiungere sia libri che riviste richiedendo i dati necessari.
     */
    private static void aggiungiElemento() {
        System.out.println("Tipo di elemento (1=Libro, 2=Rivista): ");
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Anno: ");
        int anno = Integer.parseInt(scanner.nextLine());
        System.out.print("Pagine: ");
        int pagine = Integer.parseInt(scanner.nextLine());

        try {
            if (tipo == 1) {
                System.out.print("Autore: ");
                String autore = scanner.nextLine();
                System.out.print("Genere: ");
                String genere = scanner.nextLine();
                archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
            } else if (tipo == 2) {
                System.out.println("Periodicità (SETTIMANALE/MENSILE/SEMESTRALE): ");
                Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, periodicita));
            }
            System.out.println("Elemento aggiunto con successo!");
        } catch (Exception e) {
            System.out.println("Errore nell'aggiunta: " + e.getMessage());
        }
    }

    /**
     * Cerca un elemento nel catalogo tramite il suo codice ISBN.
     */
    private static void cercaPerISBN() {
        System.out.print("Inserisci ISBN da cercare: ");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo elemento = archivio.cercaPerISBN(isbn);
            System.out.println("Elemento trovato: " + elemento);
        } catch (ElementoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cerca elementi nel catalogo per anno di pubblicazione.
     */
    private static void cercaPerAnno() {
        System.out.print("Inserisci anno da cercare: ");
        int anno = Integer.parseInt(scanner.nextLine());
        List<ElementoCatalogo> risultati = archivio.cercaPerAnno(anno);
        if (risultati.isEmpty()) {
            System.out.println("Nessun elemento trovato per l'anno " + anno);
        } else {
            risultati.forEach(System.out::println);
        }
    }

    /**
     * Cerca libri nel catalogo per autore.
     */
    private static void cercaPerAutore() {
        System.out.print("Inserisci autore da cercare: ");
        String autore = scanner.nextLine();
        List<ElementoCatalogo> risultati = archivio.cercaPerAutore(autore);
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro trovato per l'autore " + autore);
        } else {
            risultati.forEach(System.out::println);
        }
    }

    /**
     * Gestisce l'aggiornamento di un elemento esistente nel catalogo.
     * Identifica il tipo di elemento e chiama il metodo appropriato per l'aggiornamento.
     */
    private static void aggiornaElemento() {
        System.out.print("Inserisci ISBN dell'elemento da aggiornare: ");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo vecchioElemento = archivio.cercaPerISBN(isbn);
            System.out.println("Elemento trovato: " + vecchioElemento);
            
            if (vecchioElemento instanceof Libro) {
                aggiornaLibro(isbn);
            } else if (vecchioElemento instanceof Rivista) {
                aggiornaRivista(isbn);
            }
        } catch (ElementoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Aggiorna i dati di un libro esistente nel catalogo.
     * @param isbn Il codice ISBN del libro da aggiornare
     */
    private static void aggiornaLibro(String isbn) throws ElementoNonTrovatoException {
        System.out.println("Inserisci i nuovi dati:");
        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Anno: ");
        int anno = Integer.parseInt(scanner.nextLine());
        System.out.print("Pagine: ");
        int pagine = Integer.parseInt(scanner.nextLine());
        System.out.print("Autore: ");
        String autore = scanner.nextLine();
        System.out.print("Genere: ");
        String genere = scanner.nextLine();

        Libro nuovoLibro = new Libro(isbn, titolo, anno, pagine, autore, genere);
        archivio.aggiornaElemento(isbn, nuovoLibro);
        System.out.println("Libro aggiornato con successo!");
    }

    /**
     * Aggiorna i dati di una rivista esistente nel catalogo.
     * @param isbn Il codice ISBN della rivista da aggiornare
     */
    private static void aggiornaRivista(String isbn) throws ElementoNonTrovatoException {
        System.out.println("Inserisci i nuovi dati:");
        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Anno: ");
        int anno = Integer.parseInt(scanner.nextLine());
        System.out.print("Pagine: ");
        int pagine = Integer.parseInt(scanner.nextLine());
        System.out.println("Periodicità (SETTIMANALE/MENSILE/SEMESTRALE): ");
        Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());

        Rivista nuovaRivista = new Rivista(isbn, titolo, anno, pagine, periodicita);
        archivio.aggiornaElemento(isbn, nuovaRivista);
        System.out.println("Rivista aggiornata con successo!");
    }
}