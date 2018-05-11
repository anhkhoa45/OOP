const path = require('path');
const webpack = require('webpack');
const CleanWebpackPlugin = require('clean-webpack-plugin');

// Phaser webpack config
var phaserModule = path.join(__dirname, '/node_modules/phaser-ce/');
var phaser = path.join(phaserModule, 'build/custom/phaser-split.js');
var pixi = path.join(phaserModule, 'build/custom/pixi.js');
var p2 = path.join(phaserModule, 'build/custom/p2.js');

module.exports = {
  entry: {
    main: [
      'babel-polyfill',
      path.resolve(__dirname, 'src/main.js')
    ],
    vendor: ['pixi', 'p2', 'phaser']
  },
  output: {
    path: path.resolve(__dirname, '../WebContent/assets'),
    filename: './js/[name].js'
  },
  plugins: [
    new CleanWebpackPlugin(['dist']),
    new webpack.DefinePlugin({
      'process.env': {
        // NODE_ENV: JSON.stringify('production')
        NODE_ENV: JSON.stringify('development')
      },
      'CANVAS_RENDERER': JSON.stringify(true),
      'WEBGL_RENDERER': JSON.stringify(true)
    })
  ],
  module: {
    rules: [
      { test: /\.css$/, use: ['vue-style-loader', 'css-loader'], },
      { test: /\.scss$/, use: ['vue-style-loader', 'css-loader', 'sass-loader'] },
      { test: /\.vue$/, loader: 'vue-loader',
        options: {
          loaders: {
            'scss': [ 'vue-style-loader', 'css-loader', 'sass-loader' ],
          }
        }
      },
      { test: /\.js$/, loader: 'babel-loader', include: path.join(__dirname, 'src'), exclude: /node_modules/ },
      { test: /\.(png|jpg|gif|svg)$/, loader: 'file-loader', options: { name: '../assets/img/[name].[ext]?[hash]' } },
      { test: /\.(woff|woff2|eot|ttf|otf|ttc)$/, loader: 'file-loader', options: { name: '../assets/fonts/[name].[ext]?[hash]' } },
      { test: [/\.vert$/, /\.frag$/], use: 'raw-loader' },
      { test: /pixi\.js/, use: ['expose-loader?PIXI'] },
      { test: /phaser-split\.js$/, use: ['expose-loader?Phaser'] },
      { test: /p2\.js/, use: ['expose-loader?p2'] }
    ],
    loaders: [
      {test: /\.json$/, loader: 'json-loader'},
      {test: /\.(vue|js)$/, loader: 'babel-loader'},
      {test: /\.vue$/, loader: 'vue-loader'},
      {test: /\.s[a|c]ss/, loader: 'style!css!sass'}
    ]
  },
  resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      'phaser': phaser,
      'pixi': pixi,
      'p2': p2
    },
    extensions: ['*', '.webpack.js', '.js', '.vue', '.json']
  },
  devServer: {
    historyApiFallback: true,
    noInfo: true,
    overlay: true
  },
  performance: {
    hints: false
  },
  devtool: '#eval-source-map'
};

if (process.env.NODE_ENV === 'production') {
  module.exports.devtool = '#source-map';
  // http://vue-loader.vuejs.org/en/workflow/production.html
  module.exports.plugins = (module.exports.plugins || []).concat([
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new webpack.optimize.UglifyJsPlugin({
      sourceMap: true,
      compress: {
        warnings: false
      }
    }),
    new webpack.LoaderOptionsPlugin({
      minimize: true
    })
  ])
}
