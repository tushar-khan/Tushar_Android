package com.example.tusharkhan.fetchmail;

public class GmailList {
    private String from;
    private String to;
    private String subj;
    private String mailbody;
    private Integer value;
    public GmailList(){}

    public GmailList(String from, String to, String subj,String mailbody,Integer value) {
        this.from = from;
        this.to = to;
        this.subj = subj;
        this.mailbody=mailbody;
        this.value=value;
    }
    public Integer getValue() {
        return value;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMailbody() {
        return mailbody;
    }

    public String getSubj() {
        return subj;
    }
}
