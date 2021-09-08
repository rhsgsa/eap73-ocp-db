package org.jboss.as.quickstarts.register;

import java.io.IOException;
import java.io.PrintWriter;

import javax.sql.DataSource;
import java.sql.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.annotation.Resource;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.jboss.as.quickstarts.entity.*;

@SuppressWarnings("serial")
@WebServlet("/register.do")
public class MemberRegistrationServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(MemberRegistrationServlet.class.getName());

    @Resource(lookup="java:jboss/datasources/workshop-mysql")
    private DataSource ds;
    
    @Inject
    RegistrationService registerService;
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        try {

            response.setContentType("text/html");


            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            System.out.println("NAME='" + name + "'");
            System.out.println("EMAIL='" + email + "'");
            System.out.println("phone='" + phone + "'");

            Member newMember = new Member();
            newMember.setEmail(email);
            newMember.setName(name);
            newMember.setPhoneNumber(phone);

            //Use Hibernate to insert member
            registerService.register(newMember);

            //Use normal Java API to retrieve list of members.
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("Select name, email, phone_number from member");
            ResultSet rs = ps.executeQuery();
            
            PrintWriter writer = response.getWriter();   

            writer.println("<html><head><title>List of Members</title></head><body>");
            writer.println("<h2>List of Members</h2><table border=\"1\"><tr><th>Name</th><th>Email</th><th>Phone Number</th></tr>");
            while(rs.next()) {
                writer.println("<tr><td>" + rs.getString(1) + "</td>");
                writer.println("<td>" + rs.getString(2) + "</td>");
                writer.println("<td>" + rs.getString(3) + "</td></tr>");
            }
            writer.println("</table>");
            writer.println("</body></html>");

            writer.close();

            rs.close();
            ps.close();
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter writer = response.getWriter();   

            writer.println("<html><head><title>List of Members</title></head><body>");
            writer.println("<p><label>Submission failed</label>");
            writer.println("</body></html>");
            writer.close();
        } 
    }




}

