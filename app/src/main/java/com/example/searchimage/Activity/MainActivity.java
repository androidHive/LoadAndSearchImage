package com.example.searchimage.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.searchimage.Adapter.RecylerViewImgAdapter;
import com.example.searchimage.PojoCalss.PojoData;
import com.example.searchimage.R;
import com.example.searchimage.Singleton.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtsearch;
    static ArrayList<PojoData> gridDataArrayList;
    RecylerViewImgAdapter recylerViewImgAdapter;
    RecyclerView mRecyclerView;
    GridLayoutManager gridLayoutManager;
    int page = 0;
    String query;
    ImageButton mBtnSearch;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the RecyclerView
        mBtnSearch = findViewById(R.id.mBtnSearch);
        edtsearch = findViewById(R.id.edtsearch);
        mRecyclerView = findViewById(R.id.myRecycleView);
        gridDataArrayList = new ArrayList<>();

        recylerViewImgAdapter = new RecylerViewImgAdapter(MainActivity.this, gridDataArrayList);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtsearch.equals("")) {
                    query = edtsearch.getText().toString();
                    getImageList(query, page + 1);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    Toast.makeText(MainActivity.this, "Search Something!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(recylerViewImgAdapter);
    }

    public void getImageList(String query, int current_page) {
        String tag_json_obj = "json_obj_req";
        try {

            String APIURL = "https://api.imgur.com/3/gallery/search/" + current_page + "?q=" + query;
            System.out.println("APIURL = " + APIURL);
            StringRequest jsonObjReq = new StringRequest(Request.Method.GET, APIURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (gridDataArrayList != null) {
                                gridDataArrayList.clear();
                            }
                            Log.e("response GET_CAMP_LIST ", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                String link = null;
                                String imgid = null;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String title = jsonObject1.getString("title");

                                    if (jsonObject1.has("images")) {
                                        JSONArray jsonArray1 = jsonObject1.getJSONArray("images");
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                            imgid = jsonObject2.getString("id");
                                            link = jsonObject2.getString("link");
                                        }
                                    }
                                    PojoData pojoData = new PojoData(id, title, imgid, link);
                                    gridDataArrayList.add(pojoData);
                                    recylerViewImgAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    VolleyLog.e("Error: " + error.getMessage());
                    if (error.getMessage() == null) {
                        mRecyclerView.stopScroll();
                    }
                }
            }) {
                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Client-ID 137cda6b5008a7c");
                    return params;
                }
            };
            jsonObjReq.setShouldCache(false);
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}