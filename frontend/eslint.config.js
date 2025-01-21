import globals from "globals";
import js from "@eslint/js";
import react from "eslint-plugin-react";

export default [
  {
    files: ["**/*.{js,mjs,cjs,jsx}"],
    languageOptions: {
      globals: globals.browser,
    },
    plugins: {
      react: react,
    },
    rules: {
      ...js.configs.recommended.rules,
      ...react.configs.recommended.rules,
    },
  },
];
