package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class PayRecordItemBean implements Comparable<PayRecordItemBean>, Serializable {

    /**
     * id :
     * communityName : 临泉小区
     * amount : 100
     * time : 1526377824000
     * title : 附加费交费
     * payMethod : xj
     * bizId : 1234654654
     * householder : angus
     * addre : jkjlkj
     */

    private String id;
    private String communityName;
    private double amount;
    private long time;
    private String title;
    private String payMethod;
    private String bizId;
    private String householder;
    private String addre;
    private String year;
    private double amount_t;
    private int type;

    public PayRecordItemBean(String id, String communityName, double amount, long time, String title, String payMethod, String bizId, String householder, String addre, int type) {
        this.id = id;
        this.communityName = communityName;
        this.amount = amount;
        this.time = time;
        this.title = title;
        this.payMethod = payMethod;
        this.bizId = bizId;
        this.householder = householder;
        this.addre = addre;
        this.type = type;
    }

    public PayRecordItemBean(double amount_t, String year, int type) {
        this.amount_t = amount_t;
        this.year = year;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getAddre() {
        return addre;
    }

    public void setAddre(String addre) {
        this.addre = addre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getAmount_t() {
        return amount_t;
    }

    public void setAmount_t(double amount_t) {
        this.amount_t = amount_t;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull PayRecordItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
