package com.example.fontreszie;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.ljx.view.FontResizeView;
import com.ljx.view.FontResizeView.OnFontChangeListener;

import java.util.List;

import static com.example.fontreszie.SettingsStorageHelper.FONT_SIZE;

public class SetFontSizeMainActivity extends AppCompatActivity {

//    private TextView mTextView;
//    private TextView mTextView2;
    public final static String   TEXT="安全邮件";
    public final static String   TEXT_PREVIEW="字体设置预览";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_font_resize);


        final RecyclerView rv = findViewById(R.id.rv_main);
        /**
         * DividerItemDecoration是android.support.v7.widget包中的
         * new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); 这样写有一个默认的分割线 第一种分割线
         */
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        /**
         * 第二种分割线 自定义 divider_bg
         */
     //   dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rv.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        final RvAdapter rvAdapter = new RvAdapter();
        rv.setAdapter(rvAdapter);


//        mTextView = findViewById(R.id.text_view);
//        mTextView2 =findViewById(R.id.text_view2);

        FontResizeView fontResizeView = findViewById(R.id.font_resize_view);

        fontResizeView.setOnFontChangeListener(new OnFontChangeListener() {
            @Override
            public void onFontChange(float fontSize) {
                SettingsStorageHelper.put(FONT_SIZE,Float.toString(fontSize));
                rvAdapter.notifyDataSetChanged();

//                mTextView.setTextSize(fontSize);
//                mTextView.setText("当前字体大小:" + fontSize + "sp");
            }
        });
        String fontSize = SettingsStorageHelper.get(FONT_SIZE,Float.toString(15));
         float fontSizeFl = Float.parseFloat(fontSize);
        fontResizeView.setFontSize(fontSizeFl);
//        mTextView.setTextSize(fontSizeFl);
//         mTextView.setText("当前字体大小:" + fontSize + "sp");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    class RvAdapter extends RecyclerView.Adapter<RvAdapter.VH> {
        List<RvAdapter.VH> items;

        public RvAdapter(  List<RvAdapter.VH> items) {
            this.items = items;
        }

        public RvAdapter(  ) {

        }


        @Override
        public int getItemCount() {
            return 2;
        }


        @Override
        public VH onCreateViewHolder( ViewGroup parent, int viewType) {
            Log.i("SetFontSizeMainActivity", "onCreateViewHolder");
            return new VH(LayoutInflater.from(SetFontSizeMainActivity.this).inflate(R.layout.item_fontresize_list, parent, false));
        }

        @Override
        public void onBindViewHolder( final VH holder, final int position) {

            holder.textSubject.setText(TEXT);
            String fontSizeStr = SettingsStorageHelper.get(FONT_SIZE,Float.toString(15));
            float fontSize =Float.parseFloat(fontSizeStr);
            holder.textSubject.setTextSize(fontSize);
            holder.textContact.setText(TEXT_PREVIEW);
            holder.textContact.setTextSize(fontSize);
        }



        class VH extends RecyclerView.ViewHolder {
            private final TextView textDate;
            private final TextView textSubject;
            private final TextView textContact;



            public VH(View itemView) {
                super(itemView);
                textDate = itemView.findViewById(R.id.fontresize_time);
                textSubject = itemView.findViewById(R.id.fontresize_subject);
                textContact = itemView.findViewById(R.id.fontresize_abstract);


            }
        }
    }


//  <!--字体大小 及倍数-->
//    <!--0.875=14/16 -->
//    <dimen name="sp_small">14sp</dimen>
//    <!--1  标准-->
//    <dimen name="sp_stander">16sp</dimen>
//    <!--1.125=18/16-->
//    <dimen name="sp_big1">18sp</dimen>
//    <!--1.25=20/16-->
//    <dimen name="sp_big2">20sp</dimen>
//    <!--1.375=22/16-->
//    <dimen name="sp_big3">22sp</dimen>
//    <!--1.5=24/16-->
//    <dimen name="sp_big4">24sp</dimen>
    @Override
    public Resources getResources() {
        Log.i("SetFontSizeMainActivity","getResources");
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();

        config.fontScale = 1f;

        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
