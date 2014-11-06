package com.soma.yampp;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.soma.synge.R;
import com.soma.util.CommonFunction;



public class MapInfoViewFactory extends MainPageDrawerAcitivity
{
    private static int a;
    static View view;
static ListView mCommentList;

    @SuppressLint("NewApi")
    public static View generateHelperInfoView(Context context) throws Exception {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.place_list_view, null);
        mCommentList=(ListView)view.findViewById(R.id.listview);
        CommentListAdapter adapter=new CommentListAdapter(context);
        mCommentList.setAdapter(adapter);
        

        return view;


    }

}
