package com.example.appbanco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tfrAdapter extends RecyclerView.Adapter<tfrAdapter.trfViewHolder>{
    ArrayList<transferencia> listadoTrf;

    public tfrAdapter(ArrayList<transferencia> listadoTrf) {
        this.listadoTrf = listadoTrf;
    }
    @NonNull
    @Override
    public tfrAdapter.trfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transferencias,null,false);
        return new trfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tfrAdapter.trfViewHolder holder, int position) {
        holder.NroCtaDest.setText(listadoTrf.get(position).getCtadestino());
        holder.Hora.setText(listadoTrf.get(position).getHora());
        holder.Fecha.setText(listadoTrf.get(position).getFecha());
        holder.Valor.setText(listadoTrf.get(position).getValor());
    }

    @Override
    public int getItemCount() {
        return listadoTrf.size();
    }

    public class trfViewHolder extends RecyclerView.ViewHolder {
        TextView NroCtaDest, Hora, Fecha, Valor;
        public trfViewHolder(@NonNull View itemView) {
            super(itemView);
            NroCtaDest = itemView.findViewById(R.id.tvcuentadest);
            Hora = itemView.findViewById(R.id.tvhora);
            Fecha = itemView.findViewById(R.id.tvfecha);
            Valor = itemView.findViewById(R.id.tvvalor);
        }
    }
}
