package nuanliu.com.modao.bean;

import java.util.List;

public class PayRecordListBean extends CommonResponse {

    /**
     * body : {"list":[{"list":[{"addre":"临泉小区11101","amount":100,"payMethod":"xj","householder":"张三","bizId":null,"communityName":"临泉小区","id":12064,"time":1526377824000,"title":"附加费交费"},{"addre":"临泉小区11101","amount":100,"payMethod":"xj","householder":"张三","bizId":null,"communityName":"临泉小区","id":12065,"time":1526377824000,"title":"附加费交费"}],"year_s":"2018","amount_t":200}]}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private List<ListBeanX> list;

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class ListBeanX {
            /**
             * list : [{"addre":"临泉小区11101","amount":100,"payMethod":"xj","householder":"张三","bizId":null,"communityName":"临泉小区","id":12064,"time":1526377824000,"title":"附加费交费"},{"addre":"临泉小区11101","amount":100,"payMethod":"xj","householder":"张三","bizId":null,"communityName":"临泉小区","id":12065,"time":1526377824000,"title":"附加费交费"}]
             * year_s : 2018
             * amount_t : 200.0
             */

            private String year_s;
            private double amount_t;
            private List<ListBean> list;

            public String getYear_s() {
                return year_s;
            }

            public void setYear_s(String year_s) {
                this.year_s = year_s;
            }

            public double getAmount_t() {
                return amount_t;
            }

            public void setAmount_t(double amount_t) {
                this.amount_t = amount_t;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * addre : 临泉小区11101
                 * amount : 100.0
                 * payMethod : xj
                 * householder : 张三
                 * bizId : null
                 * communityName : 临泉小区
                 * id : 12064
                 * time : 1526377824000
                 * title : 附加费交费
                 */

                private String addre;
                private double amount;
                private String payMethod;
                private String householder;
                private String bizId;
                private String communityName;
                private String id;
                private long time;
                private String title;
                private int type;

                public String getAddre() {
                    return addre;
                }

                public void setAddre(String addre) {
                    this.addre = addre;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public String getPayMethod() {
                    return payMethod;
                }

                public void setPayMethod(String payMethod) {
                    this.payMethod = payMethod;
                }

                public String getHouseholder() {
                    return householder;
                }

                public void setHouseholder(String householder) {
                    this.householder = householder;
                }

                public String getBizId() {
                    return bizId;
                }

                public void setBizId(String bizId) {
                    this.bizId = bizId;
                }

                public String getCommunityName() {
                    return communityName;
                }

                public void setCommunityName(String communityName) {
                    this.communityName = communityName;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
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

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }
    }
}
