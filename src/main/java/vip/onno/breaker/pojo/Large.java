package vip.onno.breaker.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class Large {
    private String size;
    private String url;
    @JSONField(name = "geo")
    private Geo geo;

    public Geo getGeo() {
        return geo;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Large [size=" + size + ", url=" + url + ", geo=" + geo + "]";
    }

}
