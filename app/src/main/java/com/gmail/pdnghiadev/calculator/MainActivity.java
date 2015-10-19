package com.gmail.pdnghiadev.calculator;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mFormula, mResult;
    private StringBuffer outputString = new StringBuffer();
    private InfixToPostfix convert;
    private PostfixEvaluator postfixEvaluator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFormula = (TextView) findViewById(R.id.tv_fomula);
        mResult = (TextView) findViewById(R.id.tv_result);

        convert = new InfixToPostfix();
        postfixEvaluator = new PostfixEvaluator();
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.equal:
                if (mFormula.getText().toString().equals("0") || mFormula.getText().toString().length() == 0 || mFormula.getText().toString().equals("0.0")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    AlertDialog dialog = builder.create();
                    dialog.setMessage("Your expression cannot be calculated");
                    dialog.show();
                }
                outputString.delete(0, mFormula.length());
                addButton(mResult.getText().toString(), outputString);
                mResult.setText("");
                break;
            case R.id.del:
                deleteButton();
                break;
            case R.id.one:
                operationButton("1");
                break;
            case R.id.two:
                operationButton("2");
                break;
            case R.id.three:
                operationButton("3");
                break;
            case R.id.four:
                operationButton("4");
                break;
            case R.id.five:
                operationButton("5");
                break;
            case R.id.six:
                operationButton("6");
                break;
            case R.id.seven:
                operationButton("7");
                break;
            case R.id.eight:
                operationButton("8");
                break;
            case R.id.nine:
                operationButton("9");
                break;
            case R.id.zero:
                operationButton("0");
                break;
            case R.id.doc:
                addButton(".", outputString);
                break;
            case R.id.mul:
                if (mFormula.getText().toString().length() > 0){
                    duplicationButton("*");
                }
                break;
            case R.id.div:
                if (mFormula.getText().toString().length() > 0){
                    duplicationButton("/");
                }
                break;
            case R.id.add:
                if (mFormula.getText().toString().length() > 0){
                    duplicationButton("+");
                }
                break;
            case R.id.sub:
                if (mFormula.getText().toString().length() > 0){
                    duplicationButton("-");
                }
                break;
            default:
                break;
        }
    }

    // Method delete Button
    public void deleteButton(){
        if (mFormula.length() > 0){
            String temptString = mFormula.getText().toString();
            String[] token = temptString.split(" ");
            if (!token[token.length - 1].contains("+") && !token[token.length - 1].contains("-")
                    && !token[token.length - 1].contains("*") && !token[token.length - 1].contains("/")){ //Number
                outputString.deleteCharAt(outputString.length() - 1);
            }else { // Operator
                outputString.delete(outputString.length() - 3, outputString.length());
            }
            mFormula.setText(outputString);

            String converString = convert.convertToPostfix(mFormula.getText().toString());
            String[] splitString = converString.split(" ");
            if (splitString.length >= 4 && splitString.length % 2 == 0){
                postfixEvaluator.setInput(convert.convertToPostfix(mFormula.getText().toString()));
                postfixEvaluator.evaluatorPostfix();
                mResult.setText(postfixEvaluator.getOutput());
            }

            if (mFormula.length() == 1){
                mResult.setText("");
            }
        }
    }

    // Method operation result and show in TextView
    public void operationButton(String str){
        addButton(str, outputString);
        String converString = convert.convertToPostfix(mFormula.getText().toString());
        String[] splitString = converString.split(" ");

        if (splitString.length >= 4 && splitString.length % 2 == 0){
            postfixEvaluator.setInput(convert.convertToPostfix(mFormula.getText().toString()));
            postfixEvaluator.evaluatorPostfix();
            mResult.setText(postfixEvaluator.getOutput());
        }
    }

    // Method Check duplication Button
    public void duplicationButton(String str){
        String temptString = mFormula.getText().toString();
        String[] token = temptString.split(" ");

        if (!token[token.length - 1].contains("+") && !token[token.length - 1].contains("-") && !token[token.length - 1].contains("*") && !token[token.length - 1].contains("/")){ //Number
          addButton(" " + str + " ", outputString);
        }else {
            if (token[token.length - 1].equals(str)){ // Operator

            }else {
                outputString.delete(outputString.length() - 3, outputString.length());
                addButton(" " + str + " ", outputString);
            }
        }
    }

    private void addButton(String string, StringBuffer s) {
        s.append(string);
        mFormula.setText(s);
    }

    // Save SharedPreferences
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("save_result", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("formula", mFormula.getText().toString());
        editor.putString("result", mResult.getText().toString());
        editor.apply();
    }

    // Read SharedPreferences
    @Override
    protected void onResume() {
        super.onResume();
        outputString.delete(0, outputString.length());
        SharedPreferences sharedPreferences = getSharedPreferences("save_result", MODE_PRIVATE);
        outputString.append(sharedPreferences.getString("formula", ""));
        mFormula.setText(outputString);
        mResult.setText(sharedPreferences.getString("result", ""));
    }
}
