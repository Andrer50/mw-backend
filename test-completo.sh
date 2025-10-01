#!/bin/bash

echo "=== TESTING COMPLETO DE CONECTIVIDAD SUPABASE ==="
echo "Fecha: $(date)"
echo ""

# Cargar variables de entorno
if [ -f ".env" ]; then
    source .env
    echo "✅ Archivo .env cargado"
    echo "Host: $SUPABASE_HOST"
    echo "DB: $SUPABASE_DB"
    echo "User: $SUPABASE_USER"
    echo ""
else
    echo "❌ Archivo .env no encontrado"
    exit 1
fi

echo "1. === TESTING DE RED ==="
echo "Probando ping al host..."
if ping -c 4 $SUPABASE_HOST > /dev/null 2>&1; then
    echo "✅ Ping exitoso a $SUPABASE_HOST"
else
    echo "❌ Ping falló - Problema de conectividad de red"
fi

echo ""
echo "2. === TESTING DE PUERTO ==="
echo "Probando conexión al puerto 5432..."
if timeout 10 bash -c "cat < /dev/null > /dev/tcp/$SUPABASE_HOST/5432" 2>/dev/null; then
    echo "✅ Puerto 5432 accesible"
else
    echo "❌ Puerto 5432 no accesible - Firewall o proxy bloqueando"
fi

echo ""
echo "3. === TESTING CON PSQL (si está disponible) ==="
if command -v psql &> /dev/null; then
    echo "Probando conexión directa con psql..."
    PGPASSWORD=$SUPABASE_PASSWORD timeout 10 psql -h $SUPABASE_HOST -p 5432 -U $SUPABASE_USER -d $SUPABASE_DB -c "SELECT 1;" 2>/dev/null
    if [ $? -eq 0 ]; then
        echo "✅ Conexión exitosa con psql"
    else
        echo "❌ Conexión falló con psql"
    fi
else
    echo "⚠️  psql no está instalado, omitiendo esta prueba"
fi

echo ""
echo "4. === TESTING DE DNS ==="
echo "Resolviendo DNS para $SUPABASE_HOST..."
if nslookup $SUPABASE_HOST > /dev/null 2>&1; then
    echo "✅ Resolución DNS exitosa"
    echo "IP del servidor:"
    nslookup $SUPABASE_HOST | grep -A 1 "Name:" | tail -1
else
    echo "❌ Error en resolución DNS"
fi

echo ""
echo "5. === TESTING DE APLICACIÓN ==="
echo "Ejecutando aplicación con perfil de testing..."
echo "NOTA: Esto puede tomar hasta 30 segundos..."

# Ejecutar con perfil de testing
timeout 30 ./mvnw spring-boot:run -Dspring-boot.run.profiles=testing 2>&1 | head -50

echo ""
echo "=== FIN DEL TESTING ==="
echo "Si todos los tests anteriores fallan, el problema es de red/firewall."
echo "Si solo falla el test de aplicación, el problema es de configuración."