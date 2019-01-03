package com.example.mortrza.myadvertismentdispaly.SETTING;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mortrza.myadvertismentdispaly.R;

public class Setting {

    static SettingData sd;
    int min=16,min2=1;
    int textSize,textSpaceSize;
    Dialog dialog;
    RadioButton nazanin,yekan;

    private static Context context;


    public Setting(Context context){

        this.context = context;

    }

    public void Setting(){
        View alertLayout = LayoutInflater.from(context).inflate(R.layout.alert_setting,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(alertLayout);

        ///////////////////
        final TextView text,size,space;


        text = (TextView) alertLayout.findViewById(R.id.txt_sample);
        size = (TextView) alertLayout.findViewById(R.id.txt_size);
        space = (TextView) alertLayout.findViewById(R.id.txt_space);
        nazanin = (RadioButton) alertLayout.findViewById(R.id.rdio_alert_nazanin);
        yekan = (RadioButton) alertLayout.findViewById(R.id.rdio_alert_byaken);

        sd = new SettingData(context);

        final Typeface tf_yekan = Typeface.createFromAsset(context.getAssets(),"byekan.ttf");
        final Typeface tf_nazanin = Typeface.createFromAsset(context.getAssets(),"nazanin.ttf");



        nazanin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setTypeface(tf_nazanin);
                sd.SetFONT(2);
            }
        });


        yekan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setTypeface(tf_yekan);
                sd.SetFONT(1);
            }
        });


        text.setTextSize(sd.GetTextSize());
        text.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f,  context.getResources().getDisplayMetrics()),sd.GetTextSpaceLine());
        SeekBar seekBar = (SeekBar) alertLayout.findViewById(R.id.seek_setting);
        SeekBar seek_space = (SeekBar) alertLayout.findViewById(R.id.seek_space);
        seekBar.setProgress(sd.GetTextSize());
        size.setText(sd.GetTextSize()+"");
        seekBar.setMax(36);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress+min>36){
                    text.setTextSize(36);
                    textSize=progress;
                    size.setText(progress+"");
                }else {
                    text.setTextSize(progress+min);
                    textSize=progress+min;
                    size.setText(progress+min+"");
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sd.SetTextSize(textSize);
            }
        });
        //////////////////


        seek_space.setProgress(sd.GetTextSpaceLine());
        seek_space.setMax(4);
        space.setText(sd.GetTextSpaceLine()+"");

        seek_space.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress+min2>4){
                    textSpaceSize=progress;
                    text.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f,  context.getResources().getDisplayMetrics()),progress);
                    space.setText(progress+"");

                }else {
                    textSpaceSize=progress+min2;
                    text.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f,  context.getResources().getDisplayMetrics()),progress);
                    space.setText(progress+min2+"");

                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sd.SetTextSpaceLine(textSpaceSize);
            }
        });

        //////////////////
        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setLayout(900, 2000);

    }
}
