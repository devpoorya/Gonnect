# Gonnect
An elegant Android HTTP/HTTP2 library with great features.

## Installation

To add Gonnect to your project, simply add the following dependency to your module's `build.gradle` file:

    implementation 'com.github.poorya-abbasi:Gonnect:v1.3'

Alternatively, add `app/src/main/java/org/marlik/innovelopers/gonnect/Gonnect.java` from this repository to your project (don't forget to fix package name in the first line of the file) and then add the following dependencies to your module's `build.gradle` file:

    implementation 'com.squareup.okhttp3:okhttp:3.14.6'
    implementation 'com.android.volley:volley:1.1.1'

## Usage

### **Sending GET requests**

    // "tag" is a unique string for the request, which can be used later to cancel the request

    Gonnect.getCancelableData(urlString, CurrentActivity.this, tag, response -> {

        // This part is called when the request is successful
        // "response" will contain the recevied data

    }, exception -> {

        // This part is called when the request is unsuccessful
        // "exception" is an instance of IOException

    });

### **Sending GET Requests and launching an activity afterwards**
    // "tag" is a unique string for the request, which can be used later to cancel the request

    Gonnect.getCancelableDataAndLaunchActivity(urlString, tag, exception -> {
            
            // This part is called when the request is unsuccessful
            // "exception" is an instance of IOException

    }, DestinationActivity.class, CurrentActivity.this);

The result is automatically saved into the "response" extra string in the destination activity:

    // In DestinationActivity.java

    String response = getIntent().getStringExtra("response")


### **Sending POST requests**

    ContentValues cv = new ContentValues();
    cv.put("firstParameter", "someValue");
    cv.put("secondParameter", "someOtherValue");

    // "tag" is a unique string for the request, which can be used later to cancel the request

    Gonnect.sendCancelableRequest(urlString, CurrentActivity.this, cv, tag, response -> {

        // This part is called when the request is successful
        // "response" will contain the recevied data

    }, exception -> {
        
        // This part is called when the request is unsuccessful
        // "exception" is an instance of IOException

    });

### **Sending POST Requests and launching an activity afterwards**
    ContentValues cv = new ContentValues();
    cv.put("firstParameter", "someValue");
    cv.put("secondParameter", "someOtherValue");
    
    // "tag" is a unique string for the request, which can be used later to cancel the request

    Gonnect.sendCancelableRequestAndLaunchActivity(urlString, cv, tag, exception -> {
            
            // This part is called when the request is unsuccessful
            // "exception" is an instance of IOException

    }, DestinationActivity.class, CurrentActivity.this);

The result is automatically saved into the "response" extra string in the destination activity:

    // In DestinationActivity.java

    String response = getIntent().getStringExtra("response")

### **Canceling a request**

    Gonnect.cancelRequest(tag);

## Some other important notes

* Don't forget to add the following line to your project's `AndroidManifest.xml`, above the `<application>` tag:

    `<uses-permission android:name="android.permission.INTERNET" />`

* If you are calling HTTP URLs, which are not as secure as HTTPS calls, you should add the following property inside the `<application>` tag in your project's `AndroidManifest.xml`:

    `android:usesCleartextTraffic="true"`
