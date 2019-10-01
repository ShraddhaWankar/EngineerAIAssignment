package com.techment.sampleapp.main;

import com.techment.sampleapp.model.ResponseData;
import com.techment.sampleapp.network.DemoAPI;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter{

    private DemoAPI apiInterface;
    private MainContract.View mView;

    @Inject
    public MainPresenter(DemoAPI apiInterface,MainContract.View mView){
        this.apiInterface = apiInterface;
        this.mView = mView;
    }

    @Override
    public void loadData(final int pageSize) {
        if(pageSize > 1){
            mView.showProgress();
        }
        apiInterface.getPageData(pageSize).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseData responseData) {
                        mView.showData(pageSize,responseData.getHits());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgress();
                    }
                });
    }
}
