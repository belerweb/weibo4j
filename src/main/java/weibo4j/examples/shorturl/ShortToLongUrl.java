package weibo4j.examples.shorturl;

import org.json.JSONObject;

import weibo4j.model.WeiboException;

public class ShortToLongUrl {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String access_token = args[0];
    String url = args[1];
    weibo4j.ShortUrl su = new weibo4j.ShortUrl();
    su.client.setToken(access_token);
    try {
      JSONObject jo = su.shortToLongUrl(url);
      System.out.println(jo.toString());
    } catch (WeiboException e) {
      e.printStackTrace();
    }
  }

}
