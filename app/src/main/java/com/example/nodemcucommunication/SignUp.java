package com.example.nodemcucommunication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
public class SignUp extends AppCompatActivity {
    Button mNext;
    TextInputEditText mFirstNameET;
    TextInputEditText mLastNameET;
    TextInputEditText mPasswordEt;
    TextInputEditText mEmailEt;
    String name;
    String email;
    String mLastName;
    String password;
    String mobileNumber="mob";
    public static String userpref = "USER_PREF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //methods initialize
        init();
        getIntentValue();
      //  getIntentValue();
        mNext.setOnClickListener(v ->
        {
            if (IsNotEmpty()) {
                SavedValueInSharedPref();
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                   }
        });
    }
    private void init() {
        mNext = findViewById(R.id.signup_bt_next);
        mFirstNameET = findViewById(R.id.signup_fullname_id);
        mLastNameET = findViewById(R.id.sginup_username_id);
        mEmailEt = findViewById(R.id.signuemail_id);
        mPasswordEt = findViewById(R.id.signup_password_id);

    }
    private void getIntentValue() {
        Intent intent = getIntent();
      mobileNumber = intent.getStringExtra("mobilenumber");
    }
    private Boolean IsNotEmpty() {
        name = mFirstNameET.getText().toString();
        mLastName = mLastNameET.getText().toString();
        email = mEmailEt.getText().toString();
        password = mPasswordEt.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter The Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLastName.isEmpty()) {
            Toast.makeText(this, "Enter LastName", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void SavedValueInSharedPref() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(userpref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phoneNumber", mobileNumber);
        editor.putString("fname", name);
        editor.putString("lname", mLastName);
        editor.putString("email", email);
        editor.apply();
    }
}