/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        EditText customerNameText   = (EditText)findViewById(R.id.name);
        String customerName = customerNameText.getText().toString();
        //Log.v("MainActivity", cName );

        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void increment(View view) {
        if (quantity < 100){
            quantity = quantity + 1;}
        else {
            quantity = 100;
            Context context = getApplicationContext();
            CharSequence text = "One hundred order maximum please";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);

    }

    public void decrement(View view) {
        if (quantity > 1){
            quantity = quantity - 1;}
        else {
            quantity = 1;
            Context context = getApplicationContext();
            CharSequence text = "One order minimum please";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String customer) {
        String priceMessage = getString(R.string.order_summary_name, customer);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_price, price) + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;

        if (addWhippedCream){
            price = price + 1;
        }

        if (addChocolate){
            price = price + 2;
        }

        int totalPrice = 0;
        totalPrice = quantity * price;
        return totalPrice;
    }


    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */




}
