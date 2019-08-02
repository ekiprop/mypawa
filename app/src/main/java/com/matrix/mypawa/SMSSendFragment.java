package com.matrix.mypawa;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SMSSendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SMSSendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SMSSendFragment extends Fragment implements View.OnClickListener {

    private PowerViewModel powerViewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView tvSmsSend;
    EditText edSms;
    Button btnSend;

    public SMSSendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SMSSendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SMSSendFragment newInstance(String param1, String param2) {
        SMSSendFragment fragment = new SMSSendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static void myClickMethod(View v) {

        switch (v.getId()) {
            case R.id.btnSend:
                // Toast.makeText(getActivity(),"Text!", Toast.LENGTH_SHORT).show();
                //Intent b = new Intent(this, SMS.class);

                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smssend, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){


        powerViewModel = ViewModelProviders.of(getActivity()).get(PowerViewModel.class);
       

        tvSmsSend = view.findViewById(R.id.tvSmsSend);
        edSms = view.findViewById(R.id.edSms);
        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void smsSendMessage(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        String sms_msg = edSms.getText().toString();
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("address", "0702565559");
        sendIntent.putExtra("sms_body", sms_msg);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);

        Power power = new Power("Device 1","+254702565559", "TO",
                null, null,null);
        power.setId(1);
        powerViewModel.update(power);

        Toast.makeText(getActivity(), "Success Update", Toast.LENGTH_SHORT).show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
