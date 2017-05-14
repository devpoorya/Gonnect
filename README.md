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
		compile 'com.github.poorya-abbasi:Gonnect:0.1.1'
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
 	  Gonnect Automatically puts the data in the extras so
     	  In The SecondActivity Do This
          Bundle b=getIntent().getExtras();
          String response=b.getString("response");
            
