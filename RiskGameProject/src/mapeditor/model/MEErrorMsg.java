package mapeditor.model;

public enum MEErrorMsg {
    FILE_NOT_EXIST(1000, "FILE NOT EXIST\n"),
    FILE_FORMAT_ERROR(1001, "INVALID FORMAT\n"),

    UNCONNECTED_GRAPH_ERROR(2001, "UNCONNECTED GRAPH\n"),
    UNCONNECTED_CONTINENT(2002, "UNCONNECTED CONTINENT\n"),
    COUNTRY_SEPARATE_CONTINENT_ERROR(2003, "COUNTRY DOES NOT CONNECT WITH ANY OTHER COUNTRY ON ITS CONTINENT\n"),
    MULTIPLE_CONTINENT_ERROR(2004, "COUNTRY BELONGS TO MULTIPLE CONTINENT\n");

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
