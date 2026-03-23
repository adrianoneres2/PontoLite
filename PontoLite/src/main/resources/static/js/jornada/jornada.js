// EnumDiaSemana mapping
const DIAS_SEMANA = [
    { id: 1, nome: 'Segunda-feira' },
    { id: 2, nome: 'Terça-feira' },
    { id: 3, nome: 'Quarta-feira' },
    { id: 4, nome: 'Quinta-feira' },
    { id: 5, nome: 'Sexta-feira' },
    { id: 6, nome: 'Sábado' },
    { id: 7, nome: 'Domingo' }
];

// EnumTipoRegistroPonto mapping
const TIPOS_REGISTRO = [
    { id: 1, nome: 'ENTRADA' },
    { id: 2, nome: 'INTERVALO' },
    { id: 3, nome: 'RETORNO_INTERVALO' },
    { id: 4, nome: 'SAIDA' }
];

let minimumNumberOfDays = 0;

function adicionarDia() {
    clearValidationMessage();
    const tbody = document.getElementById('table-jornada-body');
    const existingRows = Array.from(tbody.querySelectorAll('tr'));

    // 1. Validate the last row if it exists
    let valuesToCopy = [];
    if (existingRows.length > 0) {
        const lastRow = existingRows[existingRows.length - 1]; // Validate the last added day
        const validation = validateRowTimes(lastRow);

        if (!validation.valid) {
            displayValidationMessage(validation.message);
            return; // Stop if invalid
        }

        // 2. Capture values to copy
        const inputs = lastRow.querySelectorAll('input[type="time"]');
        valuesToCopy = Array.from(inputs).map(i => i.value);
    }

    minimumNumberOfDays++;

    // Find existing day IDs
    const existingDayIds = existingRows.map(row => parseInt(row.dataset.diaId));

    // Find the first missing day in the sequence
    const nextDay = DIAS_SEMANA.find(d => !existingDayIds.includes(d.id));

    if (!nextDay) {
        alert('Todos os dias da semana já foram adicionados.');
        return;
    }

    // Create row
    const tr = document.createElement('tr');
    tr.dataset.diaId = nextDay.id;
    tr.innerHTML = `
				<td class="fw-bold">${nextDay.nome}</td>
				<td><input type="time" class="form-control" required></td>
				<td><input type="time" class="form-control" required></td>
				<td><input type="time" class="form-control" required></td>
				<td><input type="time" class="form-control" required></td>
				<td class="text-center">
					<button type="button" class="btn btn-outline-danger btn-sm" onclick="excluirDia(this)">
						<i class="bi bi-trash"></i>
					</button>
				</td>
			`;

    // Insert in correct order (visual "manter a ordem")
    const insertBeforeRow = existingRows.find(row => parseInt(row.dataset.diaId) > nextDay.id);

    if (insertBeforeRow) {
        tbody.insertBefore(tr, insertBeforeRow);
    } else {
        tbody.appendChild(tr);
    }

    // 3. Auto-fill values
    if (valuesToCopy.length === 4) {
        const newInputs = tr.querySelectorAll('input[type="time"]');
        newInputs.forEach((input, index) => {
            input.value = valuesToCopy[index];
        });
    }

    updateIndexes();
}

function excluirDia(btn) {
    const row = btn.closest('tr');
    row.remove();
    updateIndexes();
    minimumNumberOfDays--;
    clearValidationMessage();
}

function updateIndexes() {
    const tbody = document.getElementById('table-jornada-body');
    const rows = tbody.querySelectorAll('tr');

    let overallIndex = 0;

    rows.forEach(row => {
        const diaId = row.dataset.diaId;
        const inputs = row.querySelectorAll('input[type="time"]');

        // We expect exactly 4 inputs: Entrada, Intervalo, Retorno, Saida
        if (inputs.length !== 4) return;

        inputs.forEach((input, i) => {
            // i maps to EnumTipoRegistroPonto sequence: 0->1, 1->2, 2->3, 3->4
            const tipoId = TIPOS_REGISTRO[i].id;

            // Set names for Spring List binding
            // listaJornadaDataHora[0], listaJornadaDataHora[1], etc.

            // 1. Data/Hora Input
            input.name = `listaJornadaDataHora[${overallIndex}].hora`;

            // 2. Hidden inputs for IDs
            // Check if hidden inputs already exist for this slot, else create them
            // We need to group them with the time input or just append to cell?
            // Appending to cell might be messy if we re-render frequent.
            // Let's ensure clean state: remove old hiddens in this cell and add new ones.

            const cell = input.parentElement;
            const oldHiddens = cell.querySelectorAll('input[type="hidden"]');
            oldHiddens.forEach(h => h.remove());

            const inputDia = document.createElement('input');
            inputDia.type = 'hidden';
            inputDia.name = `listaJornadaDataHora[${overallIndex}].codigoDia`;
            inputDia.value = diaId;
            cell.appendChild(inputDia);

            const inputTipo = document.createElement('input');
            inputTipo.type = 'hidden';
            inputTipo.name = `listaJornadaDataHora[${overallIndex}].codigoTipoRegistro`;
            inputTipo.value = tipoId;
            cell.appendChild(inputTipo);

            overallIndex++;
        });
    });
}

function validateRowTimes(row) {
    const inputs = row.querySelectorAll('input[type="time"]');
    if (inputs.length !== 4) return { valid: false, message: 'Erro interno: número incorreto de campos.' };

    const times = Array.from(inputs).map(input => input.value);

    // Check if all fields are filled
    if (times.some(t => !t)) {
        return { valid: false, message: 'Preencha todos os horários do dia anterior antes de adicionar o próximo.' };
    }

    // Validation logic: Entrada < Intervalo < Retorno < Saída
    // String comparison works for HH:mm format (24h)
    if (times[0] >= times[1]) return { valid: false, message: 'A Entrada deve ser menor que o Intervalo.' };
    if (times[1] >= times[2]) return { valid: false, message: 'O Intervalo deve ser menor que o Retorno.' };
    if (times[2] >= times[3]) return { valid: false, message: 'O Retorno deve ser menor que a Saída.' };

    return { valid: true };
}

function displayValidationMessage(message, type = 'danger') {
    const msgDiv = document.getElementById('validation-message');
    msgDiv.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
                            ${message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>`;
}

function clearValidationMessage() {
    const msgDiv = document.getElementById('validation-message');
    if (msgDiv) msgDiv.innerHTML = '';
}

function validateForm() {
    clearValidationMessage();
    if (minimumNumberOfDays < 1) {
        displayValidationMessage('Pelo menos um dia deve ser adicionado.');
        return false;
    }

    const tbody = document.getElementById('table-jornada-body');
    const rows = tbody.querySelectorAll('tr');
    for (const row of rows) {
        const validation = validateRowTimes(row);
        if (!validation.valid) {
            const diaNome = row.querySelector('td.fw-bold').textContent;
            displayValidationMessage(`<strong>${diaNome}</strong>: ${validation.message}`);
            return false;
        }
    }
    return true;
}


function updateIndexesAlteracao() {
    /**
     * Atualiza os índices dos campos de data e hora da jornada.
     * Isso é necessário para que o Spring consiga mapear os campos corretamente.
     */
    const tbody = document.getElementById('table-jornada-body');
    const rows = tbody.querySelectorAll('tr');
}