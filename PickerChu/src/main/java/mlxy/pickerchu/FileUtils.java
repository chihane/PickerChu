package mlxy.pickerchu;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static File getCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return context.getExternalCacheDir();
        } else {
            return context.getCacheDir();
        }
    }

    public static File createTempFile(Context context, String filename) {
        try {
            return File.createTempFile(filename, null, getCacheDir(context));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getFileDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            return context.getFilesDir();
        }
    }

    public static void deleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    public static File createFile(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        file.createNewFile();
        return file;
    }
}
