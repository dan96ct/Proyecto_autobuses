/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function AJAXCrearObjeto() {
    if (window.XMLHttpRequest) {
// navegadores que siguen los estándares
        objetoAjax = new XMLHttpRequest();
    } else {
// navegadores obsoletos
        objetoAjax = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return objetoAjax;
}
function cogerDatosHorario(horaS,horaLL,precio) {
    location.href = '/Proyecto_autobuses/tramitarDatosViaje2_controlador?horaS=' + horaS + '&horaLL=' + horaLL + '&precio=' + precio;
}
function getEstaciones() {
    objetoAjax = AJAXCrearObjeto(); //crea el objeto
    objetoAjax.open('GET', '/Proyecto_autobuses/getEstaciones_controlador');
    objetoAjax.send();
    objetoAjax.onreadystatechange = function () {
        if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
            mostrarEstaciones();
        }
    }
}
function getRutas(estacion) {
    objetoAjax = AJAXCrearObjeto(); //crea el objeto
    objetoAjax.open('GET', '/Proyecto_autobuses/getRuta_controlador?estacion=' + estacion.value);
    objetoAjax.send();
    objetoAjax.onreadystatechange = function () {
        if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
            mostrarRutas();
        }
    }
}
function mostrarRutas() {
    var datos = objetoAjax.responseText;
    var estacionesLista = JSON.parse(datos);
    var estacionesList = document.getElementById("estaciones2");
    for (var i = 0; i < estacionesLista[0].length; i++) {
        var option = document.createElement("option");
        option.innerHTML = estacionesLista[0][i];
        estacionesList.appendChild(option);
    }
}
function mostrarEstaciones() {
    var datos = objetoAjax.responseText;
    var estacionesLista = JSON.parse(datos);
    var estacionesList = document.getElementById("estaciones1");
    for (var i = 0; i < estacionesLista[0].length; i++) {
        var option = document.createElement("option");
        option.innerHTML = estacionesLista[0][i];
        estacionesList.appendChild(option);
    }

}