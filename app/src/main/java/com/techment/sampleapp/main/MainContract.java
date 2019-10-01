package com.techment.sampleapp.main;

import com.techment.sampleapp.model.ResponseItems;

import java.util.List;

public class MainContract {
    public interface View{
        void showData(int pageSize, List<ResponseItems> dataItem);
        void showError();
        void showProgress();
        void hideProgress();
    }
    interface Presenter{
        void loadData(int pageSize);
    }
}
