
package com.example.satelprojetos.ui.cadastro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.satelprojetos.R;
import com.example.satelprojetos.activity.DrawerActivity;
import com.example.satelprojetos.config.ConfiguracaoFirebase;
import com.example.satelprojetos.helper.Base64Custom;
import com.example.satelprojetos.helper.FormularioDAO;
import com.example.satelprojetos.model.Formulario;
import com.example.satelprojetos.ui.cadastrados.CadastradosFragment;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class CadastroFragment extends Fragment {
    public int contadorIPLayout = 1;
    private static final int REQUEST_CODE = 1;
    private static final int IMAGE_CAPTURE_CODE_PAN = 2;
    private static final int IMAGE_PICK_CODE_PAN = 3;
    private static final int IMAGE_CAPTURE_CODE_ALT = 4;
    private static final int IMAGE_PICK_CODE_ALT = 5;
    private static final int IMAGE_CAPTURE_CODE_QUALIDADE = 6;
    private static final int IMAGE_PICK_CODE_QUALIDADE = 7;
    private static final int IMAGE_CAPTURE_CODE_IP = 8;
    private static final int IMAGE_PICK_CODE_IP = 9;
    private static final int IMAGE_CAPTURE_CODE_ATIVOS = 10;
    private static final int IMAGE_PICK_CODE_ATIVOS = 11;
    private static final int IMAGE_CAPTURE_CODE_ATIVOS2 = 12;
    private static final int IMAGE_PICK_CODE_ATIVOS2 = 13;
    private static final int IMAGE_CAPTURE_CODE_MUTUO = 14;
    private static final int IMAGE_PICK_CODE_MUTUO = 15;
    private static final int IMAGE_CAPTURE_CODE_MUTUO2 = 16;
    private static final int IMAGE_PICK_CODE_MUTUO2 = 17;
    private static final int IMAGE_CAPTURE_CODE_MUTUO3 = 18;
    private static final int IMAGE_PICK_CODE_MUTUO3 = 19;
    private static final int IMAGE_CAPTURE_CODE_VEG = 20;
    private static final int IMAGE_PICK_CODE_VEG= 21;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private StorageReference storageReference;
    private FirebaseAuth autentificacao;
    private ProgressDialog progressDialog;
    Geocoder geocoder;
    Boolean controle = false;
    List<Address> addresses;

    public Button btnIPLayoutRemove, btnAtivoRemove, btnMutuoRemove;
    private List<EditText> editIPQuantidadeLampada, editIPPot,editIP24H,editAtivoChFusivel,
            editAtivoChFusivelReligador,editAtivoOutro,editMutuoNome ,editMutuoQuantidade,
            editMutuoQuantidade2, editMutuoIrregularidade;
    private List<Spinner> spinIPEstrutura, spinIPTipo, spinIPTipoAtivacao,spinAtivoTrifasico,
    spinAtivoMono,spinAtivoChFaca,spinAtivoBanco,spinAtivoRamal,spinMutuoTipoCabo,
    spinMutuoTipoCabo2,spinMutuoFinalidade,spinMutuoCeans,spinMutuoTar,spinMutuoReservaTec,
            spinMutuoBackbone;
    private List<CheckBox> checkIP24,checkAtivoTrifasico,checkAtivoMono,checkAtivoReligador,
    checkAtivoMedicao,checkMutuoPlaca,checkMutuoDescida;
    private List<TextView> textIPQLampada, textIP,textAtivoChFusivel,textAtivoChFusivelReligador,
    textAtivoOutro,textAtivo, textMutuo,textMutuoNome,textMutuoQuantidade,textMutuoQuantidade2,
    textMutuoIrregularidade;
    private RelativeLayout relativeDimensaoVegetacao, relativeBaixa, relativeMedia, relativeEstadoArvore,
            relativeLocalArvore;
    private EditText codigo, endereco, latitude, longitude, observacaoFisicas,
            observacaoAtivos,
            observacaoVegetacao, observacaoIP, quantidadeOcupantes, observacaoMutuo;
    private Spinner municipio, alturaCarga, tipoPoste,
            dimensaoVegetacao, distaciaBaixa, distanciaMedia, estadoArvore,
            localArvore;
    private CheckBox normal, ferragemExposta, fletido, danificado, abalroado, trincado,
            ativos, ip, mutuo, quedaArvore,chkVegetacao;
    private Formulario formularioAtual;
    private File photoFile = null;
    private Location localizacao;
    private String codigoEnergisa ="";
    public int contadorIp = 1, contadorAt = 1, contadorAr = 1;
    private ImageView fotoPan, fotoAlt, fotoQualidade, fotoIP,fotoAtivos, fotoAtivos2,fotoMutuo, 
            fotoMutuo2, fotoMutuo3,fotoVeg;
    private Uri urlFotoPan, urlFotoAlt, urlFotoQualidade, urlFotoIP,
            urlFotoAtivos, urlFotoAtivos2, urlFotoMutuo, urlFotoMutuo2, urlFotoMutuo3,urlFotoVeg;
    private Bitmap imagemPan, imagemAlt, imagemQualidade, imagemIP, imagemAtivos, imagemAtivos2,
            imagemMutuo, imagemMutuo2, imagemMutuo3,imagemVeg;
    private String imgPathPan, imgPathAlt, imgPathQualidade,imgPathIP, imgPathAtivos,
            imgPathAtivos2, imgPathMutuo, imgPathMutuo2, imgPathMutuo3,imgPathVeg;
    public Button btnFoto, btnFoto2,btnFoto3,btnFoto4, btnFoto13,btnFoto14,btnFoto15,btnFoto16,btnFoto17,
    btnFoto30,btnUpload30;
    public View root;
    Boolean outroOcupante = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_cadastro, container, false);
        ((DrawerActivity) getActivity()).getSupportActionBar().setTitle("Cadastro");
        codigo = root.findViewById(R.id.textCadastroCodigo);
        editIPQuantidadeLampada = new ArrayList<>();
        editIPPot = new ArrayList<>();
        editIP24H = new ArrayList<>();
        spinIPEstrutura = new ArrayList<>();
        spinIPTipo = new ArrayList<>();
        spinIPTipoAtivacao = new ArrayList<>();
        checkIP24= new ArrayList<>();
        textIPQLampada = new ArrayList<>();
        textIP = new ArrayList<>();
        textAtivo = new ArrayList<>();
        textAtivoChFusivel = new ArrayList<>();
        textAtivoChFusivelReligador = new ArrayList<>();
        textAtivoOutro = new ArrayList<>();
        editAtivoChFusivel = new ArrayList<>();
        editAtivoChFusivelReligador = new ArrayList<>();
        editAtivoOutro = new ArrayList<>();
        spinAtivoTrifasico = new ArrayList<>();
        spinAtivoMono = new ArrayList<>();
        spinAtivoChFaca = new ArrayList<>();
        spinAtivoBanco = new ArrayList<>();
        spinAtivoRamal = new ArrayList<>();
        checkAtivoTrifasico = new ArrayList<>();
        checkAtivoMono = new ArrayList<>();
        checkAtivoReligador = new ArrayList<>();
        checkAtivoMedicao = new ArrayList<>();
        textMutuo = new ArrayList<>();
        textMutuoNome = new ArrayList<>();
        textMutuoQuantidade = new ArrayList<>();
        textMutuoQuantidade2 = new ArrayList<>();
        textMutuoIrregularidade = new ArrayList<>();
        editMutuoNome = new ArrayList<>();
        editMutuoQuantidade = new ArrayList<>();
        editMutuoQuantidade2 = new ArrayList<>();
        editMutuoIrregularidade = new ArrayList<>();
        spinMutuoTipoCabo = new ArrayList<>();
        spinMutuoTipoCabo2 = new ArrayList<>();
        spinMutuoFinalidade = new ArrayList<>();
        spinMutuoCeans = new ArrayList<>();
        spinMutuoTar = new ArrayList<>();
        spinMutuoReservaTec = new ArrayList<>();
        spinMutuoBackbone = new ArrayList<>();
        checkMutuoPlaca = new ArrayList<>();
        checkMutuoDescida = new ArrayList<>();


        fotoPan = root.findViewById(R.id.imageCadastroFoto);
        fotoAlt = root.findViewById(R.id.imageCadastroFoto2);
        fotoQualidade = root.findViewById(R.id.imageCadastroFoto3);
        fotoIP = root.findViewById(R.id.imageCadastroFoto4);
        fotoAtivos = root.findViewById(R.id.imageCadastroFoto13);
        fotoAtivos2 = root.findViewById(R.id.imageCadastroFoto14);
        fotoMutuo = root.findViewById(R.id.imageCadastroFoto15);
        fotoMutuo2 = root.findViewById(R.id.imageCadastroFoto16);
        fotoMutuo3 = root.findViewById(R.id.imageCadastroFoto17);
        fotoVeg = root.findViewById(R.id.imageCadastroFoto30);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                localizacao = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                StyleableToast.makeText(requireActivity().getApplicationContext(), "Por favor ative seu GPS", R.style.ToastWarning).show();
            }
        };
        geocoder = new Geocoder(requireContext(), Locale.getDefault());
        if (verificarPermissaoLocaliza()) {

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000,
                        2,
                        locationListener);
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000,
                        2,
                        locationListener);

            }


        }
        Button btnLocaliza = root.findViewById(R.id.btnCadastroGetMap);
        btnLocaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localizacao == null) {
                    StyleableToast.makeText(requireActivity().getApplicationContext(), "Não foi possivel recuperar a localização, por favor verifique seu nivel de bateria", R.style.ToastWarning).show();
                } else {
                    latitude.setText(String.format("%.5f", localizacao.getLatitude()));
                    longitude.setText(String.format("%.5f", localizacao.getLongitude()));
                    try {
                        addresses = geocoder.getFromLocation(localizacao.getLatitude(), localizacao.getLongitude(), 1);
                        endereco.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        storageReference = ConfiguracaoFirebase.getStorageReference();
        autentificacao = ConfiguracaoFirebase.getFirebaseAuth();
        btnFoto = root.findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_PAN);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_PAN);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto2 = root.findViewById(R.id.btnFoto2);
        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_ALT);


                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_ALT);

                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });
        btnFoto3 = root.findViewById(R.id.btnFoto3);
        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_QUALIDADE);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_QUALIDADE);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto4 = root.findViewById(R.id.btnFoto4);
        btnFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_IP);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_IP);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }

            }
        });
        btnFoto13 = root.findViewById(R.id.btnFoto13);
        btnFoto13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_ATIVOS);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_ATIVOS);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto14 = root.findViewById(R.id.btnFoto14);
        btnFoto14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_ATIVOS2);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_ATIVOS2);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto15 = root.findViewById(R.id.btnFoto15);
        btnFoto15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_MUTUO);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_MUTUO);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto16 = root.findViewById(R.id.btnFoto16);
        btnFoto16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_MUTUO2);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_MUTUO2);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto17 = root.findViewById(R.id.btnFoto17);
        btnFoto17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_MUTUO3);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_MUTUO3);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });

        btnFoto30 = root.findViewById(R.id.btnFoto30);
        btnFoto30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE_VEG);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE_VEG);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });


        endereco = root.findViewById(R.id.textCadastroEndereco);
        municipio = root.findViewById(R.id.spinCadastroMunicipio);
        latitude = root.findViewById(R.id.textCadastroLatitude);
        longitude = root.findViewById(R.id.textCadastroLongitude);
        alturaCarga = root.findViewById(R.id.spinCadastroAlturaCarga);

        //Fisicas
        tipoPoste = root.findViewById(R.id.spinCadastroTipoPoste);
        normal = root.findViewById(R.id.chkCadastroNormal);
        ferragemExposta = root.findViewById(R.id.chkCadastroFerragem);
        fletido = root.findViewById(R.id.chkCadastroFletido);
        danificado = root.findViewById(R.id.chkCadastroDanificado);
        abalroado = root.findViewById(R.id.chkCadastroAbalrroado);
        trincado = root.findViewById(R.id.chkCadastroTrincado);
        observacaoFisicas = root.findViewById(R.id.textCadastroObservacaoFisicas);

        //Iluminação
        ip = root.findViewById(R.id.chkCadastroIP);
        observacaoIP = root.findViewById(R.id.textCadastroObservacaoIP);

        //TRAFO
        ativos = root.findViewById(R.id.chkAtivos);
        observacaoAtivos = root.findViewById(R.id.textCadastroObservacaoAtivo);

        //MUTUO
        mutuo = root.findViewById(R.id.chkCadastroMutuo);

        quantidadeOcupantes = root.findViewById(R.id.textCadastroQuantidadeMutuo);

        observacaoMutuo = root.findViewById(R.id.textCadastroObservacaoMutuo);
        Button buttonUpload = root.findViewById(R.id.btnCadastroUploadAll);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAll();
            }
        });
        //VEGETAÇÃO
        chkVegetacao = root.findViewById(R.id.chkCadastroVegetacao);
        relativeDimensaoVegetacao = root.findViewById(R.id.relativeSpinDimensaoVegetacao);
        dimensaoVegetacao = root.findViewById(R.id.spinCadastroDimensaoVegetacao);
        relativeBaixa = root.findViewById(R.id.relativeSpinBaixa);
        distaciaBaixa = root.findViewById(R.id.spinCadastroBaixa);
        relativeMedia = root.findViewById(R.id.relativeSpinMedia);
        distanciaMedia = root.findViewById(R.id.spinCadastroMedia);
        relativeEstadoArvore = root.findViewById(R.id.relativeSpinEstadoArvore);
        estadoArvore = root.findViewById(R.id.spinCadastroEstadoArvore);
        quedaArvore = root.findViewById(R.id.chkCadastroQuedaArvore);
        relativeLocalArvore = root.findViewById(R.id.relativeSpinLocalArvore);
        localArvore = root.findViewById(R.id.spinCadastroLocalArvore);
        observacaoVegetacao = root.findViewById(R.id.textCadastroObservacaoVegetacao);
        chkVegetacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkVegetacao.isChecked()) {
                    dimensaoVegetacao.setVisibility(View.VISIBLE);
                    distaciaBaixa.setVisibility(View.VISIBLE);
                    distanciaMedia.setVisibility(View.VISIBLE);
                    estadoArvore.setVisibility(View.VISIBLE);
                    quedaArvore.setVisibility(View.VISIBLE);
                    localArvore.setVisibility(View.VISIBLE);
                    relativeDimensaoVegetacao.setVisibility(View.VISIBLE);
                    relativeBaixa.setVisibility(View.VISIBLE);
                    relativeMedia.setVisibility(View.VISIBLE);
                    relativeEstadoArvore.setVisibility(View.VISIBLE);
                    relativeLocalArvore.setVisibility(View.VISIBLE);
                } else {
                    dimensaoVegetacao.setVisibility(View.GONE);
                    distaciaBaixa.setVisibility(View.GONE);
                    distanciaMedia.setVisibility(View.GONE);
                    estadoArvore.setVisibility(View.GONE);
                    quedaArvore.setVisibility(View.GONE);
                    localArvore.setVisibility(View.GONE);
                    relativeDimensaoVegetacao.setVisibility(View.GONE);
                    relativeBaixa.setVisibility(View.GONE);
                    relativeMedia.setVisibility(View.GONE);
                    relativeEstadoArvore.setVisibility(View.GONE);
                    relativeLocalArvore.setVisibility(View.GONE);
                }
            }
        });
        try {
            if( this.getArguments() != null) {
                codigoEnergisa = (String) this.getArguments().getSerializable("codigoEnergisa");
                if (codigoEnergisa != null) {
                    codigo.setText(codigoEnergisa);
                }
            }
        } catch (Exception e) {
        }
        Button buttonCadastrar = root.findViewById(R.id.btnCadastroSalvar);
        try {
            //assert this.getArguments() != null;
            formularioAtual = (Formulario) this.getArguments().getSerializable("formularioSelecionado");
            if (formularioAtual != null) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                dialog.setTitle("O que deseja fazer:");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Editar dados do poste atual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new MesmoPosteTask().execute();
                    }
                });
                dialog.setNegativeButton("Recuperar dados para novo poste", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new NovoPosteTask().execute();

                    }
                });
                dialog.create();
                dialog.show();
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {

                }

                //LOCALIZAÇÃO

                Button btnNovoPoste = root.findViewById(R.id.btnNovoPoste);
                btnNovoPoste.setVisibility(View.VISIBLE);
                //add button to the layout
                btnNovoPoste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //region pegar dados
                        try {
                            getFormulario(true);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
        }


        normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                  if (normal.isChecked()) {
                                                      ferragemExposta.setChecked(false);
                                                      fletido.setChecked(false);
                                                      danificado.setChecked(false);
                                                      abalroado.setChecked(false);
                                                      trincado.setChecked(false);
                                                      ferragemExposta.setEnabled(false);
                                                      fletido.setEnabled(false);
                                                      danificado.setEnabled(false);
                                                      abalroado.setEnabled(false);
                                                      trincado.setEnabled(false);
                                                  } else {
                                                      ferragemExposta.setEnabled(true);
                                                      fletido.setEnabled(true);
                                                      danificado.setEnabled(true);
                                                      abalroado.setEnabled(true);
                                                      trincado.setEnabled(true);
                                                  }


                                              }
                                          }
        );

        //Listener para só habilitar os dados da própria ip e as próximas ip caso a primeira
        // tenha sido checada
        btnIPLayoutRemove = root.findViewById(R.id.buttonIPLayoutRemove);
        btnIPLayoutRemove.setVisibility(View.GONE);
        btnIPLayoutRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIP();
            }
        });
        final Button btnIPLayout = root.findViewById(R.id.buttonIPLayout);
        btnIPLayout.setVisibility(View.GONE);
        btnIPLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIP();
                try {
                    btnIPLayoutRemove.setVisibility(View.VISIBLE);
                }catch (Exception e){

                }
            }
        });
        ip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ip.isChecked()) {
                    btnIPLayout.setVisibility(View.VISIBLE);

                } else {
                    btnIPLayout.setVisibility(View.GONE);
                    removeIPAll();
                    btnIPLayoutRemove.setVisibility(View.GONE);
                }
            }
        });

        btnAtivoRemove = root.findViewById(R.id.buttonAtivoRemove);
        btnAtivoRemove.setVisibility(View.GONE);
        btnAtivoRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAtivo();
            }
        });

        final Button btnAtivo = root.findViewById(R.id.buttonAtivo);
        btnAtivo.setVisibility(View.GONE);
        btnAtivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAtivo();
                btnAtivoRemove.setVisibility(View.VISIBLE);
            }
        });

        //Listener para só habilitar os dados da própria ip e as próximas ip caso a segunda
        // tenha sido checada
        ativos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ativos.isChecked()) {
                    btnAtivo.setVisibility(View.VISIBLE);


                } else {
                    btnAtivo.setVisibility(View.GONE);
                    removeAtivoAll();
                    btnAtivoRemove.setVisibility(View.GONE);
                }
            }
        });

        btnMutuoRemove = root.findViewById(R.id.buttonMutuoRemove);
        btnMutuoRemove.setVisibility(View.GONE);
        btnMutuoRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMutuo();
            }
        });

        final Button btnMutuo = root.findViewById(R.id.buttonMutuo);
        btnMutuo.setVisibility(View.GONE);
        btnMutuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMutuo();
                btnMutuoRemove.setVisibility(View.VISIBLE);
            }
        });

        mutuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mutuo.isChecked()) {
                    btnMutuo.setVisibility(View.VISIBLE);
                } else {
                    btnMutuo.setVisibility(View.GONE);
                    removeMutuoAll();
                    btnMutuoRemove.setVisibility(View.GONE);
                }

            }
        });

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getFormulario(false);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return root;
    }

    //Pega o valor do spinner e coloca um "-" caso, o usuário não tenha escolhido nenhuma opção.
    public void setLista(Formulario formulario, Spinner spinner, String atributo) {
        if (spinner.getSelectedItem().toString().equals(spinner.getItemAtPosition(0).toString())) {
            formulario.GenericSetter(atributo, "-");
        } else {
            formulario.GenericSetter(atributo, spinner.getSelectedItem().toString());
        }
    }

    public void mutuoDados(EditText editText, Spinner spinner, Boolean estado) {
        if ((editText != null) && !estado) {
            editText.setText("");
            editText.setEnabled(estado);
            editText.setVisibility(View.GONE);

        } else if ((editText != null) && estado) {
            editText.setEnabled(estado);
            editText.setVisibility(View.VISIBLE);

        } else if ((spinner != null) && !estado) {
            spinner.setSelection(0);
            spinner.setEnabled(estado);
            spinner.setVisibility(View.GONE);
        } else if ((spinner != null) && estado) {
            spinner.setEnabled(estado);
            spinner.setVisibility(View.VISIBLE);
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


    public Boolean verificarPermissao() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED
        ) {
            return true;


        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verificarPermissao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ThreadCamera threadCamera = new ThreadCamera(requestCode, resultCode, data);
        threadCamera.start();
    }

    public void chamarGaleria(int imagemCodigo) {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (cameraIntent.resolveActivity(requireActivity().getApplicationContext().getPackageManager()) != null) {
            cameraIntent.setFlags(cameraIntent .getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivityForResult(cameraIntent, imagemCodigo);
        }
    }

    public void chamarCamera(int imagemCodigo) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getApplicationContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(requireActivity().getApplicationContext(),
                            "com.example.satelprojetos.provider",
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    cameraIntent.setFlags(cameraIntent .getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivityForResult(cameraIntent, imagemCodigo);
                }
                else{

                }
            } catch (Exception ex) {
            }
        } else {
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Log.i("NOEM", timeStamp);
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.i("Entrei aqui","2");
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //String imgPathFile = image.getAbsolutePath();
        return image;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Função para rotacionar a Imagem, para que a mesma fique com 90º
    private static Bitmap rotateImageIfRequired(String filePath) throws IOException {
        ExifInterface ei;
        ei = new ExifInterface(filePath);

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:

                return rotateImage(BitmapFactory.decodeFile(filePath), 90);
            case ExifInterface.ORIENTATION_ROTATE_180:

                return rotateImage(BitmapFactory.decodeFile(filePath), 180);
            case ExifInterface.ORIENTATION_ROTATE_270:

                return rotateImage(BitmapFactory.decodeFile(filePath), 270);
            default:

                return BitmapFactory.decodeFile(filePath);
        }
    }

    //Função para enviar a foto para o banco de dados.
    public void upload(String imgPathUpload, EditText codigoUpload, int contadorUpload, final int codigoSetor, String sufixo) {
        if (imgPathUpload == null) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StyleableToast.makeText(requireActivity().getApplicationContext(), "Escolha primeiro uma foto para fazer o upload", R.style.ToastWarning).show();
                }
            });


        } else if (codigoUpload.getText().toString().equals("") || codigoUpload == null) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StyleableToast.makeText(requireActivity().getApplicationContext(), "Insira o código do poste primeiro", R.style.ToastWarning).show();
                }
            });

        } else {
            if (isNetworkAvailable()) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                Bitmap imagemCorrigida;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    imagemCorrigida = rotateImageIfRequired(imgPathUpload);
                } catch (IOException e) {
                    imagemCorrigida = BitmapFactory.decodeFile(imgPathUpload);
                    e.printStackTrace();
                }

                imagemCorrigida.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                imagemCorrigida.recycle();
                Runtime.getRuntime().gc();
                byte[] dadosImagem = baos.toByteArray();
                String pastaNome = codigoUpload.getText().toString() + "_" + sufixo + contadorUpload + ".jpeg";
                final StorageReference imageRef = storageReference
                        .child("imagens")
                        .child(Base64Custom.codificarBase64(autentificacao.getCurrentUser().getEmail()))
                        .child(codigoUpload.getText().toString())
                        .child(pastaNome);
                UploadTask uploadTask = imageRef.putBytes(dadosImagem);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StyleableToast.makeText(requireActivity().getApplicationContext(), "Erro ao fazer upload, verifique a conexão com a internet", R.style.ToastError).show();
                            }
                        });
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                switch (codigoSetor) {
                                    case 1:
                                        urlFotoPan = task.getResult();


                                        break;
                                    case 2:
                                        urlFotoAlt = task.getResult();


                                        break;
                                    case 3:
                                        urlFotoQualidade = task.getResult();

                                        progressDialog.dismiss();
                                        break;
                                    case 4:
                                        urlFotoIP = task.getResult();


                                        break;
                                    case 5:
                                        urlFotoAtivos = task.getResult();


                                        break;
                                    case 6:
                                        urlFotoAtivos2 = task.getResult();


                                        break;
                                    case 7:
                                        urlFotoMutuo = task.getResult();


                                        break;
                                    case 8:
                                        urlFotoMutuo2 = task.getResult();


                                        break;
                                    case 9:
                                        urlFotoMutuo3 = task.getResult();


                                        break;
                                    case 10:

                                        urlFotoVeg = task.getResult();


                                        break;
                                }
                            }
                        });

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                    }
                });
            } else {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StyleableToast.makeText(requireActivity().getApplicationContext(), "Erro ao fazer upload, verifique a conexão com a internet", R.style.ToastError).show();
                    }
                });

            }
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    //Função criar um novo Thread quando chamar a ação da câmera
    class ThreadCamera extends Thread {
        private int requestCode;
        private int resultCode;
        private Intent data;

        public ThreadCamera(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        @Override
        public void run() {
            intentGet(this.requestCode, this.resultCode, this.data);
        }
    }

    //Função criar um novo Thread quando chamar a ação de Upload
    class ThreadUpload extends Thread {
        private String imgPathUpload;
        private EditText codigoUpload;
        private int contadorUpload;
        private int codigoSetor;
        private String sufixo;

        public ThreadUpload(String imgPathUpload, EditText codigoUpload, int contadorUpload, int codigoSetor, String sufixo) {
            this.imgPathUpload = imgPathUpload;
            this.codigoUpload = codigoUpload;
            this.contadorUpload = contadorUpload;
            this.codigoSetor = codigoSetor;
            this.sufixo = sufixo;
        }

        @Override
        public void run() {
            upload(this.imgPathUpload, this.codigoUpload, this.contadorUpload,
                    this.codigoSetor, this.sufixo);
        }
    }

    class ThreadNovoPoste extends Thread {
        private View root;

        public ThreadNovoPoste(View root) {
            this.root = root;
        }

        @Override
        public void run() {
            novoPoste(this.root);
        }
    }

    class ThreadMesmoPoste extends Thread {
        private View root;

        public ThreadMesmoPoste(View root) {
            this.root = root;
        }

        @Override
        public void run() {
            mesmoPoste(this.root);
        }
    }


    //Função para recuperar o resultCode após a escolha de câmera ou galeria
    public void intentGet(int requestCode, int resultCode, @Nullable final Intent data) {
        if (resultCode == RESULT_OK) {
            final Uri localImagemSelecionada;
            final Cursor cursor;
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final int columnIndex;
            try {
                switch (requestCode) {
                    case IMAGE_CAPTURE_CODE_PAN:
                        //imagemPan.recycle();
                        imgPathPan = photoFile.getAbsolutePath();
                        //imagemPan = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoPan.setImageBitmap(BitmapFactory.decodeFile(imgPathPan,options));
                            }
                        });
                        //imagemPan = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                        break;
                    case IMAGE_PICK_CODE_PAN:
                        localImagemSelecionada = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgPathPan = cursor.getString(columnIndex);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoPan.setImageBitmap(BitmapFactory.decodeFile(imgPathPan,options));
                            }
                        });
                        break;
                    case IMAGE_CAPTURE_CODE_ALT:
                        imgPathAlt = photoFile.getAbsolutePath();
                        //imagemAlt = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoAlt.setImageBitmap(BitmapFactory.decodeFile(imgPathAlt,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_ALT:
                        localImagemSelecionada = data.getData();
                        String[] filePathColumn2 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn2, null, null, null);
                        cursor.moveToFirst();
                        columnIndex = cursor.getColumnIndex(filePathColumn2[0]);
                        imgPathAlt = cursor.getString(columnIndex);
                        //imagemAlt = BitmapFactory.decodeFile(imgPathAlt,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoAlt.setImageBitmap(BitmapFactory.decodeFile(imgPathAlt,options));
                            }
                        });
                        break;

                    case IMAGE_CAPTURE_CODE_QUALIDADE:
                        imgPathQualidade = photoFile.getAbsolutePath();
                        //imagemQualidade = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoQualidade.setImageBitmap(BitmapFactory.decodeFile(imgPathQualidade,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_QUALIDADE:
                        localImagemSelecionada = data.getData();
                        String[] filePathColumn3 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn3, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn3[0]);
                        imgPathQualidade = cursor.getString(columnIndex);
                        //imagemQualidade = BitmapFactory.decodeFile(imgPathQualidade,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoQualidade.setImageBitmap(BitmapFactory.decodeFile(imgPathQualidade,options));
                            }
                        });
                        break;

                    case IMAGE_CAPTURE_CODE_IP:
                        imgPathIP = photoFile.getAbsolutePath();
                        //imagemIP = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoIP.setImageBitmap(BitmapFactory.decodeFile(imgPathIP,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_IP:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn4 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn4, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn4[0]);
                        imgPathIP = cursor.getString(columnIndex);
                        //imagemIP = BitmapFactory.decodeFile(imgPathIP,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoIP.setImageBitmap(BitmapFactory.decodeFile(imgPathIP,options));
                            }
                        });
                        break;
                    case IMAGE_CAPTURE_CODE_ATIVOS:

                        imgPathAtivos = photoFile.getAbsolutePath();
                        //imagemAtivos = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoAtivos.setImageBitmap(BitmapFactory.decodeFile(imgPathAtivos,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_ATIVOS:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn13 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn13, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn13[0]);
                        imgPathAtivos = cursor.getString(columnIndex);
                        //imagemAtivos = BitmapFactory.decodeFile(imgPathAtivos,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoAtivos.setImageBitmap(BitmapFactory.decodeFile(imgPathAtivos,options));
                            }
                        });
                        break;
                    case IMAGE_CAPTURE_CODE_ATIVOS2:

                        imgPathAtivos2 = photoFile.getAbsolutePath();
                        //imagemAtivos2 = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoAtivos2.setImageBitmap(BitmapFactory.decodeFile(imgPathAtivos2,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_ATIVOS2:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn14 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn14, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn14[0]);
                        imgPathAtivos2 = cursor.getString(columnIndex);
                        //imagemAtivos2 = BitmapFactory.decodeFile(imgPathAtivos2,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoAtivos2.setImageBitmap(BitmapFactory.decodeFile(imgPathAtivos2,options));
                            }
                        });
                        break;
                    case IMAGE_CAPTURE_CODE_MUTUO:

                        imgPathMutuo = photoFile.getAbsolutePath();
                        //imagemMutuo = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoMutuo.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_MUTUO:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn15 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn15, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn15[0]);
                        imgPathMutuo = cursor.getString(columnIndex);
                        //imagemMutuo = BitmapFactory.decodeFile(imgPathMutuo,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoMutuo.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo,options));
                            }
                        });
                        break;

                    case IMAGE_CAPTURE_CODE_MUTUO2:

                        imgPathMutuo2 = photoFile.getAbsolutePath();
                        //imagemMutuo2 = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoMutuo2.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo2,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_MUTUO2:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn16 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn16, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn16[0]);
                        imgPathMutuo2 = cursor.getString(columnIndex);
                        //imagemMutuo2 = BitmapFactory.decodeFile(imgPathMutuo2,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoMutuo2.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo2,options));
                            }
                        });
                        break;
                    case IMAGE_CAPTURE_CODE_MUTUO3:

                        imgPathMutuo3 = photoFile.getAbsolutePath();
                        //imagemMutuo3 = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoMutuo3.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo3,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_MUTUO3:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn17 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn17, null, null, null);
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn17[0]);
                        imgPathMutuo3 = cursor.getString(columnIndex);
                        //imagemMutuo3 = BitmapFactory.decodeFile(imgPathMutuo3,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoMutuo3.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo3,options));
                            }
                        });
                        break;
                    case IMAGE_CAPTURE_CODE_VEG:

                        imgPathVeg = photoFile.getAbsolutePath();
                        imagemVeg = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),options);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoVeg.setImageBitmap(BitmapFactory.decodeFile(imgPathVeg,options));
                            }
                        });
                        break;
                    case IMAGE_PICK_CODE_VEG:

                        localImagemSelecionada = data.getData();
                        String[] filePathColumn30 = {MediaStore.Images.Media.DATA};
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn30, null, null, null);
                        cursor.moveToFirst();
                        columnIndex = cursor.getColumnIndex(filePathColumn30[0]);
                        imgPathVeg = cursor.getString(columnIndex);
                        //imagemVeg = BitmapFactory.decodeFile(imgPathVeg,options);
                        cursor.close();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoVeg.setImageBitmap(BitmapFactory.decodeFile(imgPathVeg,options));
                            }
                        });
                        break;


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Função para recuperar  certos dados do poste selecionado
    private void novoPoste(final View root) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                root.findViewById(R.id.btnCadastroSalvar).setVisibility(View.GONE);
                codigo.setText("");
                contadorIp = 1;
                contadorAr = 1;
                contadorAt = 1;
                Button btnNovoPoste = root.findViewById(R.id.btnNovoPoste);
                btnNovoPoste.setText("Novo Poste");
                fotoPan.setImageResource(R.drawable.ic_menu_camera);
                fotoAlt.setImageResource(R.drawable.ic_menu_camera);
                fotoQualidade.setImageResource(R.drawable.ic_menu_camera);
                fotoIP.setImageResource(R.drawable.ic_menu_camera);
                fotoAtivos.setImageResource(R.drawable.ic_menu_camera);
                fotoAtivos2.setImageResource(R.drawable.ic_menu_camera);
                fotoMutuo.setImageResource(R.drawable.ic_menu_camera);
                fotoMutuo2.setImageResource(R.drawable.ic_menu_camera);
                fotoMutuo3.setImageResource(R.drawable.ic_menu_camera);
                fotoVeg.setImageResource(R.drawable.ic_menu_camera);
                urlFotoPan = null;
                urlFotoAlt = null;
                urlFotoQualidade = null;
                urlFotoIP = null;
                urlFotoAtivos = null;
                urlFotoAtivos2 = null;
                urlFotoMutuo = null;
                urlFotoMutuo2 = null;
                urlFotoMutuo3 = null;
                urlFotoVeg = null;

                if (formularioAtual.getMunicipio().equals("-")) {
                    municipio.setSelection(0);
                } else {
                    for (int i = 0; i < municipio.getAdapter().getCount(); i++) {
                        municipio.setSelection(i);
                        if (municipio.getSelectedItem().toString().equals(formularioAtual.getMunicipio())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getAlturaCarga().equals("-")) {
                    alturaCarga.setSelection(0);
                } else {
                    for (int i = 0; i < alturaCarga.getAdapter().getCount(); i++) {
                        alturaCarga.setSelection(i);
                        if (alturaCarga.getSelectedItem().toString().equals(formularioAtual.getAlturaCarga())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getTipoPoste().equals("-")) {
                    tipoPoste.setSelection(0);
                } else {
                    for (int i = 0; i < tipoPoste.getAdapter().getCount(); i++) {
                        tipoPoste.setSelection(i);
                        if (tipoPoste.getSelectedItem().toString().equals(formularioAtual.getTipoPoste())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getNormal().equals("Sim")) {
                    normal.setChecked(true);
                } else {
                    normal.setChecked(false);
                    ferragemExposta.setEnabled(true);
                    fletido.setEnabled(true);
                    abalroado.setEnabled(true);
                    trincado.setEnabled(true);
                    danificado.setEnabled(true);
                }
                if (formularioAtual.getFerragemExposta().equals("Sim")) {
                    ferragemExposta.setChecked(true);
                }
                if (formularioAtual.getFletido().equals("Sim")) {
                    fletido.setChecked(true);
                }
                if (formularioAtual.getDanificado().equals("Sim")) {
                    danificado.setChecked(true);
                }
                if (formularioAtual.getAbalroado().equals("Sim")) {
                    abalroado.setChecked(true);
                }
                if (formularioAtual.getTrincado().equals("Sim")) {
                    trincado.setChecked(true);
                }
                observacaoFisicas.setText(formularioAtual.getObservacaoFisicas());


                //ILUMINAÇÃO
                Log.i("SIZES","OI");
                if (formularioAtual.getIp().equals("Sim")) {
                    ip.setChecked(true);
                    String[] textoIPEstruturaPartido = formularioAtual.getIpEstrutura().split(",");
                    for (int i = 1;i< textoIPEstruturaPartido.length; i++) {
                        createIP();
                    }
                    for (int i = 1;i< textoIPEstruturaPartido.length; i++){
                        if (textoIPEstruturaPartido[i].equals("-")) {
                            spinIPEstrutura.get(i-1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinIPEstrutura.get(i-1).getAdapter().getCount(); a++) {
                                spinIPEstrutura.get(i-1).setSelection(a);
                                if (spinIPEstrutura.get(i-1).getSelectedItem().toString().equals(textoIPEstruturaPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }
                    String[] textoIPQuantidadePartido = formularioAtual.getQuantidadeLampada().split(",");
                    for (int i = 1;i< textoIPQuantidadePartido.length; i++) {
                        editIPQuantidadeLampada.get(i-1).setText(textoIPQuantidadePartido[i]);
                    }
                    String[] textoIPTipoPotPartido = formularioAtual.getTipoPot().split(",");
                    for (int i = 1;i< textoIPTipoPotPartido.length; i++){
                        if (textoIPTipoPotPartido[i].equals("-")) {
                            spinIPTipo.get(i-1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinIPTipo.get(i-1).getAdapter().getCount(); a++) {
                                spinIPTipo.get(i-1).setSelection(a);
                                if (spinIPTipo.get(i-1).getSelectedItem().toString().equals(textoIPTipoPotPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }
                    String[] textoIPTipoAtivacaoPartido = formularioAtual.getIpAtivacao().split(",");
                    for (int i = 1;i< textoIPTipoAtivacaoPartido.length; i++){
                        if (textoIPTipoAtivacaoPartido[i].equals("-")) {
                            spinIPTipoAtivacao.get(i-1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinIPTipoAtivacao.get(i-1).getAdapter().getCount(); a++) {
                                spinIPTipoAtivacao.get(i-1).setSelection(a);
                                if (spinIPTipoAtivacao.get(i-1).getSelectedItem().toString().equals(textoIPTipoAtivacaoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoIPCheckPartido = formularioAtual.getVinteEQuatro().split(",");
                    for (int i = 1;i< textoIPCheckPartido.length; i++) {
                        if(textoIPCheckPartido[i].equals("Sim")) {
                            checkIP24.get(i - 1).setChecked(true);
                            editIP24H.get(i-1).setEnabled(true);
                        }
                    }
                    String[] textoIPQuantidade24 = formularioAtual.getQuantidade24H().split(",");
                    for (int i = 1;i< textoIPQuantidade24.length; i++) {
                        editIP24H.get(i-1).setText(textoIPQuantidade24[i]);
                    }
                }
                observacaoIP.setText(formularioAtual.getObservacaoIP());


                //TRAFO

                if (formularioAtual.getAtivos().equals("Sim")) {
                    ativos.setChecked(true);
                    String[] textoAtivoChFacaPartido = formularioAtual.getChFaca().split(",");
                    for (int i = 1; i < textoAtivoChFacaPartido.length; i++) {
                        createAtivo();
                    }

                    String[] textoAtivoCheckTrifasicoPartido = formularioAtual.getChkTrafoTrifasico().split(",");
                    for (int i = 1; i < textoAtivoCheckTrifasicoPartido.length; i++) {
                        if (textoAtivoCheckTrifasicoPartido[i].equals("Sim")) {
                            checkAtivoTrifasico.get(i - 1).setChecked(true);
                            spinAtivoTrifasico.get(i - 1).setEnabled(true);
                        }
                    }

                    String[] textoAtivoCheckMonoPartido = formularioAtual.getChkTrafoMono().split(",");
                    for (int i = 1; i < textoAtivoCheckMonoPartido.length; i++) {
                        if (textoAtivoCheckMonoPartido[i].equals("Sim")) {
                            checkAtivoMono.get(i - 1).setChecked(true);
                            spinAtivoMono.get(i - 1).setEnabled(true);
                        }
                    }

                    String[] textoAtivoTrifasicoPartido = formularioAtual.getTrafoTrifasico().split(",");
                    for (int i = 1; i < textoAtivoTrifasicoPartido.length; i++) {
                        if (textoAtivoTrifasicoPartido[i].equals("-")) {
                            spinAtivoTrifasico.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoTrifasico.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoTrifasico.get(i - 1).setSelection(a);
                                if (spinAtivoTrifasico.get(i - 1).getSelectedItem().toString().equals(textoAtivoTrifasicoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoMonoPartido = formularioAtual.getTrafoMono().split(",");
                    for (int i = 1; i < textoAtivoMonoPartido.length; i++) {
                        if (textoAtivoMonoPartido[i].equals("-")) {
                            spinAtivoMono.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoMono.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoMono.get(i - 1).setSelection(a);
                                if (spinAtivoMono.get(i - 1).getSelectedItem().toString().equals(textoAtivoMonoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoReligador = formularioAtual.getReligador().split(",");
                    for (int i = 1; i < textoAtivoReligador.length; i++) {
                        if (textoAtivoReligador[i].equals("Sim")) {
                            checkAtivoReligador.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoAtivoMedicao = formularioAtual.getMedicao().split(",");
                    for (int i = 1; i < textoAtivoMedicao.length; i++) {
                        if (textoAtivoMedicao[i].equals("Sim")) {
                            checkAtivoMedicao.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoAtivoChFusivel = formularioAtual.getChFusivel().split(",");
                    for (int i = 1; i < textoAtivoChFusivel.length; i++) {
                        editAtivoChFusivel.get(i - 1).setText(textoAtivoChFusivel[i]);
                    }
                    String[] textoAtivoChFusivelReligador = formularioAtual.getChFusivelReligador().split(",");
                    for (int i = 1; i < textoAtivoChFusivelReligador.length; i++) {
                        editAtivoChFusivelReligador.get(i - 1).setText(textoAtivoChFusivelReligador[i]);
                    }

                    for (int i = 1; i < textoAtivoChFacaPartido.length; i++) {
                        if (textoAtivoChFacaPartido[i].equals("-")) {
                            spinAtivoChFaca.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoChFaca.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoChFaca.get(i - 1).setSelection(a);
                                if (spinAtivoChFaca.get(i - 1).getSelectedItem().toString().equals(textoAtivoChFacaPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoBancoPartido = formularioAtual.getBanco().split(",");
                    for (int i = 1; i < textoAtivoBancoPartido.length; i++) {
                        if (textoAtivoBancoPartido[i].equals("-")) {
                            spinAtivoBanco.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoBanco.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoBanco.get(i - 1).setSelection(a);
                                if (spinAtivoBanco.get(i - 1).getSelectedItem().toString().equals(textoAtivoBancoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoRamalPartido = formularioAtual.getRamalSubt().split(",");
                    for (int i = 1; i < textoAtivoRamalPartido.length; i++) {
                        if (textoAtivoRamalPartido[i].equals("-")) {
                            spinAtivoRamal.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoRamal.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoRamal.get(i - 1).setSelection(a);
                                if (spinAtivoRamal.get(i - 1).getSelectedItem().toString().equals(textoAtivoRamalPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoOutro = formularioAtual.getOutros().split(",");
                    for (int i = 1; i < textoAtivoOutro.length; i++) {
                        editAtivoOutro.get(i - 1).setText(textoAtivoOutro[i]);
                    }


                }
                observacaoAtivos.setText(formularioAtual.getObservacaoAtivos());

                //MUTUO
                if (formularioAtual.getMutuo().equals("Sim")) {
                    mutuo.setChecked(true);
                    quantidadeOcupantes.setText(formularioAtual.getQuantidadeOcupantes());
                    String[] textoMutuoNome = formularioAtual.getNome().split(",");
                    btnMutuoRemove.setVisibility(View.VISIBLE);
                    for (int i = 1; i < textoMutuoNome.length; i++) {
                        createMutuo();
                    }

                    for (int i = 1; i < textoMutuoNome.length; i++) {
                        editMutuoNome.get(i - 1).setText(textoMutuoNome[i]);
                    }

                    String[] textoMutuoQuantidade = formularioAtual.getQuantidadeCabos().split(",");
                    for (int i = 1; i < textoMutuoQuantidade.length; i++) {
                        editMutuoQuantidade.get(i - 1).setText(textoMutuoQuantidade[i]);
                    }

                    String[] textoMutuoTipoCabo = formularioAtual.getTipoCabo().split(",");
                    for (int i = 1; i < textoMutuoTipoCabo.length; i++) {
                        if (textoMutuoTipoCabo[i].equals("-")) {
                            spinMutuoTipoCabo.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoTipoCabo.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoTipoCabo.get(i - 1).setSelection(a);
                                if (spinMutuoTipoCabo.get(i - 1).getSelectedItem().toString().equals(textoMutuoTipoCabo[i])) {
                                    break;
                                }
                            }
                        }
                    }


                    String[] textoMutuoQuantidade2 = formularioAtual.getQuantidadeCabosdois().split(",");
                    for (int i = 1; i < textoMutuoQuantidade2.length; i++) {
                        editMutuoQuantidade2.get(i - 1).setText(textoMutuoQuantidade2[i]);
                    }

                    String[] textoMutuoTipoCabo2 = formularioAtual.getTipoCabodois().split(",");
                    for (int i = 1; i < textoMutuoTipoCabo2.length; i++) {
                        if (textoMutuoTipoCabo2[i].equals("-")) {
                            spinMutuoTipoCabo2.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoTipoCabo2.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoTipoCabo2.get(i - 1).setSelection(a);
                                if (spinMutuoTipoCabo2.get(i - 1).getSelectedItem().toString().equals(textoMutuoTipoCabo2[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoFinalidade = formularioAtual.getFinalidade().split(",");
                    for (int i = 1; i < textoMutuoFinalidade.length; i++) {
                        if (textoMutuoFinalidade[i].equals("-")) {
                            spinMutuoFinalidade.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoFinalidade.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoFinalidade.get(i - 1).setSelection(a);
                                if (spinMutuoFinalidade.get(i - 1).getSelectedItem().toString().equals(textoMutuoFinalidade[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoCeans = formularioAtual.getCeans().split(",");
                    for (int i = 1; i < textoMutuoCeans.length; i++) {
                        if (textoMutuoCeans[i].equals("-")) {
                            spinMutuoCeans.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoCeans.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoCeans.get(i - 1).setSelection(a);
                                if (spinMutuoCeans.get(i - 1).getSelectedItem().toString().equals(textoMutuoCeans[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoTar = formularioAtual.getTar().split(",");
                    for (int i = 1; i < textoMutuoTar.length; i++) {
                        if (textoMutuoTar[i].equals("-")) {
                            spinMutuoTar.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoTar.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoTar.get(i - 1).setSelection(a);
                                if (spinMutuoTar.get(i - 1).getSelectedItem().toString().equals(textoMutuoTar[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoReservaTec = formularioAtual.getReservaTec().split(",");
                    for (int i = 1; i < textoMutuoReservaTec.length; i++) {
                        if (textoMutuoReservaTec[i].equals("-")) {
                            spinMutuoReservaTec.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoReservaTec.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoReservaTec.get(i - 1).setSelection(a);
                                if (spinMutuoReservaTec.get(i - 1).getSelectedItem().toString().equals(textoMutuoReservaTec[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoBackbone = formularioAtual.getBackbone().split(",");
                    for (int i = 1; i < textoMutuoBackbone.length; i++) {
                        if (textoMutuoBackbone[i].equals("-")) {
                            spinMutuoBackbone.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoBackbone.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoBackbone.get(i - 1).setSelection(a);
                                if (spinMutuoBackbone.get(i - 1).getSelectedItem().toString().equals(textoMutuoBackbone[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoPlacaIdent = formularioAtual.getPlacaIdent().split(",");
                    for (int i = 1; i < textoMutuoPlacaIdent.length; i++) {
                        if (textoMutuoPlacaIdent[i].equals("Sim")) {
                            checkMutuoPlaca.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoMutuoDescida = formularioAtual.getDescidaCabos().split(",");
                    for (int i = 1; i < textoMutuoDescida.length; i++) {
                        if (textoMutuoDescida[i].equals("Sim")) {
                            checkMutuoDescida.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoMutuoIrregularidade = formularioAtual.getDescricaoIrregularidade().split(",");
                    for (int i = 1; i < textoMutuoIrregularidade.length; i++) {
                        editMutuoIrregularidade.get(i - 1).setText(textoMutuoIrregularidade[i]);
                    }


                }

                observacaoMutuo.setText((formularioAtual.getObservacaoMutuo()));

                if (formularioAtual.getVegetacao().equals("Sim")) {
                    chkVegetacao.setChecked(true);
                    dimensaoVegetacao.setVisibility(View.VISIBLE);
                    distaciaBaixa.setVisibility(View.VISIBLE);
                    distanciaMedia.setVisibility(View.VISIBLE);
                    estadoArvore.setVisibility(View.VISIBLE);
                    quedaArvore.setVisibility(View.VISIBLE);
                    localArvore.setVisibility(View.VISIBLE);
                    fotoVeg.setVisibility(View.VISIBLE);
                    btnFoto30.setVisibility(View.VISIBLE);
                    btnUpload30.setVisibility(View.VISIBLE);
                    relativeDimensaoVegetacao.setVisibility(View.VISIBLE);
                    relativeBaixa.setVisibility(View.VISIBLE);
                    relativeMedia.setVisibility(View.VISIBLE);
                    relativeEstadoArvore.setVisibility(View.VISIBLE);
                    relativeLocalArvore.setVisibility(View.VISIBLE);
                }

                if (formularioAtual.getDimensaoVegetacao().equals("-")) {
                    dimensaoVegetacao.setSelection(0);
                } else {
                    for (int i = 0; i < dimensaoVegetacao.getAdapter().getCount(); i++) {
                        dimensaoVegetacao.setSelection(i);
                        if (dimensaoVegetacao.getSelectedItem().toString().equals(formularioAtual.getDimensaoVegetacao())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getDistaciaBaixa().equals("-")) {
                    distaciaBaixa.setSelection(0);
                } else {
                    for (int i = 0; i < distaciaBaixa.getAdapter().getCount(); i++) {
                        distaciaBaixa.setSelection(i);
                        if (distaciaBaixa.getSelectedItem().toString().equals(formularioAtual.getDistaciaBaixa())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getDistanciaMedia().equals("-")) {
                    distanciaMedia.setSelection(0);
                } else {
                    for (int i = 0; i < distanciaMedia.getAdapter().getCount(); i++) {
                        distanciaMedia.setSelection(i);
                        if (dimensaoVegetacao.getSelectedItem().toString().equals(formularioAtual.getDistanciaMedia())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getEstadoArvore().equals("-")) {
                    estadoArvore.setSelection(0);
                } else {
                    for (int i = 0; i < estadoArvore.getAdapter().getCount(); i++) {
                        estadoArvore.setSelection(i);
                        if (estadoArvore.getSelectedItem().toString().equals(formularioAtual.getEstadoArvore())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getQuedaArvore().equals("Sim")) {
                    quedaArvore.setChecked(true);
                }
                if (formularioAtual.getLocalArvore().equals("-")) {
                    localArvore.setSelection(0);
                } else {
                    for (int i = 0; i < localArvore.getAdapter().getCount(); i++) {
                        localArvore.setSelection(i);
                        if (localArvore.getSelectedItem().toString().equals(formularioAtual.getLocalArvore())) {
                            break;
                        }
                    }
                }
                observacaoVegetacao.setText(formularioAtual.getObservacaoVegetacao());

            }
        });
    }

    //Função para recuperar todos os dados do poste selecionado
    private void mesmoPoste(final View root) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                root.findViewById(R.id.btnNovoPoste).setVisibility(View.GONE);
                contadorAr = formularioAtual.getContadorAr();
                contadorAt = formularioAtual.getContadorAt();
                contadorIp = formularioAtual.getContadorIp();
                codigo.setText(formularioAtual.getCodigo());
                controle = true;
                imgPathPan = formularioAtual.getCaminhoImagem();
                if (imgPathPan == null || imgPathPan.equals("")) {

                } else {
                    fotoPan.setImageBitmap(BitmapFactory.decodeFile(imgPathPan,options));
                    imagemPan = BitmapFactory.decodeFile(imgPathPan,options);
                }
                imgPathAlt = formularioAtual.getCaminhoImagem2();
                if (imgPathAlt == null || imgPathAlt.equals("")) {

                } else {
                    imgPathAlt = formularioAtual.getCaminhoImagem2();
                    fotoAlt.setImageBitmap(BitmapFactory.decodeFile(imgPathAlt,options));
                    imagemAlt = BitmapFactory.decodeFile(imgPathAlt,options);
                }

                imgPathQualidade = formularioAtual.getCaminhoImagem3();
                if (imgPathQualidade == null || imgPathQualidade.equals("")) {

                } else {
                    imgPathQualidade = formularioAtual.getCaminhoImagem3();
                    fotoQualidade.setImageBitmap(BitmapFactory.decodeFile(imgPathQualidade,options));
                    imagemQualidade = BitmapFactory.decodeFile(imgPathQualidade,options);
                }

                imgPathIP = formularioAtual.getCaminhoImagem4();
                if (imgPathIP == null || imgPathIP.equals("")) {

                } else {
                    imgPathIP = formularioAtual.getCaminhoImagem4();
                    fotoIP.setImageBitmap(BitmapFactory.decodeFile(imgPathIP,options));
                    imagemIP = BitmapFactory.decodeFile(imgPathIP,options);
                }

                imgPathAtivos = formularioAtual.getCaminhoImagem7();
                if (imgPathAtivos == null || imgPathAtivos.equals("")) {

                } else {
                    imgPathAtivos = formularioAtual.getCaminhoImagem7();
                    fotoAtivos.setImageBitmap(BitmapFactory.decodeFile(imgPathAtivos,options));
                    imagemAtivos = BitmapFactory.decodeFile(imgPathAtivos,options);
                }

                imgPathAtivos2 = formularioAtual.getCaminhoImagem8();
                if (imgPathAtivos2 == null || imgPathAtivos2.equals("")) {
                    imgPathAtivos2 = formularioAtual.getCaminhoImagem8();
                } else {
                    imgPathAtivos2 = formularioAtual.getCaminhoImagem8();
                    fotoAtivos2.setImageBitmap(BitmapFactory.decodeFile(imgPathAtivos2,options));
                    imagemAtivos2 = BitmapFactory.decodeFile(imgPathAtivos2,options);
                }

                imgPathMutuo = formularioAtual.getCaminhoImagem9();
                if (imgPathMutuo == null || imgPathMutuo.equals("")) {

                } else {
                    imgPathMutuo = formularioAtual.getCaminhoImagem9();
                    fotoMutuo.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo,options));
                    imagemMutuo = BitmapFactory.decodeFile(imgPathMutuo,options);
                }

                imgPathMutuo2 = formularioAtual.getCaminhoImagem10();
                if (imgPathMutuo2 == null || imgPathMutuo2.equals("")) {

                } else {
                    imgPathMutuo2 = formularioAtual.getCaminhoImagem10();
                    fotoMutuo2.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo2,options));
                    imagemMutuo2 = BitmapFactory.decodeFile(imgPathMutuo2,options);
                }

                imgPathMutuo3 = formularioAtual.getCaminhoImagem11();
                if (imgPathMutuo3 == null || imgPathMutuo3.equals("")) {

                } else {
                    imgPathMutuo3 = formularioAtual.getCaminhoImagem11();
                    fotoMutuo3.setImageBitmap(BitmapFactory.decodeFile(imgPathMutuo3,options));
                    imagemMutuo3 = BitmapFactory.decodeFile(imgPathMutuo3,options);
                }

                imgPathVeg = formularioAtual.getCaminhoImagem12();
                if (imgPathVeg == null || imgPathVeg.equals("")) {

                } else {
                    imgPathVeg = formularioAtual.getCaminhoImagem12();
                    fotoVeg.setImageBitmap(BitmapFactory.decodeFile(imgPathVeg,options));
                    imagemVeg = BitmapFactory.decodeFile(imgPathVeg,options);
                }
                endereco.setText(formularioAtual.getEndereco());
                urlFotoPan = Uri.parse(formularioAtual.getUrlImagem());
                urlFotoAlt = Uri.parse(formularioAtual.getUrlImagem2());
                urlFotoQualidade = Uri.parse(formularioAtual.getUrlImagem3());
                urlFotoIP = Uri.parse(formularioAtual.getUrlImagem4());
                urlFotoAtivos = Uri.parse(formularioAtual.getUrlImagem7());
                urlFotoAtivos2 = Uri.parse(formularioAtual.getUrlImagem8());
                urlFotoMutuo = Uri.parse(formularioAtual.getUrlImagem9());
                urlFotoMutuo2 = Uri.parse(formularioAtual.getUrlImagem10());
                urlFotoMutuo3 = Uri.parse(formularioAtual.getUrlImagem11());
                urlFotoVeg = Uri.parse(formularioAtual.getUrlImagem12());
                latitude.setText(formularioAtual.getLatitude());
                longitude.setText(formularioAtual.getLongitude());




                if (formularioAtual.getMunicipio().equals("-")) {
                    municipio.setSelection(0);
                } else {
                    for (int i = 0; i < municipio.getAdapter().getCount(); i++) {
                        municipio.setSelection(i);
                        if (municipio.getSelectedItem().toString().equals(formularioAtual.getMunicipio())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getAlturaCarga().equals("-")) {
                    alturaCarga.setSelection(0);
                } else {
                    for (int i = 0; i < alturaCarga.getAdapter().getCount(); i++) {
                        alturaCarga.setSelection(i);
                        if (alturaCarga.getSelectedItem().toString().equals(formularioAtual.getAlturaCarga())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getTipoPoste().equals("-")) {
                    tipoPoste.setSelection(0);
                } else {
                    for (int i = 0; i < tipoPoste.getAdapter().getCount(); i++) {
                        tipoPoste.setSelection(i);
                        if (tipoPoste.getSelectedItem().toString().equals(formularioAtual.getTipoPoste())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getNormal().equals("Sim")) {
                    normal.setChecked(true);
                } else {
                    normal.setChecked(false);
                    ferragemExposta.setEnabled(true);
                    fletido.setEnabled(true);
                    abalroado.setEnabled(true);
                    trincado.setEnabled(true);
                    danificado.setEnabled(true);
                }
                if (formularioAtual.getFerragemExposta().equals("Sim")) {
                    ferragemExposta.setChecked(true);
                }
                if (formularioAtual.getFletido().equals("Sim")) {
                    fletido.setChecked(true);
                }
                if (formularioAtual.getDanificado().equals("Sim")) {
                    danificado.setChecked(true);
                }
                if (formularioAtual.getAbalroado().equals("Sim")) {
                    abalroado.setChecked(true);
                }
                if (formularioAtual.getTrincado().equals("Sim")) {
                    trincado.setChecked(true);
                }
                observacaoFisicas.setText(formularioAtual.getObservacaoFisicas());


                //ILUMINAÇÃO

                if (formularioAtual.getIp().equals("Sim")) {
                    ip.setChecked(true);
                    String[] textoIPEstruturaPartido = formularioAtual.getIpEstrutura().split(",");
                    for (int i = 1;i< textoIPEstruturaPartido.length; i++) {
                        createIP();
                        btnIPLayoutRemove.setVisibility(View.VISIBLE);
                    }
                    for (int i = 1;i< textoIPEstruturaPartido.length; i++){
                        if (textoIPEstruturaPartido[i].equals("-")) {
                            spinIPEstrutura.get(i-1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinIPEstrutura.get(i-1).getAdapter().getCount(); a++) {
                                spinIPEstrutura.get(i-1).setSelection(a);
                                if (spinIPEstrutura.get(i-1).getSelectedItem().toString().equals(textoIPEstruturaPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }
                    String[] textoIPQuantidadePartido = formularioAtual.getQuantidadeLampada().split(",");
                    for (int i = 1;i< textoIPQuantidadePartido.length; i++) {
                        editIPQuantidadeLampada.get(i-1).setText(textoIPQuantidadePartido[i]);
                    }
                    String[] textoIPTipoPotPartido = formularioAtual.getTipoPot().split(",");
                    for (int i = 1;i< textoIPTipoPotPartido.length; i++){
                        if (textoIPTipoPotPartido[i].equals("-")) {
                            spinIPTipo.get(i-1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinIPTipo.get(i-1).getAdapter().getCount(); a++) {
                                spinIPTipo.get(i-1).setSelection(a);
                                if (spinIPTipo.get(i-1).getSelectedItem().toString().equals(textoIPTipoPotPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }
                    String[] textoIPTipoAtivacaoPartido = formularioAtual.getIpAtivacao().split(",");
                    for (int i = 1;i< textoIPTipoAtivacaoPartido.length; i++){
                        if (textoIPTipoAtivacaoPartido[i].equals("-")) {
                            spinIPTipoAtivacao.get(i-1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinIPTipoAtivacao.get(i-1).getAdapter().getCount(); a++) {
                                spinIPTipoAtivacao.get(i-1).setSelection(a);
                                if (spinIPTipoAtivacao.get(i-1).getSelectedItem().toString().equals(textoIPTipoAtivacaoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoIPCheckPartido = formularioAtual.getVinteEQuatro().split(",");
                    for (int i = 1;i< textoIPCheckPartido.length; i++) {
                        if(textoIPCheckPartido[i].equals("Sim")) {
                            checkIP24.get(i - 1).setChecked(true);
                            editIP24H.get(i-1).setEnabled(true);
                        }
                    }
                    String[] textoIPQuantidade24 = formularioAtual.getQuantidade24H().split(",");
                    for (int i = 1;i< textoIPQuantidade24.length; i++) {
                        editIP24H.get(i-1).setText(textoIPQuantidade24[i]);
                    }
                }
                observacaoIP.setText(formularioAtual.getObservacaoIP());


                //TRAFO

                if (formularioAtual.getAtivos().equals("Sim")) {
                    ativos.setChecked(true);
                    String[] textoAtivoChFacaPartido = formularioAtual.getChFaca().split(",");
                    btnAtivoRemove.setVisibility(View.VISIBLE);
                    for (int i = 1; i < textoAtivoChFacaPartido.length; i++) {
                        createAtivo();
                    }

                    String[] textoAtivoCheckTrifasicoPartido = formularioAtual.getChkTrafoTrifasico().split(",");
                    for (int i = 1; i < textoAtivoCheckTrifasicoPartido.length; i++) {
                        if (textoAtivoCheckTrifasicoPartido[i].equals("Sim")) {
                            checkAtivoTrifasico.get(i - 1).setChecked(true);
                            spinAtivoTrifasico.get(i - 1).setEnabled(true);
                        }
                    }

                    String[] textoAtivoCheckMonoPartido = formularioAtual.getChkTrafoMono().split(",");
                    for (int i = 1; i < textoAtivoCheckMonoPartido.length; i++) {
                        if (textoAtivoCheckMonoPartido[i].equals("Sim")) {
                            checkAtivoMono.get(i - 1).setChecked(true);
                            spinAtivoMono.get(i - 1).setEnabled(true);
                        }
                    }

                    String[] textoAtivoTrifasicoPartido = formularioAtual.getTrafoTrifasico().split(",");
                    for (int i = 1; i < textoAtivoTrifasicoPartido.length; i++) {
                        if (textoAtivoTrifasicoPartido[i].equals("-")) {
                            spinAtivoTrifasico.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoTrifasico.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoTrifasico.get(i - 1).setSelection(a);
                                if (spinAtivoTrifasico.get(i - 1).getSelectedItem().toString().equals(textoAtivoTrifasicoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoMonoPartido = formularioAtual.getTrafoMono().split(",");
                    for (int i = 1; i < textoAtivoMonoPartido.length; i++) {
                        if (textoAtivoMonoPartido[i].equals("-")) {
                            spinAtivoMono.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoMono.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoMono.get(i - 1).setSelection(a);
                                if (spinAtivoMono.get(i - 1).getSelectedItem().toString().equals(textoAtivoMonoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoReligador = formularioAtual.getReligador().split(",");
                    for (int i = 1; i < textoAtivoReligador.length; i++) {
                        if (textoAtivoReligador[i].equals("Sim")) {
                            checkAtivoReligador.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoAtivoMedicao = formularioAtual.getMedicao().split(",");
                    for (int i = 1; i < textoAtivoMedicao.length; i++) {
                        if (textoAtivoMedicao[i].equals("Sim")) {
                            checkAtivoMedicao.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoAtivoChFusivel = formularioAtual.getChFusivel().split(",");
                    for (int i = 1; i < textoAtivoChFusivel.length; i++) {
                        editAtivoChFusivel.get(i - 1).setText(textoAtivoChFusivel[i]);
                    }
                    String[] textoAtivoChFusivelReligador = formularioAtual.getChFusivelReligador().split(",");
                    for (int i = 1; i < textoAtivoChFusivelReligador.length; i++) {
                        editAtivoChFusivelReligador.get(i - 1).setText(textoAtivoChFusivelReligador[i]);
                    }

                    for (int i = 1; i < textoAtivoChFacaPartido.length; i++) {
                        if (textoAtivoChFacaPartido[i].equals("-")) {
                            spinAtivoChFaca.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoChFaca.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoChFaca.get(i - 1).setSelection(a);
                                if (spinAtivoChFaca.get(i - 1).getSelectedItem().toString().equals(textoAtivoChFacaPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoBancoPartido = formularioAtual.getBanco().split(",");
                    for (int i = 1; i < textoAtivoBancoPartido.length; i++) {
                        if (textoAtivoBancoPartido[i].equals("-")) {
                            spinAtivoBanco.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoBanco.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoBanco.get(i - 1).setSelection(a);
                                if (spinAtivoBanco.get(i - 1).getSelectedItem().toString().equals(textoAtivoBancoPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoRamalPartido = formularioAtual.getRamalSubt().split(",");
                    for (int i = 1; i < textoAtivoRamalPartido.length; i++) {
                        if (textoAtivoRamalPartido[i].equals("-")) {
                            spinAtivoRamal.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinAtivoRamal.get(i - 1).getAdapter().getCount(); a++) {
                                spinAtivoRamal.get(i - 1).setSelection(a);
                                if (spinAtivoRamal.get(i - 1).getSelectedItem().toString().equals(textoAtivoRamalPartido[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoAtivoOutro = formularioAtual.getOutros().split(",");
                    for (int i = 1; i < textoAtivoOutro.length; i++) {
                        editAtivoOutro.get(i - 1).setText(textoAtivoOutro[i]);
                    }


                }
                observacaoAtivos.setText(formularioAtual.getObservacaoAtivos());

                //MUTUO
                if (formularioAtual.getMutuo().equals("Sim")) {
                    mutuo.setChecked(true);
                    quantidadeOcupantes.setText(formularioAtual.getQuantidadeOcupantes());
                    Log.i("TESTES", formularioAtual.getQuantidadeOcupantes());
                    String[] textoMutuoNome = formularioAtual.getNome().split(",");
                    Log.i("TESTES",""+textoMutuoNome.length);
                    btnMutuoRemove.setVisibility(View.VISIBLE);
                    for (int i = 1; i < textoMutuoNome.length; i++) {
                        createMutuo();
                    }

                    for (int i = 1; i < textoMutuoNome.length; i++) {
                        editMutuoNome.get(i - 1).setText(textoMutuoNome[i]);
                    }
                    Log.i("TESTES", formularioAtual.getQuantidadeCabos());
                    String[] textoMutuoQuantidade = formularioAtual.getQuantidadeCabos().split(",");
                    Log.i("TESTES", ""+textoMutuoQuantidade.length);
                    for (int i = 1; i < textoMutuoQuantidade.length; i++) {
                        editMutuoQuantidade.get(i - 1).setText(textoMutuoQuantidade[i]);
                    }

                    String[] textoMutuoTipoCabo = formularioAtual.getTipoCabo().split(",");
                    for (int i = 1; i < textoMutuoTipoCabo.length; i++) {
                        if (textoMutuoTipoCabo[i].equals("-")) {
                            spinMutuoTipoCabo.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoTipoCabo.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoTipoCabo.get(i - 1).setSelection(a);
                                if (spinMutuoTipoCabo.get(i - 1).getSelectedItem().toString().equals(textoMutuoTipoCabo[i])) {
                                    break;
                                }
                            }
                        }
                    }


                    String[] textoMutuoQuantidade2 = formularioAtual.getQuantidadeCabosdois().split(",");
                    for (int i = 1; i < textoMutuoQuantidade2.length; i++) {
                        editMutuoQuantidade2.get(i - 1).setText(textoMutuoQuantidade2[i]);
                    }

                    String[] textoMutuoTipoCabo2 = formularioAtual.getTipoCabodois().split(",");
                    for (int i = 1; i < textoMutuoTipoCabo2.length; i++) {
                        if (textoMutuoTipoCabo2[i].equals("-")) {
                            spinMutuoTipoCabo2.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoTipoCabo2.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoTipoCabo2.get(i - 1).setSelection(a);
                                if (spinMutuoTipoCabo2.get(i - 1).getSelectedItem().toString().equals(textoMutuoTipoCabo2[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoFinalidade = formularioAtual.getFinalidade().split(",");
                    for (int i = 1; i < textoMutuoFinalidade.length; i++) {
                        if (textoMutuoFinalidade[i].equals("-")) {
                            spinMutuoFinalidade.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoFinalidade.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoFinalidade.get(i - 1).setSelection(a);
                                if (spinMutuoFinalidade.get(i - 1).getSelectedItem().toString().equals(textoMutuoFinalidade[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoCeans = formularioAtual.getCeans().split(",");
                    for (int i = 1; i < textoMutuoCeans.length; i++) {
                        if (textoMutuoCeans[i].equals("-")) {
                            spinMutuoCeans.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoCeans.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoCeans.get(i - 1).setSelection(a);
                                if (spinMutuoCeans.get(i - 1).getSelectedItem().toString().equals(textoMutuoCeans[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoTar = formularioAtual.getTar().split(",");
                    for (int i = 1; i < textoMutuoTar.length; i++) {
                        if (textoMutuoTar[i].equals("-")) {
                            spinMutuoTar.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoTar.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoTar.get(i - 1).setSelection(a);
                                if (spinMutuoTar.get(i - 1).getSelectedItem().toString().equals(textoMutuoTar[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoReservaTec = formularioAtual.getReservaTec().split(",");
                    for (int i = 1; i < textoMutuoReservaTec.length; i++) {
                        if (textoMutuoReservaTec[i].equals("-")) {
                            spinMutuoReservaTec.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoReservaTec.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoReservaTec.get(i - 1).setSelection(a);
                                if (spinMutuoReservaTec.get(i - 1).getSelectedItem().toString().equals(textoMutuoReservaTec[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoBackbone = formularioAtual.getBackbone().split(",");
                    for (int i = 1; i < textoMutuoBackbone.length; i++) {
                        if (textoMutuoBackbone[i].equals("-")) {
                            spinMutuoBackbone.get(i - 1).setSelection(0);
                        } else {
                            for (int a = 0; a < spinMutuoBackbone.get(i - 1).getAdapter().getCount(); a++) {
                                spinMutuoBackbone.get(i - 1).setSelection(a);
                                if (spinMutuoBackbone.get(i - 1).getSelectedItem().toString().equals(textoMutuoBackbone[i])) {
                                    break;
                                }
                            }
                        }
                    }

                    String[] textoMutuoPlacaIdent = formularioAtual.getPlacaIdent().split(",");
                    for (int i = 1; i < textoMutuoPlacaIdent.length; i++) {
                        if (textoMutuoPlacaIdent[i].equals("Sim")) {
                            checkMutuoPlaca.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoMutuoDescida = formularioAtual.getDescidaCabos().split(",");
                    for (int i = 1; i < textoMutuoDescida.length; i++) {
                        if (textoMutuoDescida[i].equals("Sim")) {
                            checkMutuoDescida.get(i - 1).setChecked(true);

                        }
                    }

                    String[] textoMutuoIrregularidade = formularioAtual.getDescricaoIrregularidade().split(",");
                    for (int i = 1; i < textoMutuoIrregularidade.length; i++) {
                        editMutuoIrregularidade.get(i - 1).setText(textoMutuoIrregularidade[i]);
                    }


                }

                observacaoMutuo.setText((formularioAtual.getObservacaoMutuo()));

                if (formularioAtual.getVegetacao().equals("Sim")) {
                    chkVegetacao.setChecked(true);
                    dimensaoVegetacao.setVisibility(View.VISIBLE);
                    distaciaBaixa.setVisibility(View.VISIBLE);
                    distanciaMedia.setVisibility(View.VISIBLE);
                    estadoArvore.setVisibility(View.VISIBLE);
                    quedaArvore.setVisibility(View.VISIBLE);
                    localArvore.setVisibility(View.VISIBLE);
                    fotoVeg.setVisibility(View.VISIBLE);
                    btnFoto30.setVisibility(View.VISIBLE);
                    btnUpload30.setVisibility(View.VISIBLE);
                    relativeDimensaoVegetacao.setVisibility(View.VISIBLE);
                    relativeBaixa.setVisibility(View.VISIBLE);
                    relativeMedia.setVisibility(View.VISIBLE);
                    relativeEstadoArvore.setVisibility(View.VISIBLE);
                    relativeLocalArvore.setVisibility(View.VISIBLE);
                }

                if (formularioAtual.getDimensaoVegetacao().equals("-")) {
                    dimensaoVegetacao.setSelection(0);
                } else {
                    for (int i = 0; i < dimensaoVegetacao.getAdapter().getCount(); i++) {
                        dimensaoVegetacao.setSelection(i);
                        if (dimensaoVegetacao.getSelectedItem().toString().equals(formularioAtual.getDimensaoVegetacao())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getDistaciaBaixa().equals("-")) {
                    distaciaBaixa.setSelection(0);
                } else {
                    for (int i = 0; i < distaciaBaixa.getAdapter().getCount(); i++) {
                        distaciaBaixa.setSelection(i);
                        if (distaciaBaixa.getSelectedItem().toString().equals(formularioAtual.getDistaciaBaixa())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getDistanciaMedia().equals("-")) {
                    distanciaMedia.setSelection(0);
                } else {
                    for (int i = 0; i < distanciaMedia.getAdapter().getCount(); i++) {
                        distanciaMedia.setSelection(i);
                        if (dimensaoVegetacao.getSelectedItem().toString().equals(formularioAtual.getDistanciaMedia())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getEstadoArvore().equals("-")) {
                    estadoArvore.setSelection(0);
                } else {
                    for (int i = 0; i < estadoArvore.getAdapter().getCount(); i++) {
                        estadoArvore.setSelection(i);
                        if (estadoArvore.getSelectedItem().toString().equals(formularioAtual.getEstadoArvore())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getQuedaArvore().equals("Sim")) {
                    quedaArvore.setChecked(true);
                }
                if (formularioAtual.getLocalArvore().equals("-")) {
                    localArvore.setSelection(0);
                } else {
                    for (int i = 0; i < localArvore.getAdapter().getCount(); i++) {
                        localArvore.setSelection(i);
                        if (localArvore.getSelectedItem().toString().equals(formularioAtual.getLocalArvore())) {
                            break;
                        }
                    }
                }
                observacaoVegetacao.setText(formularioAtual.getObservacaoVegetacao());
            }
        });

    }
    //Função para criar um thread assíncrono para a função novoPoste
    class NovoPosteTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialogAsync = new ProgressDialog(requireContext(), R.style.LightDialogTheme);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogAsync.setMessage("Carregando dados..."); // Setting Message
            progressDialogAsync.setTitle("Por favor Espere"); // Setting Title
            progressDialogAsync.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialogAsync.show(); // Display Progress Dialog
            progressDialogAsync.setCancelable(false);
            progressDialogAsync.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("ENTREI", "ENTREI AQUI");
            ThreadNovoPoste threadNovoPoste = new ThreadNovoPoste(root);
            threadNovoPoste.start();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialogAsync.dismiss();
        }
    }

    //Função para criar um thread assíncrono para a função mesmoPoste
    class MesmoPosteTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialogAsync = new ProgressDialog(requireContext(), R.style.LightDialogTheme);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogAsync.setMessage("Carregando dados..."); // Setting Message
            progressDialogAsync.setTitle("Por favor Espere"); // Setting Title
            progressDialogAsync.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialogAsync.show(); // Display Progress Dialog
            progressDialogAsync.setCancelable(false);
            progressDialogAsync.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ThreadMesmoPoste threadMesmoPoste = new ThreadMesmoPoste(root);
            threadMesmoPoste.start();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    progressDialogAsync.dismiss();
                }

            }, 1);
        }
    }

    //Função utilizada para pegar os dados, e salvar no banco de dados interno do android.
    public void getFormulario(Boolean novoPosteEstado) throws Throwable {
        Log.i("ENTREI","AQUI");
        progressDialog = new ProgressDialog(requireContext(), R.style.LightDialogTheme);
        progressDialog.setMessage("Salvando..."); // Setting Message
        progressDialog.setTitle("Por favor Espere"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        if ((imgPathPan == null) && (imgPathAlt == null) && (imgPathQualidade == null)) {
            StyleableToast.makeText(requireActivity().getApplicationContext(), "Preencha pelo menos uma foto", R.style.ToastWarning).show();
            progressDialog.dismiss();
        } else if (codigo.getText().toString().equals("") || codigo == null) {
            StyleableToast.makeText(requireActivity().getApplicationContext(), "Preencha o campo de código", R.style.ToastWarning).show();
            progressDialog.dismiss();
        } else {
            FormularioDAO formularioDAO = new FormularioDAO(requireActivity().getApplicationContext());
            final Formulario formulario = new Formulario();
            formulario.setCodigo(Objects.requireNonNull(codigo.getText()).toString());
            if (imgPathPan != null) {
                formulario.setCaminhoImagem(imgPathPan);
            } else {
                formulario.setCaminhoImagem("");
            }
            //Panoramica, Alt/Res, Qualidade

            if (imgPathAlt != null) {
                formulario.setCaminhoImagem2(imgPathAlt);
            } else {
                formulario.setCaminhoImagem2("");
            }
            if (imgPathQualidade != null) {
                formulario.setCaminhoImagem3(imgPathQualidade);
            } else {
                formulario.setCaminhoImagem3("");
            }
            if (imgPathIP != null) {
                formulario.setCaminhoImagem4(imgPathIP);
            } else {
                formulario.setCaminhoImagem4("");
            }
            if (imgPathAtivos != null) {
                formulario.setCaminhoImagem7(imgPathAtivos);
            } else {
                formulario.setCaminhoImagem7("");
            }
            if (imgPathAtivos2 != null) {
                formulario.setCaminhoImagem8(imgPathAtivos2);
            } else {
                formulario.setCaminhoImagem8("");
            }
            if (imgPathMutuo != null) {
                formulario.setCaminhoImagem9(imgPathMutuo);
            } else {
                formulario.setCaminhoImagem9("");
            }
            if (imgPathMutuo2 != null) {
                formulario.setCaminhoImagem10(imgPathMutuo2);
            } else {
                formulario.setCaminhoImagem10("");
            }
            if (imgPathMutuo3 != null) {
                formulario.setCaminhoImagem11(imgPathMutuo3);
            } else {
                formulario.setCaminhoImagem11("");
            }
            if (imgPathVeg != null) {
                formulario.setCaminhoImagem12(imgPathVeg);
            } else {
                formulario.setCaminhoImagem12("");
            }
            if ((urlFotoPan == null) || ((urlFotoPan.toString()).equals(""))) {
                formulario.setUrlImagem("");
                formulario.setColor(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem(urlFotoPan.toString());
                formulario.setColor(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoAlt == null) || ((urlFotoAlt.toString()).equals(""))) {
                formulario.setUrlImagem2("");
                formulario.setColor2(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem2(urlFotoAlt.toString());
                formulario.setColor2(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoQualidade == null) || ((urlFotoQualidade.toString()).equals(""))) {
                formulario.setUrlImagem3("");
                formulario.setColor3(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem3(urlFotoQualidade.toString());
                formulario.setColor3(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoIP == null) || ((urlFotoIP.toString()).equals(""))) {
                formulario.setUrlImagem4("");
                formulario.setColor4(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem4(urlFotoIP.toString());
                formulario.setColor4(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoAtivos == null) || ((urlFotoAtivos.toString()).equals(""))) {
                formulario.setUrlImagem7("");
                formulario.setColor7(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem7(urlFotoAtivos.toString());
                formulario.setColor7(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoAtivos2 == null) || ((urlFotoAtivos2.toString()).equals(""))) {
                formulario.setUrlImagem8("");
                formulario.setColor8(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem8(urlFotoAtivos2.toString());
                formulario.setColor8(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoMutuo == null) || ((urlFotoMutuo.toString()).equals(""))) {
                formulario.setUrlImagem9("");
                formulario.setColor9(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem9(urlFotoMutuo.toString());
                formulario.setColor9(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoMutuo2 == null) || ((urlFotoMutuo2.toString()).equals(""))) {
                formulario.setUrlImagem10("");
                formulario.setColor10(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem10(urlFotoMutuo2.toString());
                formulario.setColor10(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoMutuo3 == null) || ((urlFotoMutuo3.toString()).equals(""))) {
                formulario.setUrlImagem11("");
                formulario.setColor11(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem11(urlFotoMutuo3.toString());
                formulario.setColor11(String.valueOf(R.color.colorPrimary));
            }
            if ((urlFotoVeg == null) || ((urlFotoVeg.toString()).equals(""))) {
                formulario.setUrlImagem12("");
                formulario.setColor12(String.valueOf(R.color.design_default_color_error));
            } else {
                formulario.setUrlImagem12(urlFotoVeg.toString());
                formulario.setColor12(String.valueOf(R.color.colorPrimary));
            }


            //LOCALIZAÇÂO

            formulario.setEndereco(Objects.requireNonNull(endereco.getText()).toString());
            setLista(formulario, municipio, "municipio");
            formulario.setLatitude(Objects.requireNonNull(latitude.getText()).toString());
            formulario.setLongitude(Objects.requireNonNull(longitude.getText()).toString());
            setLista(formulario, alturaCarga, "alturaCarga");


            //CARACTERISTICAS FÍSICAS

            setLista(formulario, tipoPoste, "tipoPoste");
            if (normal.isChecked()) {
                formulario.setNormal("Sim");
            } else {
                formulario.setNormal("Não");
            }
            if (ferragemExposta.isChecked()) {
                formulario.setFerragemExposta("Sim");
            } else {
                formulario.setFerragemExposta("Não");
            }
            if (fletido.isChecked()) {
                formulario.setFletido("Sim");
            } else {
                formulario.setFletido("Não");
            }
            if (danificado.isChecked()) {
                formulario.setDanificado("Sim");
            } else {
                formulario.setDanificado("Não");
            }
            if (abalroado.isChecked()) {
                formulario.setAbalroado("Sim");
            } else {
                formulario.setAbalroado("Não");
            }
            if (trincado.isChecked()) {
                formulario.setTrincado("Sim");
            } else {
                formulario.setTrincado("Não");
            }
            formulario.setObservacaoFisicas(Objects.requireNonNull(observacaoFisicas.getText()).toString());


            //ILUMINAÇÃO

            if (ip.isChecked()) {
                formulario.setIp("Sim");
            } else {
                formulario.setIp("Não");
                formulario.setColor4(String.valueOf(R.color.colorPrimary));
            }
            String textoIpEstrutura ="";
            for(int t=0;t<spinIPEstrutura.size();t++){
                setLista(formulario, spinIPEstrutura.get(t), "ipEstrutura");
                textoIpEstrutura = textoIpEstrutura + "," + formulario.getIpEstrutura();
            }
            formulario.setIpEstrutura(textoIpEstrutura);
            String textoIpQuantidadeLampada ="";
            for(int t=0;t<editIPQuantidadeLampada.size();t++){
                formulario.setQuantidadeLampada(Objects.requireNonNull(editIPQuantidadeLampada.get(t).getText().toString()));
                textoIpQuantidadeLampada = textoIpQuantidadeLampada + "," + formulario.getQuantidadeLampada();
            }
            formulario.setQuantidadeLampada(textoIpQuantidadeLampada);

            String textoIpTipoPot ="";
            for(int t=0;t<spinIPTipo.size();t++){
                setLista(formulario, spinIPTipo.get(t), "tipoPot");
                textoIpTipoPot = textoIpTipoPot + "," + formulario.getTipoPot();
            }
            formulario.setTipoPot(textoIpTipoPot);
            String textoIpPotReator ="";
            for(int t=0;t<editIPPot.size();t++){
                formulario.setPotReator(Objects.requireNonNull(editIPPot.get(t).getText().toString()));
                textoIpPotReator = textoIpPotReator + "," + formulario.getPotReator();
            }
            formulario.setPotReator(textoIpPotReator);

            String textoIpAtivacao ="";
            for(int t=0;t<spinIPTipoAtivacao.size();t++){
                setLista(formulario, spinIPTipoAtivacao.get(t), "ipAtivacao");
                textoIpAtivacao = textoIpAtivacao + "," + formulario.getIpAtivacao();
            }
            formulario.setIpAtivacao(textoIpAtivacao);
            String textoIpCheck ="";
            for(int t=0;t<checkIP24.size();t++){
                if (checkIP24.get(t).isChecked()) {
                    formulario.setVinteEQuatro("Sim");
                } else {
                    formulario.setVinteEQuatro("Não");
                }
                textoIpCheck = textoIpCheck + "," + formulario.getVinteEQuatro();
            }
            formulario.setVinteEQuatro(textoIpCheck);
            String textoIpQuantidade24H ="";
            for(int t=0;t<editIP24H.size();t++){
                formulario.setQuantidade24H(Objects.requireNonNull(editIP24H.get(t).getText().toString()));
                textoIpQuantidade24H = textoIpQuantidade24H + "," + formulario.getQuantidade24H();
            }
            formulario.setQuantidade24H(Objects.requireNonNull(textoIpQuantidade24H));
            formulario.setObservacaoIP(Objects.requireNonNull(observacaoIP.getText().toString()));


            //TRAFO

            if (ativos.isChecked()) {
                formulario.setAtivos("Sim");
            } else {
                formulario.setAtivos("Não");
                formulario.setColor7(String.valueOf(R.color.colorPrimary));
                formulario.setColor8(String.valueOf(R.color.colorPrimary));
            }

            String textoAtivoChkTrifasico ="";
            for(int t=0;t<checkAtivoTrifasico.size();t++){
                if (checkAtivoTrifasico.get(t).isChecked()) {
                    formulario.setChkTrafoTrifasico("Sim");
                } else {
                    formulario.setChkTrafoTrifasico("Não");
                }
                textoAtivoChkTrifasico = textoAtivoChkTrifasico + "," + formulario.getChkTrafoTrifasico();
            }
            formulario.setChkTrafoTrifasico(textoAtivoChkTrifasico);

            String textoAtivoChkMono ="";
            for(int t=0;t<checkAtivoMono.size();t++){
                if (checkAtivoMono.get(t).isChecked()) {
                    formulario.setChkTrafoMono("Sim");
                } else {
                    formulario.setChkTrafoMono("Não");
                }
                textoAtivoChkMono = textoAtivoChkMono + "," + formulario.getChkTrafoMono();
            }
            formulario.setChkTrafoMono(textoAtivoChkMono);
            String textoAtivoTrifasico ="";
            for(int t=0;t<spinAtivoTrifasico.size();t++){
                setLista(formulario, spinAtivoTrifasico.get(t), "trafoTrifasico");
                textoAtivoTrifasico = textoAtivoTrifasico + "," + formulario.getTrafoTrifasico();
            }
            formulario.setTrafoTrifasico(textoAtivoTrifasico);
            String textoAtivoMono ="";
            for(int t=0;t<spinAtivoMono.size();t++){
                setLista(formulario, spinAtivoMono.get(t), "trafoMono");
                textoAtivoMono = textoAtivoMono + "," + formulario.getTrafoMono();
            }
            formulario.setTrafoMono(textoAtivoMono);

            String textoAtivoReligador ="";
            for(int t=0;t<checkAtivoReligador.size();t++){
                if (checkAtivoReligador.get(t).isChecked()) {
                    formulario.setReligador("Sim");
                } else {
                    formulario.setReligador("Não");
                }
                textoAtivoReligador = textoAtivoReligador + "," + formulario.getReligador();
            }
            formulario.setReligador(textoAtivoReligador);

            String textoAtivoMedicao ="";
            for(int t=0;t<checkAtivoMedicao.size();t++){
                if (checkAtivoMedicao.get(t).isChecked()) {
                    formulario.setMedicao("Sim");
                } else {
                    formulario.setMedicao("Não");
                }
                textoAtivoMedicao = textoAtivoMedicao + "," + formulario.getMedicao();
            }
            formulario.setMedicao(textoAtivoMedicao);

            String textoAtivoChFusivel ="";
            for(int t=0;t<editAtivoChFusivel.size();t++){
                formulario.setChFusivel(Objects.requireNonNull(editAtivoChFusivel.get(t).getText().toString()));
                textoAtivoChFusivel  = textoAtivoChFusivel  + "," + formulario.getChFusivel();
            }
            formulario.setChFusivel(Objects.requireNonNull(textoAtivoChFusivel));

            String textoAtivoChFusivelReligador ="";
            for(int t=0;t<editAtivoChFusivelReligador.size();t++){
                formulario.setChFusivelReligador(Objects.requireNonNull(editAtivoChFusivelReligador.get(t).getText().toString()));
                textoAtivoChFusivelReligador  = textoAtivoChFusivelReligador  + "," + formulario.getChFusivelReligador();
            }
            formulario.setChFusivelReligador(Objects.requireNonNull(textoAtivoChFusivelReligador));

            String textoAtivoChFaca ="";
            for(int t=0;t<spinAtivoChFaca.size();t++){
                setLista(formulario, spinAtivoChFaca.get(t), "chFaca");
                textoAtivoChFaca = textoAtivoChFaca + "," + formulario.getChFaca();
            }
            formulario.setChFaca(textoAtivoChFaca);

            String textoAtivoBanco ="";
            for(int t=0;t<spinAtivoBanco.size();t++){
                setLista(formulario, spinAtivoBanco.get(t), "chBanco");
                textoAtivoBanco = textoAtivoBanco + "," + formulario.getBanco();
            }
            formulario.setBanco(textoAtivoBanco);

            String textoAtivoRamalSubt ="";
            for(int t=0;t<spinAtivoRamal.size();t++){
                setLista(formulario, spinAtivoRamal.get(t), "ramalSubt");
                textoAtivoRamalSubt = textoAtivoRamalSubt + "," + formulario.getRamalSubt();
            }
            formulario.setRamalSubt(textoAtivoRamalSubt);

            String textoAtivoOutros ="";
            for(int t=0;t<editAtivoOutro.size();t++){
                formulario.setOutros(Objects.requireNonNull(editAtivoOutro.get(t).getText().toString()));
                textoAtivoOutros  = textoAtivoOutros  + "," + formulario.getOutros();
            }
            formulario.setOutros(Objects.requireNonNull(textoAtivoOutros));
            formulario.setObservacaoAtivos(Objects.requireNonNull(observacaoAtivos.getText()).toString());

            //MUTUO
            if (mutuo.isChecked()) {
                formulario.setMutuo("Sim");

            } else {
                formulario.setMutuo("Não");

            }
            formulario.setQuantidadeOcupantes(Objects.requireNonNull(editMutuoNome.size()).toString());

            String textoMutuoNome ="";
            for(int t=0;t<editMutuoNome.size();t++){
                formulario.setNome(Objects.requireNonNull(editMutuoNome.get(t).getText().toString()));
                textoMutuoNome  = textoMutuoNome  + "," + formulario.getNome();
            }
            formulario.setNome(Objects.requireNonNull(textoMutuoNome));

            String textoMutuoQuantidadeCabo ="";
            for(int t=0;t<editMutuoQuantidade.size();t++){
                formulario.setQuantidadeCabos(Objects.requireNonNull(editMutuoQuantidade.get(t).getText().toString()));
                textoMutuoQuantidadeCabo  = textoMutuoQuantidadeCabo  + "," + formulario.getQuantidadeCabos();
            }
            formulario.setQuantidadeCabos(Objects.requireNonNull(textoMutuoQuantidadeCabo));

            String textoMutuoTipoCabo ="";
            for(int t=0;t<spinMutuoTipoCabo.size();t++){
                setLista(formulario, spinMutuoTipoCabo.get(t), "tipoCabo");
                textoMutuoTipoCabo = textoMutuoTipoCabo + "," + formulario.getTipoCabo();
            }
            formulario.setTipoCabo(textoMutuoTipoCabo);

            String textoMutuoQuantidadeCabo2 ="";
            for(int t=0;t<editMutuoQuantidade2.size();t++){
                formulario.setQuantidadeCabosdois(Objects.requireNonNull(editMutuoQuantidade2.get(t).getText().toString()));
                textoMutuoQuantidadeCabo2  = textoMutuoQuantidadeCabo2  + "," + formulario.getQuantidadeCabosdois();
            }
            formulario.setQuantidadeCabosdois(Objects.requireNonNull(textoMutuoQuantidadeCabo2));

            String textoMutuoTipoCabo2 ="";
            for(int t=0;t<spinMutuoTipoCabo2.size();t++){
                setLista(formulario, spinMutuoTipoCabo2.get(t), "tipoCabodois");
                textoMutuoTipoCabo2 = textoMutuoTipoCabo2 + "," + formulario.getTipoCabodois();
            }
            formulario.setTipoCabodois(textoMutuoTipoCabo2);

            String textoMutuoFinalidade ="";
            for(int t=0;t<spinMutuoFinalidade.size();t++){
                setLista(formulario, spinMutuoFinalidade.get(t), "finalidade");
                textoMutuoFinalidade = textoMutuoFinalidade + "," + formulario.getFinalidade();
            }
            formulario.setFinalidade(textoMutuoFinalidade);

            String textoMutuoCeans ="";
            for(int t=0;t<spinMutuoCeans.size();t++){
                setLista(formulario, spinMutuoCeans.get(t), "ceans");
                textoMutuoCeans = textoMutuoCeans + "," + formulario.getCeans();
            }
            formulario.setCeans(textoMutuoCeans);

            String textoMutuoTar ="";
            for(int t=0;t<spinMutuoTar.size();t++){
                setLista(formulario, spinMutuoTar.get(t), "tar");
                textoMutuoTar = textoMutuoTar + "," + formulario.getTar();
            }
            formulario.setTar(textoMutuoTar);

            String textoMutuoReservaTec ="";
            for(int t=0;t<spinMutuoReservaTec.size();t++){
                setLista(formulario, spinMutuoReservaTec.get(t), "reservaTec");
                textoMutuoReservaTec = textoMutuoReservaTec + "," + formulario.getReservaTec();
            }
            formulario.setReservaTec(textoMutuoReservaTec);

            String textoMutuoBackbone ="";
            for(int t=0;t<spinMutuoBackbone.size();t++){
                setLista(formulario, spinMutuoBackbone.get(t), "backbone");
                textoMutuoBackbone = textoMutuoBackbone + "," + formulario.getBackbone();
            }
            formulario.setBackbone(textoMutuoBackbone);

            String textoMutuoPlaca ="";
            for(int t=0;t<checkMutuoPlaca.size();t++){
                if (checkMutuoPlaca.get(t).isChecked()) {
                    formulario.setPlacaIdent("Sim");
                } else {
                    formulario.setPlacaIdent("Não");
                }
                textoMutuoPlaca = textoMutuoPlaca + "," + formulario.getPlacaIdent();
            }
            formulario.setPlacaIdent(textoMutuoPlaca);

            String textoMutuoDescida ="";
            for(int t=0;t<checkMutuoDescida.size();t++){
                if (checkMutuoDescida.get(t).isChecked()) {
                    formulario.setDescidaCabos("Sim");
                } else {
                    formulario.setDescidaCabos("Não");
                }
                textoMutuoDescida = textoMutuoDescida + "," + formulario.getDescidaCabos();
            }
            formulario.setDescidaCabos(textoMutuoDescida);

            String textoMutuoIrregularidade ="";
            for(int t=0;t<editMutuoIrregularidade.size();t++){
                formulario.setDescricaoIrregularidade(Objects.requireNonNull(editMutuoIrregularidade.get(t).getText().toString()));
                textoMutuoIrregularidade  = textoMutuoIrregularidade  + "," + formulario.getDescricaoIrregularidade();
            }
            formulario.setDescricaoIrregularidade(Objects.requireNonNull(textoMutuoIrregularidade));

            formulario.setObservacaoMutuo(Objects.requireNonNull(observacaoMutuo.getText()).toString());
            //VEGETAÇÃO
            if (chkVegetacao.isChecked()) {
                formulario.setVegetacao("Sim");
            } else {
                formulario.setVegetacao("Não");
                formulario.setColor12(String.valueOf(R.color.colorPrimary));
            }
            setLista(formulario, dimensaoVegetacao, "dimensaoVegetacao");
            setLista(formulario, distaciaBaixa, "distanciaBaixa");
            setLista(formulario, distanciaMedia, "distanciaMedia");
            setLista(formulario, estadoArvore, "estadoArvore");
            if (quedaArvore.isChecked()) {
                formulario.setQuedaArvore("Sim");
            } else {
                formulario.setQuedaArvore("Não");
            }
            setLista(formulario, localArvore, "localArvore");
            formulario.setObservacaoVegetacao(Objects.requireNonNull(observacaoVegetacao.getText()).toString());
            formulario.setContadorAr(contadorAr);
            formulario.setContadorAt(contadorAt);
            formulario.setContadorIp(contadorIp);
            if ((novoPosteEstado == false) && (formularioAtual != null) ) {
                    formulario.setId(formularioAtual.getId());
                    formulario.setData(formularioAtual.getData());
                    if (formularioDAO.atualizar(formulario)) {
                        CadastradosFragment cadastradosFragment = new CadastradosFragment();
                        CadastroFragment cadastroFragment = new CadastroFragment();
                        FragmentManager fm = getParentFragmentManager();
                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        FragmentTransaction transaction = fm.beginTransaction();;
                        transaction.detach(cadastroFragment);
                        transaction.replace(R.id.nav_host_fragment, cadastradosFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        StyleableToast.makeText(requireActivity().getApplicationContext(), "Sucesso ao atualizar formulário", R.style.ToastDone).show();
                        progressDialog.dismiss();
                        limparMemoria();
                    } else {
                        StyleableToast.makeText(requireActivity().getApplicationContext(), "Erro ao atualizar formulário", R.style.ToastError).show();
                        progressDialog.dismiss();
                    }
            } else {
                if(outroOcupante == true){
                    formulario.setData(formularioAtual.getData());
                }
                else {
                    String thisDayText, thisMonthText, thisYearText;
                    //region Inicialização da data
                    Calendar calendar = Calendar.getInstance();

                    int thisYear = calendar.get(Calendar.YEAR);
                    thisYearText = String.valueOf(thisYear);

                    int thisMonth = calendar.get(Calendar.MONTH) + 1;
                    if (thisMonth < 9) {
                        thisMonthText = "0" + thisMonth;
                    } else {
                        thisMonthText = String.valueOf(thisMonth);
                    }

                    int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
                    if (thisDay < 9) {
                        thisDayText = "0" + thisDay;
                    } else {
                        thisDayText = String.valueOf(thisDay);
                    }
                    String data = thisDayText + "/" + thisMonthText + "/" + thisYearText;
                    String timeStamp = new SimpleDateFormat("dd/MM/yy-HH:mm:ss").format(new Date());
                    SimpleDateFormat readDate = new SimpleDateFormat("dd/MM/yy-HH:mm:ss");
                    readDate.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date date = null;

                    try {
                        date = readDate.parse(timeStamp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat writeDate = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                    writeDate.setTimeZone(TimeZone.getTimeZone("GMT-00:00"));
                    String s = writeDate.format(date);
                    formulario.setData(s);
                }
                //endregion
                if (formularioDAO.salvar(formulario)) {
                    CadastradosFragment cadastradosFragment = new CadastradosFragment();
                    CadastroFragment cadastroFragment = new CadastroFragment();
                    FragmentManager fm = getParentFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, cadastradosFragment);
                    transaction.detach(cadastroFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    StyleableToast.makeText(requireActivity().getApplicationContext(), "Sucesso ao salvar formulário", R.style.ToastDone).show();
                    progressDialog.dismiss();
                    limparMemoria();
                } else {
                    StyleableToast.makeText(requireActivity().getApplicationContext(), "Erro ao salvar formulário", R.style.ToastError).show();
                    progressDialog.dismiss();
                }

            }
            try {
                progressDialog.dismiss();
            }catch (Exception e){

            }
        }
    }


    /*
    *Função usada para limpar a memoria do android, devido aos Bitmap que consomen muita RAM
    *levando a paralização e crash do aplicativo.
     */
    public void limparMemoria() throws Throwable {
        locationManager = null;
        locationListener = null;
        storageReference = null;
        autentificacao = null;
        progressDialog = null;
        geocoder = null;
        addresses = null;

        fotoPan.setImageResource(R.drawable.ic_menu_camera);
        fotoAlt.setImageResource(R.drawable.ic_menu_camera);
        fotoQualidade.setImageResource(R.drawable.ic_menu_camera);
        fotoIP.setImageResource(R.drawable.ic_menu_camera);
        fotoAtivos.setImageResource(R.drawable.ic_menu_camera);
        fotoAtivos2.setImageResource(R.drawable.ic_menu_camera);
        fotoMutuo.setImageResource(R.drawable.ic_menu_camera);
        fotoMutuo2.setImageResource(R.drawable.ic_menu_camera);
        fotoMutuo3.setImageResource(R.drawable.ic_menu_camera);
        fotoVeg.setImageResource(R.drawable.ic_menu_camera);
        urlFotoPan = null;
        urlFotoAlt = null;
        urlFotoQualidade = null;
        urlFotoIP = null;
        urlFotoAtivos = null;
        urlFotoAtivos2 = null;
        urlFotoMutuo = null;
        urlFotoMutuo2 = null;
        urlFotoMutuo3 = null;
        urlFotoVeg = null;

        imagemPan.recycle();
        imagemAlt.recycle();
        imagemQualidade.recycle();
        imagemIP.recycle();
        imagemAtivos.recycle();
        imagemAtivos2.recycle();
        imagemMutuo.recycle();
        imagemMutuo2.recycle();
        imagemMutuo3.recycle();
        imagemVeg.recycle();

        imgPathPan = null;
        imgPathAlt = null;
        imgPathQualidade = null;
        imgPathIP = null;
        imgPathAtivos = null;
        imgPathAtivos2 = null;
        imgPathMutuo = null;
        imgPathMutuo2 = null;
        imgPathMutuo3 = null;
        imgPathVeg = null;
        root = null;
        Runtime.getRuntime().gc();
        //this.finalize();
    }

    //Funçao usada para criar os campos para preencher ilumininação pública dinâmicamente.
    public void createIP(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderIP);
        // add edittext
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 20, 0, 20);
        RelativeLayout relativeLayout1 = new RelativeLayout(requireActivity().getApplicationContext());
        RelativeLayout.LayoutParams pRelative = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout1.setLayoutParams(p);
        if(editIP24H.size()>0){
            TextView textView0 = new TextView(requireActivity().getApplicationContext());
            //textView.setLayoutParams(p);
            textView0.setText("Iluminação Publica " + (editIP24H.size()+1));
            textView0.setTextSize(20);
            textView0.setTextColor(getResources().getColor(R.color.textColor));
            textView0.setTypeface(null, Typeface.BOLD);
            ll.addView(textView0,p);
            textIP.add(textView0);
        }
        Spinner spinner = new Spinner(requireActivity().getApplicationContext());
        spinner.setLayoutParams(pRelative);
        String[] testArray = getResources().getStringArray(R.array.IluminacaoTipo);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray );
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout1.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout1.addView(spinner);
        ll.addView(relativeLayout1);
        TextView textView = new TextView(requireActivity().getApplicationContext());
        //textView.setLayoutParams(p);
        textView.setText("Quantidade Lâmpada");
        textView.setTextColor(getResources().getColor(R.color.textColor));
        textView.setTypeface(null, Typeface.BOLD);
        ll.addView(textView,p);
        final EditText editText = new EditText(requireActivity().getApplicationContext());
        editText.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText.setHeight(70);
        editText.setPadding(10, 10, 10, 10);
        ll.addView(editText,p);
        RelativeLayout relativeLayout2 = new RelativeLayout(requireActivity().getApplicationContext());
        final Spinner spinner2 = new Spinner(requireActivity().getApplicationContext());
        spinner2.setLayoutParams(pRelative);
        String[] testArray2 = getResources().getStringArray(R.array.IluminacaoLampadaTipo);
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray2 );
        spinner2.setAdapter(spinnerArrayAdapter2);
        spinner2.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout2.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout2.addView(spinner2);
        ll.addView(relativeLayout2);
        final EditText editText2 = new EditText(requireActivity().getApplicationContext());
        editText2.setHint("Pot.Lampada");
        editText2.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText2.setHeight(70);
        editText2.setPadding(10, 10, 10, 10);
        ll.addView(editText2,p);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] texto = (spinner2.getSelectedItem().toString()).split(" ");
                try {
                    editText2.setText(texto[1]);
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RelativeLayout relativeLayout3 = new RelativeLayout(requireActivity().getApplicationContext());
        Spinner spinner3 = new Spinner(requireActivity().getApplicationContext());
        spinner3.setLayoutParams(pRelative);
        String[] testArray3 = getResources().getStringArray(R.array.TipoAtivacao);
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray3 );
        spinner3.setAdapter(spinnerArrayAdapter3);
        spinner3.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout3.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout3.addView(spinner3);
        ll.addView(relativeLayout3);
        final CheckBox checkBox = new CheckBox(requireActivity().getApplicationContext());
        checkBox.setText("24 Horas");
        checkBox.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox);
        final EditText editText3 = new EditText(requireActivity().getApplicationContext());
        editText3.setHint("Quantidade 24H");
        editText3.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText3.setEnabled(false);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    try{
                        editText3.setEnabled(true);
                    }catch (Exception e){

                    }
                }else{
                    editText3.setEnabled(false);
                    editText3.setText("");
                }
            }
        });
        checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
        editText3.setHeight(70);
        editText3.setPadding(10, 0, 10, 10);
        ll.addView(editText3,p);
        spinIPEstrutura.add(spinner);
        textIPQLampada.add(textView);
        editIPQuantidadeLampada.add(editText);
        spinIPTipo.add(spinner2);
        editIPPot.add(editText2);
        spinIPTipoAtivacao.add(spinner3);
        checkIP24.add(checkBox);
        editIP24H.add(editText3);
    }

    //Função utilizada para remover a ultima iluminação pública adicionado pelo botão "Adicionar".
    public void removeIP(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderIP);
        try{
            ll.removeView(textIP.get(textIP.size()-1));
            textIP.remove(textIP.size()-1);
        }catch (Exception e){

        }
        ll.removeView((View)spinIPEstrutura.get(spinIPEstrutura.size()-1).getParent());
        ll.removeView(textIPQLampada.get(textIPQLampada.size()-1));
        ll.removeView(editIPQuantidadeLampada.get(editIPQuantidadeLampada.size()-1));
        ll.removeView((View)spinIPTipo.get(spinIPTipo.size()-1).getParent());
        ll.removeView(editIPPot.get(editIPPot.size()-1));
        ll.removeView((View)spinIPTipoAtivacao.get(spinIPTipoAtivacao.size()-1).getParent());
        ll.removeView(checkIP24.get(checkIP24.size()-1));
        ll.removeView(editIP24H.get(editIP24H.size()-1));
        spinIPEstrutura.remove(spinIPEstrutura.size()-1);
        textIPQLampada.remove(textIPQLampada.size()-1);
        editIPQuantidadeLampada.remove(editIPQuantidadeLampada.size()-1);
        spinIPTipo.remove(spinIPTipo.size()-1);
        editIPPot.remove(editIPPot.size()-1);
        spinIPTipoAtivacao.remove(spinIPTipoAtivacao.size()-1);
        checkIP24.remove(checkIP24.size()-1);
        editIP24H.remove(editIP24H.size()-1);
        if(spinIPTipoAtivacao.size() == 0){
            btnIPLayoutRemove.setVisibility(View.GONE);
        }

    }

    /*
    Função usada para remover todos os botões gerados ao apertar "Adicionar" na parte de iluminação
    pública
    */
    public void removeIPAll(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderIP);
        ll.removeAllViews();
        try{
            textIP.clear();
        }catch (Exception e){

        }
        spinIPEstrutura.clear();
        textIPQLampada.clear();
        editIPQuantidadeLampada.clear();
        spinIPTipo.clear();
        editIPPot.clear();
        spinIPTipoAtivacao.clear();
        checkIP24.clear();
        editIP24H.clear();
    }

    //Funçao usada para criar os campos para preencher uso ativo dinâmicamente.
    public void createAtivo(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderAtivo);
        // add edittext
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 20, 0, 20);
        RelativeLayout relativeLayout1 = new RelativeLayout(requireActivity().getApplicationContext());
        RelativeLayout.LayoutParams pRelative = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout1.setLayoutParams(p);
        if(editAtivoOutro.size()>0){
            TextView textView0 = new TextView(requireActivity().getApplicationContext());
            //textView.setLayoutParams(p);
            textView0.setText("Ativos " + (editAtivoOutro.size()+1));
            textView0.setTextSize(20);
            textView0.setTextColor(getResources().getColor(R.color.textColor));
            textView0.setTypeface(null, Typeface.BOLD);
            ll.addView(textView0,p);
            textAtivo.add(textView0);
        }
        final CheckBox checkBox = new CheckBox(requireActivity().getApplicationContext());
        checkBox.setText("Trifásico");
        checkBox.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox);

        final Spinner spinner = new Spinner(requireActivity().getApplicationContext());
        spinner.setLayoutParams(pRelative);
        spinner.setEnabled(false);
        String[] testArray = getResources().getStringArray(R.array.TrafoTrifasico);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray );
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout1.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout1.addView(spinner);
        ll.addView(relativeLayout1);

        final CheckBox checkBox2 = new CheckBox(requireActivity().getApplicationContext());
        checkBox2.setText("Monofásico");
        checkBox2.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox2);

        RelativeLayout relativeLayout2 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout2.setLayoutParams(p);

        final Spinner spinner2 = new Spinner(requireActivity().getApplicationContext());
        spinner2.setLayoutParams(pRelative);
        spinner2.setEnabled(false);
        String[] testArray2 = getResources().getStringArray(R.array.TrafoMono);
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray2 );
        spinner2.setAdapter(spinnerArrayAdapter2);
        spinner2.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout2.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout2.addView(spinner2);
        ll.addView(relativeLayout2);

        TextView textView = new TextView(requireActivity().getApplicationContext());
        textView.setText("Chave Fusível");
        textView.setTextColor(getResources().getColor(R.color.textColor));
        textView.setTypeface(null, Typeface.BOLD);
        ll.addView(textView,p);

        final EditText editText = new EditText(requireActivity().getApplicationContext());
        editText.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText.setHeight(70);
        editText.setPadding(10, 10, 10, 10);
        ll.addView(editText,p);

        TextView textView2 = new TextView(requireActivity().getApplicationContext());
        textView2.setText("Chave Fusível Religador");
        textView2.setTextColor(getResources().getColor(R.color.textColor));
        textView2.setTypeface(null, Typeface.BOLD);
        ll.addView(textView2,p);

        final EditText editText2 = new EditText(requireActivity().getApplicationContext());
        editText2.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText2.setHeight(70);
        editText2.setPadding(10, 10, 10, 10);
        ll.addView(editText2,p);

        RelativeLayout relativeLayout3 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout3.setLayoutParams(p);

        Spinner spinner3 = new Spinner(requireActivity().getApplicationContext());
        spinner3.setLayoutParams(pRelative);
        String[] testArray3 = getResources().getStringArray(R.array.ChFaca);
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray3 );
        spinner3.setAdapter(spinnerArrayAdapter3);
        spinner3.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout3.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout3.addView(spinner3);
        ll.addView(relativeLayout3);

        RelativeLayout relativeLayout4 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout4.setLayoutParams(p);

        Spinner spinner4 = new Spinner(requireActivity().getApplicationContext());
        spinner4.setLayoutParams(pRelative);
        String[] testArray4 = getResources().getStringArray(R.array.Banco);
        ArrayAdapter spinnerArrayAdapter4 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray4 );
        spinner4.setAdapter(spinnerArrayAdapter4);
        spinner4.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout4.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout4.addView(spinner4);
        ll.addView(relativeLayout4);

        RelativeLayout relativeLayout5 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout5.setLayoutParams(p);

        Spinner spinner5 = new Spinner(requireActivity().getApplicationContext());
        spinner5.setLayoutParams(pRelative);
        String[] testArray5 = getResources().getStringArray(R.array.RamalSubt);
        ArrayAdapter spinnerArrayAdapter5 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray5 );
        spinner5.setAdapter(spinnerArrayAdapter5);
        spinner5.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout5.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout5.addView(spinner5);
        ll.addView(relativeLayout5);

        final CheckBox checkBox3 = new CheckBox(requireActivity().getApplicationContext());
        checkBox3.setText("Religador");
        checkBox3.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox3);

        final CheckBox checkBox4 = new CheckBox(requireActivity().getApplicationContext());
        checkBox4.setText("Medição");
        checkBox4.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox4);

        TextView textView3 = new TextView(requireActivity().getApplicationContext());
        textView3.setText("Outros");
        textView3.setTextColor(getResources().getColor(R.color.textColor));
        textView3.setTypeface(null, Typeface.BOLD);
        ll.addView(textView3,p);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    spinner.setEnabled(true);
                    spinner2.setEnabled(false);
                    spinner2.setSelection(0);
                    checkBox2.setChecked(false);
                }
                else {
                    spinner.setEnabled(false);
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox2.isChecked()){
                    spinner2.setEnabled(true);
                    spinner.setEnabled(false);
                    spinner.setSelection(0);
                    checkBox.setChecked(false);
                }
                else {
                    spinner2.setEnabled(false);
                }
            }
        });

        final EditText editText3 = new EditText(requireActivity().getApplicationContext());
        editText3.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText3.setHeight(70);
        editText3.setPadding(10, 10, 10, 10);
        ll.addView(editText3,p);
        checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
        checkBox2.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
        checkBox3.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
        checkBox4.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));


        checkAtivoTrifasico.add(checkBox);
        checkAtivoMono.add(checkBox2);
        checkAtivoReligador.add(checkBox3);
        checkAtivoMedicao.add(checkBox4);

        spinAtivoTrifasico.add(spinner);
        spinAtivoMono.add(spinner2);
        spinAtivoChFaca.add(spinner3);
        spinAtivoBanco.add(spinner4);
        spinAtivoRamal.add(spinner5);

        textAtivoChFusivel.add(textView);
        textAtivoChFusivelReligador.add(textView2);
        textAtivoOutro.add(textView3);

        editAtivoChFusivel.add(editText);
        editAtivoChFusivelReligador.add(editText2);
        editAtivoOutro.add(editText3);
    }


    //Função utilizada para remover o ultimo ativo adicionado pelo botão "Adicionar".
    public void removeAtivo(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderAtivo);
        try{
            ll.removeView(textAtivo.get(textAtivo.size()-1));
            textAtivo.remove(textAtivo.size()-1);
        }catch (Exception e){

        }
        ll.removeView((View)spinAtivoTrifasico.get(spinAtivoTrifasico.size()-1).getParent());
        ll.removeView((View)spinAtivoMono.get(spinAtivoMono.size()-1).getParent());
        ll.removeView((View)spinAtivoChFaca.get(spinAtivoChFaca.size()-1).getParent());
        ll.removeView((View)spinAtivoBanco.get(spinAtivoBanco.size()-1).getParent());
        ll.removeView((View)spinAtivoRamal.get(spinAtivoRamal.size()-1).getParent());
        ll.removeView(textAtivoChFusivel.get(textAtivoChFusivel.size()-1));
        ll.removeView(editAtivoChFusivel.get(editAtivoChFusivel.size()-1));
        ll.removeView(textAtivoChFusivelReligador.get(textAtivoChFusivelReligador.size()-1));
        ll.removeView(editAtivoChFusivelReligador.get(editAtivoChFusivelReligador.size()-1));
        ll.removeView(textAtivoOutro.get(textAtivoOutro.size()-1));
        ll.removeView(editAtivoOutro.get(editAtivoOutro.size()-1));
        ll.removeView(checkAtivoTrifasico.get(checkAtivoTrifasico.size()-1));
        ll.removeView(checkAtivoMono.get(checkAtivoMono.size()-1));
        ll.removeView(checkAtivoReligador.get(checkAtivoReligador.size()-1));
        ll.removeView(checkAtivoMedicao.get(checkAtivoMedicao.size()-1));

        spinAtivoTrifasico.remove(spinAtivoTrifasico.size()-1);
        spinAtivoMono.remove(spinAtivoMono.size()-1);
        spinAtivoChFaca.remove(spinAtivoChFaca.size()-1);
        spinAtivoBanco.remove(spinAtivoBanco.size()-1);
        spinAtivoRamal.remove(spinAtivoRamal.size()-1);
        textAtivoChFusivel.remove(textAtivoChFusivel.size()-1);
        editAtivoChFusivel.remove(editAtivoChFusivel.size()-1);
        textAtivoChFusivelReligador.remove(textAtivoChFusivelReligador.size()-1);
        editAtivoChFusivelReligador.remove(editAtivoChFusivelReligador.size()-1);
        textAtivoOutro.remove(textAtivoOutro.size()-1);
        editAtivoOutro.remove(editAtivoOutro.size()-1);
        checkAtivoTrifasico.remove(checkAtivoTrifasico.size()-1);
        checkAtivoMono.remove(checkAtivoMono.size()-1);
        checkAtivoReligador.remove(checkAtivoReligador.size()-1);
        checkAtivoMedicao.remove(checkAtivoMedicao.size()-1);

        if(spinAtivoTrifasico.size() == 0){
            btnAtivoRemove.setVisibility(View.GONE);
        }
    }

    //Função usada para remover todos os campos gerados ao apertar "Adicionar" na parte de Ativos.
    public void removeAtivoAll(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderAtivo);
        ll.removeAllViews();
        try{
            textAtivo.clear();
        }catch (Exception e){

        }
        spinAtivoTrifasico.clear();
        spinAtivoMono.clear();
        spinAtivoChFaca.clear();
        spinAtivoBanco.clear();
        spinAtivoRamal.clear();
        textAtivoChFusivel.clear();
        editAtivoChFusivel.clear();
        textAtivoChFusivelReligador.clear();
        editAtivoChFusivelReligador.clear();
        textAtivoOutro.clear();
        editAtivoOutro.clear();
        checkAtivoTrifasico.clear();
        checkAtivoMono.clear();
        checkAtivoReligador.clear();
        checkAtivoMedicao.clear();
    }


    //Funçao usada para criar os campos para preencher uso mutuo dinâmicamente.
    public void createMutuo(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderMutuo);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 20, 0, 20);
        if(editMutuoQuantidade.size()>0){
            TextView textView0 = new TextView(requireActivity().getApplicationContext());
            //textView.setLayoutParams(p);
            textView0.setText("Mutuo " + (editMutuoQuantidade.size()+1));
            textView0.setTextSize(20);
            textView0.setTextColor(getResources().getColor(R.color.textColor));
            textView0.setTypeface(null, Typeface.BOLD);
            ll.addView(textView0,p);
            textMutuo.add(textView0);
        }
        RelativeLayout.LayoutParams pRelative = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(requireActivity().getApplicationContext());
        textView.setText("Nome da Empresa");
        textView.setTextColor(getResources().getColor(R.color.textColor));
        textView.setTypeface(null, Typeface.BOLD);
        ll.addView(textView,p);

        final EditText editText = new EditText(requireActivity().getApplicationContext());
        editText.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText.setHeight(70);
        editText.setPadding(10, 10, 10, 10);
        ll.addView(editText,p);

        TextView textView2 = new TextView(requireActivity().getApplicationContext());
        textView2.setText("Quantidade de Cabos");
        textView2.setTextColor(getResources().getColor(R.color.textColor));
        textView2.setTypeface(null, Typeface.BOLD);
        ll.addView(textView2,p);

        final EditText editText2 = new EditText(requireActivity().getApplicationContext());
        editText2.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText2.setHeight(70);
        editText2.setPadding(10, 10, 10, 10);
        ll.addView(editText2,p);

        RelativeLayout relativeLayout = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout.setLayoutParams(p);

        final Spinner spinner = new Spinner(requireActivity().getApplicationContext());
        spinner.setLayoutParams(pRelative);
        String[] testArray = getResources().getStringArray(R.array.TipoCaboMutuo);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray );
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout.addView(spinner);
        ll.addView(relativeLayout);

        TextView textView3 = new TextView(requireActivity().getApplicationContext());
        textView3.setText("Quantidade de Cabos 2");
        textView3.setTextColor(getResources().getColor(R.color.textColor));
        textView3.setTypeface(null, Typeface.BOLD);
        ll.addView(textView3,p);

        final EditText editText3 = new EditText(requireActivity().getApplicationContext());
        editText3.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText3.setHeight(70);
        editText3.setPadding(10, 10, 10, 10);
        ll.addView(editText3,p);

        RelativeLayout relativeLayout2 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout2.setLayoutParams(p);

        final Spinner spinner2 = new Spinner(requireActivity().getApplicationContext());
        spinner2.setLayoutParams(pRelative);
        String[] testArray2 = getResources().getStringArray(R.array.TipoCaboMutuo2);
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray2 );
        spinner2.setAdapter(spinnerArrayAdapter2);
        spinner2.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout2.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout2.addView(spinner2);
        ll.addView(relativeLayout2);

        RelativeLayout relativeLayout3 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout3.setLayoutParams(p);

        final Spinner spinner3 = new Spinner(requireActivity().getApplicationContext());
        spinner3.setLayoutParams(pRelative);
        String[] testArray3 = getResources().getStringArray(R.array.finalidade);
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray3 );
        spinner3.setAdapter(spinnerArrayAdapter3);
        spinner3.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout3.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout3.addView(spinner3);
        ll.addView(relativeLayout3);

        RelativeLayout relativeLayout4 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout4.setLayoutParams(p);

        final Spinner spinner4 = new Spinner(requireActivity().getApplicationContext());
        spinner4.setLayoutParams(pRelative);
        String[] testArray4 = getResources().getStringArray(R.array.ceans);
        ArrayAdapter spinnerArrayAdapter4 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray4 );
        spinner4.setAdapter(spinnerArrayAdapter4);
        spinner4.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout4.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout4.addView(spinner4);
        ll.addView(relativeLayout4);

        RelativeLayout relativeLayout5 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout5.setLayoutParams(p);

        final Spinner spinner5 = new Spinner(requireActivity().getApplicationContext());
        spinner5.setLayoutParams(pRelative);
        String[] testArray5 = getResources().getStringArray(R.array.tar);
        ArrayAdapter spinnerArrayAdapter5 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray5 );
        spinner5.setAdapter(spinnerArrayAdapter5);
        spinner5.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout5.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout5.addView(spinner5);
        ll.addView(relativeLayout5);

        RelativeLayout relativeLayout6 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout6.setLayoutParams(p);

        final Spinner spinner6 = new Spinner(requireActivity().getApplicationContext());
        spinner6.setLayoutParams(pRelative);
        String[] testArray6 = getResources().getStringArray(R.array.reservaTec);
        ArrayAdapter spinnerArrayAdapter6 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray6 );
        spinner6.setAdapter(spinnerArrayAdapter6);
        spinner6.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout6.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout6.addView(spinner6);
        ll.addView(relativeLayout6);

        RelativeLayout relativeLayout7 = new RelativeLayout(requireActivity().getApplicationContext());
        relativeLayout7.setLayoutParams(p);

        final Spinner spinner7 = new Spinner(requireActivity().getApplicationContext());
        spinner7.setLayoutParams(pRelative);
        String[] testArray7 = getResources().getStringArray(R.array.backbone);
        ArrayAdapter spinnerArrayAdapter7 = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, testArray7 );
        spinner7.setAdapter(spinnerArrayAdapter7);
        spinner7.setPopupBackgroundResource(R.drawable.spinner_pop);
        relativeLayout7.setBackground(getResources().getDrawable(R.drawable.spinner_style));
        relativeLayout7.addView(spinner7);
        ll.addView(relativeLayout7);

        final CheckBox checkBox = new CheckBox(requireActivity().getApplicationContext());
        checkBox.setText("Placa Identificadora");
        checkBox.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox);

        final CheckBox checkBox2 = new CheckBox(requireActivity().getApplicationContext());
        checkBox2.setText("Descidade de Cabos");
        checkBox2.setTextColor(getResources().getColor(R.color.textColor));
        ll.addView(checkBox2);

        TextView textView4 = new TextView(requireActivity().getApplicationContext());
        textView4.setText("Irregularidade");
        textView4.setTextColor(getResources().getColor(R.color.textColor));
        textView4.setTypeface(null, Typeface.BOLD);
        ll.addView(textView4,p);

        final EditText editText4 = new EditText(requireActivity().getApplicationContext());
        editText4.setBackground(getResources().getDrawable(R.drawable.edit_round));
        editText4.setHeight(70);
        editText4.setPadding(10, 10, 10, 10);
        ll.addView(editText4,p);

        checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
        checkBox2.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));

        checkMutuoPlaca.add(checkBox);
        checkMutuoDescida.add(checkBox2);

        spinMutuoTipoCabo.add(spinner);
        spinMutuoTipoCabo2.add(spinner2);
        spinMutuoFinalidade.add(spinner3);
        spinMutuoCeans.add(spinner4);
        spinMutuoTar.add(spinner5);
        spinMutuoReservaTec.add(spinner6);
        spinMutuoBackbone.add(spinner7);

        textMutuoNome.add(textView);
        textMutuoQuantidade.add(textView2);
        textMutuoQuantidade2.add(textView3);
        textMutuoIrregularidade.add(textView4);

        editMutuoNome.add(editText);
        editMutuoQuantidade.add(editText2);
        editMutuoQuantidade2.add(editText3);
        editMutuoIrregularidade.add(editText4);
    }

    //Função utilizada para remover o ultimo uso mútuo adicionado pelo botão "Adicionar".
    public void removeMutuo(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderMutuo);
        try{
            ll.removeView(textMutuo.get(textMutuo.size()-1));
            textMutuo.remove(textMutuo.size()-1);
        }catch (Exception e){

        }

        ll.removeView((View)spinMutuoTipoCabo.get(spinMutuoTipoCabo.size()-1).getParent());
        ll.removeView((View)spinMutuoTipoCabo2.get(spinMutuoTipoCabo2.size()-1).getParent());
        ll.removeView((View)spinMutuoFinalidade.get(spinMutuoFinalidade.size()-1).getParent());
        ll.removeView((View)spinMutuoCeans.get(spinMutuoCeans.size()-1).getParent());
        ll.removeView((View)spinMutuoTar.get(spinMutuoTar.size()-1).getParent());
        ll.removeView((View)spinMutuoReservaTec.get(spinMutuoReservaTec.size()-1).getParent());
        ll.removeView((View)spinMutuoBackbone.get(spinMutuoBackbone.size()-1).getParent());
        ll.removeView(textMutuoNome.get(textMutuoNome.size()-1));
        ll.removeView(textMutuoQuantidade.get(textMutuoQuantidade.size()-1));
        ll.removeView(textMutuoQuantidade2.get(textMutuoQuantidade2.size()-1));
        ll.removeView(textMutuoIrregularidade.get(textMutuoIrregularidade.size()-1));
        ll.removeView(editMutuoNome.get(editMutuoNome.size()-1));
        ll.removeView(editMutuoQuantidade.get(editMutuoQuantidade.size()-1));
        ll.removeView(editMutuoQuantidade2.get(editMutuoQuantidade2.size()-1));
        ll.removeView(editMutuoIrregularidade.get(editMutuoIrregularidade.size()-1));
        ll.removeView(checkMutuoPlaca.get(checkMutuoPlaca.size()-1));
        ll.removeView(checkMutuoDescida.get(checkMutuoDescida.size()-1));

        spinMutuoTipoCabo.remove(spinMutuoTipoCabo.size()-1);
        spinMutuoTipoCabo2.remove(spinMutuoTipoCabo2.size()-1);
        spinMutuoFinalidade.remove(spinMutuoFinalidade.size()-1);
        spinMutuoCeans.remove(spinMutuoCeans.size()-1);
        spinMutuoTar.remove(spinMutuoTar.size()-1);
        spinMutuoReservaTec.remove(spinMutuoReservaTec.size()-1);
        spinMutuoBackbone.remove(spinMutuoBackbone.size()-1);
        textMutuoNome.remove(textMutuoNome.size()-1);
        editMutuoNome.remove(editMutuoNome.size()-1);
        textMutuoQuantidade.remove(textMutuoQuantidade.size()-1);
        editMutuoQuantidade.remove(editMutuoQuantidade.size()-1);
        textMutuoQuantidade2.remove(textMutuoQuantidade2.size()-1);
        editMutuoQuantidade2.remove(editMutuoQuantidade2.size()-1);
        textMutuoIrregularidade.remove(textMutuoIrregularidade.size()-1);
        editMutuoIrregularidade.remove(editMutuoIrregularidade.size()-1);
        checkMutuoPlaca.remove(checkMutuoPlaca.size()-1);
        checkMutuoDescida.remove(checkMutuoDescida.size()-1);


        if(spinMutuoTipoCabo.size() == 0){
            btnMutuoRemove.setVisibility(View.GONE);
        }

    }

   //Função usada para remover todos os campos gerados ao apertar "Adicionar" na parte de uso Mutuo.
    public void removeMutuoAll(){
        LinearLayout ll = root.findViewById(R.id.layoutHolderMutuo);
        ll.removeAllViews();
        try{
            textMutuo.clear();
        }catch (Exception e){

        }

        spinMutuoTipoCabo.clear();
        spinMutuoTipoCabo2.clear();
        spinMutuoFinalidade.clear();
        spinMutuoCeans.clear();
        spinMutuoTar.clear();
        spinMutuoReservaTec.clear();
        spinMutuoBackbone.clear();
        textMutuoNome.clear();
        editMutuoNome.clear();
        textMutuoQuantidade.clear();
        editMutuoQuantidade.clear();
        textMutuoQuantidade2.clear();
        editMutuoQuantidade2.clear();
        textMutuoIrregularidade.clear();
        editMutuoIrregularidade.clear();
        checkMutuoPlaca.clear();
        checkMutuoDescida.clear();
    }

// Função para enviar as fotos para o Firebase
public void uploadAll(){
    contadorAt = 1;
    progressDialog = new ProgressDialog(requireContext(), R.style.LightDialogTheme);
    progressDialog.setMessage("Enviando dados..."); // Setting Message
    progressDialog.setTitle("Por favor Espere"); // Setting Title
    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Spinner
    progressDialog.show(); // Display Progress Dialog
    progressDialog.setCancelable(false);

    //É necessário estipular um tempo, para que o progress dialog, não feche imediatamente
    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathPan!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathPan, codigo, contadorAt, 1, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(10);
                    }else{
                        progressDialog.setProgress(10);
                    }
                }
            }, 1000);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathAlt!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathAlt, codigo, contadorAt, 2, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(20);
                    }else{
                        progressDialog.setProgress(20);
                    }
                }
            }, 1500);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathQualidade!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathQualidade, codigo, contadorAt, 3, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(30);
                    }else{
                        progressDialog.setProgress(30);
                    }
                }
            }, 2000);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathIP!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathIP, codigo, 1, 4, "IP");
                        threadUpload.start();
                        progressDialog.setProgress(40);
                    }else{
                        progressDialog.setProgress(40);
                    }
                }
            }, 2500);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathAtivos!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathAtivos, codigo, contadorAt, 5, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(50);
                    }else{
                        progressDialog.setProgress(50);
                    }
                }
            }, 3000);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathAtivos2!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathAtivos2, codigo, contadorAt, 6, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(60);
                    }else{
                        progressDialog.setProgress(60);
                    }
                }
            }, 3500);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathMutuo!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathMutuo, codigo, contadorAt, 7, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(70);
                    }else{
                        progressDialog.setProgress(70);
                    }
                }
            }, 4000);

    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathMutuo2!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathMutuo2, codigo, contadorAt, 8, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(80);
                    }else{
                        progressDialog.setProgress(80);
                    }
                }
            }, 4500);


    new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(imgPathMutuo3!=null){
                        ThreadUpload threadUpload = new ThreadUpload(imgPathMutuo3, codigo, contadorAt, 9, "CS");
                        threadUpload.start();
                        contadorAt = contadorAt+1;
                        progressDialog.setProgress(90);
                    }else{
                        progressDialog.setProgress(90);
                    }
                }
            }, 5000);

    new Handler().postDelayed(
            new Runnable() {
        public void run() {
            if(imgPathVeg!=null){
                ThreadUpload threadUpload = new ThreadUpload(imgPathVeg, codigo, 1, 10, "Ar");
                threadUpload.start();
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }else{
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }
        }
    }, 5500);


}


}
