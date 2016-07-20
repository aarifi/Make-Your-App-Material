package com.example.xyzreader.data;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.xyzreader.utils.Constants.FIREBASE_FOLLDER_PATH_ARTICLE_PHOTO;

/**
 * Created by AdonisArifi on 7.6.2016 - 2016 . xyzreader
 */

public class FirebaseDatabaseXyzReader {

    public FirebaseApp app = FirebaseApp.getInstance();
    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static FirebaseDatabaseXyzReader firebaseDatabaseInstance;
    //Firebase database
    public DatabaseReference mRootRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();

    public DatabaseReference articleRef = mRootRef.child("Article");

    public DatabaseReference abouteRef = mRootRef.child("About");

    public DatabaseReference isAddDataFromDropBox = mRootRef.child("Utils");

    public FirebaseStorage storage = FirebaseStorage.getInstance(app);
    public FirebaseAuth auth = FirebaseAuth.getInstance(app);

    // Get a reference to the location where we'll store our photos
    public StorageReference storageRef = storage.getReference(FIREBASE_FOLLDER_PATH_ARTICLE_PHOTO);


    public static FirebaseDatabaseXyzReader getFirebaseDatabaseInstance() {
        if (firebaseDatabaseInstance == null) {
            firebaseDatabaseInstance = new FirebaseDatabaseXyzReader();
        }
        return firebaseDatabaseInstance;
    }

    public FirebaseDatabaseXyzReader() {

    }

    public void logIn() {
        // Sign the user in with the email address and password they entered
        FirebaseDatabaseXyzReader.getFirebaseDatabaseInstance().auth.signInWithEmailAndPassword("adonisarifi@gmail.com", "123123.*");
    }

    public void setDiskPersistence() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
