pipeline {
    agent any

    tools {
        maven 'Maven-3.9.6'
        jdk 'JDK-17'
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
                    // Eğer demo dizini varsa sil
                    if (fileExists('demo')) {
                        bat 'rmdir /s /q demo'
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                script {
                    // GitHub deposunu çekmek için bu kimliği kullan
                    git branch: 'main', credentialsId: 'github', url: 'https://github.com/nurullhkrds/demo.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Maven komutlarınızı buraya ekleyin
                    bat 'mvn clean install'
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

       stage('Check Security Issues') {
            steps {
                script {
                    // Snyk çıktısını kontrol et ve güvenlik sorunları varsa hata bildir
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
