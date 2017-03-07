package com.example;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.*;
import java.security.Security;

/**
 * @author Joe Kutner on 3/6/17.
 *         Twitter: @codefinger
 */
public class Main {
  public static void main(String[] args) throws IOException, URISyntaxException {

    Security.setProperty("networkaddress.cache.ttl", "5");
    Security.setProperty("networkaddress.cache.negative.ttl", "0");

    URL proxyUrl = new URL(System.getenv("FIXIE_URL"));

    String userInfo = proxyUrl.getUserInfo();
    if (userInfo != null && !userInfo.isEmpty()) {
      final String user = userInfo.substring(0, userInfo.indexOf(':'));
      final String password = userInfo.substring(userInfo.indexOf(':') + 1);

      System.out.println("Setting SOCKS auth: " + user + ":" + password);
      Authenticator.setDefault(new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(user, password.toCharArray());
        }
      });
    }

    System.out.println("Using SOCKS proxy: " + proxyUrl.getHost() + ":" + String.valueOf(proxyUrl.getPort()));
    System.setProperty("socksProxyHost", proxyUrl.getHost());
    System.setProperty("socksProxyPort", String.valueOf(proxyUrl.getPort()));

    String dbUrlVarName = "DATABASE_URL";
    if (args.length > 0) {
      dbUrlVarName = args[0];
    }

    URI dbUri = new URI(System.getenv(dbUrlVarName));
    System.out.println("Using Database: " + dbUri.getHost() + ":" + dbUri.getPort());

    SocketFactory socketFactory = SocketFactory.getDefault();
    Socket socket = socketFactory.createSocket();

    Integer timeout = (System.getenv("SOCKET_TIMEOUT") == null ? 0 : Integer.valueOf(System.getenv("SOCKET_TIMEOUT")));

    System.out.println("Connecting (timeout=" + timeout + ")...");
    socket.connect(new InetSocketAddress(dbUri.getHost(), dbUri.getPort()), timeout);
    System.out.println("Success!");
  }
}
