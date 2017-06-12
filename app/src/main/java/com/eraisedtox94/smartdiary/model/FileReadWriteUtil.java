package com.eraisedtox94.smartdiary.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by spraful on 23-May-17.
 */

public class FileReadWriteUtil {


    //all this stub needs is a file name and location of the file
    public String readContentFromFileInExternalStorage(File file){
        StringBuilder content = new StringBuilder();
        String emptyString = "";

        String str;
        try {
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((str = bufferedReader.readLine()) != null) {
                content.append(str + "\n");
            }
            bufferedReader.close();

            Log.d("content read is:", content.toString());
        } catch (IOException e) {
            Log.d("file reading stub", "Aww ....!! resource not found :(");
            e.printStackTrace();
        }

        return content.toString();
    }

    //all this stub needs is a file name and location of the file
    public void writeContentToFileInExternalStorage(File file,String contentToBeWritten){
        Log.d("Gonna write",contentToBeWritten);
        try {
            FileWriter fileWriter = new FileWriter(file);
            //using buffered writer is fastest in writing I think
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contentToBeWritten);
            bufferedWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}