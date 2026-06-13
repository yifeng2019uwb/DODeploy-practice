#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="$SCRIPT_DIR/../.env"

[[ -f "$ENV_FILE" ]] || { echo "ERROR: .env not found — cp .env.example .env and fill in values"; exit 1; }
set -a; source "$ENV_FILE"; set +a

[[ -z "$SPRING_DATASOURCE_URL"      ]] && { echo "ERROR: SPRING_DATASOURCE_URL not set"; exit 1; }
[[ -z "$SPRING_DATASOURCE_USERNAME" ]] && { echo "ERROR: SPRING_DATASOURCE_USERNAME not set"; exit 1; }
[[ -z "$SPRING_DATASOURCE_PASSWORD" ]] && { echo "ERROR: SPRING_DATASOURCE_PASSWORD not set"; exit 1; }
[[ -z "$JWT_PRIVATE_KEY"            ]] && { echo "ERROR: JWT_PRIVATE_KEY not set"; exit 1; }
[[ -z "$JWT_PUBLIC_KEY"             ]] && { echo "ERROR: JWT_PUBLIC_KEY not set"; exit 1; }
[[ -z "$GEMINI_API_KEY"             ]] && { echo "ERROR: GEMINI_API_KEY not set"; exit 1; }

echo "Ensuring namespace exists..."
kubectl create namespace health-ai --dry-run=client -o yaml | kubectl apply -f -

echo "Creating health-ai-secrets in namespace health-ai..."

kubectl create secret generic health-ai-secrets \
  --namespace health-ai \
  --from-literal=db-url="$SPRING_DATASOURCE_URL" \
  --from-literal=db-username="$SPRING_DATASOURCE_USERNAME" \
  --from-literal=db-password="$SPRING_DATASOURCE_PASSWORD" \
  --from-literal=jwt-private-key="$JWT_PRIVATE_KEY" \
  --from-literal=jwt-public-key="$JWT_PUBLIC_KEY" \
  --from-literal=gemini-api-key="$GEMINI_API_KEY" \
  --dry-run=client -o yaml | kubectl apply -f -

echo "Secrets created."
