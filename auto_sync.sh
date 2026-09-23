#!/bin/bash
echo "=== Auto Git Sync Started ==="
while true; do
  git add .
  git commit -m "auto-sync" >/dev/null 2>&1
  git pull --rebase >/dev/null 2>&1
  git push >/dev/null 2>&1
  echo "[$(date +'%H:%M:%S')] Auto synced!"
  sleep 30
done
