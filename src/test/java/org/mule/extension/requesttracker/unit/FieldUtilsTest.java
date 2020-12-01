package org.mule.extension.requesttracker.unit;

import org.junit.Assert;
import org.junit.Test;
import org.mule.extension.requesttracker.api.models.request.Fields;
import org.mule.extension.requesttracker.internal.annotations.RTName;
import org.mule.extension.requesttracker.internal.utils.FieldUtils;
import org.mule.runtime.extension.api.annotation.param.Parameter;

public class FieldUtilsTest {

    @Test
    public void testAsParams() {
        TestClass fieldsClass = new TestClass();
        fieldsClass.setName(true);
        fieldsClass.setEmailAddress(true);
        fieldsClass.setNotThis(true);
        fieldsClass.setRtName(true);
        fieldsClass.setNotThis(false);
        String params = FieldUtils.asParam(fieldsClass, TestClass.class);
        Assert.assertNotNull(params);
        Assert.assertEquals("id,Name,EmailAddress,thisName", params);

    }

    public static class TestClass implements Fields {
        @Parameter
        private boolean name;
        @Parameter
        private boolean emailAddress;
        private boolean notThis;
        @Parameter
        @RTName("thisName")
        private boolean rtName;
        @Parameter
        private boolean norThis;

        public boolean isName() {
            return name;
        }

        public void setName(boolean name) {
            this.name = name;
        }

        public boolean isEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(boolean emailAddress) {
            this.emailAddress = emailAddress;
        }

        public boolean isNotThis() {
            return notThis;
        }

        public void setNotThis(boolean notThis) {
            this.notThis = notThis;
        }

        public boolean isRtName() {
            return rtName;
        }

        public void setRtName(boolean rtName) {
            this.rtName = rtName;
        }

        public boolean isNorThis() {
            return norThis;
        }

        public void setNorThis(boolean norThis) {
            this.norThis = norThis;
        }
    }
}
