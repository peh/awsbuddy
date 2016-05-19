var path = require('path');
var webpack = require('webpack');
var requireErrorHandlerPlugin = require('require-error-handler-webpack-plugin');

module.exports = function (entry, outName, listenPort) {
  return {
    entry: [
      'bootstrap-loader',
      entry
    ],
    output: {
      filename: outName,
      path: path.join(__dirname),
      publicPath: 'http://localhost:' + listenPort + '/' // as we are serving the app over port 8080 we have to hardcode the complete url here
    },
    module: {
      loaders: [
        {
          test: /\.jsx|\.js?$/,
          exclude: /node_modules/,
          loaders: ['babel?presets[]=es2015&presets[]=react'],
        },
        {
          test: /\.scss$/,
          loaders: ['style', 'css-loader?sourceMap', 'sass?sourceMap']
        },
        {
          test: /\.eot(\?\S*)?$/,
          loader: 'url-loader?limit=100000&mimetype=application/vnd.ms-fontobject'
        },
        {
          test: /\.woff2(\?\S*)?$/,
          loader: 'url-loader?limit=100000&mimetype=application/font-woff2'
        },
        {
          test: /\.woff(\?\S*)?$/,
          loader: 'url-loader?limit=100000&mimetype=application/font-woff'
        },
        {
          test: /\.ttf(\?\S*)?$/,
          loader: 'url-loader?limit=100000&mimetype=application/font-ttf'
        },
        {
          test: /\.(png|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
          loader: 'url?limit=8192'
        }
      ]
    },
    plugins: [
      new webpack.HotModuleReplacementPlugin(),
      new webpack.ProvidePlugin({
        jQuery: 'jquery',
        $: 'jquery'
      }),
      new requireErrorHandlerPlugin.RequireEnsureErrorHandlerPlugin(),
      new requireErrorHandlerPlugin.AMDRequireErrorHandlerPlugin()
    ]
  };
};
