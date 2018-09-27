package nuanliu.com.modao.bean;

import java.util.List;

public class OweListBean extends CommonResponse {


    /**
     * body : {"total_a":"118.19","list":[{"useArea":74.81,"arrearsDays":"0","concession":"31.42","year":"2018-2019","total_c":"118.19","price":1,"shouldPay":74.81,"simoney":74.81,"remission":"-","paidChargeMoney":0.01,"LateFee_t":"0.0","knots":0}]}
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
         * total_a : 118.19
         * list : [{"useArea":74.81,"arrearsDays":"0","concession":"31.42","year":"2018-2019","total_c":"118.19","price":1,"shouldPay":74.81,"simoney":74.81,"remission":"-","paidChargeMoney":0.01,"LateFee_t":"0.0","knots":0}]
         */

        private String total_a;
        private List<ListBean> list;

        public String getTotal_a() {
            return total_a;
        }

        public void setTotal_a(String total_a) {
            this.total_a = total_a;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * useArea : 74.81
             * arrearsDays : 0
             * concession : 31.42
             * year : 2018-2019
             * total_c : 118.19
             * price : 1.0
             * shouldPay : 74.81
             * simoney : 74.81
             * remission : -
             * paidChargeMoney : 0.01
             * LateFee_t : 0.0
             * knots : 0.0
             */

            private String useArea;
            private String arrearsDays;
            private String concession;
            private String year;
            private String total_c;
            private String price;
            private String shouldPay;
            private String simoney;
            private String remission;
            private String paidChargeMoney;
            private String LateFee_t;
            private String knots;

            public String getUseArea() {
                return useArea;
            }

            public void setUseArea(String useArea) {
                this.useArea = useArea;
            }

            public String getArrearsDays() {
                return arrearsDays;
            }

            public void setArrearsDays(String arrearsDays) {
                this.arrearsDays = arrearsDays;
            }

            public String getConcession() {
                return concession;
            }

            public void setConcession(String concession) {
                this.concession = concession;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getTotal_c() {
                return total_c;
            }

            public void setTotal_c(String total_c) {
                this.total_c = total_c;
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

            public String getPaidChargeMoney() {
                return paidChargeMoney;
            }

            public void setPaidChargeMoney(String paidChargeMoney) {
                this.paidChargeMoney = paidChargeMoney;
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
        }
    }
}
