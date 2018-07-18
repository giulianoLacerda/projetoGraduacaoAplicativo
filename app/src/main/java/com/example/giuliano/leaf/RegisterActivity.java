package com.example.giuliano.leaf;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.giuliano.leaf.api.Communication;
import static com.example.giuliano.leaf.api.Communication.retrofit;

import com.example.giuliano.leaf.controller.MainActivity;
import com.example.giuliano.leaf.model.Register;
import com.example.giuliano.leaf.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {


    // Ui References
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mRegisterView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstNameView = (EditText) findViewById(R.id.firstName);
        mLastNameView = (EditText) findViewById(R.id.lastName);
        mEmailView = (EditText) findViewById(R.id.email);
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRegisterView = findViewById(R.id.viewRegister);
        mProgressView = findViewById(R.id.progress);
        Button mSignUpButton = (Button) findViewById(R.id.registerButton);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent){
                if(id == 666 || id == EditorInfo.IME_NULL){
                    // Try Login
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void requestRegister(String firstName, String lastName, String email,
                                 String username, String password){

        Register register = new Register(firstName,lastName,email,username,password);
        Communication apiService = retrofit.create(Communication.class);

        Call<User> call = apiService.signup(register);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();

                // Reponse sucess
                if(statusCode==200){

                    User user = response.body();
                    Intent it = new Intent(RegisterActivity.this,MainActivity.class);
                    it.putExtra("USER",user);

                    // Verify that the intent will resolve to an activity
                    if (it.resolveActivity(getPackageManager()) != null) {
                        startActivity(it);
                    }
                }
                // Username or password aew incorrect
                if(statusCode==400){
                    Toast.makeText(RegisterActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                    showProgress(false);
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Response","Falha");
                t.printStackTrace();

            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


    private void attemptRegister(){

        boolean cancel = false;
        View focusView = null;

        // Reset errors.
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mEmailView.setError(null);
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(TextUtils.isEmpty(lastName)){
            mLastNameView.setError(getString(R.string.FieldRequired));
            cancel = true;
        }

        if(TextUtils.isEmpty(firstName)){
            mFirstNameView.setError(getString(R.string.FieldRequired));
            focusView = mFirstNameView;
            cancel = true;
        }

        // Check for a valid password.
        if(TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.FieldRequired));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if(TextUtils.isEmpty(username)){
            mUsernameView.setError(getString(R.string.FieldRequired));
            focusView = mUsernameView;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            mEmailView.setError(getString(R.string.FieldRequired));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("This email address is invalid");
            focusView = mEmailView;
            cancel = true;
        }


        if(cancel){
            //There was an error. Don't attempt login and focus the first
            // form field with an error.
            assert focusView != null;
            focusView.requestFocus();
        } else {
            // Show a progress spinner.
            showProgress(true);
            requestRegister(firstName,lastName,email,username,password);
        }

    }

}
