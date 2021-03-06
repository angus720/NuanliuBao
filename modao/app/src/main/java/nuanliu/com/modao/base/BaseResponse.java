package nuanliu.com.modao.base;

/**
 * @描述
 * @更新者 　$Author
 * @更新时间 　2018/06/19
 * @更新描述　　
 */

public class BaseResponse<T>  {

    public String status_name;

    public String status_detail;

    public int status;

    public T body;

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getStatus_detail() {
        return status_detail;
    }

    public void setStatus_detail(String status_detail) {
        this.status_detail = status_detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getBody() {
//        String json = new Gson().toJson(this.body);
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
