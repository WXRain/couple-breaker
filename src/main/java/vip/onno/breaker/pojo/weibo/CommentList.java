package vip.onno.breaker.pojo.weibo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import vip.onno.breaker.pojo.weibo.Comment;

public class CommentList {
    private List<Comment> data;
    @JSONField(name = "total_number")
    private Integer totalNumber;
    private Integer max;
    @JSONField(name = "hot_data")
    private List<Comment> hotData;
    @JSONField(name = "hot_total_number")
    private Integer hotTotalNumber;

    public List<Comment> getData() {
        return data;
    }

    public List<Comment> getHotData() {
        return hotData;
    }

    public Integer getHotTotalNumber() {
        return hotTotalNumber;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public void setHotData(List<Comment> hotData) {
        this.hotData = hotData;
    }

    public void setHotTotalNumber(Integer hotTotalNumber) {
        this.hotTotalNumber = hotTotalNumber;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {
        return "CommentList [data=" + data + ", totalNumber=" + totalNumber + ", max=" + max + ", hotData=" + hotData
            + ", hotTotalNumber=" + hotTotalNumber + "]";
    }

}
