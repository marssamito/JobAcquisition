package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;


public class EmployerPostJob extends Fragment {
    EditText title,expertise,description;
    Spinner expirience,category;
    Button upload;
    public static String expiriences,categories;

    private OnFragmentInteractionListener mListener;

    public EmployerPostJob() {
        // Required empty public constructor
    }


    public static EmployerPostJob newInstance(String param1, String param2) {
        EmployerPostJob fragment = new EmployerPostJob();
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

        View view= inflater.inflate(R.layout.fragment_employer_post_job, container, false);
        title=(EditText) view.findViewById(R.id.Employer_post_job);
        expertise=(EditText) view.findViewById(R.id.Employer_post_expertise);
        description=(EditText) view.findViewById(R.id.Employer_post_description);

        expirience=(Spinner) view.findViewById(R.id.Employer_post_expirience);
        category=(Spinner) view.findViewById(R.id.Employer_post_category);

        upload=(Button) view.findViewById(R.id.Employer_save);

       expirience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               expiriences  = expirience.getSelectedItem().toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               categories  = category.getSelectedItem().toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employer_post_job();
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


    private void check() {

        final String Title = title.getText().toString().trim();
        final String Expertises = expertise.getText().toString();
        final String Descriptions = description.getText().toString().trim();

        if (TextUtils.isEmpty(Title)) {
            title.setError("This field is empty");
            return;
        } else if (TextUtils.isEmpty(Expertises)) {
            expertise.setError("This field is empty");
            return;
        } else if (TextUtils.isEmpty(Descriptions)) {
            description.setError("This field is empty");
            return;
        } else {
            employer_post_job();
        }
    }


       public void employer_post_job()
    {

        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userid  = editor.getString("THE_KEY_USERID", "null").trim();
        final String Title = title.getText().toString().trim();
        final String Expertises = expertise.getText().toString().trim();
        final String Descriptions = description.getText().toString().trim();

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.Employer_post,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "success "+response, Toast.LENGTH_LONG).show();

                        title.setText("");
                        expertise.setText("");
                        description.setText("");


//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.content_contractor, new ContractorViewJobs(), getString(R.string.app_name));
//                        ft.commit();


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
                params.put("Title", Title);
                params.put("EmployerId", userid.trim());
                params.put("Expertise", Expertises);
                params.put("Description", Descriptions);
                params.put("Experience", expiriences);
                params.put("category",categories);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    /**end save to database**/
}
