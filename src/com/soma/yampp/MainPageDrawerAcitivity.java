package com.soma.yampp;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soma.synge.R;
import com.soma.util.CommonFunction;
import com.soma.util.CommonFunction.FragmentCallback;
import com.soma.util.ServiceWork;

public class MainPageDrawerAcitivity extends Activity implements OnClickListener
{
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    RelativeLayout mBottomSlider;
    public static int sFlag=0,sFlag2=0,onMapCounter=0;;
    public static String mURL;
    Animation mAnimationRight,mANimationLeft,mAnimation;
    Typeface typeface;
    TextView mUserNameTxt;
    ImageButton mTopSLiderButton,mMiddleSliderButton,mBottomSLiderButton,mSliderLeftButtonTop,mSliderLeftButtonBottom;
    LinearLayout mTopSliderLayout,mBottomSliderLayout,mMiddleSliderLayout,mEmoticonLayout,mLocationLayout;;
    Float x1,x2,y1,y2;
    ImageView mMonekey1,mMonekey2,mMonekey3,mMonekey4,mMonekey5,mMonekey6,mMonekey7,mMonekey8,mNextPage;
    ImageView mDownArrow,mUserImage,mEmoticon,mSendComment,mSearchButton,mClock,mShareIcon,mMenuIcon;
    EditText mComments,mSearchTxt,mLocation;;
    ScrollView mScrollHori;
    Button mOneHour,mOneWeek,mOneMonth;
    public static int sLocPosition=0;
    public static String sMonkey="0",sSpinnerText,sCommentText; 
    ProgressDialog progressBar;
    ProgressBar mProgress;
    // GPS Location
    GPSTracker gps;
    // Google Places
    GooglePlaces googlePlaces;
    // Places List
    PlacesList nearPlaces;
    Marker marker;
    private GoogleMap map;
    public static Bitmap mProfilePIc;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_page_with_three_drawer);
        typeface=Typeface.createFromAsset(getAssets(), "Semplicita-light.ttf");
        prefs = PreferenceManager.getDefaultSharedPreferences(MainPageDrawerAcitivity.this); //initilize the shared preferences
        prefsEditor=prefs.edit();
        mAnimationRight = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
        mANimationLeft  = AnimationUtils.loadAnimation(this, R.anim.push_left_out);

        mScrollHori=(ScrollView)findViewById(R.id.mScroll);
        mProgress=(ProgressBar)findViewById(R.id.progressBar1);
        mUserNameTxt=(TextView)findViewById(R.id.mUserName);

        mBottomSlider=(RelativeLayout)findViewById(R.id.bottomSLider);

        mTopSLiderButton=(ImageButton)findViewById(R.id.topSliderButton);
        mMiddleSliderButton=(ImageButton)findViewById(R.id.middleSliderButton);
        mBottomSLiderButton=(ImageButton)findViewById(R.id.bottomSliderButton);
        mSliderLeftButtonTop=(ImageButton)findViewById(R.id.mSLiderLeftButton);
        mEmoticon=(ImageView)findViewById(R.id.mEmoticon);
        mUserImage=(ImageView)findViewById(R.id.mUserImage);
        mMonekey1=(ImageView)findViewById(R.id.monkey1);
        mMonekey2=(ImageView)findViewById(R.id.monkey2);
        mMonekey3=(ImageView)findViewById(R.id.monkey3);
        mMonekey4=(ImageView)findViewById(R.id.monkey4);
        mMonekey5=(ImageView)findViewById(R.id.monkey5);
        mSendComment=(ImageView)findViewById(R.id.mSendComment);
        mSearchButton=(ImageView)findViewById(R.id.searchIcon);
        mDownArrow=(ImageView)findViewById(R.id.mDownArrow);
        mClock=(ImageView)findViewById(R.id.mClock);
        mShareIcon=(ImageView)findViewById(R.id.mShareIcon);
        mMenuIcon=(ImageView)findViewById(R.id.mMenu);

        mTopSliderLayout=(LinearLayout)findViewById(R.id.searchTab);
        mBottomSliderLayout=(LinearLayout)findViewById(R.id.below);
        mLocationLayout=(LinearLayout)findViewById(R.id.locationLayout);
        //      mEmoticonLayout=(LinearLayout)findViewById(R.id.mEmoticonLayout);

        mLocation=(EditText)findViewById(R.id.setLocation);
        mComments=(EditText)findViewById(R.id.mComments);
        mSearchTxt=(EditText)findViewById(R.id.mSearch);

        mOneHour=(Button)findViewById(R.id.mOneHour);
        mOneMonth=(Button)findViewById(R.id.mOneMonth);
        mOneWeek=(Button)findViewById(R.id.mOneWeek);


        mUserNameTxt.setTypeface(typeface);
        mLocation.setTypeface(typeface);
        mComments.setTypeface(typeface);
        mSearchTxt.setTypeface(typeface);
        mOneHour.setTypeface(typeface);
        mOneMonth.setTypeface(typeface);
        mOneWeek.setTypeface(typeface);

        mUserNameTxt.setText(CommonFunction.sUserInfo.get(0).get_UserName());

        mAnimation=AnimationUtils.loadAnimation(MainPageDrawerAcitivity.this,R.anim.fade_in);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mEmoticon.startAnimation(mAnimation);

        mTopSLiderButton.setOnClickListener(this);
        mMiddleSliderButton.setOnClickListener(this);
        mBottomSLiderButton.setOnClickListener(this);
        mSliderLeftButtonTop.setOnClickListener(this);
        mEmoticon.setOnClickListener(this);
        mMonekey1.setOnClickListener(this);
        mMonekey2.setOnClickListener(this);
        mMonekey3.setOnClickListener(this);
        mMonekey4.setOnClickListener(this);
        mMonekey5.setOnClickListener(this);
        mSendComment.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mOneHour.setOnClickListener(this);
        mOneMonth.setOnClickListener(this);
        mOneWeek.setOnClickListener(this);
        mDownArrow.setOnClickListener(this);
        mClock.setOnClickListener(this);
        mShareIcon.setOnClickListener(this);
        mMenuIcon.setOnClickListener(this);
        mBottomSliderLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent touchevent) {
                // TODO Auto-generated method stub

                switch (touchevent.getAction())

                {
                    // when user first touches the screen we get x and y coordinate
                    case MotionEvent.ACTION_DOWN:
                    {
                        x1 = touchevent.getX();
                        y1 = touchevent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        x2 = touchevent.getX();
                        y2 = touchevent.getY(); 

                        if (x1 > x2)
                        {
                            onMapCounter--;
                            mScrollHori.setVisibility(View.GONE);
                            mBottomSlider.startAnimation(mANimationLeft);
                            mBottomSlider.setVisibility(View.GONE);
                        }
                        break;
                    }
                }  
                return true;
            }
        });

        mTopSliderLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent touchevent) {
                // TODO Auto-generated method stub

                switch (touchevent.getAction())

                {




                    // when user first touches the screen we get x and y coordinate
                    case MotionEvent.ACTION_DOWN:
                    {
                        x1 = touchevent.getX();
                        y1 = touchevent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        x2 = touchevent.getX();
                        y2 = touchevent.getY(); 

                        if (x1 > x2)
                        {
                            mScrollHori.setVisibility(View.GONE);
                            mBottomSlider.startAnimation(mANimationLeft);
                            mBottomSlider.setVisibility(View.GONE);
                        }
                        break;
                    }
                }  
                return true;
            }
        });


        //creating GPS Class object
        gps = new GPSTracker(this);
        // check if GPS location can get


        GetDownload task = new GetDownload();
        // Execute the task
        task.execute(new String[] { CommonFunction.Image_Url+CommonFunction.sUserInfo.get(0).get_Photo() });

        try{
            if (map==null)
            {
                map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap(); 
                map.getUiSettings().setCompassEnabled(false);
                map.getUiSettings().setZoomControlsEnabled(false);
            }
        }
        catch(Exception ex)
        {
            Log.d("exception",""+ex);
        }

        if (gps.canGetLocation()) 
        {
            BitmapDrawable bd=(BitmapDrawable) getResources().getDrawable(R.drawable.marker);;
            Bitmap b=bd.getBitmap();
            Bitmap bhalfsize=Bitmap.createScaledBitmap(b, b.getWidth()/2,b.getHeight()/2, false);
            LatLng pos1=new LatLng(gps.latitude, gps.longitude);
            marker =map.addMarker(new MarkerOptions().icon(
                    BitmapDescriptorFactory
                    .fromBitmap(bhalfsize))
                    .position(pos1));
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos1, 14));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos1, 15));
            ListViewPlacesList.slatitude=gps.getLatitude();
            ListViewPlacesList.sLongitude=gps.getLongitude();
            ServiceWork.businessSearchData.clear();
            CommonFunction.sActivityName="placeList";
            mURL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+gps.getLatitude()+","+gps.getLongitude()+"&radius=1500&key=AIzaSyBDbHzEkRUXsrf5g-7Yb_sJq40GzGbtyiI";
            methodWillStartsTheAsyncTask() ;
            prefsEditor.putString("saveLatitude",""+gps.getLatitude());
            prefsEditor.putString("saveLongitude",""+gps.getLongitude());
            prefsEditor.commit();
        } 
        else 
        {
            return;
        }

        // Setting a custom info window adapter for the google map       
        map.setInfoWindowAdapter(new InfoWindowAdapter() 
        {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {               
                return null;
            }           

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout_xml
                final View view = getLayoutInflater().inflate(R.layout.info_window_layout_xml, null);

                TextView mCommentText = (TextView) view.findViewById(R.id.mCommentText);
                TextView mAddress = (TextView) view.findViewById(R.id.mAddress);
                ImageView mSmilingMonkey=(ImageView)view.findViewById(R.id.mSmilingMoneky);
                TextView mMinimizeMarker=(TextView)view.findViewById(R.id.minimizeMarker);
                String id=marker.getTitle();



                if(id!=null)
                {
                    int pos=Integer.parseInt(id);
                    Log.d("Marker pos",""+marker.getPosition());
                    Log.d("your position is",""+id);
                    String txt="<font>\"</font>"+CommonFunction.sSearchYammp.get(pos).get_comment()+"<font>\"</font>";
                    mCommentText.setText(Html.fromHtml(txt));

                    String text = "<font color=#ffffff>"+CommonFunction.sSearchYammp.get(pos).get_Username()+"</font>"+"@"+CommonFunction.sSearchYammp.get(pos).get_location();
                    mAddress.setText(Html.fromHtml(text));

                    if(CommonFunction.sSearchYammp.get(pos).get_monkey_icon().equalsIgnoreCase("monkey_1"))
                    {
                        mSmilingMonkey.setImageResource(R.drawable.monkey_1);
                    }
                    if(CommonFunction.sSearchYammp.get(pos).get_monkey_icon().equalsIgnoreCase("monkey_2"))
                    {
                        mSmilingMonkey.setImageResource(R.drawable.monkey_2);
                    }
                    if(CommonFunction.sSearchYammp.get(pos).get_monkey_icon().equalsIgnoreCase("monkey_3"))
                    {
                        mSmilingMonkey.setImageResource(R.drawable.monkey_3);
                    }
                    if(CommonFunction.sSearchYammp.get(pos).get_monkey_icon().equalsIgnoreCase("monkey_4"))
                    {
                        mSmilingMonkey.setImageResource(R.drawable.monkey_4);
                    }
                    if(CommonFunction.sSearchYammp.get(pos).get_monkey_icon().equalsIgnoreCase("monkey_5"))
                    {
                        mSmilingMonkey.setImageResource(R.drawable.monkey_5);
                    }
                }


                return view;

            }

        }); 


        map.setOnMapLongClickListener(new OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng pos) {
                // TODO Auto-generated method stub
                Double lat=pos.latitude;
                Double longi=pos.longitude;
                //                CommonFunction.sActivityName="GeoCode";
                //                mURL="https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+longi+"&key=AIzaSyBDbHzEkRUXsrf5g-7Yb_sJq40GzGbtyiI";
                //                methodThatStartsTheAsyncTask();
                ServiceWork.businessSearchData.clear();
                CommonFunction.sActivityName="placeList";
                mURL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+longi+"&radius=1500&key=AIzaSyBDbHzEkRUXsrf5g-7Yb_sJq40GzGbtyiI";
                methodWillStartsTheAsyncTask() ;
            }
        });

        map.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng pos) 
            {
                if(mLocationLayout.getVisibility()== View.VISIBLE)
                {
                    mLocationLayout.startAnimation(mANimationLeft);
                    mLocationLayout.setVisibility(View.GONE);

                }
                else
                {
                    mLocationLayout.setVisibility(View.VISIBLE);
                    mLocationLayout.startAnimation(mAnimationRight);
                }

                //                if(onMapCounter==0)
                //                {
                //                    onMapCounter++;
                //                    mBottomSlider.setVisibility(View.VISIBLE);
                //                    mBottomSlider.startAnimation(mAnimationRight);
                //                }
                //                else
                //                {
                //                    onMapCounter--;
                //                    mScrollHori.setVisibility(View.GONE);
                //                    mBottomSlider.startAnimation(mANimationLeft);
                //                    mBottomSlider.setVisibility(View.GONE);
                //                }
            }
        });


    }


    public void methodWillStartsTheAsyncTask() 
    {

        ServiceWork testAsyncTask = new ServiceWork(mURL,MainPageDrawerAcitivity.this,"a",new FragmentCallback() {

            @Override
            public void onTaskDone() 
            {

                if(ServiceWork.sPageToken!=null)
                {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                Thread.sleep(2000);
                                CommonFunction.sActivityName="placeList";
                                mURL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken="+ServiceWork.sPageToken+"&key=AIzaSyBDbHzEkRUXsrf5g-7Yb_sJq40GzGbtyiI";

                                methodWillStartsTheAsyncTask() ;
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


                        }
                    }).start();}
                else
                {

                    for(int i=0;i<ServiceWork.businessSearchData.size();i++)
                    {
                        Log.e("hello data",ServiceWork.businessSearchData.get(i).getPlaceAddress()+" "+ServiceWork.businessSearchData.get(i).getDistance());
                    }
                    mProgress.setVisibility(View.GONE);
                    mLocation.setText(ServiceWork.businessSearchData.get(0).getPlaceAddress());
                    mLocationLayout.setVisibility(View.VISIBLE);
                    mLocationLayout.startAnimation(mAnimationRight);
                }}
        });
        testAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
    }

    private class GetDownload extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            try {
                URL imageURL = new URL(urls[0]);


                map = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                return map;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (OutOfMemoryError e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {


            Log.d("resizedBitmap url","hiii " +result);
            if(result!=null)
            {
                Bitmap resized = Bitmap.createScaledBitmap(result, 100, 100, true);
                Bitmap conv_bm = getRoundedRectBitmap(resized,100);
                mProfilePIc=conv_bm;
                mUserImage.setImageBitmap(conv_bm);
            }
        }
    }



    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0,bitmap.getWidth(),bitmap.getHeight());
            RectF rectF=new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            // canvas.drawCircle(50, 50, 50, paint);
            canvas.drawRoundRect(rectF, pixels, pixels, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }



    @Override
    public void onClick(View v) 
    {

        switch(v.getId())
        {

            case R.id.mShareIcon:

                Toast.makeText(MainPageDrawerAcitivity.this,"Hang on!  will about to implement that feature soon!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.mMenu:

                Toast.makeText(MainPageDrawerAcitivity.this,"Hang on!  will about to implement that feature soon!", Toast.LENGTH_SHORT).show();
                break;    

            case R.id.mClock:

                //                CommonFunction.sActivityName="CommentList";
                //                mURL="http://yampp.it/Webservices/advanceSearch?keyword=week";
                //                new ServiceWork(mURL, MainPageDrawerAcitivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //                
                Toast.makeText(MainPageDrawerAcitivity.this,"Hang on!  will about to implement that feature soon!", Toast.LENGTH_SHORT).show();

             //   Intent intent=new Intent(MainPageDrawerAcitivity.this,CommentListActivity.class);
              //  startActivity(intent);

                break;


            case R.id.mDownArrow:

                Intent intnt=new Intent(MainPageDrawerAcitivity.this,ListViewPlacesList.class);
                startActivity(intnt);
                break;
            case R.id.mSLiderLeftButton:

                mTopSliderLayout.setVisibility(View.GONE);
                mTopSliderLayout.startAnimation(mANimationLeft);

                break;


            case R.id.topSliderButton:

                mTopSliderLayout.setVisibility(View.VISIBLE);
                mTopSliderLayout.startAnimation(mAnimationRight);

                break;

            case R.id.middleSliderButton:


                break;

            case R.id.bottomSliderButton:
                onMapCounter++;
                mBottomSlider.setVisibility(View.VISIBLE);
                mBottomSlider.startAnimation(mAnimationRight);
                break;


            case R.id.mEmoticon:

                //                PopupMenu popup = new PopupMenu(this, mEmoticon);
                //                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                //         
                //                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                //                    public boolean onMenuItemClick(MenuItem item) {
                //                      
                //                        return true;
                //                    }
                //                });
                //         
                //                popup.show();
                if(mScrollHori.getVisibility()== View.VISIBLE)
                {
                    mScrollHori.setVisibility(View.GONE);
                }
                else
                {
                    mScrollHori.setVisibility(View.VISIBLE);
                }

                break;


            case R.id.monkey1:
                sMonkey="monkey_1";
                mEmoticon.setImageResource(R.drawable.monkey_1);
                mScrollHori.setVisibility(View.GONE);
                //Toast.makeText(MainPageWithMapActivity.this, sMonkey+" is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.monkey2:
                sMonkey="monkey_2";
                mEmoticon.setImageResource(R.drawable.monkey_2);
                mScrollHori.setVisibility(View.GONE);
                //Toast.makeText(MainPageWithMapActivity.this, sMonkey+" is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.monkey3:
                sMonkey="monkey_3";
                mEmoticon.setImageResource(R.drawable.monkey_3);
                mScrollHori.setVisibility(View.GONE);
                //Toast.makeText(MainPageWithMapActivity.this, sMonkey+" is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.monkey4:
                sMonkey="monkey_4";

                mEmoticon.setImageResource(R.drawable.monkey_4);
                mScrollHori.setVisibility(View.GONE);
                //Toast.makeText(MainPageWithMapActivity.this, sMonkey+" is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.monkey5:
                sMonkey="monkey_5";
                mEmoticon.setImageResource(R.drawable.monkey_5);
                mScrollHori.setVisibility(View.GONE);
                //Toast.makeText(MainPageWithMapActivity.this, sMonkey+" is selected", Toast.LENGTH_SHORT).show();
                break;


            case R.id.mSendComment:

                sSpinnerText=mLocation.getText().toString().trim();
                sCommentText=mComments.getText().toString();
                if(sSpinnerText.trim().length()>0)
                {


                    if(sMonkey.equalsIgnoreCase("0"))
                    {
                        Toast.makeText(MainPageDrawerAcitivity.this, "Select your icon", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        String url;
                        CommonFunction.sActivityName="MainPageWithMapActivity";
//                        if(sSpinnerText.equalsIgnoreCase(ServiceWork.formated_Adres))
//                        {
//                            url=CommonFunction.host+"locationComments?user_id="+CommonFunction.sUserInfo.get(0).get_Id()+"&comment="+URLEncoder.encode(MainPageDrawerAcitivity.sCommentText)+"&icon="+MainPageDrawerAcitivity.sMonkey+"&lat="+ServiceWork.lati+"&lng="+ServiceWork.longi+"&location="+URLEncoder.encode(sSpinnerText);
//                        }
//                        else
//                        {
//                            url=CommonFunction.host+"locationComments?user_id="+CommonFunction.sUserInfo.get(0).get_Id()+"&comment="+URLEncoder.encode(MainPageDrawerAcitivity.sCommentText)+"&icon="+MainPageDrawerAcitivity.sMonkey+"&lat="+FirstActivity.sPlace.get(AfterLoginActivity.sPlacePosition).geometry.location.lat+"&lng="+FirstActivity.sPlace.get(AfterLoginActivity.sPlacePosition).geometry.location.lng+"&location="+URLEncoder.encode(sSpinnerText);
//
//                        }
                        Toast.makeText(MainPageDrawerAcitivity.this,"Hang on!  will about to implement that feature soon!", Toast.LENGTH_SHORT).show();
                        
                        onMapCounter--;
                        mBottomSlider.setVisibility(View.VISIBLE);
                        mBottomSlider.startAnimation(mAnimationRight);
                   //     ListViewForPlaces.listCheckClick=0;
                    //    sFlag2=1;
                       // new ServiceWork(url, MainPageDrawerAcitivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    }

                }

                else
                {
                    Toast.makeText(MainPageDrawerAcitivity.this, "No location is selected", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.searchIcon:

                if(mSearchTxt.getText().toString().trim().length()>0)
                {
                    onMapCounter--;
                    mBottomSlider.setVisibility(View.GONE);
                    mBottomSlider.startAnimation(mANimationLeft);
                    sFlag=2;
                    String search=mSearchTxt.getText().toString().trim();
                    CommonFunction.sActivityName="SearchYampp";
                    mURL=CommonFunction.host+"search?keyword="+URLEncoder.encode(search);
                    methodThatStartsTheAsyncTask();


                }
                break;

            case R.id.mOneHour:

                onMapCounter--;
                mBottomSlider.setVisibility(View.GONE);
                mBottomSlider.startAnimation(mANimationLeft);
                sFlag=2;
                CommonFunction.sActivityName="SearchYampp";
                mURL=CommonFunction.host+"advanceSearch?keyword=hour";
                map.clear();
                MainPageWithMapActivity.sFlag2=0;
                methodThatStartsTheAsyncTask();


                break;

            case R.id.mOneWeek:
                onMapCounter--;
                mBottomSlider.setVisibility(View.GONE);
                mBottomSlider.startAnimation(mANimationLeft);
                sFlag=2;
                CommonFunction.sActivityName="SearchYampp";
                mURL=CommonFunction.host+"advanceSearch?keyword=week";
                map.clear();
                MainPageWithMapActivity.sFlag2=0;
                methodThatStartsTheAsyncTask();

                break;

            case R.id.mOneMonth:
                onMapCounter--;
                mBottomSlider.setVisibility(View.GONE);
                mBottomSlider.startAnimation(mANimationLeft);
                sFlag=2;
                CommonFunction.sActivityName="SearchYampp";
                mURL=CommonFunction.host+"advanceSearch?keyword=month";
                map.clear();

                // map=null;
                MainPageWithMapActivity.sFlag2=0;
                methodThatStartsTheAsyncTask();

                break;

        }

    }


    public void methodThatStartsTheAsyncTask() 
    {

        ServiceWork testAsyncTask = new ServiceWork(mURL,MainPageDrawerAcitivity.this,new FragmentCallback() {

            @Override
            public void onTaskDone() 
            {
                if(sFlag==2)
                {
                    drawMarkerOnMap(); 
                    sFlag=0;
                }
                else
                {
                    map.clear();
                    BitmapDrawable bd=(BitmapDrawable) getResources().getDrawable(R.drawable.marker);;
                    Bitmap b=bd.getBitmap();
                    Bitmap bhalfsize=Bitmap.createScaledBitmap(b, b.getWidth()/2,b.getHeight()/2, false);
                    LatLng pos1=new LatLng(ServiceWork.lati,ServiceWork.longi );
                    marker =map.addMarker(new MarkerOptions().icon(
                            BitmapDescriptorFactory
                            .fromBitmap(bhalfsize))
                            .position(pos1));
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos1, 14));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos1, 15));
                    mLocation.setText(ServiceWork.formated_Adres);
                    ListViewForPlaces.listCheckClick=0;
                }
            }
        });
        testAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
    }


    public void drawMarkerOnMap()
    {
        map.clear();
        LatLng pos1;
        for(int i=0;i<CommonFunction.sSearchYammp.size();i++)
        {

            double lat=Double.parseDouble(CommonFunction.sSearchYammp.get(i).get_latitude());
            double lon=Double.parseDouble(CommonFunction.sSearchYammp.get(i).get_longitude());
            pos1 = new LatLng(lat,lon);
            Log.d("hello ",""+lat);
            if(CommonFunction.sSearchYammp.get(i).get_monkey_icon().equalsIgnoreCase("monkey_1"))
            {
                Log.d("hello ",""+i);
                marker=map.addMarker(new MarkerOptions().title(""+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.m1)).position(pos1).draggable(true).flat(true).anchor(0.0f, 1.0f));    

                //marker.showInfoWindow();
            }
            if(CommonFunction.sSearchYammp.get(i).get_monkey_icon().equalsIgnoreCase("monkey_2"))
            {
                Log.d("hello ",""+i);
                marker=map.addMarker(new MarkerOptions().title(""+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.m2)).position(pos1).draggable(true).flat(true).anchor(0.0f, 1.0f));    
                // marker.showInfoWindow();
            }
            if(CommonFunction.sSearchYammp.get(i).get_monkey_icon().equalsIgnoreCase("monkey_3"))
            {
                Log.d("hello ",""+i);
                marker=map.addMarker(new MarkerOptions().title(""+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.m3)).position(pos1).draggable(true).flat(true).anchor(0.0f, 1.0f));    
                //  marker.showInfoWindow();
            }
            if(CommonFunction.sSearchYammp.get(i).get_monkey_icon().equalsIgnoreCase("monkey_4"))
            {
                Log.d("hello ",""+i);
                marker=map.addMarker(new MarkerOptions().title(""+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.m4)).position(pos1).draggable(true).flat(true).anchor(0.0f, 1.0f));    
                // marker.showInfoWindow();
            }
            if(CommonFunction.sSearchYammp.get(i).get_monkey_icon().equalsIgnoreCase("monkey_5"))
            {
                Log.d("hello ",""+i);
                marker=map.addMarker(new MarkerOptions().title(""+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.m5)).position(pos1).draggable(true).flat(true).anchor(0.0f, 1.0f));    
                //marker.showInfoWindow();
            }





        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //    Log.e("hiiii reusme",AfterLoginActivity.sPlacePosition+" "+AfterLoginActivity.sHomeLoc);
        //  mLocation.setText(ServiceWork.businessSearchData.get(AfterLoginActivity.sPlacePosition).getPlaceAddress());
        if(ListViewForPlaces.listCheckClick==1)
        {
            mLocation.setText(AfterLoginActivity.sHomeLoc);

        }
        else
        {
            if (gps.canGetLocation()) 
            {

                Double latitude=Double.parseDouble(prefs.getString("saveLatitude", "0"));
                Double longitde=Double.parseDouble(prefs.getString("saveLongitude", "0"));
                if(latitude!=gps.latitude&&longitde!=gps.longitude)
                {
                    mLocationLayout.setVisibility(View.GONE);
                    ServiceWork.businessSearchData.clear();
                    CommonFunction.sActivityName="placeList";
                    mURL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+gps.getLatitude()+","+gps.getLongitude()+"&radius=1500&key=AIzaSyBDbHzEkRUXsrf5g-7Yb_sJq40GzGbtyiI";
                    methodWillStartsTheAsyncTask() ;
                    prefsEditor.putString("saveLatitude",""+gps.getLatitude());
                    prefsEditor.putString("saveLongitude",""+gps.getLongitude());
                    prefsEditor.commit();
                }
                else
                {
                    Log.d("old lat and long",latitude+ " "+ longitde);
                    Log.d("new lat and long",gps.getLatitude()+ " "+ gps.getLongitude());
                }
            } else 
            {

                return;
            }
        }

    }
}


