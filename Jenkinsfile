pipeline {
    agent any

    tools {
        maven 'Maven-3.9.6'
        jdk 'JDK-17'
    }

    environment {
        DOCKER_IMAGE_NAME = 'nurullahkardas/docker1'
        DOCKER_IMAGE_TAG = 'tagname'
    }

    stages {

        stage('Install Snyk') {
            steps {
                script {
                    // Snyk yüklü olduğunu varsayalım
                    echo "Snyk is already installed."
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // demo dizini varsa sil
                    if (fileExists('demo')) {
                        bat 'rmdir /s /q demo'
                    }
                }
            }
        }

        stage('Checkout GitHub') {
            steps {
                script {
                    // GitHub deposunu belirtilen kimlik bilgileriyle klonla
                    git branch: 'main', credentialsId: 'github', url: 'https://github.com/nurullhkrds/devopsdemo2.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Maven komutlarını buraya ekleyin
                    bat 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Belirtilen Dockerfile'ı kullanarak Docker imajını oluştur
                    bat "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Jenkins Credential Manager'dan Docker Hub kullanıcı adı ve şifresini al
                    withCredentials([usernamePassword(credentialsId: 'docker1', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        // Docker Hub'a giriş yap
                        bat "docker login -u %DOCKER_HUB_USERNAME% -p %DOCKER_HUB_PASSWORD%"

                        // Docker Image'ını Docker Hub'a gönder
                        bat "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                    }
                }
            }
        }

        stage('Pull Docker Image') {
            steps {
                script {
                    // Docker Hub'dan Docker imajını çek
                    bat "docker pull ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                }
            }
        }
        
        stage('Snyk Auth and Scan') {
    steps {
        script {
            // Snyk kimlik doğrulamasını yap ve API belirtecini al
            withCredentials([string(credentialsId: 'snyk-token', variable: 'SNYK_TOKEN')]) {
                def snykCommand = "\"C:\\Users\\Nurullah\\AppData\\Roaming\\npm\\snyk\" test --json"
                bat "echo 'SNYK_TOKEN=${SNYK_TOKEN}' > .env"
                def snykResult = bat(script: snykCommand, returnStatus: true)
                if (snykResult != 0) {
                    error "Snyk test failed. Exit code: ${snykResult}"
                }
            }
        }
    }
}

     



    }
}
