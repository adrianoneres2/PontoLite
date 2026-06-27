function marcar_invalido() {
  const input = document.getElementById("input-cnpj");
  input.value = "";
  input.style.backgroundColor = "#f1babaff";
};

function marcar_valido() {
  const input = document.getElementById("input-cnpj");
  input.style.backgroundColor = "#94c78bff";
};

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
};


/* jQuery para animação dos submenus */
$(document).ready(function () {
  // Altera o evento de hover para click no item de menu
  $(".has-submenu > .menu-item").on("click", function (event) {
    // Evita que o link direcione para outra página
    event.preventDefault();

    let parent = $(this).parent();

    // Adiciona/remove a classe "open" para controlar o estado do menu
    parent.toggleClass("open");

    // Alterna a exibição do submenu com um efeito de deslize
    parent.children(".submenu").stop(true, true).slideToggle(200);
  });
});


// Adicione um evento de clique ao botão
document.getElementById("default-botao-voltar").addEventListener("click", function () {
  // Redireciona para a página anterior no histórico do navegador
  history.back();
});

function displayValidationMessage(message, type = 'danger') {
  const msgDiv = document.getElementById('validation-message');
  msgDiv.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
                            ${message}
                        </div>`;
}

function clearValidationMessage() {
  const msgDiv = document.getElementById('validation-message');
  if (msgDiv) msgDiv.innerHTML = '';
}

function displayFloatValidationMessage(message) {
  //alert(message);
  const msgDiv = document.getElementById('validation-message');
  msgDiv.innerHTML = `<div class="alert-float alert alert-success" role="alert">
                            ${message}
                        </div>`;
  $('#validation-message').fadeIn(500); // Mostra o balão gradualmente
  // Fecha o balão com efeito fade-out após 3 segundos
  setTimeout(function () {
    $('#validation-message').fadeOut(500);
  }, 3000); // 3000 milissegundos = 3 segundos
}