/*
 * PathOperator.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.nio.file.Path;

/**
 * The Interface PathOperator.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface PathOperator {

    
    /**
     * Operate on directory.
     * @param path the path
     */
    void operateOnDirectory(final Path path);
    
   
    /**
     * Operate on file.
     * @param path the path
     */
    void operateOnFile(final Path path);
}
