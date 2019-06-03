<%-- 
    Document   : indexnew
    Created on : 27.05.2019, 14:17:05
    Author     : Christian
--%>

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
        <div class="jumbotron text-center">
            <h1>Addressvalidation</h1>
            <p id='para'>
                Das Ziel des Projekts ist es, eine Datenbank für Kundenadressdaten (Wohnort) zu entwickeln. 
                Diese Adressdaten werden über eine Web-GUI, die sowohl am Desktop als auch auf Mobile Devices 
                benutzbar ist (zumindest in der Browsersimulation), eingegeben. Auch der Upload von .csv 
                Dateien sollte möglich sein. Bevor diese in der Datenbank gespeichert werden, werden diese 
                über API-Funktionen validiert und bei Bedarf korrigiert bzw. bei ungenauen Eingaben mit mehreren 
                Zuordnungsmöglichkeiten wird eine Auswahl angezeigt. Dies soll auch als Vorprojekt für eine 
                eventuelle Diplomarbeit dienen.
            </p> 
        </div>

        <div class="container">
            <div class="row">
                <form method="GET" action="validateServlet">
                    <!-- Enter Address -->
                    <div class="col-sm-5 section container-fluid text-center form-group">
                        <h3>Enter Address </h3>
                        <label for="streetnamehousenr">Streetname HouseNr</label>
                        <br>
                        <input class="form-control" type="text" name="streetnamehousenr"/>
                        <br>
                        <label for="zipcode">ZipCode</label>
                        <br>
                        <input class="form-control" type="text" name="zipcode"/>
                        <br>
                        <label for="city">City</label>
                        <br>
                        <input class="form-control" type="text" name="city"/>
                        <br>
                        <label for="country">Country</label>
                        <br>
                        <input class="form-control" type="text" name="country"/><br>
                        <div>
                            <h1><input type="submit" value="validate" name="batten" class="btn btn-success btn-block"></h1>
                        </div> 
                    </div>
                    <div class="col-sm-1" id="separator">
                        <div class=" container-fluid">
                            <h3>or</h3>        
                        </div>
                    </div>

                    <!-- Drop csv -->
                    <div class="col-sm-5 section container-fluid text-center">
                        <h3>Drop csv File</h3> 
                        <fieldset>
                            <input type="hidden" id="MAX_FILE_SIZE" name="MAX_FILE_SIZE" value="300000" />

                            <div>
                                <input type="file" id="fileselect" name="fileselect[]" multiple="multiple" />
                                <div id="filedrag">or drop files here</div>
                            </div>

                            <div>
                                <h1><input type="submit" value="upload Files" name="batten" class="btn btn-success btn-block"></h1>
                            </div> 

                        </fieldset>
                        <div id="submitbutton">
                            <button type="submit">Upload Files</button>
                        </div>


                        <div id="messages">
                            <p>Status Messages</p>
                        </div>

                        <script src="js/filedrag.js"></script>
                    </div>

                </form>
            </div>
        </div>

        <!-- Seperator -->

    </body>
</html>
