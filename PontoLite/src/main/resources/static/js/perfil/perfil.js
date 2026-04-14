let configuracoesLocal = [];

function carregarConfiguracoes() {
    const codCliente = document.getElementById("select-cliente").value;
    const container = document.getElementById("container-configuracao");

    if (!codCliente) {
        container.style.display = "none";
        configuracoesLocal = [];
        renderizarTabela();
        return;
    }

    container.style.display = "block";

    fetch(`/pontolite/perfil/api/listar-configuracoes?codCliente=${codCliente}`)
        .then(response => response.json())
        .then(data => {
            configuracoesLocal = data;
            renderizarTabela();
        })
        .catch(error => {
            console.error("Erro ao carregar configurações:", error);
            alert("Erro ao carregar configurações. Verifique o console.");
        });
}

function renderizarTabela() {
    const tbody = document.querySelector("#tabela-configuracoes tbody");
    const emptyState = document.getElementById("empty-state-message");

    tbody.innerHTML = "";

    if (configuracoesLocal.length === 0) {
        emptyState.style.display = "block";
        document.getElementById("tabela-configuracoes").style.display = "none";
        return;
    }

    emptyState.style.display = "none";
    document.getElementById("tabela-configuracoes").style.display = "table";

    configuracoesLocal.forEach((config, index) => {
        const tr = document.createElement("tr");

        const tdPerfil = document.createElement("td");
        tdPerfil.textContent = config.nomePerfil;

        const tdFunc = document.createElement("td");
        tdFunc.textContent = config.nomeFuncionalidade;

        const tdAcao = document.createElement("td");
        tdAcao.className = "text-center";

        const btnRemover = document.createElement("button");
        btnRemover.className = "btn btn-danger btn-sm";
        btnRemover.innerHTML = '<i class="bi bi-trash"></i>';
        btnRemover.onclick = function () {
            removerConfiguracao(index);
        };
        tdAcao.appendChild(btnRemover);

        tr.appendChild(tdPerfil);
        tr.appendChild(tdFunc);
        tr.appendChild(tdAcao);

        tbody.appendChild(tr);
    });
}

function adicionarConfiguracao() {
    const selectPerfil = document.getElementById("select-perfil");
    const selectFuncionalidade = document.getElementById("select-funcionalidade");

    const idPerfil = selectPerfil.value;
    const nomePerfil = selectPerfil.options[selectPerfil.selectedIndex].text;
    const idFuncionalidade = selectFuncionalidade.value;
    const nomeFuncionalidade = selectFuncionalidade.options[selectFuncionalidade.selectedIndex].text;

    if (!idPerfil || !idFuncionalidade) {
        alert("Por favor, selecione um perfil e uma funcionalidade para adicionar.");
        return;
    }

    // Verificar duplicação
    const duplicado = configuracoesLocal.some(c =>
        c.idPerfil == idPerfil && c.idFuncionalidade == idFuncionalidade
    );

    if (duplicado) {
        alert("Esta configuração já foi adicionada!");
        return;
    }

    configuracoesLocal.push({
        idPerfil: parseInt(idPerfil),
        nomePerfil: nomePerfil,
        idFuncionalidade: parseInt(idFuncionalidade),
        nomeFuncionalidade: nomeFuncionalidade
    });

    renderizarTabela();
}

function removerConfiguracao(index) {
    configuracoesLocal.splice(index, 1);
    renderizarTabela();
}

function salvarConfiguracoes() {
    const codCliente = document.getElementById("select-cliente").value;
    if (!codCliente) {
        alert("Nenhum cliente selecionado.");
        return;
    }

    const csrfTokenEl = document.getElementById("csrfToken");
    const csrfHeaderEl = document.getElementById("csrfHeader");
    const headers = {
        "Content-Type": "application/json"
    };

    if (csrfTokenEl && csrfHeaderEl) {
        headers[csrfHeaderEl.value] = csrfTokenEl.value;
    }

    const btn = document.getElementById("bt-salvar");
    btn.disabled = true;
    btn.innerHTML = '<i class="bi bi-hourglass-split"></i> Salvando...';

    fetch(`/pontolite/perfil/api/salvar-configuracao?codCliente=${codCliente}`, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(configuracoesLocal)
    })
        .then(response => {
            if (response.ok) {
                alert("Configurações salvas com sucesso!");
                carregarConfiguracoes(); // Recarrega
            } else {
                throw new Error("Erro na rede ou backend: " + response.status);
            }
        })
        .catch(error => {
            console.error("Erro ao salvar:", error);
            alert("Houve um erro ao salvar configurações. Consulte o administrador do sistema.");
        })
        .finally(() => {
            btn.disabled = false;
            btn.innerHTML = '<i class="bi bi-check-lg"></i> Salvar Configurações';
        });
}
