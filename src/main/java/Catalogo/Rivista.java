package Catalogo;

// Classe enum per definire la periodicità delle riviste
enum Periodicita {
    SETTIMANALE, MENSILE, SEMESTRALE
}

// Sottoclasse che rappresenta una rivista
public class Rivista extends ElementoCatalogo {
    private Periodicita periodicita; // Periodicità della rivista

    // Costruttore
    public Rivista(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    // Getter e Setter
    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return super.toString() + ", Rivista{" +
                "periodicita=" + periodicita +
                '}';
    }
}