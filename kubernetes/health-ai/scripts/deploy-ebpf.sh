#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="$SCRIPT_DIR/../.env"

[[ -f "$ENV_FILE" ]] || { echo "ERROR: .env not found — cp .env.example .env and fill in values"; exit 1; }
set -a; source "$ENV_FILE"; set +a

[[ -z "$REGION"       ]] && { echo "ERROR: REGION not set in .env"; exit 1; }
[[ -z "$CLUSTER_NAME" ]] && { echo "ERROR: CLUSTER_NAME not set in .env"; exit 1; }

REPO="https://github.com/yifeng2019uwb/ebpf-edr-demo.git"
TMP_DIR="/tmp/ebpf-edr-src"

echo "Cloning ebpf-edr manifests..."
rm -rf "$TMP_DIR"
git clone --depth=1 "$REPO" "$TMP_DIR"

echo "Applying eBPF DaemonSet..."
REGION="$REGION" CLUSTER_NAME="$CLUSTER_NAME" \
  envsubst < "$TMP_DIR/k8s/ebpf-edr-ds.yaml" | kubectl apply -f -

rm -rf "$TMP_DIR"

echo "Waiting for eBPF agent to be ready..."
kubectl rollout status daemonset/ebpf-edr -n kube-system --timeout=120s

echo "eBPF agent deployed."
kubectl get pods -n kube-system -l app=ebpf-edr
