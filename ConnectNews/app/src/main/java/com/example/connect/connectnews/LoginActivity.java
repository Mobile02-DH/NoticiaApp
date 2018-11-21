package com.example.connect.connectnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    TextView txtEmail;
    ProgressDialog mDialog;
    ImageView imgAvatar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button botaoLogin = findViewById(R.id.button_login);
        TextView txtRegister = findViewById(R.id.textview_register_now);

        initViews();


        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null){
                    getUserProfile();
                }
            }
        });




        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        methodbuttonfb();

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

         /*       String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
//                        getData(object);

                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email, birthday, friends");
                request.setParameters(parameters);
                request.executeAsync();*/

                getUserProfile();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (AccessToken.getCurrentAccessToken() != null){
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


}
