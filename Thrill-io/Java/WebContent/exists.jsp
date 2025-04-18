<%@page import="java.sql.DriverManager" %>
    <%@page import="java.sql.ResultSet" %>
        <%@page import="java.sql.Statement" %>
            <%@page import="java.sql.PreparedStatement" %>
                <%@page import="java.sql.Connection" %>
                    <%@page contentType="text/html" pageEncoding="UTF-8" %>
                        <% try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) {
                            e.printStackTrace(); } try (Connection
                            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false", "root"
                            , "" ); Statement stmt=conn.createStatement();) { PreparedStatement
                            ps=conn.prepareStatement("SELECT id FROM user WHERE " +
                " email=?"); ps.setString(1, request.getParameter("email")); ResultSet res=ps.executeQuery();
                            if(!res.isBeforeFirst()){ out.print("User already exists"); }else{ out.print("User name is
                            valid"); } }catch (Exception e){ System.out.println(e); } %>