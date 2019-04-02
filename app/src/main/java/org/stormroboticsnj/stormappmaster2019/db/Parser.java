package org.stormroboticsnj.stormappmaster2019.db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.Result;
import com.opencsv.CSVWriter;
import org.stormroboticsnj.stormappmaster2019.MainActivity;



/**
 * Created by sanju_000 on 2/7/2018.
 */

public class Parser {
    public static void parse(Result result, Activity activity) throws IOException {
        String input = result.getText();
        if (!input.contains("@stormscouting")) {
            Intent i = new Intent(activity, MainActivity.class);
            activity.startActivity(i);
            Toast.makeText((Context) activity.getApplicationContext(), (CharSequence) "Scan a Valid QR Code", Toast.LENGTH_LONG).show();
            return;
        }
        input = input.replaceAll("@stormscouting", "");
        List<String[]> list = new ArrayList<String[]>();
        String[] matches = input.split("[|]");
        int startIndex = 0;
        for (int j = 0; j < matches.length; ++j) {
            String[] temp = matches[j].split("[\t]");
            DeepSpace deepSpace = new DeepSpace(temp);
            System.out.println(deepSpace.toString());
            Handler.getInstance(activity.getApplicationContext()).addAllData(deepSpace);
            list.add(startIndex, temp);
            ++startIndex;

        }
        try {
            makeCSV(list);
            Intent main = new Intent(activity, MainActivity.class);
            activity.startActivity(main);
            Toast.makeText((Context) activity.getApplicationContext(), (CharSequence) "Successfully Scanned QR Code", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.d("File Exception", e.toString());
        }
    }

    private static void makeCSV(List list) throws IOException {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath());
            dir.mkdirs();
            File file = new File(dir, "match_list.csv");
            CSVWriter writer;
            if (!file.exists()) {
                String[] columns = new String[23];
                columns[0] = "Team number";
                columns[1] = "Match number";
                columns[2] = "Alliance";
                columns[3] = "Starting Position";
                columns[4] = "Pass Auto Line";
                columns[5] = "Auto Hatches";
                columns[6] = "Auto Cargo";
                columns[7] = "Cargo Rocket 3";
                columns[8] = "Cargo Rocket 2";
                columns[9] = "Cargo Rocket 1";
                columns[10] = "Cargo Ship";
                columns[11] = "Cargo Player Station";
                columns[12] = "Cargo Ground Pickup";
                columns[13] = "Hatch Rocket 3";
                columns[14] = "Hatch Rocket 2";
                columns[15] = "Hatch Rocket 1";
                columns[16] = "Hatch Ship";
                columns[17] = "Hatch Player Station";
                columns[18] = "Hatch Ground Pickup";
                columns[19] = "Self Level";
                columns[20] = "Assist Level";
                columns[21] = "Assist Two Level";
                columns[22] = "Special CASEERWRQ";
                list.add(0, columns);
            }
            writer = new CSVWriter((new FileWriter(file, true)), ',', '"', '\\', "\n");
            writer.writeAll(list);
            if (file.exists()) {
                Log.d("FileDir", file.getAbsolutePath());
            }
            writer.close();
        } catch (IOException e) {
            Log.d("File Exception", e.toString());
        }

    }
}
