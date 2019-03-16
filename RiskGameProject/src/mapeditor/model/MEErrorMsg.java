package mapeditor.model;

public enum MEErrorMsg {
    FILE_NOT_EXIST(1000, "FILE NOT EXIST"),
    EMPTY_MAP_ERROR(1001, "EMPTY MAP"),
    LOST_KEY_INFORMATION_ERROR(1002, "Lost Key Information"),

    UNCONNECTED_GRAPH_ERROR(2001, "Unconnected Graph"),
    ERROR(2002, "Error"),
    MULTIPLE_CONTINENT_ERROR(2003, "Country Belongs To Multiple Continent");

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
