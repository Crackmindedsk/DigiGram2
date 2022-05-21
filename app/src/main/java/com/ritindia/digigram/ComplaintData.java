package com.ritindia.digigram;

public class ComplaintData {
    String id,category,date,complaint,status,warNO;

    public ComplaintData(String id, String category, String date, String complaint, String status, String warNO) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.complaint = complaint;
        this.status = status;
        this.warNO = warNO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarNO() {
        return warNO;
    }

    public void setWarNO(String warNO) {
        this.warNO = warNO;
    }
}
