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
        this.http.registerHandler("/", new BanUtilStatus());
        this.http.start();
    }

    private class BanUtilStatus implements HttpHandler.Request {

        private static final String PRE_CSS = "color: #FF6961;" +
                "border-style: solid;" +
                "text-shadow: 1px 2px 10px #2D2F3C;" +
                "border: #2D2F3C;" +
                "font-weight: bolder;" +
                "background-color: 2D2F3C;" +
                "padding-bottom: 10px;" +
                "display: inline-block;" +
                "padding-right:  5px;" +
                "margin: 0px;";

        private static final String PRE_TAG = "<pre style=\"" + PRE_CSS + "\">";

        private static final String INVITE_FORMAT = "https://discordapp.com/oauth2/authorize?client_id=%s&scope=bot&permissions=%d";


        private String createInviteLinkHTML() {
            String link = "google.com";
            return "<h3 style=\"display:inline\"><a href=\""
                    + link + "\">Invite to server</a></h3> (remember BanUtil is a single server bot)";
        }

        private String getExceptionTraceHTML() {

            return "<br><br><b>This may (or may not) be related:</b><br>" + PRE_TAG + "error" + "</pre>";
        }

        @Override
        public HttpResponse onRequest(HttpRequest httpRequest, InetAddress inetAddress) {
            StringBuilder statusPage = new StringBuilder("<body style=\"background-color: #95D3BD;\">");

                statusPage.append(PRE_TAG).append("Welcome").append("</pre>");
                statusPage.append("<p>Oh I think you'll find this ban hammer is fully operational.</p>");
                statusPage.append("<h1 style=\"color:#FF6961\">Bans since last restart: <span style=\"font-size:10px\">10000000000000000000000000000000</span>")
                        .append("</h1>");
                statusPage.append(this.createInviteLinkHTML());


            statusPage.append(this.getExceptionTraceHTML());
            statusPage.append("<p>Refresh page for updates.</p>");
            statusPage.append("<p>Get your own BanUtil: <a href=\"" +"Repo Link" + "\">" + "Repo Link" + "</a></p>");
            statusPage.append("</body>");
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