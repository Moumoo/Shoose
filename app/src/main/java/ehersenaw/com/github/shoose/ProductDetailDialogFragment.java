package ehersenaw.com.github.shoose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductDetailDialogFragment extends DialogFragment{
    Bitmap bitmap;
    String shopUrl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail_dialog_fragment, container);
        Button closeBtn;
        Button goToBuyBtn;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        TextView detailProduct_SN = (TextView) view.findViewById(R.id.detailProduct_SN);
        TextView detailName = (TextView) view.findViewById(R.id.detailName);
        TextView detailBrand = (TextView) view.findViewById(R.id.detailBrand);
        TextView detailPrice = (TextView) view.findViewById(R.id.detailPrice);
        TextView detailType = (TextView) view.findViewById(R.id.detailType);
        TextView detailPoint = (TextView) view.findViewById(R.id.detailPoint);
        ImageView detailImage = (ImageView) view.findViewById(R.id.product_detail_img);


        Bundle bundle = getArguments();
        if(bundle != null){
            final Product shoose = (Product) bundle.getParcelable("shoose");
            detailProduct_SN.setText("품번 : "+Integer.toString(shoose.pid));
            detailName.setText("품명 : "+shoose.pname);
            detailPrice.setText("가격 : "+Integer.toString(shoose.price));
            detailBrand.setText("상표 : "+shoose.brand);
            detailType.setText("분류 : "+shoose.type);
            detailPoint.setText("평점 : "+Double.toString(shoose.point));
            shopUrl = shoose.link;

            Thread imgThread = new Thread(){
                @Override
                public void run() {
                    try {
                        URL url = new URL(shoose.img_url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is =conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            imgThread.start();

            try {
                imgThread.join();
                detailImage.setImageBitmap(bitmap);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        closeBtn = (Button) view.findViewById(R.id.dialogClose);
        closeBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        goToBuyBtn = (Button) view.findViewById(R.id.goToBuy);
        goToBuyBtn.setOnClickListener(new Button.OnClickListener(){
//"https://stockx.imgix.net/Nike-Air-Max-1-97-Sean-Wotherspoon-NA-Product.jpg?fit=fill&bg=FFFFFF&w=300&h=214&auto=format,compress&trim=color&q=90&dpr=2&updated_at=1538080256"
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopUrl));
                startActivity(browserIntent);
            }

        });

        return view;
    }
}
