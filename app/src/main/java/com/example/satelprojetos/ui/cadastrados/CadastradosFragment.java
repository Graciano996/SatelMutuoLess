package com.example.satelprojetos.ui.cadastrados;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.satelprojetos.ui.cadastro.CadastroFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastradosFragment extends Fragment {

    private CadastradosViewModel cadastradosViewModel;
    private RecyclerView recyclerCadastrados;
    private FormularioAdapter formularioAdapter;
    private Formulario formularioSelecionado;
    Button btnEnviarCadastrados;
    private List<Formulario> listaFormulario = new ArrayList<>();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth autentificacao;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cadastrados, container, false);
        //configurar RecyclerView
        ProgressDialog loading;
        btnEnviarCadastrados = root.findViewById(R.id.BtnEnviarCadastrados);
        recyclerCadastrados = root.findViewById(R.id.recyclerCadastrados);
        recyclerCadastrados.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity().getApplicationContext(),
                        recyclerCadastrados,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Formulario formularioSelecionado = listaFormulario.get(position);
                                CadastroFragment cadastroFragment = new CadastroFragment();
                                CadastradosFragment cadastradosFragment = new CadastradosFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("formularioSelecionado", formularioSelecionado);
                                cadastroFragment.setArguments(bundle);
                                NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                                navigationView.setCheckedItem(R.id.nav_cadastro);
                                FragmentManager fm = getParentFragmentManager();
                                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.detach(cadastradosFragment);
                                transaction.replace(R.id.nav_host_fragment, cadastroFragment).addToBackStack(null);
                                transaction.commit();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                formularioSelecionado = listaFormulario.get(position);


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
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        btnEnviarCadastrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ThreadPutGS threadPutGS = new ThreadPutGS(listaFormulario,
                            FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    threadPutGS.start();
            }
        });

        return root;

    }


    public void carregarFormulariosCadastrados() {
        //Listar Formulários
        FormularioDAO formularioDAO = new FormularioDAO(getActivity().getApplicationContext());
        listaFormulario = formularioDAO.listar();
        if(listaFormulario.size() <= 0){
            btnEnviarCadastrados.setEnabled(false);
        }
        else {
            btnEnviarCadastrados.setEnabled(true);
        }

        //Configurar Adapter
        formularioAdapter = new FormularioAdapter(listaFormulario);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerCadastrados.setLayoutManager(layoutManager);
        recyclerCadastrados.setHasFixedSize(true);
        recyclerCadastrados.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayout.VERTICAL));
        recyclerCadastrados.setAdapter(formularioAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarFormulariosCadastrados();
    }

    public void enviarParaGS(final List<Formulario> formularioLista, final String email){
        final EnviadoDAO enviadoDAO = new EnviadoDAO(getActivity().getApplicationContext());
        final FormularioDAO formularioDAO = new FormularioDAO(getActivity().getApplicationContext());
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(requireContext(),R.style.LightDialogTheme);
                progressDialog.setMessage("Enviado os dados para o banco de dados..."); // Setting Message
                progressDialog.setTitle("Por favor Espere"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
            }
        });
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzZJUnvHaDYfO13T9t7NyhLcweYuuYp38D1n0JzH0Hs4FVR0mrO/exec",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                     progressDialog.dismiss();
                                     carregarFormulariosCadastrados();
                                     Toast.makeText(requireActivity().getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
                            }
                        });
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parmas = new HashMap<>();
                    //here we pass params
                    parmas.put("action", "addItem");
                    parmas.put("codigo",formularioLista.get(0).getCodigo());
                    parmas.put("email", email);
                    parmas.put("urlImagem", formularioLista.get(0).getUrlImagem());
                    parmas.put("urlImagem2", formularioLista.get(0).getUrlImagem2());
                    parmas.put("urlImagem3", formularioLista.get(0).getUrlImagem3());
                    parmas.put("urlImagem4", formularioLista.get(0).getUrlImagem4());
                    parmas.put("urlImagem7", formularioLista.get(0).getUrlImagem7());
                    parmas.put("urlImagem8", formularioLista.get(0).getUrlImagem8());
                    parmas.put("urlImagem9", formularioLista.get(0).getUrlImagem9());
                    parmas.put("urlImagem10", formularioLista.get(0).getUrlImagem10());
                    parmas.put("urlImagem11", formularioLista.get(0).getUrlImagem11());
                    parmas.put("urlImagem12", formularioLista.get(0).getUrlImagem12());
                    parmas.put("data", formularioLista.get(0).getData());
                    parmas.put("municipio", formularioLista.get(0).getMunicipio());
                    parmas.put("endereco", formularioLista.get(0).getEndereco());
                    parmas.put("latitude", "'" + formularioLista.get(0).getLatitude());
                    parmas.put("longitude", "'" + formularioLista.get(0).getLongitude());
                    parmas.put("tipoPoste", formularioLista.get(0).getTipoPoste());
                    parmas.put("alturaCarga", formularioLista.get(0).getAlturaCarga());
                    parmas.put("normal", formularioLista.get(0).getNormal());
                    parmas.put("ferragemExposta", formularioLista.get(0).getFerragemExposta());
                    parmas.put("fletido", formularioLista.get(0).getFletido());
                    parmas.put("danificado", formularioLista.get(0).getDanificado());
                    parmas.put("abalroado", formularioLista.get(0).getAbalroado());
                    parmas.put("trincado", formularioLista.get(0).getTrincado());
                    parmas.put("observacaoFisicas", formularioLista.get(0).getObservacaoFisicas());
                    parmas.put("ip", formularioLista.get(0).getIp());
                    parmas.put("ipEstrutura", formularioLista.get(0).getIpEstrutura());
                    parmas.put("quantidadeLampada", formularioLista.get(0).getQuantidadeLampada());
                    parmas.put("tipoPot", formularioLista.get(0).getTipoPot());
                    parmas.put("potReator", formularioLista.get(0).getPotReator());
                    parmas.put("ipAtivacao", formularioLista.get(0).getIpAtivacao());
                    parmas.put("vinteEQuatro", formularioLista.get(0).getVinteEQuatro());
                    parmas.put("quantidade24H", formularioLista.get(0).getQuantidade24H());
                    parmas.put("observacaoIp", formularioLista.get(0).getObservacaoIP());

                    parmas.put("ativos", formularioLista.get(0).getAtivos());
                    parmas.put("chkTrafoTrifasico", formularioLista.get(0).getChkTrafoTrifasico());
                    parmas.put("chkTrafoMono", formularioLista.get(0).getChkTrafoMono());
                    parmas.put("trafoTrifasico", formularioLista.get(0).getTrafoTrifasico());
                    parmas.put("trafoMono", formularioLista.get(0).getTrafoMono());
                    parmas.put("religador", formularioLista.get(0).getReligador());
                    parmas.put("medicao", formularioLista.get(0).getMedicao());
                    parmas.put("chFusivel", formularioLista.get(0).getChFusivel());
                    parmas.put("chFaca", formularioLista.get(0).getChFaca());
                    parmas.put("banco", formularioLista.get(0).getBanco());
                    parmas.put("chFusivelReligador", formularioLista.get(0).getChFusivelReligador());
                    parmas.put("ramalSubt", formularioLista.get(0).getRamalSubt());
                    parmas.put("outros", formularioLista.get(0).getOutros());
                    parmas.put("observacaoAtivos", formularioLista.get(0).getObservacaoAtivos());

                    parmas.put("mutuo", formularioLista.get(0).getMutuo());
                    parmas.put("quantidadeOcupantes", formularioLista.get(0).getQuantidadeOcupantes());

                    parmas.put("quantidadeCabos", formularioLista.get(0).getQuantidadeCabos());
                    parmas.put("tipoCabo", formularioLista.get(0).getTipoCabo());
                    parmas.put("quantidadeCabosdois", formularioLista.get(0).getQuantidadeCabosdois());
                    parmas.put("tipoCabodois", formularioLista.get(0).getTipoCabodois());
                    parmas.put("nomeEmpresa", formularioLista.get(0).getNome());
                    parmas.put("finalidade", formularioLista.get(0).getFinalidade());
                    parmas.put("ceans", formularioLista.get(0).getCeans());
                    parmas.put("tar", formularioLista.get(0).getTar());
                    parmas.put("reservaTec", formularioLista.get(0).getReservaTec());
                    parmas.put("backbone", formularioLista.get(0).getBackbone());
                    parmas.put("placaIdent", formularioLista.get(0).getPlacaIdent());
                    parmas.put("descidaCabos", formularioLista.get(0).getDescidaCabos());
                    parmas.put("descricaoIrregularidade", formularioLista.get(0).getDescricaoIrregularidade());
                    parmas.put("observacaoMutuo", formularioLista.get(0).getObservacaoMutuo());

                    parmas.put("vegetacao", formularioLista.get(0).getVegetacao());
                    parmas.put("vegetacaoDimensao", formularioLista.get(0).getDimensaoVegetacao());
                    parmas.put("distanciaBaixa", formularioLista.get(0).getDistaciaBaixa());
                    parmas.put("distanciaMedia", formularioLista.get(0).getDistanciaMedia());
                    parmas.put("estadoArvore", formularioLista.get(0).getEstadoArvore());
                    parmas.put("quedaArvore", formularioLista.get(0).getQuedaArvore());
                    parmas.put("localArvore", formularioLista.get(0).getLocalArvore());
                    parmas.put("observacaoArvore", formularioLista.get(0).getObservacaoVegetacao());
                    parmas.put("tamanhoLista", String.valueOf(listaFormulario.size()));
                    if(enviadoDAO.salvar(formularioLista.get(0))){
                        if(formularioDAO.deletar(formularioLista.get(0))){
                            formularioLista.remove(0);
                        }
                    }



                    return parmas;
                }
            };

            int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            RequestQueue queue = Volley.newRequestQueue(requireContext());

            queue.add(stringRequest);
        }

        class ThreadPutGS extends Thread{

        private List<Formulario> formularios;
        private String email;

            public ThreadPutGS(List<Formulario> formularios, String email) {
                this.formularios = formularios;
                this.email = email;
            }

            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    FormularioDAO formularioDAO = new FormularioDAO(getActivity().getApplicationContext());
                    listaFormulario = formularioDAO.listar();
                    autentificacao = ConfiguracaoFirebase.getFirebaseAuth();
                    DatabaseReference formularios = referencia.child("usuarios").child(Base64Custom.codificarBase64(autentificacao.getCurrentUser().getEmail())).child("formulario");
                    for (int i = 0; i < listaFormulario.size(); i++) {
                        formularios.child(listaFormulario.get(i).getId().toString()).setValue(listaFormulario.get(i));
                    }
                    enviarParaGS(this.formularios, this.email);
                }else{
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }

}