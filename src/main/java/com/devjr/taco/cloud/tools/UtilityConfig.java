package com.devjr.taco.cloud.tools;

public class UtilityConfig{

    public static final String S_URL_SERVER_FRONTEND = "http://localhost:4200";
    public static final String S_URL_SERVER_FRONTED = "http://localhost:8080";
    public static final String S_URL_CONTEXT_ROOT = ""; //"/taco-cloud";
    public static final String S_URL_RS = UtilityConfig.S_URL_SERVER_FRONTED + UtilityConfig.S_URL_CONTEXT_ROOT;
    public static final String S_PATH_HOME = S_URL_CONTEXT_ROOT;
    public static final String S_VIEW_HOME = "home";
    public static final String S_PATH_DESIGN = S_URL_CONTEXT_ROOT + "/design";
    public static final String S_VIEW_DESIGN = "design";

    public static final String S_PACKAGE_ROOT = "com.devjr.taco.cloud";
    public static final String S_PACKAGE_CONTROLLERS = UtilityConfig.S_PACKAGE_ROOT + ".controllers";
    public static final String S_PACKAGE_ENTITIES = UtilityConfig.S_PACKAGE_ROOT + ".entities";
    public static final String S_PACKAGE_REPOSITORIES = UtilityConfig.S_PACKAGE_ROOT + ".repositories";
    public static final String S_PACKAGE_TOOLS = UtilityConfig.S_PACKAGE_ROOT + ".tools";

    private UtilityConfig(){
        throw new IllegalStateException(UtilityConfig.class + " » config utility class");
    }

}
