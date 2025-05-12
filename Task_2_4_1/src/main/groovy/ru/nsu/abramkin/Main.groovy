package ru.nsu.abramkin

import groovy.util.ConfigSlurper

class Main {
    static void main(String[] args) {
        def config = new ConfigSlurper().parse(new File('src/main/groovy/config.groovy').toURL())
        def taskChecker = new TaskChecker()
        taskChecker.processTasks(config)
    }
} 