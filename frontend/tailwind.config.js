const generateColorClass = (variable) => {

  return `var(--${variable})`
}

const textColor = {
  font: generateColorClass('text-font'),
  main: generateColorClass('text-main'),
  back: generateColorClass('text-back'),
}

const backgroundColor = {
  main: generateColorClass('bg-main'),
  back: generateColorClass('bg-back'),
  panel: generateColorClass('bg-panel'),
}

const borderColor = {
  label: generateColorClass('border-label'),
}

module.exports = {
  purge: ['./src/common/css/main.css', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      textColor,
      backgroundColor,
      borderColor,
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
}
