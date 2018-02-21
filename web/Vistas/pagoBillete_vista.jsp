<%-- 
    Document   : pagoBillete_vista
    Created on : 02-feb-2018, 9:48:33
    Author     : dani
--%>

<%@page import="Modelo.Billete"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pago del billete</title>
        <script src="js/JavaScript.js" type="text/javascript"></script>
        <link href="css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
    <div id="barra_superior"><a href="../index.jsp"><img src="Imagenes/Logo.png" class="logo" alt="Logo"/></a></div>

</head>
<body>
    <%
        Billete billete = new Billete();
        billete = (Billete) session.getAttribute("billete");
        session.invalidate();
    %>
    <main id="contenido">
        <section id="resumenDatos">
            <h2>Resumen de datos</h2>
            <label><b>Origen:</b> <% out.print(billete.getOrigen());%></label><br>
            <label><b>Destino:</b> <% out.print(billete.getDestino());%></label><br>
            <label><b>Hora salida:</b> <% out.print(billete.getHoraSalida());%></label><br>
            <label><b>Hora llegada:</b> <% out.print(billete.getHoraLlegada());%></label><br>
            <label><b>Numero de pasajeros:</b> <% out.print(billete.getPersonas());%></label><br>
            <label><b>Precio:</b> <% out.print(billete.getPrecio());%></label><br>
        </section>
        <section id="formulario_pago">
            <h2>Rellene el formulario para completar el pago</h2>
            <form>
                <div class="form-group">
                    <label for="Tipo de tarjeta">Tipo de tarjeta</label>
                    <select id="tipoTarjeta" name="tarjetas">
                        <option>MasterCard</option>
                        <option>Visa</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="NumeroTarjeta">Numero de tarjeta</label>
                    <input type="number" class="form-control" name="numeroTarjeta" id="numeroTarjeta" placeholder="Numero de tarjeta">
                </div>
                <div class="form-group">
                    <label for="Fecha de caducidad">Fecha de caducidad</label>
                    <input type="month" class="form-control" name="caducidadTarjeta" id="caducidadTarjeta" placeholder="Fecha">
                </div>
                <div class="form-group">
                    <label for="CVV">CVV</label>
                    <input type="number" class="form-control" name="CVV" id="CVV" placeholder="CVV">
                </div>
                <div class="form-group">
                    <label for="NIF">NIF del titular de la tarjeta</label>
                    <input type="text" class="form-control" name="NIF" id="NIFPago" placeholder="Introduce tu NIF">
                </div>
                <div class="form-group">
                    <label for="Nombre">Nombre del titular de la tarjeta</label>
                    <input type="text" class="form-control" name="nombre" id="nombrePago" placeholder="Introduce tu nombre">
                </div>
                <div class="form-group">
                    <label for="Apellidos">Apellidos del titular de la tarjeta</label>
                    <input type="text" class="form-control" name="apellidos" id="apellidosPago" placeholder="Introduce tu nombre">
                </div>
                <button type="submit" class="btn btn-primary">Confirmar datos</button>
            </form>
        </section><br>
        <section id="pasajeros_datos">
            <div class="btn-group btn-group-lg" id="botones_formulario" role="group" aria-label="Large button group">
                <%for (int i = 0; i < billete.getArrayPasajeros().size(); i++) {
                    System.out.println(billete.getArrayPasajeros().get(i).toString());
                %>
                <script>
                    var pasajero = {"numPasajero": <% out.print("'" + i + "'"); %>, "nif": <% out.print("'" + billete.getArrayPasajeros().get(i).getIdentificador() + "'"); %>, "nombre": <% out.print("'" + billete.getArrayPasajeros().get(i).getNombre() + "'"); %>, "apellidos": <% out.print("'" + billete.getArrayPasajeros().get(i).getApellido() + "'"); %>, "email": <% out.print("'" + billete.getArrayPasajeros().get(i).getCorreo() + "'"); %>, "asiento": <% out.print("'" + billete.getArrayPasajeros().get(i).getAsiento() + "'"); %>, "id": <% out.print("'" + billete.getArrayPasajeros().get(i).getId() + "'"); %>};
                    añadirPasajero(pasajero);
                </script>
                <button type="button" onclick="datosPasajero(this);" name="<% out.print(billete.getArrayPasajeros().get(i).getId()); %>" class="btn btn-secondary btn-lg btn-block">Pasajero<% out.print(i + 1); %></button><%
                    }
                %>
            </div>
            <h2>Datos pasajero</h2>
            <label id="nombre">Seleccione un pasajero</label><br>
            <label id="apellidos"></label><br>
            <label id="nif"></label><br>
            <label id="email"></label><br>
            <label id="asiento"></label><br>

        </section>

    </main>
</body>
</html>
