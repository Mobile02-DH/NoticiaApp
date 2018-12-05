package com.example.connect.connectnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.connectnews.configuracaoFirebase.FirebaseConfiguracao;
import com.example.connect.connectnews.model.Usuario;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText campoEmail, campoSenha;
    private Button botaoLogin;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private GoogleApiClient googleApiClient;
    private SignInButton botaoGoogle;

    public static final int SIGN_IN_CODE = 777;

    CallbackManager callbackManager;
    TextView txtEmail;
    ProgressDialog mDialog;
    ImageView imgAvatar;
    EditText edt_email;
    EditText edt_senha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

       googleApiClient = new GoogleApiClient.Builder(this)
               .enableAutoManage(this,this)
               .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
               .build();


        botaoLogin = findViewById(R.id.button_login);
        TextView txtRegister = findViewById(R.id.textview_register_now);
        campoEmail = findViewById(R.id.edt_email);
        campoSenha = findViewById(R.id.edt_password);
        botaoGoogle = findViewById(R.id.btn_login_google);
        botaoGoogle.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);

            }
        });



        initViews();

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    getUserProfile();

                } else if (!edt_email.getText().toString().equals("")&& !edt_senha.getText().toString().equals("")){
                    usuario = new Usuario();
                    usuario.setEmail(edt_email.getText().toString());
                    usuario.setSenha(edt_senha.getText().toString());

                    validarLogin();

                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();


                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha o Password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o E-mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        methodbuttonfb();

    }

    public void validarLogin() {
        autenticacao = FirebaseConfiguracao.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirHome();


                    Toast.makeText(LoginActivity.this,
                            "sucesso ao fazer login", Toast.LENGTH_SHORT).show();

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuario nao esta cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "email e senha não corresponde a um usuario cadastrado";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuario: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            excecao, Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    public void abrirHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();

    }


    private void methodbuttonfb() {

        LoginButton loginButtonfb = findViewById(R.id.login_button_fb);

        loginButtonfb.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButtonfb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();


                getUserProfile();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            //txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

    }

    private void getUserProfile() {
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                // Agora que temos o token do usuario podemos requisitar os dados
                Profile.fetchProfileForCurrentAccessToken();
                if (currentProfile != null) {
                    String fbUserId = currentProfile.getId();
                    String profileUrl = currentProfile.getProfilePictureUri(200, 200).toString();
                    Log.d("FB profile", "got new/updated profile from thread " + fbUserId);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("nome", currentProfile.getName());
                    intent.putExtra("image", profileUrl);

                    startActivity(intent);
                }
            }
        };
        profileTracker.startTracking();
    }


    private void initViews() {

        callbackManager = CallbackManager.Factory.create();

        txtEmail = findViewById(R.id.txtEmail);

        imgAvatar = findViewById(R.id.avatar);

        edt_email = findViewById(R.id.edt_email);

        edt_senha = findViewById(R.id.edt_password);


    }

    private void getData(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");

            Picasso.get().load(profile_picture.toString()).into(imgAvatar);

            txtEmail.setText(object.getString("email"));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);

        if( requestCode == 777){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();

        }else{
            Toast.makeText(this,"não iniciado",Toast.LENGTH_SHORT).show();

        }

    }

    private void goMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
