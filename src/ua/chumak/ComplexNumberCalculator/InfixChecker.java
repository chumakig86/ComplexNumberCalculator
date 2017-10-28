package ua.chumak.ComplexNumberCalculator;

import java.util.Vector;
import java.util.Iterator;

import ua.chumak.ComplexNumberCalculator.Exceptions.*;

public class InfixChecker {
    Vector elements;

    /**
     * @param infixVector pointer to Vector class object
     *                    containing the analyzed sequence
     */
    public InfixChecker(Vector infixVector) {
        elements = infixVector;
    }


    public void check() throws IncorrectElementException,
            IncorrectTypeException {
        if (elements == null)
            return;
        int rightBracketsCount = 0;
        int leftBracketsCount = 0;
        int operatorsCount = 0;
        int numbersCount = 0;
        PostfixElement curElem = null;
        PostfixElement prevElem = null;
        boolean firstElem = true;

        Iterator iterator = elements.iterator();
        if (iterator == null)
            return;

        while (iterator.hasNext()) {
            curElem = (PostfixElement) iterator.next();

            //first element can be either a number or an opening parenthesis
            if (firstElem) {
                if (curElem.isOperator() && (curElem.getOperatorType() !=
                        PostfixElementType.LEFT_BRACKET))
                    throw new IncorrectElementException("An error occurred while parsing the expression",
                            curElem.getElementIndex());
                firstElem = false;
            }

            //counting the number of parentheses and the number of
            //written operators (brackets are not counted) and numbers
            if (curElem.getElementType() == PostfixElementType.OPERATOR) {
                numbersCount = 0;
                if (curElem.getOperatorType() == PostfixElementType.RIGHT_BRACKET)
                    rightBracketsCount++;
                else if (curElem.getOperatorType() ==
                        PostfixElementType.LEFT_BRACKET)
                    leftBracketsCount++;
                else
                    operatorsCount++;
            }
            if (curElem.getElementType() == PostfixElementType.NUMBER) {
                numbersCount++;
                operatorsCount = 0;
            }

            //numbers and operators must alternate
            if (numbersCount > 1 || operatorsCount > 1)
                throw new IncorrectElementException("An error occurred while parsing the expression",
                        curElem.getElementIndex());

            if (prevElem != null) {
                //after the opening bracket can go either a number or another bracket
                if (prevElem.isOperator() && (prevElem.getOperatorType() ==
                        PostfixElementType.LEFT_BRACKET)) {
                    if (curElem.isOperator())
                        if ((curElem.getOperatorType() !=
                                PostfixElementType.LEFT_BRACKET) &&
                                curElem.getOperatorType() !=
                                        PostfixElementType.RIGHT_BRACKET)
                            throw new IncorrectElementException("An error occurred while parsing the expression",
                                    curElem.getElementIndex());
                }

                //after the closing bracket, any operator can go, except
                //number or opening bracket
                if (prevElem.isOperator() && (prevElem.getOperatorType() ==
                        PostfixElementType.RIGHT_BRACKET)) {
                    if (curElem.isNumber())
                        throw new IncorrectElementException("An error occurred while parsing the expression",
                                curElem.getElementIndex());
                    else if (curElem.getOperatorType() ==
                            PostfixElementType.LEFT_BRACKET)
                        throw new IncorrectElementException("An error occurred while parsing the expression",
                                curElem.getElementIndex());
                }
            }

            prevElem = curElem;
        }

        //last element can be either a number or a closing bracket
        if (curElem.isOperator() && (curElem.getOperatorType() !=
                PostfixElementType.RIGHT_BRACKET))
            throw new IncorrectElementException("An error occurred while parsing the expression",
                    curElem.getElementIndex());

        //number of opening brackets must be equal to the number of closing brackets
        if (leftBracketsCount > rightBracketsCount)
            throw new IncorrectElementException("Missing \")\"", 0);
        if (leftBracketsCount < rightBracketsCount)
            throw new IncorrectElementException("Missing \"(\"", 0);
    }
}
