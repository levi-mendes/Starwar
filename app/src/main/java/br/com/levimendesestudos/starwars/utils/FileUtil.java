package br.com.levimendesestudos.starwars.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.levimendesestudos.starwars.ApplicationStarWarApp;

/**
 * Created by 809778 on 08/02/2017.
 */

public class FileUtil {

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator;

        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path + "zzzzz.jpg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("writeResponseBodyToDisk", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
