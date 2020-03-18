package com.sample.droider.legacyrecipeapp.api;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import com.sample.droider.legacyrecipeapp.api.request.Request;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;


import java.io.IOException;

import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;


@RunWith(RobolectricTestRunner.class)
public class ApiRequestTaskTest {


    private MockWebServer server;
    ApiManager apiManager;

    @Mock
    Request request;
    @Mock
    ApiRequestTask.CallbackToMainThread callbackToMainThread;
    @Spy
    ApiRequestTask apiRequestTask = new ApiRequestTask(request,callbackToMainThread);


    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        apiManager = new ApiManager();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();

    }

    @Test
    public void execute() throws IOException, InterruptedException {
        // CountDownLatch

        server.enqueue(new MockResponse().setBody(body));
        server.start();
        Context context = InstrumentationRegistry.getTargetContext();

        apiManager.executeGet (request.getUrl(), context, request.getRequestTag(), new ApiManager.Callback() {
            @Override
            public void onSuccess(final Response response) {
                try {
                    final Object result = request.getParser().parse(response.body().string());

                }catch (IOException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(okhttp3.Request request, Exception e) {
                }
            }
        );
    }



    private String body ="{\n" +
            "\t\"recipe\": [\n" +
            "        {\n" +
            "          \"cooking_ingredients\": [\n" +
            "            {\n" +
            "              \"material\": \"合いびき肉\",\n" +
            "              \"quantity\": \"200ｇ\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"玉ねぎ\",\n" +
            "              \"quantity\": \"1個\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"卵\",\n" +
            "              \"quantity\": \"1個\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"パン粉\",\n" +
            "              \"quantity\": \"大2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"牛乳\",\n" +
            "              \"quantity\": \"大1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"塩コショウ\",\n" +
            "              \"quantity\": \"少々\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"●酒\",\n" +
            "              \"quantity\": \"大2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"玉ねぎ\",\n" +
            "              \"quantity\": \"1個\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"●ケチャップ\",\n" +
            "              \"quantity\": \"大2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"●中濃ソース\",\n" +
            "              \"quantity\": \"大1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"●砂糖\",\n" +
            "              \"quantity\": \"大1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"material\": \"●醤油\",\n" +
            "              \"quantity\": \"大1\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"cooking_method\": [\n" +
            "            {\n" +
            "              \"procedure_no\": \"1\",\n" +
            "              \"procedure\": \"玉ねぎはみじん切りにし、●以外の材料すべてをボウルに入れて手でよくこねる。\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"procedure_no\": \"2\",\n" +
            "              \"procedure\": \"成形し、フライパンに油（分量外）をひき、中火で焼き目がつくように焼き、ふたをして中に火が通るように蒸す。\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"procedure_no\": \"3\",\n" +
            "              \"procedure\": \"ハンバーグを皿に取り、そのフライパンに●をすべて入れて、弱火で煮立つまで温め、ハンバーグにかけたら完成です。\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"genre_cd\": \"G01\",\n" +
            "          \"genre_name\": \"お肉のおかず\",\n" +
            "          \"recipe_id\": \"001\",\n" +
            "          \"recipe_name\": \"洋食屋さんのハンバーグ\",\n" +
            "          \"introduction\": \"味も見た目もよし！のハンバーグです。\",\n" +
            "          \"main_gazo\": \"https://lgtmoon.herokuapp.com/images/17086\",\n" +
            "          \"recommended_flg\": \"1\"\n" +
            "        }\n" +
            "    ],\n" +
            "\t\"comments\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"id\": 1,\n" +
            "\t\t\t\"body\": \"some comment\",\n" +
            "\t\t\t\"postId\": 1\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"profile\": {\n" +
            "\t\t\"name\": \"typicode\"\n" +
            "\t}\n" +
            "}\n" ;
}