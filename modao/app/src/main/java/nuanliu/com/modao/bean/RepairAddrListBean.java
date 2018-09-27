package nuanliu.com.modao.bean;

import java.util.List;

public class RepairAddrListBean extends CommonResponse {

    /**
     * body : {"total":1,"list":[{"address":"天基云蒙庄园D-7#1-502","city":null,"householder":"502","companyName":"中环国投（蒙阴）热力科技有限公司","houseNumber":"502","unitNumber":"1","telephone":null,"buildingid":"2GH2R02KJ2OZZJ79BSM64BOBF","account_h":"qqqq111111111111","companyid":"C_65AA7D8FAA056E4883E624298846F749","buildingNumber":"D-7","unitid":"19QUX0HX14S39RN52TZ4KZE85","residentid":"3B93GTMHW0F0Z1XLHCR28LAUG","communityName":"天基云蒙庄园","communityid":"12B0WRAYBWNNNE9HUKJKD6YMB"}]}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * total : 1
         * list : [{"address":"天基云蒙庄园D-7#1-502","city":null,"householder":"502","companyName":"中环国投（蒙阴）热力科技有限公司","houseNumber":"502","unitNumber":"1","telephone":null,"buildingid":"2GH2R02KJ2OZZJ79BSM64BOBF","account_h":"qqqq111111111111","companyid":"C_65AA7D8FAA056E4883E624298846F749","buildingNumber":"D-7","unitid":"19QUX0HX14S39RN52TZ4KZE85","residentid":"3B93GTMHW0F0Z1XLHCR28LAUG","communityName":"天基云蒙庄园","communityid":"12B0WRAYBWNNNE9HUKJKD6YMB"}]
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * address : 天基云蒙庄园D-7#1-502
             * city : null
             * householder : 502
             * companyName : 中环国投（蒙阴）热力科技有限公司
             * houseNumber : 502
             * unitNumber : 1
             * telephone : null
             * buildingid : 2GH2R02KJ2OZZJ79BSM64BOBF
             * account_h : qqqq111111111111
             * companyid : C_65AA7D8FAA056E4883E624298846F749
             * buildingNumber : D-7
             * unitid : 19QUX0HX14S39RN52TZ4KZE85
             * residentid : 3B93GTMHW0F0Z1XLHCR28LAUG
             * communityName : 天基云蒙庄园
             * communityid : 12B0WRAYBWNNNE9HUKJKD6YMB
             */

            private String address;
            private String city;
            private String householder;
            private String companyName;
            private String houseNumber;
            private String unitNumber;
            private String telephone;
            private String buildingid;
            private String account_h;
            private String companyid;
            private String buildingNumber;
            private String unitid;
            private String residentid;
            private String communityName;
            private String communityid;
            private String districtName; //行政区
            private String district; //行政区id

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

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(String houseNumber) {
                this.houseNumber = houseNumber;
            }

            public String getUnitNumber() {
                return unitNumber;
            }

            public void setUnitNumber(String unitNumber) {
                this.unitNumber = unitNumber;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getBuildingid() {
                return buildingid;
            }

            public void setBuildingid(String buildingid) {
                this.buildingid = buildingid;
            }

            public String getAccount_h() {
                return account_h;
            }

            public void setAccount_h(String account_h) {
                this.account_h = account_h;
            }

            public String getCompanyid() {
                return companyid;
            }

            public void setCompanyid(String companyid) {
                this.companyid = companyid;
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

            public String getResidentid() {
                return residentid;
            }

            public void setResidentid(String residentid) {
                this.residentid = residentid;
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
        }
    }
}
