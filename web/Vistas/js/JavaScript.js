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
var arrayPasajeros = [];

function cogerDatosHorario(horaS, horaLL, precio) {
    location.href = '/Proyecto_autobuses/tramitarDatosViaje2_controlador?horaS=' + horaS + '&horaLL=' + horaLL + '&precio=' + precio;
}
function comprobarDatosPasajero() {
    var numPasajero = document.getElementById("tituloFormulario_usuario").textContent;

    if (comprobarCamposVacios() === true) {

        switch (numPasajero) {
            case 'Datos de pasajero 1':
                guardarDatosPasajero(1);
                break;
            case 'Datos de pasajero 2':
                guardarDatosPasajero(2);
                break;
            case 'Datos de pasajero 3':
                guardarDatosPasajero(3);
                break;
            case 'Datos de pasajero 4':
                guardarDatosPasajero(4);
                break;
            case 'Datos de pasajero 5':
                guardarDatosPasajero(5);
                break;
            default:
        }
    }
}

function elegirAsiento(elemento) {
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        if (todosLosAsientos[i].getAttribute("name") !== "ocupado") {
            todosLosAsientos[i].setAttribute("style", "color:black;");
            todosLosAsientos[i].setAttribute("name", "");
        }
    }
    if (elemento.getAttribute("name") !== "ocupado") {
        elemento.setAttribute("style", "color:green;");
        elemento.setAttribute("name", "activado");
    }
}
function comprobarCamposVacios() {
    var validacion = true;
    var identificador = document.getElementById("identificador");
    var nombre = document.getElementById("nombre");
    var apellidos = document.getElementById("apellidos");
    var email = document.getElementById("email");

    if (identificador.value === "") {
        identificador.setAttribute("class", "form-control is-invalid");
        validacion = false;
    }
    if (nombre.value === "") {
        nombre.setAttribute("class", "form-control is-invalid");
        if (validacion !== false) {
            validacion = false;
        }

    }
    if (apellidos.value === "") {
        apellidos.setAttribute("class", "form-control is-invalid");
        if (validacion !== false) {
            validacion = false;
        }
    }
    if (email.value === "") {
        email.setAttribute("class", "form-control is-invalid");
        if (validacion !== false) {
            validacion = false;
        }
    }
    return validacion;
}
function guardarDatosPasajero(numero) {
    var numIdentificacion = document.getElementById("identificador").value;
    var nombre = document.getElementById("nombre").value;
    var apellidos = document.getElementById("apellidos").value;
    var email = document.getElementById("email").value;

    var asiento = 0;
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        if (todosLosAsientos[i].getAttribute("name") == "activado") {
            asiento = todosLosAsientos[i].getAttribute("id");
            todosLosAsientos[i].setAttribute("name", "ocupado");
        }
    }
    objetoAjax = AJAXCrearObjeto(); //crea el objeto
    objetoAjax.open('GET', '/Proyecto_autobuses/tramitarDatosViaje3_controlador?nif=' + numIdentificacion + '&nombre=' + nombre + '&apellidos=' + apellidos + '&email=' + email + '&asiento=' + asiento);
    objetoAjax.send();
    objetoAjax.onreadystatechange = function () {
        if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
        }
    }
    var datosIntroducidos = false;
    for (var i = 0; i < arrayPasajeros.length; i++) {
        if (arrayPasajeros[i].numPasajero == numero) {
            arrayPasajeros[i] = {"numPasajero": numero, "nif": numIdentificacion, "nombre": nombre, "apellidos": apellidos, "email": email, "asiento": asiento};
            datosIntroducidos = true;
        }
    }
    if (datosIntroducidos == false) {
        var pasajero = {"numPasajero": numero, "nif": numIdentificacion, "nombre": nombre, "apellidos": apellidos, "email": email, "asiento": asiento};
        arrayPasajeros.push(pasajero);
    }
    formularioConfirmado();

}
function comprobarFormularios(numPersonas) {
    var validar = false;
    if (numPersonas == arrayPasajeros.length) {
        validar = true;
    }
    return validar;

}
function confirmarTodosLosDatos(numPersonas) {
    alert(arrayPasajeros.length);
    if (comprobarFormularios(numPersonas) === true) {
        var json = JSON.stringify(arrayPasajeros);
        location.href = 'pagoBillete_vista.jsp';
    } else {
        alert("Faltan formularios por completar");
    }
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
function activarFormulario(element) {
    var numForm = element.getAttribute("id");
    var boton_activado = document.getElementsByClassName("nav-link active");
    boton_activado[0].setAttribute("class", "nav-link");
    element.setAttribute("class", "nav-link active");
    document.getElementById("tituloFormulario_usuario").innerHTML = "Datos de pasajero " + numForm;
    document.getElementById("identificador").setAttribute("name", "identificador" + numForm);
    document.getElementById("nombre").setAttribute("name", "nombre" + numForm);
    document.getElementById("apellidos").setAttribute("name", "apellidos" + numForm);
    document.getElementById("email").setAttribute("name", "email" + numForm);

    document.getElementById("identificador").value = "";
    document.getElementById("nombre").value = "";
    document.getElementById("apellidos").value = "";
    document.getElementById("email").value = "";

    formularioNormal();

    comprobarFormularioCompleto(numForm);


}
function comprobarFormularioCompleto(numForm) {
    for (var i = 0; i < arrayPasajeros.length; i++) {
        if (arrayPasajeros[i].numPasajero == numForm) {
            document.getElementById("identificador").value = arrayPasajeros[i].nif;
            document.getElementById("nombre").value = arrayPasajeros[i].nombre;
            document.getElementById("apellidos").value = arrayPasajeros[i].apellidos;
            document.getElementById("email").value = arrayPasajeros[i].email;

            formularioConfirmado();


        }
    }
}
function formularioConfirmado() {
    document.getElementById("identificador").setAttribute("class", "form-control is-valid");
    document.getElementById("nombre").setAttribute("class", "form-control is-valid");
    document.getElementById("apellidos").setAttribute("class", "form-control is-valid");
    document.getElementById("email").setAttribute("class", "form-control is-valid");
    var arrayForm = document.getElementsByClassName("form-group");
    for (var i = 0; i < arrayForm.length; i++) {
        arrayForm[i].setAttribute("style", "color:green;");
    }

}
function formularioNormal() {
    document.getElementById("identificador").setAttribute("class", "form-control");
    document.getElementById("nombre").setAttribute("class", "form-control");
    document.getElementById("apellidos").setAttribute("class", "form-control");
    document.getElementById("email").setAttribute("class", "form-control");
    var arrayForm = document.getElementsByClassName("form-group");
    for (var i = 0; i < arrayForm.length; i++) {
        arrayForm[i].setAttribute("style", "color:black;");
    }
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        if (todosLosAsientos[i].getAttribute("name") == "ocupado") {
            todosLosAsientos[i].setAttribute("style", "color:red;");
        } else {
            todosLosAsientos[i].setAttribute("style", "color:black;");
            todosLosAsientos[i].setAttribute("name", "");
        }
        for (var f = 0; f < arrayPasajeros.length; f++) {
            if (arrayPasajeros[f].asiento == todosLosAsientos[i].getAttribute("id")) {
                todosLosAsientos[i].setAttribute("name", "activado");
                todosLosAsientos[i].setAttribute("style", "color:green;");

            }
        }
    }


}
function borrarHijos(nodo) {
    if (nodo.hasChildNodes())
    {
        while (nodo.childNodes.length >= 1)
        {
            nodo.removeChild(nodo.firstChild);
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