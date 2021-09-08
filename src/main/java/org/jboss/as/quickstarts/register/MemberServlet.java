package org.jboss.as.quickstarts.register;

import java.io.IOException;
import java.io.PrintWriter;

//import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@SuppressWarnings("serial")
@WebServlet("/member")
public class MemberServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(MemberServlet.class.getName());

    //@Inject
    //HelloService helloService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        String PAGE_HEADER = "<html><head><title>Member Registeration</title></head><body>";        
        String START_CONTENT = " <form id=\"reg\" action=\"register.do\" method=\"POST\"><h2>Member Registration</h2><table>";
        String NAME_FIELD = "<tr><td>Name : </td><td><input type=\"text\" id=\"name\" name=\"name\" /></td></tr>";
        String EMAIL_FIELD = "<tr><td>Email : </td><td><input type=\"text\" id=\"email\" name=\"email\" /></td></tr>";
        String PHONE_FIELD = "<tr><td>Phone Number : </td><td><input type=\"text\" id=\"phone\" name=\"phone\" /></td></tr>";
        String END_CONTENT = "</table><p><input id=\"register\" type=\"submit\" value=\"Register\" /> </p> </form>";
        String PAGE_FOOTER = "</body></html>";

        PrintWriter writer = resp.getWriter();
        writer.println(PAGE_HEADER);
        writer.println(START_CONTENT);
        writer.println(NAME_FIELD);
        writer.println(EMAIL_FIELD);
        writer.println(PHONE_FIELD);
        writer.println(END_CONTENT);
        writer.println(PAGE_FOOTER);
        writer.close();
    }

}
