package aorlov.ashdb.core;


public class XSLMetaData {

    public static final String SHEET = "Sheet";
    public static final String COLUMN = "Column";
    public static final String ROW = "Row";


    private int columnNum;
    private int rownum;
    private String sheetName;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    @Override
    public String toString(){
        StringBuffer s = new StringBuffer("XSLMetaData sheetName[");
        s.append(sheetName);
        s.append("] columnNum [");
        s.append(columnNum);
        s.append("] rownum [");
        s.append(rownum);
        s.append(']');
        return s.toString();

    }

}
