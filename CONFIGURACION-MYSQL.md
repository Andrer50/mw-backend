# =================================================
# CONFIGURACIÓN DE EJEMPLO PARA application.properties
# =================================================
# 
# INSTRUCCIONES:
# 1. Abrir: src/main/resources/application.properties
# 2. Buscar la sección "# Configuración de base de datos - MySQL Local"
# 3. Cambiar los valores según tu configuración local
#
# =================================================

# CONFIGURACIONES QUE DEBES CAMBIAR:

# 1. PASSWORD DE MYSQL (la más importante)
spring.datasource.password=TU_PASSWORD_AQUI

# 2. USUARIO (si no usas 'root')
spring.datasource.username=tu_usuario_mysql

# 3. PUERTO (si no usas el puerto por defecto 3306)
spring.datasource.url=jdbc:mysql://localhost:TU_PUERTO/mw_backend?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# 4. NOMBRE DE BD (si quieres usar otro nombre)
spring.datasource.url=jdbc:mysql://localhost:3306/TU_NOMBRE_BD?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# =================================================
# EJEMPLOS COMUNES:
# =================================================

# Ejemplo 1: Usuario root con password
# spring.datasource.username=root
# spring.datasource.password=mi_password_123

# Ejemplo 2: Usuario específico
# spring.datasource.username=mw_user
# spring.datasource.password=mw_password_seguro

# Ejemplo 3: Puerto diferente (ej: 3307)
# spring.datasource.url=jdbc:mysql://localhost:3307/mw_backend?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# Ejemplo 4: Base de datos con nombre diferente
# spring.datasource.url=jdbc:mysql://localhost:3306/mi_proyecto_bd?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# =================================================
# NOTAS IMPORTANTES:
# =================================================
# 
# - NO COMMITEAR tus credenciales reales al repositorio
# - Cada desarrollador debe configurar sus propias credenciales
# - La base de datos se crea automáticamente si no existe (DDL auto=update)
# - Las tablas se crean automáticamente al ejecutar la aplicación
#
# =================================================