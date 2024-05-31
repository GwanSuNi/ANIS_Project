const path = require('path'); // 'path' 모듈은 파일 및 디렉토리 경로를 작업하는 데 사용

module.exports = {
    webpack: { // 웹팩 설정을 재정의
        alias: { // 경로 별칭 설정
            '@assets': path.resolve(__dirname, 'src/assets'),
            '@components': path.resolve(__dirname, 'src/components'),
            '@hooks': path.resolve(__dirname, 'src/hooks'),
            '@layout': path.resolve(__dirname, 'src/layout'),
            '@pages': path.resolve(__dirname, 'src/pages'),
            '@redux': path.resolve(__dirname, 'src/redux'),
            '@routes': path.resolve(__dirname, 'src/routes'),
            '@themes': path.resolve(__dirname, 'src/themes'),
            '@utils': path.resolve(__dirname, 'src/utils')
        }
    }
};