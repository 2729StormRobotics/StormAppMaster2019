package org.stormroboticsnj.stormappmaster2019.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.stormroboticsnj.stormappmaster2019.MainActivity;
import org.stormroboticsnj.stormappmaster2019.R;
import org.stormroboticsnj.stormappmaster2019.db.Handler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static EditText team;
    private static EditText match;
    private static Context mContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DeleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteFragment newInstance(String param1, String param2) {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        team = (EditText) view.findViewById(R.id.editTeamNumOpt);
        match = (EditText) view.findViewById(R.id.editMatchNumOpt);
        /* TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment); */
        return view;
    }
    public void deleteTeamFromMatch() {
        Handler.getInstance(MainActivity.getContext()).exec("DELETE FROM \""+ Handler.DB_TABLE+"\" WHERE \""+Handler.COL_TEAM+"\"=\""+ team.getText().toString()+"\" AND \""+Handler.COL_MATCH+"\"=\""+match.getText().toString() + "\"");
        Toast.makeText(MainActivity.getContext(), "If that team was in that match, it has now been removed.", Toast.LENGTH_SHORT).show();

    }
    public void deleteTeam() {
        Handler.getInstance(MainActivity.getContext()).exec("DELETE FROM \""+Handler.DB_TABLE+"\" WHERE \""+Handler.COL_TEAM+"\"=\""+team.getText().toString()+"\"");
        Toast.makeText(MainActivity.getContext(), "If that team was in the database, it has now been deleted.", Toast.LENGTH_SHORT).show();
    }
    public void deleteMatch() {
        Handler.getInstance(MainActivity.getContext()).exec("DELETE FROM \""+Handler.DB_TABLE+"\" WHERE \""+Handler.COL_MATCH+"\"=\""+match.getText().toString()+"\"");
        Toast.makeText(MainActivity.getContext(), "If that match was in the database, it has now been deleted.", Toast.LENGTH_SHORT).show();
    }
    public void deleteDuplicates() {
        int count = Handler.getInstance(MainActivity.getContext()).countResults(team.getText().toString(), match.getText().toString());
        if (count > 1) {
            Handler.getInstance(MainActivity.getContext()).removeDuplicates(team.getText().toString(), match.getText().toString());
            Toast.makeText(MainActivity.getContext(), "Removed " + (count - 1) + " duplicate entries from match.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.getContext(), "Found no duplicate entries to remove.", Toast.LENGTH_SHORT).show();
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
