// 젠킨스 파이프라인 플러그인을 호출하기 위한 블록
pipeline {
    // 파이프라인을 실행하고 싶은 위치 정의
    agent none

    // gitlab의 소스를 jenkins 디렉토리로 내려받을 시
    // skipDefaultCheckout(true)일 경우 내려받는 프로세스 skip
    // skipDefaultCheckout(false)일 경우 gitlab 소스 체크
    options { skipDefaultCheckout(true) }

    // stage의 모음
    stages {
        // 실제 작업이 수행되는 블록
        // 해당 stage 명으로 jenkins 화면에 표시됨
        stage('Build and Test') {
            // docker image에 명시된 image를 활용하여 steps 수행
            agent {
                docker {
                    image 'gradle:8.5.0-jdk17-alpine'
                    // 호스트의 Maven 로컬 저장소 (/root/.m2)를 Docker 컨테이너 내부의 같은 경로로 마운트
                    // 빌드 중에 의존성을 캐시할 수 있어 빌드 속도를 향상시킬 수 있음
                    args '-v /root/.m2:/root/.m2'
                }
            }
            options { skipDefaultCheckout(false) }
            // gradle로 테스트 없이 빌드
            steps {
                // permission denied 해결
                sh 'ls'
                // sh 'chmod +x ./backend/gradlew'
                // does not contain a gradle build 해결
                // sh 'gradle init'
                // sh './backend/gradlew build'
            }
        }
//         stage('Build Frontend') {
//             agent any
//             steps {
//                 dir('Android') {
//                     script {
//                         sh 'docker build -t front-image:latest -f Dockerfile .'
//                     }
//                 }
//             }
//         }

        stage('Build Backend') {
            agent any
            steps {
                dir('BackEnd/dmobile') {
                    script {
                        sh 'chmod +x ./gradlew'
                        sh './gradlew clean build'
                        sh 'docker build -t back-image:latest -f Dockerfile .'
                    }
                }
            }
        }
        stage('Docker run') {
            agent any
            steps {
                script {

                // 현재 동작중인 컨테이너 중 front-image의 이
                // 컨테이너를 stop 한다
//                 sh 'docker ps -f name=front-image -q \
//                 | xargs --no-run-if-empty docker container stop'
                // front-image의 이름을 가진 컨테이너를 삭제함
//                 try {
//                     sh 'docker container ls -a -f name=front-image \
//                     | xargs -r docker container rm'
//                 } catch (Exception e) {
//                     // 에러를 무시하고 계속 진행
//                     echo "Failed to remove Docker container: ${e.message}"
//                 }

                // 현재 동작중인 컨테이너 중 back-image의 이
                // 컨테이너를 stop 한다
                sh 'docker ps -f name=back-image -q | xargs --no-run-if-empty docker container stop'
//                 sh 'docker stop back-image'
                // back-image의 이름을 가진 컨테이너를 삭제함
                try {
//                     sh 'docker container ls -a -f name=back-image | xargs -r docker container rm'
                    sh 'docker rm back-image'
                } catch (Exception e) {
                    // 에러를 무시하고 계속 진행
                    echo "Failed to remove Docker container: ${e.message}"
                }

                // docker image build 시 기존에 존재하던 이미지는
                // dangling 상태가 되기 때문에 이미지를 일괄 삭제
                try {
                    sh 'docker images -f "dangling=true" -q | xargs -r docker rmi'
                } catch (Exception e) {
                    // 에러를 무시하고 계속 진행
                    echo "Failed to remove Docker container: ${e.message}"
                }

                // docker container 실행
//                 sh 'docker run -d --name front-image -p 80:80 front-image'
                sh 'docker run -d --name back-image -p 8080:8080 back-image'
                }
            }
        }
    }
}
