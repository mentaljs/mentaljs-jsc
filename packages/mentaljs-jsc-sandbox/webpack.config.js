const path = require('path');

module.exports = {
    mode: "production",
    entry: './packages/mentaljs-jsc-sandbox/index.ts',
    output: {
        filename: 'index.js',
        path: path.resolve(__dirname, '../../build')
    },
    module: {
        rules: [{
            test: /\.(t|j)s(x?)$/,
            exclude: /node_modules/,
            use: [{
                loader: 'babel-loader'
            }]
        }]
    },
    resolve: {
        extensions: ['.ts', '.tsx', '.js', '.jsx']
    },
};