package weibo4j.examples.publicservice;

import org.json.JSONArray;

import weibo4j.PublicService;
import weibo4j.model.WeiboException;

public class CityList {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String access_token = args[0];
    String province = args[1];
    PublicService ps = new PublicService();
    ps.client.setToken(access_token);
    JSONArray jo;
    try {
      jo = ps.cityList(province);
      System.out.println(jo.toString());
    } catch (WeiboException e) {
      e.printStackTrace();
    }

  }

}
