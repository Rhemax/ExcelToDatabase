package com.fly2.utils;

public class KategorieTyp {

    public static final String PERSON = "Person";
    public static final String GESCHAFT = "Geschaft";
    public static final String UNDEFINED = "Undefined";

    private static final String[] MANNLICH_ANREDE = {"Herr", "Mr", "Mr.", "Sr.", "M", "Dr.", "Herr Dr", "Herr Pr", "Senor", "Mister", "Messrs"};
    private static final String[] WEIBLICH_ANREDE = {"Frau", "Mrs.", "Sra.", "Mrs", "Mme", "Miss", "Ms"};
    private static final String[] GESCHAFT_ANREDE = {"Firma", "Fa", "Spett.l", "Comp", "Ets.", "Company", "Spediti", 
                                                    "SPETTAB", "Buchhan", "Fa.", "Autohau"};

    public static boolean isMannlich(String anrede) {
        for (String mannlich : MANNLICH_ANREDE) {
            if (anrede.equalsIgnoreCase(mannlich)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWeiblich(String anrede) {
        for (String weiblich : WEIBLICH_ANREDE) {
            if (anrede.equalsIgnoreCase(weiblich)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPerson(String anrede) {
        return isMannlich(anrede) || isWeiblich(anrede);
    }

    public static boolean isGeschaft(String anrede) {
        for (String geschaft : GESCHAFT_ANREDE) {
            if (anrede.equalsIgnoreCase(geschaft)) {
                return true;
            }
        }
        return false;
    }
}
