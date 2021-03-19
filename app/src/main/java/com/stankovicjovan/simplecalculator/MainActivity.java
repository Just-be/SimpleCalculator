package com.stankovicjovan.simplecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
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
                    else if (func.equals("log")) x = Math.log10(x);
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
        calcArea.setHorizontallyScrolling(true);

        historyArea = findViewById(R.id.historyArea);
        historyArea.setMovementMethod(new ScrollingMovementMethod());

        if(savedInstanceState != null){

            String calcArea = savedInstanceState.getString(C.tag.KEY_CALC_AREA);
            String historyArea = savedInstanceState.getString(C.tag.KEY_HISTORY_AREA);
            if(calcArea != null && historyArea != null){
                this.calcArea.setText(calcArea);
                this.historyArea.setText(historyArea);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if(calcArea != null && historyArea != null){
            outState.putString(C.tag.KEY_CALC_AREA, calcArea.getText().toString());
            outState.putString(C.tag.KEY_HISTORY_AREA, historyArea.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    public void writeSeven(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("7");
        } else {
            calcArea.append("7");
        }
    }

    public void writeEight(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("8");
        } else {
            calcArea.append("8");
        }
    }

    public void writeNine(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("9");
        } else {
            calcArea.append("9");
        }
    }

    public void writeFour(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("4");
        } else {
            calcArea.append("4");
        }
    }

    public void writeFive(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("5");
        } else {
            calcArea.append("5");
        }
    }

    public void writeSix(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("6");
        } else {
            calcArea.append("6");
        }
    }

    public void writeOne(View view) {

        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("1");
        } else {
            calcArea.append("1");
        }
    }

    public void writeTwo(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("2");
        } else {
            calcArea.append("2");
        }
    }

    public void writeThree(View view) {
        if(calcArea.getText().toString().equals("0")) {
            calcArea.setText("3");
        } else {
            calcArea.append("3");
        }
    }

    public void writeZero(View view) {
        if(!calcArea.getText().toString().equals("0")) {
            calcArea.append("0");
        }
    }

    //delete everything from calc area and write "0"
    public void deleteAll(View view) {
        calcArea.setText("0");
        calcArea.scrollTo(0,0);
    }

    //deletes last character from calc area
    public void deleteLastChar(){
        String currCalcArea = calcArea.getText().toString();
        calcArea.setText(currCalcArea.substring(0, currCalcArea.length()-1));

        //as I delete character by character I want calcArea to move automatically
        int position = calcArea.length();
        Editable etext = (Editable) calcArea.getText();
        Selection.setSelection(etext, position);
    }

    public void deleteOne(View view) {

        String currCalcArea = calcArea.getText().toString();

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

    //for numbers and +, -, *, /, ^
    public void writeSymbol(char symbol) {
        String currCalcArea = calcArea.getText().toString();
        char currChar = currCalcArea.charAt(currCalcArea.length()-1);

        //if user decides to write - at beginning of expression we need to delete the starting 0
        if(symbol == '-' && currCalcArea.length() == 1 && currChar == '0') {
            deleteLastChar();
            calcArea.append("" + symbol);
            return;
        }

        //if last character is a number or left brace or right brace simply append the symbol
        if((currChar >= '0' && currChar <= '9') || currChar == '(' || currChar == ')'){
            calcArea.append("" + symbol);
        }
        //...and if it's not delete the previous symbol and write a new one
        else {
            deleteLastChar();
            calcArea.append("" + symbol);
        }
    }

    //for every other symbol
    public void writeSign(String sign){
        if(calcArea.getText().toString().equals("0")) {
            if(sign.equals(".")) {
                calcArea.append(sign);
                return;
            }
            calcArea.setText(sign);
        } else {
            calcArea.append(sign);
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

    public void writeSqrt(View view) {
        writeSign("√");
    }

    public void writeDot(View view) {
        writeSign(".");
    }

    public void calc(View view) {
        String exp = calcArea.getText().toString();

        //eval recognizes * and / instead of × and ÷
        // so I need to replace all occurrences of those symbols
        String newExp = exp.replaceAll("×", "*");
        String newExp1 = newExp.replaceAll("÷", "/");
        exp = newExp1.replaceAll("√", "sqrt");

        //eval can return NaN or Infinity when we divide 0/0 or any number/0
        //if it does, we can't continue, but instead set calc area to 0 and return
        if(exp.equals("NaN") || exp.equals("Infinity")){
            calcArea.setText("0");
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
            calcArea.setText(resultDouble);
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
            calcArea.setText(resultInt);
            historyText = historyText + "=" + resultInt;
            history.addCalcHistory(historyText);
            binding.setHistory(history);
            lastHistoryResult(view);

        } else {
            calcArea.setText(resultDouble);
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
        //we are not going to include empty character when user goes through history
        //that's why we are checking if the current is bigger than 1
        if(history.getCurrent() > 1) {
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

    //user is always taken to the last result when submitting new expression
    // no matter where he is in history
    public void lastHistoryResult(View view){
        int i = 0;
        while(i < history.getCurrent()+1){
            next(view);
            i++;
        }
    }

    public void writeBracketLeft(View view) {
        writeSign("(");
    }

    public void writeBracketRight(View view) {
        writeSign(")");
    }

    public void writeExponent(View view) {
        writeSymbol('^');
    }

    public void writeE(View view) {
        writeSign("2.71828183");
    }

    public void convertToDecimal(View view) {
        writeSign("^(-1)");
    }

    public void writePi(View view) {
        writeSign("3.14159265");
    }

    public void writeSin(View view) {
        writeSign("sin(");
    }

    public void writeCos(View view) {
        writeSign("cos(");
    }

    public void writeTan(View view) {
        writeSign("tan(");
    }

    public void writeLog(View view) {
        writeSign("log(");
    }
}