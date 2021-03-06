package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class AddressItemBean extends CommonResponse implements Comparable<AddressItemBean>, Serializable {

    private int isDefault;
    private String city;
    private String detailed;
    private String provice;
    private String district;
    private String telphone;
    private String customerUserid;
    private String id;
    private String contacts;

    public AddressItemBean(int isDefault, String city, String detailed, String provice, String district
            , String telphone, String contacts, String id, String customerUserid) {
        this.isDefault = isDefault;
        this.city = city;
        this.detailed = detailed;
        this.provice = provice;
        this.district = district;
        this.telphone = telphone;
        this.contacts = contacts;
        this.id = id;
        this.customerUserid = customerUserid;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getCustomerUserid() {
        return customerUserid;
    }

    public void setCustomerUserid(String customerUserid) {
        this.customerUserid = customerUserid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public int compareTo(@NonNull AddressItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
