<%-- 
    Document   : buscarViaje_vista
    Created on : 25-may-2018, 18:31:38
    Author     : dani
--%>

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
<body onload="getEstaciones()">
    <main id="contenido">
        <h2 style="margin: 0 auto; width: 50%; padding: 1em;">Introduzca los datos del viaje</h2>
    <form id="formulario_filtrarViajes" action="../mostrarViajes_controlador">
        <div class="form-group">
            <input type="text" class="form-control" list="estaciones1"   placeholder="Origen" name="origen" onchange="getRutas(this)" required>
            <datalist type="text" id="estaciones1">
            </datalist>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" list="estaciones2" name="destino" placeholder="Destino" required>
            <datalist type="text" id="estaciones2">
            </datalist>
        </div>
        <div class="form-group">
            <input type="date" class="form-control"  placeholder="Fecha de ida" name="fecha" id="fecha" required>
        </div>
        <button type="submit" class="btn btn-warning">Confirmar</button>
    </form>
    </main>
</body>
</html>
