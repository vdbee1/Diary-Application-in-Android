package com.s4plabs.logbook.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.s4plabs.logbook.MainActivity;
import com.s4plabs.logbook.R;


public class SignupActivity extends Activity {
    private static final String TAG = "SignupActivity";
    private PrefManager prefManager;
    SharedPreferences sharedPref;

    EditText _nameText;

    EditText _passwordText;
    Button _signupButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        sharedPref = this.getSharedPreferences("Joker",Context.MODE_PRIVATE);
        if (!prefManager.isFirstTimeLaunch()) {
            launchLoginScreen();
            finish();
        }
        setContentView(R.layout.activity_signup);
        _nameText = (EditText) findViewById(R.id.input_name);
        _passwordText = (EditText) findViewById(R.id.input_password);

        _signupButton = (Button) findViewById(R.id.btn_signup);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();

        String password = _passwordText.getText().toString();


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Name",name);
        editor.putString("Password",password);
        editor.commit();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void launchLoginScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        prefManager.setFirstTimeLaunch(false);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();

        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }



        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            _passwordText.setError("between 4 and 12 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
