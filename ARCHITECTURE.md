# Architecture & System Topology

## Overview
`judarn-compose` implements the Judarn Swiss-modernist design token foundation for Android Jetpack Compose and Compose Multiplatform.

## Subsystems & Token Generation
- **Canonical W3C Design Tokens (`tokens/design-tokens.json`)**: Source-of-truth token declarations spanning colors, spatial scales, font slots, borders, and reading measures.
- **Generator Script (`scripts/build-tokens.js`)**: Compiles canonical tokens into Kotlin Compose objects (`src/main/kotlin/design/judarn/tokens/JudarnTokens.kt`).
- **Compose Runtime Extensions**: Modernist theme scopes, instant color inversion (ripple suppression), and touch target expansion envelopes.

## Invariants & Design Contracts
1. **0-Radius Rectangular Geometry**: Foreground interactive surfaces are flat with sharp corners.
2. **Cross-Platform Parity**: Tokens must match Swift (`judarn`) and Web (`judarn-web`) equivalents verified via parity tests.
3. **Decoupled Touch Targets**: Visual geometry remains compact while touch envelopes meet 48dp minimums.
