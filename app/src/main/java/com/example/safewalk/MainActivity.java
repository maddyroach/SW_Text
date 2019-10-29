package com.example.safewalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    protected Button myButton;
    protected Button moveToMessage;

    ArrayList<String> phoneNumber = new ArrayList<String>();
    EditText number;
    ListView show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   // initiation steps performed elsewhere
        setContentView(R.layout.activity_main);  // which layout to render (links to a page .xml file)
        BottomNavigationView navView = findViewById(R.id.nav_view);  // linking an action to a button
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //Initialize Button to change activities
        moveToMessage = (Button) findViewById(R.id.moveToMessage);

        //Initialize Button, EditText, and ListView
        myButton = (Button) findViewById(R.id.addContactButton);
        number = (EditText) findViewById(R.id.phoneNumberCollection);
        show = (ListView) findViewById(R.id.ListView);

        //Adding functionality to Button Press!
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = number.getText().toString();

                if (phoneNumber.contains(getInput)) {
                    Toast.makeText(getBaseContext(), "Phone Number Already Added to Send List", Toast.LENGTH_LONG).show();
                } else if (getInput == null || getInput.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Input Field Is Empty", Toast.LENGTH_LONG).show();
                } else {
                    phoneNumber.add(getInput);
                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, phoneNumber);
                    //show.getAdapter(adapter);
                    ((EditText) findViewById(R.id.phoneNumberCollection)).setText("");
                    Toast.makeText(getBaseContext(), "Phone Number Added to Send List", Toast.LENGTH_LONG).show();
                }

            }

        });

        moveToMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), EnterEmergencyMessages.class);
                intent.putStringArrayListExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });
    }
}
