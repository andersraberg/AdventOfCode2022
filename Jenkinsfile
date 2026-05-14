node {
    env.GRADLE_USER_HOME = "${WORKSPACE}/.gradle"

    git 'https://github.com/andersraberg/AdventOfCode2022.git'

    dir('../common-catalog') {
        git 'https://github.com/andersraberg/common-catalog.git'
    }

    stage('Build') {
        sh './gradlew clean build -Pversion=$BUILD_NUMBER --profile'
    }

    stage('Run') {
        sh './gradlew run'
    }

    stage('Code coverage') {
        sh './gradlew jacocoTestReport -Pversion=$BUILD_NUMBER'

        recordCoverage tools: [
            [parser: 'JACOCO', pattern: '**/build/reports/jacoco/test/jacocoTestReport.xml'] 
        ]	

    }

    stage('Sonar') {
        withSonarQubeEnv() {
            sh './gradlew sonarqube -Dsonar.projectKey=AdventofCode2022 -Pversion=$BUILD_NUMBER'
        }
    }
    
}
