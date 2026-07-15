/**
 * Carrega conteúdo via fetch
 * 
 * @param {string} url URL para carregar conteúdo
 * @param {Event} event Evento de clique
 */
function loadContentViaFetch(url, event) {
    event.preventDefault(); // Impede o recarregamento total da página
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na requisição: ' + response.status);
            }
            return response.text();
        })
        .then(htmlString => {
            // Cria um documento virtual para extrair o conteúdo da tag <main>
            const parser = new DOMParser();
            const doc = parser.parseFromString(htmlString, 'text/html');
            const newMainContent = doc.querySelector('main');

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
        })
        .catch(error => {
            console.error('Erro ao carregar o conteúdo:', error);
        });
}