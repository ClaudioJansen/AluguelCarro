document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    
    // Verifica se é um admin pelo domínio do email
    const isAdmin = email.endsWith('@admin');
    
    // Dados do login
    const loginData = {
        email: email,
        password: password
    };
    
    // Faz a requisição para o backend
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Falha no login');
        }
        return response.json();
    })
    .then(data => {
        // Armazena o token JWT (se seu backend utilizar)
        localStorage.setItem('token', data.token);
        localStorage.setItem('userType', isAdmin ? 'admin' : 'client');
        
        // Redireciona baseado no tipo de usuário
        if (isAdmin) {
            window.location.href = 'admin/dashboard.html';
        } else {
            window.location.href = 'client/dashboard.html';
        }
    })
    .catch(error => {
        alert('Erro no login: ' + error.message);
    });
}); 