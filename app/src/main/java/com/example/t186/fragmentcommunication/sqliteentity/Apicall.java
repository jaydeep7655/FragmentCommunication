
package com.example.t186.fragmentcommunication.sqliteentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Apicall implements Serializable {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
