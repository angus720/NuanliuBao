package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class PaymentItemBean extends CommonResponse implements Comparable<PaymentItemBean>, Serializable {

    /**
     * time, 时间
     * title, 缴费类型
     * communityName, 小区名称
     * payMethod,  支付方式
     * amount  支付金额
     */

    private String time;
    private String title;
    private String communityName;
    private String payMethod;
    private String amount;

    public PaymentItemBean(String time, String title, String communityName, String payMethod, String amount) {
        this.time = time;
        this.title = title;
        this.communityName = communityName;
        this.payMethod = payMethod;
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(@NonNull PaymentItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
