package org.marlik.innovelopers.gonnect;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Gonnect {

    private static  OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();
    private static RequestQueue queue;

    public Gonnect(){

    }

    public  static RequestBody setupRequestBody(ContentValues values){
        RequestBody requestBody=null;
        if (values != null && values.size() > 0) {
            FormBody.Builder formEncoding = new FormBody.Builder();

            Set<String> keySet = values.keySet();
            for (String key : keySet) {
                try {
                    values.getAsString(key);
                    formEncoding.add(key, values.getAsString(key));
//                    Log.d("GonnectLog","Gonnecting ...");

                } catch (Exception ex) {

                    Log.d("GonnectLog","Error Happend While Setting Up Request Body : "+ex.getMessage());
                }
            }
            requestBody = formEncoding.build();

        }
        return requestBody;

    }

    private static RequestBody setupMultipartBody(ContentValues values, String uploadKey, String filename, MediaType mediaType, File file){

        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        Set<String> keySet = values.keySet();
        for (String key : keySet) {
            try {
                builder.addFormDataPart(key, values.getAsString(key));
//                Log.d("key","KEY :"+key+"  VALUE:"+values.getAsString(key));
            } catch (Exception ex) {

                Log.d("GonnectLog","Error Happend While Setting Up Request Body : "+ex.getMessage());
            }
        }

//        builder.addPart(setupRequestBody(values));
        builder.addFormDataPart(uploadKey, filename,
                RequestBody.create(mediaType, file));
        return builder.build();

    }


    public static void upload(String url, ContentValues values,String tag,String uploadKey,String filename,String mediaType,File file,final ResponseSuccessListener listener,final ResponseFailureListener failureListener){

//        OkHttpClient okC = new OkHttpClient.Builder()
//                .connectTimeout(1, TimeUnit.MINUTES)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(mediaType),
//                        file
//                );
//
//        MultipartBody.Part filepart =
//                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//
//        RequestBody title= RequestBody.create(okhttp3.MultipartBody.FORM, values.get("title").toString());
//         content= RequestBody.create(okhttp3.MultipartBody.FORM, values.get("content").toString());
//        RequestBody type= RequestBody.create(okhttp3.MultipartBody.FORM, values.get("type").toString());
//        RequestBody format= RequestBody.create(okhttp3.MultipartBody.FORM, values.get("format").toString());
//        RequestBody token= RequestBody.create(okhttp3.MultipartBody.FORM, values.get("token").toString());
//
//        Retrofit.Builder builder=new Retrofit.Builder().baseUrl(url+"/").client(okC);
//        Retrofit retrofit=builder.build();
//        Client client=retrofit.create(Client.class);
//        retrofit2.Call<ResponseBody> call=client.upload(title,filepart,content,token,type,format);
//        call.enqueue(new retrofit2.Callback<ResponseBody>() {
//            @Override
//            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                try {
//                    listener.responseRecieved(response.body().string());
//                } catch (Exception ex) {
//                    listener.responseRecieved("sucess");
//                }
//            }
//            @Override
//            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
//                failureListener.responseFailed(new IOException());
//            }
//        });

//        SimpleMultiPartRequest req=new SimpleMultiPartRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.responseRecieved(response);
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                failureListener.responseFailed(new IOException());
//            }
//        });

//        queue = Volley.newRequestQueue(G.context);
//        queue.add(req);
//
//        Uri uri=GenericFileProvider.getUriForFile(G.context,G.context.getApplicationContext().getPackageName() + ".org.marlik.innovelopers.yekanapp.GenericFileProvider",file);
//        for(int i=0;i<values.size();i++){
//            String key=values.keySet().toArray()[i].toString();
//            req.addMultipartParam(key,"text/plain",values.get(key).toString());
//        }
//        req.addStringParam("Connection", "Keep-Alive");
//        req.addFile(uploadKey,file.getAbsolutePath());
//        req.setRetryPolicy(new DefaultRetryPolicy(8000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue = Volley.newRequestQueue(G.context);
//        queue.add(req);
//        queue.start();

//        okHttpClient.setRead(15000);


        RequestBody requestBody=setupMultipartBody(values,uploadKey,filename,MediaType.parse(mediaType),file);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
//                .tag(tag)
                .build();



        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

                failureListener.responseFailed(e);

            }

            @Override public void onResponse(Call call, Response response) throws IOException {


                listener.responseRecieved(response.body().string());


            }
        });
//        Map<String,String> map=new HashMap<>();
//        for(int i=0;i<values.size();i++){
//            String key=values.keySet().toArray()[i].toString();
//            map.put(key,values.get(key).toString());
//        }


    }

    //Simple Requests

    public static void sendRequest(String url, final ContentValues values, Context context, final ResponseListener listener){

        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.responseRecieved(true,response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.responseRecieved(false,"Gonnect Hardcoded Exception");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map=new HashMap<>();
                for(int i=0;i<values.size();i++){
                    String key=values.keySet().toArray()[i].toString();
                    map.put(key,values.get(key).toString());
                }
                return map;
            }
        };
//        stringRequest.setTag(tag);
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
//        queue = Volley.newRequestQueue(G.context);
//        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        listener.responseRecieved(response);
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                failureListener.responseFailed(new IOException());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                for(int i=0;i<values.size();i++){
//                    String key=values.keySet().toArray()[i].toString();
//                    map.put(key,values.get(key).toString());
//                }
//            }
//        };
//        stringRequest.setTag(tag);
//        queue.add(stringRequest);

//        RequestBody requestBody=setupRequestBody(values);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();


//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override public void onFailure(Call call, IOException e) {
//
//                listener.responseRecieved(false,"Gonnect Hardcoded Exception");
//
//            }
//
//            @Override public void onResponse(Call call, Response response) throws IOException {
//
//
//                listener.responseRecieved(true,response.body().string());
//
//
//            }
//        });

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

    public static void sendRequest(String url, final ContentValues values, Context context, final ResponseSuccessListener listener,final ResponseFailureListener failureListener){

        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.responseRecieved(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failureListener.responseFailed(new IOException());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map=new HashMap<>();
                for(int i=0;i<values.size();i++){
                    String key=values.keySet().toArray()[i].toString();
                    map.put(key,values.get(key).toString());
                }
                return map;
            }
        };
//        stringRequest.setTag(tag);
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }

    public static void sendCancelableRequest(String url, Context context, final ContentValues values, String tag,final ResponseSuccessListener listener,final ResponseFailureListener failureListener){

        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.responseRecieved(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failureListener.responseFailed(new IOException());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map=new HashMap<>();
                for(int i=0;i<values.size();i++){
                    String key=values.keySet().toArray()[i].toString();
                    map.put(key,values.get(key).toString());
                }
                return map;
            }
        };
        stringRequest.setTag(tag);
        stringRequest.setShouldCache(false);

        queue.add(stringRequest);


//        RequestBody requestBody=setupRequestBody(values);
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .tag(tag)
//                .build();
//
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override public void onFailure(Call call, IOException e) {
//
//                failureListener.responseFailed(e);
//
//            }
//
//            @Override public void onResponse(Call call, Response response) throws IOException {
//
//
//                listener.responseRecieved(response.body().string());
//
//
//            }
//        });

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

    public static void sendCancelableRequestAndLaunchActivity(String url, String tag,ContentValues values, final ResponseFailureListener failureListener, final Context context, final Class activity){

        RequestBody requestBody=setupRequestBody(values);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(tag)
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

    public static void getData(String url, Context context, final ResponseListener listener){

        queue= Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.responseRecieved(true,response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.responseRecieved(false,"Cannot Connect");
            }
        });
        stringRequest.setShouldCache(false);

        queue.add(stringRequest);
//        queue.stop();
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

    public static void getData(String url, Context context, final ResponseSuccessListener listener,final ResponseFailureListener failureListener){

        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.responseRecieved(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failureListener.responseFailed(new IOException());
            }
        });
        queue.add(stringRequest);
//        queue.stop();

    }

    public static void getCancelableData(String url, Context context,String tag, final ResponseSuccessListener listener,final ResponseFailureListener failureListener){

        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.responseRecieved(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failureListener.responseFailed(new IOException());
            }
        });
        stringRequest.setTag(tag);
        stringRequest.setShouldCache(false);

        queue.add(stringRequest);

//        Request request=new Request.Builder()
//                .url(url)
//                .tag(tag)
//                .build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override public void onFailure(Call call, IOException e) {
//
//                failureListener.responseFailed(e);
//
//            }
//
//            @Override public void onResponse(Call call, Response response) throws IOException {
//
//
//                listener.responseRecieved(response.body().string());
//
//            }
//        });

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

    public static void getCancelableDataAndLaunchActivity(String url,String tag, final ResponseFailureListener failureListener, final Class activity, final Context context){


        Request request=new Request.Builder()
                .url(url)
                .tag(tag)
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

        public void responseRecieved(boolean isSuccess, String errorOrResponse);

    }

    public interface FullResponseListener{

        public void responseRecieved(FullResponseStructure fullResponseStructure);

    }

    //ResponseController

    public static void responseController(String target,String response){





    }

    public static void cancelRequest(String tag){
        if (queue != null) {
            queue.cancelAll(tag);
        }
        for(Call call : okHttpClient.dispatcher().queuedCalls()) {
            if(call.request().tag().equals(tag))
                call.cancel();
        }
        for(Call call : okHttpClient.dispatcher().runningCalls()) {
            if(call.request().tag().equals(tag))
                call.cancel();
        }

    }


}

class FullResponseStructure {

    String body;
    Headers headers;

}
