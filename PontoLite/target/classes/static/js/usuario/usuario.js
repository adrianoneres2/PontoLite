
/*
 * Função para validar a igualdade entre senha e confirmação de senha.
 * Modulo: Cadastro de usuário.
 * Param: input-password
 * Param: input-password-confirm
 */
function validar_confirmacao_senha() {
	let senha = document.getElementById("input-password").value;
	let senha_confirmacao = document.getElementById("input-password-confirm").value;

	if (senha != senha_confirmacao) {
		document.getElementById("input-password-confirm").style.backgroundColor = "#ad5037";
		document.getElementById("input-password-confirm").value = "";
	} else {
		document.getElementById("input-password-confirm").style.backgroundColor = "#74a348";
	}
}


function carregarJornadaPorCliente() {
	let codigoCliente = document.getElementById("input-nome-cliente").value;

	fetch(`/pontolite/usuario/api/listar-jornada-cliente?codigoCliente=${codigoCliente}`)
		.then(response => response.json())
		.then(data => {
			let jornadas = document.getElementById("input-nome-jornada");
			jornadas.innerHTML = "";
			data.forEach(jornada => {
				let option = document.createElement("option");
				option.value = jornada.codigoJornada;
				option.text = jornada.nomeJornada;
				jornadas.appendChild(option);
			});
			jornadas.disabled = false;
		}).catch(error => {
			console.log("error" + error);
		});
}