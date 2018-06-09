<%-- 
    Document   : confirmacionPago_vista
    Created on : 01-mar-2018, 17:29:39
    Author     : Dani
--%>

<%@page import="Modelo.Billete"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="js/JavaScript.js" type="text/javascript"></script>
        <link href="css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
    <div id="barra_superior"><a href="../index.jsp"><img src="Imagenes/Logo.png" class="logo" alt="Logo"/></a></div>
</head>
<body>
    <% Billete billete = new Billete();
        billete = (Billete) session.getAttribute("billete");

    %>
    <div class="alert alert-success" role="alert" style="width: 50%; text-align: center; margin: 0 auto; margin-top: 50px;">
        <strong>¡Gracias!</strong> Su pago esta siendo tramitado
    </div>
    <%for (int i = 0; i < billete.getArrayPasajeros().size(); i++) {
    %>
    <div id="billete_viajero"> 
        <img width="200px" height="50px" src="Imagenes/Logo.png"> <h2 style="float: right;"><%out.print(billete.getCodigo());%></h2><br>
        <div id="datos_pasajero">
            <h3><%out.print(billete.getArrayPasajeros().get(i).getNombre());%></h3>
            <h3><%out.print(billete.getArrayPasajeros().get(i).getApellido());%></h3>
            <h3>Asiento <%out.print(billete.getArrayPasajeros().get(i).getAsiento());%></h3>
        </div>
        <div id="datos_viaje">
            <b>Fecha: </b><%out.print(billete.getDia());%><br>
            <b>Origen:</b><%out.print(billete.getOrigen());%><br>
            <b>Destino:</b><%out.print(billete.getDestino());%><br>
            <b>Hora de salida: </b><%out.print(billete.getHoraSalida());%><br>
            <b>Hora de llegada: </b><%out.print(billete.getHoraLlegada());%><br>
        </div>

    </div>
    <%
        }
    %>

</body>
</html>
