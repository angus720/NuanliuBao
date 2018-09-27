package nuanliu.com.modao.bean;

public class EquipmentBean extends Dto {

    private String temperature;//温度
    private String humidity;//湿度
    private String addr;//地址

    public EquipmentBean(String temperature, String humidity, String addr) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.addr = addr;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
