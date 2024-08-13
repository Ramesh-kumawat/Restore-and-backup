package com.example.restoreandbackup.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restoreandbackup.GoogleDriveHelper;
import com.example.restoreandbackup.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class CloudBackupActivity extends AppCompatActivity {

    Button Upload_GD, btn_cancel, btn_upload;
    String path;
    private String name;
    private String extension;
    static GoogleDriveHelper mDriveServiceHelper;
    Drive mDriveService;
    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;

    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";
    private final String SHEET_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final String FOLDER_NAME = "Example_Folder";


    private GoogleSignInClient mGoogleSignInClient;
    private DriveResourceClient mDriveResourceClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cload_backup);

        Upload_GD = findViewById(R.id.Upload_GD);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_upload = findViewById(R.id.btn_upload);
        clickevent();

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        extension = intent.getStringExtra("extension");

        Log.d("TAG", "onCreate: "+path);
        Log.d("TAG", "onCreate: "+name);

//        mDriveServiceHelper = new GoogleDriveHelper();
    }

    private void clickevent() {

        Upload_GD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(path+"/"+name+extension);
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            }
        });
    }

    private void signIn() {
        Log.i(TAG, "Start sign in");
        mGoogleSignInClient = buildGoogleSignInClient();
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

    }

    /** Build a Google SignIn client. */
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .build();



        return GoogleSignIn.getClient(this, signInOptions);
    }

//    private void saveFileToDrive(File file) {
//        // Start by creating a new contents, and setting a callback.
//        Log.i(TAG, "Creating new contents.");
//        mDriveResourceClient
//                .createContents()
//                .continueWithTask(
//                        new Continuation<DriveContents, Task<Void>>() {
//                            @Override
//                            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
//                                return createFileIntentSender(task.getResult(), file);
//                            }
//                        })
//                .addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Failed to create new contents.", e);
//                            }
//                        });
//    }
//    private Task<Void> createFileIntentSender(DriveContents driveContents,File image) {
//        Log.i(TAG, "New contents created.");
//        // Get an output stream for the contents.
//        OutputStream outputStream = driveContents.getOutputStream();
//        // Write the bitmap data from it.
//        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
//
//        try {
//            outputStream.write(bitmapStream.toByteArray());
//        } catch (IOException e) {
//            Log.w(TAG, "Unable to write file contents.", e);
//        }
//
//        // Create the initial metadata - MIME type and title.
//        // Note that the user will be able to change the title later.
//        MetadataChangeSet metadataChangeSet =
//                new MetadataChangeSet.Builder()
//                        .setMimeType("image/jpeg")
//                        .setTitle("Android Photo.png")
//                        .build();
//        // Set up options to configure and display the create file activity.
//        CreateFileActivityOptions createFileActivityOptions =
//                new CreateFileActivityOptions.Builder()
//                        .setInitialMetadata(metadataChangeSet)
//                        .setInitialDriveContents(driveContents)
//                        .build();
//
//        return mDriveClient
//                .newCreateFileActivityIntentSender(createFileActivityOptions)
//                .continueWith(
//                        new Continuation<IntentSender, Void>() {
//                            @Override
//                            public Void then(@NonNull Task<IntentSender> task) throws Exception {
//                                startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0);
//                                return null;
//                            }
//                        });
//    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                Log.i(TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Signed in successfully.");
                    // Use the last signed in account here since it already have a Drive scope.

                    try {
                        uploadFileToGoogleDrive(path+"/"+name+extension, name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case REQUEST_CODE_CREATOR:
                Log.i(TAG, "creator request code");
                // Called after a file is saved to Drive.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Image successfully saved.");

                    // Just start the camera again for another photo.

                }
                break;
        }
    }

    private void uploadFileToGoogleDrive(String s,String name) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(name);
        fileMetadata.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        java.io.File filePath = new java.io.File(s);
        FileContent mediaContent = new FileContent("*/*", filePath);


        File file = mDriveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
    }

     /*   if (mDriveServiceHelper != null) {
        mDriveServiceHelper.uploadFileToGoogleDrive(selectedFilePath)
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        loadToast.success();
                        showMessage("File uploaded ...!!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadToast.error();
                        showMessage("Couldn't able to upload file, error: "+e);
                    }
                });
    }
*/

}