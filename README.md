# Gonnect
An elegant HTTP/HTTP2 library with great features

# Getting Started With Gonnect

  	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Add The Following Dependency To Root Build.gradle file
  
  
 	 dependencies {
		compile 'com.github.poorya-abbasi:Gonnect:v1.3'
	  }
  
  And Add This To build.gradle File for Your Module
  
  # Usage Form
  
   ## Getting Data 
      
        Gonnect.getData(url,ResponseListener(Optional),ResponseFailureListener(Optional));
        Or
        Gonnect.getDataAndLaunchActivity(url,ResponseFailureListener(Optional),SecondActivity.class,context);
        
   ## Sending Post Request
        ContentValues cv=new ContentValues();
        cv.put("user_token",token);
        Gonnect.sendRequest(url,cv,ResponseListener(Optional),ResponseFailureListener(Optional));
        Or
        Gonnect.sendRequestAndLaunchActivity(url,cv,ResponseFailureListener(Optional),SecondActivity.class,context);
   ## How To Get The Data When You Pass It To Another Activity
 	  Gonnect Automatically puts the data in the extras In The SecondActivity:
          Bundle b=getIntent().getExtras();
          String response=b.getString("response");
	  
	  
  ## Want To Cancel The Request? Simple
  	  Gonnect.getCancelableData(url,tag, new Gonnect.ResponseSuccessListener() {
            @Override
            public void responseRecieved(String response) {
                
            }
        }, new Gonnect.ResponseFailureListener() {
            @Override
            public void responseFailed(IOException exception) {

            }
        });
		Gonnect.cancelRequest(tag);
  # There Are Some Known Bugs In The Pro Features Listeners I'm Going To Fix It ASAP 
  
  ## Need Headers? Simple Again
  	  Gonnect.getFullData(url,fullResponseListener,responseFailureListener(Optional),headers(Optional);
	  The listener gives you a FullResponseStructure:
			FullResponseListener fullResponseListener=new FullResponseListener() {
           			@Override
          			public void responseRecieved(FullResponseStructure frs) {

					Headers headers=frs.headers;
					String response=frs.body;
					
           			 }
       			 };
## And Sending a request with headers
	 Gonnect.sendProRequest(url,cv,fullReponseListener,responseFailureListener(Optional),headers(optional);
	 The listener gives you a FullResponseStructure:
			FullResponseListener fullResponseListener=new FullResponseListener() {
           			@Override
          			public void responseRecieved(FullResponseStructure frs) {

					Headers headers=frs.headers;
					String response=frs.body;
					
           			 }
       			 };

 	  
 
