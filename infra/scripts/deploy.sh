#!/bin/bash

# Build and Deploy Script for DevOps Microservices

echo "Building DevOps Microservices..."

# Build all services
echo "Building all services with Maven..."
mvn clean install

# Build Docker images
echo "Building Docker images..."
docker-compose build

echo "Starting services..."
docker-compose up -d

echo "Services are starting up..."
echo "Gateway: http://localhost:8080"
echo "User Service: http://localhost:8081"
echo "Item Service: http://localhost:8082"
echo "Frontend: http://localhost:3000"

echo "Use 'docker-compose logs -f' to see logs"
echo "Use 'docker-compose down' to stop services"