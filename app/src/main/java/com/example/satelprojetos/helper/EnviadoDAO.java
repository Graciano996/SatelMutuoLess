package com.example.satelprojetos.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.satelprojetos.model.Formulario;

import java.util.ArrayList;
import java.util.List;

public class EnviadoDAO implements IFormularioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public EnviadoDAO(Context context){
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();

    }
    @Override
    public boolean salvar(Formulario formulario) {
        ContentValues cv = new ContentValues();
        cv.put("contadorIp", formulario.getContadorIp());
        cv.put("contadorAr", formulario.getContadorAr());
        cv.put("contadorAt", formulario.getContadorAt());
        cv.put("color", formulario.getColor());
        cv.put("color2", formulario.getColor2());
        cv.put("color3", formulario.getColor3());
        cv.put("color4", formulario.getColor4());
        cv.put("color5", formulario.getColor5());
        cv.put("color6", formulario.getColor6());
        cv.put("color7", formulario.getColor7());
        cv.put("color8", formulario.getColor8());
        cv.put("color9", formulario.getColor9());
        cv.put("color10", formulario.getColor10());
        cv.put("color11", formulario.getColor11());
        cv.put("color12", formulario.getColor12());
        cv.put("codigo", formulario.getCodigo());
        cv.put("caminhoImagem", formulario.getCaminhoImagem());
        cv.put("caminhoImagem2", formulario.getCaminhoImagem2());
        cv.put("caminhoImagem3", formulario.getCaminhoImagem3());
        cv.put("caminhoImagem4", formulario.getCaminhoImagem4());
        cv.put("caminhoImagem5", formulario.getCaminhoImagem5());
        cv.put("caminhoImagem6", formulario.getCaminhoImagem6());
        cv.put("caminhoImagem7", formulario.getCaminhoImagem7());
        cv.put("caminhoImagem8", formulario.getCaminhoImagem8());
        cv.put("caminhoImagem9", formulario.getCaminhoImagem9());
        cv.put("caminhoImagem10", formulario.getCaminhoImagem10());
        cv.put("caminhoImagem11", formulario.getCaminhoImagem11());
        cv.put("caminhoImagem12", formulario.getCaminhoImagem12());
        cv.put("urlImagem", formulario.getUrlImagem());
        cv.put("urlImagem2", formulario.getUrlImagem2());
        cv.put("urlImagem3", formulario.getUrlImagem3());
        cv.put("urlImagem4", formulario.getUrlImagem4());
        cv.put("urlImagem5", formulario.getUrlImagem5());
        cv.put("urlImagem6", formulario.getUrlImagem6());
        cv.put("urlImagem7", formulario.getUrlImagem7());
        cv.put("urlImagem8", formulario.getUrlImagem8());
        cv.put("urlImagem9", formulario.getUrlImagem9());
        cv.put("urlImagem10", formulario.getUrlImagem10());
        cv.put("urlImagem11", formulario.getUrlImagem11());
        cv.put("urlImagem12", formulario.getUrlImagem12());

        cv.put("data", formulario.getData());
        cv.put("endereco", formulario.getEndereco());
        cv.put("nome", formulario.getNome());
        cv.put("municipio", formulario.getMunicipio());
        cv.put("alturaCarga", formulario.getAlturaCarga());
        cv.put("latitude", formulario.getLatitude());
        cv.put("longitude", formulario.getLongitude());


        cv.put("tipoPoste",formulario.getTipoPoste());
        cv.put("normal", formulario.getNormal());
        cv.put("ferragemExposta", formulario.getFerragemExposta());
        cv.put("fletido", formulario.getFletido());
        cv.put("danificado", formulario.getDanificado());
        cv.put("fletido", formulario.getFletido());
        cv.put("abalroado", formulario.getAbalroado());
        cv.put("trincado", formulario.getTrincado());
        cv.put("observacaoFisicas", formulario.getObservacaoFisicas());


        cv.put("ip", formulario.getIp());
        cv.put("ipEstrutura", formulario.getIpEstrutura());
        cv.put("quantidadeLampada", formulario.getQuantidadeLampada());
        cv.put("tipoPot", formulario.getTipoPot());
        cv.put("potReator", formulario.getPotReator());
        cv.put("ipAtivacao", formulario.getIpAtivacao());
        cv.put("vinteEQuatro", formulario.getVinteEQuatro());
        cv.put("quantidade24H", formulario.getQuantidade24H());

        cv.put("ip2", formulario.getIp2());
        cv.put("ipEstrutura2", formulario.getIpEstrutura2());
        cv.put("quantidadeLampada2", formulario.getQuantidadeLampada2());
        cv.put("tipoPot2", formulario.getTipoPot2());
        cv.put("potReator2", formulario.getPotReator2());
        cv.put("ipAtivacao2", formulario.getIpAtivacao2());
        cv.put("vinteEQuatro2", formulario.getVinteEQuatro2());
        cv.put("quantidade24H2", formulario.getQuantidade24H2());

        cv.put("ip3", formulario.getIp3());
        cv.put("ipEstrutura3", formulario.getIpEstrutura3());
        cv.put("quantidadeLampada3", formulario.getQuantidadeLampada3());
        cv.put("tipoPot3", formulario.getTipoPot3());
        cv.put("potReator3", formulario.getPotReator3());
        cv.put("ipAtivacao3", formulario.getIpAtivacao3());
        cv.put("vinteEQuatro3", formulario.getVinteEQuatro3());
        cv.put("quantidade24H3", formulario.getQuantidade24H3());
        cv.put("observacaoIP", formulario.getObservacaoIP());

        cv.put("ativos", formulario.getAtivos());
        cv.put("chkTrafoTrifasico", formulario.getChkTrafoTrifasico());
        cv.put("chkTrafoMono", formulario.getChkTrafoMono());
        cv.put("trafoTrifasico", formulario.getTrafoTrifasico());
        cv.put("trafoMono", formulario.getTrafoMono());
        cv.put("religador", formulario.getReligador());
        cv.put("medicao", formulario.getMedicao());
        cv.put("chFusivel", formulario.getChFusivel());
        cv.put("chFaca", formulario.getChFaca());
        cv.put("banco", formulario.getBanco());
        cv.put("chFusivelReligador", formulario.getChFusivelReligador());
        cv.put("ramalSubt", formulario.getRamalSubt());
        cv.put("observacaoAtivos", formulario.getObservacaoAtivos());
        cv.put("outros", formulario.getOutros());


        cv.put("mutuo", formulario.getMutuo());
        cv.put("quantidadeOcupantes", formulario.getQuantidadeOcupantes());

        cv.put("quantidadeCabos",formulario.getQuantidadeCabos());
        cv.put("tipoCabo", formulario.getTipoCabo());
        cv.put("quantidadeCabosdois",formulario.getQuantidadeCabosdois());
        cv.put("tipoCabodois", formulario.getTipoCabodois());
        cv.put("nomeEmpresa", formulario.getNome());
        cv.put("finalidade",formulario.getFinalidade());
        cv.put("ceans", formulario.getCeans());
        cv.put("tar", formulario.getTar());
        cv.put("reservaTec", formulario.getReservaTec());
        cv.put("backbone", formulario.getBackbone());
        cv.put("placaIdent", formulario.getPlacaIdent());
        cv.put("descidaCabos", formulario.getDescidaCabos());
        cv.put("descricaoIrregularidade", formulario.getDescricaoIrregularidade());
        cv.put("observacaoMutuo",formulario.getObservacaoMutuo());


        cv.put("dimensaoVegetacao", formulario.getDimensaoVegetacao());
        cv.put("distanciaBaixa", formulario.getDistaciaBaixa());
        cv.put("distanciaMedia", formulario.getDistanciaMedia());
        cv.put("estadoArvore", formulario.getEstadoArvore());
        cv.put("quedaArvore", formulario.getQuedaArvore());
        cv.put("localArvore", formulario.getLocalArvore());
        cv.put("observacaoVegetacao", formulario.getObservacaoVegetacao());

        try{
            escreve.insert(DbHelper.TABLE_ENVIADO,null, cv);
            Log.i("INFO","Formulario salvo com sucesso!");

        }catch (Exception e){
            Log.e("INFO","Erro ao salvar formulario" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Formulario formulario) {
        ContentValues cv = new ContentValues();
        cv.put("contadorIp", formulario.getContadorIp());
        cv.put("contadorAr", formulario.getContadorAr());
        cv.put("contadorAt", formulario.getContadorAt());
        cv.put("color", formulario.getColor());
        cv.put("color2", formulario.getColor2());
        cv.put("color3", formulario.getColor3());
        cv.put("color4", formulario.getColor4());
        cv.put("color5", formulario.getColor5());
        cv.put("color6", formulario.getColor6());
        cv.put("color7", formulario.getColor7());
        cv.put("color8", formulario.getColor8());
        cv.put("color9", formulario.getColor9());
        cv.put("color10", formulario.getColor10());
        cv.put("color11", formulario.getColor11());
        cv.put("color12", formulario.getColor12());
        cv.put("codigo", formulario.getCodigo());
        cv.put("caminhoImagem", formulario.getCaminhoImagem());
        cv.put("caminhoImagem2", formulario.getCaminhoImagem2());
        cv.put("caminhoImagem3", formulario.getCaminhoImagem3());
        cv.put("caminhoImagem4", formulario.getCaminhoImagem4());
        cv.put("caminhoImagem5", formulario.getCaminhoImagem5());
        cv.put("caminhoImagem6", formulario.getCaminhoImagem6());
        cv.put("caminhoImagem7", formulario.getCaminhoImagem7());
        cv.put("caminhoImagem8", formulario.getCaminhoImagem8());
        cv.put("caminhoImagem9", formulario.getCaminhoImagem9());
        cv.put("caminhoImagem10", formulario.getCaminhoImagem10());
        cv.put("caminhoImagem11", formulario.getCaminhoImagem11());
        cv.put("caminhoImagem12", formulario.getCaminhoImagem12());
        cv.put("urlImagem", formulario.getUrlImagem());
        cv.put("urlImagem2", formulario.getUrlImagem2());
        cv.put("urlImagem3", formulario.getUrlImagem3());
        cv.put("urlImagem4", formulario.getUrlImagem4());
        cv.put("urlImagem5", formulario.getUrlImagem5());
        cv.put("urlImagem6", formulario.getUrlImagem6());
        cv.put("urlImagem7", formulario.getUrlImagem7());
        cv.put("urlImagem8", formulario.getUrlImagem8());
        cv.put("urlImagem9", formulario.getUrlImagem9());
        cv.put("urlImagem10", formulario.getUrlImagem10());
        cv.put("urlImagem11", formulario.getUrlImagem11());
        cv.put("urlImagem12", formulario.getUrlImagem12());

        cv.put("data", formulario.getData());
        cv.put("endereco", formulario.getEndereco());
        cv.put("nome", formulario.getNome());
        cv.put("municipio", formulario.getMunicipio());
        cv.put("alturaCarga", formulario.getAlturaCarga());
        cv.put("latitude", formulario.getLatitude());
        cv.put("longitude", formulario.getLongitude());


        cv.put("tipoPoste",formulario.getTipoPoste());
        cv.put("normal", formulario.getNormal());
        cv.put("ferragemExposta", formulario.getFerragemExposta());
        cv.put("fletido", formulario.getFletido());
        cv.put("danificado", formulario.getDanificado());
        cv.put("fletido", formulario.getFletido());
        cv.put("abalroado", formulario.getAbalroado());
        cv.put("trincado", formulario.getTrincado());
        cv.put("observacaoFisicas", formulario.getObservacaoFisicas());


        cv.put("ip", formulario.getIp());
        cv.put("ipEstrutura", formulario.getIpEstrutura());
        cv.put("quantidadeLampada", formulario.getQuantidadeLampada());
        cv.put("tipoPot", formulario.getTipoPot());
        cv.put("potReator", formulario.getPotReator());
        cv.put("ipAtivacao", formulario.getIpAtivacao());
        cv.put("vinteEQuatro", formulario.getVinteEQuatro());
        cv.put("quantidade24H", formulario.getQuantidade24H());

        cv.put("ip2", formulario.getIp2());
        cv.put("ipEstrutura2", formulario.getIpEstrutura2());
        cv.put("quantidadeLampada2", formulario.getQuantidadeLampada2());
        cv.put("tipoPot2", formulario.getTipoPot2());
        cv.put("potReator2", formulario.getPotReator2());
        cv.put("ipAtivacao2", formulario.getIpAtivacao2());
        cv.put("vinteEQuatro2", formulario.getVinteEQuatro2());
        cv.put("quantidade24H2", formulario.getQuantidade24H2());

        cv.put("ip3", formulario.getIp3());
        cv.put("ipEstrutura3", formulario.getIpEstrutura3());
        cv.put("quantidadeLampada3", formulario.getQuantidadeLampada3());
        cv.put("tipoPot3", formulario.getTipoPot3());
        cv.put("potReator3", formulario.getPotReator3());
        cv.put("ipAtivacao3", formulario.getIpAtivacao3());
        cv.put("vinteEQuatro3", formulario.getVinteEQuatro3());
        cv.put("quantidade24H3", formulario.getQuantidade24H3());
        cv.put("observacaoIP", formulario.getObservacaoIP());

        cv.put("ativos", formulario.getAtivos());
        cv.put("chkTrafoTrifasico", formulario.getChkTrafoTrifasico());
        cv.put("chkTrafoMono", formulario.getChkTrafoMono());
        cv.put("trafoTrifasico", formulario.getTrafoTrifasico());
        cv.put("trafoMono", formulario.getTrafoMono());
        cv.put("religador", formulario.getReligador());
        cv.put("medicao", formulario.getMedicao());
        cv.put("chFusivel", formulario.getChFusivel());
        cv.put("chFaca", formulario.getChFaca());
        cv.put("banco", formulario.getBanco());
        cv.put("chFusivelReligador", formulario.getChFusivelReligador());
        cv.put("ramalSubt", formulario.getRamalSubt());
        cv.put("observacaoAtivos", formulario.getObservacaoAtivos());
        cv.put("outros", formulario.getOutros());


        cv.put("mutuo", formulario.getMutuo());
        cv.put("quantidadeOcupantes", formulario.getQuantidadeOcupantes());

        cv.put("quantidadeCabos",formulario.getQuantidadeCabos());
        cv.put("tipoCabo", formulario.getTipoCabo());
        cv.put("quantidadeCabosdois",formulario.getQuantidadeCabosdois());
        cv.put("tipoCabodois", formulario.getTipoCabodois());
        cv.put("nomeEmpresa", formulario.getNome());
        cv.put("finalidade",formulario.getFinalidade());
        cv.put("ceans", formulario.getCeans());
        cv.put("tar", formulario.getTar());
        cv.put("reservaTec", formulario.getReservaTec());
        cv.put("backbone", formulario.getBackbone());
        cv.put("placaIdent", formulario.getPlacaIdent());
        cv.put("descidaCabos", formulario.getDescidaCabos());
        cv.put("descricaoIrregularidade", formulario.getDescricaoIrregularidade());
        cv.put("observacaoMutuo",formulario.getObservacaoMutuo());


        cv.put("dimensaoVegetacao", formulario.getDimensaoVegetacao());
        cv.put("distanciaBaixa", formulario.getDistaciaBaixa());
        cv.put("distanciaMedia", formulario.getDistanciaMedia());
        cv.put("estadoArvore", formulario.getEstadoArvore());
        cv.put("quedaArvore", formulario.getQuedaArvore());
        cv.put("localArvore", formulario.getLocalArvore());
        cv.put("observacaoVegetacao", formulario.getObservacaoVegetacao());

        String[] args = {formulario.getId().toString()};

        try{
            escreve.update(DbHelper.TABLE_ENVIADO,cv,"id=?",args);
            Log.i("INFO","Formulario atualizado com sucesso!");

        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar formulario" + e.getMessage());
            return false;
        }
        return true;



    }

    @Override
    public boolean deletar(Formulario formulario) {

        try{
            String[] args = {formulario.getId().toString()};
            escreve.delete(DbHelper.TABLE_ENVIADO,"id=?",args);
            Log.i("INFO","Formulario atualizado com sucesso!");

        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar formulario" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Formulario> listar() {
        List<Formulario> formularios = new ArrayList<>();
        

        String sql = "SELECT * FROM " + DbHelper.TABLE_ENVIADO + " ;";
        Cursor c = le.rawQuery(sql,null);

        while (c.moveToNext()){
            Long id = c.getLong(c.getColumnIndex("id"));
            int contadorIp = c.getInt(c.getColumnIndex("contadorIp"));
            int contadorAr = c.getInt(c.getColumnIndex("contadorAr"));
            int contadorAt = c.getInt(c.getColumnIndex("contadorAt"));

            String codigo = c.getString(c.getColumnIndex("codigo"));

            String color = c.getString((c.getColumnIndex("color")));
            String color2 = c.getString((c.getColumnIndex("color2")));
            String color3 = c.getString((c.getColumnIndex("color3")));
            String color4 = c.getString((c.getColumnIndex("color4")));
            String color5 = c.getString((c.getColumnIndex("color5")));
            String color6 = c.getString((c.getColumnIndex("color6")));
            String color7 = c.getString((c.getColumnIndex("color7")));
            String color8 = c.getString((c.getColumnIndex("color8")));
            String color9 = c.getString((c.getColumnIndex("color9")));
            String color10 = c.getString((c.getColumnIndex("color10")));
            String color11 = c.getString((c.getColumnIndex("color11")));
            String color12 = c.getString((c.getColumnIndex("color12")));

            String caminhoImagem = c.getString((c.getColumnIndex("caminhoImagem")));
            String caminhoImagem2 = c.getString((c.getColumnIndex("caminhoImagem2")));
            String caminhoImagem3 = c.getString((c.getColumnIndex("caminhoImagem3")));
            String caminhoImagem4 = c.getString((c.getColumnIndex("caminhoImagem4")));
            String caminhoImagem5 = c.getString((c.getColumnIndex("caminhoImagem5")));
            String caminhoImagem6 = c.getString((c.getColumnIndex("caminhoImagem6")));
            String caminhoImagem7 = c.getString((c.getColumnIndex("caminhoImagem7")));
            String caminhoImagem8 = c.getString((c.getColumnIndex("caminhoImagem8")));
            String caminhoImagem9 = c.getString((c.getColumnIndex("caminhoImagem9")));
            String caminhoImagem10 = c.getString((c.getColumnIndex("caminhoImagem10")));
            String caminhoImagem11 = c.getString((c.getColumnIndex("caminhoImagem11")));
            String caminhoImagem12 = c.getString((c.getColumnIndex("caminhoImagem12")));

            String urlImagem = c.getString((c.getColumnIndex("urlImagem")));
            String urlImagem2 = c.getString((c.getColumnIndex("urlImagem2")));
            String urlImagem3 = c.getString((c.getColumnIndex("urlImagem3")));
            String urlImagem4 = c.getString((c.getColumnIndex("urlImagem4")));
            String urlImagem5 = c.getString((c.getColumnIndex("urlImagem5")));
            String urlImagem6 = c.getString((c.getColumnIndex("urlImagem6")));
            String urlImagem7 = c.getString((c.getColumnIndex("urlImagem7")));
            String urlImagem8 = c.getString((c.getColumnIndex("urlImagem8")));
            String urlImagem9 = c.getString((c.getColumnIndex("urlImagem9")));
            String urlImagem10 = c.getString((c.getColumnIndex("urlImagem10")));
            String urlImagem11 = c.getString((c.getColumnIndex("urlImagem11")));
            String urlImagem12 = c.getString((c.getColumnIndex("urlImagem12")));


            String endereco = c.getString((c.getColumnIndex("endereco")));
            String data = c.getString(c.getColumnIndex("data"));
            String municipio = c.getString(c.getColumnIndex("municipio"));
            String alturaCarga = c.getString(c.getColumnIndex("alturaCarga"));
            String latitude = c.getString(c.getColumnIndex("latitude"));
            String longitude = c.getString(c.getColumnIndex("longitude"));


            String tipoPoste = c.getString(c.getColumnIndex("tipoPoste"));
            String normal = c.getString(c.getColumnIndex("normal"));
            String ferragemExposta = c.getString(c.getColumnIndex("ferragemExposta"));
            String fletido = c.getString(c.getColumnIndex("fletido"));
            String danificado = c.getString(c.getColumnIndex("danificado"));
            String abalroado = c.getString(c.getColumnIndex("abalroado"));
            String trincado = c.getString(c.getColumnIndex("trincado"));
            String observacaoFisicas = c.getString(c.getColumnIndex("observacaoFisicas"));


            String ip = c.getString(c.getColumnIndex("ip"));
            String ipEstrutura = c.getString(c.getColumnIndex("ipEstrutura"));
            String quantidadeLampada = c.getString(c.getColumnIndex("quantidadeLampada"));
            String tipoPot = c.getString(c.getColumnIndex("tipoPot"));
            String potReator = c.getString(c.getColumnIndex("potReator"));
            String ipAtivacao = c.getString(c.getColumnIndex("ipAtivacao"));
            String vinteEQuatro = c.getString(c.getColumnIndex("vinteEQuatro"));
            String quantidade24H = c.getString(c.getColumnIndex("quantidade24H"));

            String ip2 = c.getString(c.getColumnIndex("ip2"));
            String ipEstrutura2 = c.getString(c.getColumnIndex("ipEstrutura2"));
            String quantidadeLampada2 = c.getString(c.getColumnIndex("quantidadeLampada2"));
            String tipoPot2 = c.getString(c.getColumnIndex("tipoPot2"));
            String potReator2 = c.getString(c.getColumnIndex("potReator2"));
            String ipAtivacao2 = c.getString(c.getColumnIndex("ipAtivacao2"));
            String vinteEQuatro2 = c.getString(c.getColumnIndex("vinteEQuatro2"));
            String quantidade24H2 = c.getString(c.getColumnIndex("quantidade24H2"));

            String ip3 = c.getString(c.getColumnIndex("ip3"));
            String ipEstrutura3 = c.getString(c.getColumnIndex("ipEstrutura3"));
            String quantidadeLampada3 = c.getString(c.getColumnIndex("quantidadeLampada3"));
            String tipoPot3 = c.getString(c.getColumnIndex("tipoPot3"));
            String potReator3 = c.getString(c.getColumnIndex("potReator3"));
            String ipAtivacao3 = c.getString(c.getColumnIndex("ipAtivacao3"));
            String vinteEQuatro3 = c.getString(c.getColumnIndex("vinteEQuatro3"));
            String quantidade24H3 = c.getString(c.getColumnIndex("quantidade24H3"));
            String observacaoIP = c.getString(c.getColumnIndex("observacaoIP"));

            String ativos = c.getString(c.getColumnIndex("ativos"));
            String chkTrafoTrifasico = c.getString(c.getColumnIndex("chkTrafoTrifasico"));
            String chkTrafoMono = c.getString(c.getColumnIndex("chkTrafoMono"));
            String trafoTrifasico = c.getString(c.getColumnIndex("trafoTrifasico"));
            String trafoMono = c.getString(c.getColumnIndex("trafoMono"));
            String religador = c.getString(c.getColumnIndex("religador"));
            String medicao = c.getString(c.getColumnIndex("medicao"));
            String chFusivel = c.getString(c.getColumnIndex("chFusivel"));
            String chFaca = c.getString(c.getColumnIndex("chFaca"));
            String banco = c.getString(c.getColumnIndex("banco"));
            String chFusivelReligador = c.getString(c.getColumnIndex("chFusivelReligador"));
            String ramalSubt = c.getString(c.getColumnIndex("ramalSubt"));
            String observacaoAtivos = c.getString(c.getColumnIndex("observacaoAtivos"));
            String outros = c.getString(c.getColumnIndex("outros"));



            String mutuo = c.getString(c.getColumnIndex("mutuo"));
            String quantidadeOcupantes = c.getString(c.getColumnIndex("quantidadeOcupantes"));

            String quantidadeCabos = c.getString(c.getColumnIndex("quantidadeCabos"));
            String tipoCabo = c.getString(c.getColumnIndex("tipoCabo"));
            String quantidadeCabosdois = c.getString(c.getColumnIndex("quantidadeCabosdois"));
            String tipoCabodois = c.getString(c.getColumnIndex("tipoCabodois"));
            String nomeEmpresa = c.getString(c.getColumnIndex("nomeEmpresa"));
            String finalidade = c.getString(c.getColumnIndex("finalidade"));
            String ceans = c.getString(c.getColumnIndex("ceans"));
            String tar = c.getString(c.getColumnIndex("tar"));
            String reservaTec = c.getString(c.getColumnIndex("reservaTec"));
            String backbone = c.getString(c.getColumnIndex("backbone"));
            String placaIdent = c.getString(c.getColumnIndex("placaIdent"));
            String descidaCabos = c.getString(c.getColumnIndex("descidaCabos"));
            String descricaoIrregularidade = c.getString(c.getColumnIndex("descricaoIrregularidade"));
            String observacaoMutuo = c.getString(c.getColumnIndex("observacaoMutuo"));


            String dimensaoVegetacao = c.getString(c.getColumnIndex("dimensaoVegetacao"));
            String distanciaBaixa = c.getString(c.getColumnIndex("distanciaBaixa"));
            String distanciaMedia = c.getString(c.getColumnIndex("distanciaMedia"));
            String estadoArvore = c.getString(c.getColumnIndex("estadoArvore"));
            String quedaArvore = c.getString(c.getColumnIndex("quedaArvore"));
            String localArvore = c.getString(c.getColumnIndex("localArvore"));
            String observacaoVegetacao = c.getString(c.getColumnIndex("observacaoVegetacao"));

            Formulario formulario = new Formulario();

            formulario.setContadorIp(contadorIp);
            formulario.setContadorAr(contadorAr);
            formulario.setContadorAt(contadorAt);
            formulario.setColor(color);
            formulario.setColor2(color2);
            formulario.setColor3(color3);
            formulario.setColor4(color4);
            formulario.setColor5(color5);
            formulario.setColor6(color6);
            formulario.setColor7(color7);
            formulario.setColor8(color8);
            formulario.setColor9(color9);
            formulario.setColor10(color10);
            formulario.setColor11(color11);
            formulario.setColor12(color12);

            formulario.setCodigo(codigo);
            formulario.setCaminhoImagem(caminhoImagem);
            formulario.setCaminhoImagem2(caminhoImagem2);
            formulario.setCaminhoImagem3(caminhoImagem3);
            formulario.setCaminhoImagem4(caminhoImagem4);
            formulario.setCaminhoImagem5(caminhoImagem5);
            formulario.setCaminhoImagem6(caminhoImagem6);
            formulario.setCaminhoImagem7(caminhoImagem7);
            formulario.setCaminhoImagem8(caminhoImagem8);
            formulario.setCaminhoImagem9(caminhoImagem9);
            formulario.setCaminhoImagem10(caminhoImagem10);
            formulario.setCaminhoImagem11(caminhoImagem11);
            formulario.setCaminhoImagem12(caminhoImagem12);

            formulario.setUrlImagem(urlImagem);
            formulario.setUrlImagem2(urlImagem2);
            formulario.setUrlImagem3(urlImagem3);
            formulario.setUrlImagem4(urlImagem4);
            formulario.setUrlImagem5(urlImagem5);
            formulario.setUrlImagem6(urlImagem6);
            formulario.setUrlImagem7(urlImagem7);
            formulario.setUrlImagem8(urlImagem8);
            formulario.setUrlImagem9(urlImagem9);
            formulario.setUrlImagem10(urlImagem10);
            formulario.setUrlImagem11(urlImagem11);
            formulario.setUrlImagem12(urlImagem12);

            formulario.setId(id);
            formulario.setEndereco(endereco);
            formulario.setData(data);
            formulario.setMunicipio(municipio);
            formulario.setAlturaCarga(alturaCarga);
            formulario.setLatitude(latitude);
            formulario.setLongitude(longitude);


            formulario.setTipoPoste(tipoPoste);
            formulario.setNormal(normal);
            formulario.setFerragemExposta(ferragemExposta);
            formulario.setFletido(fletido);
            formulario.setDanificado(danificado);
            formulario.setAbalroado(abalroado);
            formulario.setTrincado(trincado);
            formulario.setObservacaoFisicas(observacaoFisicas);


            formulario.setIp(ip);
            formulario.setIpEstrutura(ipEstrutura);
            formulario.setQuantidadeLampada(quantidadeLampada);
            formulario.setTipoPot(tipoPot);
            formulario.setPotReator(potReator);
            formulario.setIpAtivacao(ipAtivacao);
            formulario.setVinteEQuatro(vinteEQuatro);
            formulario.setQuantidade24H(quantidade24H);

            formulario.setIp2(ip2);
            formulario.setIpEstrutura2(ipEstrutura2);
            formulario.setQuantidadeLampada2(quantidadeLampada2);
            formulario.setTipoPot2(tipoPot2);
            formulario.setPotReator2(potReator2);
            formulario.setIpAtivacao2(ipAtivacao2);
            formulario.setVinteEQuatro2(vinteEQuatro2);
            formulario.setQuantidade24H2(quantidade24H2);

            formulario.setIp3(ip3);
            formulario.setIpEstrutura3(ipEstrutura3);
            formulario.setQuantidadeLampada3(quantidadeLampada3);
            formulario.setTipoPot3(tipoPot3);
            formulario.setPotReator3(potReator3);
            formulario.setIpAtivacao3(ipAtivacao3);
            formulario.setVinteEQuatro3(vinteEQuatro3);
            formulario.setQuantidade24H3(quantidade24H3);
            formulario.setObservacaoIP(observacaoIP);


            formulario.setAtivos(ativos);
            formulario.setChkTrafoTrifasico(chkTrafoTrifasico);
            formulario.setChkTrafoMono(chkTrafoMono);
            formulario.setTrafoTrifasico(trafoTrifasico);
            formulario.setTrafoMono(trafoMono);
            formulario.setReligador(religador);
            formulario.setMedicao(medicao);
            formulario.setChFusivel(chFusivel);
            formulario.setChFaca(chFaca);
            formulario.setBanco(banco);
            formulario.setChFusivelReligador(chFusivelReligador);
            formulario.setRamalSubt(ramalSubt);
            formulario.setObservacaoAtivos(observacaoAtivos);
            formulario.setOutros(outros);


            formulario.setMutuo(mutuo);
            formulario.setQuantidadeOcupantes(quantidadeOcupantes);

            formulario.setQuantidadeCabos(quantidadeCabos);
            formulario.setTipoCabo(tipoCabo);
            formulario.setQuantidadeCabosdois(quantidadeCabosdois);
            formulario.setTipoCabodois(tipoCabodois);
            formulario.setNome(nomeEmpresa);
            formulario.setFinalidade(finalidade);
            formulario.setCeans(ceans);
            formulario.setTar(tar);
            formulario.setReservaTec(reservaTec);
            formulario.setBackbone(backbone);
            formulario.setDescidaCabos(descidaCabos);
            formulario.setPlacaIdent(placaIdent);
            formulario.setDescricaoIrregularidade(descricaoIrregularidade);
            formulario.setObservacaoMutuo(observacaoMutuo);

            formulario.setDimensaoVegetacao(dimensaoVegetacao);
            formulario.setDistaciaBaixa(distanciaBaixa);
            formulario.setDistanciaMedia(distanciaMedia);
            formulario.setEstadoArvore(estadoArvore);
            formulario.setQuedaArvore(quedaArvore);
            formulario.setLocalArvore(localArvore);
            formulario.setObservacaoVegetacao(observacaoVegetacao);

            formularios.add(formulario);
        }

        return formularios;
    }
}
