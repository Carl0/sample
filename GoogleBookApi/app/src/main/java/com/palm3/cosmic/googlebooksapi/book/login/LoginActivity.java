package com.palm3.cosmic.googlebooksapi.book.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.palm3.cosmic.googlebooksapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_sign_up) TextView _signupLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // イベントリスナー設定（ログインボタン）
        _loginButton.setOnClickListener(v -> login());

        _signupLink.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog =
                new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("認証中...");
        progressDialog.show();


        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        new android.os.Handler().postDelayed(
                () -> {
                    // ログイン成功時
                    onLoginSuccess();
                    // ログイン失敗時
                    progressDialog.dismiss();
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "ログイン失敗", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("メールアドレスに誤りがあるか不正な形式です。");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("パスワードはアルファベット4桁から10桁のものをご使用下さい。");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
