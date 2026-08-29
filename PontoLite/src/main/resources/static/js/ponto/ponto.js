/**
  Controles de tela para o relatório e alteração de registro de ponto
 */

function abrirModalPontoViaData(btn) {
    const codigoRegistroPonto = btn.getAttribute('data-codigo');
    const dataFormatada = btn.getAttribute('data-data-formatada');
    const horaAtual = btn.getAttribute('data-hora');
    const tipoNome = btn.getAttribute('data-tipo');
    const dataIso = btn.getAttribute('data-data-iso');
    abrirModalAlterarPonto(codigoRegistroPonto, dataFormatada, horaAtual, tipoNome, dataIso);
}

function abrirModalAlterarPonto(codigoRegistroPonto, dataFormatada, horaAtual, tipoNome, dataIso) {
    document.getElementById('modalCodigoRegistroPonto').value = codigoRegistroPonto;
    document.getElementById('modalDataIso').value = dataIso;
    document.getElementById('modalHoraRegistro').value = horaAtual;
    document.getElementById('modalTipoRegistroNome').textContent = tipoNome;
    document.getElementById('modalDataRegistro').textContent = dataFormatada;

    // Exibe o modal Bootstrap
    if (window.jQuery && $('#modalAlterarPonto').modal) {
        $('#modalAlterarPonto').modal('show');
    } else {
        const modalEl = document.getElementById('modalAlterarPonto');
        if (modalEl) {
            modalEl.classList.add('show');
            modalEl.style.display = 'block';
        }
    }
}

function fecharModalAlterarPonto() {
    if (window.jQuery && $('#modalAlterarPonto').modal) {
        $('#modalAlterarPonto').modal('hide');
    } else {
        const modalEl = document.getElementById('modalAlterarPonto');
        if (modalEl) {
            modalEl.classList.remove('show');
            modalEl.style.display = 'none';
        }
    }
}

function submeterAlteracaoPonto(form, event) {
    event.preventDefault();

    const dataIso = document.getElementById('modalDataIso').value;
    const hora = document.getElementById('modalHoraRegistro').value;

    if (!hora) {
        alert('Por favor, informe um horário válido.');
        return;
    }

    // Combina data e hora no formato ISO (yyyy-MM-ddTHH:mm:ss)
    const horaComSegundos = hora.length === 5 ? hora + ':00' : hora;
    const dataHoraIso = dataIso + 'T' + horaComSegundos;

    document.getElementById('modalDataHoraFinalIso').value = dataHoraIso;

    fecharModalAlterarPonto();

    // Utiliza submitFormViaFetch do menu-fetch-api.js
    submitFormViaFetch(form, event);
}


function filtrarRelatorioPeriodo(event) {
    const dataInicial = document.getElementById('inputDataInicial').value;
    const dataFinal = document.getElementById('inputDataFinal').value;

    let url = '/pontolite/ponto/listar-periodo-usuario';
    const params = [];
    if (dataInicial) {
        params.push('dataHoraInicial=' + dataInicial + 'T00:00:00');
    }
    if (dataFinal) {
        params.push('dataHoraFinal=' + dataFinal + 'T23:59:59');
    }
    if (params.length > 0) {
        url += '?' + params.join('&');
    }

    loadContentViaFetch(url, event);
}