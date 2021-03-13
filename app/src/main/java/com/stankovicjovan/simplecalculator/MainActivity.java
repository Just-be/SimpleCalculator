package com.stankovicjovan.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView calcArea;
    private String calcNumS;
    private int calcNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcArea = findViewById(R.id.calcArea);
        calcNumS = "0";
    }

    public void writeSeven(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("7");
            calcNumS = "7";
        } else {
            calcArea.append("7");
            calcNumS += "7";
        }
    }

    public void writeEight(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("8");
            calcNumS = "8";
        } else {
            calcArea.append("8");
            calcNumS += "8";
        }
    }

    public void writeNine(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("9");
            calcNumS = "9";
        } else {
            calcArea.append("9");
            calcNumS += "9";
        }
    }

    public void writeFour(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("4");
            calcNumS = "4";
        } else {
            calcArea.append("4");
            calcNumS += "4";
        }
    }

    public void writeFive(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("5");
            calcNumS = "5";
        } else {
            calcArea.append("5");
            calcNumS += "5";
        }
    }

    public void writeSix(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("6");
            calcNumS = "6";
        } else {
            calcArea.append("6");
            calcNumS += "6";
        }
    }

    public void writeOne(View view) {

        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("1");
            calcNumS = "1";
        } else {
            calcArea.append("1");
            calcNumS += "1";
        }
    }

    public void writeTwo(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("2");
            calcNumS = "2";
        } else {
            calcArea.append("2");
            calcNumS += "2";
        }
    }

    public void writeThree(View view) {
        if(this.calcArea.getText().toString().equals("0")) {
            calcArea.setText("3");
            calcNumS = "3";
        } else {
            calcArea.append("3");
            calcNumS += "3";
        }
    }

    public void writeZero(View view) {
        if(!this.calcArea.getText().toString().equals("0")) {
            calcArea.append("0");
            calcNumS += "0";
        }
    }

    public void deleteAll(View view) {
        calcArea.setText("0");
        calcNumS = "0";
    }

    public void deleteOne(View view) {
        if(calcNumS.length() == 1) {
            deleteAll(view);
            return;
        }

        if(calcNumS != null && calcNumS.length() > 1) {
            calcNumS = calcNumS.substring(0, calcNumS.length()-1);
            calcArea.setText(calcNumS);
        }
    }
}