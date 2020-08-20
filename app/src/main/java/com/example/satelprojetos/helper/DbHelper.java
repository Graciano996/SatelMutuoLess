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
                " ip2 TEXT NOT NULL,ipEstrutura2 TEXT NOT NULL, quantidadeLampada2 TEXT NOT NULL," +
                " ipAtivacao2 TEXT NOT NULL, vinteEQuatro2 TEXT NOT NULL, quantidade24H2 TEXT NOT NULL," +
                " tipoPot2 TEXT NOT NULL, potReator2 TEXT NOT NULL, " +
                " ip3 TEXT NOT NULL, ipEstrutura3 TEXT NOT NULL, quantidadeLampada3 TEXT NOT NULL," +
                " ipAtivacao3 TEXT NOT NULL, vinteEQuatro3 TEXT NOT NULL, quantidade24H3 TEXT NOT NULL," +
                " tipoPot3 TEXT NOT NULL, potReator3 TEXT NOT NULL, observacaoIP TEXT NOT NULL," +
                " ativos TEXT NOT NULL, chkTrafoTrifasico TEXT NOT NULL, chkTrafoMono TEXT NOT  NULL,"+
                " trafoTrifasico TEXT NOT NULL, trafoMono TEXT NOT NULL,chFusivel TEXT NOT NULL,"+
                " chFaca TEXT NOT NULL,religador TEXT NOT NULL, medicao TEXT NOT NULL, banco TEXT NOT NULL," +
                " chFusivelReligador TEXT NOT NULL, ramalSubt TEXT NOT NULL, outros TEXT NOT NULL ,observacaoAtivos TEXT NOT NULL,"+
                " mutuo TEXT NOT NULL, quantidadeOcupantes TEXT NOT NULL, " +
                " quantidadeCabos TEXT NOT NULL, tipoCabo TEXT NOT NULL, nomeEmpresa TEXT NOT NULL," +
                " finalidade TEXT NOT NULL, ceans TEXT NOT NULL, tar TEXT NOT NULL, reservaTec TEXT NOT NULL," +
                " backbone TEXT NOT NULL, placaIdent TEXT NOT NULL, descidaCabos TEXT NOT NULL," +
                " observacaoMutuo TEXT NOT NULL, descricaoIrregularidade TEXT NOT NULL," +
                " quantidadeCabos2 TEXT NOT NULL, tipoCabo2 TEXT NOT NULL,quantidadeCabos2dois TEXT NOT NULL, tipoCabo2dois TEXT NOT NULL, nomeEmpresa2 TEXT NOT NULL," +
                " finalidade2 TEXT NOT NULL, ceans2 TEXT NOT NULL, tar2 TEXT NOT NULL, reservaTec2 TEXT NOT NULL," +
                " backbone2 TEXT NOT NULL, placaIdent2 TEXT NOT NULL, descidaCabos2 TEXT NOT NULL," +
                " observacaoMutuo2 TEXT NOT NULL, descricaoIrregularidade2 TEXT NOT NULL," +
                " quantidadeCabos3 TEXT NOT NULL, tipoCabo3 TEXT NOT NULL, nomeEmpresa3 TEXT NOT NULL," +
                " finalidade3 TEXT NOT NULL, ceans3 TEXT NOT NULL, tar3 TEXT NOT NULL, reservaTec3 TEXT NOT NULL," +
                " backbone3 TEXT NOT NULL, placaIdent3 TEXT NOT NULL, descidaCabos3 TEXT NOT NULL," +
                " observacaoMutuo3 TEXT NOT NULL, descricaoIrregularidade3 TEXT NOT NULL," +
                " quantidadeCabos4 TEXT NOT NULL, tipoCabo4 TEXT NOT NULL, nomeEmpresa4 TEXT NOT NULL," +
                " finalidade4 TEXT NOT NULL, ceans4 TEXT NOT NULL, tar4 TEXT NOT NULL, reservaTec4 TEXT NOT NULL," +
                " backbone4 TEXT NOT NULL, placaIdent4 TEXT NOT NULL,descidaCabos4 TEXT NOT NULL," +
                " observacaoMutuo4 TEXT NOT NULL, descricaoIrregularidade4 TEXT NOT NULL," +
                " quantidadeCabos5 TEXT NOT NULL, tipoCabo5 TEXT NOT NULL, nomeEmpresa5 TEXT NOT NULL," +
                " finalidade5 TEXT NOT NULL, ceans5 TEXT NOT NULL, tar5 TEXT NOT NULL, reservaTec5 TEXT NOT NULL," +
                " backbone5 TEXT NOT NULL, placaIdent5 TEXT NOT NULL,descidaCabos5 TEXT NOT NULL," +
                " observacaoMutuo5 TEXT NOT NULL, descricaoIrregularidade5 TEXT NOT NULL," +
                " dimensaoVegetacao TEXT NOT NULL, distanciaBaixa TEXT NOT NULL, distanciaMedia TEXT NOT NULL," +
                " estadoArvore TEXT NOT NULL,quedaArvore TEXT NOT NULL, localArvore TEXT NOT NULL, " +
                " observacaoVegetacao TEXT NOT NULL," +
                " caminhoImagem TEXT NOT NULL, caminhoImagem2 TEXT NOT NULL, caminhoImagem3 TEXT NOT NULL," +
                " urlImagem TEXT NOT NULL, urlImagem2 TEXT NOT NULL, urlImagem3 TEXT NOT NULL," +
                " color TEXT NOT NULL, color2 TEXT NOT NULL, color3 TEXT NOT NULL);";

        String sqlEnviado = "CREATE TABLE IF NOT EXISTS " + TABLE_ENVIADO + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " codigo TEXT NOT NULL , data TEXT NOT NULL, endereco TEXT NOT NULL, nome TEXT NOT NULL, municipio TEXT NOT NULL," +
                " alturaCarga TEXT NOT NULL, " +
                " latitude TEXT NOT NULL, longitude TEXT NOT NULL, tipoPoste TEXT NOT NULL ,normal TEXT NOT NULL," +
                " ferragemExposta TEXT NOT NULL, fletido TEXT NOT NULL, danificado TEXT NOT NULL, " +
                " abalroado TEXT NOT NULL, trincado TEXT NOT NULL, observacaoFisicas TEXT NOT NULL," +
                " ip TEXT NOT NULL,ipEstrutura TEXT NOT NULL,quantidadeLampada TEXT NOT NULL," +
                " ipAtivacao TEXT NOT NULL,vinteEQuatro TEXT NOT NULL,quantidade24H TEXT NOT NULL," +
                " tipoPot TEXT NOT NULL, potReator TEXT NOT NULL, " +
                " ip2 TEXT NOT NULL,ipEstrutura2 TEXT NOT NULL, quantidadeLampada2 TEXT NOT NULL," +
                " ipAtivacao2 TEXT NOT NULL, vinteEQuatro2 TEXT NOT NULL, quantidade24H2 TEXT NOT NULL," +
                " tipoPot2 TEXT NOT NULL, potReator2 TEXT NOT NULL, " +
                " ip3 TEXT NOT NULL, ipEstrutura3 TEXT NOT NULL, quantidadeLampada3 TEXT NOT NULL," +
                " ipAtivacao3 TEXT NOT NULL, vinteEQuatro3 TEXT NOT NULL, quantidade24H3 TEXT NOT NULL," +
                " tipoPot3 TEXT NOT NULL, potReator3 TEXT NOT NULL, observacaoIP TEXT NOT NULL," +
                " ativos TEXT NOT NULL, chkTrafoTrifasico TEXT NOT NULL, chkTrafoMono TEXT NOT  NULL,"+
                " trafoTrifasico TEXT NOT NULL, trafoMono TEXT NOT NULL,chFusivel TEXT NOT NULL,"+
                " chFaca TEXT NOT NULL,religador TEXT NOT NULL, medicao TEXT NOT NULL, banco TEXT NOT NULL," +
                " chFusivelReligador TEXT NOT NULL, ramalSubt TEXT NOT NULL, outros TEXT NOT NULL ,observacaoAtivos TEXT NOT NULL,"+
                " mutuo TEXT NOT NULL, quantidadeOcupantes TEXT NOT NULL, " +
                " quantidadeCabos TEXT NOT NULL, tipoCabo TEXT NOT NULL, nomeEmpresa TEXT NOT NULL," +
                " finalidade TEXT NOT NULL, ceans TEXT NOT NULL, tar TEXT NOT NULL, reservaTec TEXT NOT NULL," +
                " backbone TEXT NOT NULL, placaIdent TEXT NOT NULL, descidaCabos TEXT NOT NULL," +
                " observacaoMutuo TEXT NOT NULL, descricaoIrregularidade TEXT NOT NULL," +
                " quantidadeCabos2 TEXT NOT NULL, tipoCabo2 TEXT NOT NULL,quantidadeCabos2dois TEXT NOT NULL, tipoCabo2dois TEXT NOT NULL, nomeEmpresa2 TEXT NOT NULL," +
                " finalidade2 TEXT NOT NULL, ceans2 TEXT NOT NULL, tar2 TEXT NOT NULL, reservaTec2 TEXT NOT NULL," +
                " backbone2 TEXT NOT NULL, placaIdent2 TEXT NOT NULL, descidaCabos2 TEXT NOT NULL," +
                " observacaoMutuo2 TEXT NOT NULL, descricaoIrregularidade2 TEXT NOT NULL," +
                " quantidadeCabos3 TEXT NOT NULL, tipoCabo3 TEXT NOT NULL, nomeEmpresa3 TEXT NOT NULL," +
                " finalidade3 TEXT NOT NULL, ceans3 TEXT NOT NULL, tar3 TEXT NOT NULL, reservaTec3 TEXT NOT NULL," +
                " backbone3 TEXT NOT NULL, placaIdent3 TEXT NOT NULL, descidaCabos3 TEXT NOT NULL," +
                " observacaoMutuo3 TEXT NOT NULL, descricaoIrregularidade3 TEXT NOT NULL," +
                " quantidadeCabos4 TEXT NOT NULL, tipoCabo4 TEXT NOT NULL, nomeEmpresa4 TEXT NOT NULL," +
                " finalidade4 TEXT NOT NULL, ceans4 TEXT NOT NULL, tar4 TEXT NOT NULL, reservaTec4 TEXT NOT NULL," +
                " backbone4 TEXT NOT NULL, placaIdent4 TEXT NOT NULL,descidaCabos4 TEXT NOT NULL," +
                " observacaoMutuo4 TEXT NOT NULL, descricaoIrregularidade4 TEXT NOT NULL," +
                " quantidadeCabos5 TEXT NOT NULL, tipoCabo5 TEXT NOT NULL, nomeEmpresa5 TEXT NOT NULL," +
                " finalidade5 TEXT NOT NULL, ceans5 TEXT NOT NULL, tar5 TEXT NOT NULL, reservaTec5 TEXT NOT NULL," +
                " backbone5 TEXT NOT NULL, placaIdent5 TEXT NOT NULL,descidaCabos5 TEXT NOT NULL," +
                " observacaoMutuo5 TEXT NOT NULL, descricaoIrregularidade5 TEXT NOT NULL," +
                " dimensaoVegetacao TEXT NOT NULL, distanciaBaixa TEXT NOT NULL, distanciaMedia TEXT NOT NULL," +
                " estadoArvore TEXT NOT NULL,quedaArvore TEXT NOT NULL, localArvore TEXT NOT NULL, " +
                " observacaoVegetacao TEXT NOT NULL," +
                " caminhoImagem TEXT NOT NULL, caminhoImagem2 TEXT NOT NULL, caminhoImagem3 TEXT NOT NULL," +
                " urlImagem TEXT NOT NULL, urlImagem2 TEXT NOT NULL, urlImagem3 TEXT NOT NULL," +
                " color TEXT NOT NULL, color2 TEXT NOT NULL, color3 TEXT NOT NULL);";

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
