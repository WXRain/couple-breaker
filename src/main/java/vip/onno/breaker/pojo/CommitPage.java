package vip.onno.breaker.pojo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class CommitPage {
    private Integer ok;
    private String msg;
    private List<Commit> data;
    @JSONField(name = "total_number")
    private Integer totalNumber;
    private Integer max;
    @JSONField(name = "hot_data")
    private List<Commit> hotData;
    @JSONField(name = "hot_total_number")
    private Integer hotTotalNumber;

    public List<Commit> getData() {
        return data;
    }

    public List<Commit> getHotData() {
        return hotData;
    }

    public Integer getHotTotalNumber() {
        return hotTotalNumber;
    }

    public Integer getMax() {
        return max;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getOk() {
        return ok;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setData(List<Commit> data) {
        this.data = data;
    }

    public void setHotData(List<Commit> hotData) {
        this.hotData = hotData;
    }

    public void setHotTotalNumber(Integer hotTotalNumber) {
        this.hotTotalNumber = hotTotalNumber;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {
        return "CommitPage [ok=" + ok + ", msg=" + msg + ", data=" + data + ", totalNumber=" + totalNumber + ", max="
            + max + ", hotData=" + hotData + ", hotTotalNumber=" + hotTotalNumber + "]";
    }

}
