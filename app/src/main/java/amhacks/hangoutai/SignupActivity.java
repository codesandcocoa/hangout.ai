package amhacks.hangoutai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText EmailET, PasswordET, ConfPassET;
    private Button SignupButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        EmailET = (EditText) findViewById(R.id.signup_email_et);
        PasswordET = (EditText) findViewById(R.id.signup_pass_et);
        ConfPassET = (EditText) findViewById(R.id.signup_conf_pass_et);
        SignupButton = (Button) findViewById(R.id.signup_button);
        ProgressDialog progressDialog = new ProgressDialog(this);

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, cpass;
                email = EmailET.getText().toString();
                password = PasswordET.getText().toString();
                cpass = ConfPassET.getText().toString();
                if (password.equals(cpass))
                {
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("We are creating your account");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Intent hIntent = new Intent(SignupActivity.this, MainActivity.class);
                                        hIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(hIntent);
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}