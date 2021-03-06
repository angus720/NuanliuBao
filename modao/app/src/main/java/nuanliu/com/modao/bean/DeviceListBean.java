package nuanliu.com.modao.bean;

import java.util.List;

public class DeviceListBean extends CommonResponse {


    /**
     * body : {"datas":[{"temp":"30.310","address":"天基云蒙庄园D-71502","humidity":"42.4800","time":"2018-07-23 17:03:45","community":"天基云蒙庄园"},{"address":"天基云蒙庄园18号楼12202","community":"天基云蒙庄园"},{"address":"天基云蒙庄园18号楼12202","community":"天基云蒙庄园"},{"temp":"28.820","address":"天基云蒙庄园D-71502","humidity":"45.0100","time":"2018-07-23 17:09:17","community":"天基云蒙庄园"}]}
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
             * eui64 :
             * temp : 30.310
             * address : 天基云蒙庄园D-71502
             * humidity : 42.4800
             * time : 2018-07-23 17:03:45
             * community : 天基云蒙庄园
             */

            private String eui64;
            private String temp;
            private String address;
            private String humidity;
            private String time;
            private String community;

            public String getEui64() {
                return eui64;
            }

            public void setEui64(String eui64) {
                this.eui64 = eui64;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCommunity() {
                return community;
            }

            public void setCommunity(String community) {
                this.community = community;
            }
        }
    }
}
