package main;


import response.Case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private static int currentId = 1;
    private static HashMap<Integer, Case> caseList = new HashMap<Integer, Case>();

    public static List<Case> getAllCase() {
        return new ArrayList<Case>(caseList.values());
    }

    public static int addCase (Case newCase) {
        int id = currentId++;
        newCase.setId(id);
        caseList.put(id, newCase);
        return id;
    }

    public static Case getCase (int id) {
        if (caseList.containsKey(id)) {
            return caseList.get(id);
        }
        return null;
    }

    public static void deleteCases() {
        caseList.clear();
        currentId = 1;
    }

    public static boolean deleteCase(int id) {
        if (caseList.containsKey(id)) {
            for (int i = (id); i < currentId; i++) {
                caseList.put(i, caseList.get(i + 1));
                caseList.get(i - 1).setId(i - 1);
            }
            currentId--;
            caseList.remove(currentId);
            return true;
        }
        return false;
    }

    public static void putCases(List<Case> list) {
        deleteCases();
        for (Case newCase : list) {
            newCase.setId(currentId);
            caseList.put(currentId, newCase);
            currentId++;
        }
    }

    public static boolean putCase(int id, Case newCase) {
        if (caseList.containsKey(id)) {
            newCase.setId(id);
            caseList.put(id, newCase);
            return true;
        }
        return false;
    }
}
