// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: {
    browser: true,
  },
  extends: [
    // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
    // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
    'plugin:vue/essential',
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    'standard'
  ],
  // required to lint *.vue files
  plugins: [
    'vue'
  ],
  // add your custom rules here
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'space-before-function-paren': 0,
    'indent': 0,
    'no-multi-spaces':'off',
    "no-multiple-empty-lines": [0, { "max": 100 }],
    'arrow-parens': 0,
    'generator-star-spacing': 0,
    "no-unused-vars": [2, {
      "vars": "local",
      "args": "none"
    }],
    "semi": [0],
    "no-mixed-spaces-and-tabs": [0],
    "space-before-blocks":0,
    "key-spacing":0,
    "arrow-spacing":0,
    "no-trailing-spaces":0,
    "eol-last":0,
    "prefer-const":0,
    "quotes":0,
    'eol-last': 0,//这句话表示在文件末尾可以不加回车，贴个链接  https://eslint.org/docs/rules/eol-last
  }
}
