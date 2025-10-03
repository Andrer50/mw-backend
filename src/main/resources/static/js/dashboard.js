function mostrarSeccion(id) {
	document.querySelectorAll('.content-section').forEach((sec) => (sec.style.display = 'none'));
	document.getElementById(id).style.display = 'block';
}

// Inicializar todo cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function () {
	// Inicializar gráfico
	const ctx = document.getElementById('ventasChart')?.getContext('2d');
	if (ctx) {
		new Chart(ctx, {
			type: 'line',
			data: {
				labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul'],
				datasets: [
					{
						label: 'Ventas (S/)',
						data: [1200, 1900, 3000, 2500, 3200, 4100, 3800],
						borderColor: 'rgba(75,192,192,1)',
						backgroundColor: 'rgba(75,192,192,0.2)',
						fill: true,
						tension: 0.3,
					},
				],
			},
			options: {
				responsive: true,
				plugins: {
					legend: { position: 'top' },
				},
				scales: {
					y: {
						beginAtZero: true,
						ticks: {
							callback: function (value) {
								return 'S/ ' + value.toLocaleString();
							},
						},
					},
				},
			},
		});
	}

	// Configuración del tema toggle
	const themeToggleBtn = document.getElementById('theme-toggle-btn');
	const themeToggleIcon = document.getElementById('theme-toggle-icon');
	const body = document.body;

	// Función para alternar entre modo claro y oscuro
	function toggleTheme() {
		// Alternar la clase 'dark-mode' en el body (esto aplica el CSS)
		body.classList.toggle('dark-mode');

		// Actualizar el ícono
		if (body.classList.contains('dark-mode')) {
			// Modo oscuro activado: cambiar a icono de luna
			themeToggleIcon.classList.remove('ion-md-sunny');
			themeToggleIcon.classList.add('ion-md-moon');
			// Guardar la preferencia en el navegador
			localStorage.setItem('theme', 'dark');
		} else {
			// Modo claro activado: cambiar a icono de sol
			themeToggleIcon.classList.remove('ion-md-moon');
			themeToggleIcon.classList.add('ion-md-sunny');
			// Guardar la preferencia
			localStorage.setItem('theme', 'light');
		}
	}

	// Establecer el tema al cargar la página
	const savedTheme = localStorage.getItem('theme');
	if (savedTheme === 'dark') {
		toggleTheme();
	}

	// Añadir el Event Listener para el clic
	if (themeToggleBtn) {
		themeToggleBtn.addEventListener('click', function (event) {
			event.preventDefault();
			toggleTheme();
		});
	}
});
