package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import nuanliu.com.modao.utils.HanziToPinyin;

public class CompanyBean implements Comparable<CompanyBean> {

    private String companyid;
    private String companyname;
    private String pinyin;
    private char firstChar;

    public CompanyBean(String companyid, String companyname) {
        this.companyid = companyid;
        this.companyname = companyname;
        setPinyin(HanziToPinyin.getPinYin(companyname));
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;

        String first = pinyin.length() > 1 ? pinyin.substring(0, 1) : "##";

        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        } else {
            firstChar = '#';
        }
    }

    public char getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(char firstChar) {
        this.firstChar = firstChar;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CompanyBean) {

            return TextUtils.equals(this.companyid, ((CompanyBean) o).getCompanyid());
        } else {
            return super.equals(o);
        }

    }

    @Override
    public int hashCode() {
        int result = companyid.hashCode();
        result = 31 * result + (pinyin != null ? pinyin.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull CompanyBean o) {
        if (this.getFirstChar() == '#') {
            return 1;
        } else if (o.getFirstChar() == '#') {
            return -1;
        } else {
            return this.pinyin.compareTo(o.getPinyin());
        }
    }
}
