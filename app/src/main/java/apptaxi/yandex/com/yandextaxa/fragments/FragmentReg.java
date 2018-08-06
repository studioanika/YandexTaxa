package apptaxi.yandex.com.yandextaxa.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apptaxi.yandex.com.yandextaxa.MainActivity;
import apptaxi.yandex.com.yandextaxa.R;
import apptaxi.yandex.com.yandextaxa.enums.TypePhotoREG;
import apptaxi.yandex.com.yandextaxa.model.Photo;
import apptaxi.yandex.com.yandextaxa.network.App;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class FragmentReg extends Fragment {

    View v;
    Button btn_pd;

    EditText et_fio, et_city, et_phone, et_mail, et_phone_c, et_comment, et_frienf,
            et_money, et_inn, et_ogrnip;
    CheckBox ch, ch_off, ch_creslo, ch_buster;
    Button btn;
    ImageView share, img_l_d, img_ip;

    RelativeLayout rel_take_photo, rel_take_photo_po, rel_take_photo_vu,
                   rel_take_photo_p, rel_take_photo_l, rel_l_d, rel_ip;

    LinearLayout lin_l_d, lin_ip;

    ProgressBar progressBar;

    Context context;

    public FragmentReg(Context context) {
        this.context = context;
    }

    RecyclerView mRecycler_sts, mRecycler_po, mRecycler_vu, mRecycler_p, mRecycler_l;
    MyAdapter adapter_sts, adapter_po, adapter_vu, adapter_p, adapter_l;
    List<Photo> listSTS = new ArrayList<>();
    List<Photo> listPO = new ArrayList<>();
    List<Photo> listVU = new ArrayList<>();
    List<Photo> listP = new ArrayList<>();
    List<Photo> listL = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_reg, container, false);
        intitUI();
        return v;
    }

    private void intitUI() {

        progressBar = (ProgressBar) v.findViewById(R.id.prgrs);
        ch_creslo = (CheckBox) v.findViewById(R.id.checkBox2);
        ch_buster = (CheckBox) v.findViewById(R.id.checkBox);
        et_comment = (EditText) v.findViewById(R.id.gps_et_comment);
        et_fio = (EditText) v.findViewById(R.id.gps_et_fio);
        et_city = (EditText) v.findViewById(R.id.gps_et_city);
        et_phone = (EditText) v.findViewById(R.id.gps_et_phone);
        et_phone_c = (EditText) v.findViewById(R.id.gps_et_phone_contact);
        et_mail = (EditText) v.findViewById(R.id.gps_et_email);
        et_frienf = (EditText) v.findViewById(R.id.gps_et_friend);
        et_money = (EditText) v.findViewById(R.id.gps_et_money);

        ch = (CheckBox) v.findViewById(R.id.gps_check);
        btn = (Button) v.findViewById(R.id.gps_send);
        share = (ImageView) v.findViewById(R.id.img_share);

        et_inn = (EditText) v.findViewById(R.id.gps_et_ip_inn);
        et_ogrnip = (EditText) v.findViewById(R.id.gps_et_ip_orgpnip);

        ch_off = (CheckBox) v.findViewById(R.id.gps_chec_of);
        ch_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(ch.isChecked()){
                        btn.setEnabled(true);
                        btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_active));
                    }else {
                        btn.setEnabled(false);
                        btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_no_active));
                    }
                }else {
                    btn.setEnabled(false);
                    btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_no_active));
                }
            }
        });
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(ch_off.isChecked()){
                        btn.setEnabled(true);
                        btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_active));
                    }
                    else{
                        btn.setEnabled(false);
                        btn.setBackground(v.getContext().getResources().getDrawable(R.drawable.btn_no_active));
                    }
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

        rel_ip = (RelativeLayout) v.findViewById(R.id.rel_l_ip);
        lin_ip = (LinearLayout) v.findViewById(R.id.lin_l_ip);
        img_ip = (ImageView) v.findViewById(R.id.img_l_ip);

        rel_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lin_ip.getVisibility() == View.VISIBLE) {
                    lin_ip.setVisibility(View.GONE);
                    img_ip.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }
                else {
                    img_ip.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                    lin_ip.setVisibility(View.VISIBLE);
                }
            }
        });



        rel_take_photo = (RelativeLayout) v.findViewById(R.id.gps_rel_photo);
        rel_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showReg(TypePhotoREG.STS);
            }
        });
//
        try {
            mRecycler_sts = (RecyclerView) v.findViewById(R.id.rec_sts);
            adapter_sts = new MyAdapter(listSTS, TypePhotoREG.STS);
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler_sts.setLayoutManager(layoutManager);
            mRecycler_sts.setAdapter(adapter_sts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rel_take_photo_po = (RelativeLayout) v.findViewById(R.id.gps_rel_photo_po);
        rel_take_photo_po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showReg(TypePhotoREG.POLIS);
            }
        });
//
        try {
            mRecycler_po = (RecyclerView) v.findViewById(R.id.rec_po);
            adapter_po = new MyAdapter(listPO, TypePhotoREG.POLIS);
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler_po.setLayoutManager(layoutManager);
            mRecycler_po.setAdapter(adapter_po);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rel_take_photo_vu = (RelativeLayout) v.findViewById(R.id.gps_rel_photo_vy);
        rel_take_photo_vu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showReg(TypePhotoREG.VY);
            }
        });
//
        try {
            mRecycler_vu = (RecyclerView) v.findViewById(R.id.rec_vy);
            adapter_vu = new MyAdapter(listVU, TypePhotoREG.VY);
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler_vu.setLayoutManager(layoutManager);
            mRecycler_vu.setAdapter(adapter_vu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rel_take_photo_p = (RelativeLayout) v.findViewById(R.id.gps_rel_photo_p);
        rel_take_photo_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showReg(TypePhotoREG.Passport);
            }
        });
//
        try {
            mRecycler_p = (RecyclerView) v.findViewById(R.id.rec_p);
            adapter_p = new MyAdapter(listP, TypePhotoREG.Passport);
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler_p.setLayoutManager(layoutManager);
            mRecycler_p.setAdapter(adapter_p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rel_take_photo_l = (RelativeLayout) v.findViewById(R.id.gps_rel_photo_l);
        rel_take_photo_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showReg(TypePhotoREG.License);
            }
        });
//
        try {
            mRecycler_l = (RecyclerView) v.findViewById(R.id.rec_l);
            adapter_l = new MyAdapter(listL, TypePhotoREG.License);
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecycler_l.setLayoutManager(layoutManager);
            mRecycler_l.setAdapter(adapter_l);
        } catch (Exception e) {
            e.printStackTrace();
        }


        btn_pd = (Button) v.findViewById(R.id.gps_btn_pd);
        btn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showDialogPD();
            }
        });

        Button bbtn = (Button) v.findViewById(R.id.btn_off_show);
        bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.showDialogOfferta();
            }
        });

    }

    private void send() {

        Map<String, String> body = new HashMap();

        String fio = et_fio.getText().toString();
        String city = et_city.getText().toString();
        String phone = et_phone.getText().toString();
        String phone_c = et_phone_c.getText().toString();
        String mail = et_mail.getText().toString();
        String comment = et_comment.getText().toString();
        String friend = et_frienf.getText().toString();
        String money = et_money.getText().toString();
        String inn_s = et_inn.getText().toString();
        String ogr = et_ogrnip.getText().toString();
        Gson gson = new Gson();

        if(listSTS.size() < 2) {
            Toast.makeText(v.getContext(), "Добавьте 2 фотографии СТС", Toast.LENGTH_SHORT).show();
            return;
        }else {
            String sts = gson.toJson(listSTS).toString();
            body.put("sts", sts);
        }

        if(listPO.size() == 0) {
            Toast.makeText(v.getContext(), "Добавьте 2 фотографии Полис ОСАГО", Toast.LENGTH_SHORT).show();
            return;
        }else {
            String po = gson.toJson(listPO).toString();
            body.put("po", po);
        }

        if(listVU.size() < 2) {
            Toast.makeText(v.getContext(), "Добавьте 2 фотографии Водительского удостоверения", Toast.LENGTH_SHORT).show();
            return;
        }else {
            String vu = gson.toJson(listVU).toString();
            body.put("vu", vu);
        }

        if(listP.size() < 2) {
            Toast.makeText(v.getContext(), "Добавьте фотографии пасспорта", Toast.LENGTH_SHORT).show();
            return;
        }else {
            String p = gson.toJson(listP).toString();
            body.put("p", p);
        }

        if(listL.size()>0){
            String l = gson.toJson(listL).toString();
            body.put("l", l);
        }

        if(fio.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле ФИО", Toast.LENGTH_SHORT).show();
            return;
        }

        if(city.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле Город", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле Телефон для регистрации", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone_c.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле Телефон для контактов", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mail.isEmpty()) {
            Toast.makeText(v.getContext(), "Заполните поле E-mail", Toast.LENGTH_SHORT).show();
            return;
        }


        body.put("fio", fio);
        body.put("city", city);
        body.put("telephone", phone);
        body.put("telephone_c", phone_c);
        body.put("email", mail);
        body.put("comment", comment);
        body.put("friend", friend);
        body.put("money", money);
        body.put("inn", inn_s);
        body.put("ogr", ogr);
        if(ch_creslo.isChecked()) body.put("creslo", "true");
        if(ch_buster.isChecked()) body.put("buster", "true");

        progressBar.setVisibility(View.VISIBLE);
        App.getApi().setREG(body).enqueue(new Callback<ResponseBody>() {
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
                String dt = t.toString();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(v.getContext(), "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void setPhotoSTS(Bitmap bitmap, TypePhotoREG type){

        //Glide.with(v.getContext()).load(bitmap).into(photo);
        //photo.setVisibility(View.VISIBLE);

        Photo photo = new Photo();
        photo.setBitmap(bitmap);
        photo.setBase64(bitmapToBase64(bitmap));

        if(type == TypePhotoREG.STS){
            mRecycler_sts.setVisibility(View.VISIBLE);
            listSTS.add(photo);
            adapter_sts.notifyDataSetChanged();
        }else if(type == TypePhotoREG.POLIS){
            mRecycler_po.setVisibility(View.VISIBLE);
            listPO.add(photo);
            adapter_po.notifyDataSetChanged();
        }else if(type == TypePhotoREG.VY){
            mRecycler_vu.setVisibility(View.VISIBLE);
            listVU.add(photo);
            adapter_vu.notifyDataSetChanged();
        }else if(type == TypePhotoREG.Passport){
            mRecycler_p.setVisibility(View.VISIBLE);
            listP.add(photo);
            adapter_p.notifyDataSetChanged();
        }else if(type == TypePhotoREG.License){
            mRecycler_l.setVisibility(View.VISIBLE);
            listL.add(photo);
            adapter_l.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        List<Photo> photo;
        TypePhotoREG typePhotoREG;

        MyAdapter(List<Photo> _photo, TypePhotoREG _typePhotoREG) {
            this.photo = _photo;
            this.typePhotoREG = _typePhotoREG;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_rel_photo, parent, false);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            Glide.with(holder.iv.getContext()).load(photo.get(position).getBitmap()).into(holder.iv);

            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSnackDeletePhoto(position, typePhotoREG);
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

    private void showSnackDeletePhoto(final int position, final TypePhotoREG type) {

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
                if (type == TypePhotoREG.STS) {
                    listSTS.remove(position);
                    adapter_sts.notifyDataSetChanged();
                    if(listSTS.size() == 0) mRecycler_sts.setVisibility(View.GONE);
                }
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
