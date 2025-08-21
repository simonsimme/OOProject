# DevOps Microservices - Chat Application

This repository contains a microservices-based chat application with the following architecture:

## Architecture Overview

```
├── gateway/              # API Gateway service
├── services/            # Microservices
│   ├── user-service/    # User management microservice
│   └── item-service/    # Chat channel and message management
├── frontend/            # Frontend UI application
└── infra/              # Infrastructure configurations
    ├── docker/         # Docker configurations
    ├── kubernetes/     # Kubernetes manifests
    └── scripts/        # Deployment scripts
```

## Services

### Gateway (Port 8080)
- API Gateway for routing requests
- Load balancing and service discovery
- Request/response transformation

### User Service (Port 8081)
- User authentication and management
- Session management
- User profile operations

### Item Service (Port 8082)
- Chat channel management
- Message handling and persistence
- Channel membership management

### Frontend
- User interface components
- Real-time chat interface
- Connection management

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker (optional)

### Building the Application

```bash
# Build all services
mvn clean install

# Build individual services
cd gateway && mvn clean install
cd services/user-service && mvn clean install
cd services/item-service && mvn clean install
cd frontend && mvn clean install
```

### Running the Services

```bash
# Start Gateway
java -jar gateway/target/gateway.jar

# Start User Service
java -jar services/user-service/target/user-service.jar

# Start Item Service
java -jar services/item-service/target/item-service.jar

# Start Frontend
java -jar frontend/target/frontend.jar
```

### Using Docker

```bash
# Build and run all services
docker-compose up --build
```

## Development

Each service is independently deployable and can be developed separately. See individual service README files for specific development instructions.

## Infrastructure

The `infra/` directory contains:
- Docker configurations for containerization
- Kubernetes manifests for orchestration
- Deployment scripts for automation