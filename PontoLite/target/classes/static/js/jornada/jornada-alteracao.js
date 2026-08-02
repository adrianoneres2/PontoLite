
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

document.addEventListener('DOMContentLoaded', () => {
    const tbody = document.getElementById('table-jornada-body');
    if (tbody) {
        minimumNumberOfDays = tbody.querySelectorAll('tr').length;
    }
});

function adicionarDia() {
    clearValidationMessage();
    const tbody = document.getElementById('table-jornada-body');
    const existingRows = Array.from(tbody.querySelectorAll('tr'));

    let valuesToCopy = [];
    if (existingRows.length > 0) {
        const lastRow = existingRows[existingRows.length - 1];
        const validation = validateRowTimes(lastRow);

        if (!validation.valid) {
            displayValidationMessage(validation.message);
            return;
        }

        const inputs = lastRow.querySelectorAll('input[type="time"]');
        valuesToCopy = Array.from(inputs).map(i => i.value);
    }

    minimumNumberOfDays++;

    const existingDayIds = existingRows.map(row => parseInt(row.dataset.diaId));

    const nextDay = DIAS_SEMANA.find(d => !existingDayIds.includes(d.id));

    if (!nextDay) {
        alert('Todos os dias da semana já foram adicionados.');
        return;
    }

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

    const insertBeforeRow = existingRows.find(row => parseInt(row.dataset.diaId) > nextDay.id);

    if (insertBeforeRow) {
        tbody.insertBefore(tr, insertBeforeRow);
    } else {
        tbody.appendChild(tr);
    }

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

        if (inputs.length !== 4) return;

        inputs.forEach((input, i) => {
            const tipoId = TIPOS_REGISTRO[i].id;
            input.name = `listaJornadaDataHora[${overallIndex}].hora`;

            const cell = input.parentElement;

            const allInputs = Array.from(cell.querySelectorAll('input'));

            allInputs.forEach(inp => {
                if (inp.name) {
                    inp.name = inp.name.replace(/listaJornadaDataHora\[\d+\]/, `listaJornadaDataHora[${overallIndex}]`);
                }
            });

            let inputDia = cell.querySelector('input[name$=".codigoDia"]');
            if (!inputDia) {
                inputDia = document.createElement('input');
                inputDia.type = 'hidden';
                inputDia.name = `listaJornadaDataHora[${overallIndex}].codigoDia`;
                cell.appendChild(inputDia);
            }
            inputDia.value = diaId;

            let inputTipo = cell.querySelector('input[name$=".codigoTipoRegistro"]');
            if (!inputTipo) {
                inputTipo = document.createElement('input');
                inputTipo.type = 'hidden';
                inputTipo.name = `listaJornadaDataHora[${overallIndex}].codigoTipoRegistro`;
                cell.appendChild(inputTipo);
            }
            inputTipo.value = tipoId;

            overallIndex++;
        });
    });
}

function validateRowTimes(row) {
    const inputs = row.querySelectorAll('input[type="time"]');
    if (inputs.length !== 4) return { valid: false, message: 'Erro interno: número incorreto de campos.' };

    const times = Array.from(inputs).map(input => input.value);

    if (times.some(t => !t)) {
        return { valid: false, message: 'Preencha todos os horários do dia anterior antes de adicionar o próximo.' };
    }

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

    let changedMinimumNumberOfDays = 0;

    const tbody = document.getElementById('table-jornada-body');
    const rows = tbody.querySelectorAll('tr');
    console.log('rows', rows.length);
    changedMinimumNumberOfDays = rows.length;

    if (changedMinimumNumberOfDays < 1) {
        displayValidationMessage('Pelo menos um dia deve ser adicionado.');
        return false;
    }


    console.log('minimumNumberOfDays', changedMinimumNumberOfDays);
    for (const row of rows) {
        // minimumNumberOfDays
        const validation = validateRowTimes(row);
        if (!validation.valid) {
            changedMinimumNumberOfDays--;
            const diaNome = row.querySelector('td.fw-bold').textContent;
            displayValidationMessage(`<strong>${diaNome}</strong>: ${validation.message}`);
            return false;
        }
    }

    return true;
}
