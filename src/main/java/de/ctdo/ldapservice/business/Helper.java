package de.ctdo.ldapservice.business;

import java.util.List;

/**
 * @author: lucas
 * @date: 02.04.12 19:37
 */
public class Helper {


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
