package com.example.connect.connectnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.connect.connectnews.configuracaoFirebase.FirebaseConfiguracao;
import com.example.connect.connectnews.helper.Base64Custom;
import com.example.connect.connectnews.helper.Preferencias;
import com.example.connect.connectnews.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnRegisterNow;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        campoEmail = findViewById(R.id.txt_registrar_email);
        campoSenha = findViewById(R.id.txt_registrar_senha);
        btnRegisterNow = findViewById(R.id.btn_register_now);

        btnRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(TextUtils.isEmpty(textoEmail)){
                    Toast.makeText(getApplicationContext(),"Please fil in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textoSenha)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(textoSenha.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                    return;
                }


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
                if (task.isSuccessful()) {

                    Toast.makeText(RegisterActivity.this,
                            "Sucesso ao cadastrar usuario", Toast.LENGTH_SHORT).show();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId((identificadorUsuario));
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(RegisterActivity.this);
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuario.getNome());

                    abrirLoginUsuario();

                }else{
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um email valido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao ="Esta conta ja foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuario: " +e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(RegisterActivity.this,
                            excecao,Toast.LENGTH_SHORT).show();
                }
            }
        });

  }

  public void abrirLoginUsuario() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
  }
}
