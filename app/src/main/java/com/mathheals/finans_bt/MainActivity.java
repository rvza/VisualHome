package com.mathheals.finans_bt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private FirebaseAuth firebaseauth;
    private EditText email;
    private EditText password;
    private Button  giris;
    private TextView uyeol;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        firebaseauth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        giris=(Button)findViewById(R.id.btn_login);
        uyeol=(TextView)findViewById(R.id.link_signup);
        progress=new ProgressDialog(this);
        giris.setOnClickListener(this);
        uyeol.setOnClickListener(this);

        if(firebaseauth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), profile.class));
            }
           }
        private void LoginUser(){
           String email_log=email.getText().toString().trim();
           String password_log=password.getText().toString().trim();

         if(TextUtils.isEmpty(email_log)){
             Toast.makeText(this, "sifrenizi giriniz", Toast.LENGTH_SHORT).show();
             return;
             }
         if(TextUtils.isEmpty(password_log)){
             Toast.makeText(this, "pasaportunuzu giriniz", Toast.LENGTH_SHORT).show();
             return;
             }
           progress.setMessage("Giriş Yapılıyor.");
           progress.show();

         firebaseauth.signInWithEmailAndPassword(email_log,password_log).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
                progress.dismiss();
                if(task.isSuccessful()){
                    String userID =firebaseauth.getInstance().getCurrentUser().getUid();
                    Intent intent=new Intent(getApplicationContext(),profile.class);
                    Bundle bund=new Bundle();//veriyi diğer activityye yollamak için kullanıyoruz
                    bund.putString("userıd",userID);
                    intent.putExtras(bund);
                    finish();
                    startActivity(intent);
                }
             }
         });
        }

         @Override
          public void onClick(View v) {
            if(v==giris){ LoginUser();}
            if(v==uyeol){
               finish();
               startActivity(new Intent(this,Register.class));
             }
         }
      }
