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
    minimumNumberOfDays++;
    const tbody = document.getElementById('table-jornada-body');
    const existingRows = Array.from(tbody.querySelectorAll('tr'));

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
    // We want to insert this row such that the running order of diaId is maintained.
    // Since we iterate DIAS_SEMANA in order, finding the *first* missing one guarantees
    // we just need to find the first existing row with ID > nextDay.id and insert before it.

    const insertBeforeRow = existingRows.find(row => parseInt(row.dataset.diaId) > nextDay.id);

    if (insertBeforeRow) {
        tbody.insertBefore(tr, insertBeforeRow);
    } else {
        tbody.appendChild(tr);
    }

    updateIndexes();
}

function excluirDia(btn) {
    const row = btn.closest('tr');
    row.remove();
    updateIndexes();
    minimumNumberOfDays--;
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

function validateForm() {
    if (minimumNumberOfDays < 1) {
        const message = document.getElementById('validation-message');
        message.classList.add('alert', 'alert-danger');
        message.textContent = 'Pelo menos um dia deve ser adicionado.';
        message.style.marginTop = '1rem';
        return false;
    }
    return true;
}