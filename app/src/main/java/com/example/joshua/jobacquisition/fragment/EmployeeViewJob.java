package com.example.joshua.jobacquisition.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.joshua.jobacquisition.Adapter.EmployeeViewJobs;
import com.example.joshua.jobacquisition.Network.CustomVolleyRequest;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.model.Image;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class EmployeeViewJob extends Fragment {

    public static String title,expertise,experience,descrition,dateposted,jobId;

    private SweetAlertDialog Dialog;
    private ProgressDialog pDialog;
    private String TAG = EmployeeViewJob.class.getSimpleName();
    private ArrayList<Image> images;
    private EmployeeViewJobs mAdapter;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public EmployeeViewJob() {
        // Required empty public constructor
    }


    public static EmployeeViewJob newInstance(String param1, String param2) {
        EmployeeViewJob fragment = new EmployeeViewJob();
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
        View view= inflater.inflate(R.layout.fragment_employee_view_job, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_request);
        recyclerView.setHasFixedSize(true);

        pDialog = new ProgressDialog(getActivity());
        images = new ArrayList<>();
        mAdapter = new EmployeeViewJobs(getActivity(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new EmployeeViewJobs.RecyclerTouchListener(getActivity(), recyclerView, new EmployeeViewJobs.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                Image image = images.get(position);
                title=image.getMytitle();
                expertise=image.getMyexpertise();
                experience=image.getMyexperice();
                descrition=image.getMyDescription();
                dateposted=image.getMydate();
                jobId=image.getMyJobId();
//                jobid=image.getOwnerRequestbookeById();
//                tableid=image.getOwnerRequestId();

                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.content_employee, new JobMoreDetails()).addToBackStack(null).commit();


//                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Apply Job")
//                        .setContentText(title)
//                        .setCancelText("View More")
//                        .setConfirmText("Apply")
//                        .showCancelButton(true)
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//
//
//
//                                sDialog.cancel();
//                            }
//                        })
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                Toast.makeText(getActivity(), "coming soon", Toast.LENGTH_SHORT).show();
//                                sweetAlertDialog.cancel();
//                            }
//                        })
//                        .show();

//                FragmentManager fm=getFragmentManager();
//                fm.beginTransaction().replace(R.id.content_product, new AddStockSave()).addToBackStack(null).commit();

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //first check for cached request
        Cache cache = CustomVolleyRequest.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Keys.Employee_view_job);//JsonArrayRequest(db_url
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
        Dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        Dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        Dialog.setTitleText("Loading");
        Dialog.setCancelable(false);
        Dialog.show();
        //pDialog.setCancelable(true);
        //pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Keys.Employee_view_job,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        Dialog.dismiss();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();

                                image.setMytitle(object.getString("title"));
                                image.setMyexperice(object.getString("experience"));
                                image.setMyexpertise(object.getString("expertise"));
                                image.setMyDescription(object.getString("description"));
                                image.setMyJobId(object.getString("job_id"));
                                image.setMydate(object.getString("date_posted"));
                                //image.setPhoneNumber(object.getString("Phone"));

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


        // Adding request to request queue


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(req);

        //  AppController.getInstance().addToRequestQueue(req);
    }

}
