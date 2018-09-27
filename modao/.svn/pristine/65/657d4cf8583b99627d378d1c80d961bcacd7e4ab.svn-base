package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

public class OweItemBean extends CommonResponse implements Comparable<OweItemBean> {

    String year;/*年度*/
    String useArea;/*收费面积*/
    String price;/*单价*/
    String shouldPay;/*采暖费*/
    String simoney;/*附加费*/
    String remission;/*已减免*/
    String concession;/*已优惠*/
    String arrearsDays;/*欠费天数*/
    String LateFee_t;/*滞纳金*/
    String knots;/*上期结转*/
    String total_c;/*年度费用*/

    public OweItemBean(String year, String useArea, String price, String shouldPay, String simoney, String remission, String concession, String arrearsDays, String lateFee_t, String knots, String total_c) {
        this.year = year;
        this.useArea = useArea;
        this.price = price;
        this.shouldPay = shouldPay;
        this.simoney = simoney;
        this.remission = remission;
        this.concession = concession;
        this.arrearsDays = arrearsDays;
        LateFee_t = lateFee_t;
        this.knots = knots;
        this.total_c = total_c;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUseArea() {
        return useArea;
    }

    public void setUseArea(String useArea) {
        this.useArea = useArea;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(String shouldPay) {
        this.shouldPay = shouldPay;
    }

    public String getSimoney() {
        return simoney;
    }

    public void setSimoney(String simoney) {
        this.simoney = simoney;
    }

    public String getRemission() {
        return remission;
    }

    public void setRemission(String remission) {
        this.remission = remission;
    }

    public String getConcession() {
        return concession;
    }

    public void setConcession(String concession) {
        this.concession = concession;
    }

    public String getArrearsDays() {
        return arrearsDays;
    }

    public void setArrearsDays(String arrearsDays) {
        this.arrearsDays = arrearsDays;
    }

    public String getLateFee_t() {
        return LateFee_t;
    }

    public void setLateFee_t(String lateFee_t) {
        LateFee_t = lateFee_t;
    }

    public String getKnots() {
        return knots;
    }

    public void setKnots(String knots) {
        this.knots = knots;
    }

    public String getTotal_c() {
        return total_c;
    }

    public void setTotal_c(String total_c) {
        this.total_c = total_c;
    }

    @Override
    public int compareTo(@NonNull OweItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
