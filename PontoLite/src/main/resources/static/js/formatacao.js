/*
$(document).ready(function(){
  $(':input[type="text"]').css('background-color', 'lightblue');
  $(':input[type="number"]').css('background-color', 'lightgreen');
  $(':input[type="email"]').css('background-color', 'lightyellow');
  $(':input[type="password"]').css('background-color', 'lightyellow');
  $(':input[type="text"]').css('font-size', '20px');
  $(':input[type="email"]').css('font-size', '20px');
  $(':input[type="number"]').css('font-size', '20px');
  $(':input[type="password"]').css('font-size', '20px');
});
*/


// Máscara de CNPJ
function mascara_cnpj(campo) {
  let cnpj = campo.value.replace(/\D/g, '');

  if (cnpj.length <= 14) {
    cnpj = cnpj.replace(/^(\d{2})(\d)/, "$1.$2");
    cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
    cnpj = cnpj.replace(/\.(\d{3})(\d)/, ".$1/$2");
    cnpj = cnpj.replace(/(\d{4})(\d)/, "$1-$2");
    campo.value = cnpj;
  }
};

function mascaraTelefonefone(event, field) {
  var valor = document.getElementById(field).attributes[0].ownerElement['value'];
  var retorno = valor.replace(/\D/g, "");
  retorno = retorno.replace(/^0/, "");
  if (retorno.length > 10) {
    retorno = retorno.replace(/^(\d\d)(\d{5})(\d{4}).*/, "($1) $2-$3");
  } else if (retorno.length > 5) {
    if (retorno.length == 6 && event.code == "Backspace") { 
      // necessário pois senão o "-" fica sempre voltando ao dar backspace
      return; 
    } 
    retorno = retorno.replace(/^(\d\d)(\d{4})(\d{0,4}).*/, "($1) $2-$3");
  } else if (retorno.length > 2) {
    retorno = retorno.replace(/^(\d\d)(\d{0,5})/, "($1) $2");
  } else {
    if (retorno.length != 0) {
      retorno = retorno.replace(/^(\d*)/, "($1");
    }
  }
  document.getElementById(field).attributes[0].ownerElement['value'] = retorno;
}


///Consulta no formulário a medida que vai sendo digitado
document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.getElementById('searchInput');
    const table = document.getElementById('clientTable');
    const rows = table.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    const noResultsAlert = document.getElementById('noResults');

    searchInput.addEventListener('keyup', function() {
        const searchTerm = searchInput.value.toLowerCase();
        let found = false;

        for (let i = 0; i < rows.length; i++) {
            const row = rows[i];
            const cells = row.getElementsByTagName('td');
            let rowText = '';
            for (let j = 0; j < cells.length; j++) {
                rowText += cells[j].textContent.toLowerCase() + ' ';
            }

            if (rowText.includes(searchTerm)) {
                row.style.display = '';
                found = true;
            } else {
                row.style.display = 'none';
            }
        }
        
        // Exibe ou esconde o alerta de "nenhum resultado"
        if (found) {
            noResultsAlert.style.display = 'none';
        } else {
            noResultsAlert.style.display = 'block';
        }
    });
});

/*Balão de mensagem */
$(document).ready(function(){
	
	let textMessage = document.getElementById("id-mensagem-aplicacao").textContent;
	///alert(textMessage.trim().length);
	
	if(textMessage.trim().length > 0){
		///alert(textMessage);
			$('#id-mensagem-aplicacao').fadeIn(500); // Mostra o balão gradualmente
			// Fecha o balão com efeito fade-out após 3 segundos
			setTimeout(function(){
			    $('#id-mensagem-aplicacao').fadeOut(500);
			}, 3000); // 3000 milissegundos = 3 segundos	
	}
});