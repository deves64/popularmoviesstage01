package com.udacity.popular_movies_stage_01;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

class InternetCheckTask extends AsyncTask<Void,Void,Boolean> {

    private final InternetConsumer mInternetConsumer;

    public InternetCheckTask(InternetConsumer internetConsumer) {
        mInternetConsumer = internetConsumer;
        execute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        mInternetConsumer.accept(internet);
    }
}