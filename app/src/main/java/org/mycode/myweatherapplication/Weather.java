package org.mycode.myweatherapplication;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("icon")
    private String icon;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
