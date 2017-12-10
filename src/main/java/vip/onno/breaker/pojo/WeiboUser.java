package vip.onno.breaker.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WeiboUser {
    private Long id;
    @JSONField(name = "screen_name")
    private String screenName;
    @JSONField(name = "follow_me")
    private Boolean followMe;
    private Boolean following;
    @JSONField(name = "mbtype")
    private Integer mbType;
    @JSONField(name = "profile_image_url")
    private String profileImageUrl;
    @JSONField(name = "profile_url")
    private String profileUrl;
    private String remark;
    private Boolean verified;
    @JSONField(name = "verified_type")
    private Integer verifiedType;

    public Boolean getFollowing() {
        return following;
    }

    public Boolean getFollowMe() {
        return followMe;
    }

    public Long getId() {
        return id;
    }

    public Integer getMbType() {
        return mbType;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getRemark() {
        return remark;
    }

    public String getScreenName() {
        return screenName;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Integer getVerifiedType() {
        return verifiedType;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public void setFollowMe(Boolean followMe) {
        this.followMe = followMe;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMbType(Integer mbType) {
        this.mbType = mbType;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setVerifiedType(Integer verifiedType) {
        this.verifiedType = verifiedType;
    }

    @Override
    public String toString() {
        return "WeiboUser [followMe=" + followMe + ", following=" + following + ", id=" + id + ", mbType=" + mbType
            + ", profileImageUrl=" + profileImageUrl + ", profileUrl=" + profileUrl + ", remark=" + remark
            + ", screenName=" + screenName + ", verified=" + verified + ", verifiedType=" + verifiedType + "]";
    }

}
