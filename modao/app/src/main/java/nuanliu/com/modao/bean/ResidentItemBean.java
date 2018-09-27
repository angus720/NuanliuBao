package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ResidentItemBean extends CommonResponse implements Comparable<ResidentItemBean>, Serializable {

    private String residentid;
    private String telephone;
    private String address;
    private String city;
    private String householder;
    private String companyName;
    private String companyid;
    private String account_h;
    private String buildingid;   //楼栋id
    private String buildingNumber;  //楼栋号
    private String unitid;  //单元id
    private String unitNumber;   //单元号
    private String houseNumber; // 门牌号
    private String communityName; // 小区
    private String communityid; // 小区id
    private String district;   //行政区代码
    private String districtName;   //行政区名称

    public ResidentItemBean(String residentid, String telephone, String address, String city, String householder, String companyName, String companyid, String account_h, String buildingid,
                            String buildingNumber, String unitid, String unitNumber, String houseNumber, String communityName, String communityid, String district, String districtName) {
        this.residentid = residentid;
        this.telephone = telephone;
        this.address = address;
        this.city = city;
        this.householder = householder;
        this.companyName = companyName;
        this.companyid = companyid;
        this.account_h = account_h;
        this.buildingid = buildingid;
        this.buildingNumber = buildingNumber;
        this.unitid = unitid;
        this.unitNumber = unitNumber;
        this.houseNumber = houseNumber;
        this.communityName = communityName;
        this.communityid = communityid;
        this.district = district;
        this.districtName = districtName;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccount_h() {
        return account_h;
    }

    public void setAccount_h(String account_h) {
        this.account_h = account_h;
    }

    public String getResidentid() {
        return residentid;
    }

    public void setResidentid(String residentid) {
        this.residentid = residentid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(String buildingid) {
        this.buildingid = buildingid;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityid() {
        return communityid;
    }

    public void setCommunityid(String communityid) {
        this.communityid = communityid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public int compareTo(@NonNull ResidentItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
