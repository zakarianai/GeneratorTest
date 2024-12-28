package org.javascanner.util;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.javascanner.dto.Type;

public class TypeUtils {
    /**
     * Identifies the class type (ENTITY, CONTROLLER, SERVICE) based on annotations.
     */
    public static Type identifyClassType(ClassOrInterfaceDeclaration classOrInterface) {
        if (classOrInterface.isAnnotationPresent("Entity") || classOrInterface.isAnnotationPresent("Table")) {
            return Type.ENTITY;
        }
        if (classOrInterface.isAnnotationPresent("RestController") || classOrInterface.isAnnotationPresent("Controller")) {
            return Type.CONTROLLER;
        }
        if (classOrInterface.isAnnotationPresent("Service")) {
            return Type.SERVICE;
        }
//        if (classOrInterface.isAnnotationPresent("Repository")) {
//            return Type.REPOSITORY;
//        }
        return null; // Return null if the class doesn't match any type
    }
}
