package ua.chumak.ComplexNumberCalculator;

import java.util.Vector;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Iterator;

import ua.chumak.ComplexNumberCalculator.Exceptions.*;

public class PostfixConverter {
    private Vector postfixVector = null;
    private Vector infixVector = null;
    String infixStr;

    /**
     * @param str string containing an expression in the infix form
     */
    public PostfixConverter(String str) {
        infixStr = str;
    }

    /**
     * Converts a string written in infix form
     * to the array of PostfixElement objects in the postfix form
     *
     * @throws IllegalArgumentException       if there was an attempt
     *                                        to create an array of objects with a negative length
     * @throws UnrecognizableElementException if it is impossible
     *                                        to recognize a new item
     * @throws IncorrectTypeException         if there was an attempt to call
     *                                        one of the PostfixElement class methods, which is invalid for
     *                                        this class instance
     */
    public Vector convertToPostfix() throws IllegalArgumentException,
            UnrecognizableElementException, IncorrectTypeException,
            IncorrectElementException {
        infixVector = new Vector(10, 5);
        parseStr(infixStr);
        InfixChecker checker = new InfixChecker(infixVector);
        checker.check();
        convert();
        return postfixVector;
    }

    /* Converts a string written in infix form to an array
       of PostfixElement objects in infix form */
    private void parseStr(String str) throws UnrecognizableElementException,
            IncorrectTypeException {
        infixStr = removeSpaces(str);
        StringTokenizer st = new StringTokenizer(infixStr, "()+-*/", true);
        PostfixElement currentElement = null;
        PostfixElement previousElement = null;
        int tokenIndex = 0;
        int curIndex = 0;
        boolean firstElement = true;
        while (st.hasMoreTokens()) {
            String currentToken = st.nextToken();
            tokenIndex = str.indexOf(currentToken, curIndex);
            currentElement = new PostfixElement(currentToken, tokenIndex);
            if (previousElement != null) {
                if (previousElement.isOperator() &&
                        (previousElement.getOperatorType() ==
                                PostfixElementType.LEFT_BRACKET)
                        && currentElement.isOperator())
                    if ((currentElement.getOperatorType() ==
                            PostfixElementType.MINUS) ||
                            (currentElement.getOperatorType() ==
                                    PostfixElementType.PLUS)) {
                        currentToken += st.nextToken();
                        tokenIndex = str.indexOf(currentToken, curIndex);
                        currentElement = new PostfixElement(currentToken,
                                tokenIndex);
                    }
            }
            if (firstElement) {
                if (currentElement.isOperator() &&
                        ((currentElement.getOperatorType() == PostfixElementType.MINUS) ||
                                (currentElement.getOperatorType() == PostfixElementType.PLUS)))
                    currentToken += st.nextToken();
                tokenIndex = str.indexOf(currentToken, curIndex);
                currentElement = new PostfixElement(currentToken,
                        tokenIndex);
                firstElement = false;
            }

            infixVector.add(currentElement);
            curIndex = tokenIndex + currentToken.length();
            previousElement = currentElement;
        }
    }

    /* Converts an array of PostfixElement objects written
     in infix form into an array of PostfixElement objects
     in postfix form*/
    private void convert() throws IllegalArgumentException,
            UnrecognizableElementException, IncorrectTypeException {
        postfixVector = new Vector(infixVector.size());
        int curIndex = 0;
        PostfixElement curElement = null;
        Stack s = new Stack();
        s.push(new PostfixElement("(", 0));
        infixVector.add(new PostfixElement(")", 0));
        while (s.isEmpty() == false) {
            curElement = (PostfixElement) infixVector.get(curIndex);
            curIndex++;
            if (curElement.isNumber()) {
                postfixVector.add(curElement);
                continue;
            }
            if (curElement.isOperator())
                if (curElement.getOperatorType() ==
                        PostfixElementType.LEFT_BRACKET) {
                    s.push(curElement);
                    continue;
                }
            if (curElement.isOperator())
                if (curElement.getOperatorType() !=
                        PostfixElementType.LEFT_BRACKET &&
                        curElement.getOperatorType() !=
                                PostfixElementType.RIGHT_BRACKET) {
                    while (true) {
                        PostfixElement peekOperator = (PostfixElement) s.peek();
                        if (peekOperator.getOperatorType() ==
                                PostfixElementType.LEFT_BRACKET)
                            break;
                        if (peekOperator.getOperatorPriority() >=
                                curElement.getOperatorPriority())
                            postfixVector.add(s.pop());
                        else
                            break;
                    }
                    s.push(curElement);
                    continue;
                }
            if (curElement.isOperator())
                if (curElement.getOperatorType() ==
                        PostfixElementType.RIGHT_BRACKET)
                    while (true) {
                        PostfixElement peekOperator = (PostfixElement) s.peek();
                        if (peekOperator.getOperatorType() ==
                                PostfixElementType.LEFT_BRACKET) {
                            s.pop();
                            break;
                        }
                        postfixVector.add(s.pop());
                    }
        }
    }


    private String removeSpaces(String str) {
        StringTokenizer st = new StringTokenizer(str);
        StringBuffer temp = new StringBuffer(str.length());
        while (st.hasMoreTokens()) {
            temp.append(st.nextToken());
        }
        return temp.toString();
    }

}
