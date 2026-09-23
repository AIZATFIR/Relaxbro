@echo off
echo === Auto Git Sync Started (Windows) ===
:loop
git add .
git commit -m "auto-sync" >nul 2>&1
git pull --rebase >nul 2>&1
git push >nul 2>&1
echo [%time%] Auto synced!
timeout /t 30 /nobreak >nul
goto loop
