package com.example.rasp.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by BSETEC on 24-12-2015.
 */
public class Trending_showRoom_landing_page implements View.OnClickListener {
    Activity activity;
    ImageView back;
    TextView terms_condition, mail, login_txt, txt_fb_cancle, txt_fb_accept, txt_fb_login;
    RelativeLayout facebook_panel;
    Context mContext;
    String responseData;
    private DisplayImageOptions options;
    String accessToken, disc_type, pageno;
    // ArrayList<DiscoverShowroom> discover_Showroom_List;
    ArrayList<Treding_showRoom_landing_list> discover_Showroom_List;
    ArrayList<DiscoverShowroom> discover_Tag_List;
    HorizontalScrollView trending_showroom_horizontal1, trending_tag_horizontal1;
    LinearLayout trending_showroom_layout1, trending_tag_layout1;
    TextView trend_text, trending_tag_seeall,discover_showroom_text, btn_login1, btn_sign_up1;
    ImageView trending_showroom_seeall;
    ImageView trend_image;
    EmojiExplorerView hash_tag_text;

    int scroll_val=50;
    public HashMap<String, String> uesrParams = new HashMap<String, String>();
    public ProgressBar prg_bar_discover;
    public Typeface gotham_book;
    SharedPreferencesData prefDara;
    CommanDialog mCommanDialog = null ;

    public Trending_showRoom_landing_page(Context context) {
        mContext = context;
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.loading_image)
                .showImageOnFail(R.drawable.bg)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(15))
                .build();
    }


    @TargetApi(VERSION_CODES.M)
    public void headerData(Activity activity, String pageno, HorizontalScrollView trending_showroom_horizontal, LinearLayout trending_showroom_layout, ImageView trending_showroom_seeall, TextView btn_login, TextView btn_sign_up, TextView txt_fb_cancle, TextView txt_fb_accept, TextView txt_fb_login, ImageView back, RelativeLayout facebook_panel)
    {

        this.activity = activity;
        this.pageno = pageno;
        this.trending_showroom_horizontal1 = trending_showroom_horizontal;

        this.trending_showroom_layout1 = trending_showroom_layout;

        this.trending_showroom_seeall = trending_showroom_seeall;
        this.trending_showroom_seeall.setOnClickListener(this);
        btn_login1 = btn_login;
        this.btn_login1.setOnClickListener(this);
        btn_sign_up1 = btn_sign_up;
        this.btn_sign_up1.setOnClickListener(this);
        this.mContext = activity.getApplicationContext();
        this.discover_Showroom_List = new ArrayList<Treding_showRoom_landing_list>();
        uesrParams.put("page_number", "1");
        if(discover_Showroom_List.size()<8)

            new Getting_showroom(uesrParams).execute();

        this.txt_fb_cancle = txt_fb_cancle;
        this.txt_fb_accept = txt_fb_accept;
        this.txt_fb_login = txt_fb_login;
        this.facebook_panel = facebook_panel;
        this.back = back;
    }

    public String get_showrooms_landing(final HashMap<String, String> UserParams) {

        mCommanDialog = new CommanDialog(mContext, "");
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
        StringRequest postRequest = new StringRequest(Method.GET, AppCommon.BASE_URL + "customshowroom/trending_showroom_landing", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                responseData = response;
                Log.e("Response data ", "Response data" + response);
                try {
                    mCommanDialog.stopCustomDialog();
                    JSONObject jobJsonObject = new JSONObject(response);
                    String status = jobJsonObject.getString("Status");
                    if (status.equals("Success")) {

                        if (jobJsonObject.getJSONArray("trending_showroom_list") != null || jobJsonObject.getJSONArray("trending_showroom_list").length() != 0) {
                            JSONArray showroomDetails = jobJsonObject.getJSONArray("trending_showroom_list");
                            for (int i = 0; i < showroomDetails.length(); i++) {
                                JSONObject showroom_list_obj = showroomDetails.getJSONObject(i);
                                String showroom_url = showroom_list_obj.getString("showroom_url");
                                String showroom_tag= showroom_list_obj.getString("showroom_tag");

                                String showroom_name = showroom_list_obj.getString("showroom_name");
                                String showroom_description = showroom_list_obj.getString("showroom_description");
                                String showroom_img_big=showroom_list_obj.getString("showroom_img_big");
                                String showroom_img_small=showroom_list_obj.getString("showroom_img_small");
                                // String showroom_link = showroom_list_obj.getString("showroom_link");
                                String showroom_createdby_userlink=showroom_list_obj.getString("showroom_createdby_userlink");
                                String showroom_link="The value";

                                String mint_count = showroom_list_obj.getString("mint_count");
                                String member_count = showroom_list_obj.getString("member_count");
                                String privacy_settings = showroom_list_obj.getString("privacy_settings");
                                String follow_status="true";
                                String showroom_id = showroom_list_obj.getString("showroom_id");
                                String show_option = showroom_list_obj.getString("show_option");
                                String privacy_type=showroom_list_obj.getString("privacy_type");
                                String user_thumb_image = showroom_list_obj.getString("showroom_createdby_userimage");
                                String user_name = showroom_list_obj.getString("showroom_createdby_username");
                                Treding_showRoom_landing_list bean=new Treding_showRoom_landing_list(showroom_img_small,showroom_tag, showroom_name, showroom_description, showroom_link, mint_count, member_count, privacy_settings, follow_status, showroom_id, show_option,  user_thumb_image, user_name);
                                discover_Showroom_List.add(bean);
                            }

                            for (int j = 0; j < discover_Showroom_List.size(); j++) {
                                trending_showroom_layout1.addView(horizontal_listView(discover_Showroom_List.get(j).getShowroom_img(), discover_Showroom_List.get(j).getShowroom_name(), discover_Showroom_List.get(j).getShowroom_tag()));
                            }
                        }
                        SharedPreferencesData.setOnBoardingTrendingTags(mContext,discover_Showroom_List);
                    } else {
                        Log.e("Problem is in checking", "Problem is in checking");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Second trace", "second trace" + e);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseData = "Invalid request has sent";
                        Log.e("Error at", "Error at" + responseData + error);
                        mCommanDialog.stopCustomDialog();
                    }
                }
        ) {
        };
        queue.add(postRequest).setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
//                get_showrooms_landing(UserParams);
                mCommanDialog.stopCustomDialog();
            }
        });

        return responseData;
    }


    public boolean isShowroomAvailable(Treding_showRoom_landing_list bean) {
        boolean result = false;
        if (discover_Showroom_List != null) {
            for (Treding_showRoom_landing_list userBean : discover_Showroom_List) {
                if (bean.getShowroom_id().compareToIgnoreCase(bean.getShowroom_id()) == 0) {
                    result = true;
                    return result;
                }
            }
        }
        return result;
    }

    // prepare the Request
    public void getdata(final HashMap<String, String> UserParams) {
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
        Map<String, String> params = UserParams;
        Iterator<Entry<String, String>> it = UserParams.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> pairs = (Entry<String, String>) it.next();
//                if (pairs.getValue() == null) {
//                    hashParams.put(pairs.getKey(), "");
            String key = pairs.getKey();
            String value = pairs.getValue();
            Log.e("Params key value", "Params key value" + key);
            Log.e("Params key value", "Params key value" + value);
            //}
        }
        String url=AppCommon.BASE_URL + "customshowroom/trending_showroom_landing"+"?page_number=1";
        JsonObjectRequest getRequest = new JsonObjectRequest(Method.GET, url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // display response
                Log.e("Response", response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response",  error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }

    public class Getting_showroom extends AsyncTask<Void,Void,Void> {

        public HashMap<String,String> hashParams;
        public Getting_showroom(HashMap<String,String> hashParams){
            this.hashParams=hashParams;

        }
        @Override
        protected Void doInBackground(Void... params) {
            try{
                get_showrooms_landing(hashParams);
            }catch(Exception e){e.printStackTrace();}
            return null;
        }

        public void onPostExecute(Void result ){
            super.onPostExecute(result);
            // mAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.showroom_nxt_btn:
                trending_showroom_horizontal1.scrollTo(trending_showroom_horizontal1.getScrollX() + 50,trending_showroom_horizontal1.getScrollY());
                break;
            case R.id.btn_login:
                Intent intent = new Intent(mContext, Global_Discover_Login.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //activity.startActivityForResult(intent,0);
                login_popup();
                break;
            case R.id.btn_sign_up:
                /*Intent i = new Intent(mContext, Global_discover_signup.class);
                mContext.startActivity(i);*/
                login_popup();
                break;

        }
    }

    private View horizontal_listView(String showroom_img, final String showroom_name, final String showroom_tag) {
        // TODO Auto-generated method stub

        LayoutInflater mInflater = (LayoutInflater)mContext. getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.trending_showroom_items, null);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "HelveticaNeue.ttf");
        prg_bar_discover = (ProgressBar) view.findViewById(R.id.progressBar_discover);

        hash_tag_text = (EmojiExplorerView) view.findViewById(R.id.hash_tag_text);
        TextView trend_text1 = (TextView) view.findViewById(R.id.trend_text);
        trend_image = (ImageView) view.findViewById(R.id.trend_image);
        trend_text1.setTypeface(font);
        hash_tag_text.setVisibility(view.GONE);

        // Picasso.with(mContext).load(showroom_img).transform(new RoundedCornersTransformation(19, 0)).fit().into(trend_image);
        ImageLoader.getInstance().displayImage(showroom_img, trend_image, options);
        trend_text1.setText(showroom_tag);


        return view;
    }

    private View horizontal_Tag_listView(String tag_img, String tag_name)
    {
        // TODO Auto-generated method stub
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.trending_showroom_items, null);
        ThumbnailImageLoader thumb_loader = new ThumbnailImageLoader(mContext);
        prg_bar_discover = (ProgressBar) view.findViewById(R.id.progressBar_discover);

        trend_image = (ImageView) view.findViewById(R.id.trend_image);
        trend_text = (TextView) view.findViewById(R.id.trend_text);

        trend_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               /* Intent mint_detail = new Intent(mContext, Discover_mint_detail_page.class);
                mContext.startActivity(mint_detail);*/
            }
        });

        Picasso.with(mContext).load(tag_img).transform(new RoundedTransformation(12, 0)).fit().into(trend_image);

        trend_text.setText("#" + tag_name);
        trend_text.setTypeface(gotham_book);

        return view;
    }

    public void login_popup() {
        final Dialog popup = new Dialog(activity);
        popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setContentView(R.layout.signup_popup);
        back = (ImageView) popup.findViewById(R.id.pop_up_close);
        facebook_panel = (RelativeLayout) popup.findViewById(R.id.facebook_panel);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popup.dismiss();
            }
        });
//                edit = (EditText) popup
//                        .findViewById(R.id.Edit_page_editable_txt);
        terms_condition = (TextView) popup.findViewById(R.id.terms_condition);
        login_txt = (TextView) popup.findViewById(R.id.login_txt);
        mail = (TextView) popup.findViewById(R.id.mail);

        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popup.dismiss();
                Intent intent = new Intent(activity, Global_discover_signup.class);
                activity.startActivity(intent);
//                finish();

            }
        });
        login_txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popup.dismiss();
                Intent intent = new Intent(activity, Global_Discover_Login.class);
                activity.startActivity(intent);
//                finish();

            }
        });
        terms_condition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popup.dismiss();
                Intent intent = new Intent(activity, Terms_Condition.class);
                activity.startActivity(intent);

            }
        });
        facebook_panel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popup.dismiss();
                //fb login...
                Global_Discover_Main.fb_login(activity);
            }
        });

        popup.show();

    }


}