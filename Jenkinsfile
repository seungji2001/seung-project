  pipeline {
    agent any

    environment {
      APP_NAME = 'seung-app'
      IMAGE_NAME = "seung-app:${env.BUILD_NUMBER}"
    }

    stages {
      stage('Build') {
        steps {
          sh './gradlew clean build -x test'
        }
      }

      stage('Docker 이미지 빌드') {
        steps {
          sh "docker build -t ${env.IMAGE_NAME} ."
          sh "docker images | grep seung-app"
        }
      }

      stage('배포') {
        steps {
          sh '''
            # 기존 컨테이너 중지
            docker stop seung-app || true
            docker rm seung-app || true

            # 새 이미지로 실행
            docker run -d \
              --name seung-app \
              -p 9090:8080 \
              seung-app:${BUILD_NUMBER}

            echo "배포 완료 → localhost:9090"
          '''
        }
      }

      stage('배포 확인') {
        steps {
          sh 'sleep 5'  // 앱 뜰 때까지 대기
          sh 'docker ps | grep seung-app'
        }
      }
    }

    post {
      success { echo "배포 성공! localhost:9090 접속해보세요" }
      failure {
        sh 'docker logs seung-app || true'
        echo '배포 실패'
      }
    }
  }