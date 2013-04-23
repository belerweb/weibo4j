package weibo4j.examples.timeline;

import org.json.JSONObject;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.WeiboException;

public class GetMentionsIds {
  public static void main(String[] args) {
    String access_token = args[0];
    Timeline tm = new Timeline();
    tm.client.setToken(access_token);
    try {
      JSONObject ids = tm.getFriendsTimelineIds();
      Log.logInfo(ids.toString());
    } catch (WeiboException e) {
      e.printStackTrace();
    }
  }

}
