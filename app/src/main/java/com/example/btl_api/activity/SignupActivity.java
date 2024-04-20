package com.example.btl_api.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.btl_api.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth mAuth;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        setListeners();
    }

    private void setListeners() {
        binding.loginLabel.setOnClickListener(v -> {
            Intent it = new Intent(this, SignInActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
            finish();

        });

        binding.signupBtn.setOnClickListener(v -> {
            if(!isValidInfor()) return;
            binding.loadingPanel.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(binding.emailInput.getText().toString(), binding.passwordInput.getText().toString())
                    .addOnSuccessListener(suc -> {
                        startActivity(
                                new Intent(this, SignInActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                        finish();
                    })
                    .addOnFailureListener(f -> {
                        Toast.makeText(this, f.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnCompleteListener(res -> {
                        binding.loadingPanel.setVisibility(View.GONE);
                    });
        });
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidInfor() {
        if (binding.emailInput.getText().toString().trim().isEmpty()) {
            makeToast("User name must be filled");
            binding.emailInput.requestFocus();
            return false;
        } else if (binding.passwordInput.getText().toString().trim().isEmpty()) {
            makeToast("Password must be filled");
            binding.passwordInput.requestFocus();
            return false;
        } else if (binding.confirmPasswordInput.getText().toString().trim().isEmpty()) {
            makeToast("Confirm password must be filled");
            binding.confirmPasswordInput.requestFocus();
            return false;
        } else if (!binding.passwordInput.getText().toString().equals(binding.confirmPasswordInput.getText().toString())) {
            makeToast("Password not match !");
            binding.confirmPasswordInput.requestFocus();
            return false;
        }
        else if(!validate(binding.emailInput.getText().toString())) {
            makeToast("Email address is invalid");
            binding.emailInput.requestFocus();
            return false;
        }
//        else if (signedPreference.getBoolean(binding.usernameInput.getText().toString())) {
//            makeToast("Username already existed!");
//            binding.usernameInput.requestFocus();
//            return false;
//        }
        return true;
    }
    private boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}