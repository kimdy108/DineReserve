package com.project.dine.reserve.config.annotation.redisson;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringELParser {

    public static Object getDynamicValue(
            String[] parameterNames,
            Object[] args,
            String key
    ) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        ParserContext parserContext = new TemplateParserContext();

        return parser.parseExpression(key, parserContext)
                .getValue(context, Object.class);
    }
}