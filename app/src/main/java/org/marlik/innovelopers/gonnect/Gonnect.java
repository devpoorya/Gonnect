package org.marlik.innovelopers.gonnect;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Gonnect {
    private static  OkHttpClient okHttpClient=new OkHttpClient();

    public Gonnect(){

    }

    private  static RequestBody setupRequestBody(ContentValues values){
        RequestBody requestBody=null;
        if (values != null && values.size() > 0) {
            FormBody.Builder formEncoding = new FormBody.Builder();

            Set<String> keySet = values.keySet();
            for (String key : keySet) {
                try {
                    values.getAsString(key);
                    formEncoding.add(key, values.getAsString(key));

                } catch (Exception ex) {

                    Log.d("GonnectLog","Error Happend While Setting Up Request Body : "+ex.getMessage());
                }
            }
            requestBody = formEncoding.build();

        }
        return requestBody;
    }

    //Simple Requests

    public static void sendRequest(String url, ContentValues values, final ResponseListener listener){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                listener.responseRecieved(false,e.getMessage().toString());

            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(true,response.body().string());


            }
        });

    }

    public static void sendRequest(String url, ContentValues values, final ResponseSuccessListener listener){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(response.body().string());


            }
        });

    }

    public static void sendRequest(String url, ContentValues values, final ResponseFailureListener failureListener){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {




            }
        });

    }

    public static void sendRequest(String url, ContentValues values, final ResponseSuccessListener listener,final ResponseFailureListener failureListener){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(response.body().string());


            }
        });

    }

    //Pro Requests

    public static void sendProRequest(String url, ContentValues values, final FullResponseListener listener){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);


            }
        });

    }

    public static void sendProRequest(String url, ContentValues values, final FullResponseListener listener,Headers headers){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);


            }
        });

    }

    public static void sendProRequest(String url, ContentValues values, final FullResponseListener listener,final ResponseFailureListener failureListener){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);


            }
        });

    }

    public static void sendProRequest(String url, ContentValues values, final FullResponseListener listener,final ResponseFailureListener failureListener,Headers headers){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);


            }
        });

    }

    //Launch Activity Requests

    public static void sendRequestAndLaunchActivity(String url, ContentValues values, final Context context, final Class activity){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                Intent intent=new Intent(context,activity);
                intent.putExtra("response",response.body().string());
                context.startActivity(new Intent(context,activity));

            }
        });

    }

    public static void sendRequestAndLaunchActivity(String url, ContentValues values, final ResponseFailureListener failureListener, final Context context, final Class activity){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                Intent intent=new Intent(context,activity);
                intent.putExtra("response",response.body().string());
                context.startActivity(new Intent(context,activity));
                

            }
        });

    }

    //Simple Get Data

    public static void getData(String url, final ResponseListener listener){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                listener.responseRecieved(false,e.getMessage().toString());

            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(true,response.body().string());

            }
        });

    }

    public static void getData(String url, final ResponseSuccessListener listener){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(response.body().string());

            }
        });

    }

    public static void getData(String url,final ResponseFailureListener failureListener){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {



            }
        });

    }

    public static void getData(String url, final ResponseSuccessListener listener,final ResponseFailureListener failureListener){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(response.body().string());

            }
        });

    }

    //Launch Activity Get Data(s)

    public static void getDataAndLaunchActivity(String url,final Class activity, final Context context){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {



            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                Intent intent=new Intent(context,activity);
                intent.putExtra("response",response.body().string());
                context.startActivity(new Intent(context,activity));
            }
        });

    }

    public static void getDataAndLaunchActivity(String url, final ResponseFailureListener failureListener, final Class activity, final Context context){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                Intent intent=new Intent(context,activity);
                intent.putExtra("response",response.body().string());
                context.startActivity(new Intent(context,activity));
            }
        });

    }

    //Pro Get Data(s)

    public static void getFullData(String url, final FullResponseListener listener){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);

            }
        });

    }

    public static void getFullData(String url, final FullResponseListener listener,Headers headers){


        Request request=new Request.Builder()
                .url(url)
                .headers(headers)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {


            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);

            }
        });

    }

    public static void getFullData(String url, final FullResponseListener listener,final ResponseFailureListener failureListener){


        Request request=new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);

            }
        });

    }

    public static void getFullData(String url, final FullResponseListener listener,final ResponseFailureListener failureListener,Headers headers){


        Request request=new Request.Builder()
                .url(url)
                .headers(headers)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                FullResponseStructure fullResponseStructure=new FullResponseStructure();
                fullResponseStructure.body=response.body().string();
                fullResponseStructure.headers=response.headers();

                listener.responseRecieved(fullResponseStructure);

            }
        });

    }

    //Interfaces

    public interface ResponseSuccessListener{

        public void responseRecieved(String response);

    }

    public interface ResponseFailureListener{

        public void responseFailed(IOException exception);

    }

    public interface  ResponseListener{

        public void responseRecieved(boolean isSuccess,String errorOrResponse);

    }

    public interface FullResponseListener{

        public void responseRecieved(FullResponseStructure frs);

    }

    //ResponseController

    public static void responseController(String target,String response){






    }



}
