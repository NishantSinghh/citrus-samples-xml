/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.samples.todolist;

import com.consol.citrus.annotations.CitrusXmlTest;
import com.consol.citrus.jdbc.server.JdbcServer;
import com.consol.citrus.testng.AbstractTestNGCitrusTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TodoListIT extends AbstractTestNGCitrusTest {

    @Autowired
    private JdbcServer jdbcServer;

    @Test
    @CitrusXmlTest(name = "TodoListIT.TransactionHandling")
    public void testTransaction() {
    }

    @Test
    @CitrusXmlTest(name = "TodoListIT.TransactionRollback")
    public void testRollback() {
    }

    @Test
    @CitrusXmlTest(name = "TodoListIT.AutoTransactionHandling")
    public void testAutoTransactionHandling() {
        jdbcServer.getEndpointConfiguration().setAutoTransactionHandling(true);
    }

    @AfterMethod
    public void resetTransactionState(){
        jdbcServer.getEndpointConfiguration().setAutoTransactionHandling(false);
    }
}
