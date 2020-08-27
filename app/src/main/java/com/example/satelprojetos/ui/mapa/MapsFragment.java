package com.example.satelprojetos.ui.mapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.satelprojetos.R;
import com.example.satelprojetos.helper.EnviadoDAO;
import com.example.satelprojetos.helper.FormularioDAO;
import com.example.satelprojetos.helper.MapaDAO;
import com.example.satelprojetos.model.Formulario;
import com.example.satelprojetos.model.Mapa;
import com.example.satelprojetos.ui.cadastro.CadastroFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsFragment extends Fragment {
    private List<Formulario> listaFormularioCadastro = new ArrayList<>();
    private List<Formulario> listaFormularioEnvio = new ArrayList<>();
    private List<String> listaLatitude = new ArrayList<>();
    private List<String> listaLongitude = new ArrayList<>();
    private List<String> listaCodigo = new ArrayList<>();
    private List<String> listaCadastrado = new ArrayList<>();
    private List<String> listaExiste = new ArrayList<>();
    private GoogleMap mMap;
    private static final int REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private List<Mapa> listaMapa = new ArrayList<>();
    Geocoder geocoder;
    List<Address> addresses;
    List<LatLng> pathPoints;
    ProgressDialog progressDialog;
    android.location.Location lastKnownLocation;
    private Location localizacao;
    private Marker minhaLocalizacao;
    KmlLayer kmlLayer;
    MarkerManager.Collection markerCollection;
    GoogleMap googleMap2;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(final GoogleMap googleMap) {
            locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            googleMap2 = googleMap;
            ThreadGS threadGS = new ThreadGS();
            boolean connected = false;
            try {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(localizacao.getLatitude(),localizacao.getLongitude())));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(localizacao.getLatitude(),localizacao.getLongitude()), 15.0f));
            }catch (Exception e){

            }
            ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            //we are connected to a network
            connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
            if (connected) {
                MapaDAO mapaDAO = new MapaDAO(getActivity().getApplicationContext());
                mapaDAO.deletarTudo();
                threadGS.start();
                //getItems();

            } else {
                MapaDAO mapaDAO = new MapaDAO(getActivity().getApplicationContext());
                listaMapa = mapaDAO.listar();
                Log.i("TAG2", String.valueOf(listaMapa.size()));
                try {
                    for (int i = 0; i < listaMapa.size(); i++) {
                        listaLatitude.add(listaMapa.get(i).getLatitude());
                        listaLongitude.add(listaMapa.get(i).getLongitude());
                        listaCodigo.add(listaMapa.get(i).getCodigo());
                        listaCadastrado.add(listaMapa.get(i).getCadastrado());
                        listaExiste.add(listaMapa.get(i).getExiste());
                    }
                    Log.i("TAG3", String.valueOf(listaLatitude.size()));
                } catch (Exception e) {

                }
                for (int i = 0; i < listaLatitude.size(); i++) {
                    if(listaExiste.get(i).equals("NAO")){
                        continue;
                    }
                    else if (listaCadastrado.get(i).equals("SIM")) {
                        LatLng local = new LatLng(Double.parseDouble(listaLatitude.get(i)), Double.parseDouble(listaLongitude.get(i)));
                        googleMap2.addMarker(new MarkerOptions()
                                .position(local)
                                .title(listaCodigo.get(i))
                                .icon(BitmapDescriptorFactory.defaultMarker(50)));
                    }else{
                        try {
                            LatLng local = new LatLng(Double.parseDouble(listaLatitude.get(i)), Double.parseDouble(listaLongitude.get(i)));
                            googleMap2.addMarker(new MarkerOptions()
                                    .position(local)
                                    .title(listaCodigo.get(i))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        } catch (Exception e) {

                        }
                    }
                }
            }
            //progressDialog.dismiss();
            try {
                for (int i = 0; i < listaFormularioCadastro.size(); i++) {
                    LatLng local = new LatLng(Double.parseDouble(listaFormularioCadastro.get(i).getLatitude().replace(",", ".")), Double.parseDouble(listaFormularioCadastro.get(i).getLongitude().replace(",", ".")));
                    if (listaFormularioCadastro.get(i).getCodigo().equals("")) {
                        googleMap.addMarker(new MarkerOptions()
                                .position(local)
                                .title("SEM CÓDIGO")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    } else {
                        googleMap.addMarker(new MarkerOptions()
                                .position(local)
                                .title("C: " + listaFormularioCadastro.get(i).getCodigo())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
            }

            }catch (Exception e){

            }
            try {
                for (int i = 0; i < listaFormularioEnvio.size(); i++) {
                    LatLng local = new LatLng(Double.parseDouble(listaFormularioEnvio.get(i).getLatitude().replace(",", ".")), Double.parseDouble(listaFormularioEnvio.get(i).getLongitude().replace(",", ".")));
                    if (listaFormularioEnvio.get(i).getCodigo().equals("")) {
                        googleMap.addMarker(new MarkerOptions()
                                .position(local)
                                .title("SEM CÓDIGO")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    } else {
                        googleMap.addMarker(new MarkerOptions()
                                .position(local)
                                .title("E: " + listaFormularioEnvio.get(i).getCodigo())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }

                }
            }catch (Exception e){

            }

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    try {
                        minhaLocalizacao.remove();
                    } catch (Exception e) {

                    }
                    localizacao = location;
                    LatLng myLocal = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
                    /*minhaLocalizacao = googleMap.addMarker(new MarkerOptions()
                            .position(myLocal)
                            .title("Meu Local")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));*/
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(requireActivity().getApplicationContext(), "Por favor ative seu GPS", Toast.LENGTH_SHORT).show();
                }
            };
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000,
                        1,
                        locationListener);
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        1,
                        locationListener);
                googleMap.setMyLocationEnabled(true);

            }
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return true;
                }
            });
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(final Marker marker) {
                    if(marker.getTitle().equals("Meu Local")|| marker.getTitle().contains("E") || marker.getTitle().contains("C")){

                    }else {
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                        dialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String codigoEnergisa = marker.getTitle();
                                ConnectivityManager connectivityManager2 = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                if (connectivityManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                        connectivityManager2.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                                    enviarParaGS(codigoEnergisa,FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                } else {

                                    Toast.makeText(getActivity().getApplicationContext(), "Para utilizar essa opção é necessário conexão com a internet", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        dialog.setNegativeButton("Cadastrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String codigoEnergisa = marker.getTitle();
                                CadastroFragment cadastroFragment = new CadastroFragment();
                                MapsFragment mapsFragment = new MapsFragment();
                                Bundle bundle = new Bundle();
                                googleMap.clear();
                                bundle.putSerializable("codigoEnergisa", codigoEnergisa);
                                cadastroFragment.setArguments(bundle);
                                NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                                navigationView.setCheckedItem(R.id.nav_cadastro);
                                FragmentManager fm = getParentFragmentManager();
                                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }
                                FragmentTransaction transaction = fm.beginTransaction();;
                                transaction.detach(mapsFragment);
                                transaction.addToBackStack(null);
                                transaction.replace(R.id.nav_host_fragment, cadastroFragment).addToBackStack(null);
                                transaction.commit();
                            }
                        });
                        dialog.create();
                        dialog.show();

                    }
                }
            });
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        FormularioDAO formularioDAO = new FormularioDAO(getActivity().getApplicationContext());
        listaFormularioCadastro = formularioDAO.listar();
        EnviadoDAO enviadoDAO = new EnviadoDAO(getActivity().getApplicationContext());
        listaFormularioEnvio = enviadoDAO.listar();
        verificarPermissaoLocaliza();
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public Boolean verificarPermissaoLocaliza() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
        ) {
            return true;


        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE);
            return false;
        }
    }
    private void getItems() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(requireContext(),R.style.LightDialogTheme);
                progressDialog.setMessage("Carregando dados..."); // Setting Message
                progressDialog.setTitle("Por favor Espere"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzZJUnvHaDYfO13T9t7NyhLcweYuuYp38D1n0JzH0Hs4FVR0mrO/exec?action=getItems&email=" + FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String latitude = jo.getString("latitude");
                String longitude = jo.getString("longitude");
                String codigo = jo.getString("codigo");
                String cadastrado = jo.getString("cadastrado");
                String existe = jo.getString("existe");

                listaLatitude.add(latitude);
                listaLongitude.add(longitude);
                listaCodigo.add(codigo);
                listaCadastrado.add(cadastrado);
                listaExiste.add(existe);



            }
            MapaDAO mapaDAO = new MapaDAO(getActivity().getApplicationContext());
            Log.i("TAG", String.valueOf(listaLatitude.size()));
            if (mapaDAO.listar().size() == 0) {
                for (int i = 0; i < listaLatitude.size(); i++) {
                    if (listaExiste.get(i).equals("NAO")) {
                        continue;
                    }
                    else if (listaCadastrado.get(i).equals("SIM")) {
                        LatLng local = new LatLng(Double.parseDouble(listaLatitude.get(i)), Double.parseDouble(listaLongitude.get(i)));
                        googleMap2.addMarker(new MarkerOptions()
                                .position(local)
                                .title(listaCodigo.get(i))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    }
                    else {
                        try {
                            Mapa mapa = new Mapa();
                            mapa.setLatitude(listaLatitude.get(i));
                            mapa.setLongitude(listaLongitude.get(i));
                            mapa.setCodigo(listaCodigo.get(i));
                            Log.i("TAG", "Entrei " + i);
                            mapa.setCadastrado(listaCadastrado.get(i));
                            mapa.setExiste(listaExiste.get(i));
                            mapaDAO.salvar(mapa);

                            LatLng local = new LatLng(Double.parseDouble(listaLatitude.get(i)), Double.parseDouble(listaLongitude.get(i)));
                            googleMap2.addMarker(new MarkerOptions()
                                    .position(local)
                                    .title(listaCodigo.get(i))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        } catch (Exception e) {

                        }
                    }
                }
                }else{
                    for (int i = 0; i < listaLatitude.size(); i++) {
                        if (listaExiste.get(i).equals("NAO")) {
                            Mapa mapa = new Mapa();
                            mapa.setLatitude(listaLatitude.get(i));
                            mapa.setLongitude(listaLongitude.get(i));
                            mapa.setCodigo(listaCodigo.get(i));
                            Log.i("TAG", "Entrei2 " + mapa.getCodigo()+" " + i);
                            mapa.setCadastrado(listaCadastrado.get(i));
                            mapa.setExiste(listaExiste.get(i));
                            mapaDAO.atualizar(mapa);
                        }
                        else if (listaCadastrado.get(i).equals("SIM")) {
                            Mapa mapa = new Mapa();
                            mapa.setLatitude(listaLatitude.get(i));
                            mapa.setLongitude(listaLongitude.get(i));
                            mapa.setCodigo(listaCodigo.get(i));
                            Log.i("TAG", "Entrei2 " + mapa.getCodigo()+" " + i);
                            mapa.setCadastrado(listaCadastrado.get(i));
                            mapa.setExiste(listaExiste.get(i));
                            mapaDAO.atualizar(mapa);
                            LatLng local = new LatLng(Double.parseDouble(listaLatitude.get(i)), Double.parseDouble(listaLongitude.get(i)));
                            googleMap2.addMarker(new MarkerOptions()
                                    .position(local)
                                    .title(listaCodigo.get(i))
                                    .icon(BitmapDescriptorFactory.defaultMarker(50)));
                        }
                        else {
                            try {
                                Mapa mapa = new Mapa();
                                mapa.setLatitude(listaLatitude.get(i));
                                mapa.setLongitude(listaLongitude.get(i));
                                mapa.setCodigo(listaCodigo.get(i));
                                Log.i("TAG", "Entrei2 " + mapa.getCodigo()+" " + i);
                                mapa.setCadastrado(listaCadastrado.get(i));
                                mapa.setExiste(listaExiste.get(i));
                                mapaDAO.atualizar(mapa);

                                LatLng local = new LatLng(Double.parseDouble(listaLatitude.get(i)), Double.parseDouble(listaLongitude.get(i)));
                                googleMap2.addMarker(new MarkerOptions()
                                        .position(local)
                                        .title(listaCodigo.get(i))
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

       //progressDialog.dismiss();
    }


    public void enviarParaGS(final String codigo, final String email){
        final EnviadoDAO enviadoDAO = new EnviadoDAO(getActivity().getApplicationContext());
        final FormularioDAO formularioDAO = new FormularioDAO(getActivity().getApplicationContext());
        progressDialog = new ProgressDialog(requireContext(),R.style.LightDialogTheme);
        progressDialog.setMessage("Excluindo..."); // Setting Message
        progressDialog.setTitle("Por favor Espere"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzZJUnvHaDYfO13T9t7NyhLcweYuuYp38D1n0JzH0Hs4FVR0mrO/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        //getItems();
                        Toast.makeText(requireActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        MapsFragment mapsFragment = new MapsFragment();
                        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
                        navigationView.setCheckedItem(R.id.nav_mapa);
                        FragmentManager fm = getParentFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, mapsFragment).addToBackStack(null);
                        transaction.commit();
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
                parmas.put("action", "removeItem");
                parmas.put("email",email);
                parmas.put("codigo", codigo);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        queue.add(stringRequest);
    }

class ThreadGS extends Thread{
    @Override
    public void run() {
        getItems();
    }
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("DATA", "ENTRE AQUI 74");
    }
}

