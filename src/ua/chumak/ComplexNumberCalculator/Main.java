package ua.chumak.ComplexNumberCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;


import ua.chumak.ComplexNumberCalculator.Exceptions.*;


public class Main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String answer = null;
            do {
                System.out.println("Enter an expression with complex numbers (Type 'q' to exit):");
                String expression = br.readLine();
                if (expression.equalsIgnoreCase("Q")) return;
                PostfixConverter converter = new PostfixConverter(expression);
                PostfixCalculator calc = new PostfixCalculator(converter.convertToPostfix());
                ComplexNumber result = new ComplexNumber(calc.calculate());
                String res = expression + " = " + result;
                System.out.println(res);
                System.out.println("Want to do another calculation? (y/n): ");
                answer = br.readLine();
            } while (answer.equalsIgnoreCase("Y"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnrecognizableElementException e) {
            e.printStackTrace();
        } catch (IncorrectElementException e) {
            e.printStackTrace();
        } catch (IncorrectTypeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred while parsing the expression");
        }


    }
}
