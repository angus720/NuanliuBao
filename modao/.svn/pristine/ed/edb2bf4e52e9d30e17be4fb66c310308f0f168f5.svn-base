package nuanliu.com.modao.bean;

import android.support.annotation.NonNull;

public class NoticeBean extends CommonResponse implements Comparable<NoticeBean> {

    private String title;
    private long time;
    private String content;

    public NoticeBean(String title, long time, String content) {
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(@NonNull NoticeBean o) {
        return this.toString().compareTo(o.toString());
    }
}
