package mapeditor.model;

public enum MEErrorMsg {
    FILE_NOT_EXIST(1000, "FILE NOT EXIST"),
    FILE_FORMAT_ERROR(1001, "INVALID FORMAT"),

    UNCONNECTED_GRAPH_ERROR(2001, "UNCONNECTED GRAPH"),
    COUNTRY_SEPARATE_COUNTINENT_ERROR(2002, "The country doesn't connect with any country that belongs to its continent"),
    MULTIPLE_CONTINENT_ERROR(2003, "COUNTRY BELONGS TO MULTIPLE CONTINENT");

    private String msg;
    private int errorCode;

    private MEErrorMsg(int code, String msg) {
        this.errorCode = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
