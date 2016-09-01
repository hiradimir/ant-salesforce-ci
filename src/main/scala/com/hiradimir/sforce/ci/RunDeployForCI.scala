package com.hiradimir.sforce.ci; 
import org.apache.tools.ant.Main;  
import org.apache.tools.ant.Project
  
/**
 * For Debugging App
 */
object RunDeployForCI extends App{  
    var task = new DeployForCITask();
    
    var project = new Project
    project.setBaseDir(new java.io.File("ForceComSample/"))
    
    task.setTaskName("deployForCI")
    task.setProject(project)
    task.setUsername(sys.env("sforce_ci_username"))
    task.setPassword(sys.env("sforce_ci_passphrase"))
    task.setServerURL(sys.env("sforce_ci_serverurl"))
    task.setSobjectPlural("true")
    task.setTestLevel("RunAllTestsInOrg")
    task.setDeployRoot("ForceComSample/src")
    
    task.setNoErrorOnTestFail(true)
    task.setNoErrorOnCoveargeWarning(true)
    
    task.setCheckonly(true);
    task.setRollbackOnError(true);
    task.setAutoUpdatePackage(true);
    
    task.execute();

}
