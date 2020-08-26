package com.example.satelprojetos.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Formulario implements Serializable {
    private Long id;
    private String codigo;
    @Exclude
    private String color;
    @Exclude
    private String color2;
    @Exclude
    private String color3;
    @Exclude
    private String color4;
    @Exclude
    private String color7;
    @Exclude
    private String color8;
    @Exclude
    private String color9;
    @Exclude
    private String color10;
    @Exclude
    private String color11;
    @Exclude
    private String color12;
    @Exclude
    private String caminhoImagem;
    @Exclude
    private String caminhoImagem2;
    @Exclude
    private String caminhoImagem3;
    @Exclude
    private String caminhoImagem4;
    @Exclude
    private String caminhoImagem7;
    @Exclude
    private String caminhoImagem8;
    @Exclude
    private String caminhoImagem9;
    @Exclude
    private String caminhoImagem10;
    @Exclude
    private String caminhoImagem11;
    @Exclude
    private String caminhoImagem12;

    private String urlImagem;
    private String urlImagem2;
    private String urlImagem3;
    private String urlImagem4;
    private String urlImagem7;
    private String urlImagem8;
    private String urlImagem9;
    private String urlImagem10;
    private String urlImagem11;
    private String urlImagem12;
    @Exclude
    private int contadorAt;
    @Exclude
    private int contadorIp;
    @Exclude
    private int contadorAr;

    private String data;
    private String endereco;
    private String municipio;
    private String alturaCarga;
    private String latitude;
    private String longitude;
    private String tipoPoste;
    private String normal;
    private String ferragemExposta;
    private String fletido;
    private String danificado;
    private String abalroado;
    private String trincado;
    private String ramalSubt;
    private String observacaoFisicas;
    private String ativos;
    private String chkTrafoTrifasico;
    private String chkTrafoMono;
    private String trafoTrifasico;
    private String trafoMono;
    private String religador;
    private String medicao;
    private String chFusivel;
    private String chFaca;
    private String banco;
    private String chFusivelReligador;
    private String outros;
    private String observacaoAtivos;
    private String ip;
    private String ipEstrutura;
    private String quantidadeLampada;
    private String tipoPot;
    private String potReator;
    private String ipAtivacao;
    private String vinteEQuatro;
    private String quantidade24H;
    private String observacaoIP;
    private String mutuo;
    private String quantidadeOcupantes;
    private String quantidadeCabos;
    private String tipoCabo;
    private String quantidadeCabosdois;
    private String tipoCabodois;
    private String nome;
    private String finalidade;
    private String ceans;
    private String tar;
    private String reservaTec;
    private String backbone;
    private String placaIdent;
    private String descidaCabos;
    private String descricaoIrregularidade;
    private String observacaoMutuo;

    private String vegetacao;
    private String dimensaoVegetacao;
    private String distaciaBaixa;
    private String distanciaMedia;
    private String estadoArvore;
    private String quedaArvore;
    private String localArvore;
    private String observacaoVegetacao;

    public String getVegetacao() {
        return vegetacao;
    }

    public void setVegetacao(String vegetacao) {
        this.vegetacao = vegetacao;
    }

    public int getContadorAt() {
        return contadorAt;
    }

    public void setContadorAt(int contadorAt) {
        this.contadorAt = contadorAt;
    }

    public int getContadorIp() {
        return contadorIp;
    }

    public void setContadorIp(int contadorIp) {
        this.contadorIp = contadorIp;
    }

    public int getContadorAr() {
        return contadorAr;
    }

    public void setContadorAr(int contadorAr) {
        this.contadorAr = contadorAr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getColor4() {
        return color4;
    }

    public void setColor4(String color4) {
        this.color4 = color4;
    }

    public String getColor7() {
        return color7;
    }

    public void setColor7(String color7) {
        this.color7 = color7;
    }

    public String getColor8() {
        return color8;
    }

    public void setColor8(String color8) {
        this.color8 = color8;
    }

    public String getColor9() {
        return color9;
    }

    public void setColor9(String color9) {
        this.color9 = color9;
    }

    public String getColor10() {
        return color10;
    }

    public void setColor10(String color10) {
        this.color10 = color10;
    }

    public String getColor11() {
        return color11;
    }

    public void setColor11(String color11) {
        this.color11 = color11;
    }

    public String getColor12() {
        return color12;
    }

    public void setColor12(String color12) {
        this.color12 = color12;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getCaminhoImagem2() {
        return caminhoImagem2;
    }

    public void setCaminhoImagem2(String caminhoImagem2) {
        this.caminhoImagem2 = caminhoImagem2;
    }

    public String getCaminhoImagem3() {
        return caminhoImagem3;
    }

    public void setCaminhoImagem3(String caminhoImagem3) {
        this.caminhoImagem3 = caminhoImagem3;
    }

    public String getCaminhoImagem4() {
        return caminhoImagem4;
    }

    public void setCaminhoImagem4(String caminhoImagem4) {
        this.caminhoImagem4 = caminhoImagem4;
    }

    public String getCaminhoImagem7() {
        return caminhoImagem7;
    }

    public void setCaminhoImagem7(String caminhoImagem7) {
        this.caminhoImagem7 = caminhoImagem7;
    }

    public String getCaminhoImagem8() {
        return caminhoImagem8;
    }

    public void setCaminhoImagem8(String caminhoImagem8) {
        this.caminhoImagem8 = caminhoImagem8;
    }

    public String getCaminhoImagem9() {
        return caminhoImagem9;
    }

    public void setCaminhoImagem9(String caminhoImagem9) {
        this.caminhoImagem9 = caminhoImagem9;
    }

    public String getCaminhoImagem10() {
        return caminhoImagem10;
    }

    public void setCaminhoImagem10(String caminhoImagem10) {
        this.caminhoImagem10 = caminhoImagem10;
    }

    public String getCaminhoImagem11() {
        return caminhoImagem11;
    }

    public void setCaminhoImagem11(String caminhoImagem11) {
        this.caminhoImagem11 = caminhoImagem11;
    }

    public String getCaminhoImagem12() {
        return caminhoImagem12;
    }

    public void setCaminhoImagem12(String caminhoImagem12) {
        this.caminhoImagem12 = caminhoImagem12;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getUrlImagem2() {
        return urlImagem2;
    }

    public void setUrlImagem2(String urlImagem2) {
        this.urlImagem2 = urlImagem2;
    }

    public String getUrlImagem3() {
        return urlImagem3;
    }

    public void setUrlImagem3(String urlImagem3) {
        this.urlImagem3 = urlImagem3;
    }

    public String getUrlImagem4() {
        return urlImagem4;
    }

    public void setUrlImagem4(String urlImagem4) {
        this.urlImagem4 = urlImagem4;
    }

    public String getUrlImagem7() {
        return urlImagem7;
    }

    public void setUrlImagem7(String urlImagem7) {
        this.urlImagem7 = urlImagem7;
    }

    public String getUrlImagem8() {
        return urlImagem8;
    }

    public void setUrlImagem8(String urlImagem8) {
        this.urlImagem8 = urlImagem8;
    }

    public String getUrlImagem9() {
        return urlImagem9;
    }

    public void setUrlImagem9(String urlImagem9) {
        this.urlImagem9 = urlImagem9;
    }

    public String getUrlImagem10() {
        return urlImagem10;
    }

    public void setUrlImagem10(String urlImagem10) {
        this.urlImagem10 = urlImagem10;
    }

    public String getUrlImagem11() {
        return urlImagem11;
    }

    public void setUrlImagem11(String urlImagem11) {
        this.urlImagem11 = urlImagem11;
    }

    public String getUrlImagem12() {
        return urlImagem12;
    }

    public void setUrlImagem12(String urlImagem12) {
        this.urlImagem12 = urlImagem12;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getAlturaCarga() {
        return alturaCarga;
    }

    public void setAlturaCarga(String alturaCarga) {
        this.alturaCarga = alturaCarga;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTipoPoste() {
        return tipoPoste;
    }

    public void setTipoPoste(String tipoPoste) {
        this.tipoPoste = tipoPoste;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getFerragemExposta() {
        return ferragemExposta;
    }

    public void setFerragemExposta(String ferragemExposta) {
        this.ferragemExposta = ferragemExposta;
    }

    public String getFletido() {
        return fletido;
    }

    public void setFletido(String fletido) {
        this.fletido = fletido;
    }

    public String getDanificado() {
        return danificado;
    }

    public void setDanificado(String danificado) {
        this.danificado = danificado;
    }

    public String getAbalroado() {
        return abalroado;
    }

    public void setAbalroado(String abalroado) {
        this.abalroado = abalroado;
    }

    public String getTrincado() {
        return trincado;
    }

    public void setTrincado(String trincado) {
        this.trincado = trincado;
    }

    public String getRamalSubt() {
        return ramalSubt;
    }

    public void setRamalSubt(String ramalSubt) {
        this.ramalSubt = ramalSubt;
    }

    public String getObservacaoFisicas() {
        return observacaoFisicas;
    }

    public void setObservacaoFisicas(String observacaoFisicas) {
        this.observacaoFisicas = observacaoFisicas;
    }

    public String getAtivos() {
        return ativos;
    }

    public void setAtivos(String ativos) {
        this.ativos = ativos;
    }

    public String getChkTrafoTrifasico() {
        return chkTrafoTrifasico;
    }

    public void setChkTrafoTrifasico(String chkTrafoTrifasico) {
        this.chkTrafoTrifasico = chkTrafoTrifasico;
    }

    public String getChkTrafoMono() {
        return chkTrafoMono;
    }

    public void setChkTrafoMono(String chkTrafoMono) {
        this.chkTrafoMono = chkTrafoMono;
    }

    public String getTrafoTrifasico() {
        return trafoTrifasico;
    }

    public void setTrafoTrifasico(String trafoTrifasico) {
        this.trafoTrifasico = trafoTrifasico;
    }

    public String getTrafoMono() {
        return trafoMono;
    }

    public void setTrafoMono(String trafoMono) {
        this.trafoMono = trafoMono;
    }

    public String getReligador() {
        return religador;
    }

    public void setReligador(String religador) {
        this.religador = religador;
    }

    public String getMedicao() {
        return medicao;
    }

    public void setMedicao(String medicao) {
        this.medicao = medicao;
    }

    public String getChFusivel() {
        return chFusivel;
    }

    public void setChFusivel(String chFusivel) {
        this.chFusivel = chFusivel;
    }

    public String getChFaca() {
        return chFaca;
    }

    public void setChFaca(String chFaca) {
        this.chFaca = chFaca;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getChFusivelReligador() {
        return chFusivelReligador;
    }

    public void setChFusivelReligador(String chFusivelReligador) {
        this.chFusivelReligador = chFusivelReligador;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    public String getObservacaoAtivos() {
        return observacaoAtivos;
    }

    public void setObservacaoAtivos(String observacaoAtivos) {
        this.observacaoAtivos = observacaoAtivos;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpEstrutura() {
        return ipEstrutura;
    }

    public void setIpEstrutura(String ipEstrutura) {
        this.ipEstrutura = ipEstrutura;
    }

    public String getQuantidadeLampada() {
        return quantidadeLampada;
    }

    public void setQuantidadeLampada(String quantidadeLampada) {
        this.quantidadeLampada = quantidadeLampada;
    }

    public String getTipoPot() {
        return tipoPot;
    }

    public void setTipoPot(String tipoPot) {
        this.tipoPot = tipoPot;
    }

    public String getPotReator() {
        return potReator;
    }

    public void setPotReator(String potReator) {
        this.potReator = potReator;
    }

    public String getIpAtivacao() {
        return ipAtivacao;
    }

    public void setIpAtivacao(String ipAtivacao) {
        this.ipAtivacao = ipAtivacao;
    }

    public String getVinteEQuatro() {
        return vinteEQuatro;
    }

    public void setVinteEQuatro(String vinteEQuatro) {
        this.vinteEQuatro = vinteEQuatro;
    }

    public String getQuantidade24H() {
        return quantidade24H;
    }

    public void setQuantidade24H(String quantidade24H) {
        this.quantidade24H = quantidade24H;
    }
    public String getObservacaoIP() {
        return observacaoIP;
    }

    public void setObservacaoIP(String observacaoIP) {
        this.observacaoIP = observacaoIP;
    }

    public String getMutuo() {
        return mutuo;
    }

    public void setMutuo(String mutuo) {
        this.mutuo = mutuo;
    }

    public String getQuantidadeOcupantes() {
        return quantidadeOcupantes;
    }

    public void setQuantidadeOcupantes(String quantidadeOcupantes) {
        this.quantidadeOcupantes = quantidadeOcupantes;
    }

    public String getQuantidadeCabos() {
        return quantidadeCabos;
    }

    public void setQuantidadeCabos(String quantidadeCabos) {
        this.quantidadeCabos = quantidadeCabos;
    }

    public String getTipoCabo() {
        return tipoCabo;
    }

    public void setTipoCabo(String tipoCabo) {
        this.tipoCabo = tipoCabo;
    }

    public String getQuantidadeCabosdois() {
        return quantidadeCabosdois;
    }

    public void setQuantidadeCabosdois(String quantidadeCabosdois) {
        this.quantidadeCabosdois = quantidadeCabosdois;
    }

    public String getTipoCabodois() {
        return tipoCabodois;
    }

    public void setTipoCabodois(String tipoCabodois) {
        this.tipoCabodois = tipoCabodois;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getCeans() {
        return ceans;
    }

    public void setCeans(String ceans) {
        this.ceans = ceans;
    }

    public String getTar() {
        return tar;
    }

    public void setTar(String tar) {
        this.tar = tar;
    }

    public String getReservaTec() {
        return reservaTec;
    }

    public void setReservaTec(String reservaTec) {
        this.reservaTec = reservaTec;
    }

    public String getBackbone() {
        return backbone;
    }

    public void setBackbone(String backbone) {
        this.backbone = backbone;
    }

    public String getPlacaIdent() {
        return placaIdent;
    }

    public void setPlacaIdent(String placaIdent) {
        this.placaIdent = placaIdent;
    }

    public String getDescidaCabos() {
        return descidaCabos;
    }

    public void setDescidaCabos(String descidaCabos) {
        this.descidaCabos = descidaCabos;
    }

    public String getDescricaoIrregularidade() {
        return descricaoIrregularidade;
    }

    public void setDescricaoIrregularidade(String descricaoIrregularidade) {
        this.descricaoIrregularidade = descricaoIrregularidade;
    }

    public String getObservacaoMutuo() {
        return observacaoMutuo;
    }

    public void setObservacaoMutuo(String observacaoMutuo) {
        this.observacaoMutuo = observacaoMutuo;
    }

    public String getDimensaoVegetacao() {
        return dimensaoVegetacao;
    }

    public void setDimensaoVegetacao(String dimensaoVegetacao) {
        this.dimensaoVegetacao = dimensaoVegetacao;
    }

    public String getDistaciaBaixa() {
        return distaciaBaixa;
    }

    public void setDistaciaBaixa(String distaciaBaixa) {
        this.distaciaBaixa = distaciaBaixa;
    }

    public String getDistanciaMedia() {
        return distanciaMedia;
    }

    public void setDistanciaMedia(String distanciaMedia) {
        this.distanciaMedia = distanciaMedia;
    }

    public String getEstadoArvore() {
        return estadoArvore;
    }

    public void setEstadoArvore(String estadoArvore) {
        this.estadoArvore = estadoArvore;
    }

    public String getQuedaArvore() {
        return quedaArvore;
    }

    public void setQuedaArvore(String quedaArvore) {
        this.quedaArvore = quedaArvore;
    }

    public String getLocalArvore() {
        return localArvore;
    }

    public void setLocalArvore(String localArvore) {
        this.localArvore = localArvore;
    }

    public String getObservacaoVegetacao() {
        return observacaoVegetacao;
    }

    public void setObservacaoVegetacao(String observacaoVegetacao) {
        this.observacaoVegetacao = observacaoVegetacao;
    }

    public void GenericSetter(String atributo, String dado){
        switch (atributo) {
            case "municipio":
                setMunicipio(dado);
                break;
            case "alturaCarga":
                setAlturaCarga(dado);
                break;
            case "tipoPoste":
                setTipoPoste(dado);
                break;
            case "ipEstrutura":
                setIpEstrutura(dado);
                break;
            case "tipoPot":
                setTipoPot(dado);
                break;
            case "ipAtivacao":
                setIpAtivacao(dado);
                break;
            case "trafoTrifasico":
                setTrafoTrifasico(dado);
                break;
            case "trafoMono":
                setTrafoMono(dado);
                break;
            case "ramalSubt":
                setRamalSubt(dado);
                break;
            case "quantidadeOcupantes":
                setQuantidadeOcupantes(dado);
                break;
            case "tipoCabo":
                setTipoCabo(dado);
                break;
            case "finalidade":
                setFinalidade(dado);
                break;
            case "ceans":
                setCeans(dado);
                break;
            case "tar":
                setTar(dado);
                break;
            case "reservaTec":
                setReservaTec(dado);
                break;
            case "backbone":
                setBackbone(dado);
                break;
            case "tipoCabodois":
                setTipoCabodois(dado);
                break;
            case "dimensaoVegetacao":
                setDimensaoVegetacao(dado);
                break;
            case "distanciaBaixa":
                setDistaciaBaixa(dado);
                break;
            case "distanciaMedia":
                setDistanciaMedia(dado);
                break;
            case "estadoArvore":
                setEstadoArvore(dado);
                break;
            case "localArvore":
                setLocalArvore(dado);
                break;
            case "chFaca":
                setChFaca(dado);
                break;
            case "chBanco":
                setBanco(dado);
                break;
        }


    }
}
