package amhacks.hangoutai;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private EditText EmailET, PasswordET;
    private Button LoginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideSystemUI();
        mAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog;
        progressDialog= new ProgressDialog(this);
        EmailET = (EditText) findViewById(R.id.login_email_et);
        PasswordET = (EditText) findViewById(R.id.login_pass_et);
        LoginButton = (Button) findViewById(R.id.login_button);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = EmailET.getText().toString();
                password = PasswordET.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "Please fill all the credentials", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   progressDialog.setTitle("Please wait");
                   progressDialog.setMessage("We are authenticating your credentials");
                   progressDialog.show();
                   mAuth.signInWithEmailAndPassword(email, password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful())
                                   {
                                       Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                       mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(mainIntent);
                                   }
                                   else
                                   {
                                       String msg = task.getException().getMessage();
                                       Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });

                }
            }
        });

    }
    public void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}