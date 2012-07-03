USAGE:
 - Copy this folder into your Eclipse workspace
 - Add this library to you projects in Eclipse
	- File > Import > General > Existing Projects into Workspace
 - Open the properties of the project where you want to use this library
 - Click on "Android" and in the right side in the "Library" section click "Add" and select this library
 - Add the following Code to your Manifest file:
 
The permissions and features on the same level as the <application> tag:
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.VIBRATE"/>
<uses-permission android:name="android.permission.FLASHLIGHT"/>

<uses-feature android:name="android.hardware.camera"/>
<uses-feature android:name="android.hardware.camera.autofocus" android:required="false"/>
<uses-feature android:name="android.hardware.camera.flash" android:required="false"/>
<uses-feature android:name="android.hardware.screen.landscape"/>
<uses-feature android:name="android.hardware.touchscreen" android:required="false"/>

The activity inside the <application> tag:
<activity android:name="edu.kit.cm.qrscanner.CaptureActivity"
		  android:screenOrientation="landscape"
		  android:clearTaskOnLaunch="true"
		  android:stateNotNeeded="true"
		  android:configChanges="orientation|keyboardHidden"
		  android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
		  android:windowSoftInputMode="stateAlwaysHidden">
</activity>


 - Now you should be able to start the activity to scan a qr-code
 - Have a look at the example project to get further information on how to use this library