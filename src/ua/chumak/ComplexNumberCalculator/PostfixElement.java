package ua.chumak.ComplexNumberCalculator;

import ua.chumak.ComplexNumberCalculator.Exceptions.*;


public class PostfixElement implements PostfixElementType {

    private int elementType = -1;
    private int operatorType = -1;
    private ComplexNumber number = null;
    private String value = "";
    private int index = 0; //first character index of the source line element

    /**
     * @param value string containing a new element
     *              (number or operator)
     * @param index the first character index of the element in
     *              the source line
     */
    public PostfixElement(String value, int index)
            throws UnrecognizableElementException {
        parseElement(value);
        this.value = value;
        this.index = index;
    }

    /* Defines the type and parameters of the element specified by the value string */
    private void parseElement(String value)
            throws UnrecognizableElementException {
        if (value.equals("(")) {
            elementType = PostfixElementType.OPERATOR;
            operatorType = PostfixElementType.LEFT_BRACKET;
            return;
        }
        if (value.equals(")")) {
            elementType = PostfixElementType.OPERATOR;
            operatorType = PostfixElementType.RIGHT_BRACKET;
            return;
        }
        if (value.equals("+")) {
            elementType = PostfixElementType.OPERATOR;
            operatorType = PostfixElementType.PLUS;
            return;
        }
        if (value.equals("-")) {
            elementType = PostfixElementType.OPERATOR;
            operatorType = PostfixElementType.MINUS;
            return;
        }
        if (value.equals("*")) {
            elementType = PostfixElementType.OPERATOR;
            operatorType = PostfixElementType.MULTIPLICATION;
            return;
        }
        if (value.equals("/")) {
            elementType = PostfixElementType.OPERATOR;
            operatorType = PostfixElementType.DIVISION;
            return;
        }

        try {
            number = new ComplexNumber(value);
            elementType = PostfixElementType.NUMBER;
        } catch (java.text.ParseException pe) {
            throw new UnrecognizableElementException(value);
        }
    }

    /**
     * Returns the text description of this element
     */
    public String toString() {
        String retValue = "";
        if (elementType == -1)
            retValue = "Value not set";
        else {
            if (elementType == PostfixElementType.OPERATOR)
                retValue = this.value;
            if (elementType == PostfixElementType.NUMBER)
                retValue = number.toString();
        }
        return retValue;
    }

    /**
     * Returns the element type
     */
    public int getElementType() {
        return elementType;
    }

    /**
     * If this element is an operator, it returns its type
     */
    public int getOperatorType() throws IncorrectTypeException {
        if (elementType != PostfixElementType.OPERATOR)
            throw new IncorrectTypeException();
        return operatorType;
    }

    /**
     * If this element is an operator, it returns it as a priority. If operator - returns its type.
     */
    public int getOperatorPriority() throws IncorrectTypeException {
        if (elementType != PostfixElementType.OPERATOR)
            throw new IncorrectTypeException();
        return PostfixElementType.priorities[operatorType];
    }

    /**
     * If this element is a number, it returns it
     */
    public ComplexNumber getNumber() throws IncorrectTypeException {
        if (elementType != PostfixElementType.NUMBER)
            throw new IncorrectTypeException();
        return number;
    }

    /**
     * Returns true if this element is
     * operator, otherwise false
     */
    public boolean isOperator() {
        if (elementType == PostfixElementType.OPERATOR)
            return true;
        return false;
    }

    /**
     * Returns true if this element is number, otherwise - false
     */
    public boolean isNumber() {
        if (elementType == PostfixElementType.NUMBER)
            return true;
        return false;
    }

    /**
     * Returns the first character index of source line element.
     * Is used to inform about errors
     */
    public int getElementIndex() {
        return index;
    }
}
