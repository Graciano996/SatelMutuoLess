package com.example.satelprojetos.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String DB_NAME = "DB_FORMULARIO";
    public static String TABLE_FORMULARIO = "formulario";
    public static String TABLE_ENVIADO = "enviado";
    public static String TABLE_MAPA = "mapa";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_FORMULARIO + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "codigo TEXT NOT NULL ,data TEXT NOT NULL, endereco TEXT NOT NULL, nome TEXT NOT NULL, municipio TEXT NOT NULL," +
                " alturaCarga TEXT NOT NULL, " +
                " latitude TEXT NOT NULL, longitude TEXT NOT NULL, tipoPoste TEXT NOT NULL ,normal TEXT NOT NULL," +
                " ferragemExposta TEXT NOT NULL, fletido TEXT NOT NULL, danificado TEXT NOT NULL, " +
                " abalroado TEXT NOT NULL, trincado TEXT NOT NULL, observacaoFisicas TEXT NOT NULL," +
                " ip TEXT NOT NULL,ipEstrutura TEXT NOT NULL,quantidadeLampada TEXT NOT NULL," +
                " ipAtivacao TEXT NOT NULL,vinteEQuatro TEXT NOT NULL,quantidade24H TEXT NOT NULL," +
                " tipoPot TEXT NOT NULL, potReator TEXT NOT NULL, " +
                " observacaoIP TEXT NOT NULL," +
                " ativos TEXT NOT NULL, chkTrafoTrifasico TEXT NOT NULL, chkTrafoMono TEXT NOT  NULL,"+
                " trafoTrifasico TEXT NOT NULL, trafoMono TEXT NOT NULL,chFusivel TEXT NOT NULL,"+
                " chFaca TEXT NOT NULL,religador TEXT NOT NULL, medicao TEXT NOT NULL, banco TEXT NOT NULL," +
                " chFusivelReligador TEXT NOT NULL, ramalSubt TEXT NOT NULL, outros TEXT NOT NULL ,observacaoAtivos TEXT NOT NULL,"+
                " mutuo TEXT NOT NULL, quantidadeOcupantes TEXT NOT NULL, " +
                " quantidadeCabos TEXT NOT NULL, tipoCabo TEXT NOT NULL,quantidadeCabosdois TEXT NOT NULL, tipoCabodois TEXT NOT NULL," +
                " nomeEmpresa TEXT NOT NULL," +
                " finalidade TEXT NOT NULL, ceans TEXT NOT NULL, tar TEXT NOT NULL, reservaTec TEXT NOT NULL," +
                " backbone TEXT NOT NULL, placaIdent TEXT NOT NULL, descidaCabos TEXT NOT NULL," +
                " observacaoMutuo TEXT NOT NULL, descricaoIrregularidade TEXT NOT NULL," +
                " dimensaoVegetacao TEXT NOT NULL, distanciaBaixa TEXT NOT NULL, distanciaMedia TEXT NOT NULL," +
                " estadoArvore TEXT NOT NULL,quedaArvore TEXT NOT NULL, localArvore TEXT NOT NULL, " +
                " observacaoVegetacao TEXT NOT NULL,vegetacao TEXT NOT NULL," +
                " caminhoImagem TEXT NOT NULL, caminhoImagem2 TEXT NOT NULL, caminhoImagem3 TEXT NOT NULL," +
                "caminhoImagem4 TEXT NOT NULL," +
                "caminhoImagem7 TEXT NOT NULL,caminhoImagem8 TEXT NOT NULL,caminhoImagem9 TEXT NOT NULL," +
                "caminhoImagem10 TEXT NOT NULL,caminhoImagem11 TEXT NOT NULL,caminhoImagem12 TEXT NOT NULL," +
                " urlImagem TEXT NOT NULL, urlImagem2 TEXT NOT NULL, urlImagem3 TEXT NOT NULL," +
                " urlImagem4 TEXT NOT NULL," +
                " urlImagem7 TEXT NOT NULL,urlImagem8 TEXT NOT NULL,urlImagem9 TEXT NOT NULL," +
                " urlImagem10 TEXT NOT NULL,urlImagem11 TEXT NOT NULL,urlImagem12 TEXT NOT NULL," +
                " color TEXT NOT NULL, color2 TEXT NOT NULL, color3 TEXT NOT NULL, color4 TEXT NOT NULL," +
                " color7 TEXT NOT NULL, color8 TEXT NOT NULL," +
                " color9 TEXT NOT NULL, color10 TEXT NOT NULL, color11 TEXT NOT NULL, color12 TEXT NOT NULL," +
                " contadorIp INTEGER, contadorAr INTEGER,contadorAt INTEGER);";

        String sqlEnviado = "CREATE TABLE IF NOT EXISTS " + TABLE_ENVIADO + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " codigo TEXT NOT NULL , data TEXT NOT NULL, endereco TEXT NOT NULL, nome TEXT NOT NULL, municipio TEXT NOT NULL," +
                " alturaCarga TEXT NOT NULL, " +
                " latitude TEXT NOT NULL, longitude TEXT NOT NULL, tipoPoste TEXT NOT NULL ,normal TEXT NOT NULL," +
                " ferragemExposta TEXT NOT NULL, fletido TEXT NOT NULL, danificado TEXT NOT NULL, " +
                " abalroado TEXT NOT NULL, trincado TEXT NOT NULL, observacaoFisicas TEXT NOT NULL," +
                " ip TEXT NOT NULL,ipEstrutura TEXT NOT NULL,quantidadeLampada TEXT NOT NULL," +
                " ipAtivacao TEXT NOT NULL,vinteEQuatro TEXT NOT NULL,quantidade24H TEXT NOT NULL," +
                " tipoPot TEXT NOT NULL, potReator TEXT NOT NULL, " +
                " observacaoIP TEXT NOT NULL," +
                " ativos TEXT NOT NULL, chkTrafoTrifasico TEXT NOT NULL, chkTrafoMono TEXT NOT  NULL,"+
                " trafoTrifasico TEXT NOT NULL, trafoMono TEXT NOT NULL,chFusivel TEXT NOT NULL,"+
                " chFaca TEXT NOT NULL,religador TEXT NOT NULL, medicao TEXT NOT NULL, banco TEXT NOT NULL," +
                " chFusivelReligador TEXT NOT NULL, ramalSubt TEXT NOT NULL, outros TEXT NOT NULL ,observacaoAtivos TEXT NOT NULL,"+
                " mutuo TEXT NOT NULL, quantidadeOcupantes TEXT NOT NULL, " +
                " quantidadeCabos TEXT NOT NULL, tipoCabo TEXT NOT NULL,quantidadeCabosdois TEXT NOT NULL, tipoCabodois TEXT NOT NULL," +
                " nomeEmpresa TEXT NOT NULL," +
                " finalidade TEXT NOT NULL, ceans TEXT NOT NULL, tar TEXT NOT NULL, reservaTec TEXT NOT NULL," +
                " backbone TEXT NOT NULL, placaIdent TEXT NOT NULL, descidaCabos TEXT NOT NULL," +
                " observacaoMutuo TEXT NOT NULL, descricaoIrregularidade TEXT NOT NULL," +
                " dimensaoVegetacao TEXT NOT NULL, distanciaBaixa TEXT NOT NULL, distanciaMedia TEXT NOT NULL," +
                " estadoArvore TEXT NOT NULL,quedaArvore TEXT NOT NULL, localArvore TEXT NOT NULL, " +
                " observacaoVegetacao TEXT NOT NULL,vegetacao TEXT NOT NULL," +
                " caminhoImagem TEXT NOT NULL, caminhoImagem2 TEXT NOT NULL, caminhoImagem3 TEXT NOT NULL," +
                "caminhoImagem4 TEXT NOT NULL," +
                "caminhoImagem7 TEXT NOT NULL,caminhoImagem8 TEXT NOT NULL,caminhoImagem9 TEXT NOT NULL," +
                "caminhoImagem10 TEXT NOT NULL,caminhoImagem11 TEXT NOT NULL,caminhoImagem12 TEXT NOT NULL," +
                " urlImagem TEXT NOT NULL, urlImagem2 TEXT NOT NULL, urlImagem3 TEXT NOT NULL," +
                " urlImagem4 TEXT NOT NULL," +
                " urlImagem7 TEXT NOT NULL,urlImagem8 TEXT NOT NULL,urlImagem9 TEXT NOT NULL," +
                " urlImagem10 TEXT NOT NULL,urlImagem11 TEXT NOT NULL,urlImagem12 TEXT NOT NULL," +
                " color TEXT NOT NULL, color2 TEXT NOT NULL, color3 TEXT NOT NULL, color4 TEXT NOT NULL," +
                " color7 TEXT NOT NULL, color8 TEXT NOT NULL," +
                " color9 TEXT NOT NULL, color10 TEXT NOT NULL, color11 TEXT NOT NULL, color12 TEXT NOT NULL," +
                " contadorIp INTEGER, contadorAr INTEGER,contadorAt INTEGER);";

        String sqlMapa = "CREATE TABLE IF NOT EXISTS " + TABLE_MAPA + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
        " codigo TEXT NOT NULL , latitude TEXT NOT NULL, longitude TEXT NOT NULL, cadastrado TEXT NOT NULL, existe TEXT NOT NULL);";

        try{
            db.execSQL(sql);
            db.execSQL(sqlEnviado);
            db.execSQL(sqlMapa);
            Log.i("INFO DB", "Sucesso ao criar a tabela mapa");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
