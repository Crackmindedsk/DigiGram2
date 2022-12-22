package com.ritindia.digigram.model;

public class ComplaintModel {
    private int cdid;
    private String ctype;
    private String details;
    private String registereddate;
    private String status;
    private String ward;

    public ComplaintModel(int cdid, String ctype, String details, String registereddate, String status, String ward) {
        this.cdid = cdid;
        this.ctype = ctype;
        this.details = details;
        this.registereddate = registereddate;
        this.status = status;
        this.ward = ward;
    }

    public int getCdid() {
        return cdid;
    }

    public String getCtype() {
        return ctype;
    }

    public String getDetails() {
        return details;
    }

    public String getRegistereddate() {
        return registereddate;
    }

    public String getStatus() {
        return status;
    }

    public String getWard() {
        return ward;
    }
}
