package com.example.satelprojetos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.satelprojetos.R;
import com.example.satelprojetos.config.ConfiguracaoFirebase;
import com.example.satelprojetos.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private EditText emailUsuario, senhaUsuario;
    private FirebaseAuth autentificacao;
    private Usuario usuario;
    private Button btnLogin;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.button);
        autentificacao = ConfiguracaoFirebase.getFirebaseAuth();
        if(autentificacao.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
            intent.putExtra("ID", autentificacao.getCurrentUser().getEmail());
            Log.i("TESTE", autentificacao.getCurrentUser().getEmail());
            startActivity(intent);
        }


    }
    public void abrirDrawer(View view){
        emailUsuario = findViewById(R.id.editTextTextPersonName);
        String email = emailUsuario.getText().toString();
        senhaUsuario = findViewById(R.id.editTextTextPassword);
        String senha = senhaUsuario.getText().toString();
        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);
                validarLogin();
            }else {
                Toast.makeText(MainActivity.this, "Digite uma senha", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MainActivity.this, "Digite um email", Toast.LENGTH_SHORT).show();

        }

    }

    public void validarLogin(){
        progressDialog = new ProgressDialog(MainActivity.this,R.style.LightDialogTheme);
        progressDialog.setMessage("Carregando..."); // Setting Message
        progressDialog.setTitle("Por favor Espere"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        btnLogin.setEnabled(false);
        autentificacao = ConfiguracaoFirebase.getFirebaseAuth();
        autentificacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                    intent.putExtra("ID", autentificacao.getCurrentUser().getEmail());
                    Log.i("TESTE", autentificacao.getCurrentUser().getEmail());
                    startActivity(intent);
                }else {
                    String excecao = "Erro ao fazer Login, por favor verifique sua conexão";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuário não está cadastrado.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email e senha não correspondem a um usuário cadastrado.";
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, excecao, Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                }
progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.i("ENTREI","apertei back");

            final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.LightDialogTheme);
            dialog.setTitle("Sair do aplicativo?");
            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAndRemoveTask();
                    System.exit(0);
                }
            });
            dialog.setNegativeButton("Não", null);
            dialog.create();
            dialog.show();

    }
}