package org.stormroboticsnj.stormappmaster2019.Fragments;

import android.annotation.TargetApi;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.stormroboticsnj.stormappmaster2019.R;
import org.stormroboticsnj.stormappmaster2019.db.DeepSpace;
import org.stormroboticsnj.stormappmaster2019.db.Handler;
import org.w3c.dom.Text;
public class TeamData extends Fragment {
    /** Called when the activity is first created. */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TeamData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamData.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamData newInstance(String param1, String param2) {
        TeamData fragment = new TeamData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team_data, container, false);
        final TextView txtHeader = (TextView) view.findViewById(R.id.txtTopView);
        final TextView txtSummary = (TextView) view.findViewById(R.id.txtSummary);
        final EditText searchBar = (EditText) view.findViewById(R.id.editSearchNum);
        final Button searchBtn = (Button) view.findViewById(R.id.btnSearchTmDt);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String teamNum = searchBar.getText().toString();
                String insertErrString = "";
                if (teamNum.matches("")) insertErrString = "Please enter a team number.";
                else if(teamNum.length() > 5) insertErrString = "Team Number is too long.";
                if (!insertErrString.matches("")) { //If something is missing, display error to user.
                    Toast.makeText(getActivity(), "Error: " + insertErrString, Toast.LENGTH_SHORT).show();
                    return;
                }
                DeepSpace[] deep = Handler.getInstance(getActivity().getBaseContext()).getTeamData(teamNum);
                if (deep.length == 0) return;
                //System.out.println(deep[0].toString());
                TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                int[] fixedColumnWidths = new int[]{20, 20, 20, 20, 20};
                int[] scrollableColumnWidths = new int[]{20, 20, 20, 30, 30};
                int fixedRowHeight = 50;
                int fixedHeaderHeight = 60;

                TableRow row = new TableRow(getActivity().getApplicationContext());
        /*//header (fixed vertically)
        TableLayout header = (TableLayout) view.findViewById(R.id.table_header);
        row.setLayoutParams(wrapWrapTableRowParams);
        row.setGravity(Gravity.CENTER);
        row.addView(makeTableRowWithText("col 1", fixedColumnWidths[0], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 2", fixedColumnWidths[1], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 3", fixedColumnWidths[2], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 4", fixedColumnWidths[3], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 5", fixedColumnWidths[4], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 6", fixedColumnWidths[0], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 7", fixedColumnWidths[1], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 8", fixedColumnWidths[2], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 9", fixedColumnWidths[3], fixedHeaderHeight));
        row.addView(makeTableRowWithText("col 10", fixedColumnWidths[4], fixedHeaderHeight));
        header.addView(row);*/ // not in use
                //header (fixed horizontally)
                TableLayout fixedColumn = (TableLayout) view.findViewById(R.id.fixed_column);
                //rest of the table (within a scroll view)
                TableLayout scrollablePart = (TableLayout) view.findViewById(R.id.scrollable_part);
                final String[] rows = {"Match #", "Sandstorm", "Start Pos", "Pass Line", "Hatches", "Cargo", "Cargo",
                        "Level 3", "Level 2", "Level 1", "Ship", "Player", "Ground", "Hatches", "Level 3", "Level 2",
                        "Level 1", "Ship", "Player", "Ground", "Endgame", "Self", "Assist 1", "Assist 2"};
                //        = {
//                {"12", "18", "34", "35", "57", "24", "31", "51", "41"},
//                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
//                {"1", "0", "2", "2", "0", "3", "1", "2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"},
//                {"3", "0", "2", "3" ,"1", "0", "3" ,"2", "2"}};
                String[][] data = new String[rows.length][deep.length];
                for(int j = 0; j < deep.length; j++){
                    data[0][j] = Integer.toString(deep[j].getMatchNum());
                    data[1][j] = " ";
                    data[2][j] = Integer.toString(deep[j].getStartingPosition());
                    data[3][j] = deep[j].getPassAutoLine() == 1 ? "Y" : "N";
                    data[4][j] = Integer.toString(deep[j].getAutoHatches());
                    data[5][j] = Integer.toString(deep[j].getAutoCargo());
                    data[6][j] = " ";
                    data[7][j] = Integer.toString(deep[j].getCargoRT());
                    data[8][j] = Integer.toString(deep[j].getCargoRD());
                    data[9][j] = Integer.toString(deep[j].getCargoRU());;
                    data[10][j] = Integer.toString(deep[j].getCargoShip());
                    data[11][j] = Integer.toString(deep[j].getCargoPlayer());
                    data[12][j] = Integer.toString(deep[j].getCargoGround());
                    data[13][j] = " ";
                    data[14][j] = Integer.toString(deep[j].getHatchRT());
                    data[15][j] = Integer.toString(deep[j].getHatchRD());
                    data[16][j] = Integer.toString(deep[j].getHatchRU());;
                    data[17][j] = Integer.toString(deep[j].getHatchShip());
                    data[18][j] = Integer.toString(deep[j].getHatchPlayer());
                    data[19][j] = Integer.toString(deep[j].getHatchGround());
                    data[20][j] = " ";
                    data[21][j] = Integer.toString(deep[j].getSelfLevel());
                    data[22][j] = Integer.toString(deep[j].getAssistLevel());
                    data[23][j] = Integer.toString(deep[j].getAssistTwoLevel());
                    System.out.println(deep[j].toString());
                }
                String headerText = "Team " +  teamNum + " played " + data[0].length + " match" + (data[0].length==1 ? ":\n" : "es:\n");
                for (int l = 0; l < data[0].length; l++){
                    headerText += String.format(l == data[0].length - 1 ? "%s" : "%s, ", data[0][l]);
                }
                txtHeader.setText("Team Data: Team " + teamNum);
                txtSummary.setText(headerText);
                for(int i = 0; i < rows.length; i++) {
                    TextView fixedView = makeTableRowWithText(rows[i], scrollableColumnWidths[0], fixedRowHeight, (data[i][0].equals(" ")), true, new int[]{1,1});
                    fixedColumn.addView(fixedView);
                    row = new TableRow(getActivity().getApplicationContext());
                    row.setLayoutParams(wrapWrapTableRowParams);
                    row.setGravity(Gravity.CENTER);
                    for (int k = 0; k < deep.length; k++){
                        row.addView(makeTableRowWithText(data[i][k], scrollableColumnWidths[0], fixedRowHeight, (data[i][0].equals(" ")), false, new int[]{k,i}));
                    }
                    scrollablePart.addView(row);
                }
            }
        });
        return view;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        } else if (!subheader){
            recyclableTextView.setGravity(Gravity.CENTER);
            recyclableTextView.setBackgroundResource(index[0] % 2 == 0 ? R.drawable.cell_border : R.drawable.cell_border_white);
        } else {
            recyclableTextView.setBackgroundResource(index[0] % 2 == 0 ? R.drawable.cell_border : R.drawable.cell_border_white);
        }
        if (index[1] == 0) recyclableTextView.setTypeface(recyclableTextView.getTypeface(), Typeface.BOLD);
        return recyclableTextView;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
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
}
