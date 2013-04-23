package weibo4j.examples.publicservice;

import org.json.JSONArray;

import weibo4j.PublicService;
import weibo4j.model.WeiboException;

public class GetLocationByCode {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String access_token = args[0];
    String codes = args[1];
    PublicService ps = new PublicService();
    ps.client.setToken(access_token);
    try {
      JSONArray jo = ps.getLocationByCode(codes);
      System.out.println(jo.toString());
    } catch (WeiboException e) {
      e.printStackTrace();
    }

  }

}
