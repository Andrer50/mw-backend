#!/bin/bash

echo "=== VERIFICACIÓN DE CONFIGURACIÓN DE SUPABASE ==="

# Verificar si existe el archivo .env
if [ -f ".env" ]; then
    echo "✅ Archivo .env encontrado"
    echo "Contenido:"
    cat .env
else
    echo "❌ Archivo .env NO encontrado"
    echo "Copia .env.example a .env:"
    echo "cp .env.example .env"
fi

echo ""
echo "=== PROBANDO CONEXIÓN A SUPABASE ==="

# Extraer variables del .env si existe
if [ -f ".env" ]; then
    source .env
    echo "Probando conexión a: $SUPABASE_HOST"
    
    # Probar conexión con timeout
    timeout 10 bash -c "cat < /dev/null > /dev/tcp/$SUPABASE_HOST/5432"
    if [ $? -eq 0 ]; then
        echo "✅ Puerto 5432 accesible en $SUPABASE_HOST"
    else
        echo "❌ No se puede conectar al puerto 5432 en $SUPABASE_HOST"
        echo "Verifica tu conexión a internet y las configuraciones de firewall"
    fi
fi

echo ""
echo "=== INICIANDO APLICACIÓN ==="
./mvnw clean spring-boot:run