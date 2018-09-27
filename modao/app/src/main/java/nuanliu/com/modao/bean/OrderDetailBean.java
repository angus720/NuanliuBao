package nuanliu.com.modao.bean;

public class OrderDetailBean extends Dto {


    /**
     * data : {"amount":100,"householder":"张三","dj":"","companyName":"QQKPD2D5DHSP8T6DVSTAO7S3W","title":"附加费交费","account_h":"1111","addre":"130500130522临泉小区11101","companyid":"QQKPD2D5DHSP8T6DVSTAO7S3W","payMethod":"xj","znj":"","usableArea":100,"bizId":null,"time":1526377824000}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 100.0
         * householder : 张三
         * dj :
         * companyName : QQKPD2D5DHSP8T6DVSTAO7S3W
         * title : 附加费交费
         * account_h : 1111
         * addre : 130500130522临泉小区11101
         * companyid : QQKPD2D5DHSP8T6DVSTAO7S3W
         * payMethod : xj
         * znj :
         * usableArea : 100.0
         * bizId : null
         * time : 1526377824000
         * year:供热年度
         * title_:缴费项目
         * total_qf:历年欠费
         * paidChargeMoney:应收金额
         * remission:减免费
         * paymethod:支付方式
         */

        private double amount;
        private String householder;
        private String dj;
        private String companyName;
        private String title;
        private String account_h;
        private String addre;
        private String companyid;
        private String payMethod;
        private String znj;
        private double usableArea;
        private String bizId;
        private long time;
        private String year;
        private String title_;
        private String total_qf;
        private String paidChargeMoney;
        private String remission;
        private String paymethod;

        public String getPaymethod() {
            return paymethod;
        }

        public void setPaymethod(String paymethod) {
            this.paymethod = paymethod;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getHouseholder() {
            return householder;
        }

        public void setHouseholder(String householder) {
            this.householder = householder;
        }

        public String getDj() {
            return dj;
        }

        public void setDj(String dj) {
            this.dj = dj;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAccount_h() {
            return account_h;
        }

        public void setAccount_h(String account_h) {
            this.account_h = account_h;
        }

        public String getAddre() {
            return addre;
        }

        public void setAddre(String addre) {
            this.addre = addre;
        }

        public String getCompanyid() {
            return companyid;
        }

        public void setCompanyid(String companyid) {
            this.companyid = companyid;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getZnj() {
            return znj;
        }

        public void setZnj(String znj) {
            this.znj = znj;
        }

        public double getUsableArea() {
            return usableArea;
        }

        public void setUsableArea(double usableArea) {
            this.usableArea = usableArea;
        }

        public String getBizId() {
            return bizId;
        }

        public void setBizId(String bizId) {
            this.bizId = bizId;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTitle_() {
            return title_;
        }

        public void setTitle_(String title_) {
            this.title_ = title_;
        }

        public String getTotal_qf() {
            return total_qf;
        }

        public void setTotal_qf(String total_qf) {
            this.total_qf = total_qf;
        }

        public String getPaidChargeMoney() {
            return paidChargeMoney;
        }

        public void setPaidChargeMoney(String paidChargeMoney) {
            this.paidChargeMoney = paidChargeMoney;
        }

        public String getRemission() {
            return remission;
        }

        public void setRemission(String remission) {
            this.remission = remission;
        }
    }
}
