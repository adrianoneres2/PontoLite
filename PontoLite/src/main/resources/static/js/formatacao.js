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
    }

    // Validação de CNPJ
    function validar_cnpj(cnpj) {
      cnpj = cnpj.replace(/[^\d]+/g, '');
      if (cnpj.length !== 14 || /^(\d)\1+$/.test(cnpj)) {
        marcar_invalido();
        return false;
      }

      let tamanho = cnpj.length - 2;
      let numeros = cnpj.substring(0, tamanho);
      let digitos = cnpj.substring(tamanho);
      let soma = 0;
      let pos = tamanho - 7;

      for (let i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2) pos = 9;
      }

      let resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resultado != digitos.charAt(0)) {
        marcar_invalido();
        return false;
      }

      tamanho = tamanho + 1;
      numeros = cnpj.substring(0, tamanho);
      soma = 0;
      pos = tamanho - 7;

      for (let i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2) pos = 9;
      }

      resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resultado != digitos.charAt(1)) {
        marcar_invalido();
        return false;
      }

      marcar_valido();
      return true;
    }

    function marcar_valido() {
      const input = document.getElementById("input-cnpj");
	  input.classList.remove("background-color: red");
      input.classList.add("background-color: blue");
    }

    function marcar_invalido() {
      const input = document.getElementById("input-cnpj");
      input.classList.remove("background-color: blue");
      input.classList.add("background-color: red");
    }
	
	$(document).ready(function(){
	  $(':input[type="text"]').css('background-color', 'lightblue');
	  $(':input[type="number"]').css('background-color', 'lightgreen');
	  $(':input[type="email"]').css('background-color', 'lightyellow');
	});
	
