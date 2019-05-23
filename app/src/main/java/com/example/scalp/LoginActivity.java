package com.example.scalp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    CheckBox mRememberCheckBox;
    SharedPreferences mPreferences;
    SharedPreferences.Editor editor;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        mTextUsername = (EditText) findViewById(R.id.login_username);
        mTextPassword = (EditText) findViewById(R.id.login_password);
        mButtonLogin = (Button) findViewById(R.id.login_button);
        mTextViewRegister = (TextView) findViewById(R.id.register_textview);
        mRememberCheckBox = (CheckBox) findViewById(R.id.remember_checkbox);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //mPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        editor = mPreferences.edit();
        checkSharedPreferences();

        editor.putString(getString(R.string.shImage), "");
        editor.commit();

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true){
                    //save the checkbox preference
                    if(mRememberCheckBox.isChecked()){
                        //set a checkbox when the application starts
                        editor.putString(getString(R.string.checkbox), "True");
                        editor.commit();
                        //save the name
                        editor.putString(getString(R.string.shName), user);
                        editor.commit();
                        //save the password
                        editor.putString(getString(R.string.shPassword), pwd);
                        editor.commit();
                    }else{
                        //set a checkbox when the application starts
                        editor.putString(getString(R.string.checkbox), "False");
                        editor.commit();
                        //save the name
                        editor.putString(getString(R.string.shName), "");
                        editor.commit();
                        //save the password
                        editor.putString(getString(R.string.shPassword), "");
                        editor.commit();
                    }
                    //save the username to pass to homeactivity for the session
                    editor.putString(getString(R.string.sessionUserName), user);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_SHORT).show();
                    Intent HomePage = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(HomePage);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Check the SharedPreferences and assign them accordingly
    private void checkSharedPreferences(){
        String chkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String sName = mPreferences.getString(getString(R.string.shName), "");
        String sPassword = mPreferences.getString(getString(R.string.shPassword), "");

        mTextUsername.setText(sName);
        mTextPassword.setText(sPassword);

        if(chkbox.equals("True")){
            mRememberCheckBox.setChecked(true);
        }else{
            mRememberCheckBox.setChecked(false);
        }
    }
}
