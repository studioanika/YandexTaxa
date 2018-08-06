package apptaxi.yandex.com.yandextaxa.model;

import android.graphics.Bitmap;

public class Photo {

    String base64;
    Bitmap bitmap;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
