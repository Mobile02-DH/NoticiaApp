package com.example.connect.connectnews.configuracaoFirebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConfiguracao {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference referenciaFirebase;

        public static FirebaseAuth getFirebaseAutenticacao(){

            if (autenticacao == null){
                autenticacao = FirebaseAuth.getInstance();
            }
            return autenticacao;

        }

    public static DatabaseReference getFirebase(){

        if (referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;

    }


}
