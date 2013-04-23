package weibo4j.examples.timeline;

import org.json.JSONObject;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.WeiboException;

public class QueryId {

  public static void main(String[] args) {
    String access_token = args[0];
    String mid = args[1];
    Timeline tm = new Timeline();
    tm.client.setToken(access_token);
    try {
      JSONObject id = tm.QueryId(mid, 1, 1);
      Log.logInfo(String.valueOf(id));
    } catch (WeiboException e) {
      e.printStackTrace();
    }

  }

}
