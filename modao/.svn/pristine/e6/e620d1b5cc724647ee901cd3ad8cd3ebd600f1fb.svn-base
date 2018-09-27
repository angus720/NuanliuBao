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

public class RepairOrderProgressBean extends BaseResponseForRepair {


    /**
     * status : 1
     * body : {"orderid":"P507DZVZXE7667ME6HPSPMO1","progress":[{"name":"生成报修工单","time":1534380875,"detail":"工单编号P507DZVZXE7667ME6HPSPMO1"},{"name":"分配工单","time":1534380881,"detail":"维修人朱师傅 电话13888888888"}],"postion":[{"lng":0,"lat":0}]}
     */

    private int status;
    private BodyBean body;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * orderid : P507DZVZXE7667ME6HPSPMO1
         * progress : [{"name":"生成报修工单","time":1534380875,"detail":"工单编号P507DZVZXE7667ME6HPSPMO1"},{"name":"分配工单","time":1534380881,"detail":"维修人朱师傅 电话13888888888"}]
         * postion : [{"lng":0,"lat":0}]
         */

        private String orderid;
        private List<ProgressBean> progress;
        private List<PostionBean> postion;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public List<ProgressBean> getProgress() {
            return progress;
        }

        public void setProgress(List<ProgressBean> progress) {
            this.progress = progress;
        }

        public List<PostionBean> getPostion() {
            return postion;
        }

        public void setPostion(List<PostionBean> postion) {
            this.postion = postion;
        }

        public static class ProgressBean {
            /**
             * name : 生成报修工单
             * time : 1534380875
             * detail : 工单编号P507DZVZXE7667ME6HPSPMO1
             */

            private String name;
            private int time;
            private String detail;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class PostionBean {
            /**
             * lng : 0.0
             * lat : 0.0
             */

            private double lng;
            private double lat;

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
        }
    }
}
