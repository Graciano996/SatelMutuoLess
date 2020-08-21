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
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;
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
    private static final int IMAGE_CAPTURE_CODE = 2;
    private static final int IMAGE_PICK_CODE = 3;
    private static final int IMAGE_CAPTURE_CODE2 = 4;
    private static final int IMAGE_PICK_CODE2 = 5;
    private static final int IMAGE_CAPTURE_CODE3 = 6;
    private static final int IMAGE_PICK_CODE3 = 7;
    private static final int IMAGE_CAPTURE_CODE4 = 8;
    private static final int IMAGE_PICK_CODE4 = 9;
    private static final int IMAGE_CAPTURE_CODE7 = 14;
    private static final int IMAGE_PICK_CODE7 = 15;
    private static final int IMAGE_CAPTURE_CODE10 = 20;
    private static final int IMAGE_PICK_CODE10 = 21;
    private static final int IMAGE_CAPTURE_CODE13 = 26;
    private static final int IMAGE_PICK_CODE13 = 27;
    private static final int IMAGE_CAPTURE_CODE14 = 28;
    private static final int IMAGE_PICK_CODE14 = 29;
    private static final int IMAGE_CAPTURE_CODE15 = 30;
    private static final int IMAGE_PICK_CODE15 = 31;
    private static final int IMAGE_CAPTURE_CODE16 = 32;
    private static final int IMAGE_PICK_CODE16 = 33;
    private static final int IMAGE_CAPTURE_CODE17 = 34;
    private static final int IMAGE_PICK_CODE17 = 35;
    private static final int IMAGE_CAPTURE_CODE30 = 60;
    private static final int IMAGE_PICK_CODE30 = 61;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private StorageReference storageReference;
    private FirebaseAuth autentificacao;
    private ProgressDialog progressDialog;
    Geocoder geocoder;
    List<Address> addresses;
    private EditText codigo, endereco, latitude, longitude, observacaoFisicas,
            observacaoAtivos, quantidadeLampada, quantidadeLampada2, quantidadeLampada3,
            potReator, potReator2, potReator3, quantidade24H, quantidade24H2, quantidade24H3,
            observacaoVegetacao, observacaoIP,outros, quantidadeOcupantes,
            quantidadeCabos, quantidadeCabosdois, nome,  descricaoIrregularidade, observacaoMutuo;
    private Spinner municipio, alturaCarga, tipoPoste, ipEstrutura, ipEstrutura2, ipEstrutura3, tipoPot,
            tipoPot2, tipoPot3, dimensaoVegetacao, ipAtivacao, ipAtivacao2, ipAtivacao3,
            trafoTrifasico, trafoMono, ramalSubt,
            tipoCabo, tipoCabodois, distaciaBaixa, distanciaMedia, estadoArvore,
            localArvore, finalidade, ceans,tar,reservaTec,backbone;
    private CheckBox normal, ferragemExposta, fletido, danificado, abalroado, trincado, religador, medicao,
            chFusivel, chFaca, vinteEQuatro, vinteEQuatro2, vinteEQuatro3,
            ativos, chkTrafoTrifasico, chkTrafoMono, ip, ip2, ip3, chFusivelReligador, chBanco, mutuo,
            placaIdentificadora, descidaCabos, quedaArvore, posteNovo;
    private Formulario formularioAtual;
    private Boolean controle = false;
    private TextView mutuo2, mutuo3, mutuo4, mutuo5;
    private List<ImageView> listaLatitude = new ArrayList<>();
    private File photoFile = null;
    private List<Bitmap> imagemF = new ArrayList<>();
    private List<Uri> urlF = new ArrayList<>();
    private List<Boolean> novoU = new ArrayList<>();
    private Location localizacao;
    private String codigoEnergisa ="";
    public int contadorIp = 1, contadorAt = 1, contadorAr = 1;
    public int codigoSetorUpload;
    private ImageView foto, foto2, foto3, foto4, foto7, foto10,foto13, foto14,foto15, foto16, foto17,foto30;
    private Uri urlFoto, urlFoto2, urlFoto3, urlFoto4, urlFoto7,urlFoto10,
            urlFoto13, urlFoto14, urlFoto15, urlFoto16, urlFoto17,urlFoto30;

    private Bitmap imagem, imagem2, imagem3, imagem4, imagem7,
            imagem10, imagem13, imagem14, imagem15, imagem16, imagem17,imagem30;

    private String imgPath, imgPath2, imgPath3,imgPath4, imgPath7,imgPath10, imgPath13, imgPath14,
            imgPath15, imgPath16, imgPath17,imgPath30;

    private Boolean novoUpload = false, novoUpload2 = false, novoUpload3 = false, novoUpload4 = false,
             novoUpload7 = false, novoUpload10 = false, novoUpload13 = false, novoUpload14 = false
            , novoUpload15 = false, novoUpload16 = false, novoUpload17 = false, novoUpload30 = false;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View root = inflater.inflate(R.layout.fragment_cadastro, container, false);
        codigo = root.findViewById(R.id.textCadastroCodigo);
        foto = root.findViewById(R.id.imageCadastroFoto);
        foto2 = root.findViewById(R.id.imageCadastroFoto2);
        foto3 = root.findViewById(R.id.imageCadastroFoto3);
        foto4 = root.findViewById(R.id.imageCadastroFoto4);
        foto7 = root.findViewById(R.id.imageCadastroFoto7);
        foto10 = root.findViewById(R.id.imageCadastroFoto10);
        foto13 = root.findViewById(R.id.imageCadastroFoto13);
        foto14 = root.findViewById(R.id.imageCadastroFoto14);
        foto15 = root.findViewById(R.id.imageCadastroFoto15);
        foto16 = root.findViewById(R.id.imageCadastroFoto16);
        foto17 = root.findViewById(R.id.imageCadastroFoto17);
        foto30 = root.findViewById(R.id.imageCadastroFoto30);
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("TESTE", "ENTREI AQUI2");
                localizacao = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("TESTE", "ENTREI AQUI3");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("TESTE", "ENTREI AQUI4");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(requireActivity().getApplicationContext(), "Por favor ative seu GPS", Toast.LENGTH_SHORT).show();
            }
        };
        geocoder = new Geocoder(requireContext(), Locale.getDefault());
        if (verificarPermissaoLocaliza()) {

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i("TESTE", "ENTREI AQUI");
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
        final Button btnFoto = root.findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        Button btnFoto2 = root.findViewById(R.id.btnFoto2);
        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE2);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE2);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });
        Button btnFoto3 = root.findViewById(R.id.btnFoto3);
        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE3);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE3);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnFoto4 = root.findViewById(R.id.btnFoto4);
        btnFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE4);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE4);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });
        final Button btnFoto7 = root.findViewById(R.id.btnFoto7);
        btnFoto7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE7);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE7);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });
        final Button btnFoto10 = root.findViewById(R.id.btnFoto10);
        btnFoto10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE10);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE10);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });
        final Button btnFoto13 = root.findViewById(R.id.btnFoto13);
        btnFoto13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE13);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE13);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnFoto14 = root.findViewById(R.id.btnFoto14);
        btnFoto14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE14);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE14);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnFoto15 = root.findViewById(R.id.btnFoto15);
        btnFoto15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE15);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE15);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnFoto16 = root.findViewById(R.id.btnFoto16);
        btnFoto16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE16);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE16);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnFoto17 = root.findViewById(R.id.btnFoto17);
        btnFoto17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE17);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE17);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnFoto30 = root.findViewById(R.id.btnFoto30);
        btnFoto30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPermissao()) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                    dialog.setPositiveButton("Tirar foto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarCamera(IMAGE_CAPTURE_CODE30);

                        }
                    });
                    dialog.setNegativeButton("Escolher na galeria", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamarGaleria(IMAGE_PICK_CODE30);
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                ;
            }
        });

        final Button btnUpload = root.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem, imgPath, codigo, contadorAt, 1, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem, imgPath, codigo, contadorAt, 1, false, "CS");
                }
            }
        });

        Button btnUpload2 = root.findViewById(R.id.btnUpload2);
        btnUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem2, imgPath2, codigo, contadorAt, 2, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem2, imgPath2, codigo, contadorAt, 2, false, "CS");
                }
            }
        });

        Button btnUpload3 = root.findViewById(R.id.btnUpload3);
        btnUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem3, imgPath3, codigo, contadorAt, 3, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem3, imgPath3, codigo, contadorAt, 3, false, "CS");
                }
            }
        });

        final Button btnUpload4 = root.findViewById(R.id.btnUpload4);
        btnUpload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem4, imgPath4, codigo, contadorIp, 4, true, "IP");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem4, imgPath4, codigo, contadorIp, 4, false, "IP");
                }
            }
        });


        final Button btnUpload7 = root.findViewById(R.id.btnUpload7);
        btnUpload7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem7, imgPath7, codigo, contadorIp, 7, true, "IP");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem7, imgPath7, codigo, contadorIp, 7, false, "IP");
                }
            }
        });

        final Button btnUpload10 = root.findViewById(R.id.btnUpload10);
        btnUpload10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem10, imgPath10, codigo, contadorIp, 10, true, "IP");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem10, imgPath10, codigo, contadorIp, 10, false, "IP");
                }
            }
        });

        final Button btnUpload13 = root.findViewById(R.id.btnUpload13);
        btnUpload13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem13, imgPath13, codigo, contadorAt, 13, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem13, imgPath13, codigo, contadorAt, 13, false, "CS");
                }
            }
        });
        final Button btnUpload14 = root.findViewById(R.id.btnUpload14);
        btnUpload14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem14, imgPath14, codigo, contadorAt, 14, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem14, imgPath14, codigo, contadorAt, 14, false, "CS");
                }
            }
        });

        final Button btnUpload15 = root.findViewById(R.id.btnUpload15);
        btnUpload15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem15, imgPath15, codigo, contadorAt, 15, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem15, imgPath15, codigo, contadorAt, 15, false, "CS");
                }
            }
        });

        final Button btnUpload16 = root.findViewById(R.id.btnUpload16);
        btnUpload16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem16, imgPath16, codigo, contadorAt, 16, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem16, imgPath16, codigo, contadorAt, 16, false, "CS");
                }
            }
        });

        final Button btnUpload17 = root.findViewById(R.id.btnUpload17);
        btnUpload17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem17, imgPath17, codigo, contadorAt, 17, true, "CS");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem17, imgPath17, codigo, contadorAt, 17, false, "CS");
                }
            }
        });

        final Button btnUpload30 = root.findViewById(R.id.btnUpload30);
        btnUpload30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formularioAtual != null) {
                    upload(imagem30, imgPath30, codigo, contadorAr, 30, true, "Ar");
                } else {
                    Log.i("UPLOAD", "AAQUI");
                    upload(imagem30, imgPath30, codigo, contadorAr, 30, false, "Ar");
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
        ipEstrutura = root.findViewById(R.id.spinCadastroIPEstrutura);
        quantidadeLampada = root.findViewById(R.id.textCadastroQuantidadeLampada);
        ipEstrutura.setEnabled(false);
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
        ipAtivacao = root.findViewById(R.id.spinCadastroIPAtivacao);
        ipAtivacao.setEnabled(false);
        vinteEQuatro = root.findViewById(R.id.chkCadastroVinteEQuatro);
        quantidade24H = root.findViewById(R.id.txtCadastroQuantidade24H);

        ip2 = root.findViewById(R.id.chkCadastroIP2);
        ipEstrutura2 = root.findViewById(R.id.spinCadastroIPEstrutura2);
        quantidadeLampada2 = root.findViewById(R.id.textCadastroQuantidadeLampada2);
        ipEstrutura2.setEnabled(false);
        tipoPot2 = root.findViewById(R.id.spinCadastroTipoPot2);
        tipoPot2.setEnabled(false);
        potReator2 = root.findViewById(R.id.textCadastroPotReator2);
        potReator2.setEnabled(false);
        tipoPot2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] texto = tipoPot2.getSelectedItem().toString().split(" ");
                try {
                    potReator2.setText(texto[1]);
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ipAtivacao2 = root.findViewById(R.id.spinCadastroIPAtivacao2);
        ipAtivacao2.setEnabled(false);
        vinteEQuatro2 = root.findViewById(R.id.chkCadastroVinteEQuatro2);
        quantidade24H2 = root.findViewById(R.id.txtCadastroQuantidade24H2);

        ip3 = root.findViewById(R.id.chkCadastroIP3);
        ipEstrutura3 = root.findViewById(R.id.spinCadastroIPEstrutura3);
        quantidadeLampada3 = root.findViewById(R.id.textCadastroQuantidadeLampada3);
        ipEstrutura3.setEnabled(false);
        tipoPot3 = root.findViewById(R.id.spinCadastroTipoPot3);
        tipoPot3.setEnabled(false);
        potReator3 = root.findViewById(R.id.textCadastroPotReator3);
        potReator3.setEnabled(false);
        tipoPot3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] texto = tipoPot3.getSelectedItem().toString().split(" ");
                try {
                    potReator3.setText(texto[1]);
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ipAtivacao3 = root.findViewById(R.id.spinCadastroIPAtivacao3);
        ipAtivacao3.setEnabled(false);
        vinteEQuatro3 = root.findViewById(R.id.chkCadastroVinteEQuatro3);
        quantidade24H3 = root.findViewById(R.id.txtCadastroQuantidade24H3);
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
        chFusivel = root.findViewById(R.id.chkCadastroFusivel);
        chFaca = root.findViewById(R.id.chkCadastroFaca);
        chBanco = root.findViewById(R.id.chkCadastroBanco);
        chFusivelReligador = root.findViewById(R.id.chkCadastroFusivelReligador);
        ramalSubt = root.findViewById(R.id.spinCadastroRamalSubt);
        ramalSubt.setEnabled(false);
        outros = root.findViewById(R.id.textCadastroOutrosAtivos);
        observacaoAtivos = root.findViewById(R.id.textCadastroObservacaoAtivo);

        //MUTUO
        mutuo = root.findViewById(R.id.chkCadastroMutuo);
        quantidadeOcupantes = root.findViewById(R.id.textCadastroQuantidadeMutuo);
        quantidadeCabos = root.findViewById(R.id.textCadastroMutuoQuantidadeCabos);
        quantidadeCabosdois = root.findViewById(R.id.textCadastroMutuoQuantidadeCabosdois);
        tipoCabo = root.findViewById(R.id.spinCadastroMutuoTipoCabo);
        tipoCabodois = root.findViewById(R.id.spinCadastroMutuoTipoCabodois);

        nome = root.findViewById(R.id.textCadastroNome);
        finalidade = root.findViewById(R.id.spinCadastroFinalidade);
        ceans = root.findViewById(R.id.spinCadastroCeans);
        tar = root.findViewById(R.id.spinCadastroTar);
        reservaTec = root.findViewById(R.id.spinCadastroReservaTec);
        backbone = root.findViewById(R.id.spinCadastroBackbone);
        placaIdentificadora = root.findViewById(R.id.chkCadastroPlaca);
        descidaCabos = root.findViewById(R.id.chkCadastroDescidaCabos);
        descricaoIrregularidade = root.findViewById(R.id.textCadastroDescricao);
        observacaoMutuo = root.findViewById(R.id.textCadastroObservacaoMutuo);

        //VEGETAÇÃO
        dimensaoVegetacao = root.findViewById(R.id.spinCadastroDimensaoVegetacao);
        distaciaBaixa = root.findViewById(R.id.spinCadastroBaixa);
        distanciaMedia = root.findViewById(R.id.spinCadastroMedia);
        estadoArvore = root.findViewById(R.id.spinCadastroEstadoArvore);
        quedaArvore = root.findViewById(R.id.chkCadastroQuedaArvore);
        localArvore = root.findViewById(R.id.spinCadastroLocalArvore);
        observacaoVegetacao = root.findViewById(R.id.textCadastroObservacaoVegetacao);
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
                posteNovo = root.findViewById(R.id.chkCadastroPosteNovo);
                posteNovo.setVisibility(View.VISIBLE);
                posteNovo.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(formularioAtual.getCodigo().equals(codigo.getText().toString())){
                            Toast.makeText(requireActivity().getApplicationContext(), "Um novo poste precisa de um código novo", Toast.LENGTH_SHORT).show();
                            posteNovo.setChecked(false);
                        }else {
                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.LightDialogTheme);
                            dialog.setMessage("Deseja realmente cadastrar um novo poste?");
                            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    contadorIp = 1;
                                    contadorAr =1;
                                    contadorAt = 1;
                                    Button btnNovoPoste = root.findViewById(R.id.btnNovoPoste);
                                    btnNovoPoste.setText("Novo Poste");
                                    foto.setImageResource(R.drawable.ic_menu_camera);
                                    foto2.setImageResource(R.drawable.ic_menu_camera);
                                    foto3.setImageResource(R.drawable.ic_menu_camera);
                                    foto4.setImageResource(R.drawable.ic_menu_camera);
                                    foto7.setImageResource(R.drawable.ic_menu_camera);
                                    foto10.setImageResource(R.drawable.ic_menu_camera);
                                    foto13.setImageResource(R.drawable.ic_menu_camera);
                                    foto14.setImageResource(R.drawable.ic_menu_camera);
                                    foto15.setImageResource(R.drawable.ic_menu_camera);
                                    foto16.setImageResource(R.drawable.ic_menu_camera);
                                    foto17.setImageResource(R.drawable.ic_menu_camera);
                                    foto30.setImageResource(R.drawable.ic_menu_camera);
                                }
                            });
                            dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    posteNovo.setChecked(false);

                                }
                            });
                            dialog.create();
                            dialog.show();
                        }
                    }
                });

                //LOCALIZAÇÃO
                controle = true;
                contadorAr = formularioAtual.getContadorAr();
                contadorAt = formularioAtual.getContadorAt();
                contadorIp = formularioAtual.getContadorIp();
                codigo.setText(formularioAtual.getCodigo());

                imgPath = formularioAtual.getCaminhoImagem();
                if(imgPath == null || imgPath.equals("")){

                }else {
                    foto.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                    imagem = BitmapFactory.decodeFile(imgPath);
                }
                imgPath2 = formularioAtual.getCaminhoImagem2();
                if(imgPath2 == null || imgPath2.equals("")){

                }else {
                    imgPath2 = formularioAtual.getCaminhoImagem2();
                    foto2.setImageBitmap(BitmapFactory.decodeFile(imgPath2));
                    imagem2 = BitmapFactory.decodeFile(imgPath2);
                }

                imgPath3 = formularioAtual.getCaminhoImagem3();
                if(imgPath3 == null || imgPath3.equals("")){

                }else {
                    imgPath3 = formularioAtual.getCaminhoImagem3();
                    foto3.setImageBitmap(BitmapFactory.decodeFile(imgPath3));
                    imagem3 = BitmapFactory.decodeFile(imgPath3);
                }

                imgPath4 = formularioAtual.getCaminhoImagem4();
                if(imgPath4 == null || imgPath4.equals("")){

                }else {
                    imgPath4 = formularioAtual.getCaminhoImagem4();
                    foto4.setImageBitmap(BitmapFactory.decodeFile(imgPath4));
                    imagem4 = BitmapFactory.decodeFile(imgPath4);
                }

                imgPath7 = formularioAtual.getCaminhoImagem5();
                if(imgPath7 == null || imgPath7.equals("")){

                }else {
                    imgPath7 = formularioAtual.getCaminhoImagem5();
                    foto7.setImageBitmap(BitmapFactory.decodeFile(imgPath7));
                    imagem7 = BitmapFactory.decodeFile(imgPath7);
                }

                imgPath10 = formularioAtual.getCaminhoImagem6();
                if(imgPath10 == null || imgPath10.equals("")){

                }else {
                    imgPath10 = formularioAtual.getCaminhoImagem6();
                    foto10.setImageBitmap(BitmapFactory.decodeFile(imgPath10));
                    imagem10 = BitmapFactory.decodeFile(imgPath10);
                }

                imgPath13 = formularioAtual.getCaminhoImagem7();
                if(imgPath13 == null || imgPath13.equals("")){

                }else {
                    imgPath13 = formularioAtual.getCaminhoImagem7();
                    foto13.setImageBitmap(BitmapFactory.decodeFile(imgPath13));
                    imagem13 = BitmapFactory.decodeFile(imgPath13);
                }


                if(imgPath14 == null || imgPath14.equals("")){
                    imgPath14 = formularioAtual.getCaminhoImagem8();
                }else {
                    imgPath14 = formularioAtual.getCaminhoImagem8();
                    foto14.setImageBitmap(BitmapFactory.decodeFile(imgPath14));
                    imagem14 = BitmapFactory.decodeFile(imgPath14);
                }

                imgPath15 = formularioAtual.getCaminhoImagem9();
                if(imgPath15 == null || imgPath15.equals("")){

                }else {
                    imgPath15 = formularioAtual.getCaminhoImagem9();
                    foto15.setImageBitmap(BitmapFactory.decodeFile(imgPath15));
                    imagem15 = BitmapFactory.decodeFile(imgPath15);
                }

                imgPath16 = formularioAtual.getCaminhoImagem10();
                if(imgPath16 == null || imgPath16.equals("")){

                }else {
                    imgPath16 = formularioAtual.getCaminhoImagem10();
                    foto16.setImageBitmap(BitmapFactory.decodeFile(imgPath16));
                    imagem16 = BitmapFactory.decodeFile(imgPath16);
                }

                imgPath17 = formularioAtual.getCaminhoImagem11();
                if(imgPath17 == null || imgPath17.equals("")){

                }else {
                    imgPath17 = formularioAtual.getCaminhoImagem11();
                    foto17.setImageBitmap(BitmapFactory.decodeFile(imgPath17));
                    imagem17 = BitmapFactory.decodeFile(imgPath17);
                }

                imgPath30 = formularioAtual.getCaminhoImagem12();
                if(imgPath30 == null || imgPath30.equals("")){

                }else {
                    imgPath30 = formularioAtual.getCaminhoImagem12();
                    foto30.setImageBitmap(BitmapFactory.decodeFile(imgPath30));
                    imagem30 = BitmapFactory.decodeFile(imgPath30);
                }
                endereco.setText(formularioAtual.getEndereco());
                urlFoto = Uri.parse(formularioAtual.getUrlImagem());
                urlFoto2 = Uri.parse(formularioAtual.getUrlImagem2());
                urlFoto3 = Uri.parse(formularioAtual.getUrlImagem3());
                urlFoto4 = Uri.parse(formularioAtual.getUrlImagem4());
                urlFoto7 = Uri.parse(formularioAtual.getUrlImagem5());
                urlFoto10 = Uri.parse(formularioAtual.getUrlImagem6());
                urlFoto13 = Uri.parse(formularioAtual.getUrlImagem7());
                urlFoto14 = Uri.parse(formularioAtual.getUrlImagem8());
                urlFoto15 = Uri.parse(formularioAtual.getUrlImagem9());
                urlFoto16 = Uri.parse(formularioAtual.getUrlImagem10());
                urlFoto17 = Uri.parse(formularioAtual.getUrlImagem11());
                urlFoto30 = Uri.parse(formularioAtual.getUrlImagem12());
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


                //CARACTERISTICAS FÍSICAS

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
                    ip2.setVisibility(View.VISIBLE);
                    foto4.setVisibility(View.VISIBLE);
                    btnFoto4.setVisibility(View.VISIBLE);
                    btnUpload4.setVisibility(View.VISIBLE);
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
                    ip2.setEnabled(true);
                    if (formularioAtual.getIp2().equals("Sim")) {
                        ip2.setChecked(true);
                        ipEstrutura2.setVisibility(View.VISIBLE);
                        tipoPot2.setVisibility(View.VISIBLE);
                        potReator2.setVisibility(View.VISIBLE);
                        quantidadeLampada2.setVisibility(View.VISIBLE);
                        ipAtivacao2.setVisibility(View.VISIBLE);
                        vinteEQuatro2.setVisibility(View.VISIBLE);
                        quantidade24H2.setVisibility(View.VISIBLE);
                        foto7.setVisibility(View.VISIBLE);
                        btnFoto7.setVisibility(View.VISIBLE);
                        btnUpload7.setVisibility(View.VISIBLE);
                        ip3.setVisibility(View.VISIBLE);
                        if (formularioAtual.getIpEstrutura2().equals("-")) {
                            ipEstrutura2.setSelection(0);

                        } else {
                            for (int i = 0; i < ipEstrutura2.getAdapter().getCount(); i++) {
                                ipEstrutura2.setSelection(i);
                                if (ipEstrutura2.getSelectedItem().toString().equals(formularioAtual.getIpEstrutura2())) {
                                    break;
                                }
                            }
                        }
                        quantidadeLampada2.setText(formularioAtual.getQuantidadeLampada2());
                        if (formularioAtual.getTipoPot2().equals("-")) {
                            tipoPot2.setSelection(0);
                        } else {
                            for (int i = 0; i < tipoPot2.getAdapter().getCount(); i++) {
                                tipoPot2.setSelection(i);
                                if (tipoPot2.getSelectedItem().toString().equals(formularioAtual.getTipoPot2())) {
                                    break;
                                }
                            }
                        }
                        potReator2.setText(formularioAtual.getPotReator2());
                        if (formularioAtual.getIpAtivacao2().equals("-")) {
                            ipAtivacao2.setSelection(0);
                        } else {
                            for (int i = 0; i < ipAtivacao2.getAdapter().getCount(); i++) {
                                ipAtivacao2.setSelection(i);
                                if (ipAtivacao2.getSelectedItem().toString().equals(formularioAtual.getIpAtivacao2())) {
                                    break;
                                }
                            }
                        }
                        if (formularioAtual.getVinteEQuatro2().equals("Sim")) {
                            vinteEQuatro2.setChecked(true);
                            quantidade24H2.setEnabled(true);
                        }
                        quantidade24H2.setText(formularioAtual.getQuantidade24H2());
                        ipEstrutura2.setEnabled(true);
                        quantidadeLampada2.setEnabled(true);
                        tipoPot2.setEnabled(true);
                        potReator2.setEnabled(true);
                        ipAtivacao2.setEnabled(true);
                        vinteEQuatro2.setEnabled(true);
                        ip3.setEnabled(true);
                        if (formularioAtual.getIp3().equals("Sim")) {
                            ip3.setChecked(true);
                            ipEstrutura3.setVisibility(View.VISIBLE);
                            tipoPot3.setVisibility(View.VISIBLE);
                            potReator3.setVisibility(View.VISIBLE);
                            quantidadeLampada3.setVisibility(View.VISIBLE);
                            ipAtivacao3.setVisibility(View.VISIBLE);
                            vinteEQuatro3.setVisibility(View.VISIBLE);
                            quantidade24H3.setVisibility(View.VISIBLE);
                            foto10.setVisibility(View.VISIBLE);
                            btnFoto10.setVisibility(View.VISIBLE);
                            btnUpload10.setVisibility(View.VISIBLE);
                            if (formularioAtual.getIpEstrutura3().equals("-")) {
                                ipEstrutura3.setSelection(0);
                            } else {
                                for (int i = 0; i < ipEstrutura3.getAdapter().getCount(); i++) {
                                    ipEstrutura3.setSelection(i);
                                    if (ipEstrutura3.getSelectedItem().toString().equals(formularioAtual.getIpEstrutura3())) {
                                        break;
                                    }
                                }
                            }
                            quantidadeLampada3.setText(formularioAtual.getQuantidadeLampada3());
                            if (formularioAtual.getTipoPot3().equals("-")) {
                                tipoPot3.setSelection(0);
                            } else {
                                for (int i = 0; i < tipoPot3.getAdapter().getCount(); i++) {
                                    tipoPot3.setSelection(i);
                                    if (tipoPot3.getSelectedItem().toString().equals(formularioAtual.getTipoPot3())) {
                                        break;
                                    }
                                }
                            }
                            potReator3.setText(formularioAtual.getPotReator3());
                            if (formularioAtual.getIpAtivacao3().equals("-")) {
                                ipAtivacao3.setSelection(0);
                            } else {
                                for (int i = 0; i < ipAtivacao3.getAdapter().getCount(); i++) {
                                    ipAtivacao3.setSelection(i);
                                    if (ipAtivacao3.getSelectedItem().toString().equals(formularioAtual.getIpAtivacao3())) {
                                        break;
                                    }
                                }
                            }
                            if (formularioAtual.getVinteEQuatro3().equals("Sim")) {
                                vinteEQuatro3.setChecked(true);
                                quantidade24H3.setEnabled(true);
                            }
                            quantidade24H3.setText(formularioAtual.getQuantidade24H3());
                            ipEstrutura3.setEnabled(true);
                            quantidadeLampada3.setEnabled(true);
                            tipoPot3.setEnabled(true);
                            potReator3.setEnabled(true);
                            ipAtivacao3.setEnabled(true);
                            vinteEQuatro3.setEnabled(true);

                        }

                    }


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
                    foto13.setVisibility(View.VISIBLE);
                    btnFoto13.setVisibility(View.VISIBLE);
                    btnUpload13.setVisibility(View.VISIBLE);
                    foto14.setVisibility(View.VISIBLE);
                    btnFoto14.setVisibility(View.VISIBLE);
                    btnUpload14.setVisibility(View.VISIBLE);
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
                if (formularioAtual.getChFusivel().equals("Sim")) {
                    chFusivel.setChecked(true);
                }
                if (formularioAtual.getChFaca().equals("Sim")) {
                    chFaca.setChecked(true);
                }
                if (formularioAtual.getBanco().equals("Sim")) {
                    chBanco.setChecked(true);
                }
                if (formularioAtual.getChFusivelReligador().equals("Sim")) {
                    chFusivelReligador.setChecked(true);
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
                    foto15.setVisibility(View.VISIBLE);
                    btnFoto15.setVisibility(View.VISIBLE);
                    btnUpload15.setVisibility(View.VISIBLE);

                    foto16.setVisibility(View.VISIBLE);
                    btnFoto16.setVisibility(View.VISIBLE);
                    btnUpload16.setVisibility(View.VISIBLE);

                    foto17.setVisibility(View.VISIBLE);
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
                Button btnNovoPoste = root.findViewById(R.id.btnNovoPoste);
                btnNovoPoste.setVisibility(View.VISIBLE);
                //add button to the layout
                btnNovoPoste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //region pegar dados
                        progressDialog = new ProgressDialog(requireContext(), R.style.LightDialogTheme);
                        progressDialog.setMessage("Salvando..."); // Setting Message
                        progressDialog.setTitle("Por favor Espere"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        if ((imgPath == null) && (imgPath2 == null) && (imgPath3 == null)) {
                            Toast.makeText(requireContext(), "Preencha pelo menos uma foto", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (codigo.getText().toString().equals("") || codigo == null) {
                            Toast.makeText(requireContext(), "Preencha o campo de código", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            FormularioDAO formularioDAO = new FormularioDAO(requireActivity().getApplicationContext());
                            Formulario formulario = new Formulario();
                            formulario.setCodigo(Objects.requireNonNull(codigo.getText()).toString());
                            Log.i("TAG", imgPath);
                            if (imgPath != null) {
                                formulario.setCaminhoImagem(imgPath);
                            } else {
                                formulario.setCaminhoImagem("");
                            }
                            if (imgPath2 != null) {
                                formulario.setCaminhoImagem2(imgPath2);
                            } else {
                                formulario.setCaminhoImagem2("");
                            }
                            if (imgPath3 != null) {
                                formulario.setCaminhoImagem3(imgPath3);
                            } else {
                                formulario.setCaminhoImagem3("");
                            }
                            if (imgPath4 != null) {
                                formulario.setCaminhoImagem4(imgPath4);
                            } else {
                                formulario.setCaminhoImagem4("");
                            }
                            if (imgPath7 != null) {
                                formulario.setCaminhoImagem5(imgPath7);
                            } else {
                                formulario.setCaminhoImagem5("");
                            }
                            if (imgPath10 != null) {
                                formulario.setCaminhoImagem6(imgPath10);
                            } else {
                                formulario.setCaminhoImagem6("");
                            }
                            if (imgPath13 != null) {
                                formulario.setCaminhoImagem7(imgPath13);
                            } else {
                                formulario.setCaminhoImagem7("");
                            }
                            if (imgPath14 != null) {
                                formulario.setCaminhoImagem8(imgPath14);
                            } else {
                                formulario.setCaminhoImagem8("");
                            }
                            if (imgPath15 != null) {
                                formulario.setCaminhoImagem9(imgPath15);
                            } else {
                                formulario.setCaminhoImagem9("");
                            }
                            if (imgPath16 != null) {
                                formulario.setCaminhoImagem10(imgPath16);
                            } else {
                                formulario.setCaminhoImagem10("");
                            }
                            if (imgPath17 != null) {
                                formulario.setCaminhoImagem11(imgPath17);
                            } else{
                                formulario.setCaminhoImagem11("");
                            }
                            if (imgPath30 != null) {
                                formulario.setCaminhoImagem12(imgPath30);
                            } else {
                                formulario.setCaminhoImagem12("");
                            }
                            if (!novoUpload) {
                                urlFoto = null;
                            }
                            if (!novoUpload2) {
                                urlFoto2 = null;
                            }
                            if (!novoUpload3) {
                                urlFoto3 = null;
                            }
                            if (!novoUpload4) {
                                urlFoto4 = null;
                            }
                            if (!novoUpload7) {
                                urlFoto7 = null;
                            }
                            if (!novoUpload10) {
                                urlFoto10 = null;
                            }
                            if (!novoUpload13) {
                                urlFoto13 = null;
                            }
                            if (!novoUpload14) {
                                urlFoto14 = null;
                            }
                            if (!novoUpload15) {
                                urlFoto15 = null;
                            }
                            if (!novoUpload16) {
                                urlFoto16 = null;
                            }
                            if (!novoUpload17) {
                                urlFoto17 = null;
                            }
                            if (!novoUpload30) {
                                urlFoto30 = null;
                            }
                            if((urlFoto == null) || ((urlFoto.toString()).equals(""))) {
                                formulario.setUrlImagem("");
                                formulario.setColor(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem(urlFoto.toString());
                                formulario.setColor(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto2 == null) || ((urlFoto2.toString()).equals(""))) {
                                formulario.setUrlImagem2("");
                                formulario.setColor2(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem2(urlFoto2.toString());
                                formulario.setColor2(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto3 == null) || ((urlFoto3.toString()).equals(""))) {
                                formulario.setUrlImagem3("");
                                formulario.setColor3(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem3(urlFoto3.toString());
                                formulario.setColor3(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto4 == null) || ((urlFoto4.toString()).equals(""))) {
                                formulario.setUrlImagem4("");
                                formulario.setColor4(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem4(urlFoto4.toString());
                                formulario.setColor4(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto7 == null) || ((urlFoto7.toString()).equals(""))) {
                                formulario.setUrlImagem5("");
                                formulario.setColor5(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem5(urlFoto7.toString());
                                formulario.setColor5(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto10 == null) || ((urlFoto10.toString()).equals(""))) {
                                formulario.setUrlImagem6("");
                                formulario.setColor6(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem6(urlFoto10.toString());
                                formulario.setColor6(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto13 == null) || ((urlFoto13.toString()).equals(""))) {
                                formulario.setUrlImagem7("");
                                formulario.setColor7(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem7(urlFoto13.toString());
                                formulario.setColor7(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto14 == null) || ((urlFoto14.toString()).equals(""))) {
                                formulario.setUrlImagem8("");
                                formulario.setColor8(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem8(urlFoto14.toString());
                                formulario.setColor8(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto15 == null) || ((urlFoto15.toString()).equals(""))) {
                                formulario.setUrlImagem9("");
                                formulario.setColor9(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem9(urlFoto15.toString());
                                formulario.setColor9(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto16 == null) || ((urlFoto16.toString()).equals(""))) {
                                formulario.setUrlImagem10("");
                                formulario.setColor10(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem10(urlFoto16.toString());
                                formulario.setColor10(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto17 == null) || ((urlFoto17.toString()).equals(""))) {
                                formulario.setUrlImagem11("");
                                formulario.setColor11(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem11(urlFoto17.toString());
                                formulario.setColor11(String.valueOf(R.color.colorPrimary));
                            }
                            if((urlFoto30 == null) || ((urlFoto30.toString()).equals(""))) {
                                formulario.setUrlImagem12("");
                                formulario.setColor12(String.valueOf(R.color.design_default_color_error));
                            }else {
                                formulario.setUrlImagem12(urlFoto30.toString());
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

                            if (ip2.isChecked()) {
                                formulario.setIp2("Sim");
                            } else {
                                formulario.setIp2("Não");
                            }
                            setLista(formulario, ipEstrutura2, "ipEstrutura2");
                            formulario.setQuantidadeLampada2(Objects.requireNonNull(quantidadeLampada2.getText().toString()));
                            setLista(formulario, tipoPot2, "tipoPot2");
                            formulario.setPotReator2(Objects.requireNonNull(potReator2.getText()).toString());
                            setLista(formulario, ipAtivacao2, "ipAtivacao2");
                            if (vinteEQuatro2.isChecked()) {
                                formulario.setVinteEQuatro2("Sim");
                            } else {
                                formulario.setVinteEQuatro2("Não");
                            }
                            formulario.setQuantidade24H2(Objects.requireNonNull(quantidade24H2.getText().toString()));

                            if (ip3.isChecked()) {
                                formulario.setIp3("Sim");
                            } else {
                                formulario.setIp3("Não");
                            }
                            setLista(formulario, ipEstrutura3, "ipEstrutura3");
                            formulario.setQuantidadeLampada3(Objects.requireNonNull(quantidadeLampada3.getText().toString()));
                            setLista(formulario, tipoPot3, "tipoPot3");
                            formulario.setPotReator3(Objects.requireNonNull(potReator3.getText()).toString());
                            setLista(formulario, ipAtivacao3, "ipAtivacao3");
                            if (vinteEQuatro3.isChecked()) {
                                formulario.setVinteEQuatro3("Sim");
                            } else {
                                formulario.setVinteEQuatro3("Não");
                            }
                            formulario.setQuantidade24H3(Objects.requireNonNull(quantidade24H3.getText().toString()));
                            formulario.setObservacaoIP(Objects.requireNonNull(observacaoIP.getText().toString()));


                            //TRAFO

                            if (ativos.isChecked()) {
                                formulario.setAtivos("Sim");
                            } else {
                                formulario.setAtivos("Não");
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
                            if (chFusivel.isChecked()) {
                                formulario.setChFusivel("Sim");
                            } else {
                                formulario.setChFusivel("Não");
                            }
                            if (chFaca.isChecked()) {
                                formulario.setChFaca("Sim");
                            } else {
                                formulario.setChFaca("Não");
                            }
                            if (chBanco.isChecked()) {
                                formulario.setBanco("Sim");
                            } else {
                                formulario.setBanco("Não");
                            }
                            if (chFusivelReligador.isChecked()) {
                                formulario.setChFusivelReligador("Sim");
                            } else {
                                formulario.setChFusivelReligador("Não");
                            }
                            setLista(formulario, ramalSubt, "ramalSubt");
                            formulario.setObservacaoAtivos(Objects.requireNonNull(observacaoAtivos.getText()).toString());
                            formulario.setOutros(Objects.requireNonNull(outros.getText()).toString());

                            //MUTUO
                            if (mutuo.isChecked()) {
                                formulario.setMutuo("Sim");
                            } else {
                                formulario.setMutuo("Não");
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
                            writeDate.setTimeZone(TimeZone.getTimeZone("GMT-04:00"));
                            String s = writeDate.format(date);


                            Log.i("DATA", s);

                            String data = thisDayText + "/" + thisMonthText + "/" + thisYearText;
                            formulario.setData(s);
                            if (formularioDAO.salvar(formulario)) {
                                CadastradosFragment cadastradosFragment = new CadastradosFragment();

                                FragmentManager fm = getParentFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.replace(R.id.nav_host_fragment, cadastradosFragment);
                                transaction.commit();
                                Toast.makeText(requireActivity().getApplicationContext(), "Sucesso ao salvar formulário", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(requireActivity().getApplicationContext(), "Erro ao salvar formulário", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            //endregion
                            //endregion
                        }

                    }
                });

                controle = false;

            }
        } catch (Exception e) {
            Log.i("ERRO", "erro: " + e);
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

        vinteEQuatro2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if (vinteEQuatro2.isChecked()) {
                                                             quantidade24H2.setEnabled(true);
                                                         } else {
                                                             quantidade24H2.setEnabled(false);
                                                             quantidade24H2.setText("");
                                                         }
                                                     }
                                                 }
        );

        vinteEQuatro3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if (vinteEQuatro3.isChecked()) {
                                                             quantidade24H3.setEnabled(true);
                                                         } else {
                                                             quantidade24H3.setEnabled(false);
                                                             quantidade24H3.setText("");
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
                    ip2.setEnabled(true);
                    ip2.setVisibility(View.VISIBLE);
                    foto4.setEnabled(true);
                    foto4.setVisibility(View.VISIBLE);
                    btnFoto4.setVisibility(View.VISIBLE);
                    btnUpload4.setVisibility(View.VISIBLE);
                    //foto5.setEnabled(true);
                    //foto5.setVisibility(View.VISIBLE);
                    //btnFoto5.setVisibility(View.VISIBLE);
                    //btnUpload5.setVisibility(View.VISIBLE);
                    //foto6.setEnabled(true);
                    //foto6.setVisibility(View.VISIBLE);
                    //btnFoto6.setVisibility(View.VISIBLE);
                    //btnUpload6.setVisibility(View.VISIBLE);

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
                    tipoPot.setEnabled(false);
                    tipoPot.setSelection(0);
                    potReator.setEnabled(false);
                    potReator.setText("");
                    quantidadeLampada.setText("");
                    ipEstrutura.setSelection(0);
                    ipAtivacao.setEnabled(false);
                    vinteEQuatro.setEnabled(false);
                    vinteEQuatro.setChecked(false);
                    ipAtivacao.setSelection(0);
                    ip2.setChecked(false);
                    ip2.setEnabled(false);
                    ip2.setVisibility(View.GONE);
                    foto4.setVisibility(View.GONE);
                    btnFoto4.setVisibility(View.GONE);
                    btnUpload4.setVisibility(View.GONE);

                    ipEstrutura.setVisibility(View.GONE);
                    tipoPot.setVisibility(View.GONE);
                    potReator.setVisibility(View.GONE);
                    quantidadeLampada.setVisibility(View.GONE);
                    quantidade24H.setVisibility(View.GONE);
                    ipAtivacao.setVisibility(View.GONE);
                    vinteEQuatro.setVisibility(View.GONE);
                }
            }
        });

        //Listener para só habilitar os dados da própria ip e as próximas ip caso a segunda
        // tenha sido checada
        ip2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ip2.isChecked()) {
                    ipEstrutura2.setEnabled(true);
                    tipoPot2.setEnabled(true);
                    potReator2.setEnabled(true);
                    quantidadeLampada2.setEnabled(true);
                    ipAtivacao2.setEnabled(true);
                    vinteEQuatro2.setEnabled(true);
                    ip3.setEnabled(true);
                    foto7.setEnabled(true);
                    foto7.setVisibility(View.VISIBLE);
                    btnFoto7.setVisibility(View.VISIBLE);
                    btnUpload7.setVisibility(View.VISIBLE);


                    ipEstrutura2.setVisibility(View.VISIBLE);
                    tipoPot2.setVisibility(View.VISIBLE);
                    potReator2.setVisibility(View.VISIBLE);
                    quantidadeLampada2.setVisibility(View.VISIBLE);
                    ipAtivacao2.setVisibility(View.VISIBLE);
                    vinteEQuatro2.setVisibility(View.VISIBLE);
                    quantidade24H2.setVisibility(View.VISIBLE);
                    ip3.setVisibility(View.VISIBLE);
                } else {
                    ipEstrutura2.setEnabled(false);
                    quantidadeLampada2.setEnabled(false);
                    tipoPot2.setEnabled(false);
                    tipoPot2.setSelection(0);
                    potReator2.setEnabled(false);
                    potReator2.setText("");
                    quantidadeLampada2.setText("");
                    ipEstrutura2.setSelection(0);
                    ipAtivacao2.setEnabled(false);
                    vinteEQuatro2.setChecked(false);
                    vinteEQuatro2.setEnabled(false);
                    ipAtivacao2.setSelection(0);
                    ip3.setChecked(false);
                    ip3.setEnabled(false);

                    foto7.setVisibility(View.GONE);
                    btnFoto7.setVisibility(View.GONE);
                    btnUpload7.setVisibility(View.GONE);

                    ipEstrutura2.setVisibility(View.GONE);
                    tipoPot2.setVisibility(View.GONE);
                    potReator2.setVisibility(View.GONE);
                    quantidadeLampada2.setVisibility(View.GONE);
                    quantidade24H2.setVisibility(View.GONE);
                    ipAtivacao2.setVisibility(View.GONE);
                    vinteEQuatro2.setVisibility(View.GONE);
                    ip3.setVisibility(View.GONE);
                }
            }
        });

        //Listener para só habilitar os dados da própria ip caso tenha sido checada
        ip3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ip3.isChecked()) {
                    ipEstrutura3.setEnabled(true);
                    tipoPot3.setEnabled(true);
                    potReator3.setEnabled(true);
                    quantidadeLampada3.setEnabled(true);
                    ipAtivacao3.setEnabled(true);
                    vinteEQuatro3.setEnabled(true);

                    foto10.setEnabled(true);
                    foto10.setVisibility(View.VISIBLE);
                    btnFoto10.setVisibility(View.VISIBLE);
                    btnUpload10.setVisibility(View.VISIBLE);

                    ipEstrutura3.setVisibility(View.VISIBLE);
                    tipoPot3.setVisibility(View.VISIBLE);
                    potReator3.setVisibility(View.VISIBLE);
                    quantidadeLampada3.setVisibility(View.VISIBLE);
                    ipAtivacao3.setVisibility(View.VISIBLE);
                    vinteEQuatro3.setVisibility(View.VISIBLE);
                    quantidade24H3.setVisibility(View.VISIBLE);

                } else {
                    ipEstrutura3.setEnabled(false);
                    quantidadeLampada3.setEnabled(false);
                    tipoPot3.setEnabled(false);
                    tipoPot3.setSelection(0);
                    potReator3.setEnabled(false);
                    potReator3.setText("");
                    quantidadeLampada3.setText("");
                    ipEstrutura3.setSelection(0);
                    ipAtivacao3.setEnabled(false);
                    vinteEQuatro3.setChecked(false);
                    vinteEQuatro3.setEnabled(false);
                    ipAtivacao3.setSelection(0);
                    foto10.setVisibility(View.GONE);
                    btnFoto10.setVisibility(View.GONE);
                    btnUpload10.setVisibility(View.GONE);


                    ipEstrutura3.setVisibility(View.GONE);
                    tipoPot3.setVisibility(View.GONE);
                    potReator3.setVisibility(View.GONE);
                    quantidadeLampada3.setVisibility(View.GONE);
                    quantidade24H3.setVisibility(View.GONE);
                    ipAtivacao3.setVisibility(View.GONE);
                    vinteEQuatro3.setVisibility(View.GONE);
                }
            }
        });

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

                    foto13.setEnabled(true);
                    foto13.setVisibility(View.VISIBLE);
                    btnFoto13.setVisibility(View.VISIBLE);
                    btnUpload13.setVisibility(View.VISIBLE);
                    foto14.setEnabled(true);
                    foto14.setVisibility(View.VISIBLE);
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
                    outros.setVisibility(View.VISIBLE);
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
                    chFusivel.setEnabled(false);
                    chFusivel.setChecked(false);
                    chFaca.setEnabled(false);
                    chFaca.setChecked(false);
                    chFusivelReligador.setEnabled(false);
                    chFusivelReligador.setChecked(false);
                    chBanco.setEnabled(false);
                    chBanco.setChecked(false);
                    ramalSubt.setSelection(0);
                    ramalSubt.setEnabled(false);
                    outros.setEnabled(false);

                    trafoMono.setVisibility(View.INVISIBLE);
                    trafoTrifasico.setVisibility(View.GONE);
                    chkTrafoTrifasico.setVisibility(View.GONE);
                    chkTrafoMono.setVisibility(View.GONE);
                    religador.setVisibility(View.GONE);
                    medicao.setVisibility(View.GONE);
                    chFusivel.setVisibility(View.GONE);
                    chFaca.setVisibility(View.GONE);
                    chFusivelReligador.setVisibility(View.GONE);
                    chBanco.setVisibility(View.GONE);
                    ramalSubt.setVisibility(View.GONE);
                    outros.setVisibility(View.GONE);
                    foto13.setVisibility(View.GONE);
                    btnFoto13.setVisibility(View.GONE);
                    btnUpload13.setVisibility(View.GONE);
                    foto14.setVisibility(View.GONE);
                    btnFoto14.setVisibility(View.GONE);
                    btnUpload14.setVisibility(View.GONE);
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
                Log.i("CHAMADO", "FUI");
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
                    foto15.setVisibility(View.VISIBLE);
                    btnFoto15.setVisibility(View.VISIBLE);
                    btnUpload15.setVisibility(View.VISIBLE);
                    foto16.setVisibility(View.VISIBLE);
                    btnFoto16.setVisibility(View.VISIBLE);
                    btnUpload16.setVisibility(View.VISIBLE);
                    foto17.setVisibility(View.VISIBLE);
                    btnFoto17.setVisibility(View.VISIBLE);
                    btnUpload17.setVisibility(View.VISIBLE);
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
                    foto15.setVisibility(View.GONE);
                    btnFoto15.setVisibility(View.GONE);
                    btnUpload15.setVisibility(View.GONE);
                    foto16.setVisibility(View.GONE);
                    btnFoto16.setVisibility(View.GONE);
                    btnUpload16.setVisibility(View.GONE);
                    foto17.setVisibility(View.GONE);
                    btnFoto17.setVisibility(View.GONE);
                    btnUpload17.setVisibility(View.GONE);
                }
            }
        });

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(requireContext(),R.style.LightDialogTheme);
                progressDialog.setMessage("Salvando..."); // Setting Message
                progressDialog.setTitle("Por favor Espere"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                if((imgPath == null) && (imgPath2 == null) && (imgPath3 == null)){
                    Toast.makeText(requireContext(), "Preencha pelo menos uma foto", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if (codigo.getText().toString().equals("") || codigo == null){
                    Toast.makeText(requireContext(), "Preencha o campo de código", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else{
                FormularioDAO formularioDAO = new FormularioDAO(requireActivity().getApplicationContext());
                final Formulario formulario = new Formulario();
                    formulario.setCodigo(Objects.requireNonNull(codigo.getText()).toString());
                if (imgPath != null) {
                    formulario.setCaminhoImagem(imgPath);
                } else {
                    formulario.setCaminhoImagem("");
                }
                //Panoramica, Alt/Res, Qualidade

                if (imgPath2 != null) {
                    formulario.setCaminhoImagem2(imgPath2);
                } else {
                    formulario.setCaminhoImagem2("");
                }
                if (imgPath3 != null) {
                    formulario.setCaminhoImagem3(imgPath3);
                } else {
                    formulario.setCaminhoImagem3("");
                }
                    if (imgPath4 != null) {
                        formulario.setCaminhoImagem4(imgPath4);
                    } else {
                        formulario.setCaminhoImagem4("");
                    }
                    if (imgPath7 != null) {
                        formulario.setCaminhoImagem5(imgPath7);
                    } else {
                        formulario.setCaminhoImagem5("");
                    }
                    if (imgPath10 != null) {
                        formulario.setCaminhoImagem6(imgPath10);
                    } else {
                        formulario.setCaminhoImagem6("");
                    }
                    if (imgPath13 != null) {
                        formulario.setCaminhoImagem7(imgPath13);
                    } else {
                        formulario.setCaminhoImagem7("");
                    }
                    if (imgPath14 != null) {
                        formulario.setCaminhoImagem8(imgPath14);
                    } else {
                        formulario.setCaminhoImagem8("");
                    }
                    if (imgPath15 != null) {
                        formulario.setCaminhoImagem9(imgPath15);
                    } else {
                        formulario.setCaminhoImagem9("");
                    }
                    if (imgPath16 != null) {
                        formulario.setCaminhoImagem10(imgPath16);
                    } else {
                        formulario.setCaminhoImagem10("");
                    }
                    if (imgPath17 != null) {
                        formulario.setCaminhoImagem11(imgPath17);
                    } else{
                        formulario.setCaminhoImagem11("");
                    }
                    if (imgPath30 != null) {
                        formulario.setCaminhoImagem12(imgPath30);
                    } else {
                        formulario.setCaminhoImagem12("");
                    }
                if((urlFoto == null) || ((urlFoto.toString()).equals(""))) {
                    formulario.setUrlImagem("");
                    formulario.setColor(String.valueOf(R.color.design_default_color_error));
                }else {
                    formulario.setUrlImagem(urlFoto.toString());
                    formulario.setColor(String.valueOf(R.color.colorPrimary));
                }
                if((urlFoto2 == null) || ((urlFoto2.toString()).equals(""))) {
                    formulario.setUrlImagem2("");
                    formulario.setColor2(String.valueOf(R.color.design_default_color_error));
                }else {
                    formulario.setUrlImagem2(urlFoto2.toString());
                    formulario.setColor2(String.valueOf(R.color.colorPrimary));
                }
                if((urlFoto3 == null) || ((urlFoto3.toString()).equals(""))) {
                    formulario.setUrlImagem3("");
                    formulario.setColor3(String.valueOf(R.color.design_default_color_error));
                }else {
                    formulario.setUrlImagem3(urlFoto3.toString());
                    formulario.setColor3(String.valueOf(R.color.colorPrimary));
                }
                    if((urlFoto4 == null) || ((urlFoto4.toString()).equals(""))) {
                        formulario.setUrlImagem4("");
                        formulario.setColor4(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem4(urlFoto4.toString());
                        formulario.setColor4(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto7 == null) || ((urlFoto7.toString()).equals(""))) {
                        formulario.setUrlImagem5("");
                        formulario.setColor5(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem5(urlFoto7.toString());
                        formulario.setColor5(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto10 == null) || ((urlFoto10.toString()).equals(""))) {
                        formulario.setUrlImagem6("");
                        formulario.setColor6(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem6(urlFoto10.toString());
                        formulario.setColor6(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto13 == null) || ((urlFoto13.toString()).equals(""))) {
                        formulario.setUrlImagem7("");
                        formulario.setColor7(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem7(urlFoto13.toString());
                        formulario.setColor7(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto14 == null) || ((urlFoto14.toString()).equals(""))) {
                        formulario.setUrlImagem8("");
                        formulario.setColor8(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem8(urlFoto14.toString());
                        formulario.setColor8(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto15 == null) || ((urlFoto15.toString()).equals(""))) {
                        formulario.setUrlImagem9("");
                        formulario.setColor9(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem9(urlFoto15.toString());
                        formulario.setColor9(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto16 == null) || ((urlFoto16.toString()).equals(""))) {
                        formulario.setUrlImagem10("");
                        formulario.setColor10(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem10(urlFoto16.toString());
                        formulario.setColor10(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto17 == null) || ((urlFoto17.toString()).equals(""))) {
                        formulario.setUrlImagem11("");
                        formulario.setColor11(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem11(urlFoto17.toString());
                        formulario.setColor11(String.valueOf(R.color.colorPrimary));
                    }
                    if((urlFoto30 == null) || ((urlFoto30.toString()).equals(""))) {
                        formulario.setUrlImagem12("");
                        formulario.setColor12(String.valueOf(R.color.design_default_color_error));
                    }else {
                        formulario.setUrlImagem12(urlFoto30.toString());
                        formulario.setColor12(String.valueOf(R.color.colorPrimary));
                    }


                //LOCALIZAÇÂO

                formulario.setEndereco(Objects.requireNonNull(endereco.getText()).toString());
                setLista(formulario,municipio, "municipio");
                formulario.setLatitude(Objects.requireNonNull(latitude.getText()).toString());
                formulario.setLongitude(Objects.requireNonNull(longitude.getText()).toString());
                setLista(formulario, alturaCarga, "alturaCarga");


                //CARACTERISTICAS FÍSICAS

                setLista(formulario,tipoPoste, "tipoPoste");
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
                }
                setLista(formulario,ipEstrutura,"ipEstrutura");
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

                if (ip2.isChecked()) {
                    formulario.setIp2("Sim");
                } else {
                    formulario.setIp2("Não");
                }
                setLista(formulario,ipEstrutura2,"ipEstrutura2");
                formulario.setQuantidadeLampada2(Objects.requireNonNull(quantidadeLampada2.getText().toString()));
                setLista(formulario, tipoPot2, "tipoPot2");
                formulario.setPotReator2(Objects.requireNonNull(potReator2.getText()).toString());
                setLista(formulario, ipAtivacao2, "ipAtivacao2");
                if (vinteEQuatro2.isChecked()) {
                    formulario.setVinteEQuatro2("Sim");
                } else {
                    formulario.setVinteEQuatro2("Não");
                }
                formulario.setQuantidade24H2(Objects.requireNonNull(quantidade24H2.getText().toString()));

                if (ip3.isChecked()) {
                    formulario.setIp3("Sim");
                } else {
                    formulario.setIp3("Não");
                }
                setLista(formulario,ipEstrutura3,"ipEstrutura3");
                formulario.setQuantidadeLampada3(Objects.requireNonNull(quantidadeLampada3.getText().toString()));
                setLista(formulario, tipoPot3, "tipoPot3");
                formulario.setPotReator3(Objects.requireNonNull(potReator3.getText()).toString());
                setLista(formulario, ipAtivacao3, "ipAtivacao3");
                if (vinteEQuatro3.isChecked()) {
                    formulario.setVinteEQuatro3("Sim");
                } else {
                    formulario.setVinteEQuatro3("Não");
                }
                formulario.setQuantidade24H3(Objects.requireNonNull(quantidade24H3.getText().toString()));
                formulario.setObservacaoIP(Objects.requireNonNull(observacaoIP.getText().toString()));


                //TRAFO

                if (ativos.isChecked()) {
                    formulario.setAtivos("Sim");
                } else {
                    formulario.setAtivos("Não");
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
                setLista(formulario,trafoTrifasico,"trafoTrifasico");
                setLista(formulario,trafoMono,"trafoMono");
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
                if (chFusivel.isChecked()) {
                    formulario.setChFusivel("Sim");
                } else {
                    formulario.setChFusivel("Não");
                }
                if (chFaca.isChecked()) {
                    formulario.setChFaca("Sim");
                } else {
                    formulario.setChFaca("Não");
                }
                if (chBanco.isChecked()) {
                    formulario.setBanco("Sim");
                } else {
                    formulario.setBanco("Não");
                }
                if (chFusivelReligador.isChecked()) {
                    formulario.setChFusivelReligador("Sim");
                } else {
                    formulario.setChFusivelReligador("Não");
                }
                setLista(formulario,ramalSubt,"ramalSubt");
                formulario.setObservacaoAtivos(Objects.requireNonNull(observacaoAtivos.getText()).toString());
                formulario.setOutros(Objects.requireNonNull(outros.getText()).toString());

                //MUTUO
                if (mutuo.isChecked()) {
                    formulario.setMutuo("Sim");
                } else {
                    formulario.setMutuo("Não");
                }
                formulario.setQuantidadeOcupantes(Objects.requireNonNull(quantidadeOcupantes.getText()).toString());
                formulario.setQuantidadeCabos(Objects.requireNonNull(quantidadeCabos.getText()).toString());
                setLista(formulario,tipoCabo,"tipoCabo");
                    formulario.setQuantidadeCabosdois(Objects.requireNonNull(quantidadeCabosdois.getText()).toString());
                    setLista(formulario,tipoCabodois,"tipoCabodois");
                formulario.setNome(Objects.requireNonNull(nome.getText()).toString());
                setLista(formulario,finalidade,"finalidade");
                setLista(formulario,ceans,"ceans");
                setLista(formulario,tar,"tar");
                setLista(formulario,reservaTec,"reservaTec");
                setLista(formulario,backbone,"backbone");
                if(placaIdentificadora.isChecked()){
                    formulario.setPlacaIdent("Sim");
                }else {
                    formulario.setPlacaIdent("Não");
                }

                if(descidaCabos.isChecked()){
                    formulario.setDescidaCabos("Sim");
                }else {
                    formulario.setDescidaCabos("Não");
                }
                formulario.setDescricaoIrregularidade(Objects.requireNonNull(descricaoIrregularidade.getText()).toString());
                formulario.setObservacaoMutuo(Objects.requireNonNull(observacaoMutuo.getText()).toString());
                //VEGETAÇÃO
                setLista(formulario,dimensaoVegetacao,"dimensaoVegetacao");
                setLista(formulario,distaciaBaixa,"distanciaBaixa");
                setLista(formulario,distanciaMedia,"distanciaMedia");
                setLista(formulario,estadoArvore,"estadoArvore");
                if(quedaArvore.isChecked()){
                    formulario.setQuedaArvore("Sim");
                }else {
                    formulario.setQuedaArvore("Não");
                }
                setLista(formulario,localArvore,"localArvore");
                formulario.setObservacaoVegetacao(Objects.requireNonNull(observacaoVegetacao.getText()).toString());
                formulario.setNome(Objects.requireNonNull(nome.getText()).toString());
                formulario.setContadorAr(contadorAr);
                formulario.setContadorAt(contadorAt);
                formulario.setContadorIp(contadorIp);
                if (formularioAtual != null) {
                    formulario.setId(formularioAtual.getId());
                    formulario.setData(formularioAtual.getData());
                    if (formularioDAO.atualizar(formulario)) {
                        CadastradosFragment cadastradosFragment = new CadastradosFragment();

                        FragmentManager fm = getParentFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, cadastradosFragment);
                        transaction.commit();
                        Toast.makeText(requireActivity().getApplicationContext(), "Sucesso ao atualizar formulário", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(requireActivity().getApplicationContext(), "Erro ao atualizar formulário", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } else {
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
                    writeDate.setTimeZone(TimeZone.getTimeZone("GMT-04:00"));
                    String s = writeDate.format(date);

                    //endregion
                    formulario.setData(s);
                    if (formularioDAO.salvar(formulario)) {
                        CadastradosFragment cadastradosFragment = new CadastradosFragment();

                        FragmentManager fm = getParentFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, cadastradosFragment);
                        transaction.commit();
                        Toast.makeText(requireActivity().getApplicationContext(), "Sucesso ao salvar formulário", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(requireActivity().getApplicationContext(), "Erro ao salvar formulário", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }}
        });
       if(savedInstanceState != null) {
           imgPath = savedInstanceState.getString("imgPath");
           imagem = BitmapFactory.decodeFile(imgPath);
       }
        return root;
    }
//Pega o valor do spinner e coloca um "-" caso, o usuário não tenha escolhido nenhuma opção.
    public void setLista(Formulario formulario, Spinner spinner, String atributo){
        if(spinner.getSelectedItem().toString().equals(spinner.getItemAtPosition(0).toString())){
            formulario.GenericSetter(atributo,"-");
        }else{
            formulario.GenericSetter(atributo,spinner.getSelectedItem().toString());
        }
    }

    public void mutuoDados(EditText editText, Spinner spinner, Boolean estado){
        if((editText != null) && !estado){
            editText.setText("");
            editText.setEnabled(estado);
            editText.setVisibility(View.GONE);

        }
        else if((editText != null) && estado){
            editText.setEnabled(estado);
            editText.setVisibility(View.VISIBLE);

        }
        else if ((spinner != null) && !estado) {
            spinner.setSelection(0);
            spinner.setEnabled(estado);
            spinner.setVisibility(View.GONE);
        }
        else if ((spinner != null) && estado) {
            spinner.setEnabled(estado);
            spinner.setVisibility(View.VISIBLE);
        }

    }
    public Boolean verificarPermissaoLocaliza() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
        ){
            return true;


        }else{
            ActivityCompat.requestPermissions(requireActivity(),permissions,REQUEST_CODE);
            return false;
        }
    }






    public Boolean verificarPermissao() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED
        ){
                return true;


        }else{
            ActivityCompat.requestPermissions(requireActivity(),permissions,REQUEST_CODE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verificarPermissao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("CODE", String.valueOf(requestCode));
        if(resultCode == RESULT_OK){
            Uri localImagemSelecionada;
            Cursor cursor;
            int columnIndex;


            try{
                switch (requestCode){
                    case IMAGE_CAPTURE_CODE:
                        imgPath = photoFile.getAbsolutePath();
                        Log.i("TAHA", imgPath);
                        imagem = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto.setImageBitmap(imagem);
                        break;
                    case IMAGE_PICK_CODE:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto.setImageBitmap(imagem);
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgPath = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto.setImageBitmap(imagem);
                        break;
                    case IMAGE_CAPTURE_CODE2:
                        imgPath2 = photoFile.getAbsolutePath();
                        imagem2 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto2.setImageBitmap(imagem2);
                        break;
                    case IMAGE_PICK_CODE2:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem2 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto2.setImageBitmap(imagem2);
                        Log.i("ERROAntes", imagem2.toString());
                        String[] filePathColumn2 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn2, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn2[0]);
                        imgPath2 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto2.setImageBitmap(imagem2);
                        break;

                    case IMAGE_CAPTURE_CODE3:
                        imgPath3 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath3);
                        imagem3 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto3.setImageBitmap(imagem3);
                        break;
                    case IMAGE_PICK_CODE3:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem3 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto3.setImageBitmap(imagem3);
                        String[] filePathColumn3 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn3, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn3[0]);
                        imgPath3 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto3.setImageBitmap(imagem3);
                        break;

                    case IMAGE_CAPTURE_CODE4:
                        imgPath4 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath4);
                        imagem4 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto4.setImageBitmap(imagem4);
                        break;
                    case IMAGE_PICK_CODE4:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem4 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto4.setImageBitmap(imagem4);
                        String[] filePathColumn4 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn4, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn4[0]);
                        imgPath4 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto4.setImageBitmap(imagem4);
                        break;
                    case IMAGE_CAPTURE_CODE7:
                        imgPath7 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath7);
                        imagem7 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto7.setImageBitmap(imagem7);
                        break;
                    case IMAGE_PICK_CODE7:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem7 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto7.setImageBitmap(imagem7);
                        String[] filePathColumn7 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn7, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn7[0]);
                        imgPath7 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto7.setImageBitmap(imagem7);
                        break;
                    case IMAGE_CAPTURE_CODE10:
                        imgPath10 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath10);
                        imagem10 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto10.setImageBitmap(imagem10);
                        break;
                    case IMAGE_PICK_CODE10:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem10 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto10.setImageBitmap(imagem10);
                        String[] filePathColumn10 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn10, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn10[0]);
                        imgPath10 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto10.setImageBitmap(imagem10);
                        break;
                    case IMAGE_CAPTURE_CODE13:
                        imgPath13 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath13);
                        imagem13 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto13.setImageBitmap(imagem13);
                        break;
                    case IMAGE_PICK_CODE13:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem13 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto12.setImageBitmap(imagem12);
                        String[] filePathColumn13 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn13, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn13[0]);
                        imgPath13 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto13.setImageBitmap(imagem13);
                        break;
                    case IMAGE_CAPTURE_CODE14:
                        imgPath14 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath14);
                        imagem14 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto14.setImageBitmap(imagem14);
                        break;
                    case IMAGE_PICK_CODE14:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem14 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto12.setImageBitmap(imagem12);
                        String[] filePathColumn14 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn14, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn14[0]);
                        imgPath14 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto14.setImageBitmap(imagem14);
                        break;
                    case IMAGE_CAPTURE_CODE15:
                        imgPath15 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath15);
                        imagem15 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto15.setImageBitmap(imagem15);
                        break;
                    case IMAGE_PICK_CODE15:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem15 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto12.setImageBitmap(imagem12);
                        String[] filePathColumn15 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn15, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn15[0]);
                        imgPath15 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto15.setImageBitmap(imagem15);
                        break;

                    case IMAGE_CAPTURE_CODE16:
                        imgPath16 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath16);
                        imagem16 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto16.setImageBitmap(imagem16);
                        break;
                    case IMAGE_PICK_CODE16:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem16 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto12.setImageBitmap(imagem12);
                        String[] filePathColumn16 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn16, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn16[0]);
                        imgPath16 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto16.setImageBitmap(imagem16);
                        break;
                    case IMAGE_CAPTURE_CODE17:
                        imgPath17 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath17);
                        imagem17 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto17.setImageBitmap(imagem17);
                        break;
                    case IMAGE_PICK_CODE17:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem17 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        //foto12.setImageBitmap(imagem12);
                        String[] filePathColumn17 = { MediaStore.Images.Media.DATA };
                        // Get the cursor
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn17, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        columnIndex = cursor.getColumnIndex(filePathColumn17[0]);
                        imgPath17 = cursor.getString(columnIndex);
                        cursor.close();
                        // Set the Image in ImageView after decoding the String
                        foto17.setImageBitmap(imagem17);
                        break;
                    case IMAGE_CAPTURE_CODE30:
                        imgPath30 = photoFile.getAbsolutePath();
                        Log.i("TAHC", imgPath30);
                        imagem30 = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        foto30.setImageBitmap(imagem30);
                        break;
                    case IMAGE_PICK_CODE30:
                        Log.i("TAH2", data.getData().toString());
                        localImagemSelecionada = data.getData();
                        imagem30 = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(),localImagemSelecionada);
                        String[] filePathColumn30 = { MediaStore.Images.Media.DATA };
                        cursor = requireActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada,
                                filePathColumn30, null, null, null);
                        cursor.moveToFirst();
                        columnIndex = cursor.getColumnIndex(filePathColumn30[0]);
                        imgPath30 = cursor.getString(columnIndex);
                        cursor.close();
                        foto30.setImageBitmap(imagem30);
                        break;


                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    public void chamarGaleria(int imagemCodigo){
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(cameraIntent.resolveActivity(requireActivity().getApplicationContext().getPackageManager()) != null){
            startActivityForResult(cameraIntent, imagemCodigo);
        }
    }

    public void chamarCamera(int imagemCodigo){
         Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getApplicationContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            try {

                photoFile = createImageFile();

                Log.i("TAG",photoFile.getAbsolutePath());

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(requireActivity().getApplicationContext(),
                            "com.example.satelprojetos.provider",
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, imagemCodigo);
                }
            } catch (Exception ex) {
                // Error occurred while creating the File

            }


        }else
        {

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


    private static Bitmap rotateImageIfRequired(Context context,Bitmap img, String filePath) throws IOException {
        ExifInterface ei;
            ei = new ExifInterface(filePath);

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
Log.i("TAGX", String.valueOf(orientation));
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:

                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:

                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:

                return rotateImage(img, 270);
            default:

                return img;
        }
    }

    public void upload(Bitmap imagemUpload,String imgPathUpload, EditText codigoUpload, int contadorUpload, final int codigoSetor, final boolean estado, String sufixo){
        if(imagemUpload == null){
            Toast.makeText(requireActivity().getApplicationContext(), "Escolha primeiro uma foto para fazer o upload", Toast.LENGTH_SHORT).show();

        }else if(codigoUpload.getText().toString().equals("") ||codigoUpload == null) {
            Toast.makeText(requireActivity().getApplicationContext(), "Insira o código do poste primeiro", Toast.LENGTH_SHORT).show();
        }else {
            if(isNetworkAvailable()) {
                progressDialog = new ProgressDialog(requireContext(),R.style.LightDialogTheme);
                progressDialog.setMessage("Fazendo upload..."); // Setting Message
                progressDialog.setTitle("Por favor Espere"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                String nomeFoto = UUID.randomUUID().toString();
                Bitmap imagemCorrigida;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    imagemCorrigida = rotateImageIfRequired(requireContext(),imagemUpload,imgPathUpload);
                } catch (IOException e) {
                    imagemCorrigida = imagemUpload;
                    e.printStackTrace();
                }

                imagemCorrigida.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] dadosImagem = baos.toByteArray();
                String pastaNome = codigoUpload.getText().toString() + "_" + sufixo + contadorUpload +".jpeg";
                final StorageReference imageRef = storageReference
                        .child("imagens")
                        .child(Base64Custom.codificarBase64(autentificacao.getCurrentUser().getEmail()))
                        .child(codigoUpload.getText().toString())
                        .child(pastaNome);
                UploadTask uploadTask = imageRef.putBytes(dadosImagem);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity().getApplicationContext(), "Erro ao fazer upload verifique a conexão com a internet", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i("ERRO", "EERO2");
                        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                switch (codigoSetor) {
                                    case 1:
                                        urlFoto = task.getResult();
                                        novoUpload = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 2:
                                        urlFoto2 = task.getResult();
                                        novoUpload2 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 3:
                                        urlFoto3 = task.getResult();
                                        novoUpload3 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 4:
                                        urlFoto4 = task.getResult();
                                        novoUpload4 = estado;
                                        contadorIp = contadorIp+1;
                                        break;
                                    case 7:
                                        urlFoto7 = task.getResult();
                                        novoUpload7 = estado;
                                        contadorIp = contadorIp+1;
                                        break;
                                    case 10:
                                        urlFoto10 = task.getResult();
                                        novoUpload10 = estado;
                                        contadorIp = contadorIp+1;
                                        break;
                                    case 13:
                                        urlFoto13 = task.getResult();
                                        novoUpload13 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 14:
                                        urlFoto14 = task.getResult();
                                        novoUpload14 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 15:
                                        urlFoto15 = task.getResult();
                                        novoUpload15 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 16:
                                        urlFoto16 = task.getResult();
                                        novoUpload16 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 17:
                                        urlFoto17 = task.getResult();
                                        novoUpload17 = estado;
                                        contadorAt = contadorAt+1;
                                        break;
                                    case 30:
                                        urlFoto30 = task.getResult();
                                        novoUpload30 = estado;
                                        contadorAr = contadorAr+1;
                                        break;
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.i("ERRO", "EERO3");
                    }
                });
            }else {
                Toast.makeText(requireActivity().getApplicationContext(), "Erro ao fazer upload verifique a conexão com a internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imgPath", imgPath);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

}

