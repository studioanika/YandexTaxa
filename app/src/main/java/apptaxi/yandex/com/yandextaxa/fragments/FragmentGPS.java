package apptaxi.yandex.com.yandextaxa.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import apptaxi.yandex.com.yandextaxa.MainActivity;
import apptaxi.yandex.com.yandextaxa.R;
import apptaxi.yandex.com.yandextaxa.network.App;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class FragmentGPS extends Fragment {

    View v;

    EditText et_fio, et_city, et_phone, et_mail;
    CheckBox ch;
    Button btn;
    ImageView share;
    Button btn_pd;

    Context context;
    public FragmentGPS(Context context){
        this.context = context;
    }

    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_gps, container, false);
        intitUI();
        return v;
    }

    private void intitUI() {

        et_fio = (EditText) v.findViewById(R.id.gps_et_fio);
        et_city = (EditText) v.findViewById(R.id.gps_et_city);
        et_phone = (EditText) v.findViewById(R.id.gps_et_phone);
        et_mail = (EditText) v.findViewById(R.id.gps_et_email);
        btn_pd = (Button) v.findViewById(R.id.gps_btn_pd);
        btn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showDialogPD();
            }
        });

        ch = (CheckBox) v.findViewById(R.id.gps_check);
        btn = (Button) v.findViewById(R.id.gps_send);
        share = (ImageView) v.findViewById(R.id.img_share);

        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btn.setEnabled(true);
                    btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_active));
                }else {
                    btn.setEnabled(false);
                    btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_no_active));
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/details?id="+getActivity().getApplicationContext().getPackageName();
                String text = getResources().getString(R.string.share_st)+url;

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        text);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
    }

    private void send() {

        String fio = et_fio.getText().toString();
        String city = et_city.getText().toString();
        String phone = et_phone.getText().toString();
        String mail = et_mail.getText().toString();

        if(fio.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле ФИО", Toast.LENGTH_SHORT).show();
            return;
        }

        if(city.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле Город", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле Телефон", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mail.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле E-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        sendToServer(fio, city, phone, mail);

    }

    public void  sendToServer(String fio, String city, String  phone, String email){

        // TODO здесь отправка на сервер
        Map<String, String> body = new HashMap();
        body.put("fio", fio);
        body.put("city", city);
        body.put("telephone", phone);
        body.put("email", email);


        App.getApi().setGPS(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody body1 = response.body();

                try {
                    String b = response.body().string();

                    if(b.contains("Ok")) Toast.makeText(v.getContext(), "Заявка успешно отправлена", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(v.getContext(), "Ошибка отправки заявки...", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String ts = t.toString();
                Toast.makeText(v.getContext(), "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
