# Architecture & System Topology

## Overview
`judarn-compose` implements the Judarn Swiss-modernist design token foundation for Android Jetpack Compose and Compose Multiplatform.

## Subsystems & Token Generation
- **Canonical W3C Design Tokens (`tokens/judarn-tokens.json`)**: Source-of-truth token declarations spanning colors, spatial scales, radii, font slots, borders, reading measures, and high-contrast appearance overrides.
- **Generator & Verifier Script (`scripts/build-tokens.js`)**: Compiles canonical tokens, audits tri-platform token parity, and runs automated WCAG 2.1 AAA relative luminance contrast assertions across all appearance quadrants.
- **Compose Runtime Extensions & Accessibility**: Modernist theme scopes, Material 3 squircle shape scales, bounded touch ripples, decoupled 48dp touch envelopes, luminance-aware contrasting ink, and TalkBack tabular collection landmarks.
- **WCAG Contrast Verifier (`JudarnContrastVerifier.kt`)**: Trait-aware mathematical relative luminance and contrast ratio verifier ensuring strict AAA/AA compliance across surface pairings.

## Invariants & Design Contracts
1. **Platform Invariants**: Judarn binds to the host operating system. Judarn sets color, spatial rhythm, and typography. The host platform shapes geometry, depth, and touch mechanics. Never override host platform physics, hardware curves, or native interaction states.
2. **Shape & Curvature**: Surfaces delegate corner radii to Material Design 3 shape tokens via `JudarnRadius` (`card: 16.dp`, `control: 10.dp`, `badge: 9999.dp`).
3. **Surface Hierarchy**: Distinguish surfaces by background fill contrast (`surfaceElevated` on `substrate`). Never stroke card perimeters with hairline outlines. Hairlines (`rule = 1.dp`) belong only inside tabular matrices and data grids.
4. **Cross-Platform Parity**: Tokens must match Swift (`judarn`) and Web (`judarn-web`) equivalents verified via automated parity tests.
5. **Touch Feedback**: Retain bounded touch ripples (`androidx.compose.material3.ripple()`). Never suppress touch indication (`indication = null`).
6. **WCAG AAA Contrast Resilience**: Primary ink (`ink100`) guarantees $\ge 7.0:1$ AAA contrast across all surfaces and appearance modes; hairlines dynamically scale to 2dp bold rules under high contrast.


