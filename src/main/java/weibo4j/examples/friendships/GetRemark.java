package weibo4j.examples.friendships;

import org.json.JSONArray;

import weibo4j.Friendships;
import weibo4j.model.WeiboException;

public class GetRemark {

  public static void main(String[] args) {
    String access_token = args[0];
    String uids = args[1];
    Friendships fm = new Friendships();
    fm.client.setToken(access_token);
    try {
      JSONArray user = fm.getRemark(uids);
      System.out.println(user.toString());
    } catch (WeiboException e) {
      e.printStackTrace();
    }

  }

}
