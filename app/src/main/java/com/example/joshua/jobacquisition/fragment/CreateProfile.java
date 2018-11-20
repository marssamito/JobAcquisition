package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class CreateProfile extends Fragment {

    EditText fname,othernames,dob,idNo,email,nationality,language,lowlevels,
            higherlevels,otherlevels,mission,vission,skill,volunteer,reffereename,reffcontact,reffoccupation;
    Button profilesave;
    private OnFragmentInteractionListener mListener;

    public CreateProfile() {
        // Required empty public constructor
    }


    public static CreateProfile newInstance(String param1, String param2) {
        CreateProfile fragment = new CreateProfile();
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
        View view= inflater.inflate(R.layout.fragment_create_profile, container, false);
        fname=(EditText) view.findViewById(R.id.profile_fname);
        othernames=(EditText) view.findViewById(R.id.profile_other);
        dob=(EditText) view.findViewById(R.id.profile_Dob);
        idNo=(EditText) view.findViewById(R.id.profile_id);
        email=(EditText) view.findViewById(R.id.profile_email);
        nationality=(EditText) view.findViewById(R.id.profile_nationality);
        language=(EditText) view.findViewById(R.id.profile_language);
        lowlevels=(EditText) view.findViewById(R.id.profile_low);
        higherlevels=(EditText) view.findViewById(R.id.profile_High);
        otherlevels=(EditText) view.findViewById(R.id.profile_others);
        mission=(EditText) view.findViewById(R.id.profile_mission);
        vission=(EditText) view.findViewById(R.id.profile_vision);
        skill=(EditText) view.findViewById(R.id.profile_skills);
        volunteer=(EditText) view.findViewById(R.id.profile_Experience);
        reffereename=(EditText) view.findViewById(R.id.profile_refName);
        reffcontact=(EditText) view.findViewById(R.id.profile_refContacts);
        reffoccupation=(EditText) view.findViewById(R.id.profile_refOccupation);

        profilesave=(Button) view.findViewById(R.id.profile_save);

        profilesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_profile();
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

    /**
     * start save to database*
     **/

//    private void check()
//    {
//
//        final String Fname = fname.getText().toString().trim();
//        final String Oname = othernames.getText().toString().trim();
//        final String dobs = dob.getText().toString().trim();
//        final String idNos = idNo.getText().toString().trim();
//        final String emails = email.getText().toString().trim();
//        final String nationalitys = nationality.getText().toString().trim();
//        final String languages = language.getText().toString().trim();
//        final String lowlevel = lowlevels.getText().toString().trim();
//        final String higherlevel = higherlevels.getText().toString().trim();
//        final String otherlevel = otherlevels.getText().toString().trim();
//        final String missions = mission.getText().toString().trim();
//        final String vissions = vission.getText().toString().trim();
//        final String skills = skill.getText().toString().trim();
//        final String volunteers = volunteer.getText().toString().trim();
//        final String reffereenames = reffereename.getText().toString().trim();
//        final String reffcontacts = reffcontact.getText().toString().trim();
//        final String reffoccupations = reffoccupation.getText().toString().trim();
//
//        if (TextUtils.isEmpty(fname)) {
//            employer_fname.setError("This field is empty");
//            return;
//        }else
//        if (TextUtils.isEmpty(Lname)) {
//            employer_lname.setError("This field is empty");
//            return;
//        }}



    public void post_profile()
    {
        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userid = editor.getString("THE_KEY_USERID", "null").trim();

        final String Fname = fname.getText().toString().trim();
        final String Oname = othernames.getText().toString().trim();
        final String dobs = dob.getText().toString().trim();
        final String idNos = idNo.getText().toString().trim();
        final String emails = email.getText().toString().trim();
        final String nationalitys = nationality.getText().toString().trim();
        final String languages = language.getText().toString().trim();
        final String lowlevel = lowlevels.getText().toString().trim();
        final String higherlevel = higherlevels.getText().toString().trim();
        final String otherlevel = otherlevels.getText().toString().trim();
        final String missions = mission.getText().toString().trim();
        final String vissions = vission.getText().toString().trim();
        final String skills = skill.getText().toString().trim();
        final String volunteers = volunteer.getText().toString().trim();
        final String reffereenames = reffereename.getText().toString().trim();
        final String reffcontacts = reffcontact.getText().toString().trim();
        final String reffoccupations = reffoccupation.getText().toString().trim();


        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.employeeCreateProfile,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();

                        if(response.equals("Success"))
                        {
                            Toast.makeText(getActivity(), "successfully Created ", Toast.LENGTH_LONG).show();

                        }
                        else  if(response.equals("profile exist")) {
                            Toast.makeText(getActivity(), "profile exist", Toast.LENGTH_LONG).show();

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
                params.put("First_Name", Fname);
                params.put("Other_Name", Oname);
                params.put("Dob", dobs);
                params.put("Id_No", idNos);
                params.put("Email", emails);
                params.put("Nationality", nationalitys);
                params.put("Language_Spoken", languages);
                params.put("Low_Levels", lowlevel);
                params.put("High_levels", higherlevel);
                params.put("Other_Levels", otherlevel);
                params.put("Mission", missions);
                params.put("Vision", vissions);
                params.put("Skills", skills);
                params.put("Voluntership", volunteers);
                params.put("Referee_Name", reffereenames);
                params.put("Referee_Contact", reffcontacts);
                params.put("Referee_Occupation", reffoccupations);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    /**end save to database**/
}
