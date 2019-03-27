package mapeditor.model;

/**
 * @author TX
 * @since build1
 **/
public enum MEErrorMsg {
    FILE_NOT_EXIST(1000, "FILE NOT EXIST\n"),
    FILE_FORMAT_ERROR(1001, "INVALID FORMAT\n"),
    FILE_CONTINENT_ERROR(1002, "INVALID CONTINENT\n"),

    UNCONNECTED_GRAPH_ERROR(2001, "UNCONNECTED GRAPH\n"),
    UNCONNECTED_CONTINENT(2002, "UNCONNECTED CONTINENT\n"),
    COUNTRY_SEPARATE_CONTINENT_ERROR(2003, "COUNTRY DOES NOT CONNECT WITH ANY OTHER COUNTRY ON ITS CONTINENT\n"),
    MULTIPLE_CONTINENT_ERROR(2004, "COUNTRY BELONGS TO MULTIPLE CONTINENT\n");

    private String msg;

    private MEErrorMsg(int code, String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
