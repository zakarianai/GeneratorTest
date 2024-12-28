package org.javascanner.util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import org.javascanner.dto.FieldDetail;
import org.javascanner.dto.Type;
import org.javascanner.dto.ClassDetail;
import org.javascanner.dto.MethodDetail;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.javascanner.util.TypeUtils.identifyClassType;

public class PackageScanner {

    public static List<ClassDetail> scanPackage(String path) {
        List<ClassDetail> classDetails = new ArrayList<>();
        try {
            // Traverse the project folder for `.java` files
            Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(file -> {
                    try {
                        // Parse the Java file
                        CompilationUnit compilationUnit = StaticJavaParser.parse(file);

                        // Extract package name
                        String packageName = compilationUnit.getPackageDeclaration()
                                .map(PackageDeclaration::getNameAsString)
                                .orElse("default");

                        // Find all class or interface declarations
                        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterface -> {
                            // Identify the type of the class (ENTITY, CONTROLLER, SERVICE)
                            Type classType = identifyClassType(classOrInterface);

                            if (classType != null) {
                                // Process the class details
                                ClassDetail classDetail = new ClassDetail();
                                classDetail.setClassName(classOrInterface.getNameAsString());
                                classDetail.setType(classType);
                                classDetail.setPackageName(packageName);

                                // Extract fields
                                List<FieldDetail> fields = new ArrayList<>();
                                classOrInterface.findAll(FieldDeclaration.class).forEach(field -> {
                                    field.getVariables().forEach(variable -> {
                                        FieldDetail fieldDetail = new FieldDetail();
                                        fieldDetail.setName(variable.getNameAsString());
                                        fieldDetail.setType(variable.getType().asString());
                                        fields.add(fieldDetail);
                                    });
                                });

                                classDetail.setFields(fields);

                                // Extract method signature and body
                                List<MethodDetail> methods = new ArrayList<>();
                                compilationUnit.findAll(MethodDeclaration.class).forEach(method -> {
                                    // Construct the full method signature
                                    String name = method.getNameAsString();
                                    String parameters = method.getParameters().stream()
                                            .map(param -> param.getType().asString() + " " + param.getNameAsString())
                                            .collect(Collectors.joining(", "));
                                    String returnType = method.getTypeAsString();

                                    String fullSignature = returnType + " " + name + "(" + parameters + ")";

                                    // Get the method body as a string
                                    String body = method.getBody().map(Object::toString).orElse("No body");

                                    // Add the method details to the list
                                    methods.add(new MethodDetail(fullSignature, body));
                                });

                                // Attach method details to the class
                                if(!methods.isEmpty()) {
                                    classDetail.setMethodDetail(methods);
                                }

                                classDetails.add(classDetail);
                            }
                        });
                    } catch (Exception e) {
                        System.err.println("Error parsing file: " + file + " - " + e.getMessage());
                    }
                });
        } catch (Exception e) {
            System.err.println("Error scanning package: " + e.getMessage());
        }

        return classDetails;
    }


}
