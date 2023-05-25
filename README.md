Запуск приложения в docker:
1) Запустить окружение (elasticsearch, postgresql)
2) В жизненном цикле maven сначала очистить папку target (clean), а затем пересобрать проект (install)
3) Далее запускаем остальные сервисы в docker-compose.yml