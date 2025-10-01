@echo off
echo === DIAGNOSTICO DE CONECTIVIDAD SUPABASE ===
echo.

echo 1. Probando conectividad basica...
ping -n 4 db.awfyktwngztupvxfgcun.supabase.co
echo.

echo 2. Probando puerto 5432...
telnet db.awfyktwngztupvxfgcun.supabase.co 5432
echo.

echo 3. Usando PowerShell para probar puerto...
powershell -Command "Test-NetConnection -ComputerName 'db.awfyktwngztupvxfgcun.supabase.co' -Port 5432"
echo.

echo 4. Probando resolucion DNS...
nslookup db.awfyktwngztupvxfgcun.supabase.co
echo.

echo === FIN DEL DIAGNOSTICO ===
pause