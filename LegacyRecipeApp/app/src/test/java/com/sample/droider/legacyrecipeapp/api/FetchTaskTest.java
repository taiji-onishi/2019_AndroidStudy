package com.sample.droider.legacyrecipeapp.api;

import android.content.Context;

import com.sample.droider.legacyrecipeapp.api.request.Request;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;


public class FetchTaskTest {


    @Mock
    Context context;
    @Mock
    Request request;
    @Mock
    ApiRequestTask.CallbackToMainThread callbackToMainThread;
    @Spy
    ApiRequestTask apiRequestTask = new ApiRequestTask(request,callbackToMainThread,context);

    FetchTask fetchTask;


    @Before
    public void setUp() {
        fetchTask = new FetchTask();
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(apiRequestTask).when(apiRequestTask).executeWithoutContext();
        Mockito.doNothing().when(apiRequestTask).executeGet();
    }

    @Test
    public void callExecute(){
        fetchTask.execute(apiRequestTask);
        Mockito.verify(apiRequestTask,Mockito.times(1)).executeWithoutContext();
    }

    @Test
    public void callExecuteGet(){
        fetchTask.execute(apiRequestTask);
        Mockito.verify(apiRequestTask,Mockito.times(1)).executeGet();
    }

    /*
    * Wanted but not invoked:
    * apiRequestTask.executeGet();
    * -> at com.sample.droider.legacyrecipeapp.api.FetchTaskTest.callExecuteGet(FetchTaskTest.java:39)
    *
    * However, there was exactly 1 interaction with this mock:
    * apiRequestTask.execute(
    * Mock for Context, hashCode: 1209770703);
    * -> at com.sample.droider.legacyrecipeapp.api.FetchTaskTest.callExecuteGet(FetchTaskTest.java:38)
    *
    */

}