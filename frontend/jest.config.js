export default {
    testEnvironment: 'jsdom',
    collectCoverage: true,
    collectCoverageFrom: ['src/**/*.{js,jsx,ts,tsx}', '!src/index.js'],
    coverageReporters: ['text', 'lcov'],
};
