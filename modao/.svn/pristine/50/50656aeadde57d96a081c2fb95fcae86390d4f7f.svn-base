package nuanliu.com.modao.bean;

import java.util.List;

public class PaymentListBean extends CommonResponse {

    private PaymentListBean.BodyBean body;

    public PaymentListBean.BodyBean getBody() {
        return body;
    }

    public void setBody(PaymentListBean.BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<PaymentListBean.BodyBean.ListBean> getList() {
            return list;
        }

        public void setList(List<PaymentListBean.BodyBean.ListBean> list) {
            this.list = list;
        }

        public static class ListBean {

            /**
             * time, 时间
             * title, 缴费类型
             * communityName, 小区名称
             * payMethod,  支付方式
             * amount  支付金额
             */

            private String time;
            private String title;
            private String communityName;
            private String payMethod;
            private String amount;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCommunityName() {
                return communityName;
            }

            public void setCommunityName(String communityName) {
                this.communityName = communityName;
            }

            public String getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(String payMethod) {
                this.payMethod = payMethod;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

        }
    }
}
