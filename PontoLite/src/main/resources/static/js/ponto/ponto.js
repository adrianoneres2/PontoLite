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
    const observacao = document.getElementById('modalObservacao').value;

    if (!hora) {
        alert('Por favor, informe um horário válido.');
        return;
    }

    if (!observacao) {
        alert('Por favor, informe uma observação.');
        return;
    }

    // Combina data e hora no formato ISO (yyyy-MM-ddTHH:mm:ss)
    const horaComSegundos = hora.length === 5 ? hora + ':00' : hora;
    const dataHoraIso = dataIso + 'T' + horaComSegundos;

    document.getElementById('modalDataHoraFinalIso').value = dataHoraIso;
    document.getElementById('modalObservacaoFinal').value = observacao;

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

/**
 * Busca a quantidade de solicitações de alteração de ponto aguardando aprovação
 * e atualiza apenas o contador no ícone de notificação.
 */
function buscarAguardandoAprovacaoNotificacao(event) {
    if (event) {
        event.preventDefault();
    }
    const contadorElement = document.getElementById('contador-aprovacoes-pendentes');

    console.log('contadorElement', contadorElement);
    if (!contadorElement) return;

    fetch('/pontolite/ponto/buscar-aguardando-aprovacao')
        .then(response => {
            if (!response.ok) {
                console.error('HTTP Error:', response.status, response.statusText);
                throw new Error('Erro ao buscar solicitações aguardando aprovação');
            }
            return response.json();
        })
        .then(data => {
            if (data && data.quantidade !== undefined) {
                contadorElement.textContent = data.quantidade;
            }
        })
        .catch(error => {
            console.error('Erro ao atualizar contador de notificações:', error);
        });
}

// Inicializa o contador ao carregar a página se o ícone estiver visível na tela
if (typeof $ !== 'undefined') {
    $(document).ready(function () {
        if (document.getElementById('contador-aprovacoes-pendentes')) {
            buscarAguardandoAprovacaoNotificacao();
        }
    });
} else {
    document.addEventListener('DOMContentLoaded', function () {
        if (document.getElementById('contador-aprovacoes-pendentes')) {
            buscarAguardandoAprovacaoNotificacao();
        }
    });
}