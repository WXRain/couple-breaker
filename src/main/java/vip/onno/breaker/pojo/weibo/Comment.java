package vip.onno.breaker.pojo.weibo;

import com.alibaba.fastjson.annotation.JSONField;

public class Comment {
    private Long id;
    private String source;
    private User user;
    private String text;
    private Integer likeCounts;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "reply_id")
    private Long replyId;
    @JSONField(name = "reply_text")
    private String replyText;
    @JSONField(name = "like_counts")
    private Boolean liked;
    @JSONField(name = "pic")
    private CommentPicture pic;

    public String getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public Boolean getLiked() {
        return liked;
    }

    public CommentPicture getPic() {
        return pic;
    }

    public Long getReplyId() {
        return replyId;
    }

    public String getReplyText() {
        return replyText;
    }

    public String getSource() {
        return source;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public void setPic(CommentPicture pic) {
        this.pic = pic;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", source=" + source + ", user=" + user + ", text=" + text + ", likeCounts="
            + likeCounts + ", createdAt=" + createdAt + ", replyId=" + replyId + ", replyText=" + replyText + ", liked="
            + liked + ", pic=" + pic + "]";
    }

}
