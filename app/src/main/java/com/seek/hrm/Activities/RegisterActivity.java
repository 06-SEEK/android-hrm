package com.seek.hrm.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import com.seek.hrm.R;
import com.seek.hrm.databinding.ActivityRegisterBinding;
import com.seek.hrm.Parsing.RegisterResponse;
import com.seek.hrm.Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static String USER_TOKEN = "USER_TOKEN";
    private ActivityRegisterBinding binding;
    private String inputEmail;
    private String inputPassword;
    private String inputName;
    private String inputConfirmPassword;
    private static final String regex = "^(.+)@(.+)$";
    private static final Pattern pattern = Pattern.compile(regex);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isValidEmail = pattern.matcher(charSequence).matches();
                if (isValidEmail == true) {

                } else {
                    binding.editTextEmail.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.buttonRegister.setOnClickListener(view -> {
            boolean isValidEmail = pattern.matcher(binding.editTextEmail.getText().toString()).matches();
            boolean isPasswordMatch =binding.editTextPassword.getText().toString().equals(binding.editTextConfirm.getText().toString());
            boolean isPassword =binding.editTextPassword.getText().toString().length() > 5;
            boolean isValid = true;

            if(!isValidEmail){
                binding.editTextEmail.setError("Invalid email");
                isValid=false;
            }
            if (!isPassword) {
                binding.editTextPassword.setError("password must contain at least 6 character");
                isValid = false;
            }else if(!isPasswordMatch){
                binding.editTextConfirm.setError("Not match");
                isValid=false;
            }

            if(isValid){
                inputEmail = binding.editTextEmail.getText().toString();
                inputPassword = binding.editTextPassword.getText().toString();
                inputConfirmPassword = binding.editTextConfirm.getText().toString();
//            Toast.makeText(getApplicationContext(), inputEmail + " " + inputPassword + " " + inputName,
//                    Toast.LENGTH_LONG).show();
                final RegisterResponse register = new RegisterResponse(inputEmail,inputPassword);
                Call<RegisterResponse> call = RetrofitClient
                        .getInstance()
                        .getAppService()
                        .createUser(register);
                call.enqueue(new Callback<RegisterResponse>() {
                                 @Override
                                 public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                     Log.d("Body", "Body");
                                     if (response.isSuccessful()) {
                                         Toast.makeText(getApplicationContext(), "Register successfully", Toast.LENGTH_LONG).show();
                                         try {
                                             JSONObject responseObject = new JSONObject(response.body().getData().toString());
                                             String token = (String) responseObject.getString("token");
                                             //Save token to local storage
                                             SharedPreferences.Editor editor = getSharedPreferences(USER_TOKEN, MODE_PRIVATE).edit();
                                             editor.putString("token", token);
                                             editor.apply();
                                             //Read token from local storage
                                             SharedPreferences prefs = getSharedPreferences(USER_TOKEN, MODE_PRIVATE);
                                             String retrievedToken = prefs.getString("token", "none");
                                             Log.d("TOKEN", retrievedToken);
                                             Toast.makeText(getApplicationContext(), "Successfully",Toast.LENGTH_LONG).show();
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }

                                     } else {
                                         Toast.makeText(RegisterActivity.this, "Email is already taken", Toast.LENGTH_SHORT).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<RegisterResponse> call, Throwable t) {
                                     Toast.makeText(RegisterActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                                 }
                             }
                );
            }

        });
        binding.buttonLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}