#!/bin/bash

echo "=== TESTING CONFIGURACIONES SUPABASE ==="
echo ""

# Verificar archivo .env
if [ ! -f ".env" ]; then
    echo "❌ Archivo .env no encontrado"
    exit 1
fi

echo "✅ Archivo .env encontrado"
source .env
echo "Host: $SUPABASE_HOST"
echo ""

echo "1. === TESTING CON CONFIGURACIÓN ACTUAL ==="
echo "Probando con configuración limpia (pool-size=15, timeout=20s)..."
timeout 30 ./mvnw clean spring-boot:run 2>&1 | head -20
echo ""

echo "2. === TESTING CON CONFIGURACIÓN MÍNIMA ==="
echo "Probando con configuración mínima..."
timeout 30 ./mvnw clean spring-boot:run -Dspring-boot.run.profiles=minimal 2>&1 | head -20
echo ""

echo "3. === TESTING CON CONFIGURACIÓN POR DEFECTO SUPABASE ==="
echo "Probando sin configuraciones de pool (Supabase maneja todo)..."
timeout 30 ./mvnw clean spring-boot:run -Dspring-boot.run.profiles=default 2>&1 | head -20
echo ""

echo "=== RECOMENDACIONES ==="
echo "- Si la configuración 'default' funciona: El problema eran las configuraciones de pool"
echo "- Si ninguna funciona: El problema es de conectividad de red"
echo "- Si todas funcionan: El problema era el cache de Maven"