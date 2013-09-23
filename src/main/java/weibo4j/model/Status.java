package weibo4j.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weibo4j.http.Response;

public class Status extends WeiboResponse implements java.io.Serializable {

  private static final long serialVersionUID = -8795691786466526420L;

  private JSONObject json;
  private Date createdAt; // 微博创建时间
  private Long id; // 微博ID
  private String mid; // 微博MID
  private String idstr; // 字符串型的微博ID
  private String text; // 微博内容
  private String source; // 微博来源
  private boolean favorited; // 是否已收藏，true：是，false：否
  private boolean truncated;// 是否被截断，true：是，false：否
  private String inReplyToStatusId; // 回复ID
  private String inReplyToUserId; // 回复人ID
  private String inReplyToScreenName; // 回复人昵称
  private String thumbnailPic; // 微博内容中的图片的缩略地址
  private String bmiddlePic; // 中型图片
  private String originalPic; // 原始图片
  // TODO private Geo geo; // 地理信息字段
  private User user = null; // 微博作者的用户信息字段
  private Status retweetedStatus; // 被转发的原微博信息字段，当该微博为转发微博时返回
  private int repostsCount; // 转发数
  private int commentsCount; // 评论数
  private int attitudesCount; // 表态数
  private int mlevel;
  private Visible visible;

  public Status() {}

  public Status(Response res) throws WeiboException {
    super(res);
    JSONObject json = res.asJSONObject();
    constructJson(json);
  }

  private void constructJson(JSONObject json) throws WeiboException {
    try {
      this.json = json;
      createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
      id = json.getLong("id");
      mid = json.getString("mid");
      idstr = json.getString("idstr");
      text = json.getString("text");
      source = json.getString("source");
      favorited = getBoolean("favorited", json);
      truncated = getBoolean("truncated", json);
      inReplyToStatusId = json.getString("in_reply_to_status_id");
      inReplyToUserId = json.getString("in_reply_to_user_id");
      inReplyToScreenName = json.getString("in_reply_to_screen_name");
      thumbnailPic = json.optString("thumbnail_pic");
      bmiddlePic = json.optString("bmiddle_pic");
      originalPic = json.optString("original_pic");
      // TODO GEO
      if (!json.isNull("user")) {
        user = new User(json.getJSONObject("user"));
      }
      if (!json.isNull("retweeted_status")) {
        retweetedStatus = new Status(json.getJSONObject("retweeted_status"));
      }

      repostsCount = json.getInt("reposts_count");
      commentsCount = json.getInt("comments_count");
      attitudesCount = json.getInt("attitudes_count");
      mlevel = json.getInt("mlevel");
      if (!json.isNull("visible")) {
        visible = new Visible(json.getJSONObject("visible"));
      }
    } catch (JSONException je) {
      throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
    }
  }


  public Status(JSONObject json) throws WeiboException, JSONException {
    constructJson(json);
  }

  public Status(String str) throws WeiboException, JSONException {
    // StatusStream uses this constructor
    super();
    JSONObject json = new JSONObject(str);
    constructJson(json);
  }

  public static StatusWapper constructWapperStatus(Response res) throws WeiboException {
    JSONObject jsonStatus = res.asJSONObject(); // asJSONArray();
    JSONArray statuses = null;
    try {
      if (!jsonStatus.isNull("statuses")) {
        statuses = jsonStatus.getJSONArray("statuses");
      }
      if (!jsonStatus.isNull("reposts")) {
        statuses = jsonStatus.getJSONArray("reposts");
      }
      int size = statuses.length();
      List<Status> status = new ArrayList<Status>(size);
      for (int i = 0; i < size; i++) {
        status.add(new Status(statuses.getJSONObject(i)));
      }
      long previousCursor = jsonStatus.getLong("previous_cursor");
      long nextCursor = jsonStatus.getLong("next_cursor");
      long totalNumber = jsonStatus.getLong("total_number");
      String hasvisible = jsonStatus.getString("hasvisible");
      return new StatusWapper(status, previousCursor, nextCursor, totalNumber, hasvisible);
    } catch (JSONException jsone) {
      throw new WeiboException(jsone);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Status other = (Status) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return json.toString();
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getIdstr() {
    return idstr;
  }

  public void setIdstr(String idstr) {
    this.idstr = idstr;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public boolean isFavorited() {
    return favorited;
  }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  public boolean isTruncated() {
    return truncated;
  }

  public void setTruncated(boolean truncated) {
    this.truncated = truncated;
  }

  public String getInReplyToStatusId() {
    return inReplyToStatusId;
  }

  public void setInReplyToStatusId(String inReplyToStatusId) {
    this.inReplyToStatusId = inReplyToStatusId;
  }

  public String getInReplyToUserId() {
    return inReplyToUserId;
  }

  public void setInReplyToUserId(String inReplyToUserId) {
    this.inReplyToUserId = inReplyToUserId;
  }

  public String getInReplyToScreenName() {
    return inReplyToScreenName;
  }

  public void setInReplyToScreenName(String inReplyToScreenName) {
    this.inReplyToScreenName = inReplyToScreenName;
  }

  public String getThumbnailPic() {
    return thumbnailPic;
  }

  public void setThumbnailPic(String thumbnailPic) {
    this.thumbnailPic = thumbnailPic;
  }

  public String getBmiddlePic() {
    return bmiddlePic;
  }

  public void setBmiddlePic(String bmiddlePic) {
    this.bmiddlePic = bmiddlePic;
  }

  public String getOriginalPic() {
    return originalPic;
  }

  public void setOriginalPic(String originalPic) {
    this.originalPic = originalPic;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Status getRetweetedStatus() {
    return retweetedStatus;
  }

  public void setRetweetedStatus(Status retweetedStatus) {
    this.retweetedStatus = retweetedStatus;
  }

  public int getRepostsCount() {
    return repostsCount;
  }

  public void setRepostsCount(int repostsCount) {
    this.repostsCount = repostsCount;
  }

  public int getCommentsCount() {
    return commentsCount;
  }

  public void setCommentsCount(int commentsCount) {
    this.commentsCount = commentsCount;
  }

  public int getAttitudesCount() {
    return attitudesCount;
  }

  public void setAttitudesCount(int attitudesCount) {
    this.attitudesCount = attitudesCount;
  }

  public int getMlevel() {
    return mlevel;
  }

  public void setMlevel(int mlevel) {
    this.mlevel = mlevel;
  }

  public Visible getVisible() {
    return visible;
  }

  public void setVisible(Visible visible) {
    this.visible = visible;
  }

}
