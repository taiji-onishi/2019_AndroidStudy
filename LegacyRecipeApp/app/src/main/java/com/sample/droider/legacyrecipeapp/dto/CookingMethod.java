package com.sample.droider.legacyrecipeapp.dto;

import com.google.gson.annotations.SerializedName;

public class CookingMethod {

    @SerializedName("procedure_no")
    private String procedureNo;

    @SerializedName("procedure")
    private String procedure;

    public String getProcedureNo() {
        return procedureNo;
    }

    public void setProcedureNo(String procedureNo) {
        this.procedureNo = procedureNo;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }
}
