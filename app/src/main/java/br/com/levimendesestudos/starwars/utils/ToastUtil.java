package br.com.levimendesestudos.starwars.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 809778 on 02/02/2017.
 */

public class ToastUtil {

    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showLong(Context context, int id) {
        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, int id) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
    }
}
