package weibo4j.http;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;


public class AccessToken extends WeiboResponse implements Serializable {

  private static final long serialVersionUID = 6986530164134648944L;
  private String accessToken;
  private String expireIn;
  private String refreshToken;
  private String uid;

  public AccessToken(Response res) throws WeiboException {
    super(res);
    JSONObject json = res.asJSONObject();
    try {
      accessToken = json.getString("access_token");
      expireIn = json.getString("expires_in");
      // Fix issue #1, see https://github.com/belerweb/weibo4j/issues/1
      // refreshToken = json.getString("refresh_token");
      uid = json.getString("uid");
    } catch (JSONException je) {
      throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
    }
  }

  AccessToken(String res) throws WeiboException, JSONException {
    super();
    JSONObject json = new JSONObject(res);
    accessToken = json.getString("access_token");
    expireIn = json.getString("expires_in");
    refreshToken = json.getString("refresh_token");
    uid = json.getString("uid");
  }

  public String getAccessToken() {
    return accessToken;
  }

  // Fix issue #2, see https://github.com/belerweb/weibo4j/issues/2
  public String getUid() {
    return uid;
  }

  public String getExpireIn() {
    return expireIn;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
    result = prime * result + ((expireIn == null) ? 0 : expireIn.hashCode());
    result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AccessToken other = (AccessToken) obj;
    if (accessToken == null) {
      if (other.accessToken != null) return false;
    } else if (!accessToken.equals(other.accessToken)) return false;
    if (expireIn == null) {
      if (other.expireIn != null) return false;
    } else if (!expireIn.equals(other.expireIn)) return false;
    if (refreshToken == null) {
      if (other.refreshToken != null) return false;
    } else if (!refreshToken.equals(other.refreshToken)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "AccessToken [" + "accessToken=" + accessToken + ", expireIn=" + expireIn
        + ", refreshToken=" + refreshToken + ",uid=" + uid + "]";
  }



}
