pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo '체크아웃 완료'
                sh 'ls -la'
            }
        }

        stage('Build') {
            steps {
                echo "브랜치: ${env.GIT_BRANCH}"
                echo "커밋: ${env.GIT_COMMIT}"
            }
        }
    }

    post {
        success { echo '성공!' }
        failure { echo '실패!' }
    }
}