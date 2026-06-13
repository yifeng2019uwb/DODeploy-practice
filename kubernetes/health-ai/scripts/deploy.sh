#!/bin/bash
set -e

REPO="https://github.com/yifeng2019uwb/healthcare-ai-microservices.git"
TMP_DIR="/tmp/health-ai-src"

echo "Cloning health-ai manifests..."
rm -rf "$TMP_DIR"
git clone --depth=1 "$REPO" "$TMP_DIR"

echo "Applying manifests..."
kubectl apply -f "$TMP_DIR/kubernetes/namespace.yaml"
kubectl apply -f "$TMP_DIR/kubernetes/configmap.yaml"
kubectl apply -f "$TMP_DIR/kubernetes/service.yaml"
kubectl apply -f "$TMP_DIR/kubernetes/deployment.yaml"

rm -rf "$TMP_DIR"

echo "Waiting for pods..."
kubectl rollout status deployment/auth-service     -n health-ai --timeout=120s
kubectl rollout status deployment/provider-service -n health-ai --timeout=120s
kubectl rollout status deployment/ai-service       -n health-ai --timeout=120s
kubectl rollout status deployment/gateway          -n health-ai --timeout=120s

echo "Deploy complete."
kubectl get pods -n health-ai
