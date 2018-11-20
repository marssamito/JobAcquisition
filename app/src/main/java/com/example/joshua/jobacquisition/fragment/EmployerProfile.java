package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joshua.jobacquisition.Activity.Login;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.model.Image;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmployerProfile extends Fragment {

    private SweetAlertDialog Dialog;
    private ProgressDialog pDialog;
    private String TAG = EmployeeViewJob.class.getSimpleName();

    EditText employer_fname,employer_lname,companyName,website,occupation,kra,email,phone,idNumber,location,workAddress;
    Button save;

    private OnFragmentInteractionListener mListener;

    public EmployerProfile() {
        // Required empty public constructor
    }


    public static EmployerProfile newInstance(String param1, String param2) {
        EmployerProfile fragment = new EmployerProfile();
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
        View view= inflater.inflate(R.layout.fragment_employer_profile, container, false);

        getProfile();
        employer_fname=(EditText) view.findViewById(R.id.employer_fname);
        employer_lname=(EditText) view.findViewById(R.id.employer_other);
        companyName=(EditText) view.findViewById(R.id.employer_company);
        website=(EditText) view.findViewById(R.id.employer_website);
        occupation=(EditText) view.findViewById(R.id.employer_occupation);
        kra=(EditText) view.findViewById(R.id.employer_Pin);
        email=(EditText) view.findViewById(R.id.employer_email);
        phone=(EditText) view.findViewById(R.id.employer_number);
        idNumber=(EditText) view.findViewById(R.id.employer_id);
        location=(EditText) view.findViewById(R.id.employer_location);
        workAddress=(EditText) view.findViewById(R.id.employer_workAddress);
        save=(Button) view.findViewById(R.id.employer_save);

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

    private void getProfile() {

        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userID = editor.getString("THE_KEY_USERID", "null");


        Dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        Dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        Dialog.setTitleText("Loading");
        Dialog.setCancelable(false);
        Dialog.show();

        StringRequest req = new StringRequest(Keys.employerViewProfile+"?userID="+userID,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Dialog.dismiss();

                        try {
                            JSONObject object=new JSONObject(response.toString());

                           // Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();

                            employer_fname.setText(object.getString("Fname"));
                            employer_lname.setText(object.getString("Lname"));
                            companyName.setText(object.getString("Company"));
                            website.setText(object.getString("website"));
                            occupation.setText(object.getString("Occupation"));
                            kra.setText(object.getString("KRA"));
                            email.setText(object.getString("Email"));
                            phone.setText(object.getString("phone"));
                            idNumber.setText(object.getString("idnumber"));
                            location.setText(object.getString("location"));
                            workAddress.setText(object.getString("workAddress"));

                        } catch (JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }

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
