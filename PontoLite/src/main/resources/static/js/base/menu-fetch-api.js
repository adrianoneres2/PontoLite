/**
 * Carrega conteúdo via fetch
 * 
 * @param {string} url URL para carregar conteúdo
 * @param {Event} event Evento de clique
 */
function loadContentViaFetch(url, event, options = {}) {
    if (event) {
        event.preventDefault(); // Impede o recarregamento total da página
    }
    console.log('URL: ' + url);
    fetch(url, options)
        .then(response => {
            if (!response.ok) {
                switch (response.status) {
                    case 401:
                        window.location.href = '/pontolite/login';
                        break;
                    case 403:
                        throw new Error('Verifique permissões de acesso!');
                    case 404:
                        throw new Error('Recurso não encontrado!');
                    case 500:
                        throw new Error('Erro interno do servidor!');
                    default:
                        throw new Error('Erro na requisição: ' + response.status);
                }
            }
            return response.text();
        })
        .then(htmlString => {
            // Cria um documento virtual para extrair o conteúdo da tag <main>
            const parser = new DOMParser();
            const doc = parser.parseFromString(htmlString, 'text/html');
            const newMainContent = doc.querySelector('main');
            const newFloatingMessage = doc.querySelector('message');

            if (newMainContent) {
                const currentMain = document.querySelector('main');
                if (currentMain) {
                    // Substitui o elemento main atual pelo novo carregado via fetch
                    currentMain.replaceWith(newMainContent);

                    // Para que scripts incluídos no HTML fragmentado sejam executados (ex: relógio),
                    // precisamos extraí-los e re-inseri-los no DOM.
                    const scripts = newMainContent.querySelectorAll('script');
                    scripts.forEach(script => {
                        const newScript = document.createElement('script');
                        if (script.src) {
                            newScript.src = script.src;
                        } else {
                            newScript.textContent = script.textContent;
                        }
                        document.body.appendChild(newScript);
                        script.remove();
                    });
                }
            }
            if (newFloatingMessage) {
                const currentFloatingMessage = document.querySelector('message');
                if (currentFloatingMessage) {
                    // Substitui o elemento main atual pelo novo carregado via fetch
                    currentFloatingMessage.replaceWith(newFloatingMessage);
                }
            }
        })
        .catch(error => {
            console.error('Erro ao carregar o conteúdo:', error);
            const errorMessage = document.querySelector('main');
            errorMessage.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
        });
}

/**
 * Envia um formulário via fetch e atualiza o conteúdo da página
 * 
 * @param {HTMLFormElement} form Elemento do formulário
 * @param {Event} event Evento de submit
 */
function submitFormViaFetch(form, event) {
    event.preventDefault();
    const url = form.action;
    const options = {
        method: form.method || 'POST',
        body: new FormData(form)
    };
    loadContentViaFetch(url, null, options);
}


function pesquisarFormulario(urlBase, event) {

    event.preventDefault();

    /**
     * Pega o valor atual digitado no input se ele existir
     */
    let value = '';
    let value2 = '';

    if (document.getElementById('searchInput')) {
        value = document.getElementById('searchInput').value;
    }
    if (document.getElementById('searchInput2')) {
        value2 = document.getElementById('searchInput2').value;
    }

    //console.log('URL: ' + urlBase);
    //console.log('Value: ' + value);

    // Monta a URL dinamicamente
    const url = urlBase.replace('parametro1', value);
    const url2 = url.replace('parametro2', value2);
    //console.log('URL: ' + url);
    // Chama a sua função original passando a URL construída
    loadContentViaFetch(url2, event);
}


function apiAtualizaStatus(urlBase, event) {
    if (event) {
        event.preventDefault(); // Impede o recarregamento total da página
    }
    console.log('URL: ' + urlBase);

    // Obtém o elemento do botão clicado (independente de ter clicado no botão ou no ícone dentro dele)
    const buttonElement = event ? (event.currentTarget || event.target.closest('a') || event.target) : null;

    fetch(urlBase, {
        method: 'GET',
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            console.log('Status atualizado com sucesso!');
            if (buttonElement) {
                // Alterna as classes do botão (sucesso <-> perigo)
                if (buttonElement.classList.contains('btn-outline-success')) {
                    buttonElement.classList.remove('btn-outline-success');
                    buttonElement.classList.add('btn-outline-danger');
                } else if (buttonElement.classList.contains('btn-outline-danger')) {
                    buttonElement.classList.remove('btn-outline-danger');
                    buttonElement.classList.add('btn-outline-success');
                }

                // Alterna as classes do ícone contido no botão
                const iconElement = buttonElement.querySelector('i');
                if (iconElement) {
                    if (iconElement.classList.contains('bi-check-circle-fill')) {
                        iconElement.classList.remove('bi-check-circle-fill', 'text-success');
                        iconElement.classList.add('bi-x-circle-fill', 'text-danger');
                    } else if (iconElement.classList.contains('bi-x-circle-fill')) {
                        iconElement.classList.remove('bi-x-circle-fill', 'text-danger');
                        iconElement.classList.add('bi-check-circle-fill', 'text-success');
                    }
                }
            }
        } else {
            console.error('Erro ao atualizar status! Código HTTP:', response.status);
        }
    }).catch(error => {
        console.error('Exception - Erro ao atualizar status:', error);
    });
}