package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class DeviceItemBean extends CommonResponse implements Comparable<DeviceItemBean>, Serializable {


    /**
     * eui64 : 24EE1707592CB313
     * temp : 30.310
     * address : 天基云蒙庄园D-71502
     * humidity : 42.4800
     * time : 2018-07-23 17:03:45
     * community : 天基云蒙庄园
     */

    private String eui64;
    private String temp;
    private String address;
    private String humidity;
    private String time;
    private String community;

    public DeviceItemBean(String eui64, String temp, String address, String humidity, String community) {
        this.eui64 = eui64;
        this.temp = temp;
        this.address = address;
        this.humidity = humidity;
        this.community = community;
    }

    public String getEui64() {
        return eui64;
    }

    public void setEui64(String eui64) {
        this.eui64 = eui64;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    @Override
    public int compareTo(@NonNull DeviceItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
