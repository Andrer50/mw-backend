document.querySelectorAll('a[href^="#"]:not([data-bs-toggle]):not([href="#"])').forEach((anchor) => {
	anchor.addEventListener('click', function (e) {
		e.preventDefault();
		const href = this.getAttribute('href');
		if (href && href !== '#') {
			const target = document.querySelector(href);
			if (target) {
				target.scrollIntoView({
					behavior: 'smooth',
					block: 'start',
				});
			}
		}
	});
});

// Animación de aparición en scroll
const observerOptions = {
	threshold: 0.1,
	rootMargin: '0px 0px -50px 0px',
};

const observer = new IntersectionObserver((entries) => {
	entries.forEach((entry) => {
		if (entry.isIntersecting) {
			entry.target.style.opacity = '1';
			entry.target.style.transform = 'translateY(0)';
		}
	});
}, observerOptions);

// Aplicar animación a elementos con clase fade-in
document.querySelectorAll('.fade-in').forEach((el) => {
	el.style.opacity = '0';
	el.style.transform = 'translateY(30px)';
	el.style.transition = 'all 0.6s ease-out';
	observer.observe(el);
});

// Contador animado para las estadísticas del hero
function animateCounters() {
	const counters = document.querySelectorAll('.hero-content h3');
	counters.forEach((counter) => {
		const target = parseInt(counter.textContent.replace(/[^0-9]/g, ''));
		const increment = target / 100;
		let current = 0;

		const timer = setInterval(() => {
			current += increment;
			if (current >= target) {
				counter.textContent = counter.textContent.replace(/[0-9]+/, target);
				clearInterval(timer);
			} else {
				counter.textContent = counter.textContent.replace(/[0-9]+/, Math.ceil(current));
			}
		}, 20);
	});
}

// Ejecutar contador cuando la sección hero sea visible
const heroObserver = new IntersectionObserver((entries) => {
	entries.forEach((entry) => {
		if (entry.isIntersecting) {
			animateCounters();
			heroObserver.unobserve(entry.target);
		}
	});
});

const heroSection = document.querySelector('.hero-section');
if (heroSection) {
	heroObserver.observe(heroSection);
}

// Efecto de navbar al hacer scroll
window.addEventListener('scroll', function () {
	const navbar = document.querySelector('.navbar');
	if (window.scrollY > 50) {
		navbar.style.background = 'linear-gradient(135deg, rgba(13, 110, 253, 0.95), rgba(0, 86, 179, 0.95))';
		navbar.style.backdropFilter = 'blur(10px)';
	} else {
		navbar.style.background = 'linear-gradient(135deg, #0d6efd, #0056b3)';
		navbar.style.backdropFilter = 'none';
	}
});

// Inicializar tooltips cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function () {
	// Inicializar tooltips de Bootstrap
	const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
	const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
		return new bootstrap.Tooltip(tooltipTriggerEl);
	});
});

// Funcionalidad para botón "Agregar al Carrito" (simulado)
document.addEventListener('DOMContentLoaded', function () {
	document.querySelectorAll('.btn-outline-success').forEach((button) => {
		button.addEventListener('click', function (e) {
			e.preventDefault();

			// Cambiar temporalmente el texto del botón
			const originalText = this.innerHTML;
			this.innerHTML = '<i class="bi bi-check me-2"></i>¡Agregado!';
			this.classList.remove('btn-outline-success');
			this.classList.add('btn-success');

			// Actualizar contador del carrito (simulado)
			const cartBadge = document.querySelector('.navbar .badge');
			if (cartBadge) {
				const currentCount = parseInt(cartBadge.textContent) || 0;
				cartBadge.textContent = currentCount + 1;
				cartBadge.classList.add('bg-danger');
				cartBadge.classList.remove('bg-warning');
			}

			// Restaurar después de 2 segundos
			setTimeout(() => {
				this.innerHTML = originalText;
				this.classList.remove('btn-success');
				this.classList.add('btn-outline-success');
			}, 2000);
		});
	});
});

// Efecto hover mejorado para cards de productos
document.querySelectorAll('.product-card').forEach((card) => {
	card.addEventListener('mouseenter', function () {
		this.style.transform = 'translateY(-15px)';
	});

	card.addEventListener('mouseleave', function () {
		this.style.transform = 'translateY(0)';
	});
});

// Validación simple del formulario de newsletter
document.addEventListener('DOMContentLoaded', function () {
	const newsletterForm = document.querySelector('footer form');
	if (newsletterForm) {
		newsletterForm.addEventListener('submit', function (e) {
			e.preventDefault();
			const emailInput = this.querySelector('input[type="email"]');
			const submitBtn = this.querySelector('button');

			if (emailInput && emailInput.value && emailInput.checkValidity()) {
				// Simular envío exitoso
				const originalBtnContent = submitBtn.innerHTML;
				submitBtn.innerHTML = '<i class="bi bi-check"></i>';
				submitBtn.classList.remove('btn-warning');
				submitBtn.classList.add('btn-success');
				submitBtn.disabled = true;
				emailInput.value = '';

				// Mostrar mensaje temporal
				const message = document.createElement('small');
				message.className = 'text-success d-block mt-2';
				message.textContent = '¡Suscripción exitosa!';
				this.appendChild(message);

				setTimeout(() => {
					submitBtn.innerHTML = originalBtnContent;
					submitBtn.classList.remove('btn-success');
					submitBtn.classList.add('btn-warning');
					submitBtn.disabled = false;
					if (message.parentNode) {
						message.remove();
					}
				}, 3000);
			} else if (emailInput) {
				emailInput.classList.add('is-invalid');
				setTimeout(() => {
					emailInput.classList.remove('is-invalid');
				}, 3000);
			}
		});
	}
});

// Mejorar la experiencia de carga
window.addEventListener('load', function () {
	document.body.classList.add('loaded');

	// Reinicializar tooltips si es necesario
	const tooltips = document.querySelectorAll('[title]:not([data-bs-original-title])');
	tooltips.forEach((tooltip) => {
		try {
			new bootstrap.Tooltip(tooltip);
		} catch (e) {
			console.log('Tooltip ya inicializado o error:', e);
		}
	});
});

// Efecto parallax sutil para el hero (con throttling para mejor rendimiento)
let parallaxTicking = false;

function updateParallax() {
	const heroSection = document.querySelector('.hero-section');
	if (heroSection) {
		const scrolled = window.pageYOffset;
		const parallax = scrolled * 0.3;

		if (scrolled < heroSection.offsetHeight) {
			heroSection.style.transform = `translateY(${parallax}px)`;
		}
	}
	parallaxTicking = false;
}

window.addEventListener('scroll', function () {
	if (!parallaxTicking) {
		requestAnimationFrame(updateParallax);
		parallaxTicking = true;
	}
});

// Mejorar accesibilidad con navegación por teclado
document.addEventListener('keydown', function (e) {
	if (e.key === 'Tab') {
		document.body.classList.add('keyboard-navigation');
	}
});

document.addEventListener('mousedown', function () {
	document.body.classList.remove('keyboard-navigation');
});
