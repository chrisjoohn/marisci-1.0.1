package datasource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSV {

    public ArrayList<ArrayList<String>> readCSV(String csvPath){
        String csvFile = csvPath;
        String splitBy = ",";
        String line = "";

        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            boolean skip = true;
            while((line = br.readLine()) != null){

                if(skip){ //do not read headers;
                    skip = false;
                    continue;
                }

                ArrayList<String> student = new ArrayList<>();
                String[] items = line.split(splitBy);

                for (int i=0; i<items.length;i++){
                    student.add(items[i].trim());
                }
                list.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

//    public static void main(String[] args) {
//        for(ArrayList<String> student : readCSV("/home/cj/IdeaProjects/StudentList.csv")){
//            for(int i=0; i<student.size(); i++){
//                System.out.println(student.get(i));
//            }
//            System.out.println();
//        }
//    }

}
