package com.sample.droider.legacyrecipeapp.api;


public class FetchTask {

    public void execute(ApiRequestTask apiRequestTask){
        apiRequestTask.executeWithoutContext();
    }
}
