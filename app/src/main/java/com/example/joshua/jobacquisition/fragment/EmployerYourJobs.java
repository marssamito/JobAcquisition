package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.joshua.jobacquisition.Activity.Login;
import com.example.joshua.jobacquisition.Adapter.YourJobAdapter;
import com.example.joshua.jobacquisition.Network.CustomVolleyRequest;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.model.Image;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class EmployerYourJobs extends Fragment {

    public static String  title,jobId, userID;
    private SweetAlertDialog Dialog;
    private ProgressDialog pDialog;
    private String TAG = EmployeeViewJob.class.getSimpleName();
    private ArrayList<Image> images;
    private YourJobAdapter mAdapter;
    private RecyclerView recyclerView;
    
    private OnFragmentInteractionListener mListener;

    public EmployerYourJobs() {
        // Required empty public constructor
    }

   
    public static EmployerYourJobs newInstance(String param1, String param2) {
        EmployerYourJobs fragment = new EmployerYourJobs();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_your_jobs, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_request);
        recyclerView.setHasFixedSize(true);

        pDialog = new ProgressDialog(getActivity());
        images = new ArrayList<>();
        mAdapter = new YourJobAdapter(getActivity(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new YourJobAdapter.RecyclerTouchListener(getActivity(), recyclerView, new YourJobAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                Image image = images.get(position);
                title=image.getMytitle();
                jobId=image.getMyJobId();
                userID=image.getEmpID().trim();


                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.content_employer, new EmployerJobsApplied()).addToBackStack(null).commit();

                
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //first check for cached request
        Cache cache = CustomVolleyRequest.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Keys.employerYourJobs);//JsonArrayRequest(db_url
        if (entry != null) {

            Image image = new Image();
        } else {
            getRequests();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

   
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getRequests() {

        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userID = editor.getString("THE_KEY_USERID", "null");
        Toast.makeText(getActivity(), ""+userID, Toast.LENGTH_SHORT).show();

        Dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        Dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        Dialog.setTitleText("Loading");
        Dialog.setCancelable(false);
        Dialog.show();
        //pDialog.setCancelable(true);
        //pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Keys.employerYourJobs+"?userID="+userID.trim(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        Dialog.dismiss();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();

                                Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                                image.setMytitle(object.getString("jobTittle"));
                                image.setMyJobId(object.getString("jobId"));
                                image.setEmpID(object.getString("empoyerID"));
                                image.setMydate(object.getString("datePosted"));
//                                image.setIDnumber(object.getString("National_ID"));

                                images.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }


                            //Toast.makeText(getActivity(),"sorry"+response,Toast.LENGTH_SHORT).show();


                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Dialog.hide();
                Toast.makeText(getActivity(), "sorry"+error, Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(req);
    }

}
