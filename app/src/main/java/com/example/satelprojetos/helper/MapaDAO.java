package com.example.satelprojetos.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.satelprojetos.model.Mapa;

import java.util.ArrayList;
import java.util.List;

public class MapaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public MapaDAO(Context context){
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();

    }
    public boolean salvar(Mapa mapa) {
        ContentValues cv = setCV(mapa);



        try{
            escreve.insert(DbHelper.TABLE_MAPA,null, cv);
            Log.i("INFO","Formulario salvo com sucesso!");

        }catch (Exception e){
            Log.e("INFO","Erro ao salvar formulario" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean atualizar(Mapa mapa) {
        ContentValues cv = setCV(mapa);

        String[] args = {mapa.getCodigo()};

        try{
            escreve.update(DbHelper.TABLE_MAPA,cv,"codigo=?",args);
            Log.i("INFO","Formulario atualizado com sucesso!");

        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar formulario" + e.getMessage());
            return false;
        }
        return true;



    }

    public boolean deletarTudo(){
        try{
        escreve.execSQL("delete from "+ DbHelper.TABLE_MAPA);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean deletar(Mapa mapa) {

        try{
            String[] args = {mapa.getCodigo()};
            escreve.delete(DbHelper.TABLE_MAPA,"codigo=?",args);
            Log.i("INFO","Formulario atualizado com sucesso!");

        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar formulario" + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Mapa> listar() {
        List<Mapa> mapas = new ArrayList<>();
        

        String sql = "SELECT * FROM " + DbHelper.TABLE_MAPA + " ;";
        Cursor c = le.rawQuery(sql,null);

        while (c.moveToNext()){

            String latitude = c.getString(c.getColumnIndex("latitude"));
            String longitude = c.getString((c.getColumnIndex("longitude")));
            String codigo = c.getString((c.getColumnIndex("codigo")));
            String cadastrado = c.getString((c.getColumnIndex("cadastrado")));
            String existe = c.getString(c.getColumnIndex("existe"));

            Mapa mapa = new Mapa();
            mapa.setLatitude(latitude);
            mapa.setLongitude(longitude);
            mapa.setCodigo(codigo);
            mapa.setCadastrado(cadastrado);
            mapa.setExiste(existe);


            mapas.add(mapa);
        }

        return mapas;
    }

    public ContentValues setCV(Mapa mapa){
        ContentValues cv = new ContentValues();
        cv.put("latitude", mapa.getLatitude());
        cv.put("longitude", mapa.getLongitude());
        cv.put("codigo", mapa.getCodigo());
        cv.put("cadastrado", mapa.getCadastrado());
        cv.put("existe", mapa.getExiste());

        return cv;
    }
}
