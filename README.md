# cordova-fileAssociation
File association plugin for cordova. Main purpose is to make possible to associate cordova app with file types well known or custom made. Currently it is for Android only.


How to use it
1.Clone from git git clone 
2.Configure your AndroidManifest.xml activity for example for json file:

....

<activity android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale" android:label="@string/activity_name" android:launchMode="singleTask" android:name="MainActivity" android:theme="@android:style/Theme.DeviceDefault.NoActionBar" android:windowSoftInputMode="adjustResize">
            ......
           <!--
                opening by file browser
            -->
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:host="*" android:pathPattern=".*\.json" android:scheme="file" />
            </intent-filter>

            <!--
                opening by email application
            -->
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:mimeType="application/json" />
            </intent-filter>

            <!--
                opening from remote ex. with http (ex. browser)
            -->
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.BROWSABLE" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="http" />
                <data android:host="*" />
                <data android:pathPattern=".*\\.json" />
            </intent-filter>
            .....
        </activity>
        
  </activity>      
  
  3.Add plugin to project by:
  cordova plugin add <path to plugin location>/FileAssociation
  
  4.Register listeners in your cordova app to be sure that plugin is available to use:
  
   var fileAssociation = function () {
                if (!window.plugins) {
                    console.log("plugins not defined");
                    return;
                }

                window.plugins.fileAssociation.getAssociatedData(null,
                    function (success) {
                        try {
                            if (success != null) {
                                //handle your data here maybe ex. by promise 
                                activityPromise.resolve(decoded);
                            }
                        } catch (e) {
                            activityPromise.reject(e);
                        }
                    }, function (error) {
                        activityPromise.reject(error);
                    });
            };
  
  //added to be sure that plgin is available<br>
  document.addEventListener("resume", function () {
                fileAssociation();
            }, false);

            document.addEventListener("deviceready", function () {
                fileAssociation();
            }, false);
  
  5.Use well 
  
  
