package edu.mobapde.convertbycornetto;

/**
 * Created by ngoc on 3/2/2016.
 */
public class Unit {

    public final static String TABLE_NAME = "unit";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_UNITNAME = "unitname";
    public final static String COLUMN_EQUIVALENCE = "equivalence";

    private int id;
    private String unitName;
    private double cornettoEquivalence;

    public Unit() {
    }

    public Unit(String unitName, double cornettoEquivalence) {
        this.unitName = unitName;
        this.cornettoEquivalence = cornettoEquivalence;
    }

    public Unit(int id, String unitName, double cornettoEquivalence) {
        this.id = id;
        this.unitName = unitName;
        this.cornettoEquivalence = cornettoEquivalence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getCornettoEquivalence() {
        return cornettoEquivalence;
    }

    public void setCornettoEquivalence(double cornettoEquivalence) {
        this.cornettoEquivalence = cornettoEquivalence;
    }
}
