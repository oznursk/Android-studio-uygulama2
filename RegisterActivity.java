package com.example.myfinall;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btn_reg;
    EditText name, email, passw;
    TextView sign_in;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mauth = FirebaseAuth.getInstance();
        btn_reg = findViewById(R.id.btn_reg);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        passw = findViewById(R.id.passw);
        sign_in = findViewById(R.id.sign_in);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameT = name.getText().toString().trim();
                String emailT = email.getText().toString().trim();
                String passwT = passw.getText().toString().trim();

                if (nameT.isEmpty()) {
                    name.setError("isim kısmını boş bırakmayın");
                    return;
                }

                if (emailT.isEmpty()) {
                    email.setError("email kısmını boş bırakmayın");
                    return;
                }

                if (passwT.isEmpty()) {
                    passw.setError("şifre kısmını boş bırakmayın");
                    return;
                }
                mauth.createUserWithEmailAndPassword(emailT, passwT)
                        .addOnCompleteListener(RegisterActivity.this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Kullanıcı kaydı başarılı", Toast.LENGTH_SHORT).show();


                                String userName = name.getText().toString().trim();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", userName);
                                db.collection("users").document(mauth.getUid())
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "BAŞARILI BİR ŞEKİLDE OLUŞTURULDU!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "oluşturulurken hata alındı");
                                            }
                                        });
                            } else {
                                Exception e = task.getException();
                                if (e != null) {
                                    Toast.makeText(RegisterActivity.this, "Üyelik oluşturulurken bir hata alındı:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
