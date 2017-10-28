package ua.chumak.ComplexNumberCalculator;

import java.util.Vector;
import java.util.Stack;

import ua.chumak.ComplexNumberCalculator.Exceptions.*;


public class PostfixCalculator {

    private Vector postfixVector = null;
    private ComplexNumber result = null;

    /**
     * @param postfixVector object of java.util.Vector class,
     *                      containing an array of data in a postfix form
     */
    public PostfixCalculator(Vector postfixVector) {
        this.postfixVector = postfixVector;
    }


    public ComplexNumber calculate() throws java.text.ParseException,
            IncorrectTypeException, UnrecognizableElementException {
        result = new ComplexNumber("0");
        Stack stack = new Stack();
        PostfixElement temp = null;
        for (int i = 0; i < postfixVector.size(); i++) {
            temp = (PostfixElement) postfixVector.get(i);
            if (temp.isNumber())
                stack.push(temp);
            if (temp.isOperator()) {
                ComplexNumber y = ((PostfixElement) stack.pop()).getNumber();
                ComplexNumber x = ((PostfixElement) stack.pop()).getNumber();
                ComplexNumber res = solveOperation(x, y,
                        temp.getOperatorType());
                stack.push(new PostfixElement(res.toString(), 0));
            }
        }
        result = ((PostfixElement) stack.pop()).getNumber();
        return result;
    }

    /* calculates the result of each operation */
    private ComplexNumber solveOperation(ComplexNumber x, ComplexNumber y,
                                         int operatorType) throws java.text.ParseException {
        ComplexNumber res = new ComplexNumber("0");
        res = x;
        switch (operatorType) {
            case PostfixElementType.PLUS:
                res.add(y);
                break;
            case PostfixElementType.MINUS:
                res.sub(y);
                break;
            case PostfixElementType.MULTIPLICATION:
                res.mult(y);
                break;
            case PostfixElementType.DIVISION:
                res.div(y);
                break;
        }
        return res;
    }
}
