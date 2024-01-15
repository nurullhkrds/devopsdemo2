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
                    echo "Snyk is already installed." // Assuming Snyk is pre-installed
                }
            }
        }


        stage('Cleanup') {
            steps {
                script {
                    // If the demo directory exists, delete it
                    if (fileExists('demo')) {
                        bat 'rmdir /s /q demo'
                    }
                }
            }
        }

        stage('Checkout github') {
            steps {
                script {
                    // Use the specified credentials to clone the GitHub repository
                    git branch: 'main', credentialsId: 'github', url: 'https://github.com/nurullhkrds/devopsdenemeson.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Add your Maven commands here
                    bat 'mvn clean install'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image using the specified Dockerfile
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
                    // Pull Docker image from Docker Hub
                    bat "docker pull ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Snyk Scan') {
            steps {
                script {
                    // Snyk taramasını gerçekleştir
                    def snykResult = bat(script: '"C:\\Users\\Windows 11\\AppData\\Roaming\\npm\\snyk" test --json', returnStatus: true).trim()
                    def snykJson = readJSON text: snykResult

                    if (snykJson.issues.length > 0) {
                        echo "Security vulnerabilities found! Please check Snyk for details."
                        snykJson.issues.each { issue ->
                            echo "Issue: ${issue.title}"
                            echo "Severity: ${issue.severity}"
                            echo "URL: ${issue.url}"
                        }
                        error "Security vulnerabilities found! See above for details."
                    } else {
                        echo "No security vulnerabilities found."
                    }
                }
            }
        }

    }
}
