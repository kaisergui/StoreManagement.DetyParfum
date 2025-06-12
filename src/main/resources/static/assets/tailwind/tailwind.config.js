/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
	"../../*.html",             // pega index.html, login.html etc
	  "../../views/*.html"        // pega views/*.html
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}