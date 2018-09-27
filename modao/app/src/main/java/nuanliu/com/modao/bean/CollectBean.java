package nuanliu.com.modao.bean;

import java.util.LinkedHashMap;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/1/5
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017/1/5
 * @更新描述　　${ToDo}
 */

public class CollectBean {

    private LinkedHashMap<String,Boolean> mMap=new LinkedHashMap<>();

    public CollectBean(LinkedHashMap<String, Boolean> map) {
        mMap = map;
    }

    public LinkedHashMap<String, Boolean> getMap() {
        return mMap;
    }

    public void setMap(LinkedHashMap<String, Boolean> map) {
        mMap = map;
    }
}
