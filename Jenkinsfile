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
      sh './gradlew clean build -x test'
      // -x test : 테스트 스킵 (일단 빌드만)
    }
  }

  stage('Archive') {
    steps {
      archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
    }
  }
}

post {
  success { echo '빌드 성공!' }
  failure { echo '빌드 실패!' }
}
}