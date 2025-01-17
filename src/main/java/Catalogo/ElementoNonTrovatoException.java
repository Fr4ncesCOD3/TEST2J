package Catalogo;
//Eccezione personalizzata per gestire il caso in cui un elemento non viene trovato nel catalogo
public class ElementoNonTrovatoException extends Exception {
    public ElementoNonTrovatoException(String message) {
        super(message);
    }
} 