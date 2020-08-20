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
        cv.put("color", formulario.getColor());
        cv.put("color2", formulario.getColor2());
        cv.put("color3", formulario.getColor3());

        cv.put("codigo",formulario.getCodigo());
        cv.put("caminhoImagem", formulario.getCaminhoImagem());
        cv.put("caminhoImagem2", formulario.getCaminhoImagem2());
        cv.put("caminhoImagem3", formulario.getCaminhoImagem3());
        cv.put("urlImagem", formulario.getUrlImagem());
        cv.put("urlImagem2", formulario.getUrlImagem2());
        cv.put("urlImagem3", formulario.getUrlImagem3());

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

        cv.put("quantidadeCabos2",formulario.getQuantidadeCabos2());
        cv.put("tipoCabo2", formulario.getTipoCabo2());
        cv.put("quantidadeCabos2dois",formulario.getQuantidadeCabos2dois());
        cv.put("tipoCabo2dois", formulario.getTipoCabo2dois());
        cv.put("nomeEmpresa2", formulario.getNome2());
        cv.put("finalidade2",formulario.getFinalidade2());
        cv.put("ceans2", formulario.getCeans2());
        cv.put("tar2", formulario.getTar2());
        cv.put("reservaTec2", formulario.getReservaTec2());
        cv.put("backbone2", formulario.getBackbone2());
        cv.put("placaIdent2", formulario.getPlacaIdent2());
        cv.put("descidaCabos2", formulario.getDescidaCabos2());
        cv.put("descricaoIrregularidade2", formulario.getDescricaoIrregularidade2());
        cv.put("observacaoMutuo2",formulario.getObservacaoMutuo2());

        cv.put("quantidadeCabos3",formulario.getQuantidadeCabos3());
        cv.put("tipoCabo3", formulario.getTipoCabo3());
        cv.put("nomeEmpresa3", formulario.getNome3());
        cv.put("finalidade3",formulario.getFinalidade3());
        cv.put("ceans3", formulario.getCeans3());
        cv.put("tar3", formulario.getTar3());
        cv.put("reservaTec3", formulario.getReservaTec3());
        cv.put("backbone3", formulario.getBackbone3());
        cv.put("placaIdent3", formulario.getPlacaIdent3());
        cv.put("descidaCabos3", formulario.getDescidaCabos3());
        cv.put("descricaoIrregularidade3", formulario.getDescricaoIrregularidade3());
        cv.put("observacaoMutuo3",formulario.getObservacaoMutuo3());

        cv.put("quantidadeCabos4",formulario.getQuantidadeCabos4());
        cv.put("tipoCabo4", formulario.getTipoCabo4());
        cv.put("nomeEmpresa4", formulario.getNome4());
        cv.put("finalidade4",formulario.getFinalidade4());
        cv.put("ceans4", formulario.getCeans4());
        cv.put("tar4", formulario.getTar4());
        cv.put("reservaTec4", formulario.getReservaTec4());
        cv.put("backbone4", formulario.getBackbone4());
        cv.put("placaIdent4", formulario.getPlacaIdent4());
        cv.put("descidaCabos4", formulario.getDescidaCabos4());
        cv.put("descricaoIrregularidade4", formulario.getDescricaoIrregularidade4());
        cv.put("observacaoMutuo4",formulario.getObservacaoMutuo4());

        cv.put("quantidadeCabos5",formulario.getQuantidadeCabos5());
        cv.put("tipoCabo5", formulario.getTipoCabo5());
        cv.put("nomeEmpresa5", formulario.getNome5());
        cv.put("finalidade5",formulario.getFinalidade5());
        cv.put("ceans5", formulario.getCeans5());
        cv.put("tar5", formulario.getTar5());
        cv.put("reservaTec5", formulario.getReservaTec());
        cv.put("backbone5", formulario.getBackbone5());
        cv.put("placaIdent5", formulario.getPlacaIdent5());
        cv.put("descidaCabos5", formulario.getDescidaCabos5());
        cv.put("descricaoIrregularidade5", formulario.getDescricaoIrregularidade5());
        cv.put("observacaoMutuo5",formulario.getObservacaoMutuo5());


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
        cv.put("color", formulario.getColor());
        cv.put("color2", formulario.getColor2());
        cv.put("color3", formulario.getColor3());

        cv.put("codigo",formulario.getCodigo());
        cv.put("caminhoImagem", formulario.getCaminhoImagem());
        cv.put("caminhoImagem2", formulario.getCaminhoImagem2());
        cv.put("caminhoImagem3", formulario.getCaminhoImagem3());
        cv.put("urlImagem", formulario.getUrlImagem());
        cv.put("urlImagem2", formulario.getUrlImagem2());
        cv.put("urlImagem3", formulario.getUrlImagem3());

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

        cv.put("quantidadeCabos2",formulario.getQuantidadeCabos2());
        cv.put("tipoCabo2", formulario.getTipoCabo2());
        cv.put("quantidadeCabos2dois",formulario.getQuantidadeCabos2dois());
        cv.put("tipoCabo2dois", formulario.getTipoCabo2dois());
        cv.put("nomeEmpresa2", formulario.getNome2());
        cv.put("finalidade2",formulario.getFinalidade2());
        cv.put("ceans2", formulario.getCeans2());
        cv.put("tar2", formulario.getTar2());
        cv.put("reservaTec2", formulario.getReservaTec2());
        cv.put("backbone2", formulario.getBackbone2());
        cv.put("placaIdent2", formulario.getPlacaIdent2());
        cv.put("descidaCabos2", formulario.getDescidaCabos2());
        cv.put("descricaoIrregularidade2", formulario.getDescricaoIrregularidade2());
        cv.put("observacaoMutuo2",formulario.getObservacaoMutuo2());

        cv.put("quantidadeCabos3",formulario.getQuantidadeCabos3());
        cv.put("tipoCabo3", formulario.getTipoCabo3());
        cv.put("nomeEmpresa3", formulario.getNome3());
        cv.put("finalidade3",formulario.getFinalidade3());
        cv.put("ceans3", formulario.getCeans3());
        cv.put("tar3", formulario.getTar3());
        cv.put("reservaTec3", formulario.getReservaTec3());
        cv.put("backbone3", formulario.getBackbone3());
        cv.put("placaIdent3", formulario.getPlacaIdent3());
        cv.put("descidaCabos3", formulario.getDescidaCabos3());
        cv.put("descricaoIrregularidade3", formulario.getDescricaoIrregularidade3());
        cv.put("observacaoMutuo3",formulario.getObservacaoMutuo3());

        cv.put("quantidadeCabos4",formulario.getQuantidadeCabos4());
        cv.put("tipoCabo4", formulario.getTipoCabo4());
        cv.put("nomeEmpresa4", formulario.getNome4());
        cv.put("finalidade4",formulario.getFinalidade4());
        cv.put("ceans4", formulario.getCeans4());
        cv.put("tar4", formulario.getTar4());
        cv.put("reservaTec4", formulario.getReservaTec4());
        cv.put("backbone4", formulario.getBackbone4());
        cv.put("placaIdent4", formulario.getPlacaIdent4());
        cv.put("descidaCabos4", formulario.getDescidaCabos4());
        cv.put("descricaoIrregularidade4", formulario.getDescricaoIrregularidade4());
        cv.put("observacaoMutuo4",formulario.getObservacaoMutuo4());

        cv.put("quantidadeCabos5",formulario.getQuantidadeCabos5());
        cv.put("tipoCabo5", formulario.getTipoCabo5());
        cv.put("nomeEmpresa5", formulario.getNome5());
        cv.put("finalidade5",formulario.getFinalidade5());
        cv.put("ceans5", formulario.getCeans5());
        cv.put("tar5", formulario.getTar5());
        cv.put("reservaTec5", formulario.getReservaTec());
        cv.put("backbone5", formulario.getBackbone5());
        cv.put("placaIdent5", formulario.getPlacaIdent5());
        cv.put("descidaCabos5", formulario.getDescidaCabos5());
        cv.put("descricaoIrregularidade5", formulario.getDescricaoIrregularidade5());
        cv.put("observacaoMutuo5",formulario.getObservacaoMutuo5());


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

            String codigo = c.getString(c.getColumnIndex("codigo"));
            String color = c.getString((c.getColumnIndex("color")));
            String color2 = c.getString((c.getColumnIndex("color2")));
            String color3 = c.getString((c.getColumnIndex("color3")));

            String caminhoImagem = c.getString((c.getColumnIndex("caminhoImagem")));
            String caminhoImagem2 = c.getString((c.getColumnIndex("caminhoImagem2")));
            String caminhoImagem3 = c.getString((c.getColumnIndex("caminhoImagem3")));

            String urlImagem = c.getString((c.getColumnIndex("urlImagem")));
            String urlImagem2 = c.getString((c.getColumnIndex("urlImagem2")));
            String urlImagem3 = c.getString((c.getColumnIndex("urlImagem3")));

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

            String quantidadeCabos2 = c.getString(c.getColumnIndex("quantidadeCabos2"));
            String tipoCabo2 = c.getString(c.getColumnIndex("tipoCabo2"));
            String quantidadeCabos2dois = c.getString(c.getColumnIndex("quantidadeCabos2dois"));
            String tipoCabo2dois = c.getString(c.getColumnIndex("tipoCabo2dois"));
            String nomeEmpresa2 = c.getString(c.getColumnIndex("nomeEmpresa2"));
            String finalidade2 = c.getString(c.getColumnIndex("finalidade2"));
            String ceans2 = c.getString(c.getColumnIndex("ceans2"));
            String tar2 = c.getString(c.getColumnIndex("tar2"));
            String reservaTec2 = c.getString(c.getColumnIndex("reservaTec2"));
            String backbone2 = c.getString(c.getColumnIndex("backbone2"));
            String placaIdent2 = c.getString(c.getColumnIndex("placaIdent2"));
            String descidaCabos2 = c.getString(c.getColumnIndex("descidaCabos2"));
            String descricaoIrregularidade2 = c.getString(c.getColumnIndex("descricaoIrregularidade2"));
            String observacaoMutuo2 = c.getString(c.getColumnIndex("observacaoMutuo2"));

            String quantidadeCabos3 = c.getString(c.getColumnIndex("quantidadeCabos3"));
            String tipoCabo3 = c.getString(c.getColumnIndex("tipoCabo3"));
            String nomeEmpresa3 = c.getString(c.getColumnIndex("nomeEmpresa3"));
            String finalidade3 = c.getString(c.getColumnIndex("finalidade3"));
            String ceans3 = c.getString(c.getColumnIndex("ceans3"));
            String tar3 = c.getString(c.getColumnIndex("tar3"));
            String reservaTec3 = c.getString(c.getColumnIndex("reservaTec3"));
            String backbone3 = c.getString(c.getColumnIndex("backbone3"));
            String placaIdent3 = c.getString(c.getColumnIndex("placaIdent3"));
            String descidaCabos3 = c.getString(c.getColumnIndex("descidaCabos3"));
            String descricaoIrregularidade3 = c.getString(c.getColumnIndex("descricaoIrregularidade3"));
            String observacaoMutuo3 = c.getString(c.getColumnIndex("observacaoMutuo3"));

            String quantidadeCabos4 = c.getString(c.getColumnIndex("quantidadeCabos4"));
            String tipoCabo4 = c.getString(c.getColumnIndex("tipoCabo4"));
            String nomeEmpresa4 = c.getString(c.getColumnIndex("nomeEmpresa4"));
            String finalidade4 = c.getString(c.getColumnIndex("finalidade4"));
            String ceans4 = c.getString(c.getColumnIndex("ceans4"));
            String tar4 = c.getString(c.getColumnIndex("tar4"));
            String reservaTec4 = c.getString(c.getColumnIndex("reservaTec4"));
            String backbone4 = c.getString(c.getColumnIndex("backbone4"));
            String placaIdent4 = c.getString(c.getColumnIndex("placaIdent4"));
            String descidaCabos4 = c.getString(c.getColumnIndex("descidaCabos4"));
            String descricaoIrregularidade4 = c.getString(c.getColumnIndex("descricaoIrregularidade4"));
            String observacaoMutuo4 = c.getString(c.getColumnIndex("observacaoMutuo4"));

            String quantidadeCabos5 = c.getString(c.getColumnIndex("quantidadeCabos5"));
            String tipoCabo5 = c.getString(c.getColumnIndex("tipoCabo5"));
            String nomeEmpresa5 = c.getString(c.getColumnIndex("nomeEmpresa5"));
            String finalidade5 = c.getString(c.getColumnIndex("finalidade5"));
            String ceans5 = c.getString(c.getColumnIndex("ceans5"));
            String tar5 = c.getString(c.getColumnIndex("tar5"));
            String reservaTec5 = c.getString(c.getColumnIndex("reservaTec5"));
            String backbone5 = c.getString(c.getColumnIndex("backbone5"));
            String placaIdent5 = c.getString(c.getColumnIndex("placaIdent5"));
            String descidaCabos5 = c.getString(c.getColumnIndex("descidaCabos5"));
            String descricaoIrregularidade5 = c.getString(c.getColumnIndex("descricaoIrregularidade5"));
            String observacaoMutuo5 = c.getString(c.getColumnIndex("observacaoMutuo5"));


            String dimensaoVegetacao = c.getString(c.getColumnIndex("dimensaoVegetacao"));
            String distanciaBaixa = c.getString(c.getColumnIndex("distanciaBaixa"));
            String distanciaMedia = c.getString(c.getColumnIndex("distanciaMedia"));
            String estadoArvore = c.getString(c.getColumnIndex("estadoArvore"));
            String quedaArvore = c.getString(c.getColumnIndex("quedaArvore"));
            String localArvore = c.getString(c.getColumnIndex("localArvore"));
            String observacaoVegetacao = c.getString(c.getColumnIndex("observacaoVegetacao"));

            Formulario formulario = new Formulario();
            formulario.setColor(color);
            formulario.setColor2(color2);
            formulario.setColor3(color3);

            formulario.setCodigo(codigo);
            formulario.setCaminhoImagem(caminhoImagem);
            formulario.setCaminhoImagem2(caminhoImagem2);
            formulario.setCaminhoImagem3(caminhoImagem3);
            formulario.setUrlImagem(urlImagem);
            formulario.setUrlImagem2(urlImagem2);
            formulario.setUrlImagem3(urlImagem3);

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

            formulario.setQuantidadeCabos2(quantidadeCabos2);
            formulario.setTipoCabo2(tipoCabo2);
            formulario.setQuantidadeCabos2dois(quantidadeCabos2dois);
            formulario.setTipoCabo2dois(tipoCabo2dois);
            formulario.setNome2(nomeEmpresa2);
            formulario.setFinalidade2(finalidade2);
            formulario.setCeans2(ceans2);
            formulario.setTar2(tar2);
            formulario.setReservaTec2(reservaTec2);
            formulario.setBackbone2(backbone2);
            formulario.setDescidaCabos2(descidaCabos2);
            formulario.setPlacaIdent2(placaIdent2);
            formulario.setDescricaoIrregularidade2(descricaoIrregularidade2);
            formulario.setObservacaoMutuo2(observacaoMutuo2);

            formulario.setQuantidadeCabos3(quantidadeCabos3);
            formulario.setTipoCabo3(tipoCabo3);
            formulario.setNome3(nomeEmpresa3);
            formulario.setFinalidade3(finalidade3);
            formulario.setCeans3(ceans3);
            formulario.setTar3(tar3);
            formulario.setReservaTec3(reservaTec3);
            formulario.setBackbone3(backbone3);
            formulario.setDescidaCabos3(descidaCabos3);
            formulario.setPlacaIdent3(placaIdent3);
            formulario.setDescricaoIrregularidade3(descricaoIrregularidade3);
            formulario.setObservacaoMutuo3(observacaoMutuo3);

            formulario.setQuantidadeCabos4(quantidadeCabos4);
            formulario.setTipoCabo4(tipoCabo4);
            formulario.setNome4(nomeEmpresa4);
            formulario.setFinalidade4(finalidade4);
            formulario.setCeans4(ceans4);
            formulario.setTar4(tar4);
            formulario.setReservaTec4(reservaTec4);
            formulario.setBackbone4(backbone4);
            formulario.setDescidaCabos4(descidaCabos4);
            formulario.setPlacaIdent4(placaIdent4);
            formulario.setDescricaoIrregularidade4(descricaoIrregularidade4);
            formulario.setObservacaoMutuo4(observacaoMutuo4);

            formulario.setQuantidadeCabos5(quantidadeCabos5);
            formulario.setTipoCabo5(tipoCabo5);
            formulario.setNome5(nomeEmpresa5);
            formulario.setFinalidade5(finalidade5);
            formulario.setCeans5(ceans5);
            formulario.setTar5(tar5);
            formulario.setReservaTec5(reservaTec5);
            formulario.setBackbone5(backbone5);
            formulario.setDescidaCabos5(descidaCabos5);
            formulario.setPlacaIdent5(placaIdent5);
            formulario.setDescricaoIrregularidade5(descricaoIrregularidade5);
            formulario.setObservacaoMutuo5(observacaoMutuo5);

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
