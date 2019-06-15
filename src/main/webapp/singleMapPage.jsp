<%-- 
    Document   : indexnew
    Created on : 27.05.2019, 14:17:05
    Author     : Christian
--%>

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
        <!-- Infos -->

        <%
            Address correctedAddress = (Address) request.getAttribute("correctedAddress");
        %>

        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <h3>Corrected Address</h3>
                </div>
                <div class="row">
                    <!-- Enter Address -->
                    <div class="col-sm-6 section container-fluid text-center form-group">
                        <form Method="POST" action="ValidationServlet">
                            <label for="streetname">Streetname </label>
                            <br>
                            <input class="form-control" type="text" name="streetname"  value="<%=correctedAddress.getStreet()%>"/>
                            <br>

                            <label for="housenr">HouseNR </label>
                            <br>
                            <input class="form-control" type="text" name="housenr" value="<%=correctedAddress.getHouseNr()%>"/>
                            <br>

                            <label for="zipcode">ZipCode</label>
                            <br>
                            <input class="form-control" type="text" name="zipcode" value="<%=correctedAddress.getZipCode()%>"/>
                            <br>

                            <label for="city">City</label>
                            <br>
                            <input class="form-control" type="text" name="city" value="<%=correctedAddress.getCity()%>"/>
                            <br>

                            <label for="region">Region</label>
                            <br>
                            <input class="form-control" type="text" name="region" value="<%=correctedAddress.getRegion()%>"/>
                            <br>

                            <label for="country">Country</label>
                            <br>
                            <input class="form-control" type="text" name="country" value="<%=correctedAddress.getCountry()%>"/><br>

                            <div>
                                <h1><input type="submit" name="batten" value="accept" class="btn btn-success btn-block"></h1>
                            </div>
                        </form>
                    </div>

                    <%
                        String street = correctedAddress.getStreet()+"+"+correctedAddress.getHouseNr();
                        String zipCode = correctedAddress.getRegion();
                        String city = correctedAddress.getCity();
                        String country = correctedAddress.getCountry();
                        String url = "";
                        System.out.println(street);
                        if (!street.equals(null))
                        {
                            street = street.replace(" ", "+");
                            city = city.replace(" ", "+");
                            url = "https://maps.google.com/maps?q=" + city + "+" + zipCode + "+" + street + "," + country + "&t=k&z=13&ie=UTF8&iwloc=&output=embed";
                            System.out.println(url);
                        }
                        request.setAttribute("housenr", street);
                        request.setAttribute("zipCode", zipCode);
                        request.setAttribute("city", city);
                        request.setAttribute("housenr", country);
                        request.setAttribute("url", url);
                        out.println(" <div class='col-sm-6'><div class='text-center'><iframe width='600' height='450' id='gmap_canvas' "
                                + "src=" + url + " frameborder='1' scrolling='no' marginheight='0' marginwidth='0'></iframe></div></div>");
                    %>
                </div>


                </body>
                </html>