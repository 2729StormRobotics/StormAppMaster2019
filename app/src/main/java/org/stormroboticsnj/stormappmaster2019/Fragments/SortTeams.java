package org.stormroboticsnj.stormappmaster2019.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.stormroboticsnj.stormappmaster2019.R;
import org.stormroboticsnj.stormappmaster2019.db.DeepSpace;
import org.stormroboticsnj.stormappmaster2019.db.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SortTeams.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SortTeams#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortTeams extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<ArrayList<Integer>> dataTable;
    String[] dataCol = {Handler.COL_AUTO_HATCHES, Handler.COL_AUTO_CARGO, Handler.COL_CARGO_RT,
            Handler.COL_CARGO_RD, Handler.COL_CARGO_RU, Handler.COL_CARGO_SHIP, Handler.COL_CARGO_PLAYER,
            Handler.COL_CARGO_GROUND, Handler.COL_HATCH_RT, Handler.COL_HATCH_RD, Handler.COL_HATCH_RU,
            Handler.COL_HATCH_SHIP, Handler.COL_HATCH_PLAYER, Handler.COL_HATCH_GROUND,
            Handler.COL_SELF_LEVEL, Handler.COL_ASSIST_LEVEL, Handler.COL_ASSIST_TWO_LEVEL};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private TableLayout scrollablePart;

    public SortTeams() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SortTeams.
     */
    // TODO: Rename and change types and number of parameters
    public static SortTeams newInstance(String param1, String param2) {
        SortTeams fragment = new SortTeams();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sort_teams, container, false);
        Spinner dropdown = view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_teams_array,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        scrollablePart = (TableLayout) view.findViewById(R.id.scrollable_part_sort);
        final RadioGroup rdog = view.findViewById(R.id.rdogSortBy);
        rdog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdoMax:
                        sortArrayList(dataTable, 1);
                        break;
                    case R.id.rdoAvg:
                        sortArrayList(dataTable, 2);
                        break;
                    default:
                        sortArrayList(dataTable, 0);
                }
                updateData();
            }
        });

        return view;
    }

    //util method
    private TextView recyclableTextView;

    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels,
                                         boolean header, boolean subheader, int[] index) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        recyclableTextView = new TextView(getActivity().getApplicationContext());
        recyclableTextView.setText(text);
        recyclableTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        recyclableTextView.setTextSize(24);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
        recyclableTextView.setHeight(fixedHeightInPixels);

        if (header) {
            recyclableTextView.setTypeface(recyclableTextView.getTypeface(), Typeface.BOLD);
        } else if (!subheader) {
            recyclableTextView.setGravity(Gravity.CENTER);
            recyclableTextView.setBackgroundResource(index[0] % 2 == 0 ? R.drawable.cell_border : R.drawable.cell_border_white);
        } else {
            recyclableTextView.setBackgroundResource(index[0] % 2 == 0 ? R.drawable.cell_border : R.drawable.cell_border_white);
        }
        if (index[1] == 0)
            recyclableTextView.setTypeface(recyclableTextView.getTypeface(), Typeface.BOLD);


        return recyclableTextView;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<DeepSpace> sortedData = Handler.getInstance(getContext()).getSortedData(dataCol[position], "Test");
        //Integer[][] dataTable = new Integer[sortedData.size()][2];
        dataTable = new ArrayList<ArrayList<Integer>>();
        //List<List<Integer>> complexDataTable = new ArrayList<List<Integer>>();
        for (int i = 0; i < sortedData.size(); i++) {
            final int num = sortedData.get(i).getTeamNum(); // Get team number
            final int data = Integer.parseInt(sortedData.get(i).getData()); // Get data for team
            int index = checkTeamNum(dataTable, num);
            if (index == -1) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(num);
                temp.add(data);
                dataTable.add(temp);
            } else {
                List list = dataTable.get(index);
                list.add(data);
            }
        }
        //loop through the data
        int max = 0;
        int total = 0;
        ArrayList<Integer> temp;
        int size;
        int otherTemp;
        for (int j = 0; j < dataTable.size(); ++j) {
            temp = dataTable.get(j);
            size = temp.size() - 1;
            for (int k = 1; k < temp.size(); ) {
                if (temp.get(k) > max) max = temp.get(k);
                total += temp.get(k);
                temp.remove(k);
            }
            temp.add(max);
            temp.add(total / size);
            max = 0;
            total = 0;
        }
        sortArrayList(dataTable, 1);
        updateData();
    }

    private int checkTeamNum(List<ArrayList<Integer>> list, int num) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).get(0) == num) return i;
        }
        return -1;
    }

    private void sortArrayList(List<ArrayList<Integer>> list, final int sortBy) {
        Collections.sort(list, new Comparator<ArrayList<Integer>>() {
            public int compare(ArrayList<Integer> m1, ArrayList<Integer> m2) {
                if (sortBy > 0) {
                    if (m1.get(sortBy) == m2.get(sortBy)) {
                        return 0;
                    } else if (m1.get(sortBy) > m2.get(sortBy)) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    if (m1.get(0) == m2.get(0)) {
                        return 0;
                    } else if (m1.get(0) > m2.get(0)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateData() {
        TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        int scrollableColumnWidth = 30;
        int fixedRowHeight = 50;
        scrollablePart.removeAllViews();
        TableRow row;
        for (int i = 0; i < dataTable.size(); i++) {
            row = new TableRow(getActivity().getApplicationContext());
            row.setLayoutParams(wrapWrapTableRowParams);
            row.setGravity(Gravity.CENTER);
            for (int k = 0; k < dataTable.get(i).size(); k++) {
                row.addView(makeTableRowWithText(dataTable.get(i).get(k).toString(), scrollableColumnWidth, fixedRowHeight, false, false, new int[]{k, i}));
            }
            scrollablePart.addView(row, i);
        }
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
