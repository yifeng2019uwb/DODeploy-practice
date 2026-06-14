#!/bin/bash
set -e

REGISTRY_NAME="${REGISTRY_NAME:?REGISTRY_NAME env var is required}"
APP_ID="${APP_ID:-}"
IMAGE="registry.digitalocean.com/$REGISTRY_NAME/event-analysis"
TAG=$(git rev-parse --short HEAD)

echo "==> Building and pushing Docker image..."
docker system prune -af --volumes
doctl registry login
# docker buildx build --platform linux/amd64 --push --provenance=false -t "$IMAGE:$TAG" -t "$IMAGE:latest" .
docker buildx create --use --name amd64_builder 2>/dev/null || docker buildx use amd64_builder
docker buildx inspect --bootstrap
docker buildx build \
  --platform linux/amd64 \
  --push \
  --provenance=false \
  -t "$IMAGE:$TAG" \
  -t "$IMAGE:latest" \
  .

echo "==> Deploying to DO App Platform..."
if [ -z "$APP_ID" ]; then
  doctl apps create --spec .do/deploy.template.yml
else
  doctl apps update "$APP_ID" --spec .do/deploy.template.yml
fi

echo "==> Done. Image: $IMAGE:$TAG"