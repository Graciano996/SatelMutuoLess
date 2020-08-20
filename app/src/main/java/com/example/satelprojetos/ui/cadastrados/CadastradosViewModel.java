package com.example.satelprojetos.ui.cadastrados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastradosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CadastradosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}