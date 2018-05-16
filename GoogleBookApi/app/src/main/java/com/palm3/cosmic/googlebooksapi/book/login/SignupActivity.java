package com.palm3.cosmic.googlebooksapi.book.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.palm3.cosmic.googlebooksapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_re_enter_password) EditText _reEnterPasswordText;
    @BindView(R.id.btn_sign_up) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(v -> signup());

        _loginLink.setOnClickListener(v -> {
            // 新規登録完了後、ログインアクティビティへ戻る
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }

    public void signup() {
        Log.d(TAG, "サインアップ");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog =
                new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("アカウント作成中...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: 独自のサインアップロジック

        new android.os.Handler().postDelayed(
                () -> {
                    onSignupSuccess();
                    progressDialog.dismiss();
                }, 3000);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "ログイン失敗", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // バリデーション（名前）
        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("最低3文字以上ご入力下さい。");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        // バリデーション（住所）
        if (address.isEmpty()) {
            _addressText.setError("有効な住所をご入力下さい。");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        // バリデーション（メールアドレス）
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("有効なメールアドレスをご入力下さい。");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        // TODO: バリデーション見直し
        if (mobile.isEmpty() || mobile.length() != 10) {
            _mobileText.setError("有効なお電話番号をご入力下さい。");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("パスワードはアルファベット4桁から10桁のものをご使用下さい。");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10) {
            _reEnterPasswordText.setError("パスワードが一致しません。");
        } else {
            _reEnterPasswordText.setError(null);
        }
        return valid;
    }
}
