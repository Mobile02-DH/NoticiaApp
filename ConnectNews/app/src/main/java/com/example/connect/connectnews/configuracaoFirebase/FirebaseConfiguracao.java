package com.example.connect.connectnews.configuracaoFirebase;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfiguracao {

    private static FirebaseAuth autenticacao;

        public static FirebaseAuth getFirebaseAutenticacao(){

            if (autenticacao == null){
                autenticacao = FirebaseAuth.getInstance();
            }
            return autenticacao;

        }

}
