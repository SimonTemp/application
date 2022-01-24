
#!/bin/bash
docker build -t app:latest .
docker run --name app -d -p 3000:3000 app:latest
