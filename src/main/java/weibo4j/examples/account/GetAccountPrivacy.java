package weibo4j.examples.account;

import org.json.JSONObject;

import weibo4j.Account;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.WeiboException;

public class GetAccountPrivacy {

  public static void main(String[] args) {
    String access_token = args[0];
    Account am = new Account();
    am.client.setToken(access_token);
    try {
      JSONObject json = am.getAccountPrivacy();
      Log.logInfo(json.toString());
    } catch (WeiboException e) {
      e.printStackTrace();
    }
  }

}
