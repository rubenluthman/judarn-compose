# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Canonical 4-file documentation topology (`README.md`, `ARCHITECTURE.md`, `ROADMAP.md`, `CHANGELOG.md`).
- Proprietary `LICENSE` (All Rights Reserved).
- Tracked `.githooks/commit-msg` enforcing Conventional Commits.
- Agent workflow guide in `.agents/AGENTS.md`.
- 4-quadrant high-contrast trait bridging in `JudarnColors` (`lightHighContrast`, `darkHighContrast`) and `highContrast` support in `JudarnTheme`.
- Dynamic high-contrast rule scaling to `JudarnSpacing.ruleBold` (2dp) in `JudarnDataMatrix` and `JudarnTextField`.
- Luminance-aware `Color.contrastingInk` for dynamic high-contrast foreground text resolution on `JudarnBadge` and `JudarnButton` accent variants.
- Zero-width space (`\u200B`) baseline height preservation in `JudarnDataMatrix` for empty and sparse cells.
- Tabular landmark collection semantics (`CollectionInfo`, `CollectionItemInfo`, `heading()`) in `JudarnDataMatrix`.
- Header coordinate hints and cell coordinates (`"Row X of Y, Column Z of W"`) in `JudarnDataMatrix`.
- Spoken resolution of empty cells (`"Empty"`) to prevent silent baseline glyph traversal in `JudarnDataMatrix`.
- Decorative hairline divider masking from screen readers (`Modifier.clearAndSetSemantics { }`) in `JudarnDataMatrix`.
- Trait-aware WCAG 2.1/2.2 AA and AAA accessibility contrast verifier (`JudarnContrastVerifier.kt`) with relative luminance linearization and core token matrix audit engine.
- Color convenience extensions for WCAG compliance evaluation and contrast ratio measurement (`Color.contrastRatio`, `Color.isWCAGCompliant`, `Color.relativeLuminance`).
- Automated WCAG 2.1 AAA accessibility contrast verification in `scripts/build-tokens.js` executed via `npm test`.
- Optional 3:2 aspect ratio `media` composable slot in `JudarnCard`.

### Changed
- Deprecated `useGlassBackground` in `JudarnCard` in favor of solid Swiss-modernist `surfaceElevated` substrates.
- Pruned forced secondary foreground color override on `JudarnCard` content blocks, allowing child views to maintain their own contrast hierarchy.

### Fixed
- Enforce fixed column `minWidth` sizing constraints in `JudarnDataMatrix` to prevent zero-width clipping under horizontal container compression.
- Calibrate `signalError` light high-contrast OKLCh lightness ($L=0.415$) to guarantee $\ge 7.0:1$ AAA contrast on `substrate`.

## [1.0.0] - 2026-09-01

### Added
- Harmonized tracking metrics, pluggable font slots, and data/mono styles.
- Tri-platform `JudarnLogoLockup` composable.
- Automated cross-platform token parity matrix verification suite.
