package ua.chumak.ComplexNumberCalculator.Exceptions;

public class IncorrectElementException extends Exception {
    int index = 0;

    /**
     * @param mes   error description
     * @param index index of the invalid element's first character
     */
    public IncorrectElementException(String mes, int index) {
        super(mes);
        this.index = index;
    }
}
