# Agent Operating Invariants & Workflow Guide

## Commands & Verification Suites
- **Test / Verify Tokens**: `npm test`
- **Build Tokens**: `npm run build:tokens`
- **Hook Test**: `.githooks/commit-msg <msg-file>`

## Project Invariants
1. Follow Conventional Commits (`feat:`, `fix:`, `refactor:`, `test:`, etc.).
2. Maintain max 4 root documentation files (`README.md`, `ARCHITECTURE.md`, `ROADMAP.md`, `CHANGELOG.md`) plus `LICENSE`.
3. Co-evolve documentation and code atomically in the same commit.
