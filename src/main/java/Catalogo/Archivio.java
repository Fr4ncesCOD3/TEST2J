package Catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe che gestisce l'archivio degli elementi del catalogo bibliotecario.
 * Permette di aggiungere, cercare e aggiornare libri e riviste.
 */
public class Archivio {
    // Lista che contiene tutti gli elementi del catalogo
    private List<ElementoCatalogo> elementi;

    /**
     * Costruttore che inizializza un nuovo archivio vuoto
     */
    public Archivio() {
        this.elementi = new ArrayList<>();
    }

    /**
     * Aggiunge un nuovo elemento al catalogo
     * @param elemento L'elemento da aggiungere
     * @throws IllegalArgumentException se esiste già un elemento con lo stesso ISBN
     */
    public void aggiungiElemento(ElementoCatalogo elemento) throws IllegalArgumentException {
        if (elementi.stream()
                .anyMatch(e -> e.getCodiceISBN().equals(elemento.getCodiceISBN()))) {
            throw new IllegalArgumentException("Elemento con ISBN " + elemento.getCodiceISBN() + " già presente");
        }
        elementi.add(elemento);
    }

    /**
     * Cerca un elemento tramite il suo codice ISBN
     * @param isbn Il codice ISBN da cercare
     * @return L'elemento trovato
     * @throws ElementoNonTrovatoException se nessun elemento ha l'ISBN specificato
     */
    public ElementoCatalogo cercaPerISBN(String isbn) throws ElementoNonTrovatoException {
        return elementi.stream()
                .filter(e -> e.getCodiceISBN().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new ElementoNonTrovatoException("Elemento con ISBN " + isbn + " non trovato"));
    }

    /**
     * Cerca tutti gli elementi pubblicati in un determinato anno
     * @param anno L'anno di pubblicazione da cercare
     * @return Lista degli elementi pubblicati nell'anno specificato
     */
    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return elementi.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    /**
     * Cerca tutti i libri di un determinato autore
     * @param autore Il nome dell'autore da cercare
     * @return Lista dei libri dell'autore specificato
     */
    public List<ElementoCatalogo> cercaPerAutore(String autore) {
        return elementi.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(l -> l.getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Aggiorna un elemento esistente nel catalogo
     * @param isbn L'ISBN dell'elemento da aggiornare
     * @param nuovoElemento Il nuovo elemento con cui sostituire quello esistente
     * @throws ElementoNonTrovatoException se nessun elemento ha l'ISBN specificato
     */
    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) throws ElementoNonTrovatoException {
        ElementoCatalogo elementoDaAggiornare = cercaPerISBN(isbn);
        int index = elementi.indexOf(elementoDaAggiornare);
        elementi.set(index, nuovoElemento);
    }

    /**
     * Restituisce una copia della lista di tutti gli elementi nel catalogo
     * @return Lista degli elementi nel catalogo
     */
    public List<ElementoCatalogo> getElementi() {
        return new ArrayList<>(elementi);
    }
}
