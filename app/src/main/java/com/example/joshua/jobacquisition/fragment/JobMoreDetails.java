package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joshua.jobacquisition.Activity.Login;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JobMoreDetails extends Fragment {

    public TextView mtitle,expertise,expirience,description,dates;
    public Button apply;

    private OnFragmentInteractionListener mListener;

    public JobMoreDetails() {
        // Required empty public constructor
    }


    public static JobMoreDetails newInstance(String param1, String param2) {
        JobMoreDetails fragment = new JobMoreDetails();
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

       View view= inflater.inflate(R.layout.fragment_job_more_details, container, false);
        mtitle=(TextView) view.findViewById(R.id.viewTitle);
        mtitle.setText("Title: "+EmployeeViewJob.title);
        expertise=(TextView) view.findViewById(R.id.viewExpertise);
        expertise.setText("Expertise: "+EmployeeViewJob.expertise);
        expirience=(TextView) view.findViewById(R.id.viewExperience);
        expirience.setText("Experience: "+EmployeeViewJob.experience);
        description=(TextView) view.findViewById(R.id.viewDescription);
        description.setText(EmployeeViewJob.descrition);
        dates=(TextView) view.findViewById(R.id.viewDate);
        dates.setText("Date Posted: "+EmployeeViewJob.dateposted);

        apply=(Button) view.findViewById(R.id.viewbutton);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth();
            }
        });

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

    /**start save to database**/
    private void auth()
    {

        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Login.MODE_PRIVATE);
        final String idNo = editor.getString("THE_KEY_IDNO", "null").trim();


        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        StringRequest strinRequest = new StringRequest(Request.Method.POST, Keys.auth,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();

                        try {
                            JSONObject jObj=new JSONObject(response.toString());
                            String helb = jObj.getString("helb").toString();
                            String kra = jObj.getString("kra").toString();
                            String crb = jObj.getString("crb").toString();

                            if (helb.equals("1") && kra.equals("1")&& crb.equals("1"))
                            {
                                //Toast.makeText(getActivity(), " "+helb+kra+crb, Toast.LENGTH_LONG).show();
                                employee_apply_job();
                            }else
                            {
                                Toast.makeText(getActivity(), "Please Clear with HELB or KRA or CRB to apply", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",idNo);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(strinRequest);

    }
    /**end save to database**/

    public void employee_apply_job()
    {
        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userid = editor.getString("THE_KEY_USERID", "null").trim();
        final String idNo = editor.getString("THE_KEY_IDNO", "null").trim();


        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.applyJob,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), " "+response, Toast.LENGTH_LONG).show();
                        if (response.equals("success"))
                        {
                            Toast.makeText(getActivity(), "job Applied", Toast.LENGTH_SHORT).show();
                        }
                        else {

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User_Id", userid);
                params.put("idNumber", idNo);
                params.put("job_id", EmployeeViewJob.jobId);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    /**end save to database**/
}
