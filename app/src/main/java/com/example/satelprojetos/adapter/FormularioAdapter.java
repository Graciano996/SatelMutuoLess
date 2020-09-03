package com.example.satelprojetos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.satelprojetos.R;
import com.example.satelprojetos.model.Formulario;

import java.util.List;

public class FormularioAdapter extends RecyclerView.Adapter<FormularioAdapter.MyViewHolder> {
    private List<Formulario> listaFormulario;

    public FormularioAdapter(List<Formulario> lista) {
        this.listaFormulario = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_formulario_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Formulario formulario = listaFormulario.get(position);
        holder.formularioEndereco.setText("Código: " + formulario.getCodigo());
        holder.formularioData.setText(formulario.getData());
        holder.formularioLatitude.setText("Lat:" + formulario.getLatitude());
        holder.formularioLongitude.setText("Lon: " + formulario.getLongitude());
        int color = Integer.parseInt(formulario.getColor());
        int color2 = Integer.parseInt(formulario.getColor2());
        int color3 = Integer.parseInt(formulario.getColor3());
        int color4 = Integer.parseInt(formulario.getColor4());
        int color7 = Integer.parseInt(formulario.getColor7());
        int color8 = Integer.parseInt(formulario.getColor8());
        int color9 = Integer.parseInt(formulario.getColor9());
        int color10 = Integer.parseInt(formulario.getColor10());
        int color11 = Integer.parseInt(formulario.getColor11());
        int color12 = Integer.parseInt(formulario.getColor12());
        holder.formularioUpload.setBackgroundResource(color);
        holder.formularioUpload2.setBackgroundResource(color2);
        holder.formularioUpload3.setBackgroundResource(color3);
        holder.formularioUpload4.setBackgroundResource(color4);
        holder.formularioUpload7.setBackgroundResource(color7);
        holder.formularioUpload8.setBackgroundResource(color8);
        holder.formularioUpload9.setBackgroundResource(color9);
        holder.formularioUpload10.setBackgroundResource(color10);
        holder.formularioUpload11.setBackgroundResource(color11);
        holder.formularioUpload12.setBackgroundResource(color12);
    }

    @Override
    public int getItemCount() {
        return this.listaFormulario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView formularioLatitude, formularioLongitude, formularioData, formularioEndereco,
        formularioUpload, formularioUpload2, formularioUpload3,formularioUpload4,formularioUpload7,formularioUpload8,formularioUpload9,
                formularioUpload10,formularioUpload11,formularioUpload12;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            formularioEndereco = itemView.findViewById(R.id.cadastradosEndereco);
            formularioData = itemView.findViewById(R.id.cadastradosData);
            formularioLatitude= itemView.findViewById(R.id.cadastradosLatitude);
            formularioLongitude= itemView.findViewById(R.id.cadastradosLongitude);
            formularioUpload = itemView.findViewById(R.id.cadastroUpload);
            formularioUpload2 = itemView.findViewById(R.id.cadastroUpload2);
            formularioUpload3 = itemView.findViewById(R.id.cadastroUpload3);
            formularioUpload4 = itemView.findViewById(R.id.cadastroUpload4);
            formularioUpload7 = itemView.findViewById(R.id.cadastroUpload7);
            formularioUpload8 = itemView.findViewById(R.id.cadastroUpload8);
            formularioUpload9 = itemView.findViewById(R.id.cadastroUpload9);
            formularioUpload10 = itemView.findViewById(R.id.cadastroUpload10);
            formularioUpload11 = itemView.findViewById(R.id.cadastroUpload11);
            formularioUpload12 = itemView.findViewById(R.id.cadastroUpload12);
        }
    }
}
