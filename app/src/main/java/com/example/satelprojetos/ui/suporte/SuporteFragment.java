package com.example.satelprojetos.ui.suporte;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.satelprojetos.R;

public class SuporteFragment extends Fragment {

    private SuporteViewModel suporteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        suporteViewModel =
                ViewModelProviders.of(this).get(SuporteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_suporte, container, false);

        return root;
    }


}