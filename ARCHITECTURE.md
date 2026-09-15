# Architecture & System Topology

## Overview
`judarn-compose` implements the Judarn Swiss-modernist design token foundation for Android Jetpack Compose and Compose Multiplatform.

## Subsystems & Token Generation
- **Canonical W3C Design Tokens (`tokens/judarn-tokens.json`)**: Source-of-truth token declarations spanning colors, spatial scales, font slots, borders, reading measures, and high-contrast appearance overrides.
- **Generator & Verifier Script (`scripts/build-tokens.js`)**: Compiles canonical tokens, audits tri-platform token parity, and runs automated WCAG 2.1 AAA relative luminance contrast assertions across all appearance quadrants.
- **Compose Runtime Extensions & Accessibility**: Modernist theme scopes, ripple-free state transitions, decoupled 48dp touch envelopes, luminance-aware contrasting ink, and TalkBack tabular collection landmarks.
- **WCAG Contrast Verifier (`JudarnContrastVerifier.kt`)**: Trait-aware mathematical relative luminance and contrast ratio verifier ensuring strict AAA/AA compliance across surface pairings.

## Invariants & Design Contracts
1. **0-Radius Rectangular Geometry**: Foreground interactive surfaces are flat with sharp corners.
2. **Cross-Platform Parity**: Tokens must match Swift (`judarn`) and Web (`judarn-web`) equivalents verified via automated parity tests.
3. **Decoupled Touch Targets**: Visual geometry remains compact while touch envelopes meet 48dp Material Design 3 minimums.
4. **WCAG AAA Contrast Resilience**: Primary ink (`ink100`) guarantees $\ge 7.0:1$ AAA contrast across all surfaces and appearance modes; hairlines dynamically scale to 2dp bold rules under high contrast.

