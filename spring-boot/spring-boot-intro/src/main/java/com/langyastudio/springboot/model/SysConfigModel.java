package com.langyastudio.springboot.model;

import org.springframework.stereotype.Component;

@Component
public class SysConfigModel {

    private String F_KEY;

    private String F_VALUE;

    private String F_NOTE;

    public String getFKEY() {
        return this.F_KEY;
    }

    public void setFKEY(String F_KEY) {
        this.F_KEY = F_KEY;
    }

    public String getFVALUE() {
        return this.F_VALUE;
    }

    public void setFVALUE(String F_VALUE) {
        this.F_VALUE = F_VALUE;
    }

    public String getFNOTE() {
        return this.F_NOTE;
    }

    public void setFNOTE(String F_NOTE) {
        this.F_NOTE = F_NOTE;
    }





}
