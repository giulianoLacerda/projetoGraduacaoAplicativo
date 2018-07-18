package com.example.giuliano.leaf.controller;

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

import com.example.giuliano.leaf.R;
import com.example.giuliano.leaf.RegisterActivity;
import com.example.giuliano.leaf.api.Communication;
import com.example.giuliano.leaf.model.Authentication;
import com.example.giuliano.leaf.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.giuliano.leaf.api.Communication.retrofit;

public class LoginActivity extends AppCompatActivity {

    // Ui References
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mSignInButton = (Button) findViewById(R.id.signin);
        Button mSignUpButton = (Button) findViewById(R.id.signUp);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent){
                if(id == 666 || id == EditorInfo.IME_NULL){
                    // Try Login
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(it);
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void requestLogin(String email, String senha){

        Authentication authentication = new Authentication(email,senha);
        Communication apiService = retrofit.create(Communication.class);

        Call<User> call = apiService.signin(authentication);
        //Log.d("Debug","Call.enqueue");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();

                // Reponse sucess
                if(statusCode==200){

                    User user = response.body();
                    user.setCookie(response.headers().get("set-cookie"));
                    //Log.d("Cookie",user.getCookie());
                    Intent it = new Intent(LoginActivity.this,MainActivity.class);
                    it.putExtra("USER",user);

                    // Verify that the intent will resolve to an activity
                    if (it.resolveActivity(getPackageManager()) != null) {
                        startActivity(it);
                        finish();
                    }
                }
                // Username or password aew incorrect
                if(statusCode==400){
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
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

    private void attemptLogin(){

        boolean cancel = false;
        View focusView = null;

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

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

        if(cancel){
            //There was an error. Don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner.
            showProgress(true);
            requestLogin(username,password);
        }

    }
}
