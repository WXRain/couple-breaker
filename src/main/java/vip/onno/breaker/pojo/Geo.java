package vip.onno.breaker.pojo;

public class Geo {
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
