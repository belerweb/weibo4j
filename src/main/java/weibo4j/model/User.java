/*
 * Copyright (c) 2007-2009, Yusuke Yamamoto All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. Redistributions in
 * binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution. Neither
 * the name of the Yusuke Yamamoto nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package weibo4j.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weibo4j.http.Response;

/**
 * A data class representing Basic user information element
 */
public class User extends WeiboResponse implements java.io.Serializable {

  private static final long serialVersionUID = -332738032648843482L;
  private JSONObject json;

  private Long id; // 用户UID
  private String idstr; // 字符串型的用户UID
  private String screenName; // 用户昵称
  private String name; // 友好显示名称
  private int province; // 用户所在省级ID
  private int city; // 用户所在城市ID
  private String location; // 用户所在地
  private String description; // 用户个人描述
  private String url; // 用户博客地址
  private String profileImageUrl; // 用户头像地址，50×50像素
  private String profileUrl; // 用户的微博统一URL地址
  private String domain; // 用户的个性化域名
  private String weihao; // 用户的微号
  private String gender; // 性别,m--男，f--女,n--未知
  private int followersCount; // 粉丝数
  private int friendsCount; // 关注数
  private int statusesCount; // 微博数
  private int favouritesCount; // 收藏数
  private Date createdAt; // 用户创建（注册）时间
  private boolean following; // 保留字段,是否已关注(此特性暂不支持)
  private boolean allowAllActMsg; // 是否允许所有人给我发私信
  private boolean geoEnabled; // 是否允许标识用户的地理位置
  private boolean verified; // 加V标示，是否微博认证用户
  private int verifiedType; // 认证类型
  private String remark; // 备注信息，在查询用户关系时提供此字段。
  private Status status = null; // 用户最新一条微博
  private boolean allowAllComment; // 是否允许所有人对我的微博进行评论
  private String avatarLarge; // 大头像地址
  private String verifiedReason; // 认证原因
  private boolean followMe; // 此用户是否关注我
  private int onlineStatus; // 用户在线状态
  private int biFollowersCount; // 互粉数
  private String lang; // 用户语言版本

  public User(JSONObject json) throws WeiboException {
    super();
    init(json);
  }

  private void init(JSONObject json) throws WeiboException {
    if (json != null) {
      try {
        id = json.getLong("id");
        idstr = json.getString("idstr");
        screenName = json.getString("screen_name");
        name = json.getString("name");
        province = json.getInt("province");
        city = json.getInt("city");
        location = json.getString("location");
        description = json.getString("description");
        url = json.getString("url");
        profileImageUrl = json.getString("profile_image_url");
        profileUrl = json.getString("profile_url");
        domain = json.getString("domain");
        weihao = json.getString("weihao");
        gender = json.getString("gender");
        followersCount = json.getInt("followers_count");
        friendsCount = json.getInt("friends_count");
        favouritesCount = json.getInt("favourites_count");
        statusesCount = json.getInt("statuses_count");
        createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
        following = getBoolean("following", json);
        allowAllActMsg = json.getBoolean("allow_all_act_msg");
        geoEnabled = json.getBoolean("geo_enabled");
        verified = getBoolean("verified", json);
        verifiedType = json.getInt("verified_type");
        remark = json.optString("remark");
        if (!json.isNull("status")) {
          status = new Status(json.getJSONObject("status"));
        }
        allowAllComment = json.getBoolean("allow_all_comment");
        avatarLarge = json.getString("avatar_large");
        verifiedReason = json.getString("verified_reason");
        followMe = json.getBoolean("follow_me");
        onlineStatus = json.getInt("online_status");
        biFollowersCount = json.getInt("bi_followers_count");
        lang = json.getString("lang");
      } catch (JSONException jsone) {
        throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
      }
    }
  }

  public static String[] constructIds(Response res) throws WeiboException {
    try {
      JSONArray list = res.asJSONObject().getJSONArray("ids");
      String temp = list.toString().substring(1, list.toString().length() - 1);
      String[] ids = temp.split(",");
      return ids;
    } catch (JSONException jsone) {
      throw new WeiboException(jsone.getMessage() + ":" + jsone.toString(), jsone);
    }
  }

  /**
   * 
   * @param res
   * @return
   * @throws WeiboException
   */
  public static UserWapper constructWapperUsers(Response res) throws WeiboException {
    JSONObject jsonUsers = res.asJSONObject(); // asJSONArray();
    try {
      JSONArray user = jsonUsers.getJSONArray("users");
      int size = user.length();
      List<User> users = new ArrayList<User>(size);
      for (int i = 0; i < size; i++) {
        users.add(new User(user.getJSONObject(i)));
      }
      long previousCursor = jsonUsers.getLong("previous_curosr");
      long nextCursor = jsonUsers.getLong("next_cursor");
      long totalNumber = jsonUsers.getLong("total_number");
      String hasvisible = jsonUsers.getString("hasvisible");
      return new UserWapper(users, previousCursor, nextCursor, totalNumber, hasvisible);
    } catch (JSONException jsone) {
      throw new WeiboException(jsone);
    }
  }

  /**
   * @param res
   * @return
   * @throws WeiboException
   */
  static List<User> constructResult(Response res) throws WeiboException {
    JSONArray list = res.asJSONArray();
    try {
      int size = list.length();
      List<User> users = new ArrayList<User>(size);
      for (int i = 0; i < size; i++) {
        users.add(new User(list.getJSONObject(i)));
      }
      return users;
    } catch (JSONException e) {}
    return null;
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
    User other = (User) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return json.toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdstr() {
    return idstr;
  }

  public void setIdstr(String idstr) {
    this.idstr = idstr;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getProvince() {
    return province;
  }

  public void setProvince(int province) {
    this.province = province;
  }

  public int getCity() {
    return city;
  }

  public void setCity(int city) {
    this.city = city;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getWeihao() {
    return weihao;
  }

  public void setWeihao(String weihao) {
    this.weihao = weihao;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public void setFollowersCount(int followersCount) {
    this.followersCount = followersCount;
  }

  public int getFriendsCount() {
    return friendsCount;
  }

  public void setFriendsCount(int friendsCount) {
    this.friendsCount = friendsCount;
  }

  public int getStatusesCount() {
    return statusesCount;
  }

  public void setStatusesCount(int statusesCount) {
    this.statusesCount = statusesCount;
  }

  public int getFavouritesCount() {
    return favouritesCount;
  }

  public void setFavouritesCount(int favouritesCount) {
    this.favouritesCount = favouritesCount;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public boolean isFollowing() {
    return following;
  }

  public void setFollowing(boolean following) {
    this.following = following;
  }

  public boolean isAllowAllActMsg() {
    return allowAllActMsg;
  }

  public void setAllowAllActMsg(boolean allowAllActMsg) {
    this.allowAllActMsg = allowAllActMsg;
  }

  public boolean isGeoEnabled() {
    return geoEnabled;
  }

  public void setGeoEnabled(boolean geoEnabled) {
    this.geoEnabled = geoEnabled;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public int getVerifiedType() {
    return verifiedType;
  }

  public void setVerifiedType(int verifiedType) {
    this.verifiedType = verifiedType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public boolean isAllowAllComment() {
    return allowAllComment;
  }

  public void setAllowAllComment(boolean allowAllComment) {
    this.allowAllComment = allowAllComment;
  }

  public String getAvatarLarge() {
    return avatarLarge;
  }

  public void setAvatarLarge(String avatarLarge) {
    this.avatarLarge = avatarLarge;
  }

  public String getVerifiedReason() {
    return verifiedReason;
  }

  public void setVerifiedReason(String verifiedReason) {
    this.verifiedReason = verifiedReason;
  }

  public boolean isFollowMe() {
    return followMe;
  }

  public void setFollowMe(boolean followMe) {
    this.followMe = followMe;
  }

  public int getOnlineStatus() {
    return onlineStatus;
  }

  public void setOnlineStatus(int onlineStatus) {
    this.onlineStatus = onlineStatus;
  }

  public int getBiFollowersCount() {
    return biFollowersCount;
  }

  public void setBiFollowersCount(int biFollowersCount) {
    this.biFollowersCount = biFollowersCount;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

}
