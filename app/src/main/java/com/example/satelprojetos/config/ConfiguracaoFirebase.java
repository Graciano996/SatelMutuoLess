package com.example.satelprojetos.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autentificacao;
    private static StorageReference storage;

    public static FirebaseAuth getFirebaseAuth(){
        if(autentificacao == null){
            autentificacao = FirebaseAuth.getInstance();
        }
        return autentificacao;
    }

    public static StorageReference getStorageReference(){
        if(storage == null){
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }
}
