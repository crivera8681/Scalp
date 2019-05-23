package com.example.scalp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;

    EditText mTextUsername;
    EditText mTextPassword;
    EditText getTextConfirmPassword;
    EditText mName;
    EditText mEmail;
    EditText mPhoneNumber;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        mTextUsername = (EditText) findViewById(R.id.login_username);
        mTextPassword = (EditText) findViewById(R.id.login_password);
        getTextConfirmPassword = (EditText) findViewById(R.id.login_confirm_password);
        mButtonRegister = (Button) findViewById(R.id.register_button);
        mTextViewLogin = (TextView) findViewById(R.id.login_textview);
        mName = (EditText) findViewById(R.id.login_name);
        mEmail = (EditText) findViewById(R.id.login_email);
        mPhoneNumber = (EditText) findViewById(R.id.login_phonenumber);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String confirm_pwd = getTextConfirmPassword.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String phone = mPhoneNumber.getText().toString().trim();

                if(pwd.equals(confirm_pwd)){
                    long val = db.addUser(user, pwd, name, email, phone);
                    if(val > 0){
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registration Error.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
