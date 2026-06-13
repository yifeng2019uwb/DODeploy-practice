#!/bin/bash
set -e

CLUSTER_NAME="health-ai-doks"

echo "Destroying cluster: $CLUSTER_NAME..."
doctl kubernetes cluster delete "$CLUSTER_NAME" --force

echo "Cluster deleted."
