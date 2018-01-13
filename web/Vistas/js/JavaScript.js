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

function getEstaciones() {
    objetoAjax = AJAXCrearObjeto(); //crea el objeto
    objetoAjax.open('GET', '/Proyecto_autobuses/getEstaciones_controlador');
    objetoAjax.send();
    objetoAjax.onreadystatechange = function () {
        if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
            alert(objetoAjax.responseText);
        }
    }

}
