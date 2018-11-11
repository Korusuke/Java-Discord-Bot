package webpage;


import de.lucavinci.http.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

public class index {

    private final HttpServer http;

    public index() {
        this.http = new HttpServer(
                Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")));
        this.http.registerHandler("/", new Status());
        this.http.start();
    }

    private class Status implements HttpHandler.Request {

        @Override
        public HttpResponse onRequest(HttpRequest httpRequest, InetAddress inetAddress) {
            StringBuilder statusPage = new StringBuilder("<body style = \"        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;background-color: #080808;color:white; font-size: 110%;\" align='center'><div style='max-width:1000px; margin:auto;'");

            final String body = "    " +
                    "<img src=\"https://github.com/2knal/Probably-OOPM/raw/master/assets/Korusuke.png\" class=\"center\" style = \"display:block;\n" +
                    " margin-left:auto;\n" +
                    "        margin-right:auto;\n" +
                    "    margin-top: 0.25%;    max-width: 17.50%;\n" +
                    "    max-height: 10%\">" +

                    "<h2 align = \"center\">Korusuke Bot</h2>\n" +
                    "        <div align=\"center\">\n" +
                    "        <p><a href=\"https://www.java.com/\" rel=\"nofollow\"><img src=\"https://camo.githubusercontent.com/6764e02a1c021a4e7bf16fce7ec8a90abb52b4ae/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4d616465253230776974682d4a6176612d50696e6b2e737667\" alt=\"made-with-java\" data-canonical-src=\"https://img.shields.io/badge/Made%20with-Java-Pink.svg\" style=\"max-width:100%;\"></a>\n" +
                    "        <br></p>\n" +
                    "        </div>\n" +
                    "<hr>" +
                    "<p><b>We have made a dead simple discord bot, using <a href=\"https://github.com/DV8FromTheWorld/JDA\">JDA</a>, certain APIs and Firebase for data logging.</b></p>\n" +
                    "<p><h4>Click here to try our Korusuke Bot : " +
                    "<form  target=\"_blank\" action=\"https://discord.gg/ds6SaC\"><input style = \"        background-color: rgb(14, 109, 251) ;" +
                    "                          color: white;" +
                    "                                  padding:5px;" +
                    "                            position:relative;\" class=\"but\" type=\"submit\" value=\"Invite Link\"/></form></h4></p>" +
                    "<h4>\n" +
                    "        <b>Features</b>\n" +
                    "<ul>\n" +
                    "    <li>Provides latest News along with user specific search queries\n" +
                    "\n" +
                    "    <li>Searches for queried movie and provides it's details\n" +
                    "    \n" +
                    "    <li>Provides r/ProgrammerHumor memes, to lighten up ones mood\n" +
                    "</ul>\n" +
                    "</h4>" +
                    "<h4>\n" +
                    "        <b>Add-Ons</b>\n" +
                    "<ul>\n" +
                    "    <li>Allow users to fetch movie reviews\n" +
                    "\n" +
                    "    <li>Search for TV shows\n" +
                    "\n" +
                    "    <li>Add a music bot\n" +
                    "\n" +
                    "    <li>Add a chatbot feature, in case the user feels lonely :P\n" +
                    "</ul>\n" +
                    "</h4>" +
                    "<h4>\n" +
                    "        <b>The list of APIs we used:</b>\n" +
                    "<ul>\n" +
                    "    <li><a href=\"https://newsapi.org/\" target=\"_blank\">NewsApi</a>\n" +
                    "\n" +
                    "    <li><a href=\"https://www.themoviedb.org/\"target=\"_blank\">TMDb</a>\n" +
                    "\n" +
                    "    <li><a href=\"https://www.reddit.com/\" target=\"_blank\">Reddit</a>\n" +
                    "</ul>\n" +
                    "</h4>" +
                    "<h4>\n" +
                    "        <b>Contributors</b>\n" +
                    "<ul>\n" +
                    "    <li><a href=\"https://github.com/2knal\" target=\"_blank\">@2knal</a>\n" +
                    "\n" +
                    "    <li><a href=\"https://github.com/korusuke\"target=\"_blank\">@korusuke</a>\n" +
                    "\n" +
                    "    <li><a href=\"https://github.com/Aditya2223\" target=\"_blank\">@Aditya2223</a>\n" +
                    "</ul\n" +
                    "</h4>";


            statusPage.append(body);
            statusPage.append("<a href=\"https://github.com/2knal/Probably-OOPM\" target=\"_blank\">Github Repo</a>" +
                    "<hr>\n" +
                    "</div></body>");
            return new HttpResponse(statusPage.toString(), HttpStatusCode.OK, HttpMimeType.TXT) {
                @Override
                public String format() {
                    // Make the response standard compliant.
                    return super.format().replace("\n", "\r\n");
                }
            };
        }
    }
}
