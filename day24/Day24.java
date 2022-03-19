
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

import java.util.Stack;

/*
    is div z 1 add to stack
    if not remove from stack
*/

public class Day24 {
    private String _puzzleinput;
    private String _filename;

    public Day24(String file_name) {
        _filename = file_name;
        getPuzzleInput(_filename);
        _puzzleinput = _puzzleinput.trim();
    }

    public String getPuzzleInput(String filename) {
        Path fileName = Path.of(filename);
        try {
            _puzzleinput = Files.readString(fileName);
        } catch (IOException e) {
            System.out.println("Error at file read");
            e.printStackTrace();
            return "";
        }
        return _puzzleinput;

    }

    public String solvePart1() {
        var input = getPuzzleInput(_filename).trim().split("\n");
        int[] digits = new int[14];
        var equations = extractEquations(input);
        for (var equation : equations) {
            if (equation.rhs.value > 0) {
                digits[equation.lhs.index] = 9;
                digits[equation.rhs.index] = (int) (9 - equation.rhs.value);
            } else {
                digits[equation.rhs.index] = 9;
                digits[equation.lhs.index] = (int) (9 + equation.rhs.value);
            }
        }
        var results = 0L;
        for (var i = 0; i < 14; i++) {
            results = results * 10 + digits[i];
        }
        return String.valueOf(results);
    }

    public String solvePart2() {
        var input = getPuzzleInput(_filename).trim().split("\n");
        int[] digits = new int[14];
        var equations = extractEquations(input);
        for (var equation : equations) {
            if (equation.rhs.value > 0) {
                digits[equation.lhs.index] = (int) (1 + equation.rhs.value);
                digits[equation.rhs.index] = 1;
            } else {
                digits[equation.rhs.index] = (int) (1 - equation.rhs.value);
                digits[equation.lhs.index] = 1;
            }
        }
        var results = 0L;
        for (var i = 0; i < 14; i++) {
            results = results * 10 + digits[i];
        }
        return String.valueOf(results);
    }

    Equation[] extractEquations(String[] input) {
        Equation[] equations = new Equation[7];
        var stack = new Stack<Expression>();
        int j = 0;
        for (var i = 0; i < 14; i++) {
            // if div z 1
            if (input[i * 18 + 4].equals("div z 1")) {
                var left_value = Long.parseLong(input[i * 18 + 15].split(" ")[2]);
                stack.push(new Expression(i, left_value));
            } else {
                var right_value = Long.parseLong(input[i * 18 + 5].split(" ")[2]);
                var left_expression = stack.pop();
                equations[j++] = new Equation(new Expression(i, 0L),
                        new Expression(left_expression.index, left_expression.value + right_value));
            }
        }
        return equations;
    }

    // w3 + 4 = w4 + 8
    record Equation(Expression lhs, Expression rhs) {

    }

    record Expression(int index, long value) {

    }

}
