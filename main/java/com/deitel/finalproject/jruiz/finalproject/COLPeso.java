//MainActivity.java
//Calculates a bill total based on a tip percentage

package com.deitel.finalproject.jruiz.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

// MainActivity class for the Tip Calculator app
public class COLPeso extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance(Locale.US); //Uses $ sign like US
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double percentSplit = .50;//PERCENT OF BILL SPLIT BETWEEN CURRENCIES
    private double billAmount = 0.0; // bill amount entered by the user
    private double percent = 0.15; // initial tip percentage
    private TextView amountTextView; // shows formatted bill amount
    private TextView percentTextView; // shows tip percentage
    private TextView convCurTextView; // shows percentage of currency label to be converted
    private TextView tipTextView; // shows calculated tip amount
    private TextView totalTextView; // shows calculated total bill amount
    private TextView conTextView; //shows percentage of currency to be converted

    SeekBar percentSeekBar; //Global Variable
    SeekBar percentSeekBar1; //Global Variable

    private EditText amountEditText;

   // MyDBHandler dbHandlerCurrency; //SQL Table Design

    // called when the activity is first created, defines facilities for app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass's version
        setContentView(R.layout.activity_colpeso); //inflate the GUI

        // get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        convCurTextView = (TextView) findViewById(R.id.convCurTextView);
        conTextView = (TextView) findViewById(R.id.conTextView);
        //tipTextView.setText(currencyFormat.format(0)); //set text to 0
        //totalTextView.setText(currencyFormat.format(0)); //set text to 0
        //conTextView.setText(currencyFormat.format(0)); //set text to 0

        // set amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

        percentSeekBar1 =
                (SeekBar) findViewById(R.id.percentSeekBar1);
        percentSeekBar1.setOnSeekBarChangeListener(seekBarListener);

        //Intent intent = new Intent(this, Pounds.class);
        //intent.putExtra("Name of Message", messagetext);
        //startActivity(intent);

       // dbHandlerCurrency = new MyDBHandler(this);
       // printDatabase();
    }

   /* //Print the database
    public void printDatabase(){
        String dbString = dbHandlerCurrency.databaseToString();
        conTextView.setText(dbString);
        amountEditText.setText("");
    }

    public void printView(){
        String dbString = dbHandlerCurrency.viewToString();
        conTextView.setText(dbString);
        amountEditText.setText(""); //user input 1 = amount edit text, user input 2 = con text view

    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        Products product =
                new Products(Double.parseDouble(amountEditText.getText().toString()),Double.parseDouble(conTextView.getText().toString()));
        dbHandlerCurrency.addProduct(product);
        printDatabase();
    }

    //Delete items
    public void deleteButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        String inputText = amountEditText.getText().toString();
        dbHandlerCurrency.deleteProduct(Double.parseDouble(inputText));
        printDatabase();
    }

    public void queryButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        //String inputText = userInput1.getText().toString();
        //dbHandler.deleteProduct(inputText);
        printView();
    }
*/

    // calculate and display tip and total amounts
    private void calculate() {
        //format percent and display in percentTextView
        percentTextView.setText("Tip% " + percentFormat.format(percent));
        convCurTextView.setText("Con. Currency " + percentFormat.format(percentSplit));

        // calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //COL PESO AMOUNT
        double ColPesoAmount = total * 2849.05 * percentSplit;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
        conTextView.setText("COL" + currencyFormat.format(ColPesoAmount)); //Uses $ sign but country code included for clarity
    }

    // listener object for the Seekbar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)  {
                    if (seekBar.getId() == R.id.percentSeekBar) {
                        /* Do something that you want when the first SeekBar is change).*/
                        Log.d("Juan Ruiz", "Progress=" + progress);
                        percent = progress / 100.0; //set percent based on progress

                    }
                    else if (seekBar.getId() == R.id.percentSeekBar1) {
                        // Do something that you want when the second SeekBar is change). For now, to debug, add        Log.d(“YourName”, 2nd SeekBar progress=”+progress);
                        Log.d("Juan Ruiz", "Progress=" + progress);
                        percentSplit = progress/100.0;

                    }
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            try { //get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString())/100;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate(); //update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    };
}
