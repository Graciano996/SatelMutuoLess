package com.example.satelprojetos.ui.enviados;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.satelprojetos.R;
import com.example.satelprojetos.adapter.FormularioAdapter;
import com.example.satelprojetos.config.ConfiguracaoFirebase;
import com.example.satelprojetos.helper.Base64Custom;
import com.example.satelprojetos.helper.EnviadoDAO;
import com.example.satelprojetos.helper.FormularioDAO;
import com.example.satelprojetos.helper.RecyclerItemClickListener;
import com.example.satelprojetos.model.Formulario;
import com.example.satelprojetos.ui.cadastrados.CadastradosViewModel;
import com.example.satelprojetos.ui.cadastro.CadastroFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnviadoFragment extends Fragment {

    private RecyclerView recyclerEnviados;
    private FormularioAdapter formularioAdapter;
    private Formulario formularioSelecionado;
    private Button btnEnviarCadastrados;
    private List<Formulario> listaFormulario = new ArrayList<>();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth autentificacao;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_enviados, container, false);
        //configurar RecyclerView
        ProgressDialog loading;
        recyclerEnviados = root.findViewById(R.id.recyclerEnviado);
        recyclerEnviados  .addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity().getApplicationContext(),
                        recyclerEnviados,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                /*Formulario formularioSelecionado = listaFormulario.get(position);
                                CadastroFragment cadastroFragment = new CadastroFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("formularioSelecionado", formularioSelecionado);
                                cadastroFragment.setArguments(bundle);
                                Log.i("TAGB",formularioSelecionado.toString());
                                NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                                navigationView.setCheckedItem(R.id.nav_cadastro);
                                FragmentManager fm = getParentFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.replace(R.id.nav_host_fragment, cadastroFragment).addToBackStack(null);
                                transaction.commit();*/

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                formularioSelecionado = listaFormulario.get(position);
                                EnviadoDAO enviadoDAO = new EnviadoDAO(getActivity().getApplicationContext());
                                enviadoDAO.deletar(formularioSelecionado);
                                carregarFormulariosCadastrados();
                                /*formularioSelecionado = listaFormulario.get(position);


                                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                                dialog.setTitle("Confirmar exclusão?");

                                dialog.setMessage("Deseja excluir o formulário: " + formularioSelecionado.getNome() + " ?");
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FormularioDAO formularioDAO = new FormularioDAO(getActivity().getApplicationContext());
                                        if(formularioDAO.deletar(formularioSelecionado)){
                                            carregarFormulariosCadastrados();
                                            Toast.makeText(getActivity().getApplicationContext(), "Sucesso ao excluir formulário", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getActivity().getApplicationContext(), "Erro ao excluir formulário", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                dialog.setNegativeButton("Não", null);
                                dialog.create();
                                dialog.show();*/
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


        return root;

    }


    public void carregarFormulariosCadastrados() {
        //Listar Formulários
        EnviadoDAO enviadoDAO = new EnviadoDAO(getActivity().getApplicationContext());
        listaFormulario = enviadoDAO.listar();
        Log.i("TAG",String.valueOf(listaFormulario.size()));

        //Configurar Adapter
        formularioAdapter = new FormularioAdapter(listaFormulario);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerEnviados.setLayoutManager(layoutManager);
        recyclerEnviados.setHasFixedSize(true);
        recyclerEnviados.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayout.VERTICAL));
        recyclerEnviados.setAdapter(formularioAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarFormulariosCadastrados();
    }

}