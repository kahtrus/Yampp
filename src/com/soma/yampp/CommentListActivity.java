package com.soma.yampp;

import com.soma.synge.R;
import com.soma.util.CommonFunction;
import com.soma.util.ServiceWork;
import com.soma.util.CommonFunction.FragmentCallback;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;

public class CommentListActivity extends Activity{
    String mURL;
    ListView mCommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       
        
        setContentView(R.layout.commnet_list_xml);
        mCommentList=(ListView)findViewById(R.id.listview);
        CommonFunction.sActivityName="CommentList";
        mURL="http://yampp.net/Webservices/advanceSearch?keyword=week";
        methodThatStartsTheAsyncTask() ;
        
    }
    
    
    public void methodThatStartsTheAsyncTask() 
    {

        ServiceWork testAsyncTask = new ServiceWork(mURL,CommentListActivity.this,new FragmentCallback() {

            @Override
            public void onTaskDone() 
            {
                CommentListAdapter adapter=new CommentListAdapter(CommentListActivity.this);
                mCommentList.setAdapter(adapter);
            }
        });testAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
    }
}
