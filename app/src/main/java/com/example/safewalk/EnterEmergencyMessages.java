package com.example.safewalk;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class EnterEmergencyMessages extends AppCompatActivity {
    protected Button myButtonTwo;
    protected Button sendMessageLow;

    //Message Variables
    String messageMed, messageHard;

    EditText messageMedInput;
    EditText messageHardInput;
    int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReciever;
    ArrayList<String> phoneNumberTwo = new ArrayList<String>();

   // @Override this fucking thing man
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_emergency_messages);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phoneNumberTwo = getIntent().getStringArrayListExtra("phoneNumber");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Initializing Buttons and Message IDs!

        sendMessageLow = (Button)findViewById(R.id.sendMessageLow);

        myButtonTwo = (Button)findViewById(R.id.saveMessages);
        messageMedInput = (EditText) findViewById(R.id.messageMediumest);
        messageHardInput = (EditText)findViewById(R.id.messageHighest);

        //Initializing Pending Intents for Sending Text
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);



        myButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 messageMed = messageMedInput.getText().toString();
                 messageHard = messageHardInput.getText().toString();

                ((EditText) findViewById(R.id.messageMediumest)).setText("");
                ((EditText) findViewById(R.id.messageHighest)).setText("");

                Toast.makeText(getBaseContext(), "Messages Saved", Toast.LENGTH_LONG).show();

            }
        });

        //Checking permission for SMS

        sendMessageLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sending SMS Button will need phone number from other class (will this work?)

                    //MyMessage();
                    //make a for loop to iterate through the list of phone numbers
                    //String phoneNumb = "17057611035";
                    //Changed this

                    for (int i =0; i< phoneNumberTwo.size(); i++) {
                        Toast.makeText(getBaseContext(), phoneNumberTwo.get(i), Toast.LENGTH_LONG).show();


                        if (ContextCompat.checkSelfPermission(EnterEmergencyMessages.this, Manifest.permission.SEND_SMS) !=
                                PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EnterEmergencyMessages.this, new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        }
                        if (ContextCompat.checkSelfPermission(EnterEmergencyMessages.this, Manifest.permission.SEND_SMS) ==
                                PackageManager.PERMISSION_GRANTED) {
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phoneNumberTwo.get(i), null, messageMed, sentPI, deliveredPI);
                        }
                    }//changed this

            }

        });
        }

        @Override
        protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent!", Toast.LENGTH_LONG).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic Failure!", Toast.LENGTH_LONG).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No Service!", Toast.LENGTH_LONG).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU!", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio OFF!", Toast.LENGTH_LONG).show();
                        break;

                }

            }
        };
        smsDeliveredReciever = new BroadcastReceiver() {
            
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered!", Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReciever, new IntentFilter(DELIVERED));


        }

    }

