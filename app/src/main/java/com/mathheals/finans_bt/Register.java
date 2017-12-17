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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * Created by user on 14.10.2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener{
    private EditText email;
    private EditText sifre;
    private EditText isim,doktor,hastane;
    private Button kayit_ol;
    private TextView kayit;
    private ProgressDialog progress;
    private ImageView resim;
    final String idm=UUID.randomUUID().toString();
    private static final int PICK_IMAGE_REQUEST = 71;

    DatabaseReference dataref;
    FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        progress=new ProgressDialog(this);
        email=(EditText)findViewById(R.id.input_email);
        sifre=(EditText)findViewById(R.id.input_password);
        isim=(EditText)findViewById(R.id.input_name);
        kayit_ol=(Button)findViewById(R.id.btn_signup);
        kayit=(TextView)findViewById(R.id.link_login);

        firebaseauth=FirebaseAuth.getInstance();
        dataref= FirebaseDatabase.getInstance().getReference();
        if(firebaseauth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),profile.class));
        }
        kayit_ol.setOnClickListener(this);
        kayit.setOnClickListener(this);
        resim=(ImageView)findViewById(R.id.image);
    }

    private void Register_ol() {
        String isim_reg = isim.getText().toString().trim();
        String email_reg = email.getText().toString().trim();
        String password_reg = sifre.getText().toString().trim();

        if (TextUtils.isEmpty(email_reg)) {
            Toast.makeText(this, "sifrenizi giriniz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password_reg)) {
            Toast.makeText(this, "pasaportunuzu giriniz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(isim_reg)) {
            Toast.makeText(this, "isiminizi giriniz", Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Kayıt olunuyor..");
        progress.show();
        firebaseauth.createUserWithEmailAndPassword(email_reg,password_reg).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    saveuser();
                    Toast.makeText(Register.this, "registered success", Toast.LENGTH_SHORT).show();
                    String userID =firebaseauth.getInstance().getCurrentUser().getUid();
                    Intent intent=new Intent(getApplicationContext(),profile.class);
                    Bundle bund=new Bundle();//veriyi diğer activityye yollamak için kullanıyoruz
                    bund.putString("userıd",userID);
                    intent.putExtras(bund);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Register.this, "couldnt registered", Toast.LENGTH_SHORT).show();
                }
               }
        });
    }

    private void saveuser(){
        String isim_reg = isim.getText().toString().trim();
        String email_reg = email.getText().toString().trim();
        String password_reg = sifre.getText().toString().trim();

        String id=dataref.child("User").push().getKey();
        User user=new User(id,isim_reg,email_reg,password_reg);
        FirebaseUser user_fire=firebaseauth.getCurrentUser();
        dataref.child("User").child(id).setValue(user);
        Toast.makeText(this, "kaydedildi", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onClick(View v) {
        if (v == kayit_ol) {
            Register_ol();
        }
        if (v == kayit) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }}


