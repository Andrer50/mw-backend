#!/bin/bash

echo "=== COMPARACION DE CONEXIONES SUPABASE ==="
echo ""

# URLs a probar
DIRECT_HOST="db.awfyktwngztupvxfgcun.supabase.co"
POOLER_HOST="aws-1-sa-east-1.pooler.supabase.com"

echo "1. Testing CONEXION DIRECTA (la que falla):"
echo "Host: $DIRECT_HOST"
if timeout 5 bash -c "cat < /dev/null > /dev/tcp/$DIRECT_HOST/5432" 2>/dev/null; then
    echo "✅ Conexión directa FUNCIONA"
else
    echo "❌ Conexión directa FALLA (firewall/proxy)"
fi

echo ""
echo "2. Testing CONNECTION POOLER (la que funciona):"
echo "Host: $POOLER_HOST"
if timeout 5 bash -c "cat < /dev/null > /dev/tcp/$POOLER_HOST/5432" 2>/dev/null; then
    echo "✅ Connection pooler FUNCIONA"
else
    echo "❌ Connection pooler FALLA"
fi

echo ""
echo "CONCLUSION:"
echo "- Si ambas fallan: Problema de firewall/red general"
echo "- Si solo la directa falla: Usar connection pooler"
echo "- Si ambas funcionan: Problema de configuración Spring Boot"