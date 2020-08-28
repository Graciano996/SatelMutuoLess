package com.example.satelprojetos.ui.cadastro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.BitmapCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.example.satelprojetos.R;
import com.example.satelprojetos.activity.DrawerActivity;
import com.example.satelprojetos.activity.MainActivity;
import com.example.satelprojetos.config.ConfiguracaoFirebase;
import com.example.satelprojetos.helper.Base64Custom;
import com.example.satelprojetos.helper.FormularioDAO;
import com.example.satelprojetos.model.Formulario;
import com.example.satelprojetos.ui.cadastrados.CadastradosFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URLStreamHandlerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class CadastroFragment extends Fragment {
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
    List<Address> addresses;

    private RelativeLayout relativeChFaca,relativeBanco,relativeRamal, relativeIPTipoEstrutura,
                            relativeIPTipoLampada,relativeIPTipoAtivacao, relativeFinalidade,
    relativeCeans, relativeTar, relativeReservaTec, relativeBackbone,relativeTipoCabo,relativeTipoCabodois,
    relativeDimensaoVegetacao, relativeBaixa, relativeMedia, relativeEstadoArvore, relativeLocalArvore;
    private TextView textOutros, textLampada, textFusivel, textFusivelReligador, textQuantidadeCabo,
    textQuantidadeCabodois, textNome, textIrregularidade, textOcupante;
    private EditText codigo, endereco, latitude, longitude, observacaoFisicas,
            observacaoAtivos, quantidadeLampada,
            potReator, quantidade24H,
            observacaoVegetacao, observacaoIP,outros, quantidadeOcupantes, chFusivel,chFusivelReligador,
            quantidadeCabos, quantidadeCabosdois, nome,  descricaoIrregularidade, observacaoMutuo;
    private Spinner municipio, alturaCarga, tipoPoste, ipEstrutura, tipoPot,
            dimensaoVegetacao, ipAtivacao,
            trafoTrifasico, trafoMono, ramalSubt,
            tipoCabo, tipoCabodois, distaciaBaixa, distanciaMedia, estadoArvore,
            localArvore, finalidade, ceans,tar,reservaTec,backbone,chBanco,chFaca;
    private CheckBox normal, ferragemExposta, fletido, danificado, abalroado, trincado, religador, medicao,
            vinteEQuatro,
            ativos, chkTrafoTrifasico, chkTrafoMono, ip, mutuo,
            placaIdentificadora, descidaCabos, quedaArvore,chkVegetacao;
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
    public Button btnUpload, btn, btnFoto, btnFoto2,btnFoto3,btnFoto4, btnFoto13,btnFoto14,btnFoto15,btnFoto16,btnFoto17,
    btnFoto30,btnUpload2,btnUpload3, btnUpload4,btnUpload13,btnUpload14,btnUpload15,btnUpload16,btnUpload17,btnUpload30;
    public View root;
    Boolean outroOcupante = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_cadastro, container, false);
        codigo = root.findViewById(R.id.textCadastroCodigo);
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
                Toast.makeText(requireActivity().getApplicationContext(), "Por favor ative seu GPS", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(requireActivity().getApplicationContext(), "Não é possui utilizar essa função no estado de economia de energia", Toast.LENGTH_SHORT).show();
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

        btnUpload = root.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload;
                ThreadUpload threadUpload = new ThreadUpload(imgPathPan, codigo, contadorAt, 1, "CS");
                threadUpload.start();
                //upload(imagemPan, imgPathPan, codigo, contadorAt, 1, "CS");

            }
        });

        btnUpload2 = root.findViewById(R.id.btnUpload2);
        btnUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload2;
                ThreadUpload threadUpload = new ThreadUpload(imgPathAlt, codigo, contadorAt, 2, "CS");
                threadUpload.start();
                //upload(imagemAlt, imgPathAlt, codigo, contadorAt, 2, "CS");

            }
        });

        btnUpload3 = root.findViewById(R.id.btnUpload3);
        btnUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload3;
                ThreadUpload threadUpload = new ThreadUpload(imgPathQualidade, codigo, contadorAt, 3, "CS");
                threadUpload.start();
                //upload(imagemQualidade, imgPathQualidade, codigo, contadorAt, 3, "CS");
            }
        });

        btnUpload4 = root.findViewById(R.id.btnUpload4);
        btnUpload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload4;
                ThreadUpload threadUpload = new ThreadUpload(imgPathIP, codigo, contadorIp, 4, "IP");
                threadUpload.start();
            }
        });

        btnUpload13 = root.findViewById(R.id.btnUpload13);
        btnUpload13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload13;
                ThreadUpload threadUpload = new ThreadUpload(imgPathAtivos, codigo, contadorAt, 5, "CS");
                threadUpload.start();
            }
        });
        btnUpload14 = root.findViewById(R.id.btnUpload14);
        btnUpload14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload14;
                ThreadUpload threadUpload = new ThreadUpload(imgPathAtivos2, codigo, contadorAt, 6, "CS");
                threadUpload.start();
            }
        });

        btnUpload15 = root.findViewById(R.id.btnUpload15);
        btnUpload15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload15;
                ThreadUpload threadUpload = new ThreadUpload(imgPathMutuo, codigo, contadorAt, 7, "CS");
                threadUpload.start();
            }
        });

        btnUpload16 = root.findViewById(R.id.btnUpload16);
        btnUpload16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload16;
                ThreadUpload threadUpload = new ThreadUpload(imgPathMutuo2, codigo, contadorAt, 8, "CS");
                threadUpload.start();
            }
        });

        btnUpload17 = root.findViewById(R.id.btnUpload17);
        btnUpload17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload17;
                ThreadUpload threadUpload = new ThreadUpload(imgPathMutuo3, codigo, contadorAt, 9, "CS");
                threadUpload.start();
            }
        });

        btnUpload30 = root.findViewById(R.id.btnUpload30);
        btnUpload30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = btnUpload30;
                ThreadUpload threadUpload = new ThreadUpload(imgPathVeg, codigo, contadorAr, 10, "Ar");
                threadUpload.start();
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
        textLampada = root.findViewById(R.id.textView17);
        relativeIPTipoEstrutura = root.findViewById(R.id.relativeSpinIluminacaoTipo);
        ipEstrutura = root.findViewById(R.id.spinCadastroIPEstrutura);
        quantidadeLampada = root.findViewById(R.id.textCadastroQuantidadeLampada);
        ipEstrutura.setEnabled(false);
        relativeIPTipoLampada = root.findViewById(R.id.relativeSpinIluminacaoTipoPot);
        tipoPot = root.findViewById(R.id.spinCadastroTipoPot);
        tipoPot.setEnabled(false);
        potReator = root.findViewById(R.id.textCadastroPotReator);
        potReator.setEnabled(false);
        tipoPot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] texto = (tipoPot.getSelectedItem().toString()).split(" ");
                try {
                    potReator.setText(texto[1]);
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relativeIPTipoAtivacao = root.findViewById(R.id.relativeSpinIPAtivacao);
        ipAtivacao = root.findViewById(R.id.spinCadastroIPAtivacao);
        ipAtivacao.setEnabled(false);
        vinteEQuatro = root.findViewById(R.id.chkCadastroVinteEQuatro);
        quantidade24H = root.findViewById(R.id.txtCadastroQuantidade24H);
        observacaoIP = root.findViewById(R.id.textCadastroObservacaoIP);

        //TRAFO
        ativos = root.findViewById(R.id.chkAtivos);
        chkTrafoTrifasico = root.findViewById(R.id.chkCadastroTrafoTrifasico);
        chkTrafoMono = root.findViewById(R.id.chkCadastroTrafoMono);
        trafoTrifasico = root.findViewById(R.id.spinCadastroTrafoTrifasico);
        trafoTrifasico.setEnabled(false);
        trafoMono = root.findViewById(R.id.spinCadastroTrafoMono);
        trafoMono.setEnabled(false);
        religador = root.findViewById(R.id.chkCadastroReligador);
        medicao = root.findViewById(R.id.chkCadastroMedicao);
        textFusivel = root.findViewById(R.id.textView18);
        chFusivel = root.findViewById(R.id.textChaveFusivel);
        relativeChFaca = root.findViewById(R.id.relativeChFaca);
        chFaca = root.findViewById(R.id.spinCadastroChFaca);
        relativeBanco = root.findViewById(R.id.relativeBanco);
        chBanco = root.findViewById(R.id.spinCadastroBanco);
        textFusivelReligador = root.findViewById(R.id.textView19);
        chFusivelReligador = root.findViewById(R.id.textChaveFusivelReligador);
        relativeRamal = root.findViewById(R.id.relativeSpinRamalSubt);
        ramalSubt = root.findViewById(R.id.spinCadastroRamalSubt);
        ramalSubt.setEnabled(false);
        textOutros = root.findViewById(R.id.textViewOutros);
        outros = root.findViewById(R.id.textCadastroOutrosAtivos);
        observacaoAtivos = root.findViewById(R.id.textCadastroObservacaoAtivo);

        //MUTUO
        mutuo = root.findViewById(R.id.chkCadastroMutuo);
        textOcupante = root.findViewById(R.id.textViewQuantidadeOcupante);
        quantidadeOcupantes = root.findViewById(R.id.textCadastroQuantidadeMutuo);
        textQuantidadeCabo = root.findViewById(R.id.textViewQuantidadeMutuodois);
        quantidadeCabos = root.findViewById(R.id.textCadastroMutuoQuantidadeCabos);
        textQuantidadeCabodois = root.findViewById(R.id.textViewQuantidadeMutuo);
        quantidadeCabosdois = root.findViewById(R.id.textCadastroMutuoQuantidadeCabosdois);
        relativeTipoCabo = root.findViewById(R.id.relativeSpinTipoCabo);
        tipoCabo = root.findViewById(R.id.spinCadastroMutuoTipoCabo);
        relativeTipoCabodois = root.findViewById(R.id.relativeSpinTipoCabodois);
        tipoCabodois = root.findViewById(R.id.spinCadastroMutuoTipoCabodois);
        textNome = root.findViewById(R.id.textViewNome);
        nome = root.findViewById(R.id.textCadastroNome);
        relativeFinalidade = root.findViewById(R.id.relativeSpinFinalidade);
        finalidade = root.findViewById(R.id.spinCadastroFinalidade);
        relativeCeans = root.findViewById(R.id.relativeSpinCeans);
        ceans = root.findViewById(R.id.spinCadastroCeans);
        relativeTar = root.findViewById(R.id.relativeSpinTar);
        tar = root.findViewById(R.id.spinCadastroTar);
        relativeReservaTec = root.findViewById(R.id.relativeSpinReservaTec);
        reservaTec = root.findViewById(R.id.spinCadastroReservaTec);
        relativeBackbone = root.findViewById(R.id.relativeSpinBackbone);
        backbone = root.findViewById(R.id.spinCadastroBackbone);
        placaIdentificadora = root.findViewById(R.id.chkCadastroPlaca);
        descidaCabos = root.findViewById(R.id.chkCadastroDescidaCabos);
        textIrregularidade = root.findViewById(R.id.textViewIrregularidade);
        descricaoIrregularidade = root.findViewById(R.id.textCadastroDescricao);
        observacaoMutuo = root.findViewById(R.id.textCadastroObservacaoMutuo);

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
                    fotoVeg.setVisibility(View.VISIBLE);
                    btnFoto30.setVisibility(View.VISIBLE);
                    btnUpload30.setVisibility(View.VISIBLE);
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
                    fotoVeg.setVisibility(View.GONE);
                    btnFoto30.setVisibility(View.GONE);
                    btnUpload30.setVisibility(View.GONE);
                    relativeDimensaoVegetacao.setVisibility(View.GONE);
                    relativeBaixa.setVisibility(View.GONE);
                    relativeMedia.setVisibility(View.GONE);
                    relativeEstadoArvore.setVisibility(View.GONE);
                    relativeLocalArvore.setVisibility(View.GONE);
                }
            }
        });
        try {
            assert this.getArguments() != null;
            codigoEnergisa = (String) this.getArguments().getSerializable("codigoEnergisa");
            if (codigoEnergisa != null) {
                codigo.setText(codigoEnergisa);
            }
        } catch (Exception e) {
        }
        Button buttonCadastrar = root.findViewById(R.id.btnCadastroSalvar);
        try {
            assert this.getArguments() != null;
            formularioAtual = (Formulario) this.getArguments().getSerializable("formularioSelecionado");
            if (formularioAtual != null) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                dialog.setMessage("O que deseja fazer:");
                dialog.setPositiveButton("Editar/Recuperar Dados Para Novo Poste", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                        dialog2.setMessage("Escolha uma opção?");
                        dialog2.setPositiveButton("Novo Poste", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new NovoPosteTask().execute();


                            }
                        });
                        dialog2.setNegativeButton("Editar Dados No Mesmo Poste", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new MesmoPosteTask().execute();

                            }
                        });
                        dialog2.create();
                        dialog2.show();
                    }
                });
                dialog.setNegativeButton("Inisirir Outro Ocupante Ao Mesmo Poste", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new OutroOcupanteTask().execute();

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

        vinteEQuatro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if (vinteEQuatro.isChecked()) {
                                                            quantidade24H.setEnabled(true);
                                                        } else {
                                                            quantidade24H.setEnabled(false);
                                                            quantidade24H.setText("");
                                                        }
                                                    }
                                                }
        );


        //Listener para só habilitar os dados da própria ip e as próximas ip caso a primeira
        // tenha sido checada
        ip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ip.isChecked()) {
                    ipEstrutura.setEnabled(true);
                    tipoPot.setEnabled(true);
                    potReator.setEnabled(true);
                    quantidadeLampada.setEnabled(true);
                    ipAtivacao.setEnabled(true);
                    vinteEQuatro.setEnabled(true);
                    fotoIP.setEnabled(true);
                    fotoIP.setVisibility(View.VISIBLE);
                    btnFoto4.setVisibility(View.VISIBLE);
                    btnUpload4.setVisibility(View.VISIBLE);
                    textLampada.setVisibility(View.VISIBLE);
                    relativeIPTipoEstrutura.setVisibility(View.VISIBLE);
                    relativeIPTipoLampada.setVisibility(View.VISIBLE);
                    relativeIPTipoAtivacao.setVisibility(View.VISIBLE);

                    ipEstrutura.setVisibility(View.VISIBLE);
                    tipoPot.setVisibility(View.VISIBLE);
                    potReator.setVisibility(View.VISIBLE);
                    quantidadeLampada.setVisibility(View.VISIBLE);
                    ipAtivacao.setVisibility(View.VISIBLE);
                    vinteEQuatro.setVisibility(View.VISIBLE);
                    quantidade24H.setVisibility(View.VISIBLE);
                } else {
                    ipEstrutura.setEnabled(false);
                    quantidadeLampada.setEnabled(false);
                    quantidadeLampada.setText("0");
                    tipoPot.setEnabled(false);
                    tipoPot.setSelection(0);
                    potReator.setEnabled(false);
                    quantidadeLampada.setText("0");
                    ipEstrutura.setSelection(0);
                    ipAtivacao.setEnabled(false);
                    vinteEQuatro.setEnabled(false);
                    vinteEQuatro.setChecked(false);
                    ipAtivacao.setSelection(0);
                    fotoIP.setVisibility(View.GONE);
                    btnFoto4.setVisibility(View.GONE);
                    btnUpload4.setVisibility(View.GONE);
                    textLampada.setVisibility(View.GONE);

                    ipEstrutura.setVisibility(View.GONE);
                    tipoPot.setVisibility(View.GONE);
                    potReator.setVisibility(View.GONE);
                    quantidadeLampada.setVisibility(View.GONE);
                    quantidade24H.setVisibility(View.GONE);
                    ipAtivacao.setVisibility(View.GONE);
                    vinteEQuatro.setVisibility(View.GONE);
                    relativeIPTipoEstrutura.setVisibility(View.GONE);
                    relativeIPTipoLampada.setVisibility(View.GONE);
                    relativeIPTipoAtivacao.setVisibility(View.GONE);
                }
            }
        });

        //Listener para só habilitar os dados da própria ip e as próximas ip caso a segunda
        // tenha sido checada
        ativos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ativos.isChecked()) {
                    chkTrafoTrifasico.setEnabled(true);
                    chkTrafoMono.setEnabled(true);
                    religador.setEnabled(true);
                    medicao.setEnabled(true);
                    chFusivel.setEnabled(true);
                    chFaca.setEnabled(true);
                    chFusivelReligador.setEnabled(true);
                    chBanco.setEnabled(true);
                    ramalSubt.setEnabled(true);
                    outros.setEnabled(true);

                    fotoAtivos.setEnabled(true);
                    fotoAtivos.setVisibility(View.VISIBLE);
                    btnFoto13.setVisibility(View.VISIBLE);
                    btnUpload13.setVisibility(View.VISIBLE);
                    fotoAtivos2.setEnabled(true);
                    fotoAtivos2.setVisibility(View.VISIBLE);
                    btnFoto14.setVisibility(View.VISIBLE);
                    btnUpload14.setVisibility(View.VISIBLE);

                    trafoMono.setVisibility(View.VISIBLE);
                    trafoTrifasico.setVisibility(View.VISIBLE);
                    chkTrafoTrifasico.setVisibility(View.VISIBLE);
                    chkTrafoMono.setVisibility(View.VISIBLE);
                    religador.setVisibility(View.VISIBLE);
                    medicao.setVisibility(View.VISIBLE);
                    chFusivel.setVisibility(View.VISIBLE);
                    chFaca.setVisibility(View.VISIBLE);
                    chFusivelReligador.setVisibility(View.VISIBLE);
                    chBanco.setVisibility(View.VISIBLE);
                    ramalSubt.setVisibility(View.VISIBLE);
                    textOutros.setVisibility(View.VISIBLE);
                    relativeBanco.setVisibility(View.VISIBLE);
                    relativeRamal.setVisibility(View.VISIBLE);
                    relativeChFaca.setVisibility(View.VISIBLE);
                    outros.setVisibility(View.VISIBLE);
                    textFusivel.setVisibility(View.VISIBLE);
                    textFusivelReligador.setVisibility(View.VISIBLE);
                } else {
                    chkTrafoTrifasico.setChecked(false);
                    chkTrafoTrifasico.setEnabled(false);
                    chkTrafoMono.setChecked(false);
                    chkTrafoMono.setEnabled(false);
                    chkTrafoTrifasico.setChecked(false);
                    trafoTrifasico.setSelection(0);
                    trafoTrifasico.setEnabled(false);
                    trafoMono.setSelection(0);
                    trafoMono.setEnabled(false);
                    religador.setEnabled(false);
                    religador.setChecked(false);
                    medicao.setEnabled(false);
                    medicao.setChecked(false);
                    chFusivel.setText("0");
                    chFaca.setEnabled(false);
                    chFaca.setSelection(0);
                    chFusivelReligador.setEnabled(false);
                    chFusivelReligador.setText("0");
                    chBanco.setEnabled(false);
                    chBanco.setSelection(0);
                    ramalSubt.setSelection(0);
                    ramalSubt.setEnabled(false);
                    outros.setEnabled(false);

                    trafoMono.setVisibility(View.GONE);
                    trafoTrifasico.setVisibility(View.GONE);
                    chkTrafoTrifasico.setVisibility(View.GONE);
                    chkTrafoMono.setVisibility(View.GONE);
                    religador.setVisibility(View.GONE);
                    medicao.setVisibility(View.GONE);
                    chFusivel.setVisibility(View.GONE);
                    textOutros.setVisibility(View.GONE);
                    chFaca.setVisibility(View.GONE);
                    chFusivelReligador.setVisibility(View.GONE);
                    chBanco.setVisibility(View.GONE);
                    ramalSubt.setVisibility(View.GONE);
                    outros.setVisibility(View.GONE);
                    fotoAtivos.setVisibility(View.GONE);
                    btnFoto13.setVisibility(View.GONE);
                    btnUpload13.setVisibility(View.GONE);
                    fotoAtivos2.setVisibility(View.GONE);
                    btnFoto14.setVisibility(View.GONE);
                    btnUpload14.setVisibility(View.GONE);
                    relativeBanco.setVisibility(View.GONE);
                    relativeRamal.setVisibility(View.GONE);
                    relativeChFaca.setVisibility(View.GONE);
                    textFusivel.setVisibility(View.GONE);
                    textFusivelReligador.setVisibility(View.GONE);
                }
            }
        });

        chkTrafoTrifasico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkTrafoTrifasico.isChecked()) {
                    chkTrafoMono.setChecked(false);
                    trafoMono.setSelection(0);
                    trafoMono.setEnabled(false);
                    trafoTrifasico.setEnabled(true);
                } else {
                    trafoTrifasico.setSelection(0);
                    trafoTrifasico.setEnabled(false);
                }

            }
        });
        chkTrafoMono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkTrafoMono.isChecked()) {
                    chkTrafoTrifasico.setChecked(false);
                    trafoTrifasico.setSelection(0);
                    trafoTrifasico.setEnabled(false);
                    trafoMono.setEnabled(true);
                } else {
                    trafoMono.setSelection(0);
                    trafoMono.setEnabled(false);
                }

            }
        });

        mutuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mutuo.isChecked()) {
                    mutuoDados(quantidadeCabos, null, true);
                    mutuoDados(null, tipoCabo, true);
                    mutuoDados(quantidadeCabosdois, null, true);
                    mutuoDados(null, tipoCabodois, true);
                    nome.setVisibility(View.VISIBLE);
                    mutuoDados(null, finalidade, true);
                    mutuoDados(null, ceans, true);
                    mutuoDados(null, tar, true);
                    mutuoDados(null, reservaTec, true);
                    mutuoDados(null, backbone, true);
                    mutuoDados(descricaoIrregularidade, null, true);
                    mutuoDados(observacaoMutuo, null, true);
                    placaIdentificadora.setEnabled(true);
                    placaIdentificadora.setVisibility(View.VISIBLE);
                    descidaCabos.setVisibility(View.VISIBLE);
                    descidaCabos.setEnabled(true);
                    fotoMutuo.setVisibility(View.VISIBLE);
                    btnFoto15.setVisibility(View.VISIBLE);
                    btnUpload15.setVisibility(View.VISIBLE);
                    fotoMutuo2.setVisibility(View.VISIBLE);
                    btnFoto16.setVisibility(View.VISIBLE);
                    btnUpload16.setVisibility(View.VISIBLE);
                    fotoMutuo3.setVisibility(View.VISIBLE);
                    btnFoto17.setVisibility(View.VISIBLE);
                    btnUpload17.setVisibility(View.VISIBLE);
                    textOcupante.setVisibility(View.VISIBLE);
                    quantidadeOcupantes.setVisibility(View.VISIBLE);
                    textQuantidadeCabo.setVisibility(View.VISIBLE);
                    textQuantidadeCabodois.setVisibility(View.VISIBLE);
                    textNome.setVisibility(View.VISIBLE);
                    textIrregularidade.setVisibility(View.VISIBLE);
                    relativeFinalidade.setVisibility(View.VISIBLE);
                    relativeCeans.setVisibility(View.VISIBLE);
                    relativeTar.setVisibility(View.VISIBLE);
                    relativeReservaTec.setVisibility(View.VISIBLE);
                    relativeBackbone.setVisibility(View.VISIBLE);
                    relativeTipoCabo.setVisibility(View.VISIBLE);
                    relativeTipoCabodois.setVisibility(View.VISIBLE);
                } else {
                    mutuoDados(quantidadeCabos, null, false);
                    mutuoDados(quantidadeCabosdois, null, false);
                    mutuoDados(null, tipoCabo, false);
                    mutuoDados(null, tipoCabodois, false);
                    nome.setVisibility(View.GONE);
                    mutuoDados(null, finalidade, false);
                    mutuoDados(null, ceans, false);
                    mutuoDados(null, tar, false);
                    mutuoDados(null, reservaTec, false);
                    mutuoDados(null, backbone, false);
                    mutuoDados(descricaoIrregularidade, null, false);
                    mutuoDados(observacaoMutuo, null, false);
                    placaIdentificadora.setChecked(false);
                    placaIdentificadora.setEnabled(false);
                    placaIdentificadora.setVisibility(View.GONE);
                    descidaCabos.setChecked(false);
                    descidaCabos.setEnabled(false);
                    descidaCabos.setVisibility(View.GONE);
                    fotoMutuo.setVisibility(View.GONE);
                    btnFoto15.setVisibility(View.GONE);
                    btnUpload15.setVisibility(View.GONE);
                    fotoMutuo2.setVisibility(View.GONE);
                    btnFoto16.setVisibility(View.GONE);
                    btnUpload16.setVisibility(View.GONE);
                    fotoMutuo3.setVisibility(View.GONE);
                    btnFoto17.setVisibility(View.GONE);
                    btnUpload17.setVisibility(View.GONE);
                    textOcupante.setVisibility(View.GONE);
                    quantidadeOcupantes.setVisibility(View.GONE);
                    textQuantidadeCabo.setVisibility(View.GONE);
                    textQuantidadeCabodois.setVisibility(View.GONE);
                    textNome.setVisibility(View.GONE);
                    textIrregularidade.setVisibility(View.GONE);
                    relativeFinalidade.setVisibility(View.GONE);
                    relativeCeans.setVisibility(View.GONE);
                    relativeTar.setVisibility(View.GONE);
                    relativeReservaTec.setVisibility(View.GONE);
                    relativeBackbone.setVisibility(View.GONE);
                    relativeTipoCabo.setVisibility(View.GONE);
                    relativeTipoCabodois.setVisibility(View.GONE);
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
            } catch (Exception ex) {
            }
        } else {
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
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

    public void upload(String imgPathUpload, EditText codigoUpload, int contadorUpload, final int codigoSetor, String sufixo) {
        if (imgPathUpload == null) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(requireActivity().getApplicationContext(), "Escolha primeiro uma foto para fazer o upload", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (codigoUpload.getText().toString().equals("") || codigoUpload == null) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(requireActivity().getApplicationContext(), "Insira o código do poste primeiro", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            if (isNetworkAvailable()) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(requireContext(), R.style.LightDialogTheme);
                        progressDialog.setMessage("Carregando dados..."); // Setting Message
                        progressDialog.setTitle("Por favor Espere"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
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
                                Toast.makeText(requireActivity().getApplicationContext(), "Erro ao fazer upload verifique a conexão com a internet", Toast.LENGTH_SHORT).show();
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
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 2:
                                        urlFotoAlt = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 3:
                                        urlFotoQualidade = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 4:
                                        urlFotoIP = task.getResult();
                                        contadorIp = contadorIp + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 5:
                                        urlFotoAtivos = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 6:
                                        urlFotoAtivos2 = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 7:
                                        urlFotoMutuo = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 8:
                                        urlFotoMutuo2 = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 9:
                                        urlFotoMutuo3 = task.getResult();
                                        contadorAt = contadorAt + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
                                        break;
                                    case 10:
                                        Log.i("ARVORE", "2");
                                        urlFotoVeg = task.getResult();
                                        contadorAr = contadorAr + 1;
                                        btn.setText("Enviado");
                                        progressDialog.dismiss();
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
                        Toast.makeText(requireActivity().getApplicationContext(), "Erro ao fazer upload verifique a conexão com a internet", Toast.LENGTH_SHORT).show();
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

    class ThreadOutroOcupante extends Thread {
        private View root;

        public ThreadOutroOcupante(View root) {
            this.root = root;
        }

        @Override
        public void run() {
            novoOcupante(this.root);
        }
    }

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

                if (formularioAtual.getIp().equals("Sim")) {
                    ip.setChecked(true);
                    ipEstrutura.setVisibility(View.VISIBLE);
                    tipoPot.setVisibility(View.VISIBLE);
                    potReator.setVisibility(View.VISIBLE);
                    quantidadeLampada.setVisibility(View.VISIBLE);
                    ipAtivacao.setVisibility(View.VISIBLE);
                    vinteEQuatro.setVisibility(View.VISIBLE);
                    quantidade24H.setVisibility(View.VISIBLE);
                    fotoIP.setVisibility(View.VISIBLE);
                    btnFoto4.setVisibility(View.VISIBLE);
                    btnUpload4.setVisibility(View.VISIBLE);
                    textLampada.setVisibility(View.VISIBLE);
                    relativeIPTipoEstrutura.setVisibility(View.VISIBLE);
                    relativeIPTipoLampada.setVisibility(View.VISIBLE);
                    relativeIPTipoAtivacao.setVisibility(View.VISIBLE);
                    if (formularioAtual.getIpEstrutura().equals("-")) {
                        ipEstrutura.setSelection(0);
                    } else {
                        for (int i = 0; i < ipEstrutura.getAdapter().getCount(); i++) {
                            ipEstrutura.setSelection(i);
                            if (ipEstrutura.getSelectedItem().toString().equals(formularioAtual.getIpEstrutura())) {
                                break;
                            }
                        }
                    }
                    quantidadeLampada.setText(formularioAtual.getQuantidadeLampada());
                    if (formularioAtual.getTipoPot().equals("-")) {
                        tipoPot.setSelection(0);
                    } else {
                        for (int i = 0; i < tipoPot.getAdapter().getCount(); i++) {
                            tipoPot.setSelection(i);
                            if (tipoPot.getSelectedItem().toString().equals(formularioAtual.getTipoPot())) {
                                break;
                            }
                        }
                    }
                    potReator.setText(formularioAtual.getPotReator());
                    if (formularioAtual.getIpAtivacao().equals("-")) {
                        ipAtivacao.setSelection(0);
                    } else {
                        for (int i = 0; i < ipAtivacao.getAdapter().getCount(); i++) {
                            ipAtivacao.setSelection(i);
                            if (ipAtivacao.getSelectedItem().toString().equals(formularioAtual.getIpAtivacao())) {
                                break;
                            }
                        }
                    }
                    if (formularioAtual.getVinteEQuatro().equals("Sim")) {
                        vinteEQuatro.setChecked(true);
                        quantidade24H.setEnabled(true);
                    }
                    quantidade24H.setText(formularioAtual.getQuantidade24H());
                    ipEstrutura.setEnabled(true);
                    quantidadeLampada.setEnabled(true);
                    tipoPot.setEnabled(true);
                    potReator.setEnabled(true);
                    ipAtivacao.setEnabled(true);
                    vinteEQuatro.setEnabled(true);
                }
                observacaoIP.setText(formularioAtual.getObservacaoIP());


                //TRAFO

                if (formularioAtual.getAtivos().equals("Sim")) {
                    ativos.setChecked(true);
                    chkTrafoMono.setEnabled(true);
                    chkTrafoTrifasico.setEnabled(true);
                    trafoMono.setEnabled(true);
                    trafoTrifasico.setEnabled(true);
                    chFusivel.setEnabled(true);
                    chFaca.setEnabled(true);
                    religador.setEnabled(true);
                    medicao.setEnabled(true);
                    chBanco.setEnabled(true);
                    chFusivelReligador.setEnabled(true);
                    ramalSubt.setEnabled(true);
                    textOutros.setVisibility(View.VISIBLE);
                    outros.setEnabled(true);

                    ativos.setVisibility(View.VISIBLE);
                    chkTrafoMono.setVisibility(View.VISIBLE);
                    chkTrafoTrifasico.setVisibility(View.VISIBLE);
                    trafoMono.setVisibility(View.VISIBLE);
                    trafoTrifasico.setVisibility(View.VISIBLE);
                    chFusivel.setVisibility(View.VISIBLE);
                    chFaca.setVisibility(View.VISIBLE);
                    religador.setVisibility(View.VISIBLE);
                    medicao.setVisibility(View.VISIBLE);
                    chBanco.setVisibility(View.VISIBLE);
                    chFusivelReligador.setVisibility(View.VISIBLE);
                    ramalSubt.setVisibility(View.VISIBLE);
                    outros.setVisibility(View.VISIBLE);
                    fotoAtivos.setVisibility(View.VISIBLE);
                    btnFoto13.setVisibility(View.VISIBLE);
                    btnUpload13.setVisibility(View.VISIBLE);
                    fotoAtivos2.setVisibility(View.VISIBLE);
                    btnFoto14.setVisibility(View.VISIBLE);
                    btnUpload14.setVisibility(View.VISIBLE);
                    relativeBanco.setVisibility(View.VISIBLE);
                    relativeRamal.setVisibility(View.VISIBLE);
                    relativeChFaca.setVisibility(View.VISIBLE);
                    textFusivel.setVisibility(View.VISIBLE);
                    textFusivelReligador.setVisibility(View.VISIBLE);
                }
                if (formularioAtual.getChkTrafoTrifasico().equals("Sim")) {
                    chkTrafoTrifasico.setChecked(true);
                }
                if (formularioAtual.getChkTrafoMono().equals("Sim")) {
                    chkTrafoMono.setChecked(true);
                }
                if (formularioAtual.getTrafoTrifasico().equals("-")) {
                    trafoTrifasico.setSelection(0);
                } else {
                    for (int i = 0; i < trafoTrifasico.getAdapter().getCount(); i++) {
                        trafoTrifasico.setSelection(i);
                        if (trafoTrifasico.getSelectedItem().toString().equals(formularioAtual.getTrafoTrifasico())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getTrafoMono().equals("-")) {
                    trafoMono.setSelection(0);
                } else {
                    for (int i = 0; i < trafoMono.getAdapter().getCount(); i++) {
                        trafoMono.setSelection(i);
                        if (trafoMono.getSelectedItem().toString().equals(formularioAtual.getTrafoMono())) {
                            break;
                        }
                    }
                }

                if (formularioAtual.getReligador().equals("Sim")) {
                    religador.setChecked(true);
                }
                if (formularioAtual.getMedicao().equals("Sim")) {
                    medicao.setChecked(true);
                }
                chFusivel.setText(formularioAtual.getChFusivel());
                chFusivelReligador.setText(formularioAtual.getChFusivelReligador());
                if (formularioAtual.getBanco().equals("-")) {
                    chBanco.setSelection(0);
                } else {
                    for (int i = 0; i < chBanco.getAdapter().getCount(); i++) {
                        chBanco.setSelection(i);
                        if (chBanco.getSelectedItem().toString().equals(formularioAtual.getBanco())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getChFaca().equals("-")) {
                    chFaca.setSelection(0);
                } else {
                    for (int i = 0; i < chFaca.getAdapter().getCount(); i++) {
                        chFaca.setSelection(i);
                        if (chFaca.getSelectedItem().toString().equals(formularioAtual.getChFaca())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getRamalSubt().equals("-")) {
                    ramalSubt.setSelection(0);
                } else {
                    for (int i = 0; i < ramalSubt.getAdapter().getCount(); i++) {
                        ramalSubt.setSelection(i);
                        if (ramalSubt.getSelectedItem().toString().equals(formularioAtual.getRamalSubt())) {
                            break;
                        }
                    }
                }
                observacaoAtivos.setText(formularioAtual.getObservacaoAtivos());
                outros.setText(formularioAtual.getOutros());

                //MUTUO
                if (formularioAtual.getMutuo().equals("Sim")) {
                    mutuo.setChecked(true);
                    fotoMutuo.setVisibility(View.VISIBLE);
                    btnFoto15.setVisibility(View.VISIBLE);
                    btnUpload15.setVisibility(View.VISIBLE);

                    fotoMutuo2.setVisibility(View.VISIBLE);
                    btnFoto16.setVisibility(View.VISIBLE);
                    btnUpload16.setVisibility(View.VISIBLE);

                    fotoMutuo3.setVisibility(View.VISIBLE);
                    btnFoto17.setVisibility(View.VISIBLE);
                    btnUpload17.setVisibility(View.VISIBLE);
                    textOcupante.setVisibility(View.VISIBLE);
                    quantidadeOcupantes.setVisibility(View.VISIBLE);
                    quantidadeCabos.setVisibility(View.VISIBLE);
                    tipoCabo.setVisibility(View.VISIBLE);
                    quantidadeCabosdois.setVisibility(View.VISIBLE);
                    tipoCabodois.setVisibility(View.VISIBLE);
                    nome.setVisibility(View.VISIBLE);
                    finalidade.setVisibility(View.VISIBLE);
                    ceans.setVisibility(View.VISIBLE);
                    tar.setVisibility(View.VISIBLE);
                    reservaTec.setVisibility(View.VISIBLE);
                    backbone.setVisibility(View.VISIBLE);
                    placaIdentificadora.setVisibility(View.VISIBLE);
                    descidaCabos.setVisibility(View.VISIBLE);
                    descricaoIrregularidade.setVisibility(View.VISIBLE);
                    textQuantidadeCabo.setVisibility(View.VISIBLE);
                    textQuantidadeCabodois.setVisibility(View.VISIBLE);
                    textNome.setVisibility(View.VISIBLE);
                    textIrregularidade.setVisibility(View.VISIBLE);
                    relativeFinalidade.setVisibility(View.VISIBLE);
                    relativeCeans.setVisibility(View.VISIBLE);
                    relativeTar.setVisibility(View.VISIBLE);
                    relativeReservaTec.setVisibility(View.VISIBLE);
                    relativeBackbone.setVisibility(View.VISIBLE);
                    relativeTipoCabo.setVisibility(View.VISIBLE);
                    relativeTipoCabodois.setVisibility(View.VISIBLE);


                }
                //1
                quantidadeOcupantes.setText(formularioAtual.getQuantidadeOcupantes());
                quantidadeCabos.setText(formularioAtual.getQuantidadeCabos());
                if (formularioAtual.getTipoCabo().equals("-")) {
                    tipoCabo.setSelection(0);
                } else {
                    for (int i = 0; i < tipoCabo.getAdapter().getCount(); i++) {
                        tipoCabo.setSelection(i);
                        if (tipoCabo.getSelectedItem().toString().equals(formularioAtual.getTipoCabo())) {
                            break;
                        }
                    }
                }
                quantidadeCabosdois.setText(formularioAtual.getQuantidadeCabosdois());
                if (formularioAtual.getTipoCabodois().equals("-")) {
                    tipoCabodois.setSelection(0);
                } else {
                    for (int i = 0; i < tipoCabodois.getAdapter().getCount(); i++) {
                        tipoCabodois.setSelection(i);
                        if (tipoCabodois.getSelectedItem().toString().equals(formularioAtual.getTipoCabodois())) {
                            break;
                        }
                    }
                }
                nome.setText(formularioAtual.getNome());
                if (formularioAtual.getFinalidade().equals("-")) {
                    finalidade.setSelection(0);
                } else {
                    for (int i = 0; i < finalidade.getAdapter().getCount(); i++) {
                        finalidade.setSelection(i);
                        if (finalidade.getSelectedItem().toString().equals(formularioAtual.getFinalidade())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getCeans().equals("-")) {
                    ceans.setSelection(0);
                } else {
                    for (int i = 0; i < ceans.getAdapter().getCount(); i++) {
                        ceans.setSelection(i);
                        if (ceans.getSelectedItem().toString().equals(formularioAtual.getCeans())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getTar().equals("-")) {
                    tar.setSelection(0);
                } else {
                    for (int i = 0; i < tar.getAdapter().getCount(); i++) {
                        tar.setSelection(i);
                        if (tar.getSelectedItem().toString().equals(formularioAtual.getTar())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getReservaTec().equals("-")) {
                    reservaTec.setSelection(0);
                } else {
                    for (int i = 0; i < reservaTec.getAdapter().getCount(); i++) {
                        reservaTec.setSelection(i);
                        if (reservaTec.getSelectedItem().toString().equals(formularioAtual.getReservaTec())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getBackbone().equals("-")) {
                    backbone.setSelection(0);
                } else {
                    for (int i = 0; i < backbone.getAdapter().getCount(); i++) {
                        backbone.setSelection(i);
                        if (backbone.getSelectedItem().toString().equals(formularioAtual.getBackbone())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getPlacaIdent().equals("Sim")) {
                    placaIdentificadora.setChecked(true);
                }
                if (formularioAtual.getDescidaCabos().equals("Sim")) {
                    descidaCabos.setChecked(true);
                }
                descricaoIrregularidade.setText(formularioAtual.getDescricaoIrregularidade());
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


                if ((urlFotoPan == null) || ((urlFotoPan.toString()).equals(""))) {

                } else {
                    btnUpload.setText("Enviado");
                }
                if ((urlFotoAlt == null) || ((urlFotoAlt.toString()).equals(""))) {

                } else {
                    btnUpload2.setText("Enviado");
                }
                if ((urlFotoQualidade == null) || ((urlFotoQualidade.toString()).equals(""))) {

                } else {
                    btnUpload3.setText("Enviado");
                }
                if ((urlFotoIP == null) || ((urlFotoIP.toString()).equals(""))) {

                } else {
                    btnUpload4.setText("Enviado");
                }
                if ((urlFotoAtivos == null) || ((urlFotoAtivos.toString()).equals(""))) {

                } else {
                    btnUpload13.setText("Enviado");
                }
                if ((urlFotoAtivos2 == null) || ((urlFotoAtivos2.toString()).equals(""))) {

                } else {
                    btnUpload14.setText("Enviado");
                }
                if ((urlFotoMutuo == null) || ((urlFotoMutuo.toString()).equals(""))) {

                } else {
                    btnUpload15.setText("Enviado");
                }
                if ((urlFotoMutuo2 == null) || ((urlFotoMutuo2.toString()).equals(""))) {

                } else {
                    btnUpload16.setText("Enviado");
                }
                if ((urlFotoMutuo3 == null) || ((urlFotoMutuo3.toString()).equals(""))) {

                } else {
                    btnUpload17.setText("Enviado");
                }
                if ((urlFotoVeg == null) || ((urlFotoVeg.toString()).equals(""))) {

                } else {
                    btnUpload30.setText("Enviado");
                }

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
                    ipEstrutura.setVisibility(View.VISIBLE);
                    tipoPot.setVisibility(View.VISIBLE);
                    potReator.setVisibility(View.VISIBLE);
                    quantidadeLampada.setVisibility(View.VISIBLE);
                    ipAtivacao.setVisibility(View.VISIBLE);
                    vinteEQuatro.setVisibility(View.VISIBLE);
                    quantidade24H.setVisibility(View.VISIBLE);
                    fotoIP.setVisibility(View.VISIBLE);
                    btnFoto4.setVisibility(View.VISIBLE);
                    btnUpload4.setVisibility(View.VISIBLE);
                    textLampada.setVisibility(View.VISIBLE);
                    relativeIPTipoEstrutura.setVisibility(View.VISIBLE);
                    relativeIPTipoLampada.setVisibility(View.VISIBLE);
                    relativeIPTipoAtivacao.setVisibility(View.VISIBLE);
                    if (formularioAtual.getIpEstrutura().equals("-")) {
                        ipEstrutura.setSelection(0);
                    } else {
                        for (int i = 0; i < ipEstrutura.getAdapter().getCount(); i++) {
                            ipEstrutura.setSelection(i);
                            if (ipEstrutura.getSelectedItem().toString().equals(formularioAtual.getIpEstrutura())) {
                                break;
                            }
                        }
                    }
                    quantidadeLampada.setText(formularioAtual.getQuantidadeLampada());
                    if (formularioAtual.getTipoPot().equals("-")) {
                        tipoPot.setSelection(0);
                    } else {
                        for (int i = 0; i < tipoPot.getAdapter().getCount(); i++) {
                            tipoPot.setSelection(i);
                            if (tipoPot.getSelectedItem().toString().equals(formularioAtual.getTipoPot())) {
                                break;
                            }
                        }
                    }
                    potReator.setText(formularioAtual.getPotReator());
                    if (formularioAtual.getIpAtivacao().equals("-")) {
                        ipAtivacao.setSelection(0);
                    } else {
                        for (int i = 0; i < ipAtivacao.getAdapter().getCount(); i++) {
                            ipAtivacao.setSelection(i);
                            if (ipAtivacao.getSelectedItem().toString().equals(formularioAtual.getIpAtivacao())) {
                                break;
                            }
                        }
                    }
                    if (formularioAtual.getVinteEQuatro().equals("Sim")) {
                        vinteEQuatro.setChecked(true);
                        quantidade24H.setEnabled(true);
                    }
                    quantidade24H.setText(formularioAtual.getQuantidade24H());
                    ipEstrutura.setEnabled(true);
                    quantidadeLampada.setEnabled(true);
                    tipoPot.setEnabled(true);
                    potReator.setEnabled(true);
                    ipAtivacao.setEnabled(true);
                    vinteEQuatro.setEnabled(true);
                }
                observacaoIP.setText(formularioAtual.getObservacaoIP());


                //TRAFO

                if (formularioAtual.getAtivos().equals("Sim")) {
                    ativos.setChecked(true);
                    chkTrafoMono.setEnabled(true);
                    chkTrafoTrifasico.setEnabled(true);
                    trafoMono.setEnabled(true);
                    trafoTrifasico.setEnabled(true);
                    chFusivel.setEnabled(true);
                    chFaca.setEnabled(true);
                    religador.setEnabled(true);
                    medicao.setEnabled(true);
                    chBanco.setEnabled(true);
                    chFusivelReligador.setEnabled(true);
                    ramalSubt.setEnabled(true);
                    textOutros.setVisibility(View.VISIBLE);
                    outros.setEnabled(true);

                    ativos.setVisibility(View.VISIBLE);
                    chkTrafoMono.setVisibility(View.VISIBLE);
                    chkTrafoTrifasico.setVisibility(View.VISIBLE);
                    trafoMono.setVisibility(View.VISIBLE);
                    trafoTrifasico.setVisibility(View.VISIBLE);
                    chFusivel.setVisibility(View.VISIBLE);
                    chFaca.setVisibility(View.VISIBLE);
                    religador.setVisibility(View.VISIBLE);
                    medicao.setVisibility(View.VISIBLE);
                    chBanco.setVisibility(View.VISIBLE);
                    chFusivelReligador.setVisibility(View.VISIBLE);
                    ramalSubt.setVisibility(View.VISIBLE);
                    outros.setVisibility(View.VISIBLE);
                    fotoAtivos.setVisibility(View.VISIBLE);
                    btnFoto13.setVisibility(View.VISIBLE);
                    btnUpload13.setVisibility(View.VISIBLE);
                    fotoAtivos2.setVisibility(View.VISIBLE);
                    btnFoto14.setVisibility(View.VISIBLE);
                    btnUpload14.setVisibility(View.VISIBLE);
                    relativeBanco.setVisibility(View.VISIBLE);
                    relativeRamal.setVisibility(View.VISIBLE);
                    relativeChFaca.setVisibility(View.VISIBLE);
                    textFusivel.setVisibility(View.VISIBLE);
                    textFusivelReligador.setVisibility(View.VISIBLE);
                }
                if (formularioAtual.getChkTrafoTrifasico().equals("Sim")) {
                    chkTrafoTrifasico.setChecked(true);
                }
                if (formularioAtual.getChkTrafoMono().equals("Sim")) {
                    chkTrafoMono.setChecked(true);
                }
                if (formularioAtual.getTrafoTrifasico().equals("-")) {
                    trafoTrifasico.setSelection(0);
                } else {
                    for (int i = 0; i < trafoTrifasico.getAdapter().getCount(); i++) {
                        trafoTrifasico.setSelection(i);
                        if (trafoTrifasico.getSelectedItem().toString().equals(formularioAtual.getTrafoTrifasico())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getTrafoMono().equals("-")) {
                    trafoMono.setSelection(0);
                } else {
                    for (int i = 0; i < trafoMono.getAdapter().getCount(); i++) {
                        trafoMono.setSelection(i);
                        if (trafoMono.getSelectedItem().toString().equals(formularioAtual.getTrafoMono())) {
                            break;
                        }
                    }
                }

                if (formularioAtual.getReligador().equals("Sim")) {
                    religador.setChecked(true);
                }
                if (formularioAtual.getMedicao().equals("Sim")) {
                    medicao.setChecked(true);
                }
                chFusivel.setText(formularioAtual.getChFusivel());
                chFusivelReligador.setText(formularioAtual.getChFusivelReligador());
                if (formularioAtual.getBanco().equals("-")) {
                    chBanco.setSelection(0);
                } else {
                    for (int i = 0; i < chBanco.getAdapter().getCount(); i++) {
                        chBanco.setSelection(i);
                        if (chBanco.getSelectedItem().toString().equals(formularioAtual.getBanco())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getChFaca().equals("-")) {
                    chFaca.setSelection(0);
                } else {
                    for (int i = 0; i < chFaca.getAdapter().getCount(); i++) {
                        chFaca.setSelection(i);
                        if (chFaca.getSelectedItem().toString().equals(formularioAtual.getChFaca())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getRamalSubt().equals("-")) {
                    ramalSubt.setSelection(0);
                } else {
                    for (int i = 0; i < ramalSubt.getAdapter().getCount(); i++) {
                        ramalSubt.setSelection(i);
                        if (ramalSubt.getSelectedItem().toString().equals(formularioAtual.getRamalSubt())) {
                            break;
                        }
                    }
                }
                observacaoAtivos.setText(formularioAtual.getObservacaoAtivos());
                outros.setText(formularioAtual.getOutros());

                //MUTUO
                if (formularioAtual.getMutuo().equals("Sim")) {
                    mutuo.setChecked(true);
                    fotoMutuo.setVisibility(View.VISIBLE);
                    btnFoto15.setVisibility(View.VISIBLE);
                    btnUpload15.setVisibility(View.VISIBLE);

                    fotoMutuo2.setVisibility(View.VISIBLE);
                    btnFoto16.setVisibility(View.VISIBLE);
                    btnUpload16.setVisibility(View.VISIBLE);

                    fotoMutuo3.setVisibility(View.VISIBLE);
                    btnFoto17.setVisibility(View.VISIBLE);
                    btnUpload17.setVisibility(View.VISIBLE);
                    quantidadeOcupantes.setVisibility(View.VISIBLE);
                    quantidadeCabos.setVisibility(View.VISIBLE);
                    tipoCabo.setVisibility(View.VISIBLE);
                    quantidadeCabosdois.setVisibility(View.VISIBLE);
                    tipoCabodois.setVisibility(View.VISIBLE);
                    nome.setVisibility(View.VISIBLE);
                    finalidade.setVisibility(View.VISIBLE);
                    ceans.setVisibility(View.VISIBLE);
                    tar.setVisibility(View.VISIBLE);
                    reservaTec.setVisibility(View.VISIBLE);
                    backbone.setVisibility(View.VISIBLE);
                    placaIdentificadora.setVisibility(View.VISIBLE);
                    descidaCabos.setVisibility(View.VISIBLE);
                    descricaoIrregularidade.setVisibility(View.VISIBLE);
                    textQuantidadeCabo.setVisibility(View.VISIBLE);
                    textQuantidadeCabodois.setVisibility(View.VISIBLE);
                    textNome.setVisibility(View.VISIBLE);
                    textIrregularidade.setVisibility(View.VISIBLE);
                    relativeFinalidade.setVisibility(View.VISIBLE);
                    relativeCeans.setVisibility(View.VISIBLE);
                    relativeTar.setVisibility(View.VISIBLE);
                    relativeReservaTec.setVisibility(View.VISIBLE);
                    relativeBackbone.setVisibility(View.VISIBLE);
                    relativeTipoCabo.setVisibility(View.VISIBLE);
                    relativeTipoCabodois.setVisibility(View.VISIBLE);


                }
                //1
                quantidadeOcupantes.setText(formularioAtual.getQuantidadeOcupantes());
                quantidadeCabos.setText(formularioAtual.getQuantidadeCabos());
                if (formularioAtual.getTipoCabo().equals("-")) {
                    tipoCabo.setSelection(0);
                } else {
                    for (int i = 0; i < tipoCabo.getAdapter().getCount(); i++) {
                        tipoCabo.setSelection(i);
                        if (tipoCabo.getSelectedItem().toString().equals(formularioAtual.getTipoCabo())) {
                            break;
                        }
                    }
                }
                quantidadeCabosdois.setText(formularioAtual.getQuantidadeCabosdois());
                if (formularioAtual.getTipoCabodois().equals("-")) {
                    tipoCabodois.setSelection(0);
                } else {
                    for (int i = 0; i < tipoCabodois.getAdapter().getCount(); i++) {
                        tipoCabodois.setSelection(i);
                        if (tipoCabodois.getSelectedItem().toString().equals(formularioAtual.getTipoCabodois())) {
                            break;
                        }
                    }
                }
                nome.setText(formularioAtual.getNome());
                if (formularioAtual.getFinalidade().equals("-")) {
                    finalidade.setSelection(0);
                } else {
                    for (int i = 0; i < finalidade.getAdapter().getCount(); i++) {
                        finalidade.setSelection(i);
                        if (finalidade.getSelectedItem().toString().equals(formularioAtual.getFinalidade())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getCeans().equals("-")) {
                    ceans.setSelection(0);
                } else {
                    for (int i = 0; i < ceans.getAdapter().getCount(); i++) {
                        ceans.setSelection(i);
                        if (ceans.getSelectedItem().toString().equals(formularioAtual.getCeans())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getTar().equals("-")) {
                    tar.setSelection(0);
                } else {
                    for (int i = 0; i < tar.getAdapter().getCount(); i++) {
                        tar.setSelection(i);
                        if (tar.getSelectedItem().toString().equals(formularioAtual.getTar())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getReservaTec().equals("-")) {
                    reservaTec.setSelection(0);
                } else {
                    for (int i = 0; i < reservaTec.getAdapter().getCount(); i++) {
                        reservaTec.setSelection(i);
                        if (reservaTec.getSelectedItem().toString().equals(formularioAtual.getReservaTec())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getBackbone().equals("-")) {
                    backbone.setSelection(0);
                } else {
                    for (int i = 0; i < backbone.getAdapter().getCount(); i++) {
                        backbone.setSelection(i);
                        if (backbone.getSelectedItem().toString().equals(formularioAtual.getBackbone())) {
                            break;
                        }
                    }
                }
                if (formularioAtual.getPlacaIdent().equals("Sim")) {
                    placaIdentificadora.setChecked(true);
                }
                if (formularioAtual.getDescidaCabos().equals("Sim")) {
                    descidaCabos.setChecked(true);
                }
                descricaoIrregularidade.setText(formularioAtual.getDescricaoIrregularidade());
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


    private void novoOcupante(final View root) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outroOcupante = true;
                root.findViewById(R.id.btnCadastroSalvar).setVisibility(View.GONE);
                contadorAr = formularioAtual.getContadorAr();
                contadorAt = formularioAtual.getContadorAt();
                contadorIp = formularioAtual.getContadorIp();
                codigo.setText(formularioAtual.getCodigo());

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

                /*imgPathIP = formularioAtual.getCaminhoImagem4();
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
                }*/
                endereco.setText(formularioAtual.getEndereco());
                urlFotoPan = Uri.parse(formularioAtual.getUrlImagem());
                urlFotoAlt = Uri.parse(formularioAtual.getUrlImagem2());
                urlFotoQualidade = Uri.parse(formularioAtual.getUrlImagem3());
                /*
                urlFotoIP = Uri.parse(formularioAtual.getUrlImagem4());
                urlFotoAtivos = Uri.parse(formularioAtual.getUrlImagem7());
                urlFotoAtivos2 = Uri.parse(formularioAtual.getUrlImagem8());
                urlFotoMutuo = Uri.parse(formularioAtual.getUrlImagem9());
                urlFotoMutuo2 = Uri.parse(formularioAtual.getUrlImagem10());
                urlFotoMutuo3 = Uri.parse(formularioAtual.getUrlImagem11());
                urlFotoVeg = Uri.parse(formularioAtual.getUrlImagem12());
                */
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
                latitude.setText(formularioAtual.getLatitude());
                longitude.setText(formularioAtual.getLongitude());

                //CARACTERISTICAS FÍSICAS
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
            }
        });
    }

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

    class OutroOcupanteTask extends AsyncTask<Void, Void, Void> {
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
            ThreadOutroOcupante threadOutroOcupante = new ThreadOutroOcupante(root);
            threadOutroOcupante.start();
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

    public void getFormulario(Boolean novoPosteEstado) throws Throwable {
        Log.i("ENTREI","AQUI");
        progressDialog = new ProgressDialog(requireContext(), R.style.LightDialogTheme);
        progressDialog.setMessage("Salvando..."); // Setting Message
        progressDialog.setTitle("Por favor Espere"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        if ((imgPathPan == null) && (imgPathAlt == null) && (imgPathQualidade == null)) {
            Toast.makeText(requireContext(), "Preencha pelo menos uma foto", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (codigo.getText().toString().equals("") || codigo == null) {
            Toast.makeText(requireContext(), "Preencha o campo de código", Toast.LENGTH_SHORT).show();
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
            setLista(formulario, ipEstrutura, "ipEstrutura");
            formulario.setQuantidadeLampada(Objects.requireNonNull(quantidadeLampada.getText().toString()));
            setLista(formulario, tipoPot, "tipoPot");
            formulario.setPotReator(Objects.requireNonNull(potReator.getText()).toString());
            setLista(formulario, ipAtivacao, "ipAtivacao");
            if (vinteEQuatro.isChecked()) {
                formulario.setVinteEQuatro("Sim");
            } else {
                formulario.setVinteEQuatro("Não");
            }
            formulario.setQuantidade24H(Objects.requireNonNull(quantidade24H.getText().toString()));
            formulario.setObservacaoIP(Objects.requireNonNull(observacaoIP.getText().toString()));


            //TRAFO

            if (ativos.isChecked()) {
                formulario.setAtivos("Sim");
            } else {
                formulario.setAtivos("Não");
                formulario.setColor7(String.valueOf(R.color.colorPrimary));
                formulario.setColor8(String.valueOf(R.color.colorPrimary));
            }
            if (chkTrafoTrifasico.isChecked()) {
                formulario.setChkTrafoTrifasico("Sim");
            } else {
                formulario.setChkTrafoTrifasico("Não");
            }
            if (chkTrafoMono.isChecked()) {
                formulario.setChkTrafoMono("Sim");
            } else {
                formulario.setChkTrafoMono("Não");
            }
            setLista(formulario, trafoTrifasico, "trafoTrifasico");
            setLista(formulario, trafoMono, "trafoMono");
            if (religador.isChecked()) {
                formulario.setReligador("Sim");
            } else {
                formulario.setReligador("Não");
            }
            if (medicao.isChecked()) {
                formulario.setMedicao("Sim");
            } else {
                formulario.setMedicao("Não");
            }
            formulario.setChFusivel(Objects.requireNonNull(chFusivel.getText().toString()));
            setLista(formulario, chFaca, "chFaca");
            setLista(formulario, chBanco, "chBanco");
            formulario.setChFusivelReligador(Objects.requireNonNull(chFusivelReligador.getText().toString()));
            setLista(formulario, ramalSubt, "ramalSubt");
            formulario.setObservacaoAtivos(Objects.requireNonNull(observacaoAtivos.getText()).toString());
            formulario.setOutros(Objects.requireNonNull(outros.getText()).toString());

            //MUTUO
            if (mutuo.isChecked()) {
                formulario.setMutuo("Sim");

            } else {
                formulario.setMutuo("Não");
                formulario.setColor9(String.valueOf(R.color.colorPrimary));
                formulario.setColor10(String.valueOf(R.color.colorPrimary));
                formulario.setColor11(String.valueOf(R.color.colorPrimary));
            }
            formulario.setQuantidadeOcupantes(Objects.requireNonNull(quantidadeOcupantes.getText()).toString());
            formulario.setQuantidadeCabos(Objects.requireNonNull(quantidadeCabos.getText()).toString());
            setLista(formulario, tipoCabo, "tipoCabo");
            formulario.setQuantidadeCabosdois(Objects.requireNonNull(quantidadeCabosdois.getText()).toString());
            setLista(formulario, tipoCabodois, "tipoCabodois");
            formulario.setNome(Objects.requireNonNull(nome.getText()).toString());
            setLista(formulario, finalidade, "finalidade");
            setLista(formulario, ceans, "ceans");
            setLista(formulario, tar, "tar");
            setLista(formulario, reservaTec, "reservaTec");
            setLista(formulario, backbone, "backbone");
            if (placaIdentificadora.isChecked()) {
                formulario.setPlacaIdent("Sim");
            } else {
                formulario.setPlacaIdent("Não");
            }

            if (descidaCabos.isChecked()) {
                formulario.setDescidaCabos("Sim");
            } else {
                formulario.setDescidaCabos("Não");
            }
            formulario.setDescricaoIrregularidade(Objects.requireNonNull(descricaoIrregularidade.getText()).toString());
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
            formulario.setNome(Objects.requireNonNull(nome.getText()).toString());
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
                        Toast.makeText(requireActivity().getApplicationContext(), "Sucesso ao atualizar formulário", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        limparMemoria();
                    } else {
                        Toast.makeText(requireActivity().getApplicationContext(), "Erro ao atualizar formulário", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(requireActivity().getApplicationContext(), "Sucesso ao salvar formulário", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    limparMemoria();
                } else {
                    Toast.makeText(requireActivity().getApplicationContext(), "Erro ao salvar formulário", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
            try {
                progressDialog.dismiss();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbindDrawables(root.findViewById(R.id.nav_host_fragment_container));  //R.id.coordinator is the root layout of your fragment view
            System.gc();
        } catch (Exception e) {

        }
        root = null;
    }


    //free up any drawables..views
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            if (!(view instanceof AdapterView<?>))
                ((ViewGroup) view).removeAllViews();
        }
    }

    public void limparMemoria() throws Throwable {
        locationManager = null;
        locationListener = null;
        storageReference = null;
        autentificacao = null;
        progressDialog = null;
        geocoder = null;
        addresses = null;

        relativeChFaca= null;
        relativeBanco= null;
        relativeRamal = null;
        relativeIPTipoEstrutura = null;
        relativeIPTipoLampada = null;
        relativeIPTipoAtivacao = null;
        relativeFinalidade = null;
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

}
