#!/bin/bash
set -e

CLUSTER_NAME="health-ai-doks"
REGION="sfo3"
VERSION="1.36.0-do.1"
NODE_SIZE="s-2vcpu-4gb"
NODE_COUNT="1"

echo "Creating DOKS cluster: $CLUSTER_NAME in $REGION..."

doctl kubernetes cluster create "$CLUSTER_NAME" \
  --region "$REGION" \
  --version "$VERSION" \
  --node-pool "name=default;size=$NODE_SIZE;count=$NODE_COUNT" \
  --ha=false \
  --wait

echo "Saving kubeconfig..."
doctl kubernetes cluster kubeconfig save "$CLUSTER_NAME"

echo "Cluster ready."
kubectl get nodes
