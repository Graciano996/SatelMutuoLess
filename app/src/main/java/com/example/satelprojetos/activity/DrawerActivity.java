package com.example.satelprojetos.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satelprojetos.R;
import com.example.satelprojetos.config.ConfiguracaoFirebase;
import com.example.satelprojetos.helper.FormularioDAO;
import com.example.satelprojetos.ui.cadastro.CadastroFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.List;

import static java.lang.Integer.valueOf;

public class DrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int REQUEST_CODE = 1;
    private CadastroFragment cadastroFragment;
    private TextView navEmail;
    private static final String SESSION_ID = "sessionId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        verificarPermissaoLocaliza();
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cadastrados, R.id.nav_cadastro, R.id.nav_suporte,R.id.nav_mapa,R.id.nav_enviado)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        String sessionId = getIntent().getStringExtra("ID");
        SharedPreferences preferences = getSharedPreferences(SESSION_ID,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Id",sessionId);
        editor.apply();


        Log.i("sessionId", sessionId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        navEmail = findViewById(R.id.lblNav_headerEmail);
        navEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClick(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            v.clearFocus();
            Log.i("FOCUS", v.toString());
        }catch (Exception e){
            Log.i("INFO DB", "Erro" + e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Log.i("ENTREI","apertei back");

        String conteudoDrawer = navigationView.getCheckedItem().toString();
        Log.i("nome",conteudoDrawer);
        if (conteudoDrawer.equals("Cadastrados")) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.LightDialogTheme);
            dialog.setTitle("Sair da conta?");
            dialog.setMessage("Ao pressionar sim voltará a tela de login");
            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ConfiguracaoFirebase.getFirebaseAuth().signOut();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            dialog.setNegativeButton("Não", null);
            dialog.create();
            dialog.show();


        } else {
            navigationView.setCheckedItem(R.id.nav_cadastrados);
            getSupportFragmentManager().popBackStack();
        }

    }
    public Boolean verificarPermissaoLocaliza() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
        ){
            return true;


        }else{
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE);
            return false;
        }
    }
}