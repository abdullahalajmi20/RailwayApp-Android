package com.sn.railway.objects;

import com.google.gson.annotations.SerializedName;

public class Train_Object extends CommonResponse_Object{

    private String trainno;
    private String name;
    @SerializedName("class")
    private String classX;
    private String rundays;
    private String from;
    private String fromname;
    private String dep;
    private String to;
    private String toname;
    private String arr;
    private String type;
    private String datefrom;
    private String dateto;
    private String traveltime;

    public String getTrainno() {
        return trainno;
    }

    public void setTrainno(String trainno) {
        this.trainno = trainno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getRundays() {
        return rundays;
    }

    public void setRundays(String rundays) {
        this.rundays = rundays;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    public String getDateto() {
        return dateto;
    }

    public void setDateto(String dateto) {
        this.dateto = dateto;
    }

    public String getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(String traveltime) {
        this.traveltime = traveltime;
    }
}
