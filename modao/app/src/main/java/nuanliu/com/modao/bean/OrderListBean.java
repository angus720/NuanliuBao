package nuanliu.com.modao.bean;


import java.util.List;

import nuanliu.com.modao.base.BaseResponseForRepair;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/5/26
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017/5/26
 * @更新描述　　${ToDo}
 */

public class OrderListBean extends BaseResponseForRepair {

    /**
     * body : {"datas":[{"projectName":"国瑞城C区","content":"暖气不热","isCheckOK":false,"kind":3,"orderid":"W14957749623090001","source":2,"status":0,"time":1495774962,"title":"【报修】暖气不热","type":3},{"projectName":"国瑞城B区","content":"锅炉维修","isCheckOK":false,"kind":3,"orderid":"W14957846497860002","source":2,"status":0,"time":1495784650,"title":"【报修】锅炉维修","type":3},{"projectName":"国瑞城D区","content":"暖气不热","isCheckOK":false,"kind":3,"orderid":"W14957889261920001","source":2,"status":0,"time":1495788926,"title":"【报修】暖气不热","type":3}]}
     * status : 1
     */

    private BodyBean body;
    private int status;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
             * "orderid":"工单编号(string)"
             * "projectName":"小区名称",
             * "title":"标题",
             * "content":"维修内容",
             * "source": 1[系统警报] | 2[用户报修],
             * "kind": 1[维修] | 2[检修] | 3[报修],
             * "type": 1[锅炉房] | 2[管井] | 3[住户],
             * "status": 0[待派单] | 1[进行中] | 2[已完工] | 3[已完结]
             * "isConfirmReceive":是否确认接收(boolean),
             * "isCheckOK":是否通过验收(boolean),
             * "time":时间戳(int)
             */

            private String orderid;
            private String projectName;
            private String title;
            private String content;
            private int source;
            private int kind;
            private int type;
            private int status;
            private int category;
            private boolean isConfirmReceive;
            private boolean isCheckOK;
            private long time;
            private String address;
            private String type_name;

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public boolean isConfirmReceive() {
                return isConfirmReceive;
            }

            public void setConfirmReceive(boolean confirmReceive) {
                isConfirmReceive = confirmReceive;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public boolean isIsCheckOK() {
                return isCheckOK;
            }

            public void setIsCheckOK(boolean isCheckOK) {
                this.isCheckOK = isCheckOK;
            }

            public int getKind() {
                return kind;
            }

            public void setKind(int kind) {
                this.kind = kind;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }
        }
    }
}
