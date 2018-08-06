package apptaxi.yandex.com.yandextaxa.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apptaxi.yandex.com.yandextaxa.R;
import apptaxi.yandex.com.yandextaxa.enums.TypePhotoREG;

public class FragmentInfo extends Fragment {

    View v;
    ImageView img_bg;

    ImageView share;

    CardView card1,card2, card3, card4, card5, card6, card7, card8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_info, container, false);
        intitUI();
        return v;
    }

    private void intitUI() {

        img_bg = (ImageView) v.findViewById(R.id.info_img_bg);
        Glide.with(v.getContext()).load(R.drawable.bg).into(img_bg);

        share = (ImageView) v.findViewById(R.id.img_share);

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

        card1 = (CardView) v.findViewById(R.id.info_card_1);
        card2 = (CardView) v.findViewById(R.id.info_card_2);
        card3 = (CardView) v.findViewById(R.id.info_card_3);
        card4 = (CardView) v.findViewById(R.id.info_card_4);
        card5 = (CardView) v.findViewById(R.id.info_card_5);
        card6 = (CardView) v.findViewById(R.id.info_card_6);
        card7 = (CardView) v.findViewById(R.id.info_card_7);
        card8 = (CardView) v.findViewById(R.id.info_card_8);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "ru.yandex.taximeter")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "ru.yandex.taximeter")));
                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://driver.yandex/%D1%83%D1%80%D0%BE%D0%BA-%E2%84%964/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://driver.yandex/%D1%83%D1%80%D0%BE%D0%BA-%E2%84%961/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogOpenMoney();
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://driver.yandex/faq/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCheck();
            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogInstr();

            }
        });


    }

    private void showDialogInstr() {

        try {
            final Dialog dialog = new Dialog(v.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dealog_instr);

            TextView tv_no = (TextView) dialog.findViewById(R.id.add);
            String txt_1_1 = "<font size='18'><b>Автоматические моментальные выплата за безналичные заказы!</b></font><br><br>Оплата за безналичные заказы и субсидии (доплаты и бонусы) производятся по <font color='red'>Яндекс</font><b> ЕЖЕДНЕВНО</b>";
            TextView tv_1_1 = (TextView) dialog.findViewById(R.id.txt_1_1);
            tv_1_1.setText(Html.fromHtml(txt_1_1), TextView.BufferType.SPANNABLE);

            tv_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            ImageView img_2 = (ImageView) dialog.findViewById(R.id.img_2);
            Glide.with(img_2.getContext()).load(R.drawable.bg_2).into(img_2);

            String txt_2_2 = "Наша компания подключилась к проекту TANKER.<br><br>Теперь Вы можете оплачивать заправку на АЗС используя свой внутренний счет в Яндекс.Таксометр!<br><br>То есть теперь можно брать все заказы по безналу и использовать эти деньги сразу для того,\n" +
                    "что бы заплатить за бензин через это приложение (Танкер).<br><br>Установите приложение <a href = 'https://play.google.com/store/apps/details?id=ru.tankerapp.android'>TANKER<a> из Google Play\n" +
                    "или пройдите по ссылке:";
            TextView tv_2_2 = (TextView) dialog.findViewById(R.id.text2_2);
            tv_2_2.setText(Html.fromHtml(txt_2_2), TextView.BufferType.SPANNABLE);
            tv_2_2.setMovementMethod(LinkMovementMethod.getInstance());

            String driver_t = "Для успешного старта необходимо ознакомится с правилами и стандартами качества в Яндекс Такси. Вся исчерпывающая информация есть в базе знаний по ссылке <a href='https://driver.yandex/'>https://driver.yandex/</a> Здесь Вы найдете видеоролики и описания, как правильно вести работу, что бы добиться максимальных результатов.";
            TextView tv_driver_t = (TextView) dialog.findViewById(R.id.tv_driver_t);
            tv_driver_t.setText(Html.fromHtml(driver_t), TextView.BufferType.SPANNABLE);
            tv_driver_t.setMovementMethod(LinkMovementMethod.getInstance());

            final TextView tv_href = (TextView) dialog.findViewById(R.id.href_tanker);
            tv_href.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = tv_href.getText().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
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

    private void showDialogOpenMoney() {

        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_open_money);

        TextView tv_y = (TextView) dialog.findViewById(R.id.tv_yandex);

        tv_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                String url = "https://money.yandex.ru";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView tv_q = (TextView) dialog.findViewById(R.id.tv_quvi);

        tv_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                String url = "https://qiwi.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView tv_w = (TextView) dialog.findViewById(R.id.tv_webmoney);

        tv_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                String url = "https://webmoney.ru/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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

    private void showDialogCheck() {

        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_check_dk);

        TextView tv_close = (TextView) dialog.findViewById(R.id.tv_close);
        TextView tv_y = (TextView) dialog.findViewById(R.id.tv_y);
        TextView tv_q = (TextView) dialog.findViewById(R.id.tv_q);
        TextView tv_w = (TextView) dialog.findViewById(R.id.tv_w);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final WebView webView = (WebView) dialog.findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            public void onPageFinished(WebView view, String url) {

                webView.setVisibility(View.VISIBLE);


            }
        });

        webView.loadUrl("http://eaisto.info");


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        dialog.show();

    }



}
