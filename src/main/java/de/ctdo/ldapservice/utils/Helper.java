package de.ctdo.ldapservice.utils;

import java.util.List;

public final class Helper {
    private Helper() {}

    public static int getMaxIntInList(List<String> list) {
        int maxId = 0;
        for(String uidS: list) {
            int uid = Integer.parseInt(uidS);
            maxId = uid > maxId ? uid : maxId;
        }
        return maxId;
    }

}
