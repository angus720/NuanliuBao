package nuanliu.com.modao.bean;


import java.util.List;

import nuanliu.com.modao.base.BaseResponseForRepair;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/6/9
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017/6/9
 * @更新描述　　${ToDo}
 */

public class RepairOrderDetailBean extends BaseResponseForRepair {

    /**
     * body : {"comments":[{"comment":"完成","creater":"公司最高管理员(sjz)","time":1496833063}],"communityName":"国瑞城F区","contacts":"","content":"更换板换","description":"","isArrived":false,"isCheckOK":false,"kind":3,"orderid":"W14968275764430001","projectName":"国瑞城F区","projectid":"p25t6sndij","source":2,"status":4,"telephone":"18269668588","time":1496827576,"title":"【报修】更换板换","type":3}
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
        /**
         * comments : [{"comment":"完成","creater":"公司最高管理员(sjz)","time":1496833063}]
         * communityName : 国瑞城F区
         * contacts :
         * content : 更换板换
         * description :
         * isArrived : false
         * isCheckOK : false
         * kind : 3
         * orderid : W14968275764430001
         * projectName : 国瑞城F区
         * projectid : p25t6sndij
         * source : 2
         * status : 4
         * telephone : 18269668588
         * time : 1496827576
         * title : 【报修】更换板换
         * type : 3
         */

        private String orderid;
        private String projectName;
        private String communityName;
        private String address;
        private String title;
        private String contacts;
        private String content;
        private String description;
        private boolean isArrived;
        private boolean isCheckOK;
        private int kind;
        private String projectid;
        private int source;
        private String source_name;
        private int category;

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        private int status;
        private String telephone;
        private int time;
        private int type;
        private double lng;
        private double lat;
        private List<CommentsBean> comments;
        private AssignBean assign;

        private Maintainerbean maintainer;

        public Maintainerbean getMaintainer() {
            return maintainer;
        }

        public void setMaintainer(Maintainerbean maintainer) {
            this.maintainer = maintainer;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isIsArrived() {
            return isArrived;
        }

        public void setIsArrived(boolean isArrived) {
            this.isArrived = isArrived;
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

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectid() {
            return projectid;
        }

        public void setProjectid(String projectid) {
            this.projectid = projectid;
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
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

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public AssignBean getAssign() {
            return assign;
        }

        public void setAssign(AssignBean assign) {
            this.assign = assign;
        }

        public static class CommentsBean {
            /**
             * comment : 完成
             * creater : 公司最高管理员(sjz)
             * time : 1496833063
             */

            private String comment;
            private String creater;
            private int time;
            private String pictures;
            private int id;
            private int moment;
            private String voice;

            public String getVoice() {
                return voice;
            }

            public void setVoice(String voice) {
                this.voice = voice;
            }

            public int getMoment() {
                return moment;
            }

            public void setMoment(int moment) {
                this.moment = moment;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPictures() {
                return pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }

        public static class AssignBean {
            private int assignid;
            private String assigner;
            private String account;
            private String name;
            private String phone;
            private boolean isValid;
            private int time;

            public int getAssignid() {
                return assignid;
            }

            public void setAssignid(int assignid) {
                this.assignid = assignid;
            }

            public String getAssigner() {
                return assigner;
            }

            public void setAssigner(String assigner) {
                this.assigner = assigner;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public boolean isValid() {
                return isValid;
            }

            public void setValid(boolean valid) {
                isValid = valid;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }

        public static class Maintainerbean {
            private String account;
            private String name;
            private String phone;

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
