// Verifica autenticação
function checkAuth() {
    const token = localStorage.getItem('token');
    const userType = localStorage.getItem('userType');
    
    if (!token || userType !== 'client') {
        window.location.href = '../index.html';
    }
}

// Carrega os aluguéis do cliente
function loadRentals() {
    fetch('/api/rentals/my-rentals', {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => response.json())
    .then(rentals => {
        const tbody = document.querySelector('#rentalsTable tbody');
        tbody.innerHTML = '';
        
        rentals.forEach(rental => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${rental.car.brand} ${rental.car.model} (${rental.car.year})</td>
                <td>${new Date(rental.startDate).toLocaleDateString()}</td>
                <td>${new Date(rental.endDate).toLocaleDateString()}</td>
                <td>${rental.status}</td>
                <td>
                    ${rental.status === 'PENDING' ? 
                        `<button onclick="cancelRental(${rental.id})" class="btn-primary">Cancelar</button>` : 
                        ''}
                </td>
            `;
            tbody.appendChild(tr);
        });
    })
    .catch(error => {
        alert('Erro ao carregar aluguéis: ' + error.message);
    });
}

// Carrega os carros disponíveis
function loadAvailableCars() {
    fetch('/api/cars/available', {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => response.json())
    .then(cars => {
        const select = document.getElementById('carSelect');
        select.innerHTML = '<option value="">Selecione um veículo</option>';
        
        cars.forEach(car => {
            const option = document.createElement('option');
            option.value = car.id;
            option.textContent = `${car.brand} ${car.model} (${car.year}) - Placa: ${car.plate}`;
            select.appendChild(option);
        });
    })
    .catch(error => {
        alert('Erro ao carregar veículos: ' + error.message);
    });
}

// Cancela um aluguel
function cancelRental(rentalId) {
    if (confirm('Tem certeza que deseja cancelar este aluguel?')) {
        fetch(`/api/rentals/${rentalId}/cancel`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
        .then(response => {
            if (response.ok) {
                loadRentals();
            } else {
                throw new Error('Não foi possível cancelar o aluguel');
            }
        })
        .catch(error => {
            alert('Erro ao cancelar aluguel: ' + error.message);
        });
    }
}

// Submete novo aluguel
document.getElementById('newRentalForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const rentalData = {
        carId: document.getElementById('carSelect').value,
        startDate: document.getElementById('startDate').value,
        endDate: document.getElementById('endDate').value
    };
    
    fetch('/api/rentals', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        body: JSON.stringify(rentalData)
    })
    .then(response => {
        if (response.ok) {
            alert('Solicitação de aluguel enviada com sucesso!');
            this.reset();
            loadRentals();
        } else {
            throw new Error('Não foi possível criar o aluguel');
        }
    })
    .catch(error => {
        alert('Erro ao solicitar aluguel: ' + error.message);
    });
});

// Função de logout
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userType');
    window.location.href = '../index.html';
}

// Inicialização
checkAuth();
loadRentals();
loadAvailableCars(); 