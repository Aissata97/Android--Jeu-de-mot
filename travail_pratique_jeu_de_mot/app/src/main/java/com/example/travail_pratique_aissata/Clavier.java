package com.example.travail_pratique_aissata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.concurrent.Callable;

public class Clavier {

    private static final String touches = "AZERTYUIOP QSDFGHJKL0 -WXCVBNM<0";
    private static final int RowsCount = 3;
    private static final int ColumnsCount = 10;

    public static GridLayout creer(Context ctx, final KeyPressHandler eventHandler){

        GridLayout grid = new GridLayout(ctx);
        grid.setRowCount(RowsCount);
        grid.setColumnCount(ColumnsCount);

        grid.setLayoutParams(new LinearLayout.LayoutParams(200, GridLayout.LayoutParams.MATCH_PARENT, 2f));
        grid.getLayoutParams().width = GridLayout.LayoutParams.MATCH_PARENT;
//        grid.getLayoutParams().height = GridLayout.LayoutParams.MATCH_PARENT;

        int len = touches.length();
        int row = 0;
        int col = 0;
        for(int i = 0; i < len; i++){
            final char c = touches.charAt(i);
            if(c == ' '){
                col = 0;
                row++;
                continue;
            }
            final Button btn = new Button(ctx);
            if(c != '0'){
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonPressFeedback(btn);
                        eventHandler.on(c);
                    }
                });
                btn.setText(c + "");
            }
            btn.setTextColor(0xffffffff);
            btn.setBackgroundColor(0);
            btn.setTextSize(24f);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                    GridLayout.spec(row, 1f),
                    GridLayout.spec(col, 1f));

            layoutParams.width = 20;
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.setGravity(Gravity.FILL);
            if(row > 0){
                int offset = 30;
                layoutParams.leftMargin = offset;
                layoutParams.rightMargin = -offset;
            }
//            layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            grid.addView(btn, layoutParams);
            col++;
        }

        return  grid;
    }

    private static void buttonPressFeedback(final Button btn){
        btn.setBackgroundColor(0xffffffff);
        btn.setTextColor(0xFFFD956F);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        btn.setBackgroundColor(0);
                        btn.setTextColor(0xffffffff);
                    }
                },
                200);
    }

    public static abstract class KeyPressHandler{
        public abstract void on(char c);
    }

}
