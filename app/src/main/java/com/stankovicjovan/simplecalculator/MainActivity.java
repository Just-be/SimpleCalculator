package com.stankovicjovan.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //everything that is written in calculation area
    private TextView calcArea;

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcArea = findViewById(R.id.calcArea);
    }

    public void writeSeven(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("7");
        } else {
            this.calcArea.append("7");
        }
    }

    public void writeEight(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("8");
        } else {
            this.calcArea.append("8");
        }
    }

    public void writeNine(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("9");
        } else {
            this.calcArea.append("9");
        }
    }

    public void writeFour(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("4");
        } else {
            this.calcArea.append("4");
        }
    }

    public void writeFive(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("5");
        } else {
            this.calcArea.append("5");
        }
    }

    public void writeSix(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("6");
        } else {
            this.calcArea.append("6");
        }
    }

    public void writeOne(View view) {

        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("1");
        } else {
            this.calcArea.append("1");
        }
    }

    public void writeTwo(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("2");
        } else {
            this.calcArea.append("2");
        }
    }

    public void writeThree(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            this.calcArea.setText("3");
        } else {
            this.calcArea.append("3");
        }
    }

    public void writeZero(View view) {
        if(!this.calcArea.getText().toString().equals("0")) {
            this.calcArea.append("0");
        }
    }

    //delete everything from calc area and write "0"
    public void deleteAll(View view) {
        this.calcArea.setText("0");
    }

    //deletes last character from calc area
    public void deleteLastChar(){
        String currCalcArea = this.calcArea.getText().toString();
        this.calcArea.setText(currCalcArea.substring(0, currCalcArea.length()-1));
    }

    public void deleteOne(View view) {

        String currCalcArea = this.calcArea.getText().toString();

        //deleting the last number writes "0" in the calc area
        if(currCalcArea.length() == 1) {
            deleteAll(view);
            return;
        }

        //otherwise delete the last digit of the given number (working with Strings)
        if(currCalcArea != null && currCalcArea.length() > 1) {
            deleteLastChar();
        }
    }

    public void writeSymbol(char symbol) {
        String currCalcArea = this.calcArea.getText().toString();
        char currChar = currCalcArea.charAt(currCalcArea.length()-1);

        //if user decides to write - at beginning of expression we need to delete the starting 0
        if(symbol == '-' && currCalcArea.length() == 1 && currChar == '0') {
            deleteLastChar();
            this.calcArea.append("" + symbol);
            return;
        }

        //if last character is a number simply append the symbol...
        if(currChar >= '0' && currChar <= '9'){
            this.calcArea.append("" + symbol);
        }
        //...and if it's not delete the previous symbol and write a new one
        else {
            deleteLastChar();
            this.calcArea.append("" + symbol);
        }
    }

    public void writePlus(View view) {
        writeSymbol('+');
    }

    public void writeMinus(View view) {
        writeSymbol('-');
    }

    public void writeMultiply(View view) {
        writeSymbol('×');
    }

    public void writeDivide(View view) {
        writeSymbol('÷');
    }

    public void calc(View view) {
        String exp = this.calcArea.getText().toString();
        String newExp = exp.replaceAll("×", "*");
        exp = newExp.replaceAll("÷", "/");

        //eval can return NaN or Infinity when we divide 0/0 or any number/0
        //if it does, we can't continue, but instead set calc area to 0 and return
        if(exp.equals("NaN") || exp.equals("Infinity")){
            this.calcArea.setText("0");
            return;
        }

        Double calcExp = eval(exp);
        int intCalcExp = calcExp.intValue();
        if(intCalcExp == Integer.MAX_VALUE) {
            this.calcArea.setText(Double.toString(calcExp));
            return;
        }

        if(calcExp % 1 == 0){
            this.calcArea.setText(Integer.toString(intCalcExp));
        } else {
            this.calcArea.setText(Double.toString(calcExp));
        }

    }
}