import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        "primary": "#32435F",
        "secondary": "#A67F78",
        "background-light": "#E1DCD9",
        "text-primary": "#32435F",
        "text-secondary": "#8F8681",
        "border-color": "#D4CBC8",
        "income": "#2E7D32",
        "expense": "#C62828",
      },
      fontFamily: {
        display: ["Manrope", "sans-serif"],
      },
      borderRadius: {
        DEFAULT: "0.5rem",
        lg: "0.75rem",
        xl: "1rem",
        full: "9999px",
      },
    },
  },
  plugins: [],
};
export default config;
