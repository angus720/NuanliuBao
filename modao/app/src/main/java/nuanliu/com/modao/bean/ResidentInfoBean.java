package nuanliu.com.modao.bean;

public class ResidentInfoBean extends Dto {

    //residentid  房屋id
    //houseNumber 门牌号
    //householder  户主姓名
    //account_h   新卡号
    //account_h_old  旧卡号
    //communityName  小区名称
    //buildingNumber  楼栋号
    //unitNumber  单元号

    private String residentid;
    private String houseNumber;
    private String householder;
    private String account_h;
    private String account_h_old;
    private String communityName;
    private String buildingNumber;
    private String unitNumber;

    public String getResidentid() {
        return residentid;
    }

    public void setResidentid(String residentid) {
        this.residentid = residentid;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getAccount_h() {
        return account_h;
    }

    public void setAccount_h(String account_h) {
        this.account_h = account_h;
    }

    public String getAccount_h_old() {
        return account_h_old;
    }

    public void setAccount_h_old(String account_h_old) {
        this.account_h_old = account_h_old;
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
}
