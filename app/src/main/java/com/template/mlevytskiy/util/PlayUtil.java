package com.template.mlevytskiy.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;

/**
 * Created by max on 11.12.14.
 */
public class PlayUtil {

    public static void play(MediaPlayer mPlayer, Uri mp3, Context context) {
        mPlayer.reset();
        try {
            try {
                mPlayer.setDataSource(context, mp3);
            } catch (IllegalStateException e) {
                mPlayer.reset();
                mPlayer.setDataSource(context, mp3);
            }
            mPlayer.prepare();
        } catch (IOException e) {
            //doNothing
            e.printStackTrace();
        }
        mPlayer.start();
    }
}
