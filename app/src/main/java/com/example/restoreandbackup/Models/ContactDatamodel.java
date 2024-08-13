package com.example.restoreandbackup.Models;

import android.net.Uri;

public class ContactDatamodel {

    String position;
    Uri uri;

    public ContactDatamodel(String position, Uri uri) {
        this.position = position;
        this.uri = uri;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
