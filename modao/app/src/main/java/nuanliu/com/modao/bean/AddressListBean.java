package nuanliu.com.modao.bean;

import java.util.List;

public class AddressListBean extends CommonResponse {


    /**
     * body : {"total":1,"list":[{"isDefault":1,"city":"1","detailed":"1","provice":"1","district":"1","telphone":"1","customerUserid":"aaa","id":"1","contacts":"1"}]}
     */

    private AddressListBean.BodyBean body;

    public AddressListBean.BodyBean getBody() {
        return body;
    }

    public void setBody(AddressListBean.BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * total : 1
         * list : [{"isDefault":1,"city":"1","detailed":"1","provice":"1","district":"1","telphone":"1","customerUserid":"aaa","id":"1","contacts":"1"}]
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<AddressListBean.BodyBean.ListBean> getList() {
            return list;
        }

        public void setList(List<AddressListBean.BodyBean.ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * isDefault : 1
             * city : 1
             * detailed : 1
             * provice : 1
             * district : 1
             * telphone : 1
             * customerUserid : aaa
             * id : 1
             * contacts : 1
             */

            private int isDefault;
            private String city;
            private String detailed;
            private String provice;
            private String district;
            private String telphone;
            private String customerUserid;
            private String id;
            private String contacts;

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
        }
    }

}
