package io.hightide;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class HightidePlugin implements Plugin<Project> {
    
    String mainClassName = "io.hightide.Application";
    
    void apply(Project project) { 
        
        project.repositories {
            mavenLocal()
            mavenCentral()
        }

        project.configurations {
            reloadAgent            
        }

        project.dependencies {
            reloadAgent "io.hightide:hightide-reloader:0.1-SNAPSHOT"
        }

        project.task('run', dependsOn: 'classes', type: JavaExec) {
            main = mainClassName
            classpath = project.sourceSets.main.runtimeClasspath
            reloaderJar = project.configurations.reloadAgent.files.iterator().next()   
            jvmArgs = ["-javaagent:${reloaderJar}"]
        }

        project.task('debug', dependsOn: 'classes', type: JavaExec) {
            main = mainClassName
            classpath = project.sourceSets.main.runtimeClasspath
            reloaderJar = project.configurations.reloadAgent.files.iterator().next()   
            jvmArgs = ["-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005", "-javaagent:${reloaderJar}"]
        }
    }
        
}