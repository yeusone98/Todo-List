package tdtu.android.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextMailSignUp, editTextPasswordSignUp, editTextNameSignUp;
    private ImageView image_btn_signup;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextMailSignUp = findViewById(R.id.editTextEmailSignUp);
        editTextPasswordSignUp = findViewById(R.id.editTextPasswordSignUp);
        image_btn_signup = findViewById(R.id.image_btn_signup);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);


        image_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = editTextMailSignUp.getText().toString().trim();
                String password = editTextPasswordSignUp.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    editTextMailSignUp.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    editTextPasswordSignUp.setError("Password is required");
                    return;
                }
                else{
                    loader.setMessage("Registration in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else{
                                String error = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Registration Failed" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }


            }
        });
    }
}