<%-- 
    Document   : unexpectedError
    Created on : Jun 11, 2019, 2:52:51 PM
    Author     : marinaspari
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <div class="container">
            <div class="row">
                <div class="col-sm-12 section container-fluid text-center form-group">
                    <form method="GET" action="ValidationServlet">
                        <h1>Unexpected Error...</h1>
                        <h3>
                            <%=request.getAttribute("exceptiontype")%>
                        </h3>
                        <input type="submit" value="back">
                    </form>
                </div>
            </div>
        </div>

        <!-- Seperator -->

    </body>
</html>