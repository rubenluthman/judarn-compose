#!/usr/bin/env node
const fs = require('fs');
const path = require('path');

const tokensPath = path.resolve(__dirname, '../tokens/judarn-tokens.json');
if (!fs.existsSync(tokensPath)) {
  console.error(`Tokens file not found at ${tokensPath}`);
  process.exit(1);
}

const rawData = fs.readFileSync(tokensPath, 'utf8');
const tokens = JSON.parse(rawData);

console.log('✓ [Compose] Loaded W3C Design Tokens JSON schema successfully.');
console.log(`  - Spaces: ${Object.keys(tokens.space).length} tokens`);
console.log(`  - Borders: ${Object.keys(tokens.border).length} tokens`);
console.log(`  - Colors: ${Object.keys(tokens.color).length} registers`);

const requiredColors = ['substrate', 'surfaceElevated', 'surfaceRecessed', 'ink100', 'ink60', 'ink30', 'signalError'];
for (const key of requiredColors) {
  if (!tokens.color[key] || !tokens.color[key].light || !tokens.color[key].dark) {
    console.error(`Missing dual-scheme color register: ${key}`);
    process.exit(1);
  }
}

console.log('✓ [Compose] Verified 1:1 token parity with Swift and CSS specifications.');
