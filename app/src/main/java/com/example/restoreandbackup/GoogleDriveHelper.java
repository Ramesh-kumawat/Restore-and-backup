package com.example.restoreandbackup;

import android.util.Log;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GoogleDriveHelper {


    private static final String TAG = "GoogleDriveService";
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;

    private final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";
    private final String SHEET_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final String FOLDER_NAME = "Example_Folder";


    public GoogleDriveHelper(Drive driveService) {
        mDriveService = driveService;
    }




    public void uploadFileToGoogleDrive(String path) throws IOException {


        Log.e("TAG", "uploadFileToGoogleDrive: path: " + path);
        java.io.File filePath = new java.io.File(path);

        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());
        fileMetadata.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        FileContent mediaContent = new FileContent("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", filePath);
        File file = mDriveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());


    }

}
