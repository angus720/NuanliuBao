package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

public class RepairAddrItemBean extends CommonResponse implements Comparable<RepairAddrItemBean> {

    private String companyName;  //公司名称
    private String residentid;  //房屋id
    private String householder;  //户主姓名
    private String telephone;   //电话
    private String city;  //城市
    private String communityName;  //小区名
    private String buildingNumber;  //楼栋号
    private String unitNumber;  //单元号
    private String houseNumber;  //门牌号
    private String address;  //地址
    private String account_h; // 账号
    private String districtName; //行政区
    private String district; //行政区id

    public RepairAddrItemBean(String districtName, String communityName, String buildingNumber, String unitNumber, String houseNumber, String address, String residentid) {
        this.districtName = districtName;
        this.communityName = communityName;
        this.buildingNumber = buildingNumber;
        this.unitNumber = unitNumber;
        this.houseNumber = houseNumber;
        this.address = address;
        this.residentid = residentid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getResidentid() {
        return residentid;
    }

    public void setResidentid(String residentid) {
        this.residentid = residentid;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount_h() {
        return account_h;
    }

    public void setAccount_h(String account_h) {
        this.account_h = account_h;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public int compareTo(@NonNull RepairAddrItemBean o) {
        return this.toString().compareTo(o.toString());
    }
}
