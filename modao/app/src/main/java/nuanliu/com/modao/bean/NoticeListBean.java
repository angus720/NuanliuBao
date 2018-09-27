package nuanliu.com.modao.bean;

import java.util.List;

public class NoticeListBean extends CommonResponse {


    /**
     * body : {"datas":[{"id":1,"companyid":"C_65AA7D8FAA056E4883E624298846F749","title":"公告1","content":"今天停水啦","an_index":1,"createTime":1535021083000,"isShow":true},{"id":2,"companyid":"C_65AA7D8FAA056E4883E624298846F749","title":"通知2","content":"明天放假啦","an_index":2,"createTime":1535021101000,"isShow":true},{"id":1,"companyid":"C_65AA7D8FAA056E4883E624298846F749","title":"公告1","content":"今天停水啦","an_index":1,"createTime":1535021083000,"isShow":true},{"id":2,"companyid":"C_65AA7D8FAA056E4883E624298846F749","title":"通知2","content":"明天放假啦","an_index":2,"createTime":1535021101000,"isShow":true}]}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private List<DatasBean> datas;

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * id : 1
             * companyid : C_65AA7D8FAA056E4883E624298846F749
             * title : 公告1
             * content : 今天停水啦
             * an_index : 1
             * createTime : 1535021083000
             * isShow : true
             */

            private int id;
            private String companyid;
            private String title;
            private String content;
            private int an_index;
            private long createTime;
            private boolean isShow;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCompanyid() {
                return companyid;
            }

            public void setCompanyid(String companyid) {
                this.companyid = companyid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAn_index() {
                return an_index;
            }

            public void setAn_index(int an_index) {
                this.an_index = an_index;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public boolean isIsShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }
        }
    }
}
