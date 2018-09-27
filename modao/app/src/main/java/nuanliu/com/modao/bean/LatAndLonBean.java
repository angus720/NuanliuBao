package nuanliu.com.modao.bean;

import java.util.List;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017-12-27
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017-12-27
 * @更新描述　　${ToDo}
 */

public class LatAndLonBean {

    private List<LocationsBean> locations;

    public List<LocationsBean> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationsBean> locations) {
        this.locations = locations;
    }

    public static class LocationsBean {
        /**
         * Lat : 36.865676
         * Lng : 118.075914
         * PID : PROJECTC87D51104A1F5C846FEF63EC213BFF1B
         */

        private String Lat;
        private String Lng;
        private String PID;

        public String getLat() {
            return Lat;
        }

        public void setLat(String Lat) {
            this.Lat = Lat;
        }

        public String getLng() {
            return Lng;
        }

        public void setLng(String Lng) {
            this.Lng = Lng;
        }

        public String getPID() {
            return PID;
        }

        public void setPID(String PID) {
            this.PID = PID;
        }
    }
}
