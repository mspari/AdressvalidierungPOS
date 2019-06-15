<%-- 
    Document   : indexnew
    Created on : 27.05.2019, 14:17:05
    Author     : Christian
--%>

<%@page import="java.util.List"%>
<%@page import="com.sun.xml.internal.bind.v2.model.core.Adapter"%>
<%@page import="data.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Address Validation</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

        <link href="css/style.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div class="col-sm-12">
            <h3>Corrected Address</h3>
        </div>
        <form action="ValidationServlet" method="post">

            <%
                List<Address> addresslist = (List<Address>) session.getAttribute("addresslist");
            %>

            <table class="table">
                <%
                    for (int i = 0; i < addresslist.size(); i++)
                    {
                        Address elem = addresslist.get(i);
                %>
                <%=elem.toHTMLTableRow(i)%>
                <%
                    }
                %>
            </table>

            <h1><input type="submit" name="batten" value="accept selected" class="btn btn-success btn-block"></h1>
        </form>
    </body>
</html>