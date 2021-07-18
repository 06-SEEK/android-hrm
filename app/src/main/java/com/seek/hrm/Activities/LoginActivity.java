package com.seek.hrm.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Pattern;

import com.seek.hrm.R;
import com.seek.hrm.Session.LoginSession;
import com.seek.hrm.databinding.ActivityLoginBinding;
import com.seek.hrm.Parsing.LoginResponse;
import com.seek.hrm.Network.RetrofitClient;
import com.seek.hrm.Validation.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String inputEmail;
    private String inputPassword;
    private static final String regex = "^(.+)@(.+)$";
    private static final Pattern pattern = Pattern.compile(regex);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        binding.buttonRegister.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });

        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isValidEmail = AuthenticateValidator.isValidEmail(charSequence.toString().trim());
                if (isValidEmail == true) {

                } else {
                    binding.editTextEmail.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonLogin.setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            boolean isValidEmail = pattern.matcher(binding.editTextEmail.getText().toString()).matches();

            if (isValidEmail) {
                inputEmail = binding.editTextEmail.getText().toString();
                inputPassword = binding.editTextPassword.getText().toString();

                final LoginResponse login = new LoginResponse(inputEmail, inputPassword);
                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getAppService()
                        .login(login);
                call.enqueue(new Callback<LoginResponse>() {
                                 @Override
                                 public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                     if (response.isSuccessful()) {
                                         Log.d("status", String.valueOf(response.code()));
                                         Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                                         RetrofitClient.getInstance().setAuthorizationHeader(response.body().getBearerToken());
                                         LoginSession.setInstance(response.body());
                                         startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                         finish();
                                     } else {
//                                         Log.d("status", response.body().getMessage().toString());
                                         Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<LoginResponse> call, Throwable t) {
                                     Toast.makeText(LoginActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                 }
                             }
                );

//                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            } else {
                binding.editTextEmail.setError("Invalid email");
            }
        });
    }
}