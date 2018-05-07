package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BirthdayTable {
    public static String[][] loadBirthdayTable() {
        ArrayList<Integer> tempArr = new ArrayList<>();
        String[][] arrayOfClients;
        try {
            Calendar dateOfBirthday = Calendar.getInstance();
            Calendar dateNow = Calendar.getInstance();
            SimpleDateFormat fotmat1 = new SimpleDateFormat("dd.MM");
            String stringForTransformDate = fotmat1.format(dateNow.getTime());
            //dateNow.setTime(fotmat1.parse(dateNow.toString()));
            dateNow.setTime(fotmat1.parse(stringForTransformDate));
            for (int i = 0; i < ClientTable.clientInfo.length; i++) {
                dateOfBirthday.setTime(fotmat1.parse(ClientTable.clientInfo[i][7]+""));
                if ((dateNow.getTime()).compareTo(dateOfBirthday.getTime()) == 0){
                    tempArr.add(i);
                }
            }
            arrayOfClients = new String[tempArr.size()][3];
            for (int i = 0; i < arrayOfClients.length; i++) {
                arrayOfClients[i][0] = ClientTable.clientInfo[tempArr.get(i)][0]+"";
                arrayOfClients[i][1] = ClientTable.clientInfo[tempArr.get(i)][1]+"";
                arrayOfClients[i][2] = ClientTable.clientInfo[tempArr.get(i)][2]+"";
            }
            return arrayOfClients;

            }
        catch (Exception e){
            e.printStackTrace();
        }
        return new String[0][3];

    }
}
