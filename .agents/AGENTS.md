# Agent Operating Invariants & Workflow Guide

## Commands & Verification Suites
- **Test / Verify Tokens**: `npm test`
- **Build Tokens**: `npm run build:tokens`
- **Hook Test**: `.githooks/commit-msg <msg-file>`

## Project Invariants
1. Follow Conventional Commits (`feat:`, `fix:`, `refactor:`, `test:`, etc.).
2. Maintain max 4 root documentation files (`README.md`, `ARCHITECTURE.md`, `ROADMAP.md`, `CHANGELOG.md`) plus `LICENSE`.
3. Co-evolve documentation and code atomically in the same commit.
4. **Platform Invariant**: Judarn sets color, spatial rhythm, and typography. The host platform shapes geometry, depth, and touch mechanics. Never override host platform physics, hardware curves, or native interaction states.
5. **Surface Hierarchy Invariant**: Group surfaces with substrate background contrast (`surfaceElevated` on `substrate`). Never stroke card perimeters with hairline outlines. Retain bounded touch ripples (`androidx.compose.material3.ripple()`).

