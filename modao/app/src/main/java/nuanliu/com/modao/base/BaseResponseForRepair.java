package nuanliu.com.modao.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/6/1
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017/6/1
 * @更新描述　　${}
 */

public class BaseResponseForRepair implements Parcelable {
    public String status_name;
    public String status_detail;


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status_name);
        dest.writeString(this.status_detail);
    }

    public BaseResponseForRepair() {
    }

    protected BaseResponseForRepair(Parcel in) {
        this.status_name = in.readString();
        this.status_detail = in.readString();
    }

    public static final Creator<BaseResponseForRepair> CREATOR = new Creator<BaseResponseForRepair>() {
        @Override
        public BaseResponseForRepair createFromParcel(Parcel source) {
            return new BaseResponseForRepair(source);
        }

        @Override
        public BaseResponseForRepair[] newArray(int size) {
            return new BaseResponseForRepair[size];
        }
    };
}
