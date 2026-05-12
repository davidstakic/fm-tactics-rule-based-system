package com.ftn.sbnz.kjar;

import java.io.InputStream;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;

public class TemplateRuleLoader {

        public static String compileTemplate(
                        String templatePath,
                        String xlsPath) {

                try {

                        InputStream templateStream = TemplateRuleLoader.class.getResourceAsStream(templatePath);

                        InputStream xlsStream = TemplateRuleLoader.class.getResourceAsStream(xlsPath);

                        ExternalSpreadsheetCompiler compiler = new ExternalSpreadsheetCompiler();

                        return compiler.compile(
                                        xlsStream,
                                        templateStream,
                                        2,
                                        1);

                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }
}
