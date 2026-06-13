.PHONY: create secrets deploy status deploy-ebpf validate destroy help

SCRIPTS_DIR := scripts

help:
	@grep -E '^[a-zA-Z_-]+:.*##' Makefile | awk 'BEGIN {FS = ":.*##"}; {printf "  %-10s %s\n", $$1, $$2}'

create:  ## Create DOKS cluster
	bash $(SCRIPTS_DIR)/create-cluster.sh

secrets: ## Create K8s secrets from .env
	bash $(SCRIPTS_DIR)/secrets.sh

deploy:  ## Deploy health-ai to cluster (run: make create, make secrets, make deploy)
	bash $(SCRIPTS_DIR)/deploy.sh

status:  ## Show pods and gateway external IP
	kubectl get pods -n health-ai
	kubectl get svc gateway -n health-ai

deploy-ebpf: ## Deploy eBPF EDR agent as DaemonSet
	bash $(SCRIPTS_DIR)/deploy-ebpf.sh

validate: ## Run eBPF validation tests against DOKS cluster (V2–V12)
	bash $(SCRIPTS_DIR)/validate-doks.sh

destroy: ## Delete the DOKS cluster
	bash $(SCRIPTS_DIR)/destroy.sh
