package ua.chumak.ComplexNumberCalculator.Exceptions;

public class UnrecognizableElementException extends Exception {
    /**
     * @param element A string containing a parameter that
     *                was impossible to recognize
     */
    public UnrecognizableElementException(String element) {
        super("Can't recognize the item \"" + element + "\"");
    }
}
