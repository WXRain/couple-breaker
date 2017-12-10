package vip.onno.breaker.pojo;

public class Picture {
    private String pid;
    private String size;
    private String url;
    private Geo geo;
    private Large large;

    public Geo getGeo() {
        return geo;
    }

    public Large getLarge() {
        return large;
    }

    public String getPid() {
        return pid;
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

    public void setLarge(Large large) {
        this.large = large;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Picture [pid=" + pid + ", size=" + size + ", url=" + url + ", geo=" + geo + ", large=" + large + "]";
    }

}
