//Juan Ruiz
//MainActivity.java
//Calculates a bill total based on a tip percentage
//Converts that total from USD into Euros
//Locale utility information: https://docs.oracle.com/javase/7/docs/api/java/util/Locale.html

package com.deitel.finalproject.jruiz.finalproject;

import android.os.Bundle; // for saving state information
import android.support.v7.app.AppCompatActivity; // base class //can switch order with one below
import android.text.Editable;  // for EditText event handling
import android.text.TextWatcher; //EditText Listener
import android.util.Log;
import android.view.View;
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.TextView; // for displaying text
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.text.NumberFormat; // for currency formatting

// MainActivity class for the Currency Converter app, utilizes Euros for default currency
public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();
    private static final NumberFormat convertedFormat =
            NumberFormat.getCurrencyInstance(java.util.Locale.GERMANY); //Germany uses the Euro so it will be fitting

    private double percentSplit = .50;//PERCENT OF BILL SPLIT BETWEEN CURRENCIES
    private double billAmount = 0.0; // bill amount entered by the user
    private double percent = 0.15; // initial tip percentage
    private TextView amountTextView; // shows formatted bill amount
    private TextView percentTextView; // shows tip percentage
    private TextView convCurTextView; // shows percentage of currency label to be converted
    private TextView tipTextView; // shows calculated tip amount
    private TextView totalTextView; // shows calculated total bill amount
    private TextView conTextView; //shows percentage of currency to be converted

    private EditText amountEditText; //currency inputted

    SeekBar percentSeekBar; //Global Variable
    SeekBar percentSeekBar1; //Global Variable

    CheckBox poundBox; //Global Variable
    CheckBox yuanBox; //Global Variable
    CheckBox pesoBox; //Global Variable

    Button poundButton; //Global Variable
    Button yuanButton;  //Global Variable
    Button pesoButton;  //Global Variable

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass's version
        setContentView(R.layout.activity_main); //inflate the GUI

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

        //set CheckBox Listener
        poundBox = (CheckBox) findViewById(R.id.poundBox);
        yuanBox = (CheckBox) findViewById(R.id.yuanBox);
        pesoBox = (CheckBox) findViewById(R.id.pesoBox);

        //set Button Listener
        poundButton = (Button) findViewById(R.id.poundButton);
        yuanButton = (Button) findViewById(R.id.yuanButton);
        pesoButton = (Button) findViewById(R.id.pesoButton);

        //Intent intent = new Intent(this, Pounds.class);
        //intent.putExtra("Name of Message", messagetext);
        //startActivity(intent);

        //listener function for checkboxes
    /*public void poundButtonCheck(View v) {

        if (poundBox.isChecked()) {

            openPounds();
        } else {
            //Stays on activity_main
            poundBox.setChecked(false);
        }
    }

    public void yuanButtonCheck(View v) {

        if (yuanBox.isChecked()) {

            openYuan();
        } else {
            //stays on activity_main
            yuanBox.setChecked(false);

        }
    }

    public void pesoButtonCheck(View v) {

        if (pesoBox.isChecked()) {

            openColPeso();
        }
        else {
            //stays on activity_main
            pesoBox.setChecked(false);
        }
    }*/

        poundBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(poundBox.isChecked()){

                    openPounds();
                }

                else {
                    pesoBox.setChecked(false);
                }
            }
        });

        yuanBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (yuanBox.isChecked()) {

                    openYuan();
                }
                else {
                    yuanBox.setChecked(false);
                }
            }
        });

        pesoBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (pesoBox.isChecked()) {

                    openColPeso();
                }
                else {
                    pesoBox.setChecked(false);
                }
            }
        });

        //listener object for buttons
        poundButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*if (((CheckBox) view).isChecked()) {
                    Toast.makeText(MainActivity.this,
                            "Switching View To New Currency", Toast.LENGTH_LONG).show();*/
                openPounds();
            }
        });

        yuanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openYuan();
            }
        });

        pesoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openColPeso();
            }
        });
    }

   //DBHandler here
    public void openPounds(){
        Intent pound_intent = new Intent(this, Pounds.class);
        startActivity(pound_intent);
        Toast.makeText(MainActivity.this, "Switching View To New Currency (Pounds)", Toast.LENGTH_SHORT).show();

    }

    public void openYuan(){
        Intent yuan_intent = new Intent(this, Yuan.class);
        startActivity(yuan_intent);
        Toast.makeText(MainActivity.this, "Switching View To New Currency(Yuan)", Toast.LENGTH_SHORT).show();

    }

    public void openColPeso(){
        Intent peso_intent = new Intent(this, COLPeso.class);
        startActivity(peso_intent);
        Toast.makeText(MainActivity.this, "Switching View To New Currency(Pesos)", Toast.LENGTH_SHORT).show();
    }

    // calculate and display tip and total amounts
    private void calculate() {
        //format percent and display in percentTextView
        percentTextView.setText("Tip% " + percentFormat.format(percent));
        convCurTextView.setText("Con. Currency " + percentFormat.format(percentSplit));

        // calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        //EURO AMOUNT
        double euroAmount = total * 0.84 * percentSplit;

        //display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
        conTextView.setText(convertedFormat.format(euroAmount)); //"€"
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/
      //  Intent sync_intent = new Intent(this, Pounds.class);
       // sync_intent.putExtra("Test Message To Send", billAmount);
       // startActivity(sync_intent);
        return super.onOptionsItemSelected(item);

    }

    //Intent intent = new Intent(this, Pounds.class);
    //intent.putExtra("Name of Message", messagetext);
    //startActivity(intent);

    //hostActivity.yourVariable;


    // listener object for the Seekbar's progress changed events
    private final SeekBar.OnSeekBarChangeListener seekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
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
