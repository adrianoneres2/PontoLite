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
// Fonte Gobal do sistema
$(document).ready(function() {
    $('body').css('font-family', 'Fnt_Oswald-VariableFont_wght, Arial, sans-serif');
});

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
