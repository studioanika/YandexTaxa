package apptaxi.yandex.com.yandextaxa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import apptaxi.yandex.com.yandextaxa.enums.TypePhotoREG;
import apptaxi.yandex.com.yandextaxa.fragments.FragmentGPS;
import apptaxi.yandex.com.yandextaxa.fragments.FragmentInfo;
import apptaxi.yandex.com.yandextaxa.fragments.FragmentReg;
import apptaxi.yandex.com.yandextaxa.fragments.FragmentTO;

public class MainActivity extends AppCompatActivity {


    private static Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    private final int TAKE_PICTURE = 1;
    private final int FILE_CHOOSER_RESULT = 2;
    private Uri outputFileUri;
    FragmentTO fragmentTO;
    FragmentReg fragmentReg;

    TypePhotoREG typePhotoREG;

    int isimage = 0;
    String id = "";

    Bitmap bitmap;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new FragmentInfo();
                        transactionFragment();
                        return true;
                    case R.id.navigation_gps:
                        fragment = new FragmentGPS(MainActivity.this);
                        transactionFragment();
                        return true;
                    case R.id.navigation_to:
                        fragment = new FragmentTO(MainActivity.this);
                        fragmentTO = (FragmentTO) fragment;
                        transactionFragment();
                        return true;
                    case R.id.navigation_reg:
                        fragment = new FragmentReg(MainActivity.this);
                        fragmentReg = (FragmentReg) fragment;
                        transactionFragment();
                        return true;
                }
                return false;
            }
        });

        fragment = new FragmentInfo();
        transactionFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            }, 199);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_tel);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call("+79263608166");
            }
        });

    }

    private void transactionFragment(){

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Выбор изображения")
                        .setCancelable(true)
                        .setPositiveButton("Открыть фото",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        showFileChooser();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Сделать фото",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        takePhoto();
                                        dialog.cancel();
                                    }
                                });

                return builder.create();
            default:
                return null;
        }
    }

    public File file() {
        String path = "";
        if (isimage == 1) {
            path = outputFileUri.getPath();
        } else if (isimage == 2) {
            path = getPath(outputFileUri);
        }

        File f = new File(getCacheDir(), "buffer.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case TAKE_PICTURE:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), outputFileUri);
                        bitmap = decodeSampledBitmapFromUri(outputFileUri.getPath(), 800, 600);

                        if(typePhotoREG == TypePhotoREG.TO) fragmentTO.setPhoto(bitmap);
                        else fragmentReg.setPhotoSTS(bitmap, typePhotoREG);

                        isimage = 1;
                    } catch (IOException e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FILE_CHOOSER_RESULT:
                    try {
                        outputFileUri = data.getData();

                        bitmap = decodeSampledBitmapFromUri(getPath(outputFileUri), 800, 600);
                        if(typePhotoREG == TypePhotoREG.TO) fragmentTO.setPhoto(bitmap);
                        else fragmentReg.setPhotoSTS(bitmap, typePhotoREG);

                        isimage = 2;
                    } catch (Exception e) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


    }

    public static Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void showFileChooser() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, FILE_CHOOSER_RESULT);
    }

    public void takePhoto() {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(Environment.getExternalStorageDirectory(),
                    "minskkniga.jpg");
            outputFileUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, TAKE_PICTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public void show(){
        showDialog(1);
         typePhotoREG = TypePhotoREG.TO;
    }

    public void showReg(TypePhotoREG _typePhotoREG){
        typePhotoREG = _typePhotoREG;
        showDialog(1);
    }

    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void showDialogPD() {

        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dealog_offerta);

            final WebView webView = (WebView) dialog.findViewById(R.id.wv);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                }

                public void onPageFinished(WebView view, String url) {

                    webView.setVisibility(View.VISIBLE);


                }
            });

            webView.loadUrl("https://taxi-yandex.pro/soglashenie.php");


            TextView tv_close = (TextView) dialog.findViewById(R.id.tv_close);
            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);


            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showDialogOfferta() {

        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dealog_offerta_norm);

            TextView tv_close = (TextView) dialog.findViewById(R.id.tv_close);
            final WebView webView = (WebView) dialog.findViewById(R.id.wv);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                }

                public void onPageFinished(WebView view, String url) {

                    webView.setVisibility(View.VISIBLE);


                }
            });

            webView.loadUrl("https://taxi-yandex.pro/oferta.php");
            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);


            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
