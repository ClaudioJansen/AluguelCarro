// Verifica autenticação
function checkAuth() {
    const token = localStorage.getItem('token');
    const userType = localStorage.getItem('userType');
    
    if (!token || userType !== 'admin') {
        window.location.href = '../index.html';
    }
}

// Carrega solicitações pendentes
function loadPendingRentals() {
    fetch('/api/rentals/pending', {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => response.json())
    .then(rentals => {
        const tbody = document.querySelector('#pendingRentalsTable tbody');
        tbody.innerHTML = '';
        
        rentals.forEach(rental => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${rental.client.name}</td>
                <td>${rental.car.brand} ${rental.car.model} (${rental.car.year})</td>
                <td>${new Date(rental.startDate).toLocaleDateString()}</td>
                <td>${new Date(rental.endDate).toLocaleDateString()}</td>
                <td>
                    <button onclick="approveRental(${rental.id})" class="btn-primary">Aprovar</button>
                    <button onclick="rejectRental(${rental.id})" class="btn-primary" style="background-color: #dc3545;">Rejeitar</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    })
    .catch(error => {
        alert('Erro ao carregar solicitações: ' + error.message);
    });
}

// Carrega lista de carros
function loadCars() {
    fetch('/api/cars', {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => response.json())
    .then(cars => {
        const tbody = document.querySelector('#carsTable tbody');
        tbody.innerHTML = '';
        
        cars.forEach(car => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>${car.year}</td>
                <td>${car.plate}</td>
                <td>${car.status}</td>
                <td>
                    <button onclick="deleteCar(${car.id})" class="btn-primary" style="background-color: #dc3545;">Remover</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    })
    .catch(error => {
        alert('Erro ao carregar veículos: ' + error.message);
    });
}

// Aprova um aluguel
function approveRental(rentalId) {
    fetch(`/api/rentals/${rentalId}/approve`, {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => {
        if (response.ok) {
            loadPendingRentals();
            loadCars();
        } else {
            throw new Error('Não foi possível aprovar o aluguel');
        }
    })
    .catch(error => {
        alert('Erro ao aprovar aluguel: ' + error.message);
    });
}

// Rejeita um aluguel
function rejectRental(rentalId) {
    fetch(`/api/rentals/${rentalId}/reject`, {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => {
        if (response.ok) {
            loadPendingRentals();
        } else {
            throw new Error('Não foi possível rejeitar o aluguel');
        }
    })
    .catch(error => {
        alert('Erro ao rejeitar aluguel: ' + error.message);
    });
}

// Remove um carro
function deleteCar(carId) {
    if (confirm('Tem certeza que deseja remover este veículo?')) {
        fetch(`/api/cars/${carId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
        .then(response => {
            if (response.ok) {
                loadCars();
            } else {
                throw new Error('Não foi possível remover o veículo');
            }
        })
        .catch(error => {
            alert('Erro ao remover veículo: ' + error.message);
        });
    }
}

// Mostra/esconde formulário de adicionar carro
function showAddCarForm() {
    const form = document.getElementById('addCarForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

// Submete novo carro
document.getElementById('carForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const carData = {
        brand: document.getElementById('brand').value,
        model: document.getElementById('model').value,
        year: document.getElementById('year').value,
        plate: document.getElementById('plate').value
    };
    
    fetch('/api/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        body: JSON.stringify(carData)
    })
    .then(response => {
        if (response.ok) {
            alert('Veículo adicionado com sucesso!');
            this.reset();
            showAddCarForm();
            loadCars();
        } else {
            throw new Error('Não foi possível adicionar o veículo');
        }
    })
    .catch(error => {
        alert('Erro ao adicionar veículo: ' + error.message);
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
loadPendingRentals();
loadCars(); 