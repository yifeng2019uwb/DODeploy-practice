# Known Issues

## Docker Image Platform Mismatch on Apple Silicon (M1/M2/M3)

**Symptom:** DO App Platform rejects the image with:
> Container Image Platform Mismatch: deployment failed due to container image mismatch with platform architecture (linux/amd64)

**Root Cause:** Building Docker images on Apple Silicon produces `linux/arm64` images by default. DO App Platform requires `linux/amd64`.

**Failed Workarounds:**
- `docker build --platform linux/amd64` — still produces arm64 metadata
- `docker buildx build --platform linux/amd64 --push` — pushes image without architecture metadata, DO still rejects it

**Solution:** Use GitHub-based deployment. DO builds the image on their own Linux (amd64) infrastructure — no architecture issues.

**Setup:**
1. Connect GitHub repo in DO console → Settings → Integrations → GitHub
2. Use `.do/app.yaml` with `github:` source block (not `image:` DOCR block)
3. DO builds and deploys automatically on every push to `main`