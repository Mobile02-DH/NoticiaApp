package com.example.connect.connectnews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.connect.connectnews.configuracaoFirebase.FirebaseConfiguracao;
import com.example.connect.connectnews.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnRegisterNow;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        campoEmail = findViewById(R.id.txt_email);
        campoSenha = findViewById(R.id.txt_password);
        btnRegisterNow = findViewById(R.id.btn_register_now);

        btnRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();


                if( !textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);

                        cadastrarUsuario();

                    }else{
                        Toast.makeText(RegisterActivity.this,
                                "Preencha o Password",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this,
                    "Preencha o E-mail",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

  public void cadastrarUsuario (){

        autenticacao = FirebaseConfiguracao.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Toast.makeText(RegisterActivity.this,
                            "Sucesso ao cadastrar usuario",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RegisterActivity.this,
                            "Erro ao cadastrar usuario",Toast.LENGTH_SHORT).show();
                }
            }
        });

  }
}
