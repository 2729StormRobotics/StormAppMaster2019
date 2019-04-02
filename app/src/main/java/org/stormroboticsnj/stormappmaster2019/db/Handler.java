package org.stormroboticsnj.stormappmaster2019.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;
import android.util.Log;

//import com.openCsv.CSVWriter;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 113020 on 2/4/2019.
 */

public class Handler extends SQLiteOpenHelper {

    //the database
    private static final int DB_VERSION = 7;
    private static final String DB_NAME = "matchNum";
    public static final String DB_TABLE = "matchDb";

    //column names
    public static final String COL_ID = "_id";
    public static final String COL_TEAM = "team_num";
    public static final String COL_ALLIANCE = "alliance";
    public static final String COL_MATCH = "match_num";
    public static final String COL_STARTING_POSITION = "starting_position";
    public static final String COL_PASS_AUTO_LINE = "pass_auto_line";
    public static final String COL_AUTO_HATCHES = "auto_hatches";
    public static final String COL_AUTO_CARGO = "auto_cargo";
    public static final String COL_CARGO_RT = "cargo_lvl3";
    public static final String COL_CARGO_RD = "cargo_lvl2";
    public static final String COL_CARGO_RU = "cargo_lvl1";
    public static final String COL_CARGO_SHIP = "cargo_ship";
    public static final String COL_CARGO_PLAYER = "cargo_player";
    public static final String COL_CARGO_GROUND = "cargo_ground";
    public static final String COL_HATCH_RT = "hatch_lvl3";
    public static final String COL_HATCH_RD = "hatch_lvl2";
    public static final String COL_HATCH_RU = "hatch_lvl1";
    public static final String COL_HATCH_SHIP = "hatch_ship";
    public static final String COL_HATCH_PLAYER = "hatch_player";
    public static final String COL_HATCH_GROUND = "hatch_ground";
    public static final String COL_SELF_LEVEL = "self_level";
    public static final String COL_ASSIST_LEVEL = "assist_level";
    public static final String COL_ASSIST_TWO_LEVEL = "assist_two_level";
    public static final String COL_SPECIAL_CASES = "special_cases";


    //needed for getinstance()
    private static Handler dbH = null;
    private Context ctx;

    //Default constructor
    public Handler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Use only one instance of database, not many.
    public static Handler getInstance(Context context) {
        if (dbH == null) {
            dbH = new Handler(context.getApplicationContext());
        }
        return dbH;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Build SQL query to create the table
        String CREATE_TEAM = "CREATE TABLE " + DB_TABLE + "(" +
                //create columns
                COL_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                COL_TEAM + " INTEGER NOT NULL, " +
                COL_MATCH + " INTEGER NOT NULL, " +
                COL_ALLIANCE + " INTEGER NOT NULL, " +
                COL_STARTING_POSITION + " INTEGER NOT NULL, " +
                COL_PASS_AUTO_LINE + " INTEGER NOT NULL, " +
                COL_AUTO_HATCHES + " INTEGER NOT NULL, " +
                COL_AUTO_CARGO + " INTEGER NOT NULL, " +
                COL_CARGO_RT + " INTEGER NOT NULL, " +
                COL_CARGO_RD + " INTEGER NOT NULL, " +
                COL_CARGO_RU + " INTEGER NOT NULL, " +
                COL_CARGO_SHIP + " INTEGER NOT NULL, " +
                COL_CARGO_PLAYER+ " INTEGER NOT NULL, " +
                COL_CARGO_GROUND + " INTEGER NOT NULL, " +
                COL_HATCH_RT + " INTEGER NOT NULL, " +
                COL_HATCH_RD + " INTEGER NOT NULL, " +
                COL_HATCH_RU + " INTEGER NOT NULL, " +
                COL_HATCH_SHIP + " INTEGER NOT NULL, " +
                COL_HATCH_PLAYER+ " INTEGER NOT NULL, " +
                COL_HATCH_GROUND + " INTEGER NOT NULL, " +
                COL_SELF_LEVEL + " INTEGER NOT NULL, " +
                COL_ASSIST_LEVEL + " INTEGER NOT NULL, " +
                COL_ASSIST_TWO_LEVEL + " INTEGER NOT NULL, "+
                COL_SPECIAL_CASES + " INTEGER NOT NULL" + ")";
        //executes SQL query

        System.out.println("CREATE_TEAM: ");
        System.out.println(CREATE_TEAM);

        db.execSQL(CREATE_TEAM);

    }

    //If database structure changed, drop and rebuild. Should never actually be called.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, null, null); //Drop the table, clear data
    }

    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        ctx.deleteDatabase(db.getPath()); //Delete the database itself
    }

    public void deleteTeam(String teamNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DB_TABLE + " WHERE " + COL_TEAM + " = " + teamNum + ";");
    }

    public int dbSize() {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                ++i;
            } while (cursor.moveToNext());
        }
        return i;
    }

    //Creates a map data structure to add data
    public ContentValues addData(DeepSpace game){
        System.out.println("Game: " + game);
        ContentValues data = new ContentValues();
        data.put(COL_TEAM, game.getTeamNum());
        data.put(COL_MATCH, game.getMatchNum());
        data.put(COL_ALLIANCE, game.getAlliance());
        data.put(COL_STARTING_POSITION, game.getStartingPosition());
        data.put(COL_PASS_AUTO_LINE, game.getPassAutoLine());
        data.put(COL_AUTO_HATCHES, game.getAutoHatches());
        data.put(COL_AUTO_CARGO, game.getAutoCargo());
        data.put(COL_CARGO_RT, game.getCargoRT());
        data.put(COL_CARGO_RD, game.getCargoRD());
        data.put(COL_CARGO_RU, game.getCargoRU());
        data.put(COL_CARGO_SHIP, game.getCargoShip());
        data.put(COL_CARGO_PLAYER, game.getCargoPlayer());
        data.put(COL_CARGO_GROUND, game.getCargoGround());
        data.put(COL_HATCH_RT, game.getHatchRT());
        data.put(COL_HATCH_RD, game.getHatchRD());
        data.put(COL_HATCH_RU, game.getHatchRU());
        data.put(COL_HATCH_SHIP, game.getHatchShip());
        data.put(COL_HATCH_PLAYER, game.getHatchPlayer());
        data.put(COL_HATCH_GROUND, game.getHatchGround());
        data.put(COL_SELF_LEVEL, game.getSelfLevel());
        data.put(COL_ASSIST_LEVEL, game.getAssistLevel());
        data.put(COL_ASSIST_TWO_LEVEL, game.getAssistTwoLevel());
        data.put(COL_SPECIAL_CASES, game.getSpecialCases());
        System.out.println("Data: " + data);
        return data;
    }

    //Insert ContentValues into table
    public void addAllData(DeepSpace game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = addData(game);
        db.insert(DB_TABLE, null, values);
        db.close(); //Frees database resource
    }

    public void addCSVData() throws IOException, FileNotFoundException {
        SQLiteDatabase db = this.getWritableDatabase();
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, "match_list.csv");
        if (file.exists()) {
            Log.d("EXISTS", "FOUND");
        }

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fileReader);
            db.beginTransaction();
            String line = "";
            String str1 = "INSERT INTO " + DB_TABLE + "(" +
                    COL_TEAM + ", " +
                    COL_MATCH + ", " +
                    COL_ALLIANCE + ", " +
                    COL_STARTING_POSITION + ", " +
                    COL_PASS_AUTO_LINE + ", " +
                    COL_AUTO_HATCHES + ", " +
                    COL_AUTO_CARGO + ", " +
                    COL_CARGO_RT + ", " +
                    COL_CARGO_RD + ", " +
                    COL_CARGO_RU + ", " +
                    COL_CARGO_SHIP + ", " +
                    COL_CARGO_PLAYER + ", " +
                    COL_CARGO_GROUND + ", " +
                    COL_HATCH_RT + ", " +
                    COL_HATCH_RD + ", " +
                    COL_HATCH_RU + ", " +
                    COL_HATCH_SHIP + ", " +
                    COL_HATCH_PLAYER + ", " +
                    COL_HATCH_GROUND + ", " +
                    COL_SELF_LEVEL + ", " +
                    COL_ASSIST_LEVEL + ", " +
                    COL_ASSIST_TWO_LEVEL + ", " +
                    COL_SPECIAL_CASES + ");";
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                if (!str[0].contains("T")) {
                    sb.append(str[0] + ",");
                    sb.append(str[1] + ",");
                    sb.append(str[2] + ",");
                    sb.append(str[3] + ",");
                    sb.append(str[4] + ",");
                    sb.append(str[5] + ",");
                    sb.append(str[6] + ",");
                    sb.append(str[7] + ",");
                    sb.append(str[8] + ",");
                    sb.append(str[9] + ",");
                    sb.append(str[10] + ",");
                    sb.append(str[11] + ",");
                    //Log.d("sb", sb.toString());

                    db.execSQL(sb.toString());
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();

            // Inserting Row
            /*db.insert(TABLE_TEAM, null, values);*/
            db.close(); // Closing database connection
        } catch (FileNotFoundException e) {
            Log.d("FILENOTFOUND", e.toString());
        } catch (IOException e) {
            Log.d("IOEXCEPTION", e.toString());
        }

    }


    public List<DeepSpace> getAllTeams(){
        List<DeepSpace> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DB_TABLE;
        //Cursor executes queries
        Cursor cursor = db.rawQuery(query, null);

        //Makes sure that cursor can start at the dataset
        if(cursor.moveToFirst()) {
            do {
                DeepSpace tmp = new DeepSpace();
                //cursor.getint/getstring gets the value as the datatype from a column index
                tmp.setTeamNum(cursor.getInt(cursor.getColumnIndex(COL_TEAM))); //0
                tmp.setMatchNum(cursor.getInt(cursor.getColumnIndex(COL_MATCH))); //1
                tmp.setAlliance(cursor.getInt(cursor.getColumnIndex(COL_ALLIANCE))); //2
                tmp.setStartingPosition(cursor.getInt(cursor.getColumnIndex(COL_STARTING_POSITION))); //3
                tmp.setPassAutoLine(cursor.getInt(cursor.getColumnIndex(COL_PASS_AUTO_LINE))); //4
                tmp.setAutoHatches(cursor.getInt(cursor.getColumnIndex(COL_AUTO_HATCHES))); //5
                tmp.setAutoCargo(cursor.getInt(cursor.getColumnIndex(COL_AUTO_CARGO))); //6
                tmp.setCargoRT(cursor.getInt(cursor.getColumnIndex(COL_CARGO_RT))); //7
                tmp.setCargoRD(cursor.getInt(cursor.getColumnIndex(COL_CARGO_RD))); //8
                tmp.setCargoRU(cursor.getInt(cursor.getColumnIndex(COL_CARGO_RU))); //9
                tmp.setCargoShip(cursor.getInt(cursor.getColumnIndex(COL_CARGO_SHIP))); //10
                tmp.setCargoPlayer(cursor.getInt(cursor.getColumnIndex(COL_CARGO_PLAYER))); //11
                tmp.setCargoGround(cursor.getInt(cursor.getColumnIndex(COL_CARGO_GROUND))); //12
                tmp.setHatchRT(cursor.getInt(cursor.getColumnIndex(COL_HATCH_RT))); //13
                tmp.setHatchRD(cursor.getInt(cursor.getColumnIndex(COL_HATCH_RD))); //14
                tmp.setHatchRU(cursor.getInt(cursor.getColumnIndex(COL_HATCH_RU))); //15
                tmp.setHatchShip(cursor.getInt(cursor.getColumnIndex(COL_HATCH_SHIP))); //16
                tmp.setHatchPlayer(cursor.getInt(cursor.getColumnIndex(COL_HATCH_PLAYER))); //17
                tmp.setHatchGround(cursor.getInt(cursor.getColumnIndex(COL_HATCH_GROUND))); //18
                tmp.setSelfLevel(cursor.getInt(cursor.getColumnIndex(COL_SELF_LEVEL)));//19
                tmp.setAssistTwoLevel(cursor.getInt(cursor.getColumnIndex(COL_ASSIST_LEVEL)));//20
                tmp.setAssistTwoLevel(cursor.getInt(cursor.getColumnIndex(COL_ASSIST_TWO_LEVEL)));//21
                tmp.setSpecialCases(cursor.getInt(cursor.getColumnIndex(COL_SPECIAL_CASES))); //22
                list.add(tmp);
                //makes sure it executes so long as there is new entries to look at
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getNumTeams(List<DeepSpace> list) {
        int numTeams = 0;
        for (int i = 0; i < list.size(); i++) {
            numTeams++;
        }
        return numTeams;
    }

    public ArrayList<DeepSpace> getSortedData(String col, String header) {
        String query = "SELECT * FROM " + DB_TABLE + " ORDER BY " + col + " DESC";
        ArrayList<DeepSpace> teams = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex(col));
                //if(col.equals(COL_FLOOR_INTAKE) || col.equals(COL_TOP_INTAKE) || col.equals(COL_ONE_ROBOT)
                //        || col.equals(COL_TWO_ROBOT)) data = data.equals("1") ?  "Yes" : "No";
                DeepSpace temp = new DeepSpace();
                temp.setTeamNum(cursor.getInt(cursor.getColumnIndex(COL_TEAM)));
                temp.setData(data);
                teams.add(temp);
            }while(cursor.moveToNext());
        }
        db.close();
        return teams;
    }

    public DeepSpace getTotalData(String teamNum) {
        int startingPosition = 0, passAutoLine = 0, autoHatches = 0, autoCargo = 0, cargoRT = 0, cargoRD = 0, cargoRU = 0, cargoShip = 0,
                cargoPlayer = 0, cargoGround = 0, hatchRT = 0, hatchRD = 0, hatchRU = 0, hatchShip = 0, hatchPlayer = 0,
                hatchGround = 0, selfLevel = 0, assistLevel = 0, assistTwoLevel = 0, specialCases = 0, count = 0;
        String query = "SELECT * FROM " + DB_TABLE + " WHERE " + COL_TEAM + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{teamNum});
        if (cursor.moveToFirst()) {
            do {
                startingPosition += cursor.getInt(cursor.getColumnIndex(COL_STARTING_POSITION));
                passAutoLine += cursor.getInt(cursor.getColumnIndex(COL_PASS_AUTO_LINE));
                autoHatches += cursor.getInt(cursor.getColumnIndex(COL_AUTO_HATCHES));
                autoCargo += cursor.getInt(cursor.getColumnIndex(COL_AUTO_CARGO));
                cargoRT += cursor.getInt(cursor.getColumnIndex(COL_CARGO_RT));
                cargoRD += cursor.getInt(cursor.getColumnIndex(COL_CARGO_RD));
                cargoRU += cursor.getInt(cursor.getColumnIndex(COL_CARGO_RU));
                cargoShip += cursor.getInt(cursor.getColumnIndex(COL_CARGO_SHIP));
                cargoPlayer += cursor.getInt(cursor.getColumnIndex(COL_CARGO_PLAYER));
                cargoGround += cursor.getInt(cursor.getColumnIndex(COL_CARGO_GROUND));
                hatchRT += cursor.getInt(cursor.getColumnIndex(COL_HATCH_RT));
                hatchRD += cursor.getInt(cursor.getColumnIndex(COL_HATCH_RD));
                hatchRU += cursor.getInt(cursor.getColumnIndex(COL_HATCH_RU));
                hatchShip += cursor.getInt(cursor.getColumnIndex(COL_HATCH_SHIP));
                hatchPlayer += cursor.getInt(cursor.getColumnIndex(COL_HATCH_PLAYER));
                hatchGround += cursor.getInt(cursor.getColumnIndex(COL_HATCH_GROUND));
                selfLevel += cursor.getInt(cursor.getColumnIndex(COL_SELF_LEVEL));
                assistLevel += cursor.getInt(cursor.getColumnIndex(COL_ASSIST_LEVEL));
                assistTwoLevel += cursor.getInt(cursor.getColumnIndex(COL_ASSIST_TWO_LEVEL));
                ++count;
            } while (cursor.moveToNext());
        }
        DeepSpace launch = new DeepSpace();
        launch.setMatchNum(count);
        launch.setStartingPosition(startingPosition);
        launch.setPassAutoLine(passAutoLine);
        launch.setAutoHatches(autoHatches);
        launch.setAutoCargo(autoCargo);
        launch.setCargoRT(cargoRT);
        launch.setCargoRD(cargoRD);
        launch.setCargoRU(cargoRU);
        launch.setCargoShip(cargoShip);
        launch.setCargoPlayer(cargoPlayer);
        launch.setCargoGround(cargoGround);
        launch.setHatchRT(hatchRT);
        launch.setHatchRD(hatchRD);
        launch.setHatchRU(hatchRU);
        launch.setHatchShip(hatchShip);
        launch.setHatchPlayer(hatchPlayer);
        launch.setHatchGround(hatchGround);
        launch.setSelfLevel(selfLevel);
        launch.setAssistLevel(assistLevel);
        launch.setAssistTwoLevel(assistTwoLevel);
        db.close();
        return launch;
    }

    public DeepSpace[] getMatchUpData(String matchNum) {
        int team = 0, alliance = 0, startingPosition = 0, passAutoLine = 0, autoHatches = 0, autoCargo = 0, cargoRT = 0,
                cargoRD = 0, cargoRU = 0, cargoShip = 0, cargoPlayer = 0, cargoGround = 0, hatchRT = 0, hatchRD = 0,
                hatchRU = 0, hatchShip = 0, hatchPlayer = 0, hatchGround = 0, selfLevel = 0, assistLevel = 0,
                assistTwoLevel = 0, specialCases = 0;
        String teamStr = "";
        String hangNotes = "";
        String strategy = "";
        List<DeepSpace> launchAr = new LinkedList<DeepSpace>();

        String query = "SELECT * FROM " + DB_TABLE + " WHERE " + COL_MATCH + "=? ORDER BY " + COL_ALLIANCE + ", " + COL_TEAM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{matchNum});
        if (cursor.moveToFirst()) {
            do {
                DeepSpace launch = new DeepSpace();
                team = cursor.getInt(cursor.getColumnIndex(COL_TEAM));
                alliance = cursor.getInt(cursor.getColumnIndex(COL_ALLIANCE));
                startingPosition = cursor.getInt(cursor.getColumnIndex(COL_STARTING_POSITION));
                passAutoLine = cursor.getInt(cursor.getColumnIndex(COL_PASS_AUTO_LINE));
                autoHatches = cursor.getInt(cursor.getColumnIndex(COL_AUTO_HATCHES));
                autoCargo = cursor.getInt(cursor.getColumnIndex(COL_AUTO_CARGO));
                cargoRT = cursor.getInt(cursor.getColumnIndex(COL_CARGO_RT));
                cargoRD = cursor.getInt(cursor.getColumnIndex(COL_CARGO_RD));
                cargoRU = cursor.getInt(cursor.getColumnIndex(COL_CARGO_RU));
                cargoShip = cursor.getInt(cursor.getColumnIndex(COL_CARGO_SHIP));
                cargoGround = cursor.getInt(cursor.getColumnIndex(COL_CARGO_GROUND));
                selfLevel = cursor.getInt(cursor.getColumnIndex(COL_SELF_LEVEL));
                assistLevel = cursor.getInt(cursor.getColumnIndex(COL_ASSIST_LEVEL));
                assistTwoLevel = cursor.getInt(cursor.getColumnIndex(COL_ASSIST_TWO_LEVEL));
                specialCases = cursor.getInt(cursor.getColumnIndex(COL_SPECIAL_CASES));
                System.out.println(team);
                launch.setTeamNum(team);
                launch.setAlliance(alliance);
                launch.setStartingPosition(startingPosition);
                launch.setPassAutoLine(passAutoLine);
                launch.setAutoHatches(autoHatches);
                launch.setAutoCargo(autoCargo);
                launch.setCargoRT(cargoRT);
                launch.setCargoRD(cargoRD);
                launch.setCargoRU(cargoRU);
                launch.setCargoShip(cargoShip);
                launch.setCargoPlayer(cargoPlayer);
                launch.setCargoGround(cargoGround);
                launch.setHatchRT(hatchRT);
                launch.setHatchRD(hatchRD);
                launch.setHatchRU(hatchRU);
                launch.setHatchShip(hatchShip);
                launch.setHatchPlayer(hatchPlayer);
                launch.setHatchGround(hatchGround);
                launch.setSelfLevel(selfLevel);
                launch.setAssistLevel(assistLevel);
                launch.setAssistTwoLevel(assistTwoLevel);
                launch.setSpecialCases(specialCases);
                launchAr.add(launch); //?
            } while (cursor.moveToNext());
        }
        db.close();
        DeepSpace
                [] pu = new DeepSpace
                [launchAr.size()];
        launchAr.toArray(pu);
        return pu;
    }

    public DeepSpace[] getTeamData(String teamNum) {
        int match = 0, alliance = 0, startingPosition = 0, passAutoLine= 0, autoHatches = 0, autoCargo = 0,
                cargoRT = 0, cargoRD = 0, cargoRU = 0, cargoShip = 0, cargoPlayer = 0, cargoGround = 0,
                hatchRT = 0, hatchRD = 0, hatchRU = 0, hatchShip = 0, hatchPlayer = 0, hatchGround = 0,
                selfLevel =0, assistLevel = 0, assistTwoLevel = 0, specialCases = 0;
        String matchStr = "";
        String strategy = "";
        List<DeepSpace> launchAr = new LinkedList<DeepSpace>();

        String query = "SELECT * FROM " + DB_TABLE + " WHERE " + COL_TEAM + "=? ORDER BY " + COL_ALLIANCE + ", " + COL_MATCH;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{teamNum});
        if (cursor.moveToFirst()) {
            do {
                DeepSpace launch = new DeepSpace();
                match = cursor.getInt(cursor.getColumnIndex(COL_MATCH));
                alliance = cursor.getInt(cursor.getColumnIndex(COL_ALLIANCE));
                startingPosition = cursor.getInt(cursor.getColumnIndex(COL_STARTING_POSITION));
                passAutoLine = cursor.getInt(cursor.getColumnIndex(COL_PASS_AUTO_LINE));
                autoHatches = cursor.getInt(cursor.getColumnIndex(COL_AUTO_HATCHES));
                autoCargo = cursor.getInt(cursor.getColumnIndex(COL_AUTO_CARGO));
                cargoRT = cursor.getInt(cursor.getColumnIndex(COL_CARGO_RT));
                cargoRD = cursor.getInt(cursor.getColumnIndex(COL_CARGO_RD));
                cargoRU = cursor.getInt(cursor.getColumnIndex(COL_CARGO_RU));
                cargoShip = cursor.getInt(cursor.getColumnIndex(COL_CARGO_SHIP));
                cargoPlayer = cursor.getInt(cursor.getColumnIndex(COL_CARGO_PLAYER));
                cargoGround = cursor.getInt(cursor.getColumnIndex(COL_CARGO_GROUND));
                hatchRT = cursor.getInt(cursor.getColumnIndex(COL_HATCH_RT));
                hatchRD = cursor.getInt(cursor.getColumnIndex(COL_HATCH_RD));
                hatchRU = cursor.getInt(cursor.getColumnIndex(COL_HATCH_RU));
                hatchShip = cursor.getInt(cursor.getColumnIndex(COL_HATCH_SHIP));
                hatchPlayer = cursor.getInt(cursor.getColumnIndex(COL_HATCH_PLAYER));
                hatchGround = cursor.getInt(cursor.getColumnIndex(COL_HATCH_GROUND));
                selfLevel = cursor.getInt(cursor.getColumnIndex(COL_SELF_LEVEL));
                assistLevel = cursor.getInt(cursor.getColumnIndex(COL_ASSIST_LEVEL));
                assistTwoLevel = cursor.getInt(cursor.getColumnIndex(COL_ASSIST_TWO_LEVEL));
                //specialCases = cursor.getInt(cursor.getColumnIndex(COL_SPECIAL_CASES));

                System.out.println(match);
                launch.setMatchNum(match);
                launch.setAlliance(alliance);
                launch.setStartingPosition(startingPosition);
                launch.setPassAutoLine(passAutoLine);
                launch.setAutoHatches(autoHatches);
                launch.setAutoCargo(autoCargo);
                launch.setCargoRT(cargoRT);
                launch.setCargoRD(cargoRD);
                launch.setCargoRU(cargoRU);
                launch.setCargoShip(cargoShip);
                launch.setCargoPlayer(cargoPlayer);
                launch.setCargoGround(cargoGround);
                launch.setHatchRT(hatchRT);
                launch.setHatchRD(hatchRD);
                launch.setHatchRU(hatchRU);
                launch.setHatchShip(hatchShip);
                launch.setHatchPlayer(hatchPlayer);
                launch.setHatchGround(hatchGround);
                launch.setSelfLevel(selfLevel);
                launch.setAssistLevel(assistLevel);
                launch.setAssistTwoLevel(assistTwoLevel);

                launchAr.add(launch);
            } while (cursor.moveToNext());
        }
        db.close();
        DeepSpace[] pu = new DeepSpace[launchAr.size()];
        launchAr.toArray(pu);
        return pu;
    }

    //****GRAPH STUFF****//

    /*public int[] getTeamGraphSumData(String[] headers, String team){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM \"" + DB_TABLE + "\" WHERE \"" + COL_TEAM + "\"=?";
        int[] data = new int[headers.length];
        Cursor cursor = db.rawQuery(query, new String[]{team});
        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < headers.length; i++){
                    try {
                        data[i] += Integer.parseInt(cursor.getString(cursor.getColumnIndex(headers[i])));
                    } catch (NumberFormatException nfe) {
                        // Failed to parse a number from the string, skipping to the next value.
                    }
                }
            }while(cursor.moveToNext());
        }
        db.close();
        return data;
    }
    public int[] getTeamGraphAvgData(String[] headers, String team){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM \"" + DB_TABLE + "\" WHERE \"" + COL_TEAM + "\"=?";
        int[] data = new int[headers.length+1];
        Cursor cursor = db.rawQuery(query, new String[]{team});
        int count = 0;
        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < headers.length; ++i){
                    try {
                        data[i] += Integer.parseInt(cursor.getString(cursor.getColumnIndex(headers[i])));
                    } catch (NumberFormatException nfe) {
                        data[i] += 0;
                    }
                }
                ++count;
            }while(cursor.moveToNext());
        }
        data[data.length-1] = count;
        db.close();
        return data;
    }*/

    public void exec(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public void exportCsv() {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, "csvname.csv");
        System.out.println("CSV File location: " + file.getAbsoluteFile());
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            String query = "SELECT * FROM " + DB_TABLE + " ORDER BY " + COL_TEAM + " DESC";
            ArrayList<DeepSpace
                    > teams = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    List<String> tmpList = new LinkedList<>();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        tmpList.add(cursor.getString(i));
                    }
                    String arrStr[] = {};
                    arrStr = tmpList.toArray(arrStr);
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
            cursor.close();
            db.close();
        } catch (Exception sqlEx) {
            Log.e("Handler", sqlEx.getMessage(), sqlEx);
        }


        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "AnalysisData.csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        CSVWriter writer;
        // File exist
        try {
            if (f.exists() && !f.isDirectory()) {
                FileWriter mFileWriter = new FileWriter(filePath, false);
                writer = new CSVWriter(mFileWriter);
            } else {
                writer = new CSVWriter(new FileWriter(filePath));
            }
            String[] data = {"Ship Name", "Scientist Name", "..."};
            writer.writeNext(data);
            writer.close();
        } catch (IOException ioe) {
            System.err.println("Failed to write CSV file.");
        }

    }
    public int countResults(String teamNum, String matchNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM \"" + DB_TABLE + "\" WHERE \"" + COL_TEAM + "\"=\"" + teamNum + "\" AND \"" + COL_MATCH + "\"=\"" + matchNum + "\"", new String[]{});
        return cursor.getCount();
    }

    public void removeDuplicates(String teamNum, String matchNum){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT \"" + COL_ID + "\" FROM \"" + DB_TABLE + "\" WHERE \"" + COL_TEAM + "\"=\"" + teamNum + "\" AND \"" + COL_MATCH + "\"=\"" + matchNum + "\"", new String[]{});
        if (cursor.moveToFirst()){
            while(cursor.moveToNext()){
                db.execSQL("DELETE FROM \"" + DB_TABLE + "\" WHERE \"" + COL_ID +"\" = \"" + cursor.getString(cursor.getColumnIndex(COL_ID)) + "\"");
            }
        }
    }
}

