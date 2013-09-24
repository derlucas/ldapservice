package de.ctdo.ldapservice.utils;

import java.util.List;

public class Helper {
    private Helper() {}

    public static int getMaxIntInList(List list) {
        int maxId = 0;
        for(Object uidS: list) {
            if( uidS != null && uidS instanceof String) {
                int uid = Integer.parseInt(uidS.toString());
                maxId = uid > maxId ? uid : maxId;
            }
        }
        return maxId;
    }

}
