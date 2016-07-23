package Renamer.Core;

/**
 * This is filter rule
 * Created by sayarasin on 07/20/2016.
 */
public class Rule {

    private String  suffix = "*";
    private int     deeps  = -1;
    private boolean covert = false;


    /**
     * Set file suffix filter, if set to '*', no suffix
     * will be screened
     * @return File suffix
     */
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Set scan deeps
     * @return Deeps, must >=0 or -1, if set to -1,
     * there is no limited
     */
    public int getDeeps() {
        return deeps;
    }
    public void setDeeps(int deeps) {
        this.deeps = deeps;
    }


    public boolean getCovert() {
        return covert;
    }
    public void setCovert(boolean covert) {
        this.covert = covert;
    }
}
