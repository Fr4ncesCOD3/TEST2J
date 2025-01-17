package Catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Archivio {
    private List<ElementoCatalogo> elementi;

    public Archivio() {
        this.elementi = new ArrayList<>();
    }

    public void aggiungiElemento(ElementoCatalogo elemento) throws IllegalArgumentException {
        if (elementi.stream()
                .anyMatch(e -> e.getCodiceISBN().equals(elemento.getCodiceISBN()))) {
            throw new IllegalArgumentException("Elemento con ISBN " + elemento.getCodiceISBN() + " già presente");
        }
        elementi.add(elemento);
    }

    public ElementoCatalogo cercaPerISBN(String isbn) throws ElementoNonTrovatoException {
        return elementi.stream()
                .filter(e -> e.getCodiceISBN().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new ElementoNonTrovatoException("Elemento con ISBN " + isbn + " non trovato"));
    }

    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        return elementi.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<ElementoCatalogo> cercaPerAutore(String autore) {
        return elementi.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(l -> l.getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) throws ElementoNonTrovatoException {
        ElementoCatalogo elementoDaAggiornare = cercaPerISBN(isbn);
        int index = elementi.indexOf(elementoDaAggiornare);
        elementi.set(index, nuovoElemento);
    }

    public List<ElementoCatalogo> getElementi() {
        return new ArrayList<>(elementi);
    }
}
