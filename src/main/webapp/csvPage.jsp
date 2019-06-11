<%-- 
    Document   : csvPage
    Created on : 03.06.2019, 14:04:58
    Author     : Christian
--%>

<%@page import="java.util.List"%>
<%@page import="data.Address"%>
<%@page import="data.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            List<Address> list = (List<Address>) request.getAttribute("addresslist");
            for (Address address : list)
            {
        %>
        <%=address.toString()%>
        <%
            }
        %>
    </body>
</html>
