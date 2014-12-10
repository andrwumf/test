package com.template.mlevytskiy;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import com.template.mlevytskiy.common.memory.MemoryCommunicator;
import com.template.mlevytskiy.memory.MemoryKey;
import com.template.mlevytskiy.ui.trainingModule.TrainingWords;
import com.template.mlevytskiy.util.IrregularVerbsUtils;
import com.template.mlevytskiy.vo.IrregularVerb;
import java.util.List;
import java.util.Locale;

/**
 * Created by Макс on 03.04.14.
 */
public class App extends Application {

    public static App instance;
    private TrainingWords trainingWords = new TrainingWords();
    private static List<IrregularVerb> list = null;

    public static boolean listIsEmpty() {
        return list == null;
    }

    public static List<IrregularVerb> getList() {
        if (list == null) {
            initStringsFromRes();
        }
        return list;
    }

    public TrainingWords getTrainingWords() {
        return trainingWords;
    }

    private static void initStringsFromRes() {
        list = new IrregularVerbsUtils().fromStringsRes(instance);
    }

    public static void asyncInitStringFromRes(final Runnable afterInit) {
        new Thread() {
            public void run() {
                initStringsFromRes();
                new Handler(Looper.getMainLooper()).post(afterInit);
            }
        }.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MemoryCommunicator memory = new MemoryCommunicator(MemoryKey.language);
        if (!memory.isEmpty()) {
            String language = memory.loadString();
            setLocale(language);
        }
    }

    public void setLocale(String localeName) {
        Locale locale2 = new Locale(localeName);
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        this.getResources().updateConfiguration(config2, null);
        new MemoryCommunicator(MemoryKey.language).saveString(localeName);
    }

}
