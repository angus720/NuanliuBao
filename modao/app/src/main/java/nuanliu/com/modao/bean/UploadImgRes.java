package nuanliu.com.modao.bean;

public class UploadImgRes {
    /**
     * body : {"imageid":"OVFUXKYAM91SKX0IR8EA7ORT4","url":"http://www.comelogin.com/maintain_new/resource/images/2017-10-13/QWQBSIIE6BW3224V9D388KKXY.jpg"}
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
         * imageid : OVFUXKYAM91SKX0IR8EA7ORT4
         * url : http://www.comelogin.com/maintain_new/resource/images/2017-10-13/QWQBSIIE6BW3224V9D388KKXY.jpg
         */

        private String imageid;
        private String url;

        public String getImageid() {
            return imageid;
        }

        public void setImageid(String imageid) {
            this.imageid = imageid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    /**
     * status : 1
     * imgId : 图片id
     * url : 图片url
     */


}
