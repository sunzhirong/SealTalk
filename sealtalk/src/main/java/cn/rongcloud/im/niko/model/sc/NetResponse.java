package cn.rongcloud.im.niko.model.sc;

public class NetResponse<T> {


    /**
     * RsData : {"Firstname":"string","Lastname":"string","Email":"string","Phone":"string","Gender":true,"DOB":"2020-07-11T06:10:22.204Z","Address":"string","Address2":"string","City":"string","State":"string","Country":"string","Origin":"string","Height":0,"Weight":0}
     * RsCode : 0
     * RsMsg : string
     * RsDetail : {}
     * RsNote : string
     */

    private T RsData;
    private int RsCode;
    private String RsMsg;
    private RsDetailBean RsDetail;
    private String RsNote;

    public T getRsData() {
        return RsData;
    }

    public void setRsData(T RsData) {
        this.RsData = RsData;
    }

    public int getRsCode() {
        return RsCode;
    }

    public void setRsCode(int RsCode) {
        this.RsCode = RsCode;
    }

    public String getRsMsg() {
        return RsMsg;
    }

    public void setRsMsg(String RsMsg) {
        this.RsMsg = RsMsg;
    }

    public RsDetailBean getRsDetail() {
        return RsDetail;
    }

    public void setRsDetail(RsDetailBean RsDetail) {
        this.RsDetail = RsDetail;
    }

    public String getRsNote() {
        return RsNote;
    }

    public void setRsNote(String RsNote) {
        this.RsNote = RsNote;
    }


    public static class RsDetailBean {
    }
}
