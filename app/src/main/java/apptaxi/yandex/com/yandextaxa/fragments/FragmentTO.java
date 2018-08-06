package apptaxi.yandex.com.yandextaxa.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apptaxi.yandex.com.yandextaxa.MainActivity;
import apptaxi.yandex.com.yandextaxa.R;
import apptaxi.yandex.com.yandextaxa.model.Photo;
import apptaxi.yandex.com.yandextaxa.network.App;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class FragmentTO extends Fragment {

    View v;
    Button btn_pd;

    CheckBox ch;
    Button btn;
    ImageView share, photo;

    EditText et_fio, et_city, et_phone, et_mail;
    EditText et_gos_reg_znak, et_number_kuzov,
             et_razreshennaya_massa, et_massa_bez_nagr;

    EditText et_rd_seria, et_rd_nuumber, et_rd_from, et_rd_date, et_rd_marka_s,
             et_rd_probeg, et_rd_vin, et_rd_marka_ts, et_rd_model_ts,
             et_rd_year, et_rd_rama;

    Spinner type_toplivo, type_tormoz, type_rd, type_category;

    RelativeLayout rel_take_photo;

    RecyclerView mRecycler;

    Context context;

    ProgressBar progressBar;

    MyAdapter adapter;
    List<Photo> list = new ArrayList<>();

    RelativeLayout rel_l_d, rel_a_d, rel_r_d;
    LinearLayout lin_l_d, lin_a_d, lin_r_d;
    ImageView img_l_d, img_a_d, img_r_d;

    String[] mass_toplivo = new String[] {"Тип топлива","Бензин", "Дизельное топливо",
             "Сжатый газ", "Сжиженый газ", "Без топлива"};

    String[] mass_tormoz = new String[] {"Тип тормозной системы","Гидравлический",
            "Механический","Пневматический", "Комбинированный"};

    String [] mass_type_r = new String[]{"Регистрационный документ", "СРТС", "ПТС"};

    String [] mass_category = new String[]{"Категория ТС (ОКП) *", "Легковые (М1)",
            "Автобусы до  т. (М2)", "Автобусы от 5 т. (М3)", "Грузовые до 3,5 т. (N1)",
            "Грузовые от 3,5 т. до 12 т. (N2)", "Грузовые от 12 т. (N3)",
            "Прицепы до 0,75 т. (O1)", "Прицепы от 0,75 до 3,5 т. (O2)",
            "Прицепы от 3.5 до 12 т. (O3)", "Прицепы от 10 т. (O4)","Мотоциклы (L)"};

    public FragmentTO(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_to, container, false);
        intitUI();
        return v;
    }

    private void intitUI() {
        progressBar = (ProgressBar) v.findViewById(R.id.prgrs);
        photo = (ImageView) v.findViewById(R.id.img_photo);
        rel_take_photo = (RelativeLayout) v.findViewById(R.id.gps_rel_photo);
        et_fio = (EditText) v.findViewById(R.id.gps_et_fio);
        et_city = (EditText) v.findViewById(R.id.gps_et_city);
        et_phone = (EditText) v.findViewById(R.id.gps_et_phone);
        et_mail = (EditText) v.findViewById(R.id.gps_et_email);
        et_gos_reg_znak = (EditText) v.findViewById(R.id.gps_et_gos_reg_znak);
        et_number_kuzov = (EditText) v.findViewById(R.id.gps_et_number_kuzov);
        et_razreshennaya_massa = (EditText) v.findViewById(R.id.gps_et_razreshennaya_massa);
        et_massa_bez_nagr = (EditText) v.findViewById(R.id.gps_et_massa_bez_nagr);
        et_rd_seria = (EditText) v.findViewById(R.id.gps_et_rd_seria);
        et_rd_nuumber = (EditText) v.findViewById(R.id.gps_et_rd_number);
        et_rd_from = (EditText) v.findViewById(R.id.gps_et_rd_from);
        et_rd_date = (EditText) v.findViewById(R.id.gps_et_rd_date);
        et_rd_marka_s = (EditText) v.findViewById(R.id.gps_et_rd_marka_shin);
        et_rd_probeg = (EditText) v.findViewById(R.id.gps_et_rd_probeg);
        et_rd_vin = (EditText) v.findViewById(R.id.gps_et_rd_VIN);
        et_rd_marka_ts = (EditText) v.findViewById(R.id.gps_et_rd_marka_ts);
        et_rd_model_ts = (EditText) v.findViewById(R.id.gps_et_rd_model_ts);
        et_rd_year = (EditText) v.findViewById(R.id.gps_et_rd_year);
        et_rd_rama = (EditText) v.findViewById(R.id.gps_et_rd_rama);

        type_toplivo = (Spinner) v.findViewById(R.id.sp_type_toplivo);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, mass_toplivo);
        type_toplivo.setAdapter(spinnerArrayAdapter);

        type_tormoz = (Spinner) v.findViewById(R.id.sp_type_tormoz);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, mass_tormoz);
        type_tormoz.setAdapter(spinnerArrayAdapter2);

        type_rd = (Spinner) v.findViewById(R.id.sp_type_r_d);
        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, mass_type_r);
        type_rd.setAdapter(spinnerArrayAdapter3);

        type_category = (Spinner) v.findViewById(R.id.gps_sp_category);
        ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, mass_category);
        type_category.setAdapter(spinnerArrayAdapter4);

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

        rel_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.show();
            }
        });

        rel_l_d = (RelativeLayout) v.findViewById(R.id.rel_l_d);
        lin_l_d = (LinearLayout) v.findViewById(R.id.lin_l_d);
        img_l_d = (ImageView) v.findViewById(R.id.img_l_d);

        rel_l_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lin_l_d.getVisibility() == View.VISIBLE) {
                    lin_l_d.setVisibility(View.GONE);
                    img_l_d.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }
                else {
                    img_l_d.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    lin_l_d.setVisibility(View.VISIBLE);
                }
            }
        });

        rel_a_d = (RelativeLayout) v.findViewById(R.id.rel_a_d);
        lin_a_d = (LinearLayout) v.findViewById(R.id.lin_a_d);
        img_a_d = (ImageView) v.findViewById(R.id.img_a_d);

        rel_a_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lin_a_d.getVisibility() == View.VISIBLE) {
                    lin_a_d.setVisibility(View.GONE);
                    img_a_d.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }
                else {
                    img_a_d.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    lin_a_d.setVisibility(View.VISIBLE);
                }
            }
        });

        rel_r_d = (RelativeLayout) v.findViewById(R.id.rel_r_d);
        lin_r_d = (LinearLayout) v.findViewById(R.id.lin_r_d);
        img_r_d = (ImageView) v.findViewById(R.id.img_r_d);

        rel_r_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lin_r_d.getVisibility() == View.VISIBLE) {
                    lin_r_d.setVisibility(View.GONE);
                    img_r_d.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }
                else {
                    img_r_d.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    lin_r_d.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_pd = (Button) v.findViewById(R.id.gps_btn_pd);
        btn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showDialogPD();
            }
        });

        mRecycler = (RecyclerView) v.findViewById(R.id.to_recycler);
        adapter = new MyAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(adapter);
    }

    private void send() {

        Map<String, String> body = new HashMap();

        String fio = et_fio.getText().toString();
        String city = et_city.getText().toString();
        String phone = et_phone.getText().toString();
        String mail = et_mail.getText().toString();

        String da_gos_nomer = et_gos_reg_znak.getText().toString();
        String da_kuzov = et_number_kuzov.getText().toString();

        String da_razr_max_massa = et_razreshennaya_massa.getText().toString();
        String da_massa_no_gruz = et_massa_bez_nagr.getText().toString();
        String da_type_topl =  mass_toplivo[type_toplivo.getSelectedItemPosition()];
        String da_type_trmz = mass_tormoz[type_tormoz.getSelectedItemPosition()];

        String rd_type = mass_type_r[type_rd.getSelectedItemPosition()];
        String rd_seria = et_rd_seria.getText().toString();
        String rd_number = et_rd_nuumber.getText().toString();
        String rd_from = et_rd_from.getText().toString();
        String rd_date = et_rd_date.getText().toString();
        String rd_marka_shin = et_rd_marka_s.getText().toString();
        String rd_probeg = et_rd_probeg.getText().toString();
        String rd_vin = et_rd_vin.getText().toString();
        String rd_marka_ts = et_rd_marka_ts.getText().toString();
        String rd_model_ts = et_rd_model_ts.getText().toString();
        String category_ts = mass_category[type_category.getSelectedItemPosition()];
        String rd_year = et_rd_year.getText().toString();
        String rd_rama = et_rd_rama.getText().toString();

        body.put("da_gos_nomer", da_gos_nomer);
        body.put("da_kuzov", da_kuzov);
        body.put("da_razr_max_massa", da_razr_max_massa);
        body.put("da_massa_no_gruz", da_massa_no_gruz);
        body.put("da_type_topl", da_type_topl);
        body.put("da_type_trmz", da_type_trmz);
        body.put("rd_type", rd_type);
        body.put("rd_seria", rd_seria);
        body.put("rd_number", rd_number);
        body.put("rd_from", rd_from);
        body.put("rd_date", rd_date);
        body.put("rd_marka_shin", rd_marka_shin);
        body.put("rd_probeg", rd_probeg);
        body.put("rd_vin", rd_vin);
        body.put("rd_marka_ts", rd_marka_ts);
        body.put("rd_model_ts", rd_model_ts);
        body.put("category_ts", category_ts);
        body.put("rd_year", rd_year);
        body.put("rd_rama", rd_rama);

        if(list.size() != 0) {
            Gson gson = new Gson();
            String photos = gson.toJson(list).toString();
            body.put("photos", photos);
        }
//        if(type_toplivo.getSelectedItemPosition() == 0 ){
//            Toast.makeText(v.getContext(), "Выберите тип топлива", Toast.LENGTH_SHORT).show();
////            return;
//        }
//
//        if(da_razr_max_massa.isEmpty()) {
//            Toast.makeText(v.getContext(), "Заполните поле Разрешенная максимальная масса", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(da_kuzov.isEmpty()) {
//            Toast.makeText(v.getContext(), "Заполните поле Кузов №", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(da_gos_nomer.isEmpty()) {
//            Toast.makeText(v.getContext(), "Заполните поле Гос. рег. знак", Toast.LENGTH_SHORT).show();
//            return;
//        }

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



        body.put("fio", fio);
        body.put("city", city);
        body.put("telephone", phone);
        body.put("email", mail);
        progressBar.setVisibility(View.VISIBLE);
        App.getApi().setTO(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody body1 = response.body();
                progressBar.setVisibility(View.GONE);
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
                progressBar.setVisibility(View.GONE);
                Toast.makeText(v.getContext(), "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });

        //sendToServer(fio, city, phone, mail);

    }

    public void setPhoto(Bitmap bitmap){
        mRecycler.setVisibility(View.VISIBLE);
        //Glide.with(v.getContext()).load(bitmap).into(photo);
        //photo.setVisibility(View.VISIBLE);

        Photo photo = new Photo();
        photo.setBitmap(bitmap);
        photo.setBase64(bitmapToBase64(bitmap));

        list.add(photo);

        adapter.notifyDataSetChanged();
    }

    public void  sendToServer(String fio, String city, String  phone, String mail){

        // TODO здесь отправка на сервер
        Map<String, String> body = new HashMap();
        body.put("fio", fio);
        body.put("city", city);
        body.put("telephone", phone);
        body.put("email", mail);

        App.getApi().setTO(body).enqueue(new Callback<ResponseBody>() {
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
                Toast.makeText(v.getContext(), "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        List<Photo> photo;


        MyAdapter(List<Photo> _photo) {
            this.photo = _photo;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_rel_photo, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Glide.with(holder.iv.getContext()).load(photo.get(position).getBitmap()).into(holder.iv);

            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSnackDeletePhoto(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return photo.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView iv, close;
            ViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.img_photo);
                close = (ImageView) itemView.findViewById(R.id.img_close);

            }
        }
    }

    private void showSnackDeletePhoto(final int position) {

        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_photo);

        TextView tv_no = (TextView) dialog.findViewById(R.id.txt_no);
        TextView tv_yes = (TextView) dialog.findViewById(R.id.txt_yes);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                list.remove(position);
                adapter.notifyDataSetChanged();

                if(list.size() == 0) mRecycler.setVisibility(View.GONE);
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

    }

    private String bitmapToBase64(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        String encoded  = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);

        return encoded;
    }

}
