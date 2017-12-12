package vip.onno.breaker.pojo.weibo;

import com.alibaba.fastjson.annotation.JSONField;

public class CommentPicture {
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
        return "CommentPicture [pid=" + pid + ", size=" + size + ", url=" + url + ", geo=" + geo + ", large=" + large + "]";
    }

    public static class Large {
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

    public static class Geo {
        private Integer width;
        private Integer height;
        private Boolean croped;

        public Boolean getCroped() {
          return croped;
        }

        public Integer getHeight() {
          return height;
        }

        public Integer getWidth() {
          return width;
        }

        public void setCroped(Boolean croped) {
          this.croped = croped;
        }

        public void setHeight(Integer height) {
          this.height = height;
        }

        public void setWidth(Integer width) {
          this.width = width;
        }

        @Override
        public String toString() {
          return "Geo [width=" + width + ", height=" + height + ", croped=" + croped + "]";
        }

    }

}
