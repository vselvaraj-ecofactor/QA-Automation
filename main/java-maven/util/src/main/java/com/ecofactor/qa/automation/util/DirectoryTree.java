/*
 * DirectoryTree.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * The Class DirectoryTree.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DirectoryTree extends SimpleFileVisitor<Path> {

    PathOperator operator;

    /**
     * Instantiates a new directory tree.
     * 
     * @param operator the operator
     */
    public DirectoryTree(PathOperator operator) {

        super();
        this.operator = operator;
    }

    /*
     * Method override
     */
    /**
     * @param dir
     * @param exc
     * @return
     * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

        operator.operateOnDirectory(dir);
        return FileVisitResult.CONTINUE;
    }

    /*
     * Method override
     */
    /**
     * @param file
     * @param traversalException
     * @return
     * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException traversalException) {

        return FileVisitResult.CONTINUE;
    }

    /*
     * Method override
     */
    /**
     * @param dir
     * @param attrs
     * @return
     * @throws IOException
     * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        return super.preVisitDirectory(dir, attrs);
    }

    /*
     * Method override
     */
    /**
     * @param file
     * @param attrs
     * @return
     * @throws IOException
     * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        operator.operateOnFile(file);
        return super.visitFile(file, attrs);
    }

}

