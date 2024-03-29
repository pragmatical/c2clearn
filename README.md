# c2clearn

Code2College Android Development Class Example Application, instructions on usage below.  The application is written in Kotlin using Firebase for backend.  The example application provides functionality to:
- Register - using Firebase Authentication and Firebase Real Time Database
- Authenticate - using Firebase Authentication
- Create Posts - using Firebase Real Time Database
- Update Profile - including a profile picture using Firebase Real Time Database and Firebase Storage

## 1.  Fork, clone repo and create project from cloned repo
1. Download and install [Android Studio](https://developer.android.com/studio)
2. Fork the following repo: <https://github.com/pragmatical/c2clearn>
3. Start Android Studio
4. Select File>New>Project From Version Control...
5. Enter the url of your forked repo:
   
   ```
   https://github.com/<your-account>/c2clearn.git
   ```
6. Click clone - this will take a bit of time but when finished your project will open in Android studio

## 2.  Set Up Firebase

### Set Up Authentication

1. In Android Studio open firebase assistant by clicking on Tools>Firebase
2. Expand Authentication Section in the Assistant
3. In Firebase Assistant click Authenticate Using Google
4. Click connect to firebase and log in
5. Click on Add Project to add your c2c project and follow instructions to create the project
6. When project is created click connect to connect your app to the newly set up  firebase project and return to android studio
7. Add the Authentication SDK as mentioned in step 2 of  assistant
8. Navigate to firebase console and open your newly created project
9. Navigate to Authentication section and go to Sign-in method
10. Select Email/Password, enable email/password and save

### Set Up Real Time Database

1. In Android Studio open firebase assistant by clicking on Tools>Firebase
2. Expand Realtime Database in the assistant
3. Your app should be conected to the database already soset up the dependencies by following steps in the Add Realtime Database to your app
4. Navigate to firebase console and open your newly created project
5. Click All Products and then Click on Realtime Database
6. Click on Create Database
7. Follow Steps to create the database ensuring you start the database in test mode, finish by clicking enable in that screen.

### Set Up Storage

1. In Android Studio open firebase assistant by clicking on Tools>Firebase
2. Expand Cloud Storage for Firebase in the assistant and click get started with cloud storage
3. Firebase connection should exist but if not, connect to firebase project
4. Your app should be conected to the database already so set up the dependencies by following steps in the Add Realtime Database to your app
5. Navigate to firebase console and open your newly created project
6. Click All Products and then Click on Storage
7. Click on Get Started 
8. Follow Steps to create storage bucket and ensure you create the storage bucket in test mode.
9. To finish the setup select a location and click Done

## 3.  Run the app

1. Your app should be ready to run in Android Studio
2. From Android Studio click Run App
3. From running app in emulator click sign up and create your first user
4. Navigate to firebase tosee that user created in the Authentication area as well as in the Realtime Database
    

