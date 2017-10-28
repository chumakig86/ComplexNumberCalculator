package ua.chumak.ComplexNumberCalculator;

public interface PostfixElementType {
    //element type
    final int NUMBER = 100;
    final int OPERATOR = 101;
    //number of operators
    final int PLUS = 0;
    final int MINUS = 1;
    final int DIVISION = 2;
    final int MULTIPLICATION = 3;
    final int LEFT_BRACKET = 4;
    final int RIGHT_BRACKET = 5;
    //operator priority (array index corresponds to the
    //operator number) higher number=higher priority
    //for example MULTIPLICATION operator has priotiry =1.
    final int[] priorities = {0, 0, 1, 1, 2, 2};
}
