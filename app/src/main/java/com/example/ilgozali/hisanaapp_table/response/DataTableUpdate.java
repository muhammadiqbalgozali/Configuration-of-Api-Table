package com.example.ilgozali.hisanaapp_table.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTableUpdate {
    @SerializedName("custno")
    @Expose
    private String custno;
    @SerializedName("finish")
    @Expose
    private String finish;
    @SerializedName("status")
    @Expose
    private String status;

    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
