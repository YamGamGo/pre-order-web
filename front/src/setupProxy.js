// src/setupProxy.js
const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    const target = 'http://localhost:8080';
    const common = {
        target,
        changeOrigin: true,
        cookieDomainRewrite: 'localhost',    };
    app.use('/api', createProxyMiddleware(common));
    app.use('/oauth', createProxyMiddleware(common));
    app.use('/login', createProxyMiddleware(common));
};

