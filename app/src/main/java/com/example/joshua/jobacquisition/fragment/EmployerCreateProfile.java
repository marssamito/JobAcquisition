package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class EmployerCreateProfile extends Fragment {
    EditText employer_fname,employer_lname,companyName,website,occupation,kra,email,phone,idNumber,location,workAddress;
    Button save;


    private OnFragmentInteractionListener mListener;

    public EmployerCreateProfile() {
        // Required empty public constructor
    }


    public static EmployerCreateProfile newInstance(String param1, String param2) {
        EmployerCreateProfile fragment = new EmployerCreateProfile();
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
       View view= inflater.inflate(R.layout.fragment_employer_create_profile, container, false);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //employer_profile();
                check();
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
        final String Fname = employer_fname.getText().toString().trim();
        final String Lname = employer_lname.getText().toString();
        final String Company = companyName.getText().toString().trim();
        final String Website = website.getText().toString().trim();
        final String Occupation = occupation.getText().toString().trim();
        final String KRA = kra.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();
        final String IDnumber = idNumber.getText().toString().trim();
        final String Location = location.getText().toString().trim();
        final String WorkAddress = workAddress.getText().toString().trim();

        if (TextUtils.isEmpty(Fname)) {
            employer_fname.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(Lname)) {
            employer_lname.setError("This field is empty");
            return;
        }

        else
        if (TextUtils.isEmpty(Company)) {
            companyName.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(Website)) {
            website.setError("This field is empty");
            return;
        }//duplicate thiss

        else
        if (TextUtils.isEmpty(Occupation)) {
            occupation.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(KRA)) {
            kra.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(Email)) {
            email.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(Phone)) {
           phone.setError("This field is empty");
            return;}
        else
        if (TextUtils.isEmpty(IDnumber)) {
           idNumber.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(Location)){
            location.setError("This field is empty");
            return;
        }
        else
        if (TextUtils.isEmpty(WorkAddress)) {
            workAddress.setError("This field is empty");
            return;
        }

        else{
        employer_profile();}
    }
 public void employer_profile()
    {
        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userid = editor.getString("THE_KEY_USERID", "null").trim();

        final String Fname = employer_fname.getText().toString().trim();
        final String Lname = employer_lname.getText().toString();
        final String Company = companyName.getText().toString().trim();
        final String Website = website.getText().toString().trim();
        final String Occupation = occupation.getText().toString().trim();
        final String KRA = kra.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();
        final String IDnumber = idNumber.getText().toString().trim();
        final String Location = location.getText().toString().trim();
        final String WorkAddress = workAddress.getText().toString().trim();

               final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.employerCreateProfile,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        //Toast.makeText(getActivity(), " "+response, Toast.LENGTH_LONG).show();
                        if (response.equals("profile exist"))
                        {
                            Toast.makeText(getActivity(), "u have profile", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            employer_fname.setText("");
                            employer_lname.setText("");
                            companyName.setText("");
                            website.setText("");
                            occupation.setText("");
                            kra.setText("");
                            email.setText("");
                            phone.setText("");
                            idNumber.setText("");
                            location.setText("");
                            workAddress.setText("");
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
                params.put("Fname", Fname);
                params.put("Lname", Lname);
                params.put("Company", Company);
                params.put("Website", Website);
                params.put("Occupation", Occupation);
                params.put("KRA", KRA);
                params.put("Email", Email);
                params.put("Phone", Phone);
                params.put("Id_No", IDnumber);
                params.put("Location", Location);
                params.put("Work_Address", WorkAddress);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    /**end save to database**/
}
