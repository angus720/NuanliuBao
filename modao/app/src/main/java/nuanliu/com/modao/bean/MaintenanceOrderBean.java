package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class MaintenanceOrderBean implements Comparable<MaintenanceOrderBean>, Serializable {

    private long time;
    private String orderid;
    private int status;// 0[待处理] | 1、2[处理中] | 3[待评价] | 4、5[已完结]
    private String title;
    private String projectName;
    private String address;
    private String type_name;
    private int category;

    public MaintenanceOrderBean(long time, String orderid, int status, String title, String projectName, String type_name, String address, int category) {
        this.time = time;
        this.orderid = orderid;
        this.status = status;
        this.title = title;
        this.projectName = projectName;
        this.type_name = type_name;
        this.address = address;
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public int compareTo(@NonNull MaintenanceOrderBean o) {
        return this.toString().compareTo(o.toString());
    }
}
