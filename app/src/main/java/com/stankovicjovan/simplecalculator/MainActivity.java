package com.stankovicjovan.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.stankovicjovan.simplecalculator.databinding.ActivityMainBinding;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //everything that is written in calculation area
    private TextView calcArea;
    //button for writing . on the calculation area
    private Button dotButton;
    //history area
    private TextView historyArea;

    private ActivityMainBinding binding;
    private History history;

    public static double eval(final String str) throws RuntimeException, InvocationTargetException {
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        history = History.getInstance();

        binding.setHistory(history);

        calcArea = findViewById(R.id.calcArea);

        //calc area should scroll when the expression is too big
        calcArea.setMovementMethod(new ScrollingMovementMethod());

        historyArea = findViewById(R.id.historyArea);
        historyArea.setMovementMethod(new ScrollingMovementMethod());

        dotButton = findViewById(R.id.btnDot);
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
        dotButton.setEnabled(true);
    }

    //deletes last character from calc area
    public void deleteLastChar(){
        String currCalcArea = this.calcArea.getText().toString();
        if(currCalcArea.charAt(currCalcArea.length()-1) == '.')
            dotButton.setEnabled(true);
        this.calcArea.setText(currCalcArea.substring(0, currCalcArea.length()-1));
    }

    public void deleteOne(View view) {

        String currCalcArea = this.calcArea.getText().toString();

        //deleting the last number writes "0" in the calc area
        if(currCalcArea.length() == 1) {
            deleteAll(view);
            return;
        }

        //otherwise delete the last character of the given input (working with Strings)
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
        dotButton.setEnabled(true);
    }

    public void writeMinus(View view) {
        writeSymbol('-');
        dotButton.setEnabled(true);
    }

    public void writeMultiply(View view) {
        writeSymbol('×');
        dotButton.setEnabled(true);
    }

    public void writeDivide(View view) {
        writeSymbol('÷');
        dotButton.setEnabled(true);
    }

    public void writeDot(View view) {
        String currCalcArea = calcArea.getText().toString();
        if(currCalcArea.charAt(currCalcArea.length()-1) != '.'){
            this.calcArea.append(".");
            //once the dot is clicked it's not longer clickable until the user enters operator
            //this way we avoid the errors
            dotButton.setEnabled(false);
        }
    }

    public void calc(View view) {
        String exp = this.calcArea.getText().toString();

        //eval recognizes * and / instead of × and ÷
        // so I need to replace all occurrences of those symbols
        String newExp = exp.replaceAll("×", "*");
        exp = newExp.replaceAll("÷", "/");

        //eval can return NaN or Infinity when we divide 0/0 or any number/0
        //if it does, we can't continue, but instead set calc area to 0 and return
        if(exp.equals("NaN") || exp.equals("Infinity")){
            this.calcArea.setText("0");
            return;
        }

        //resolving exceptions (when user evaluates unknown expression)
        Double calcExp = null;
        try {
            calcExp = eval(exp);
        } catch (InvocationTargetException e) {
            String message = getApplicationContext().getString(R.string.invalid_expression) + "";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            deleteAll(view);
            return;
        } catch (RuntimeException e) {
            String message = getApplicationContext().getString(R.string.invalid_expression) + "";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            deleteAll(view);
            return;
        }

        int intCalcExp = calcExp.intValue();

        //historyText is what is going to be written in the history
        String historyText = calcArea.getText().toString();
        String resultInt = Integer.toString(intCalcExp);
        String resultDouble = Double.toString(calcExp);

        //if the calculated value is too high only this block of code is executed..
        if(intCalcExp == Integer.MAX_VALUE) {
            this.calcArea.setText(resultDouble);
            historyText = historyText + "=" + resultDouble;
            history.addCalcHistory(historyText);
            binding.setHistory(history);
            next(view);
            calcArea.scrollTo(0,0);
            historyArea.scrollTo(0,0);
            calcArea.setText("0");
            lastHistoryResult(view);
            return;
        }

        //calculated value can be either whole number or decimal
        //and we need to set historyText according to that
        if(calcExp % 1 == 0){
            this.calcArea.setText(resultInt);
            historyText = historyText + "=" + resultInt;
            history.addCalcHistory(historyText);
            binding.setHistory(history);
            lastHistoryResult(view);

        } else {
            this.calcArea.setText(resultDouble);
            historyText = historyText + "=" + resultDouble;
            history.addCalcHistory(historyText);
            binding.setHistory(history);
            lastHistoryResult(view);
        }

        calcArea.scrollTo(0,0);
        historyArea.scrollTo(0,0);
        calcArea.setText("0");
    }

    //logic when user clicks previous button
    public void prev(View view) {
        if(history.getCurrent() > 0) {
            history.currentDecrement();
            binding.setHistory(history);
        }
    }

    //logic when user clicks next button
    public void next(View view) {
        if(history.getCurrent() < history.getSize() - 1) {
            history.currentIncrement();
            binding.setHistory(history);
        }
    }

    public void clearHistory(View view) {
        history.clearHistory();
        binding.setHistory(history);
    }

    //user is always taken to the last result no matter where he is in history
    public void lastHistoryResult(View view){
        int i = 0;
        while(i < history.getCurrent()+1){
            next(view);
            i++;
        }
    }
}